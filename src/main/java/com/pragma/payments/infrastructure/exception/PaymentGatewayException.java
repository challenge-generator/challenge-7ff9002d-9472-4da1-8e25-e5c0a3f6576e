package com.pragma.payments.infrastructure.exception;

public class PaymentGatewayException extends RuntimeException {
    private final String gatewayCode;
    private final String gatewayMessage;
    private final boolean retryable;
    private final long retryAfterMillis;

    public PaymentGatewayException(String message) {
        super(message);
        this.gatewayCode = null;
        this.gatewayMessage = null;
        this.retryable = false;
        this.retryAfterMillis = 0;
    }

    public PaymentGatewayException(String message, Throwable cause) {
        super(message, cause);
        this.gatewayCode = null;
        this.gatewayMessage = null;
        this.retryable = false;
        this.retryAfterMillis = 0;
    }

    public PaymentGatewayException(String message, String gatewayCode, String gatewayMessage, boolean retryable) {
        super(message);
        this.gatewayCode = gatewayCode;
        this.gatewayMessage = gatewayMessage;
        this.retryable = retryable;
        this.retryAfterMillis = retryable ? 5000 : 0;
    }

    public PaymentGatewayException(String message, String gatewayCode, String gatewayMessage, boolean retryable, long retryAfterMillis) {
        super(message);
        this.gatewayCode = gatewayCode;
        this.gatewayMessage = gatewayMessage;
        this.retryable = retryable;
        this.retryAfterMillis = retryAfterMillis;
    }

    public PaymentGatewayException(String message, String gatewayCode, String gatewayMessage, boolean retryable, long retryAfterMillis, Throwable cause) {
        super(message, cause);
        this.gatewayCode = gatewayCode;
        this.gatewayMessage = gatewayMessage;
        this.retryable = retryable;
        this.retryAfterMillis = retryAfterMillis;
    }

    public String getGatewayCode() {
        return gatewayCode;
    }

    public String getGatewayMessage() {
        return gatewayMessage;
    }

    public boolean isRetryable() {
        return retryable;
    }

    public long getRetryAfterMillis() {
        return retryAfterMillis;
    }

    public boolean isTimeout() {
        return gatewayCode != null && gatewayCode.equals("TIMEOUT");
    }

    public boolean isDeclined() {
        return gatewayCode != null && gatewayCode.startsWith("DECLINE");
    }
}