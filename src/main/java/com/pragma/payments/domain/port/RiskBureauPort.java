package com.pragma.payments.domain.port;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

/**
 * Puerto para interactuar con el buró de riesgos.
 * Define el contrato para consultar y报告ar información crediticia
 * del cliente relacionado con la transacción.
 */
public interface RiskBureauPort {

    /**
     * Consulta el perfil de riesgo de un cliente en el buró.
     * @param customerId identificador del cliente
     * @return perfil de riesgo con puntuación y detalles
     * @throws RiskBureauException si ocurre un error al consultar
     */
    RiskProfile getCustomerRiskProfile(String customerId) throws RiskBureauException;

    /**
     * Consulta el límite de crédito disponible para un cliente.
     * @param customerId identificador del cliente
     * @param merchantId identificador del comerciante
     * @return límite de crédito disponible
     * @throws RiskBureauException si ocurre un error al consultar
     */
    BigDecimal getAvailableCreditLimit(String customerId, String merchantId) throws RiskBureauException;

    /**
     * Reporta un cambio en el estado de riesgo del cliente.
     * @param customerId identificador del cliente
     * @param newStatus nuevo estado de riesgo
     * @param reason razón del cambio
     * @throws RiskBureauException si ocurre un error al reportar
     */
    void reportRiskStatusChange(String customerId, RiskStatus newStatus, String reason) throws RiskBureauException;

    /**
     * Consulta el historial de pagos del cliente.
     * @param customerId identificador del cliente
     * @param monthsAhead número de meses hacia atrás a consultar
     * @return historial de pagos
     * @throws RiskBureauException si ocurre un error al consultar
     */
    PaymentHistory getPaymentHistory(String customerId, int monthsAhead) throws RiskBureauException;

    /**
     * Perfil de riesgo de un cliente.
     */
    record RiskProfile(
        String customerId,
        int riskScore,
        RiskStatus status,
        BigDecimal totalDebt,
        BigDecimal creditLimit,
        int activeLoans,
        LocalDateTime lastUpdate,
        List<RiskFactor> riskFactors
    ) {
        public boolean isEligibleForTransaction(BigDecimal amount) {
            return status == RiskStatus.ACTIVE && 
                   riskScore >= 600 && 
                   totalDebt.compareTo(creditLimit.multiply(BigDecimal.valueOf(0.8))) < 0;
        }

        public boolean hasHighRiskIndicators() {
            return riskFactors.stream().anyMatch(RiskFactor::isHighImpact);
        }
    }

    /**
     * Estados posibles del riesgo del cliente.
     */
    enum RiskStatus {
        ACTIVE,
        SUSPENDED,
        DEFAULT,
        INACTIVE,
        UNDER_REVIEW
    }

    /**
     * Factor de riesgo específico.
     */
    record RiskFactor(
        String code,
        String description,
        int impactScore,
        boolean isHighImpact
    ) {}

    /**
     * Historial de pagos de un cliente.
     */
    record PaymentHistory(
        String customerId,
        int totalPayments,
        int onTimePayments,
        int latePayments,
        BigDecimal totalAmountPaid,
        BigDecimal averagePaymentAmount,
        List<PaymentRecord> recentPayments
    ) {
        public double getOnTimePaymentRate() {
            if (totalPayments == 0) return 0.0;
            return (double) onTimePayments / totalPayments * 100;
        }

        public boolean hasRecentLatePayments(int months) {
            LocalDateTime cutoff = LocalDateTime.now().minusMonths(months);
            return recentPayments.stream()
                .anyMatch(p -> p.paymentDate().isAfter(cutoff) && !p.isOnTime());
        }
    }

    /**
     * Registro de un pago específico.
     */
    record PaymentRecord(
        String paymentId,
        BigDecimal amount,
        LocalDateTime paymentDate,
        LocalDateTime dueDate,
        boolean isOnTime,
        String status
    ) {}

    /**
     * Excepción específica para errores del buró de riesgos.
     */
    class RiskBureauException extends RuntimeException {
        private final String errorCode;
        private final String customerId;

        public RiskBureauException(String message) {
            super(message);
            this.errorCode = "RBU000";
            this.customerId = null;
        }

        public RiskBureauException(String message, Throwable cause) {
            super(message, cause);
            this.errorCode = "RBU000";
            this.customerId = null;
        }

        public RiskBureauException(String message, String errorCode, String customerId) {
            super(message);
            this.errorCode = errorCode;
            this.customerId = customerId;
        }

        public RiskBureauException(String message, Throwable cause, String errorCode, String customerId) {
            super(message, cause);
            this.errorCode = errorCode;
            this.customerId = customerId;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public Optional<String> getCustomerId() {
            return Optional.ofNullable(customerId);
        }
    }
}