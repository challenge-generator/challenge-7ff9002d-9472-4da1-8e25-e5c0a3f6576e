package com.pragma.payments.infrastructure.exception;


import com.pragma.payments.domain.port.BankingCoreException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String PROBLEM_BASE_URI = "https://api.pragma.com/payments/problems";

    @ExceptionHandler(BankingCoreException.class)
    public ProblemDetail handleBankingCoreException(BankingCoreException ex, WebRequest request) {
        log.error("Error en el core bancario: {} - TransactionId: {} - Code: {}", 
            ex.getMessage(), ex.getTransactionId(), ex.getErrorCode(), ex);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
            ex.isRetryable() ? HttpStatus.SERVICE_UNAVAILABLE : HttpStatus.BAD_REQUEST,
            ex.getMessage()
        );

        problemDetail.setTitle("Error en el Core Bancario");
        problemDetail.setType(URI.create(PROBLEM_BASE_URI + "/banking-core-error"));
        problemDetail.setProperty("errorCode", ex.getErrorCode());
        problemDetail.setProperty("serviceName", ex.getServiceName());
        problemDetail.setProperty("timestamp", Instant.now().toString());

        if (ex.getTransactionId() != null) {
            problemDetail.setProperty("transactionId", ex.getTransactionId());
        }

        if (ex.isRetryable()) {
            problemDetail.setProperty("retryable", true);
            problemDetail.setProperty("retryAfter", "30 seconds");
        }

        return problemDetail;
    }

    @ExceptionHandler(PaymentGatewayException.class)
    public ProblemDetail handlePaymentGatewayException(PaymentGatewayException ex, WebRequest request) {
        log.error("Error en la gateway de pagos: {} - TransactionId: {} - Code: {}", 
            ex.getMessage(), ex.getTransactionId(), ex.getErrorCode(), ex);

        HttpStatus status = determineHttpStatus(ex);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, ex.getMessage());

        problemDetail.setTitle("Error en la Gateway de Pagos");
        problemDetail.setType(URI.create(PROBLEM_BASE_URI + "/payment-gateway-error"));
        problemDetail.setProperty("errorCode", ex.getErrorCode());
        problemDetail.setProperty("gatewayName", ex.getGatewayName());
        problemDetail.setProperty("timestamp", Instant.now().toString());

        if (ex.getTransactionId() != null) {
            problemDetail.setProperty("transactionId", ex.getTransactionId());
        }

        problemDetail.setProperty("retryable", ex.isRetryable());

        if (ex.isRetryable()) {
            problemDetail.setProperty("retryAfter", "60 seconds");
        }

        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        log.error("Error de validación en la solicitud: {}", ex.getMessage());

        Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
            .collect(Collectors.toMap(
                error -> error.getField(),
                error -> error.getDefaultMessage() != null ? error.getDefaultMessage() : "Valor inválido",
                (existing, replacement) -> existing
            ));

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST,
            "Error de validación en los datos de la solicitud"
        );

        problemDetail.setTitle("Error de Validación");
        problemDetail.setType(URI.create(PROBLEM_BASE_URI + "/validation-error"));
        problemDetail.setProperty("errors", fieldErrors);
        problemDetail.setProperty("timestamp", Instant.now().toString());

        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail handleTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request) {
        log.error("Error de tipo en argumento: {} - Valor: {}", ex.getName(), ex.getValue());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST,
            String.format("El parámetro '%s' tiene un formato inválido", ex.getName())
        );

        problemDetail.setTitle("Error de Tipo de Argumento");
        problemDetail.setType(URI.create(PROBLEM_BASE_URI + "/type-mismatch"));
        problemDetail.setProperty("parameter", ex.getName());
        problemDetail.setProperty("expectedType", ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "desconocido");
        problemDetail.setProperty("providedValue", ex.getValue());
        problemDetail.setProperty("timestamp", Instant.now().toString());

        return problemDetail;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest request) {
        log.error("Error al leer el cuerpo de la solicitud: {}", ex.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST,
            "El cuerpo de la solicitud no se puede leer o está en formato inválido"
        );

        problemDetail.setTitle("Error de Lectura de Solicitud");
        problemDetail.setType(URI.create(PROBLEM_BASE_URI + "/message-not-readable"));
        problemDetail.setProperty("timestamp", Instant.now().toString());

        return problemDetail;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        log.error("Argumento ilegal: {}", ex.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST,
            ex.getMessage()
        );

        problemDetail.setTitle("Argumento Inválido");
        problemDetail.setType(URI.create(PROBLEM_BASE_URI + "/illegal-argument"));
        problemDetail.setProperty("timestamp", Instant.now().toString());

        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception ex, WebRequest request) {
        log.error("Error interno no esperado: {} - Path: {}", ex.getMessage(), request.getDescription(false), ex);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Ha ocurrido un error interno en el sistema. Por favor, contacte al administrador."
        );

        problemDetail.setTitle("Error Interno del Servidor");
        problemDetail.setType(URI.create(PROBLEM_BASE_URI + "/internal-error"));
        problemDetail.setProperty("timestamp", Instant.now().toString());
        problemDetail.setProperty("supportContact", "soporte@pragma.com");

        return problemDetail;
    }

    private HttpStatus determineHttpStatus(PaymentGatewayException ex) {
        String errorCode = ex.getErrorCode();

        if (ex.isRetryable()) {
            return HttpStatus.SERVICE_UNAVAILABLE;
        }

        return switch (errorCode) {
            case "GATEWAY_DECLINED", "GATEWAY_INVALID_CARD", "GATEWAY_EXPIRED_CARD" -> HttpStatus.PAYMENT_REQUIRED;
            case "GATEWAY_FRAUD_DETECTED" -> HttpStatus.FORBIDDEN;
            default -> HttpStatus.BAD_REQUEST;
        };
    }
}