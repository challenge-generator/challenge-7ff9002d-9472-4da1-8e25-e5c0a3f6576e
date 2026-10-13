package com.pragma.payments.infrastructure.exception;

public class AntiFraudException extends RuntimeException {
    private final String transactionId;
    private final String riskLevel;
    private final boolean retryable;

    public AntiFraudException(String message) {
        super(message);
        this.transactionId = null;
        this.riskLevel = null;
        this.retryable = false;
    }

    public AntiFraudException(String message, Throwable cause) {
        super(message, cause);
        this.transactionId = null;
        this.riskLevel = null;
        this.retryable = false;
    }

    public AntiFraudException(String message, String transactionId, String riskLevel, boolean retryable) {
        super(message);
        this.transactionId = transactionId;
        this.riskLevel = riskLevel;
        this.retryable = retryable;
    }

    public AntiFraudException(String message, String transactionId, String riskLevel, boolean retryable, Throwable cause) {
        super(message, cause);
        this.transactionId = transactionId;
        this.riskLevel = riskLevel;
        this.retryable = retryable;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public boolean isRetryable() {
        return retryable;
    }

    public boolean isHighRisk() {
        return "HIGH".equalsIgnoreCase(riskLevel);
    }

    public boolean isMediumRisk() {
        return "MEDIUM".equalsIgnoreCase(riskLevel);
    }

    public boolean requiresManualReview() {
        return "HIGH".equalsIgnoreCase(riskLevel) || "MEDIUM".equalsIgnoreCase(riskLevel);
    }
}