package com.pragma.payments.domain.port;

import com.pragma.payments.domain.model.Transaction;
import com.pragma.payments.domain.model.TransactionStatus;
import com.pragma.payments.infrastructure.exception.PaymentGatewayException;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Puerto que define el contrato para interactuar con la gateway de pagos externa.
 * Esta interfaz pertenece al dominio y es implementada por la capa de infraestructura.
 * El dominio define QUÉ se necesita hacer; la infraestructura define CÓMO se hace.
 */
public interface PaymentGatewayPort {

    /**
     * Procesa un pago a través de la gateway externa.
     *
     * @param transaction la transacción a procesar con todos los datos necesarios
     * @return la transacción actualizada con el estadoresultado del procesamiento
     * @throws PaymentGatewayException si ocurre un error al comunicarse con la gateway
     */
    Transaction processPayment(Transaction transaction) throws PaymentGatewayException;

    /**
     * Consulta el estado de una transacción en la gateway de pagos.
     *
     * @param transactionId identificador único de la transacción en el sistema
     * @return el estado actual de la transacción o empty si no se encuentra
     * @throws PaymentGatewayException si ocurre un error al consultar la gateway
     */
    Optional<TransactionStatus> getPaymentStatus(String transactionId) throws PaymentGatewayException;

    /**
     * Realiza el reembolso de una transacción previamente procesada.
     *
     * @param transactionId identificador de la transacción a reembolsar
     * @param amount monto a reembolsar (puede ser parcial)
     * @return la transacción con estado actualizado a refunded
     * @throws PaymentGatewayException si el reembolso falla
     */
    Transaction refundPayment(String transactionId, BigDecimal amount) throws PaymentGatewayException;

    /**
     * Valida que la transacción pueda ser procesada por la gateway.
     * Realiza validaciones básicas como formato de tarjeta, fondos disponibles, etc.
     *
     * @param transaction la transacción a validar
     * @return true si la transacción es válida para procesamiento
     * @throws PaymentGatewayException si la validación falla
     */
    boolean validateTransaction(Transaction transaction) throws PaymentGatewayException;
}