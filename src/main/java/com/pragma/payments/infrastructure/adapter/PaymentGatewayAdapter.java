package com.pragma.payments.infrastructure.adapter;

import com.pragma.payments.domain.model.Transaction;
import com.pragma.payments.domain.port.PaymentGatewayPort;
import com.pragma.payments.infrastructure.exception.PaymentGatewayException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class PaymentGatewayAdapter implements PaymentGatewayPort {

    private static final Logger log = LoggerFactory.getLogger(PaymentGatewayAdapter.class);
    private static final String GATEWAY_NAME = "PAYMENT_GATEWAY";
    private static final Map<String, Transaction> transactionStore = new HashMap<>();

    @Override
    @Retry(name = "paymentGatewayRetry", fallbackMethod = "processPaymentFallback")
    @CircuitBreaker(name = "paymentGatewayCircuitBreaker", fallbackMethod = "processPaymentFallback")
    public Transaction processPayment(Transaction transaction) {
        log.info("Procesando pago para transacción: {} - Monto: {} {}", 
                transaction.getId(), transaction.getAmount(), transaction.getCurrency());

        validateTransaction(transaction);

        String transactionKey = generateTransactionKey(transaction.getMerchantId(), transaction.getId());
        
        try {
            PaymentResult result = executePaymentGatewayCall(transaction);
            
            if (result.isSuccessful()) {
                transaction.markAsProcessed();
                transactionStore.put(transactionKey, transaction);
                log.info("Pago procesado exitosamente para transacción: {}", transaction.getId());
            } else {
                transaction.setFailureReason(result.getErrorMessage());
                log.warn("Pago rechazado por gateway para transacción: {} - Razón: {}", 
                        transaction.getId(), result.getErrorMessage());
            }
            
            return transaction;
            
        } catch (GatewayCommunicationException e) {
            log.error("Error de comunicación con gateway de pagos para transacción: {} - {}", 
                    transaction.getId(), e.getMessage());
            throw new PaymentGatewayException("Error de comunicación con gateway: " + e.getMessage(), e);
        } catch (GatewayTimeoutException e) {
            log.error("Timeout del gateway de pagos para transacción: {} - {}", 
                    transaction.getId(), e.getMessage());
            throw new PaymentGatewayException("Timeout del gateway: " + e.getMessage(), e);
        }
    }

    @Override
    @Retry(name = "paymentGatewayRetry")
    public Optional<Transaction> getPaymentStatus(String merchantId, String transactionId) {
        log.debug("Consultando estado del pago - Merchant: {} - Transaction: {}", merchantId, transactionId);
        
        String key = generateTransactionKey(merchantId, UUID.fromString(transactionId));
        return Optional.ofNullable(transactionStore.get(key));
    }

    @Override
    @Retry(name = "paymentGatewayRefundRetry")
    public Transaction refundPayment(String merchantId, String transactionId, BigDecimal amount) {
        log.info("Iniciando reembolso para transacción: {} - Monto: {}", transactionId, amount);
        
        String key = generateTransactionKey(merchantId, UUID.fromString(transactionId));
        Transaction originalTransaction = transactionStore.get(key);
        
        if (originalTransaction == null) {
            log.error("Transacción no encontrada para reembolso: {}", transactionId);
            throw new PaymentGatewayException("Transacción no encontrada: " + transactionId);
        }
        
        if (amount.compareTo(originalTransaction.getAmount()) > 0) {
            log.error("Monto de reembolso excede el original - Requerido: {} - Original: {}", 
                    amount, originalTransaction.getAmount());
            throw new PaymentGatewayException("Monto de reembolso excede el original");
        }
        
        RefundResult result = executeRefundCall(merchantId, transactionId, amount);
        
        if (result.isSuccessful()) {
            log.info("Reembolso procesado exitosamente para transacción: {}", transactionId);
        } else {
            log.warn("Reembolso rechazado para transacción: {} - Razón: {}", 
                    transactionId, result.getErrorMessage());
            throw new PaymentGatewayException("Reembolso rechazado: " + result.getErrorMessage());
        }
        
        return originalTransaction;
    }

    private void validateTransaction(Transaction transaction) {
        if (transaction.getAmount() == null || transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new PaymentGatewayException("Monto de transacción inválido");
        }
        if (transaction.getMerchantId() == null || transaction.getMerchantId().isBlank()) {
            throw new PaymentGatewayException("ID de comerciante inválido");
        }
        if (transaction.getCustomerId() == null || transaction.getCustomerId().isBlank()) {
            throw new PaymentGatewayException("ID de cliente inválido");
        }
        if (transaction.getPaymentMethod() == null || transaction.getPaymentMethod().isBlank()) {
            throw new PaymentGatewayException("Método de pago no especificado");
        }
    }

    private PaymentResult executePaymentGatewayCall(Transaction transaction) {
        log.debug("Ejecutando llamada al gateway de pagos para {}", transaction.getId());
        
        simulateGatewayProcessing(transaction);
        
        boolean simulatedSuccess = !transaction.getCustomerId().contains("DECLINED") 
                && !transaction.getMerchantId().contains("SUSPENDED");
        
        if (transaction.getAmount().compareTo(new BigDecimal("10000")) > 0) {
            return new PaymentResult(false, "Monto excede límite permitido");
        }
        
        return new PaymentResult(simulatedSuccess, 
                simulatedSuccess ? null : "Transacción declinada por el emisor");
    }

    private RefundResult executeRefundCall(String merchantId, String transactionId, BigDecimal amount) {
        log.debug("Ejecutando reembolso para transacción: {}", transactionId);
        
        simulateRefundProcessing(merchantId, transactionId);
        
        return new RefundResult(true, null);
    }

    private void simulateGatewayProcessing(Transaction transaction) {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("Simulación de procesamiento interrumpida para {}", transaction.getId());
        }
    }

    private void simulateRefundProcessing(String merchantId, String transactionId) {
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("Simulación de reembolso interrumpida para {}", transactionId);
        }
    }

    private String generateTransactionKey(String merchantId, UUID transactionId) {
        return merchantId + "_" + transactionId.toString();
    }

    private Transaction processPaymentFallback(Transaction transaction, Throwable t) {
        log.error("Fallback activado para transacción: {} - Causa: {}", 
                transaction.getId(), t.getMessage());
        transaction.setFailureReason("Servicio de pagos no disponible: " + t.getMessage());
        return transaction;
    }

    private record PaymentResult(boolean successful, String errorMessage) {}
    private record RefundResult(boolean successful, String errorMessage) {}

    private static class GatewayCommunicationException extends RuntimeException {
        public GatewayCommunicationException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    private static class GatewayTimeoutException extends RuntimeException {
        public GatewayTimeoutException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}