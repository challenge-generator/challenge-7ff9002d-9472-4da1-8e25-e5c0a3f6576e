package com.pragma.payments.domain.model;

/**
 * Enum que representa los posibles estados de una transacción de pago.
 */
public enum TransactionStatus {
    PENDING,
    PROCESSING,
    SUCCESS,
    FAILED,
    RETRYING,
    REJECTED,
    CANCELLED
}