# AGENTS.md

Instrucciones para el agente de IA que abra este repositorio (Claude Code, Cursor, Codex, Copilot, Gemini). Se cargan solas: no hay que pegar nada en ningun chat.

## Que es este repositorio

Es el codigo base de un reto de aprendizaje de Pragma: **Manejo de Errores y Reintentos en Sistema de Pagos**.

| | |
|---|---|
| Tema | E2E prod technologiesWork 202610131810 |
| Nivel | senior-l2 |
| Chapter | Backend |
| Especialidad | Java |
| Stack | Java / Spring Boot 3.5 |
| Patron arquitectonico | hexagonal/clean con manejo de errores centralizado y reintentos reactivos |
| Tiempo estimado | 8 horas |

## Receta del stack

Esqueleto obligatorio:

- `pom.xml en la raiz`
- `clase con @SpringBootApplication`
- `application.yml en src/main/resources`
- `capa de dominio con entidades y puertos`
- `capa de aplicacion con casos de uso`
- `capa de infraestructura con adaptadores y @RestController`

Trampas conocidas:

- TODA `<version>` del pom va con tres segmentos: la del parent (ej. `3.5.6`) y la de cada dependencia que la lleve (ej. Resilience4j `2.2.0`). `3.4` y `2.0` no existen como artefacto y el build muere resolviendo dependencias.
- Las dependencias que el parent POM gestiona van SIN `<version>`: `spring-boot-starter-web`, `-data-jpa`, `-validation`, `-test`, etc.
- Resilience4j publica un artefacto por linea de Spring Boot. Con Spring Boot 3 va `resilience4j-spring-boot3` con version de tres segmentos (ej. `2.2.0`, no `2.0`). `resilience4j-spring-boot2` es de Spring Boot 2 y rompe el arranque.
- Si usas anotaciones de validacion (`@NotNull`, `@Size`, `@Positive`) declara `spring-boot-starter-validation`: el starter web no las trae.
- El `spring-boot-maven-plugin` tiene que estar en `<build><plugins>` o no se empaqueta ejecutable.
- Spring Boot 3 usa `jakarta.*`, nunca `javax.*`.
- Cada archivo empieza con su `package` y con un `import` por cada clase del proyecto que viva en otro paquete. Usar `PaymentService` desde `infrastructure` sin `import com.x.application.PaymentService` no compila.

Dependencias:

- org.springframework.boot:spring-boot-starter-web n/a
- org.springframework.boot:spring-boot-starter-data-jpa n/a
- org.springframework.boot:spring-boot-starter-validation n/a
- org.springframework.boot:spring-boot-starter-actuator n/a
- io.github.resilience4j:resilience4j-spring-boot3 2.2.0
- org.projectlombok:lombok 1.18.30
- org.postgresql:postgresql n/a
- org.springframework.boot:spring-boot-starter-test n/a
- org.mockito:mockito-core 5.12.0
- org.springframework.boot:spring-boot-configuration-processor n/a

## Tu tarea

Dejar este proyecto en estado **verificable**: que el comando de verificacion corra sin errores. Escribi los archivos en disco, en este repositorio. No generes ZIPs ni archivos adjuntos.

En orden:

1. Corre `mvn clean compile` y mira que falla.
2. Completa lo que falte de la lista de abajo: manifiesto de dependencias, punto de entrada, capa de interfaz y las capas del patron declarado.
3. Arregla SOLO los errores que impiden compilar o arrancar.
4. Volve a correr `mvn clean compile` hasta que pase.
5. Pará ahí.

## Regla dura: las fases son trabajo del humano

**PROHIBIDO implementar los entregables de las fases.** El valor del reto esta en que la persona los resuelva. Tu trabajo es que tenga un proyecto que arranca; el hueco pedagogico se queda como esta.

No resuelvas nada de esto:

- **Fase 1 — Implementación de Manejo de Errores Básico**: Mecanismo de registro de errores funcional.
- **Fase 2 — Implementación de Reintentos**: Mecanismo de reintentos funcional.
- **Fase 3 — Optimización y Escalabilidad**: Mecanismo de reintentos optimizado y escalable.

Distincion operativa:

- **Arreglar** (si): import faltante, tipo que no existe, dependencia sin declarar, error de sintaxis, archivo referenciado que no existe.
- **No tocar** (no): logica de negocio incompleta, validaciones ausentes, secretos hardcodeados, APIs deprecadas que funcionan, concurrencia insegura, patrones mejorables. Eso es lo que la persona tiene que encontrar.

