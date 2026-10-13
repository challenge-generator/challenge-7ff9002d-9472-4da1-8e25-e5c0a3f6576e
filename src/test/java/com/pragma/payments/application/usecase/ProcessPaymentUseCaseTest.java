package com.pragma.payments.application.usecase;

import com.pragma.payments.domain.model.Transaction;
import com.pragma.payments.domain.model.TransactionStatus;
import com.pragma.payments.domain.port.AntiFraudPort;
import com.pragma.payments.domain.port.BankingCorePort;
import com.pragma.payments.domain.port.CreditOriginatorPort;
import com.pragma.payments.domain.port.PaymentGatewayPort;
import com.pragma.payments.domain.port.RiskBureauPort;
import com.pragma.payments.infrastructure.exception.AntiFraudException;
import com.pragma.payments.infrastructure.exception.BankingCoreException;
import com.pragma.payments.infrastructure.exception.PaymentGatewayException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProcessPaymentUseCase")
class ProcessPaymentUseCaseTest {

    @Mock
    private CreditOriginatorPort creditOriginatorPort;

    @Mock
    private AntiFraudPort antiFraudPort;

    @Mock
    private RiskBureauPort riskBureauPort;

    @Mock
    private BankingCorePort bankingCorePort;

    @Mock
    private PaymentGatewayPort paymentGatewayPort;

