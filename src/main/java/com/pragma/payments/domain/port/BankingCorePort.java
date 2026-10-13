package com.pragma.payments.domain.port;

import com.pragma.payments.domain.model.Transaction;
import com.pragma.payments.domain.model.TransactionStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

/**
 * Puerto para interactuar con el core bancario.
 * Define el contrato para procesar transacciones financieras,
 * verificar cuentas y gestionar la compensación de pagos.
 */
public interface BankingCorePort {

    /**
     * Procesa un pago a través del core bancario.
     * @param transaction la transacción a procesar
     * @return resultado del procesamiento
     * @throws BankingCoreException si ocurre un error en el procesamiento
     */
    PaymentProcessResult processPayment(Transaction transaction) throws BankingCoreException;

    /**
     * Compensa una transacción previamente procesada.
     * @param transactionId identificador de la transacción a compensar
     * @param reason razón de la compensación
     * @return resultado de la compensación
     * @throws BankingCoreException si ocurre un error al compensar
     */
    CompensationResult compensatePayment(String transactionId, String reason) throws BankingCoreException;

    /**
     * Verifica si una cuenta está activa y tiene fondos suficientes.
     * @param accountId identificador de la cuenta
     * @param amount monto a verificar
     * @return resultado de la verificación
     * @throws BankingCoreException si ocurre un error al verificar
     */
    AccountVerificationResult verifyAccount(String accountId, BigDecimal amount) throws BankingCoreException;

    /**
     * Consulta el saldo disponible de una cuenta.
     * @param accountId identificador de la cuenta
     * @return saldo disponible
     * @throws BankingCoreException si ocurre un error al consultar
     */
    BigDecimal getAccountBalance(String accountId) throws BankingCoreException;

    /**
     * Consulta el historial de transacciones de una cuenta.
     * @param accountId identificador de la cuenta
     * @param fromDate fecha inicial de la consulta
     * @param toDate fecha final de la consulta
     * @return lista de transacciones
     * @throws BankingCoreException si ocurre un error al consultar
     */
    List<Transaction> getAccountTransactions(String accountId, LocalDateTime fromDate, LocalDateTime toDate) 
            throws BankingCoreException;

    /**
     * Resultado del procesamiento de un pago.
     */
    record PaymentProcessResult(
        String transactionId,
        String authorizationCode,
        TransactionStatus status,
        LocalDateTime processedAt,
        String message,
        Optional<String> referenceNumber
    ) {
        public boolean isSuccessful() {
            return status == TransactionStatus.APPROVED;
        }

        public boolean requiresReview() {
            return status == TransactionStatus.PENDING;
        }
    }

    /**
     * Resultado de una compensación.
     */
    record CompensationResult(
        String originalTransactionId,
        String compensationId,
        boolean success,
        LocalDateTime processedAt,
        String message
    ) {
        public static CompensationResult successful(String transactionId, String compensationId) {
            return new CompensationResult(
                transactionId,
                compensationId,
                true,
                LocalDateTime.now(),
                "Compensación exitosa"
            );
        }

        public static CompensationResult failed(String transactionId, String message) {
            return new CompensationResult(
                transactionId,
                null,
                false,
                LocalDateTime.now(),
                message
            );
        }
    }

    /**
     * Resultado de la verificación de cuenta.
     */
    record AccountVerificationResult(
        String accountId,
        boolean isActive,
        boolean hasSufficientFunds,
        BigDecimal availableBalance,
        String status,
        Optional<String> holdReason
    ) {
        public boolean canProcessPayment() {
            return isActive && hasSufficientFunds;
        }
    }

    /**
     * Excepción específica para errores del core bancario.
     */
    class BankingCoreException extends RuntimeException {
        private final String errorCode;
        private final String transactionId;
        private final String accountId;

        public BankingCoreException(String message) {
            super(message);
            this.errorCode = "BCR000";
            this.transactionId = null;
            this.accountId = null;
        }

        public BankingCoreException(String message, Throwable cause) {
            super(message, cause);
            this.errorCode = "BCR000";
            this.transactionId = null;
            this.accountId = null;
        }

        public BankingCoreException(String message, String errorCode, String transactionId, String accountId) {
            super(message);
            this.errorCode = errorCode;
            this.transactionId = transactionId;
            this.accountId = accountId;
        }

        public BankingCoreException(String message, Throwable cause, String errorCode, String transactionId, String accountId) {
            super(message, cause);
            this.errorCode = errorCode;
            this.transactionId = transactionId;
            this.accountId = accountId;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public Optional<String> getTransactionId() {
            return Optional.ofNullable(transactionId);
        }

        public Optional<String> getAccountId() {
            return Optional.ofNullable(accountId);
        }
    }
}