## Superficie de practica (NO completes)

Estos archivos SON el ejercicio de la persona. No los implementes; deja stubs. No toques la logica que el reto pide completar.

- [ ] `src/main/java/com/pragma/payments/infrastructure/config/Resilience4jConfig.java` — El topic pide resiliencia: este archivo es el ejercicio.

## Lo que falta y tenes que completar

### 1. Referencias colgando (109)

Salieron de un analisis estatico del codigo que SI esta en el repo. Cada una rompe la compilacion:

- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/CreditOriginatorAdapter.java` — `com.pragma.payments.infrastructure.exception.CreditOriginatorException`
      El import com.pragma.payments.infrastructure.exception.CreditOriginatorException usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- [ ] `src/main/java/com/pragma/payments/application/usecase/ProcessPaymentUseCase.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/CreditOriginatorAdapter.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/PaymentGatewayAdapter.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/application/usecase/ProcessPaymentUseCase.java` — `Transaction.getId`
      Se invoca `getId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/application/usecase/ProcessPaymentUseCase.java` — `Transaction.getAmount`
      Se invoca `getAmount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/application/usecase/ProcessPaymentUseCase.java` — `Transaction.getMerchantId`
      Se invoca `getMerchantId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/application/usecase/ProcessPaymentUseCase.java` — `Transaction.getCustomerId`
      Se invoca `getCustomerId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/application/usecase/ProcessPaymentUseCase.java` — `Transaction.getFailureReason`
      Se invoca `getFailureReason` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/application/usecase/ProcessPaymentUseCase.java` — `Transaction.setStatus`
      Se invoca `setStatus` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/CreditOriginatorAdapter.java` — `Transaction.getId`
      Se invoca `getId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/CreditOriginatorAdapter.java` — `Transaction.setId`
      Se invoca `setId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/CreditOriginatorAdapter.java` — `Transaction.setCreatedAt`
      Se invoca `setCreatedAt` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/CreditOriginatorAdapter.java` — `Transaction.setUpdatedAt`
      Se invoca `setUpdatedAt` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/CreditOriginatorAdapter.java` — `Transaction.setStatus`
      Se invoca `setStatus` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/CreditOriginatorAdapter.java` — `Transaction.setProcessed`
      Se invoca `setProcessed` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/CreditOriginatorAdapter.java` — `Transaction.setRetryCount`
      Se invoca `setRetryCount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/CreditOriginatorAdapter.java` — `Transaction.setAmount`
      Se invoca `setAmount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/CreditOriginatorAdapter.java` — `Transaction.setCurrency`
      Se invoca `setCurrency` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/CreditOriginatorAdapter.java` — `Transaction.setMerchantId`
      Se invoca `setMerchantId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/CreditOriginatorAdapter.java` — `Transaction.setCustomerId`
      Se invoca `setCustomerId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/CreditOriginatorAdapter.java` — `Transaction.setPaymentMethod`
      Se invoca `setPaymentMethod` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/CreditOriginatorAdapter.java` — `Transaction.getAmount`
      Se invoca `getAmount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/CreditOriginatorAdapter.java` — `Transaction.getMerchantId`
      Se invoca `getMerchantId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/CreditOriginatorAdapter.java` — `Transaction.getCustomerId`
      Se invoca `getCustomerId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/AntiFraudAdapter.java` — `Transaction.getId`
      Se invoca `getId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/AntiFraudAdapter.java` — `Transaction.getAmount`
      Se invoca `getAmount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/AntiFraudAdapter.java` — `Transaction.getMerchantId`
      Se invoca `getMerchantId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/AntiFraudAdapter.java` — `Transaction.getCustomerId`
      Se invoca `getCustomerId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/RiskBureauAdapter.java` — `Transaction.getId`
      Se invoca `getId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/RiskBureauAdapter.java` — `RiskScore.score`
      Se invoca `score` sobre `RiskScore`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/RiskBureauAdapter.java` — `RiskScore.riskLevel`
      Se invoca `riskLevel` sobre `RiskScore`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/RiskBureauAdapter.java` — `Transaction.getMerchantId`
      Se invoca `getMerchantId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/RiskBureauAdapter.java` — `Transaction.getAmount`
      Se invoca `getAmount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/RiskBureauAdapter.java` — `Transaction.getPaymentMethod`
      Se invoca `getPaymentMethod` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/RiskBureauAdapter.java` — `Transaction.getCustomerId`
      Se invoca `getCustomerId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/BankingCoreAdapter.java` — `Transaction.getId`
      Se invoca `getId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/BankingCoreAdapter.java` — `PaymentResult.authorizationCode`
      Se invoca `authorizationCode` sobre `PaymentResult`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/BankingCoreAdapter.java` — `Transaction.getAmount`
      Se invoca `getAmount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/BankingCoreAdapter.java` — `Transaction.getMerchantId`
      Se invoca `getMerchantId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/BankingCoreAdapter.java` — `Transaction.getCustomerId`
      Se invoca `getCustomerId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/BankingCoreAdapter.java` — `Transaction.getCurrency`
      Se invoca `getCurrency` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/PaymentGatewayAdapter.java` — `Transaction.getId`
      Se invoca `getId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/PaymentGatewayAdapter.java` — `Transaction.getAmount`
      Se invoca `getAmount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/PaymentGatewayAdapter.java` — `Transaction.getCurrency`
      Se invoca `getCurrency` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/PaymentGatewayAdapter.java` — `Transaction.getMerchantId`
      Se invoca `getMerchantId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/PaymentGatewayAdapter.java` — `RefundResult.isSuccessful`
      Se invoca `isSuccessful` sobre `RefundResult`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/PaymentGatewayAdapter.java` — `RefundResult.getErrorMessage`
      Se invoca `getErrorMessage` sobre `RefundResult`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/PaymentGatewayAdapter.java` — `Transaction.getCustomerId`
      Se invoca `getCustomerId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapter/PaymentGatewayAdapter.java` — `Transaction.getPaymentMethod`
      Se invoca `getPaymentMethod` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/exception/GlobalExceptionHandler.java` — `PaymentGatewayException.getMessage`
      Se invoca `getMessage` sobre `PaymentGatewayException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/exception/GlobalExceptionHandler.java` — `PaymentGatewayException.getTransactionId`
      Se invoca `getTransactionId` sobre `PaymentGatewayException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/exception/GlobalExceptionHandler.java` — `PaymentGatewayException.getErrorCode`
      Se invoca `getErrorCode` sobre `PaymentGatewayException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/exception/GlobalExceptionHandler.java` — `PaymentGatewayException.getServiceName`
      Se invoca `getServiceName` sobre `PaymentGatewayException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/exception/GlobalExceptionHandler.java` — `PaymentGatewayException.getGatewayName`
      Se invoca `getGatewayName` sobre `PaymentGatewayException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/exception/GlobalExceptionHandler.java` — `PaymentGatewayException.getBindingResult`
      Se invoca `getBindingResult` sobre `PaymentGatewayException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/exception/GlobalExceptionHandler.java` — `PaymentGatewayException.getName`
      Se invoca `getName` sobre `PaymentGatewayException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/exception/GlobalExceptionHandler.java` — `PaymentGatewayException.getValue`
      Se invoca `getValue` sobre `PaymentGatewayException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/exception/GlobalExceptionHandler.java` — `PaymentGatewayException.getRequiredType`
      Se invoca `getRequiredType` sobre `PaymentGatewayException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `PaymentRequest.amount`
      Se invoca `amount` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `PaymentRequest.currency`
      Se invoca `currency` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `PaymentRequest.merchantId`
      Se invoca `merchantId` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `PaymentRequest.customerId`
      Se invoca `customerId` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `PaymentRequest.paymentMethod`
      Se invoca `paymentMethod` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `Transaction.getId`
      Se invoca `getId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `Transaction.getStatus`
      Se invoca `getStatus` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `AntiFraudException.getMessage`
      Se invoca `getMessage` sobre `AntiFraudException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `AntiFraudException.getAccountId`
      Se invoca `getAccountId` sobre `AntiFraudException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `AntiFraudException.getHttpStatusCode`
      Se invoca `getHttpStatusCode` sobre `AntiFraudException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `AntiFraudException.getGatewayCode`
      Se invoca `getGatewayCode` sobre `AntiFraudException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `ProcessPaymentUseCase.getTransaction`
      Se invoca `getTransaction` sobre `ProcessPaymentUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `Transaction.isPresent`
      Se invoca `isPresent` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `Transaction.get`
      Se invoca `get` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `Transaction.getAmount`
      Se invoca `getAmount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `Transaction.getCurrency`
      Se invoca `getCurrency` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `Transaction.getCreatedAt`
      Se invoca `getCreatedAt` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `Transaction.getUpdatedAt`
      Se invoca `getUpdatedAt` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `Transaction.getFailureReason`
      Se invoca `getFailureReason` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `Transaction.getRetryCount`
      Se invoca `getRetryCount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/application/usecase/ProcessPaymentUseCaseTest.java` — `AntiFraudPort.evaluateRisk`
      Se invoca `evaluateRisk` sobre `AntiFraudPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/application/usecase/ProcessPaymentUseCaseTest.java` — `RiskBureauPort.checkRisk`
      Se invoca `checkRisk` sobre `RiskBureauPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/application/usecase/ProcessPaymentUseCaseTest.java` — `BankingCorePort.reserveFunds`
      Se invoca `reserveFunds` sobre `BankingCorePort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/application/usecase/ProcessPaymentUseCaseTest.java` — `Transaction.getStatus`
      Se invoca `getStatus` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/application/usecase/ProcessPaymentUseCaseTest.java` — `Transaction.getId`
      Se invoca `getId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/application/usecase/ProcessPaymentUseCaseTest.java` — `Transaction.getRetryCount`
      Se invoca `getRetryCount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/application/usecase/ProcessPaymentUseCaseTest.java` — `Transaction.setId`
      Se invoca `setId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/application/usecase/ProcessPaymentUseCaseTest.java` — `Transaction.setAmount`
      Se invoca `setAmount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/application/usecase/ProcessPaymentUseCaseTest.java` — `Transaction.setCurrency`
      Se invoca `setCurrency` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/application/usecase/ProcessPaymentUseCaseTest.java` — `Transaction.setMerchantId`
      Se invoca `setMerchantId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/application/usecase/ProcessPaymentUseCaseTest.java` — `Transaction.setCustomerId`
      Se invoca `setCustomerId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/application/usecase/ProcessPaymentUseCaseTest.java` — `Transaction.setPaymentMethod`
      Se invoca `setPaymentMethod` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/application/usecase/ProcessPaymentUseCaseTest.java` — `Transaction.setStatus`
      Se invoca `setStatus` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/application/usecase/ProcessPaymentUseCaseTest.java` — `Transaction.setCreatedAt`
      Se invoca `setCreatedAt` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/application/usecase/ProcessPaymentUseCaseTest.java` — `Transaction.setUpdatedAt`
      Se invoca `setUpdatedAt` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/application/usecase/ProcessPaymentUseCaseTest.java` — `Transaction.setRetryCount`
      Se invoca `setRetryCount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/application/usecase/ProcessPaymentUseCaseTest.java` — `Transaction.setProcessed`
      Se invoca `setProcessed` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/infrastructure/adapter/AntiFraudAdapterTest.java` — `AntiFraudPort.evaluateRisk`
      Se invoca `evaluateRisk` sobre `AntiFraudPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/infrastructure/adapter/AntiFraudAdapterTest.java` — `AntiFraudAdapter.evaluateRisk`
      Se invoca `evaluateRisk` sobre `AntiFraudAdapter`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/infrastructure/adapter/AntiFraudAdapterTest.java` — `Transaction.setId`
      Se invoca `setId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/infrastructure/adapter/AntiFraudAdapterTest.java` — `Transaction.setAmount`
      Se invoca `setAmount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/infrastructure/adapter/AntiFraudAdapterTest.java` — `Transaction.setCurrency`
      Se invoca `setCurrency` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/infrastructure/adapter/AntiFraudAdapterTest.java` — `Transaction.setMerchantId`
      Se invoca `setMerchantId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/infrastructure/adapter/AntiFraudAdapterTest.java` — `Transaction.setCustomerId`
      Se invoca `setCustomerId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/infrastructure/adapter/AntiFraudAdapterTest.java` — `Transaction.setPaymentMethod`
      Se invoca `setPaymentMethod` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/infrastructure/adapter/AntiFraudAdapterTest.java` — `Transaction.setStatus`
      Se invoca `setStatus` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/infrastructure/adapter/AntiFraudAdapterTest.java` — `Transaction.setCreatedAt`
      Se invoca `setCreatedAt` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/infrastructure/adapter/AntiFraudAdapterTest.java` — `Transaction.setUpdatedAt`
      Se invoca `setUpdatedAt` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/infrastructure/adapter/AntiFraudAdapterTest.java` — `Transaction.setRetryCount`
      Se invoca `setRetryCount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/infrastructure/adapter/AntiFraudAdapterTest.java` — `Transaction.setProcessed`
      Se invoca `setProcessed` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

### Presentes (24)

- `pom.xml`
- `src/main/java/com/pragma/payments/Application.java`
- `src/main/resources/application.yml`
- `src/main/java/com/pragma/payments/domain/model/Transaction.java`
- `src/main/java/com/pragma/payments/domain/model/TransactionStatus.java`
- `src/main/java/com/pragma/payments/domain/port/CreditOriginatorPort.java`
- `src/main/java/com/pragma/payments/domain/port/AntiFraudPort.java`
- `src/main/java/com/pragma/payments/domain/port/RiskBureauPort.java`
- `src/main/java/com/pragma/payments/domain/port/BankingCorePort.java`
- `src/main/java/com/pragma/payments/domain/port/PaymentGatewayPort.java`
- `src/main/java/com/pragma/payments/application/usecase/ProcessPaymentUseCase.java`
- `src/main/java/com/pragma/payments/infrastructure/adapter/CreditOriginatorAdapter.java`
- `src/main/java/com/pragma/payments/infrastructure/adapter/AntiFraudAdapter.java`
- `src/main/java/com/pragma/payments/infrastructure/adapter/RiskBureauAdapter.java`
- `src/main/java/com/pragma/payments/infrastructure/adapter/BankingCoreAdapter.java`
- `src/main/java/com/pragma/payments/infrastructure/adapter/PaymentGatewayAdapter.java`
- `src/main/java/com/pragma/payments/infrastructure/config/Resilience4jConfig.java`
- `src/main/java/com/pragma/payments/infrastructure/exception/AntiFraudException.java`
- `src/main/java/com/pragma/payments/infrastructure/exception/BankingCoreException.java`
- `src/main/java/com/pragma/payments/infrastructure/exception/PaymentGatewayException.java`
- `src/main/java/com/pragma/payments/infrastructure/exception/GlobalExceptionHandler.java`
- `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java`
- `src/test/java/com/pragma/payments/application/usecase/ProcessPaymentUseCaseTest.java`
- `src/test/java/com/pragma/payments/infrastructure/adapter/AntiFraudAdapterTest.java`

### Capas del patron declarado

Cada una tiene que existir como directorio real con al menos un archivo. Codigo plano en la raiz no satisface el patron.

- `src/main/java/com/pragma/payments`
- `src/main/java/com/pragma/payments/domain`
- `src/main/java/com/pragma/payments/domain/model`
- `src/main/java/com/pragma/payments/domain/port`
- `src/main/java/com/pragma/payments/application`
- `src/main/java/com/pragma/payments/application/usecase`
- `src/main/java/com/pragma/payments/infrastructure`
- `src/main/java/com/pragma/payments/infrastructure/adapter`
- `src/main/java/com/pragma/payments/infrastructure/config`
- `src/main/java/com/pragma/payments/infrastructure/exception`
- `src/main/resources`
- `src/test/java/com/pragma/payments`

## Verificacion

```bash
mvn clean compile
```

El comando tiene que pasar SIN implementar los archivos de la superficie de practica: solo andamiaje.

Ese comando pasando es la definicion de "terminado" para vos.

## Convenciones que tenes que respetar

- Un solo ecosistema: no declares librerias de otro lenguaje ni mezcles gestores de paquetes.
- Toda libreria que uses tiene que estar declarada en el manifiesto de dependencias.
- Todo import declarado tiene que usarse; todo tipo usado tiene que existir o venir de una dependencia declarada.
- El patron es **hexagonal/clean con manejo de errores centralizado y reintentos reactivos**: los contratos (interfaces, puertos) los define la capa interna y los implementa la externa, nunca al revés.
- Los archivos que crees llevan implementacion real, no stubs: sin `TODO`, sin cuerpos vacios, sin `// getters y setters`.

## Contexto del candidato

Sirve para calibrar el nivel del codigo, no para resolver las fases.

- Perfil: Chapter Backend, Especialidad Desarrollo de software, Tecnología Java, Senior L2
- Brecha que el reto ataca: Comprueba manejo de errores y reintentos.

---

*Generado por Challenge Generator — Pragma. `README.md` tiene el enunciado completo del reto para la persona. `PROMPT_MEJORA.md` es la variante para pegar en un chat, si se prefiere ese flujo.*