    private ProcessPaymentUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ProcessPaymentUseCase(
                creditOriginatorPort,
                antiFraudPort,
                riskBureauPort,
                bankingCorePort,
                paymentGatewayPort
        );
    }

    @Nested
    @DisplayName("Escenario: Pago exitoso")
    class PagoExitoso {

        @Test
        @DisplayName("Debería procesar pago exitosamente cuando todos los servicios responden correctamente")
        void shouldProcessPaymentSuccessfully() {
            BigDecimal amount = new BigDecimal("1000.00");
            String currency = "USD";
            String merchantId = "merchant-123";
            String customerId = "customer-456";
            String paymentMethod = "CREDIT_CARD";

            Transaction savedTransaction = createTransaction(TransactionStatus.PENDING);
            when(creditOriginatorPort.registerTransaction(any(Transaction.class))).thenReturn(savedTransaction);

            when(antiFraudPort.evaluateRisk(any(Transaction.class))).thenReturn(false);

            when(riskBureauPort.checkRisk(customerId)).thenReturn(Optional.empty());

            when(bankingCorePort.reserveFunds(customerId, amount, currency)).thenReturn(true);

            Transaction processedTransaction = createTransaction(TransactionStatus.APPROVED);
            processedTransaction.markAsProcessed();
            when(paymentGatewayPort.processPayment(any(Transaction.class))).thenReturn(processedTransaction);

            when(creditOriginatorPort.updateTransactionStatus(anyString(), eq(TransactionStatus.APPROVED), any()))
                    .thenReturn(processedTransaction);

            Transaction result = useCase.execute(amount, currency, merchantId, customerId, paymentMethod);

            assertNotNull(result);
            assertEquals(TransactionStatus.APPROVED, result.getStatus());
            verify(creditOriginatorPort).registerTransaction(any(Transaction.class));
            verify(antiFraudPort).evaluateRisk(any(Transaction.class));
            verify(riskBureauPort).checkRisk(customerId);
            verify(bankingCorePort).reserveFunds(customerId, amount, currency);
            verify(paymentGatewayPort).processPayment(any(Transaction.class));
        }
    }

    @Nested
    @DisplayName("Escenario: Error de AntiFraude")
    class ErrorAntiFraud {

        @Test
        @DisplayName("Debería lanzar AntiFraudException cuando el motor antifraude detecta alto riesgo")
        void shouldThrowAntiFraudExceptionWhenHighRisk() {
            BigDecimal amount = new BigDecimal("5000.00");
            Transaction transaction = createTransaction(TransactionStatus.PENDING);

            when(creditOriginatorPort.registerTransaction(any(Transaction.class))).thenReturn(transaction);

            doThrow(new AntiFraudException("Transacción de alto riesgo", transaction.getId().toString(), "HIGH", false))
                    .when(antiFraudPort).evaluateRisk(any(Transaction.class));

            assertThrows(AntiFraudException.class, () ->
                    useCase.execute(amount, "USD", "merchant-1", "customer-1", "CREDIT_CARD")
            );

            verify(creditOriginatorPort).updateTransactionStatus(
                    eq(transaction.getId().toString()),
                    eq(TransactionStatus.REJECTED),
                    any()
            );
        }

        @Test
        @DisplayName("Debería marcar transacción como rechazada cuando antifraude falla")
        void shouldMarkTransactionAsRejectedWhenAntiFraudFails() {
            BigDecimal amount = new BigDecimal("100.00");
            Transaction transaction = createTransaction(TransactionStatus.PENDING);

            when(creditOriginatorPort.registerTransaction(any(Transaction.class))).thenReturn(transaction);

            doThrow(new AntiFraudException("Error de comunicación con motor antifraude",
                    transaction.getId().toString(), null, true))
                    .when(antiFraudPort).evaluateRisk(any(Transaction.class));

            assertThrows(AntiFraudException.class, () ->
                    useCase.execute(amount, "USD", "merchant-1", "customer-1", "DEBIT_CARD")
            );

            verify(creditOriginatorPort).updateTransactionStatus(
                    eq(transaction.getId().toString()),
                    eq(TransactionStatus.REJECTED),
                    any()
            );
        }
    }

    @Nested
    @DisplayName("Escenario: Error del Core Bancario")
    class ErrorBankingCore {

        @Test
        @DisplayName("Debería lanzar BankingCoreException cuando hay fondos insuficientes")
        void shouldThrowBankingCoreExceptionWhenInsufficientFunds() {
            BigDecimal amount = new BigDecimal("10000.00");
            Transaction transaction = createTransaction(TransactionStatus.PENDING);

            when(creditOriginatorPort.registerTransaction(any(Transaction.class))).thenReturn(transaction);
            when(antiFraudPort.evaluateRisk(any(Transaction.class))).thenReturn(false);
            when(riskBureauPort.checkRisk(anyString())).thenReturn(Optional.empty());

            doThrow(new BankingCoreException("Fondos insuficientes", "customer-1", "RESERVE", false))
                    .when(bankingCorePort).reserveFunds(anyString(), any(), anyString());

            assertThrows(BankingCoreException.class, () ->
                    useCase.execute(amount, "USD", "merchant-1", "customer-1", "CREDIT_CARD")
            );

            verify(creditOriginatorPort).updateTransactionStatus(
                    eq(transaction.getId().toString()),
                    eq(TransactionStatus.FAILED),
                    any()
            );
        }

        @Test
        @DisplayName("Debería reintentar cuando el error del core bancario es temporario")
        void shouldRetryWhenBankingCoreErrorIsTemporary() {
            BigDecimal amount = new BigDecimal("500.00");
            Transaction transaction = createTransaction(TransactionStatus.PENDING);

            when(creditOriginatorPort.registerTransaction(any(Transaction.class))).thenReturn(transaction);
            when(antiFraudPort.evaluateRisk(any(Transaction.class))).thenReturn(false);
            when(riskBureauPort.checkRisk(anyString())).thenReturn(Optional.empty());

            doThrow(new BankingCoreException("Servicio temporalmente no disponible", "customer-1", "RESERVE", true))
                    .when(bankingCorePort).reserveFunds(anyString(), any(), anyString());

            assertThrows(BankingCoreException.class, () ->
                    useCase.execute(amount, "USD", "merchant-1", "customer-1", "CREDIT_CARD")
            );

            assertTrue(transaction.canRetry(3));
        }
    }

    @Nested
    @DisplayName("Escenario: Error de Gateway de Pago")
    class ErrorPaymentGateway {

        @Test
        @DisplayName("Debería lanzar PaymentGatewayException cuando el gateway rechaza el pago")
        void shouldThrowPaymentGatewayExceptionWhenRejected() {
            BigDecimal amount = new BigDecimal("250.00");
            Transaction transaction = createTransaction(TransactionStatus.PENDING);

            when(creditOriginatorPort.registerTransaction(any(Transaction.class))).thenReturn(transaction);
            when(antiFraudPort.evaluateRisk(any(Transaction.class))).thenReturn(false);
            when(riskBureauPort.checkRisk(anyString())).thenReturn(Optional.empty());
            when(bankingCorePort.reserveFunds(anyString(), any(), anyString())).thenReturn(true);

            doThrow(new PaymentGatewayException("Tarjeta rechazada", "DECLINE_05", "Card declined", false))
                    .when(paymentGatewayPort).processPayment(any(Transaction.class));

            assertThrows(PaymentGatewayException.class, () ->
                    useCase.execute(amount, "USD", "merchant-1", "customer-1", "CREDIT_CARD")
            );

            verify(bankingCorePort).releaseFunds(anyString(), any(), anyString());
        }

        @Test
        @DisplayName("Debería reintentar cuando el gateway tiene timeout")
        void shouldRetryWhenGatewayTimeout() {
            BigDecimal amount = new BigDecimal("100.00");
            Transaction transaction = createTransaction(TransactionStatus.PENDING);

            when(creditOriginatorPort.registerTransaction(any(Transaction.class))).thenReturn(transaction);
            when(antiFraudPort.evaluateRisk(any(Transaction.class))).thenReturn(false);
            when(riskBureauPort.checkRisk(anyString())).thenReturn(Optional.empty());
            when(bankingCorePort.reserveFunds(anyString(), any(), anyString())).thenReturn(true);

            doThrow(new PaymentGatewayException("Timeout del gateway", "TIMEOUT", "Gateway timeout", true, 5000))
                    .when(paymentGatewayPort).processPayment(any(Transaction.class));

            assertThrows(PaymentGatewayException.class, () ->
                    useCase.execute(amount, "USD", "merchant-1", "customer-1", "CREDIT_CARD")
            );

            verify(bankingCorePort).releaseFunds(anyString(), any(), anyString());
        }
    }

    @Nested
    @DisplayName("Escenario: Reintentos")
    class Reintentos {

        @Test
        @DisplayName("Debería incrementar retryCount en cada intento fallido")
        void shouldIncrementRetryCountOnFailure() {
            BigDecimal amount = new BigDecimal("100.00");
            Transaction transaction = createTransaction(TransactionStatus.PENDING);
            transaction.incrementRetryCount();

            when(creditOriginatorPort.registerTransaction(any(Transaction.class))).thenReturn(transaction);
            when(antiFraudPort.evaluateRisk(any(Transaction.class))).thenReturn(false);
            when(riskBureauPort.checkRisk(anyString())).thenReturn(Optional.empty());
            when(bankingCorePort.reserveFunds(anyString(), any(), anyString())).thenReturn(true);

            doThrow(new PaymentGatewayException("Error temporal", "TMP_ERROR", "Temporary error", true))
                    .when(paymentGatewayPort).processPayment(any(Transaction.class));

            assertThrows(PaymentGatewayException.class, () ->
                    useCase.execute(amount, "USD", "merchant-1", "customer-1", "CREDIT_CARD")
            );

            assertEquals(1, transaction.getRetryCount());
        }

        @Test
        @DisplayName("Debería permitir reintentos hasta el límite configurado")
        void shouldAllowRetriesUpToLimit() {
            Transaction transaction = createTransaction(TransactionStatus.PENDING);
            transaction.setFailureReason("Error anterior");

            int maxRetries = 3;
            assertTrue(transaction.canRetry(maxRetries));

            transaction.incrementRetryCount();
            transaction.incrementRetryCount();
            assertTrue(transaction.canRetry(maxRetries));

            transaction.incrementRetryCount();
            assertFalse(transaction.canRetry(maxRetries));
        }
    }

    private Transaction createTransaction(TransactionStatus status) {
        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setAmount(new BigDecimal("100.00"));
        transaction.setCurrency("USD");
        transaction.setMerchantId("merchant-123");
        transaction.setCustomerId("customer-456");
        transaction.setPaymentMethod("CREDIT_CARD");
        transaction.setStatus(status);
        transaction.setCreatedAt(Instant.now());
        transaction.setUpdatedAt(Instant.now());
        transaction.setRetryCount(0);
        transaction.setProcessed(false);
        return transaction;
    }
}