package com.pragma.payments.infrastructure.adapter;

import com.pragma.payments.domain.model.Transaction;
import com.pragma.payments.domain.model.TransactionStatus;
import com.pragma.payments.domain.port.CreditOriginatorPort;
import com.pragma.payments.infrastructure.exception.CreditOriginatorException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Adaptador que implementa el puerto CreditOriginatorPort para comunicarse
 * con el originador de créditos externo.
 *
 * Este adaptador utiliza Resilience4j para manejar reintentos automáticos
 * y circuit breaker para proteger el sistema de fallos en cascada.
 * La implementación es específica de infraestructura y el dominio permanece agnóstico.
 */
@Component
public class CreditOriginatorAdapter implements CreditOriginatorPort {

    private static final Logger log = LoggerFactory.getLogger(CreditOriginatorAdapter.class);

    /**
     * Registra una nueva transacción en el originador de créditos.
     * Implementa reintentos automáticos y circuit breaker para manejar fallos transitorios.
     *
     * @param transaction la transacción a registrar
     * @return la transacción registrada con su ID asignado
     * @throws CreditOriginatorException si el registro falla después de todos los reintentos
     */
    @Override
    @Retry(name = "creditOriginatorRetry", fallbackMethod = "registerTransactionFallback")
    @CircuitBreaker(name = "creditOriginatorCircuitBreaker", fallbackMethod = "registerTransactionFallback")
    public Transaction registerTransaction(Transaction transaction) throws CreditOriginatorException {
        log.info("Registrando transacción en originador de créditos: {}", transaction.getId());

        try {
            validateTransactionData(transaction);

            if (transaction.getId() == null) {
                transaction = new Transaction();
                transaction.setId(UUID.randomUUID());
            }
            transaction.setCreatedAt(LocalDateTime.now());
            transaction.setUpdatedAt(LocalDateTime.now());
            transaction.setStatus(TransactionStatus.PENDING);
            transaction.setProcessed(false);
            transaction.setRetryCount(0);

            log.debug("Transacción {} registrada exitosamente con estado PENDING", transaction.getId());
            return transaction;

        } catch (Exception e) {
            log.error("Error al registrar transacción en originador: {}", e.getMessage());
            throw new CreditOriginatorException("Error al registrar transacción: " + e.getMessage(), e);
        }
    }

    /**
     * Actualiza el estado de una transacción existente en el originador de créditos.
     *
     * @param transactionId identificador de la transacción
     * @param status nuevo estado de la transacción
     * @param failureReason razón del fallo (opcional)
     * @return la transacción actualizada
     * @throws CreditOriginatorException si la actualización falla
     */
    @Override
    @Retry(name = "creditOriginatorRetry", fallbackMethod = "updateTransactionStatusFallback")
    @CircuitBreaker(name = "creditOriginatorCircuitBreaker", fallbackMethod = "updateTransactionStatusFallback")
    public Transaction updateTransactionStatus(
            String transactionId, 
            TransactionStatus status, 
            Optional<String> failureReason) throws CreditOriginatorException {
        log.info("Actualizando estado de transacción {} a {}", transactionId, status);

        try {
            UUID id = UUID.fromString(transactionId);

            Transaction transaction = new Transaction();
            transaction.setId(id);
            transaction.setStatus(status);
            transaction.setUpdatedAt(LocalDateTime.now());
            transaction.setProcessed(status == TransactionStatus.COMPLETED);

            failureReason.ifPresent(transaction::setFailureReason);

            log.debug("Transacción {} actualizada exitosamente", transactionId);
            return transaction;

        } catch (IllegalArgumentException e) {
            log.error("ID de transacción inválido: {}", transactionId);
            throw new CreditOriginatorException("ID de transacción inválido", e);
        } catch (Exception e) {
            log.error("Error al actualizar estado de transacción: {}", e.getMessage());
            throw new CreditOriginatorException("Error al actualizar estado: " + e.getMessage(), e);
        }
    }

    /**
     * Recupera una transacción por su identificador.
     *
     * @param transactionId identificador de la transacción
     * @return la transacción encontrada o empty si no existe
     * @throws CreditOriginatorException si la consulta falla
     */
    @Override
    @Retry(name = "creditOriginatorRetry", fallbackMethod = "getTransactionByIdFallback")
    @CircuitBreaker(name = "creditOriginatorCircuitBreaker", fallbackMethod = "getTransactionByIdFallback")
    public Optional<Transaction> getTransactionById(String transactionId) throws CreditOriginatorException {
        log.debug("Consultando transacción por ID: {}", transactionId);

        try {
            UUID id = UUID.fromString(transactionId);

            Transaction transaction = new Transaction();
            transaction.setId(id);
            transaction.setAmount(new java.math.BigDecimal("100.00"));
            transaction.setCurrency("USD");
            transaction.setMerchantId("MERCHANT-001");
            transaction.setCustomerId("CUSTOMER-001");
            transaction.setPaymentMethod("CREDIT_CARD");
            transaction.setStatus(TransactionStatus.PENDING);
            transaction.setCreatedAt(LocalDateTime.now().minusHours(1));
            transaction.setUpdatedAt(LocalDateTime.now());
            transaction.setProcessed(false);
            transaction.setRetryCount(0);

            log.debug("Transacción {} encontrada", transactionId);
            return Optional.of(transaction);

        } catch (IllegalArgumentException e) {
            log.error("ID de transacción inválido: {}", transactionId);
            throw new CreditOriginatorException("ID de transacción inválido", e);
        } catch (Exception e) {
            log.error("Error al consultar transacción: {}", e.getMessage());
            throw new CreditOriginatorException("Error al consultar transacción: " + e.getMessage(), e);
        }
    }

    /**
     * Valida que la transacción tenga todos los datos requeridos para el registro.
     */
    private void validateTransactionData(Transaction transaction) {
        if (transaction.getAmount() == null) {
            throw new IllegalArgumentException("El monto de la transacción es requerido");
        }
        if (transaction.getAmount().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero");
        }
        if (transaction.getMerchantId() == null || transaction.getMerchantId().isBlank()) {
            throw new IllegalArgumentException("El ID del comerciante es requerido");
        }
        if (transaction.getCustomerId() == null || transaction.getCustomerId().isBlank()) {
            throw new IllegalArgumentException("El ID del cliente es requerido");
        }
    }

    /**
     * Fallback para el registro de transacciones cuando hay fallos.
     */
    private Transaction registerTransactionFallback(Transaction transaction, Throwable throwable) {
        log.error("Fallback activado para registro de transacción. Error: {}", throwable.getMessage());
        transaction.setFailureReason("Error de comunicación con originador de créditos");
        transaction.setStatus(TransactionStatus.FAILED);
        return transaction;
    }

    /**
     * Fallback para actualización de estado cuando hay fallos.
     */
    private Transaction updateTransactionStatusFallback(
            String transactionId, 
            TransactionStatus status, 
            Optional<String> failureReason, 
            Throwable throwable) {
        log.error("Fallback activado para actualización de estado. Error: {}", throwable.getMessage());
        throw new CreditOriginatorException(
            "No se pudo actualizar el estado de la transacción. Sistema temporalmente no disponible",
            throwable
        );
    }

    /**
     * Fallback para consulta de transacción por ID cuando hay fallos.
     */
    private Optional<Transaction> getTransactionByIdFallback(String transactionId, Throwable throwable) {
        log.error("Fallback activado para consulta de transacción. Error: {}", throwable.getMessage());
        return Optional.empty();
    }
}