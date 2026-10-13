package com.pragma.payments.infrastructure.controller;

import com.pragma.payments.application.usecase.ProcessPaymentUseCase;
import com.pragma.payments.domain.model.Transaction;
import com.pragma.payments.domain.model.TransactionStatus;
import com.pragma.payments.infrastructure.exception.AntiFraudException;
import com.pragma.payments.infrastructure.exception.BankingCoreException;
import com.pragma.payments.infrastructure.exception.PaymentGatewayException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final ProcessPaymentUseCase processPaymentUseCase;

    @PostMapping
    public ResponseEntity<PaymentResponse> processPayment(@Valid @RequestBody PaymentRequest request) {
        log.info("Recibida solicitud de pago para amount={}, currency={}, merchantId={}",
                request.amount(), request.currency(), request.merchantId());

        try {
            Transaction transaction = processPaymentUseCase.execute(
                    request.amount(),
                    request.currency(),
                    request.merchantId(),
                    request.customerId(),
                    request.paymentMethod()
            );

            PaymentResponse response = PaymentResponse.fromTransaction(transaction);
            log.info("Pago procesado exitosamente: transactionId={}, status={}",
                    transaction.getId(), transaction.getStatus());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (AntiFraudException e) {
            log.error("Error antifraude para merchantId={}: {}", request.merchantId(), e.getMessage());
            return ResponseEntity.status(mapExceptionToStatus(e)).body(
                    PaymentResponse.error(
                            "ANTI_FRAUD_REJECTED",
                            e.getMessage(),
                            e.isRetryable(),
                            e.getRiskLevel()
                    )
            );
        } catch (BankingCoreException e) {
            log.error("Error del core bancario para accountId={}: {}", e.getAccountId(), e.getMessage());
            return ResponseEntity.status(e.getHttpStatusCode()).body(
                    PaymentResponse.error(
                            "BANKING_ERROR",
                            e.getMessage(),
                            e.isRetryable(),
                            null
                    )
            );
        } catch (PaymentGatewayException e) {
            log.error("Error de gateway de pago: {}", e.getMessage());
            return ResponseEntity.status(e.isRetryable() ? HttpStatus.SERVICE_UNAVAILABLE : HttpStatus.BAD_REQUEST).body(
                    PaymentResponse.error(
                            e.getGatewayCode() != null ? e.getGatewayCode() : "GATEWAY_ERROR",
                            e.getMessage(),
                            e.isRetryable(),
                            null
                    )
            );
        } catch (Exception e) {
            log.error("Error inesperado procesando pago: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    PaymentResponse.error("INTERNAL_ERROR", "Error interno del servidor", true, null)
            );
        }
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<PaymentResponse> getTransaction(@PathVariable String transactionId) {
        log.info("Consultando transacción: transactionId={}", transactionId);

        try {
            var transaction = processPaymentUseCase.getTransaction(transactionId);
            if (transaction.isPresent()) {
                return ResponseEntity.ok(PaymentResponse.fromTransaction(transaction.get()));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Error consultando transacción {}: {}", transactionId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private HttpStatus mapExceptionToStatus(AntiFraudException e) {
        if (e.isHighRisk()) {
            return HttpStatus.FORBIDDEN;
        } else if (e.isMediumRisk()) {
            return HttpStatus.PRECONDITION_FAILED;
        } else if (e.isRetryable()) {
            return HttpStatus.SERVICE_UNAVAILABLE;
        }
        return HttpStatus.BAD_REQUEST;
    }

    public record PaymentRequest(
            @NotNull @Positive BigDecimal amount,
            @NotBlank String currency,
            @NotBlank String merchantId,
            @NotBlank String customerId,
            @NotBlank String paymentMethod
    ) {}

    public record PaymentResponse(
            UUID transactionId,
            BigDecimal amount,
            String currency,
            String status,
            Instant createdAt,
            Instant updatedAt,
            String failureReason,
            int retryCount,
            String errorCode,
            boolean retryable,
            String riskLevel
    ) {
        public static PaymentResponse fromTransaction(Transaction tx) {
            return new PaymentResponse(
                    tx.getId(),
                    tx.getAmount(),
                    tx.getCurrency(),
                    tx.getStatus().name(),
                    tx.getCreatedAt(),
                    tx.getUpdatedAt(),
                    tx.getFailureReason(),
                    tx.getRetryCount(),
                    null,
                    false,
                    null
            );
        }

        public static PaymentResponse error(String errorCode, String message, boolean retryable, String riskLevel) {
            return new PaymentResponse(
                    null,
                    null,
                    null,
                    TransactionStatus.FAILED.name(),
                    null,
                    null,
                    message,
                    0,
                    errorCode,
                    retryable,
                    riskLevel
            );
        }
    }
}