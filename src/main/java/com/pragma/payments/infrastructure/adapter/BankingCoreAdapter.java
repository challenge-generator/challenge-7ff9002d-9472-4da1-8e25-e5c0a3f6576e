package com.pragma.payments.infrastructure.adapter;

import com.pragma.payments.domain.model.Transaction;
import com.pragma.payments.domain.port.BankingCorePort;
import com.pragma.payments.infrastructure.exception.BankingCoreException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class BankingCoreAdapter implements BankingCorePort {

    @Override
    @CircuitBreaker(name = "bankingCoreCircuitBreaker", fallbackMethod = "fallbackProcessPayment")
    @Retry(name = "bankingCoreRetry")
    public PaymentResult processPayment(Transaction transaction) throws BankingCoreException {
        log.info("Iniciando procesamiento de pago en core bancario para transacción: {}", transaction.getId());

        try {
            validatePaymentRequest(transaction);

            PaymentResult result = executePayment(transaction);

            log.info("Pago procesado exitosamente. TransactionId: {}, AuthorizationCode: {}",
                    transaction.getId(), result.authorizationCode());

            return result;
        } catch (BankingCoreException e) {
            log.error("Error en procesamiento de pago para transacción {}: {}", transaction.getId(), e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado en core bancario para transacción {}: {}", transaction.getId(), e.getMessage());
            throw new BankingCoreException("Error inesperado en procesamiento: " + e.getMessage(), e);
        }
    }

    private void validatePaymentRequest(Transaction transaction) throws BankingCoreException {
        log.debug("Validando solicitud de pago para transacción: {}", transaction.getId());

        if (transaction.getAmount() == null || transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            log.error("Monto inválido para transacción: {}", transaction.getId());
            throw new BankingCoreException("Monto inválido");
        }

        if (transaction.getAmount().compareTo(new BigDecimal("50000")) > 0) {
            log.error("Monto excede límite máximo permitido: {}", transaction.getAmount());
            throw new BankingCoreException("Monto excede límite máximo");
        }

        if (transaction.getMerchantId() == null || transaction.getMerchantId().isBlank()) {
            log.error("Merchant ID no proporcionado para transacción: {}", transaction.getId());
            throw new BankingCoreException("Merchant ID requerido");
        }

        if (transaction.getCustomerId() == null || transaction.getCustomerId().isBlank()) {
            log.error("Customer ID no proporcionado para transacción: {}", transaction.getId());
            throw new BankingCoreException("Customer ID requerido");
        }

        log.debug("Validación completada exitosamente para transacción: {}", transaction.getId());
    }

    private PaymentResult executePayment(Transaction transaction) {
        log.debug("Ejecutando pago en sistema bancario para amount: {}, currency: {}",
                transaction.getAmount(), transaction.getCurrency());

        String authorizationCode = generateAuthorizationCode();

        String referenceNumber = generateReferenceNumber(transaction);

        return new PaymentResult(
                transaction.getId().toString(),
                authorizationCode,
                referenceNumber,
                PaymentStatus.APPROVED,
                LocalDateTime.now(),
                "Pago procesado exitosamente"
        );
    }

    private String generateAuthorizationCode() {
        return "AUTH-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private String generateReferenceNumber(Transaction transaction) {
        return "REF-" + transaction.getMerchantId().substring(0, Math.min(4, transaction.getMerchantId().length()))
                + "-" + System.currentTimeMillis();
    }

    private PaymentResult fallbackProcessPayment(Transaction transaction, Exception e) {
        log.error("FALLBACK: Circuit breaker abierto o reintentos agotados para BankingCore. Transacción: {}. Error: {}",
                transaction.getId(), e.getMessage());

        throw new BankingCoreException("Core bancario no disponible temporalmente. Por favor intente más tarde.", e);
    }

    @Override
    public PaymentResult reversePayment(String transactionId, String reason) throws BankingCoreException {
        log.info("Iniciando reversión de pago para transacción: {}, reason: {}", transactionId, reason);

        try {
            String reversalCode = "REV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

            log.info("Reversión completada para transacción: {}, código: {}", transactionId, reversalCode);

            return new PaymentResult(
                    transactionId,
                    reversalCode,
                    "REVERSAL-" + transactionId,
                    PaymentStatus.REVERSED,
                    LocalDateTime.now(),
                    "Reversión exitosa: " + reason
            );
        } catch (Exception e) {
            log.error("Error en reversión de pago para transacción {}: {}", transactionId, e.getMessage());
            throw new BankingCoreException("Error en reversión: " + e.getMessage(), e);
        }
    }

    @Override
    public AccountBalance getAccountBalance(String accountId) throws BankingCoreException {
        log.info("Consultando saldo para cuenta: {}", accountId);

        try {
            BigDecimal availableBalance = new BigDecimal("15000.00");
            BigDecimal holdBalance = new BigDecimal("500.00");

            return new AccountBalance(
                    accountId,
                    availableBalance,
                    holdBalance,
                    availableBalance.subtract(holdBalance),
                    LocalDateTime.now()
            );
        } catch (Exception e) {
            log.error("Error consultando saldo para cuenta {}: {}", accountId, e.getMessage());
            throw new BankingCoreException("Error consultando saldo: " + e.getMessage(), e);
        }
    }

    @Override
    public String getServiceName() {
        return "BankingCoreService";
    }

    @Override
    public boolean isAvailable() {
        log.debug("Verificando disponibilidad del servicio BankingCore");
        return true;
    }

    public record PaymentResult(
            String transactionId,
            String authorizationCode,
            String referenceNumber,
            PaymentStatus status,
            LocalDateTime processedAt,
            String message
    ) {}

    public enum PaymentStatus {
        APPROVED, DECLINED, REVERSED, PENDING
    }

    public record AccountBalance(
            String accountId,
            BigDecimal availableBalance,
            BigDecimal holdBalance,
            BigDecimal totalBalance,
            LocalDateTime asOf
    ) {}
}