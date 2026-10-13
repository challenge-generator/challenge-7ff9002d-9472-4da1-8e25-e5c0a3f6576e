package com.pragma.payments.infrastructure.adapter;

import com.pragma.payments.domain.model.Transaction;
import com.pragma.payments.domain.port.RiskBureauPort;
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
public class RiskBureauAdapter implements RiskBureauPort {

    @Override
    @CircuitBreaker(name = "riskBureauCircuitBreaker", fallbackMethod = "fallbackEvaluateRisk")
    @Retry(name = "riskBureauRetry")
    public RiskScore evaluateRisk(Transaction transaction) throws RiskBureauException {
        log.info("Iniciando evaluación de riesgo para transacción: {}", transaction.getId());

        try {
            RiskScore score = calculateRiskScore(transaction);

            log.info("Evaluación de riesgo completada para transacción {}. Score: {}, Nivel: {}",
                    transaction.getId(), score.score(), score.riskLevel());

            return score;
        } catch (Exception e) {
            log.error("Error en evaluación de riesgo para transacción {}: {}", transaction.getId(), e.getMessage());
            throw new RiskBureauException("Error en evaluación de riesgo: " + e.getMessage(), e);
        }
    }

    private RiskScore calculateRiskScore(Transaction transaction) {
        log.debug("Calculando score de riesgo para merchant: {}, amount: {}",
                transaction.getMerchantId(), transaction.getAmount());

        int baseScore = 50;

        BigDecimal amount = transaction.getAmount();
        if (amount.compareTo(new BigDecimal("1000")) > 0) {
            baseScore += 20;
            log.debug("Monto mayor a 1000, incrementando riesgo");
        }

        if (amount.compareTo(new BigDecimal("5000")) > 0) {
            baseScore += 15;
            log.debug("Monto mayor a 5000, incrementando riesgo");
        }

        String paymentMethod = transaction.getPaymentMethod();
        if ("CREDIT_CARD".equals(paymentMethod)) {
            baseScore += 10;
            log.debug("Método de pago con tarjeta de crédito, ajuste de riesgo");
        } else if ("DEBIT_CARD".equals(paymentMethod)) {
            baseScore += 5;
        }

        String customerId = transaction.getCustomerId();
        if (customerId != null && customerId.length() < 8) {
            baseScore += 10;
            log.debug("Customer ID corto detectado, incrementando riesgo");
        }

        RiskLevel level = determineRiskLevel(baseScore);

        return new RiskScore(
                transaction.getId().toString(),
                baseScore,
                level,
                LocalDateTime.now(),
                generateRiskFactors(transaction)
        );
    }

    private RiskLevel determineRiskLevel(int score) {
        if (score >= 80) {
            return RiskLevel.HIGH;
        } else if (score >= 50) {
            return RiskLevel.MEDIUM;
        } else {
            return RiskLevel.LOW;
        }
    }

    private String[] generateRiskFactors(Transaction transaction) {
        return new String[]{
                "MONTO: " + transaction.getAmount(),
                "METODO: " + transaction.getPaymentMethod(),
                "MERCHANT: " + transaction.getMerchantId()
        };
    }

    private RiskScore fallbackEvaluateRisk(Transaction transaction, Exception e) {
        log.error("FALLBACK: Circuit breaker abierto o reintentos agotados para RiskBureau. Transacción: {}. Error: {}",
                transaction.getId(), e.getMessage());

        return new RiskScore(
                transaction.getId().toString(),
                50,
                RiskLevel.MEDIUM,
                LocalDateTime.now(),
                new String[]{"FALLBACK_ACTIVE", e.getMessage()}
        );
    }

    @Override
    public String getServiceName() {
        return "RiskBureauService";
    }

    @Override
    public boolean isAvailable() {
        log.debug("Verificando disponibilidad del servicio RiskBureau");
        return true;
    }

    public record RiskScore(
            String transactionId,
            int score,
            RiskLevel riskLevel,
            LocalDateTime evaluatedAt,
            String[] riskFactors
    ) {}

    public enum RiskLevel {
        LOW, MEDIUM, HIGH
    }

    public static class RiskBureauException extends RuntimeException {
        public RiskBureauException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}