package com.pragma.payments.domain.port;

import com.pragma.payments.domain.model.Transaction;
import com.pragma.payments.domain.model.TransactionStatus;

import java.util.Optional;

/**
 * Puerto que define la interfaz para interactuar con el originador de créditos.
 * Este puerto es implementado por la capa de infraestructura.
 */
public interface CreditOriginatorPort {
    /**
     * Registra una nueva transacción en el originador de créditos.
     * @param transaction Transacción a registrar
     * @return Transacción con el estado actualizado
     * @throws CreditOriginatorException Si ocurre un error al registrar la transacción
     */
    Transaction registerTransaction(Transaction transaction) throws CreditOriginatorException;

    /**
     * Actualiza el estado de una transacción existente.
     * @param transactionId ID de la transacción
     * @param status Nuevo estado
     * @param failureReason Motivo de fallo (opcional)
     * @return Transacción actualizada
     * @throws CreditOriginatorException Si ocurre un error al actualizar la transacción
     */
    Transaction updateTransactionStatus(String transactionId, TransactionStatus status, Optional<String> failureReason)
            throws CreditOriginatorException;

    /**
     * Obtiene una transacción por su ID.
     * @param transactionId ID de la transacción
     * @return Transacción si existe
     * @throws CreditOriginatorException Si ocurre un error al buscar la transacción
     */
    Optional<Transaction> getTransactionById(String transactionId) throws CreditOriginatorException;

    /**
     * Excepción personalizada para errores del originador de créditos.
     */
    class CreditOriginatorException extends RuntimeException {
        public CreditOriginatorException(String message) {
            super(message);
        }

        public CreditOriginatorException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}