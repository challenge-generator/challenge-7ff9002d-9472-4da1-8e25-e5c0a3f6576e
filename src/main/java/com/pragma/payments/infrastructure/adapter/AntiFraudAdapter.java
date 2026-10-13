package com.pragma.payments.infrastructure.adapter;

import com.pragma.payments.domain.model.Transaction;
import com.pragma.payments.domain.port.AntiFraudPort;
import com.pragma.payments.infrastructure.exception.AntiFraudException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class AntiFraudAdapter implements AntiFraudPort {

    @Override
    @CircuitBreaker(name = "antiFraudCircuitBreaker", fallbackMethod = "fallbackValidateTransaction")
    @Retry(name = "antiFraudRetry")
    public boolean validateTransaction(Transaction transaction) throws AntiFraudException {
        log.info("Iniciando validación antifraude para transacción: {}", transaction.getId());

        try {
            boolean isValid = performAntiFraudValidation(transaction);

            if (isValid) {
                log.info("Transacción {} validada exitosamente por motor antifraude", transaction.getId());
            } else {
                log.warn("Transacción {} rechazada por motor antifraude", transaction.getId());
            }

            return isValid;
        } catch (Exception e) {
            log.error("Error en validación antifraude para transacción {}: {}", transaction.getId(), e.getMessage());
            throw new AntiFraudException("Error en validación antifraude: " + e.getMessage(), e);
        }
    }

    private boolean performAntiFraudValidation(Transaction transaction) {
        log.debug("Ejecutando validación interna de antifraude para amount: {}, merchant: {}",
                transaction.getAmount(), transaction.getMerchantId());

        if (transaction.getAmount().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            log.warn("Monto inválido detectado: {}", transaction.getAmount());
            return false;
        }

        if (transaction.getMerchantId() == null || transaction.getMerchantId().isBlank()) {
            log.warn("Merchant ID inválido");
            return false;
        }

        if (transaction.getCustomerId() == null || transaction.getCustomerId().isBlank()) {
            log.warn("Customer ID inválido");
            return false;
        }

        if (transaction.getAmount().compareTo(new java.math.BigDecimal("10000")) > 0) {
            log.info("Monto alto detectado, requiriendo validación adicional");
            return validateHighValueTransaction(transaction);
        }

        return true;
    }

    private boolean validateHighValueTransaction(Transaction transaction) {
        log.debug("Validando transacción de alto valor: {}", transaction.getAmount());

        String customerId = transaction.getCustomerId();
        if (customerId.startsWith("TEST")) {
            log.warn("Cliente de prueba detectado en transacción de alto valor");
            return false;
        }

        return true;
    }

    private boolean fallbackValidateTransaction(Transaction transaction, Exception e) {
        log.error("FALLBACK: Circuit breaker abierto o reintentos agotados para AntiFraud. Transacción: {}. Error: {}",
                transaction.getId(), e.getMessage());

        if (e instanceof AntiFraudException) {
            throw (AntiFraudException) e;
        }

        throw new AntiFraudException("Servicio de antifraude no disponible temporalmente", e);
    }

    @Override
    public String getServiceName() {
        return "AntiFraudService";
    }

    @Override
    public boolean isAvailable() {
        log.debug("Verificando disponibilidad del servicio AntiFraud");
        return true;
    }
}