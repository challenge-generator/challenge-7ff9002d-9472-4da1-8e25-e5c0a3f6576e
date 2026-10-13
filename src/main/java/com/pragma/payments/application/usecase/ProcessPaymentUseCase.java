package com.pragma.payments.application.usecase;

import com.pragma.payments.domain.model.Transaction;
import com.pragma.payments.domain.model.TransactionStatus;
import com.pragma.payments.domain.port.CreditOriginatorPort;
import com.pragma.payments.domain.port.PaymentGatewayPort;
import com.pragma.payments.infrastructure.exception.PaymentGatewayException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Caso de uso principal que orquesta el procesamiento completo de un pago.
 * Coordina la interacción entre el originador de créditos y la gateway de pagos,
 * manejando errores y reintentos de manera centralizada.
 *
 * Este caso de uso sigue el patrón de aplicación orquestadora, delegando las
 * operaciones específicas a los puertos del dominio que son implementados por
 * los adaptadores de infraestructura.
 */
@Component
public class ProcessPaymentUseCase {

    private static final Logger log = LoggerFactory.getLogger(ProcessPaymentUseCase.class);
    private static final int MAX_RETRIES = 3;

    private final PaymentGatewayPort paymentGatewayPort;
    private final CreditOriginatorPort creditOriginatorPort;

    public ProcessPaymentUseCase(
            PaymentGatewayPort paymentGatewayPort,
            CreditOriginatorPort creditOriginatorPort) {
        this.paymentGatewayPort = paymentGatewayPort;
        this.creditOriginatorPort = creditOriginatorPort;
    }

    /**
     * Ejecuta el flujo completo de procesamiento de un pago.
     *
     * El flujo incluye:
     * 1. Registro inicial de la transacción en el originador de créditos
     * 2. Procesamiento del pago a través de la gateway
     * 3. Actualización del estado de la transacción
     *
     * @param transaction la transacción a procesar
     * @return la transacción procesada con estado actualizado
     */
    @Retry(name = "paymentProcessRetry")
    @CircuitBreaker(name = "paymentCircuitBreaker", fallbackMethod = "processPaymentFallback")
    public Transaction execute(Transaction transaction) {
        log.info("Iniciando procesamiento de pago para transacción: {}", transaction.getId());

        validateTransaction(transaction);

        Transaction registeredTransaction = registerTransaction(transaction);

        Transaction processedTransaction = processWithGateway(registeredTransaction);

        updateTransactionStatus(processedTransaction);

        log.info("Pago procesado exitosamente para transacción: {}", processedTransaction.getId());
        return processedTransaction;
    }

    /**
     * Valida que la transacción tenga todos los datos requeridos.
     */
    private void validateTransaction(Transaction transaction) {
        if (transaction.getAmount() == null || transaction.getAmount().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto de la transacción debe ser mayor a cero");
        }
        if (transaction.getMerchantId() == null || transaction.getMerchantId().isBlank()) {
            throw new IllegalArgumentException("El ID del comerciante es requerido");
        }
        if (transaction.getCustomerId() == null || transaction.getCustomerId().isBlank()) {
            throw new IllegalArgumentException("El ID del cliente es requerido");
        }
        log.debug("Transacción validada correctamente");
    }

    /**
     * Registra la transacción en el originador de créditos.
     */
    private Transaction registerTransaction(Transaction transaction) {
        try {
            log.debug("Registrando transacción en originador de créditos: {}", transaction.getId());
            Transaction registered = creditOriginatorPort.registerTransaction(transaction);
            log.info("Transacción registrada exitosamente en originador: {}", registered.getId());
            return registered;
        } catch (Exception e) {
            log.error("Error al registrar transacción en originador: {}", e.getMessage());
            transaction.setFailureReason("Error en registro de transacción: " + e.getMessage());
            throw new RuntimeException("Fallo en registro de transacción", e);
        }
    }

    /**
     * Procesa el pago a través de la gateway de pagos.
     */
    private Transaction processWithGateway(Transaction transaction) {
        try {
            log.debug("Procesando pago con gateway para transacción: {}", transaction.getId());
            Transaction processed = paymentGatewayPort.processPayment(transaction);
            log.info("Pago procesado por gateway para transacción: {}", processed.getId());
            return processed;
        } catch (PaymentGatewayException e) {
            log.error("Error de la gateway de pagos: {}", e.getMessage());
            transaction.setFailureReason("Error en gateway de pagos: " + e.getMessage());
            throw new RuntimeException("Fallo en procesamiento de pago", e);
        }
    }

    /**
     * Actualiza el estado de la transacción en el originador de créditos.
     */
    private void updateTransactionStatus(Transaction transaction) {
        try {
            log.debug("Actualizando estado de transacción: {}", transaction.getId());
            TransactionStatus newStatus = determineTransactionStatus(transaction);
            creditOriginatorPort.updateTransactionStatus(
                transaction.getId().toString(),
                newStatus,
                Optional.ofNullable(transaction.getFailureReason())
            );
            log.info("Estado de transacción actualizado a: {}", newStatus);
        } catch (Exception e) {
            log.warn("Error al actualizar estado de transacción: {}", e.getMessage());
        }
    }

    /**
     * Determina el estado final de la transacción basado en el resultado del procesamiento.
     */
    private TransactionStatus determineTransactionStatus(Transaction transaction) {
        if (transaction.getFailureReason() != null && !transaction.getFailureReason().isBlank()) {
            return TransactionStatus.FAILED;
        }
        return TransactionStatus.COMPLETED;
    }

    /**
     * Método de fallback cuando el circuit breaker está abierto o hay una excepción.
     * Proporciona una respuesta degradada graceful.
     */
    private Transaction processPaymentFallback(Transaction transaction, Throwable throwable) {
        log.error("Circuit breaker activado para transacción: {}. Error: {}", 
            transaction.getId(), throwable.getMessage());

        transaction.setFailureReason("Sistema temporalmente no disponible. Por favor intente más tarde.");
        transaction.setStatus(TransactionStatus.PENDING);

        return transaction;
    }

    /**
     * Reintenta una transacción que falló anteriormente.
     * Verifica que la transacción aún pueda ser reintentada según la configuración.
     *
     * @param transactionId ID de la transacción a reintentar
     * @return la transacción reintentada
     */
    @Retry(name = "paymentRetry")
    public Transaction retryTransaction(UUID transactionId) {
        log.info("Iniciando reintento para transacción: {}", transactionId);

        Optional<Transaction> transactionOpt = creditOriginatorPort.getTransactionById(transactionId.toString());
        if (transactionOpt.isEmpty()) {
            throw new IllegalArgumentException("Transacción no encontrada: " + transactionId);
        }

        Transaction transaction = transactionOpt.get();
        if (!transaction.canRetry(MAX_RETRIES)) {
            throw new IllegalStateException("Transacción excedió el límite de reintentos");
        }

        transaction.incrementRetryCount();
        return execute(transaction);
    }
}