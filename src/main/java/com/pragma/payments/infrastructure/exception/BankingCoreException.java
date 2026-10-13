package com.pragma.payments.infrastructure.exception;

public class BankingCoreException extends RuntimeException {
    private final String accountId;
    private final String operationType;
    private final boolean retryable;
    private final int httpStatusCode;

    public BankingCoreException(String message) {
        super(message);
        this.accountId = null;
        this.operationType = null;
        this.retryable = false;
        this.httpStatusCode = 500;
    }

    public BankingCoreException(String message, Throwable cause) {
        super(message, cause);
        this.accountId = null;
        this.operationType = null;
        this.retryable = false;
        this.httpStatusCode = 500;
    }

    public BankingCoreException(String message, String accountId, String operationType, boolean retryable) {
        super(message);
        this.accountId = accountId;
        this.operationType = operationType;
        this.retryable = retryable;
        this.httpStatusCode = retryable ? 503 : 400;
    }

    public BankingCoreException(String message, String accountId, String operationType, boolean retryable, Throwable cause) {
        super(message, cause);
        this.accountId = accountId;
        this.operationType = operationType;
        this.retryable = retryable;
        this.httpStatusCode = retryable ? 503 : 400;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getOperationType() {
        return operationType;
    }

    public boolean isRetryable() {
        return retryable;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public boolean isInsufficientFunds() {
        return getMessage() != null && getMessage().toLowerCase().contains("insufficient funds");
    }

    public boolean isAccountLocked() {
        return getMessage() != null && getMessage().toLowerCase().contains("account locked");
    }
}