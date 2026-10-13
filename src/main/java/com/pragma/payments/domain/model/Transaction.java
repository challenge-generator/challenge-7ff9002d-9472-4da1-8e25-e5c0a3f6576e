package com.pragma.payments.domain.model;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidad de dominio que representa una transacción de pago.
 * Contiene toda la información necesaria para procesar el pago y manejar su estado.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class Transaction {
    @NotNull
    private UUID id;

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotBlank
    @Size(max = 3)
    private String currency;

    @NotBlank
    @Size(max = 255)
    private String merchantId;

    @NotBlank
    @Size(max = 255)
    private String customerId;

    @NotBlank
    @Size(max = 255)
    private String paymentMethod;

    @NotNull
    private TransactionStatus status;

    @NotNull
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Size(max = 255)
    private String failureReason;

    @PositiveOrZero
    private int retryCount;

    @Builder.Default
    private boolean processed = false;

    /**
     * Marca la transacción como procesada y actualiza la fecha de última modificación.
     */
    public void markAsProcessed() {
        this.processed = true;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Incrementa el contador de reintentos y actualiza la fecha de última modificación.
     */
    public void incrementRetryCount() {
        this.retryCount++;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Establece el motivo de fallo y actualiza la fecha de última modificación.
     * @param reason Motivo del fallo
     */
    public void setFailureReason(String reason) {
        this.failureReason = reason;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Verifica si la transacción puede ser reintentada basado en el límite de reintentos.
     * @param maxRetries Límite máximo de reintentos
     * @return true si puede ser reintentada, false en caso contrario
     */
    public boolean canRetry(int maxRetries) {
        return this.retryCount < maxRetries && !this.processed;
    }
}