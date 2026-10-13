package com.pragma.payments.domain.port;

import com.pragma.payments.domain.model.Transaction;
import java.util.Optional;

/**
 * Puerto para interactuar con el motor antifraude.
 * Define el contrato que la infraestructura debe implementar para evaluar
 * transacciones en busca de patrones fraudulentos.
 */
public interface AntiFraudPort {

    /**
     * Evalúa una transacción contra las reglas del motor antifraude.
     * @param transaction la transacción a evaluar
     * @return resultado de la evaluación con código de riesgo
     * @throws AntiFraudEvaluationException si ocurre un error al evaluar
     */
    AntiFraudResult evaluateTransaction(Transaction transaction) throws AntiFraudEvaluationException;

    /**
     * Consulta el historial de alertas de fraude para un cliente.
     * @param customerId identificador del cliente
     * @return lista de alertas previas
     * @throws AntiFraudEvaluationException si ocurre un error al consultar
     */
    FraudAlertHistory getCustomerAlertHistory(String customerId) throws AntiFraudEvaluationException;

    /**
     * Reporta una actividad sospechosa al motor antifraude.
     * @param transaction la transacción sospechosa
     * @param reason razón de la sospecha
     * @throws AntiFraudEvaluationException si ocurre un error al reportar
     */
    void reportSuspiciousActivity(Transaction transaction, String reason) throws AntiFraudEvaluationException;

    /**
     * Resultado de la evaluación antifraude.
     */
    record AntiFraudResult(
        String transactionId,
        RiskLevel riskLevel,
        String evaluationCode,
        String message,
        Optional<List<String>> triggeredRules
    ) {
        public boolean isApproved() {
            return riskLevel == RiskLevel.LOW;
        }

        public boolean requiresManualReview() {
            return riskLevel == RiskLevel.MEDIUM;
        }
    }

    /**
     * Niveles de riesgo evaluados por el motor antifraude.
     */
    enum RiskLevel {
        LOW,
        MEDIUM,
        HIGH,
        BLOCKED
    }

    /**
     * Historial de alertas de fraude para un cliente.
     */
    record FraudAlertHistory(
        String customerId,
        int totalAlerts,
        int resolvedAlerts,
        LocalDateTime lastAlertDate,
        List<AlertDetail> recentAlerts
    ) {
        public boolean hasRecentAlerts(int days) {
            if (lastAlertDate == null) return false;
            return lastAlertDate.isAfter(LocalDateTime.now().minusDays(days));
        }
    }

    /**
     * Detalle de una alerta específica.
     */
    record AlertDetail(
        String alertId,
        String transactionId,
        String reason,
        LocalDateTime createdAt,
        boolean resolved
    ) {}

    /**
     * Excepción específica para errores del motor antifraude.
     */
    class AntiFraudEvaluationException extends RuntimeException {
        private final String errorCode;
        private final String transactionId;

        public AntiFraudEvaluationException(String message) {
            super(message);
            this.errorCode = "AFR000";
            this.transactionId = null;
        }

        public AntiFraudEvaluationException(String message, Throwable cause) {
            super(message, cause);
            this.errorCode = "AFR000";
            this.transactionId = null;
        }

        public AntiFraudEvaluationException(String message, String errorCode, String transactionId) {
            super(message);
            this.errorCode = errorCode;
            this.transactionId = transactionId;
        }

        public AntiFraudEvaluationException(String message, Throwable cause, String errorCode, String transactionId) {
            super(message, cause);
            this.errorCode = errorCode;
            this.transactionId = transactionId;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public Optional<String> getTransactionId() {
            return Optional.ofNullable(transactionId);
        }
    }
}