package com.pragma.payments.infrastructure.adapter;

import com.pragma.payments.domain.model.Transaction;
import com.pragma.payments.domain.model.TransactionStatus;
import com.pragma.payments.domain.port.AntiFraudPort;
import com.pragma.payments.infrastructure.exception.AntiFraudException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AntiFraudAdapter")
class AntiFraudAdapterTest {

    @Mock
    private AntiFraudPort antiFraudPort;

    private CircuitBreakerRegistry circuitBreakerRegistry;
    private AntiFraudAdapter adapter;

    @BeforeEach
    void setUp() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(java.time.Duration.ofSeconds(5))
                .slidingWindowSize(10)
                .minimumNumberOfCalls(5)
                .build();

        circuitBreakerRegistry = CircuitBreakerRegistry.of(config);
        adapter = new AntiFraudAdapter(antiFraudPort, circuitBreakerRegistry);
    }

    @Nested
    @DisplayName("Escenario: Evaluación exitosa")
    class EvaluacionExitosa {

        @Test
        @DisplayName("Debería retornar false cuando la transacción es de bajo riesgo")
        void shouldReturnFalseForLowRiskTransaction() {
            Transaction transaction = createTransaction(new BigDecimal("100.00"));
            when(antiFraudPort.evaluateRisk(transaction)).thenReturn(false);

            boolean result = adapter.evaluateRisk(transaction);

            assertFalse(result);
            verify(antiFraudPort).evaluateRisk(transaction);
        }

        @Test
        @DisplayName("Debería retornar true cuando la transacción es de alto riesgo")
        void shouldReturnTrueForHighRiskTransaction() {
            Transaction transaction = createTransaction(new BigDecimal("10000.00"));
            when(antiFraudPort.evaluateRisk(transaction)).thenReturn(true);

            boolean result = adapter.evaluateRisk(transaction);

            assertTrue(result);
            verify(antiFraudPort).evaluateRisk(transaction);
        }
    }

    @Nested
    @DisplayName("Escenario: Fallo del servicio externo")
    class FalloServicioExterno {

        @Test
        @DisplayName("Debería lanzar AntiFraudException cuando el servicio falla")
        void shouldThrowAntiFraudExceptionWhenServiceFails() {
            Transaction transaction = createTransaction(new BigDecimal("500.00"));
            when(antiFraudPort.evaluateRisk(transaction))
                    .thenThrow(new RuntimeException("Servicio antifraude no disponible"));

            assertThrows(AntiFraudException.class, () -> adapter.evaluateRisk(transaction));
        }

        @Test
        @DisplayName("Debería marcar el circuit breaker como abierto después de múltiples fallos")
        void shouldOpenCircuitBreakerAfterMultipleFailures() {
            Transaction transaction = createTransaction(new BigDecimal("100.00"));

            when(antiFraudPort.evaluateRisk(any(Transaction.class)))
                    .thenThrow(new RuntimeException("Error"));

            for (int i = 0; i < 6; i++) {
                try {
                    adapter.evaluateRisk(transaction);
                } catch (Exception e) {
                    // Se espera excepción
                }
            }

            CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("antiFraud");
            assertEquals(CircuitBreaker.State.OPEN, circuitBreaker.getState());
        }

        @Test
        @DisplayName("Debería permitir llamadas después de que el circuit breaker meio полуоткрывается")
        void shouldAllowCallsWhenCircuitBreakerHalfOpen() throws InterruptedException {
            Transaction transaction = createTransaction(new BigDecimal("100.00"));

            when(antiFraudPort.evaluateRisk(any(Transaction.class)))
                    .thenThrow(new RuntimeException("Error"));

            for (int i = 0; i < 6; i++) {
                try {
                    adapter.evaluateRisk(transaction);
                } catch (Exception e) {
                    // Se espera excepción
                }
            }

            Thread.sleep(6000);

            when(antiFraudPort.evaluateRisk(transaction)).thenReturn(false);

            boolean result = adapter.evaluateRisk(transaction);
            assertFalse(result);
        }
    }

    @Nested
    @DisplayName("Escenario: Validación de parámetros")
    class ValidacionParametros {

        @Test
        @DisplayName("Debería lanzar excepción para monto cero")
        void shouldThrowExceptionForZeroAmount() {
            Transaction transaction = createTransaction(BigDecimal.ZERO);

            assertThrows(AntiFraudException.class, () -> adapter.evaluateRisk(transaction));
        }

        @Test
        @DisplayName("Debería lanzar excepción para monto negativo")
        void shouldThrowExceptionForNegativeAmount() {
            Transaction transaction = createTransaction(new BigDecimal("-100.00"));

            assertThrows(AntiFraudException.class, () -> adapter.evaluateRisk(transaction));
        }

        @Test
        @DisplayName("Debería lanzar excepción para transacción nula")
        void shouldThrowExceptionForNullTransaction() {
            assertThrows(AntiFraudException.class, () -> adapter.evaluateRisk(null));
        }
    }

    @Nested
    @DisplayName("Escenario: Comportamiento del Circuit Breaker")
    class ComportamientoCircuitBreaker {

        @Test
        @DisplayName("Debería registrar el circuit breaker con nombre correcto")
        void shouldRegisterCircuitBreakerWithCorrectName() {
            CircuitBreaker cb = circuitBreakerRegistry.circuitBreaker("antiFraud");
            assertNotNull(cb);
        }

        @Test
        @DisplayName("Debería ejecutar función decorada con circuit breaker")
        void shouldExecuteDecoratedFunction() {
            Transaction transaction = createTransaction(new BigDecimal("200.00"));
            when(antiFraudPort.evaluateRisk(transaction)).thenReturn(false);

            Supplier<Boolean> decoratedSupplier = CircuitBreaker.decorateSupplier(
                    circuitBreakerRegistry.circuitBreaker("antiFraud"),
                    () -> antiFraudPort.evaluateRisk(transaction)
            );

            Boolean result = decoratedSupplier.get();
            assertFalse(result);
        }

        @Test
        @DisplayName("Debería manejar excepción del circuit breaker gracefully")
        void shouldHandleCircuitBreakerExceptionGracefully() {
            Transaction transaction = createTransaction(new BigDecimal("300.00"));

            when(antiFraudPort.evaluateRisk(any(Transaction.class)))
                    .thenThrow(new RuntimeException("Error"));

            for (int i = 0; i < 6; i++) {
                try {
                    adapter.evaluateRisk(transaction);
                } catch (AntiFraudException e) {
                    assertTrue(e.isRetryable());
                }
            }
        }
    }

    private Transaction createTransaction(BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setAmount(amount);
        transaction.setCurrency("USD");
        transaction.setMerchantId("merchant-test");
        transaction.setCustomerId("customer-test");
        transaction.setPaymentMethod("CREDIT_CARD");
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setCreatedAt(Instant.now());
        transaction.setUpdatedAt(Instant.now());
        transaction.setRetryCount(0);
        transaction.setProcessed(false);
        return transaction;
    }
}