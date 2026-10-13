# Prompt para Mejorar el Codigo Base

Copia y pega el contenido del bloque de abajo en un asistente de IA (Claude, ChatGPT)
para obtener un ZIP con el proyecto completo y arrancable.

Si preferis trabajar en tu editor con un agente local (Claude Code, Cursor, Copilot), usa `AGENTS.md` en vez de este archivo: dice lo mismo pero para que escriba los archivos en disco.

## Las dos reglas que no se negocian

1. **Completa el boilerplate.** Todo lo que el proyecto necesita para compilar y arrancar: manifiesto de dependencias, punto de entrada, configuracion, capa de interfaz, y las capas del patron arquitectonico declarado. Eso es andamiaje y es tu trabajo.
2. **NO resuelvas el reto.** Los entregables de las fases son el trabajo de la persona. El hueco pedagogico se deja como esta: el proyecto arranca, pero lo que el reto pide implementar NO esta implementado.

Dicho de otra forma: si algo impide compilar, arreglalo. Si algo es logica de negocio incompleta, validaciones ausentes, un secreto hardcodeado o un patron mejorable, dejalo exactamente como esta — es lo que la persona tiene que encontrar.

## Superficie de practica — NO resuelvas

Estos archivos SON el ejercicio de la persona. No los implementes; deja stubs.

- `src/main/java/com/pragma/payments/infrastructure/config/Resilience4jConfig.java` — El topic pide resiliencia: este archivo es el ejercicio.

## Lo que le falta a este proyecto

Esto NO lo tenes que adivinar: salio de comparar el proyecto contra la arquitectura declarada del reto y de un analisis estatico del codigo. Completalo TODO.

### Referencias colgando en el codigo que si esta

Cada una rompe la compilacion:

- `src/main/java/com/pragma/payments/infrastructure/adapter/CreditOriginatorAdapter.java` — `com.pragma.payments.infrastructure.exception.CreditOriginatorException`: El import com.pragma.payments.infrastructure.exception.CreditOriginatorException usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- `src/main/java/com/pragma/payments/application/usecase/ProcessPaymentUseCase.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/adapter/CreditOriginatorAdapter.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/adapter/PaymentGatewayAdapter.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/application/usecase/ProcessPaymentUseCase.java` — `Transaction.getId`: Se invoca `getId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/application/usecase/ProcessPaymentUseCase.java` — `Transaction.getAmount`: Se invoca `getAmount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/application/usecase/ProcessPaymentUseCase.java` — `Transaction.getMerchantId`: Se invoca `getMerchantId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/application/usecase/ProcessPaymentUseCase.java` — `Transaction.getCustomerId`: Se invoca `getCustomerId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/application/usecase/ProcessPaymentUseCase.java` — `Transaction.getFailureReason`: Se invoca `getFailureReason` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/application/usecase/ProcessPaymentUseCase.java` — `Transaction.setStatus`: Se invoca `setStatus` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/CreditOriginatorAdapter.java` — `Transaction.getId`: Se invoca `getId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/CreditOriginatorAdapter.java` — `Transaction.setId`: Se invoca `setId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/CreditOriginatorAdapter.java` — `Transaction.setCreatedAt`: Se invoca `setCreatedAt` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/CreditOriginatorAdapter.java` — `Transaction.setUpdatedAt`: Se invoca `setUpdatedAt` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/CreditOriginatorAdapter.java` — `Transaction.setStatus`: Se invoca `setStatus` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/CreditOriginatorAdapter.java` — `Transaction.setProcessed`: Se invoca `setProcessed` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/CreditOriginatorAdapter.java` — `Transaction.setRetryCount`: Se invoca `setRetryCount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/CreditOriginatorAdapter.java` — `Transaction.setAmount`: Se invoca `setAmount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/CreditOriginatorAdapter.java` — `Transaction.setCurrency`: Se invoca `setCurrency` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/CreditOriginatorAdapter.java` — `Transaction.setMerchantId`: Se invoca `setMerchantId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/CreditOriginatorAdapter.java` — `Transaction.setCustomerId`: Se invoca `setCustomerId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/CreditOriginatorAdapter.java` — `Transaction.setPaymentMethod`: Se invoca `setPaymentMethod` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/CreditOriginatorAdapter.java` — `Transaction.getAmount`: Se invoca `getAmount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/CreditOriginatorAdapter.java` — `Transaction.getMerchantId`: Se invoca `getMerchantId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/CreditOriginatorAdapter.java` — `Transaction.getCustomerId`: Se invoca `getCustomerId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/AntiFraudAdapter.java` — `Transaction.getId`: Se invoca `getId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/AntiFraudAdapter.java` — `Transaction.getAmount`: Se invoca `getAmount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/AntiFraudAdapter.java` — `Transaction.getMerchantId`: Se invoca `getMerchantId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/AntiFraudAdapter.java` — `Transaction.getCustomerId`: Se invoca `getCustomerId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/RiskBureauAdapter.java` — `Transaction.getId`: Se invoca `getId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/RiskBureauAdapter.java` — `RiskScore.score`: Se invoca `score` sobre `RiskScore`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/RiskBureauAdapter.java` — `RiskScore.riskLevel`: Se invoca `riskLevel` sobre `RiskScore`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/RiskBureauAdapter.java` — `Transaction.getMerchantId`: Se invoca `getMerchantId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/RiskBureauAdapter.java` — `Transaction.getAmount`: Se invoca `getAmount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/RiskBureauAdapter.java` — `Transaction.getPaymentMethod`: Se invoca `getPaymentMethod` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/RiskBureauAdapter.java` — `Transaction.getCustomerId`: Se invoca `getCustomerId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/BankingCoreAdapter.java` — `Transaction.getId`: Se invoca `getId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/BankingCoreAdapter.java` — `PaymentResult.authorizationCode`: Se invoca `authorizationCode` sobre `PaymentResult`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/BankingCoreAdapter.java` — `Transaction.getAmount`: Se invoca `getAmount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/BankingCoreAdapter.java` — `Transaction.getMerchantId`: Se invoca `getMerchantId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/BankingCoreAdapter.java` — `Transaction.getCustomerId`: Se invoca `getCustomerId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/BankingCoreAdapter.java` — `Transaction.getCurrency`: Se invoca `getCurrency` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/PaymentGatewayAdapter.java` — `Transaction.getId`: Se invoca `getId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/PaymentGatewayAdapter.java` — `Transaction.getAmount`: Se invoca `getAmount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/PaymentGatewayAdapter.java` — `Transaction.getCurrency`: Se invoca `getCurrency` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/PaymentGatewayAdapter.java` — `Transaction.getMerchantId`: Se invoca `getMerchantId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/PaymentGatewayAdapter.java` — `RefundResult.isSuccessful`: Se invoca `isSuccessful` sobre `RefundResult`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/PaymentGatewayAdapter.java` — `RefundResult.getErrorMessage`: Se invoca `getErrorMessage` sobre `RefundResult`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/PaymentGatewayAdapter.java` — `Transaction.getCustomerId`: Se invoca `getCustomerId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/PaymentGatewayAdapter.java` — `Transaction.getPaymentMethod`: Se invoca `getPaymentMethod` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/exception/GlobalExceptionHandler.java` — `PaymentGatewayException.getMessage`: Se invoca `getMessage` sobre `PaymentGatewayException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/exception/GlobalExceptionHandler.java` — `PaymentGatewayException.getTransactionId`: Se invoca `getTransactionId` sobre `PaymentGatewayException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/exception/GlobalExceptionHandler.java` — `PaymentGatewayException.getErrorCode`: Se invoca `getErrorCode` sobre `PaymentGatewayException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/exception/GlobalExceptionHandler.java` — `PaymentGatewayException.getServiceName`: Se invoca `getServiceName` sobre `PaymentGatewayException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/exception/GlobalExceptionHandler.java` — `PaymentGatewayException.getGatewayName`: Se invoca `getGatewayName` sobre `PaymentGatewayException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/exception/GlobalExceptionHandler.java` — `PaymentGatewayException.getBindingResult`: Se invoca `getBindingResult` sobre `PaymentGatewayException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/exception/GlobalExceptionHandler.java` — `PaymentGatewayException.getName`: Se invoca `getName` sobre `PaymentGatewayException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/exception/GlobalExceptionHandler.java` — `PaymentGatewayException.getValue`: Se invoca `getValue` sobre `PaymentGatewayException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/exception/GlobalExceptionHandler.java` — `PaymentGatewayException.getRequiredType`: Se invoca `getRequiredType` sobre `PaymentGatewayException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `PaymentRequest.amount`: Se invoca `amount` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `PaymentRequest.currency`: Se invoca `currency` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `PaymentRequest.merchantId`: Se invoca `merchantId` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `PaymentRequest.customerId`: Se invoca `customerId` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `PaymentRequest.paymentMethod`: Se invoca `paymentMethod` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `Transaction.getId`: Se invoca `getId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `Transaction.getStatus`: Se invoca `getStatus` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `AntiFraudException.getMessage`: Se invoca `getMessage` sobre `AntiFraudException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `AntiFraudException.getAccountId`: Se invoca `getAccountId` sobre `AntiFraudException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `AntiFraudException.getHttpStatusCode`: Se invoca `getHttpStatusCode` sobre `AntiFraudException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `AntiFraudException.getGatewayCode`: Se invoca `getGatewayCode` sobre `AntiFraudException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `ProcessPaymentUseCase.getTransaction`: Se invoca `getTransaction` sobre `ProcessPaymentUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `Transaction.isPresent`: Se invoca `isPresent` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `Transaction.get`: Se invoca `get` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `Transaction.getAmount`: Se invoca `getAmount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `Transaction.getCurrency`: Se invoca `getCurrency` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `Transaction.getCreatedAt`: Se invoca `getCreatedAt` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `Transaction.getUpdatedAt`: Se invoca `getUpdatedAt` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `Transaction.getFailureReason`: Se invoca `getFailureReason` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java` — `Transaction.getRetryCount`: Se invoca `getRetryCount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/application/usecase/ProcessPaymentUseCaseTest.java` — `AntiFraudPort.evaluateRisk`: Se invoca `evaluateRisk` sobre `AntiFraudPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/application/usecase/ProcessPaymentUseCaseTest.java` — `RiskBureauPort.checkRisk`: Se invoca `checkRisk` sobre `RiskBureauPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/application/usecase/ProcessPaymentUseCaseTest.java` — `BankingCorePort.reserveFunds`: Se invoca `reserveFunds` sobre `BankingCorePort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/application/usecase/ProcessPaymentUseCaseTest.java` — `Transaction.getStatus`: Se invoca `getStatus` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/application/usecase/ProcessPaymentUseCaseTest.java` — `Transaction.getId`: Se invoca `getId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/application/usecase/ProcessPaymentUseCaseTest.java` — `Transaction.getRetryCount`: Se invoca `getRetryCount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/application/usecase/ProcessPaymentUseCaseTest.java` — `Transaction.setId`: Se invoca `setId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/application/usecase/ProcessPaymentUseCaseTest.java` — `Transaction.setAmount`: Se invoca `setAmount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/application/usecase/ProcessPaymentUseCaseTest.java` — `Transaction.setCurrency`: Se invoca `setCurrency` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/application/usecase/ProcessPaymentUseCaseTest.java` — `Transaction.setMerchantId`: Se invoca `setMerchantId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/application/usecase/ProcessPaymentUseCaseTest.java` — `Transaction.setCustomerId`: Se invoca `setCustomerId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/application/usecase/ProcessPaymentUseCaseTest.java` — `Transaction.setPaymentMethod`: Se invoca `setPaymentMethod` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/application/usecase/ProcessPaymentUseCaseTest.java` — `Transaction.setStatus`: Se invoca `setStatus` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/application/usecase/ProcessPaymentUseCaseTest.java` — `Transaction.setCreatedAt`: Se invoca `setCreatedAt` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/application/usecase/ProcessPaymentUseCaseTest.java` — `Transaction.setUpdatedAt`: Se invoca `setUpdatedAt` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/application/usecase/ProcessPaymentUseCaseTest.java` — `Transaction.setRetryCount`: Se invoca `setRetryCount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/application/usecase/ProcessPaymentUseCaseTest.java` — `Transaction.setProcessed`: Se invoca `setProcessed` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/infrastructure/adapter/AntiFraudAdapterTest.java` — `AntiFraudPort.evaluateRisk`: Se invoca `evaluateRisk` sobre `AntiFraudPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/infrastructure/adapter/AntiFraudAdapterTest.java` — `AntiFraudAdapter.evaluateRisk`: Se invoca `evaluateRisk` sobre `AntiFraudAdapter`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/infrastructure/adapter/AntiFraudAdapterTest.java` — `Transaction.setId`: Se invoca `setId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/infrastructure/adapter/AntiFraudAdapterTest.java` — `Transaction.setAmount`: Se invoca `setAmount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/infrastructure/adapter/AntiFraudAdapterTest.java` — `Transaction.setCurrency`: Se invoca `setCurrency` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/infrastructure/adapter/AntiFraudAdapterTest.java` — `Transaction.setMerchantId`: Se invoca `setMerchantId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/infrastructure/adapter/AntiFraudAdapterTest.java` — `Transaction.setCustomerId`: Se invoca `setCustomerId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/infrastructure/adapter/AntiFraudAdapterTest.java` — `Transaction.setPaymentMethod`: Se invoca `setPaymentMethod` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/infrastructure/adapter/AntiFraudAdapterTest.java` — `Transaction.setStatus`: Se invoca `setStatus` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/infrastructure/adapter/AntiFraudAdapterTest.java` — `Transaction.setCreatedAt`: Se invoca `setCreatedAt` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/infrastructure/adapter/AntiFraudAdapterTest.java` — `Transaction.setUpdatedAt`: Se invoca `setUpdatedAt` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/infrastructure/adapter/AntiFraudAdapterTest.java` — `Transaction.setRetryCount`: Se invoca `setRetryCount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/infrastructure/adapter/AntiFraudAdapterTest.java` — `Transaction.setProcessed`: Se invoca `setProcessed` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

## Como saber que terminaste

```bash
mvn clean compile
```

Ese comando corriendo sin errores es la definicion de "listo".

---

```
## Briefing del reto (autoridad)
Este bloque manda sobre los archivos adjuntos. El stack y el rol salen de AQUÍ, no de un topic genérico ni de markdown placeholder.

### Perfil
Chapter Backend, Especialidad Desarrollo de software, Tecnología Java, Senior L2

### Brecha de conocimiento
Comprueba manejo de errores y reintentos.

### Reto
- Tema: E2E prod technologiesWork 202610131810
- Seniority: senior-l2
- Tipo: practical
- Título: Manejo de Errores y Reintentos en Sistema de Pagos
- Tiempo estimado: 8 horas

### Fases (trabajo del HUMANO — PROHIBIDO completarlas)
No implementes estos entregables. Dejalos como hueco pedagógico. El asistente solo materializa el proyecto arrancable para que el participante pueda trabajar.
- Fase 1: Implementación de Manejo de Errores Básico — objetivo: Implementar un mecanismo básico de manejo de errores que permita identificar y registrar fallos en las transacciones. — entregable (NO resolver): Mecanismo de registro de errores funcional.
- Fase 2: Implementación de Reintentos — objetivo: Implementar un mecanismo de reintentos que permita recuperar transacciones fallidas. — entregable (NO resolver): Mecanismo de reintentos funcional.
- Fase 3: Optimización y Escalabilidad — objetivo: Optimizar el mecanismo de reintentos para asegurar la escalabilidad y eficiencia del sistema. — entregable (NO resolver): Mecanismo de reintentos optimizado y escalable.

Eres un asistente experto en análisis, corrección y generación de archivos de cualquier tipo:
código fuente, documentación, hojas de cálculo, documentos Word, configuraciones, entre otros.
Voy a enviarte una cadena de texto que contiene uno o más archivos. Cada archivo está delimitado por un marcador con el siguiente formato:
// === ARCHIVO: ruta/del/archivo.extension ===
o también puede aparecer como:
## === ARCHIVO: ruta/del/archivo.extension ===
Lo que sigue al marcador puede ser:

El contenido real del archivo (código, texto, YAML, etc.)
Una descripción en lenguaje natural de lo que debe contener el archivo


TU TAREA
PASO 0 — ¿Esto es un proyecto o una carcasa?
Antes de extraer archivos, leé el Briefing (si está) y diagnosticá el adjunto.

Es CARCASA si ocurre CUALQUIERA de estas:
- No hay manifiesto de dependencias del stack del briefing (manifest.json de VTEX IO / package.json / pom.xml / build.gradle / requirements.txt / go.mod / *.tf / *.csproj, según corresponda)
- Hay un "binario" que en realidad es un comentario ("no puede ser mostrado como texto plano", placeholder .fig/.docx vacío)
- Los markdowns ya completan entregables de fases posteriores ("se implementó fade-in", lista de áreas ya resuelta)

Si es CARCASA:
- MATERIALIZÁ un proyecto que arranca en el stack del briefing (VTEX IO Store Framework, Angular, Terraform, pytest, Nest, etc.). Incluí manifiesto, punto de entrada y capa de interfaz reales.
- NO copies los markdowns de "solución" como si fueran el producto. Son ruido de generación.
- NO resuelvas las fases del briefing (están marcadas PROHIBIDO). Dejá el hueco pedagógico: el flujo existe, las microinteracciones/calidad/infra que el reto pide NO están hechas.
- Después seguí al PASO 5 (ZIP).

Si es un proyecto REAL (manifiesto + código que compila o arranca):
- Seguí PASO 1 en adelante. 🔴 compilación sí. 🟡 pedagógico no.

PASO 1 — Detección y extracción
Identifica todos los archivos presentes en la cadena. Para cada archivo extrae:

Su ruta completa (ej: src/main/java/com/pragma/Service.java)
Su contenido o descripción

PASO 2 — Clasificación por tipo
Clasifica cada archivo en una de estas categorías:
A) Código fuente (Java, Python, TypeScript, JavaScript, Kotlin, etc.)
B) Configuración / documentación (YAML, properties, Markdown, JSON, txt, etc.)
C) Excel (.xlsx, .xls, .csv)
D) Word (.docx, .doc)
E) Otro tipo de archivo binario o especial
PASO 3 — Clasificación de errores en código fuente

Objetivo prioritario: que el proyecto compile. No corrijas flujo de negocio ni lógica funcional.

Antes de modificar cualquier archivo de código fuente, clasifica cada problema encontrado en una de estas dos categorías:
🔴 ERROR DE COMPILACIÓN — corregir siempre
Son errores que impiden que el proyecto arranque, sin valor pedagógico:

Import faltante o incorrecto
Clase, método o variable referenciada que no existe en ningún archivo del proyecto
Error de sintaxis
Anotación con atributos inválidos
Dependencia ausente en pom.xml, package.json, etc.
Archivo referenciado que no existe y debe ser creado con implementación mínima

→ CORREGIR estos errores.
🟡 PROBLEMA FUNCIONAL O DE CALIDAD — preservar siempre
Son problemas que no impiden compilar. Pueden ser intencionales para el aprendizaje:

Clave secreta hardcodeada ("secret", "password123")
API deprecada que funciona pero tiene reemplazo moderno
Lógica de negocio incorrecta o incompleta
Código redundante o de baja legibilidad
Falta de validaciones en flujo de negocio
Patrones de diseño incorrectos pero funcionales
Concurrencia no segura
Configuración funcional pero no óptima

→ PRESERVAR tal cual. No corregir, no mejorar, no comentar.
PASO 4 — Procesamiento según tipo de archivo
Tipo A — Código fuente
Aplica únicamente las correcciones clasificadas como 🔴 ERROR DE COMPILACIÓN.
No alteres ningún elemento clasificado como 🟡 PROBLEMA FUNCIONAL O DE CALIDAD.
Si falta un archivo referenciado, créalo con la implementación mínima necesaria para compilar.
Tipo B — Configuración / documentación
Extrae el contenido tal cual, sin modificaciones salvo errores evidentes de sintaxis
(ej: YAML mal indentado).
Tipo C — Excel (.xlsx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un archivo Excel funcional con:

Fila de encabezados en negrita con color de fondo distintivo
Columnas con ancho ajustado al contenido
Tipos de dato correctos por columna
Validaciones si la descripción lo indica
Hojas nombradas descriptivamente si hay más de una
Filas de ejemplo si no hay datos reales

Tipo D — Word (.docx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un documento Word funcional con:

Estilos de título (Título 1, Título 2) para jerarquía de secciones
Fuente legible (Calibri o equivalente), tamaño 11-12pt para cuerpo
Márgenes estándar
Tabla de contenido si tiene múltiples secciones
Tablas con encabezados en negrita si aplica

Tipo E — Otro
Genera el archivo con el contenido o estructura más apropiada según la descripción.
PASO 5 — Exportación en ZIP
Empaqueta todos los archivos en un único archivo ZIP descargable respetando exactamente
la estructura de rutas indicada por los marcadores.
El ZIP debe incluir:

Archivos de código con únicamente los errores de compilación corregidos
Archivos de configuración y documentación sin cambios
Archivos nuevos creados para resolver dependencias de compilación faltantes
Archivos Excel y Word generados desde descripción

IMPORTANTE: El ZIP debe estar listo para descargar al finalizar. No preguntes si el usuario
quiere generarlo. Simplemente genera el archivo y proporciona el enlace de descarga; No debes desplegar en el chat el resumen de lo que arreglaste al Zip, solo entregalo.

REGLAS IMPORTANTES

No omitas ningún archivo aunque no tenga errores ni modificaciones
Respeta los nombres y rutas exactas indicadas por los marcadores
Si un archivo no tiene marcador claro, infiere el nombre desde su contenido
Si la cadena contiene solo documentación, placeholders o binarios fake, NO la reproduzcas:
aplicá PASO 0 (materializar el proyecto del briefing). Reproducir la carcasa es un fallo.
No agregues texto después del enlace de descarga del ZIP
No preguntes si el usuario quiere el ZIP: simplemente generalo siempre
Si detectas que falta un archivo de configuración necesario para compilar
(pom.xml, package.json, requirements.txt, build.gradle, etc.), créalo e inclúyelo
inferiendo su contenido desde los imports y frameworks detectados en el código
Nunca corrijas problemas 🟡 aunque parezcan obvios o fáciles de mejorar.
El participante que recibirá este proyecto los debe encontrar y resolver él mismo.


INPUT
Aquí está la cadena con los archivos:

// === ARCHIVO: pom.xml ===
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.6</version>
        <relativePath/>
    </parent>

    <groupId>com.pragma</groupId>
    <artifactId>payments</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>payments</name>
    <description>Sistema de pagos con manejo robusto de errores y reintentos</description>

    <properties>
        <java.version>21</java.version>
        <resilience4j.version>2.2.0</resilience4j.version>
    </properties>

    <dependencies>
        <!-- Spring Boot Starters -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <!-- Resilience4j -->
        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-spring-boot3</artifactId>
            <version>${resilience4j.version}</version>
        </dependency>

        <!-- Lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.30</version>
            <scope>provided</scope>
        </dependency>

        <!-- PostgreSQL -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>

        <!-- Spring Boot Test -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-core</artifactId>
            <version>5.12.0</version>
            <scope>test</scope>
        </dependency>

        <!-- Configuration Processor -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>

// === ARCHIVO: src/main/java/com/pragma/payments/Application.java ===
package com.pragma.payments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.context.annotation.Import;
import com.pragma.payments.infrastructure.config.Resilience4jConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.time.Duration;

@Slf4j
@SpringBootApplication
@EnableRetry
@EnableAsync
@ConfigurationPropertiesScan
@Import(Resilience4jConfig.class)
public class Application {

    private final RetryRegistry retryRegistry;
    private final CircuitBreakerRegistry circuitBreakerRegistry;

    public Application(RetryRegistry retryRegistry, CircuitBreakerRegistry circuitBreakerRegistry) {
        this.retryRegistry = retryRegistry;
        this.circuitBreakerRegistry = circuitBreakerRegistry;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        log.info("Application started successfully");
        log.info("Retry configurations: {}", retryRegistry.getAllRetries());
        log.info("CircuitBreaker configurations: {}", circuitBreakerRegistry.getAllCircuitBreakers());
    }

    @Bean
    public RetryConfig customRetryConfig() {
        return RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofMillis(500))
                .retryExceptions(
                    org.springframework.web.client.HttpServerErrorException.class,
                    java.io.IOException.class,
                    java.util.concurrent.TimeoutException.class
                )
                .build();
    }

    @Bean
    public CircuitBreakerConfig customCircuitBreakerConfig() {
        return CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofMillis(1000))
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .slidingWindowSize(5)
                .build();
    }
}

// === ARCHIVO: src/main/resources/application.yml ===
spring:
  application:
    name: payments-service
  datasource:
    url: jdbc:postgresql://localhost:5432/payments_db
    username: payments_user
    password: payments_password
    driver-class-name: org.postgresql.Driver
    hikari:
      maximum-pool-size: 10
      connection-timeout: 30000
      idle-timeout: 600000
      max-lifetime: 1800000
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.PostgreSQLDialect

server:
  port: 8080
  error:
    include-message: always
    include-binding-errors: always
    include-stacktrace: on-param

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,retryEvents,circuitbreakers
  endpoint:
    health:
      show-details: always
  health:
    circuitbreakers:
      enabled: true

resilience4j:
  retry:
    instances:
      antiFraudRetry:
        maxAttempts: 3
        waitDuration: 500ms
        retryExceptions:
          - org.springframework.web.client.HttpServerErrorException
          - java.io.IOException
          - java.util.concurrent.TimeoutException
      riskBureauRetry:
        maxAttempts: 3
        waitDuration: 500ms
        retryExceptions:
          - org.springframework.web.client.HttpServerErrorException
          - java.io.IOException
          - java.util.concurrent.TimeoutException
      bankingCoreRetry:
        maxAttempts: 2
        waitDuration: 1000ms
        retryExceptions:
          - org.springframework.web.client.HttpServerErrorException
          - java.io.IOException
          - java.util.concurrent.TimeoutException
      paymentGatewayRetry:
        maxAttempts: 3
        waitDuration: 500ms
        retryExceptions:
          - org.springframework.web.client.HttpServerErrorException
          - java.io.IOException
          - java.util.concurrent.TimeoutException
  circuitbreaker:
    instances:
      antiFraudCircuitBreaker:
        registerHealthIndicator: true
        failureRateThreshold: 50
        waitDurationInOpenState: 10s
        permittedNumberOfCallsInHalfOpenState: 3
        slidingWindowType: COUNT_BASED
        slidingWindowSize: 5
      riskBureauCircuitBreaker:
        registerHealthIndicator: true
        failureRateThreshold: 50
        waitDurationInOpenState: 10s
        permittedNumberOfCallsInHalfOpenState: 3
        slidingWindowType: COUNT_BASED
        slidingWindowSize: 5
      bankingCoreCircuitBreaker:
        registerHealthIndicator: true
        failureRateThreshold: 30
        waitDurationInOpenState: 20s
        permittedNumberOfCallsInHalfOpenState: 2
        slidingWindowType: COUNT_BASED
        slidingWindowSize: 5
      paymentGatewayCircuitBreaker:
        registerHealthIndicator: true
        failureRateThreshold: 50
        waitDurationInOpenState: 10s
        permittedNumberOfCallsInHalfOpenState: 3
        slidingWindowType: COUNT_BASED
        slidingWindowSize: 5

// === ARCHIVO: src/main/java/com/pragma/payments/domain/model/Transaction.java ===
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

// === ARCHIVO: src/main/java/com/pragma/payments/domain/model/TransactionStatus.java ===
package com.pragma.payments.domain.model;

/**
 * Enum que representa los posibles estados de una transacción de pago.
 */
public enum TransactionStatus {
    PENDING,
    PROCESSING,
    SUCCESS,
    FAILED,
    RETRYING,
    REJECTED,
    CANCELLED
}

// === ARCHIVO: src/main/java/com/pragma/payments/domain/port/CreditOriginatorPort.java ===
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

// === ARCHIVO: src/main/java/com/pragma/payments/domain/port/AntiFraudPort.java ===
package com.pragma.payments.domain.port;

import com.pragma.payments.domain.model.Transaction;
import java.util.Optional;

/**
 * Puerto para interactuar con el motor antifraude.
 * Define el contrato que la infraestructura debe implementar para evaluar
 * transacciones en busca de patrones fraudulentos.
 */
public interface AntiFraudPort {

    /**
     * Evalúa una transacción contra las reglas del motor antifraude.
     * @param transaction la transacción a evaluar
     * @return resultado de la evaluación con código de riesgo
     * @throws AntiFraudEvaluationException si ocurre un error al evaluar
     */
    AntiFraudResult evaluateTransaction(Transaction transaction) throws AntiFraudEvaluationException;

    /**
     * Consulta el historial de alertas de fraude para un cliente.
     * @param customerId identificador del cliente
     * @return lista de alertas previas
     * @throws AntiFraudEvaluationException si ocurre un error al consultar
     */
    FraudAlertHistory getCustomerAlertHistory(String customerId) throws AntiFraudEvaluationException;

    /**
     * Reporta una actividad sospechosa al motor antifraude.
     * @param transaction la transacción sospechosa
     * @param reason razón de la sospecha
     * @throws AntiFraudEvaluationException si ocurre un error al reportar
     */
    void reportSuspiciousActivity(Transaction transaction, String reason) throws AntiFraudEvaluationException;

    /**
     * Resultado de la evaluación antifraude.
     */
    record AntiFraudResult(
        String transactionId,
        RiskLevel riskLevel,
        String evaluationCode,
        String message,
        Optional<List<String>> triggeredRules
    ) {
        public boolean isApproved() {
            return riskLevel == RiskLevel.LOW;
        }

        public boolean requiresManualReview() {
            return riskLevel == RiskLevel.MEDIUM;
        }
    }

    /**
     * Niveles de riesgo evaluados por el motor antifraude.
     */
    enum RiskLevel {
        LOW,
        MEDIUM,
        HIGH,
        BLOCKED
    }

    /**
     * Historial de alertas de fraude para un cliente.
     */
    record FraudAlertHistory(
        String customerId,
        int totalAlerts,
        int resolvedAlerts,
        LocalDateTime lastAlertDate,
        List<AlertDetail> recentAlerts
    ) {
        public boolean hasRecentAlerts(int days) {
            if (lastAlertDate == null) return false;
            return lastAlertDate.isAfter(LocalDateTime.now().minusDays(days));
        }
    }

    /**
     * Detalle de una alerta específica.
     */
    record AlertDetail(
        String alertId,
        String transactionId,
        String reason,
        LocalDateTime createdAt,
        boolean resolved
    ) {}

    /**
     * Excepción específica para errores del motor antifraude.
     */
    class AntiFraudEvaluationException extends RuntimeException {
        private final String errorCode;
        private final String transactionId;

        public AntiFraudEvaluationException(String message) {
            super(message);
            this.errorCode = "AFR000";
            this.transactionId = null;
        }

        public AntiFraudEvaluationException(String message, Throwable cause) {
            super(message, cause);
            this.errorCode = "AFR000";
            this.transactionId = null;
        }

        public AntiFraudEvaluationException(String message, String errorCode, String transactionId) {
            super(message);
            this.errorCode = errorCode;
            this.transactionId = transactionId;
        }

        public AntiFraudEvaluationException(String message, Throwable cause, String errorCode, String transactionId) {
            super(message, cause);
            this.errorCode = errorCode;
            this.transactionId = transactionId;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public Optional<String> getTransactionId() {
            return Optional.ofNullable(transactionId);
        }
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/domain/port/RiskBureauPort.java ===
package com.pragma.payments.domain.port;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

/**
 * Puerto para interactuar con el buró de riesgos.
 * Define el contrato para consultar y报告ar información crediticia
 * del cliente relacionado con la transacción.
 */
public interface RiskBureauPort {

    /**
     * Consulta el perfil de riesgo de un cliente en el buró.
     * @param customerId identificador del cliente
     * @return perfil de riesgo con puntuación y detalles
     * @throws RiskBureauException si ocurre un error al consultar
     */
    RiskProfile getCustomerRiskProfile(String customerId) throws RiskBureauException;

    /**
     * Consulta el límite de crédito disponible para un cliente.
     * @param customerId identificador del cliente
     * @param merchantId identificador del comerciante
     * @return límite de crédito disponible
     * @throws RiskBureauException si ocurre un error al consultar
     */
    BigDecimal getAvailableCreditLimit(String customerId, String merchantId) throws RiskBureauException;

    /**
     * Reporta un cambio en el estado de riesgo del cliente.
     * @param customerId identificador del cliente
     * @param newStatus nuevo estado de riesgo
     * @param reason razón del cambio
     * @throws RiskBureauException si ocurre un error al reportar
     */
    void reportRiskStatusChange(String customerId, RiskStatus newStatus, String reason) throws RiskBureauException;

    /**
     * Consulta el historial de pagos del cliente.
     * @param customerId identificador del cliente
     * @param monthsAhead número de meses hacia atrás a consultar
     * @return historial de pagos
     * @throws RiskBureauException si ocurre un error al consultar
     */
    PaymentHistory getPaymentHistory(String customerId, int monthsAhead) throws RiskBureauException;

    /**
     * Perfil de riesgo de un cliente.
     */
    record RiskProfile(
        String customerId,
        int riskScore,
        RiskStatus status,
        BigDecimal totalDebt,
        BigDecimal creditLimit,
        int activeLoans,
        LocalDateTime lastUpdate,
        List<RiskFactor> riskFactors
    ) {
        public boolean isEligibleForTransaction(BigDecimal amount) {
            return status == RiskStatus.ACTIVE && 
                   riskScore >= 600 && 
                   totalDebt.compareTo(creditLimit.multiply(BigDecimal.valueOf(0.8))) < 0;
        }

        public boolean hasHighRiskIndicators() {
            return riskFactors.stream().anyMatch(RiskFactor::isHighImpact);
        }
    }

    /**
     * Estados posibles del riesgo del cliente.
     */
    enum RiskStatus {
        ACTIVE,
        SUSPENDED,
        DEFAULT,
        INACTIVE,
        UNDER_REVIEW
    }

    /**
     * Factor de riesgo específico.
     */
    record RiskFactor(
        String code,
        String description,
        int impactScore,
        boolean isHighImpact
    ) {}

    /**
     * Historial de pagos de un cliente.
     */
    record PaymentHistory(
        String customerId,
        int totalPayments,
        int onTimePayments,
        int latePayments,
        BigDecimal totalAmountPaid,
        BigDecimal averagePaymentAmount,
        List<PaymentRecord> recentPayments
    ) {
        public double getOnTimePaymentRate() {
            if (totalPayments == 0) return 0.0;
            return (double) onTimePayments / totalPayments * 100;
        }

        public boolean hasRecentLatePayments(int months) {
            LocalDateTime cutoff = LocalDateTime.now().minusMonths(months);
            return recentPayments.stream()
                .anyMatch(p -> p.paymentDate().isAfter(cutoff) && !p.isOnTime());
        }
    }

    /**
     * Registro de un pago específico.
     */
    record PaymentRecord(
        String paymentId,
        BigDecimal amount,
        LocalDateTime paymentDate,
        LocalDateTime dueDate,
        boolean isOnTime,
        String status
    ) {}

    /**
     * Excepción específica para errores del buró de riesgos.
     */
    class RiskBureauException extends RuntimeException {
        private final String errorCode;
        private final String customerId;

        public RiskBureauException(String message) {
            super(message);
            this.errorCode = "RBU000";
            this.customerId = null;
        }

        public RiskBureauException(String message, Throwable cause) {
            super(message, cause);
            this.errorCode = "RBU000";
            this.customerId = null;
        }

        public RiskBureauException(String message, String errorCode, String customerId) {
            super(message);
            this.errorCode = errorCode;
            this.customerId = customerId;
        }

        public RiskBureauException(String message, Throwable cause, String errorCode, String customerId) {
            super(message, cause);
            this.errorCode = errorCode;
            this.customerId = customerId;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public Optional<String> getCustomerId() {
            return Optional.ofNullable(customerId);
        }
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/domain/port/BankingCorePort.java ===
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

// === ARCHIVO: src/main/java/com/pragma/payments/domain/port/PaymentGatewayPort.java ===
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

// === ARCHIVO: src/main/java/com/pragma/payments/application/usecase/ProcessPaymentUseCase.java ===
package com.pragma.payments.application.usecase;

import com.pragma.payments.domain.model.Transaction;
import com.pragma.payments.domain.model.TransactionStatus;
import com.pragma.payments.domain.port.CreditOriginatorPort;
import com.pragma.payments.domain.port.PaymentGatewayPort;
import com.pragma.payments.infrastructure.exception.PaymentGatewayException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Caso de uso principal que orquesta el procesamiento completo de un pago.
 * Coordina la interacción entre el originador de créditos y la gateway de pagos,
 * manejando errores y reintentos de manera centralizada.
 *
 * Este caso de uso sigue el patrón de aplicación orquestadora, delegando las
 * operaciones específicas a los puertos del dominio que son implementados por
 * los adaptadores de infraestructura.
 */
@Component
public class ProcessPaymentUseCase {

    private static final Logger log = LoggerFactory.getLogger(ProcessPaymentUseCase.class);
    private static final int MAX_RETRIES = 3;

    private final PaymentGatewayPort paymentGatewayPort;
    private final CreditOriginatorPort creditOriginatorPort;

    public ProcessPaymentUseCase(
            PaymentGatewayPort paymentGatewayPort,
            CreditOriginatorPort creditOriginatorPort) {
        this.paymentGatewayPort = paymentGatewayPort;
        this.creditOriginatorPort = creditOriginatorPort;
    }

    /**
     * Ejecuta el flujo completo de procesamiento de un pago.
     *
     * El flujo incluye:
     * 1. Registro inicial de la transacción en el originador de créditos
     * 2. Procesamiento del pago a través de la gateway
     * 3. Actualización del estado de la transacción
     *
     * @param transaction la transacción a procesar
     * @return la transacción procesada con estado actualizado
     */
    @Retry(name = "paymentProcessRetry")
    @CircuitBreaker(name = "paymentCircuitBreaker", fallbackMethod = "processPaymentFallback")
    public Transaction execute(Transaction transaction) {
        log.info("Iniciando procesamiento de pago para transacción: {}", transaction.getId());

        validateTransaction(transaction);

        Transaction registeredTransaction = registerTransaction(transaction);

        Transaction processedTransaction = processWithGateway(registeredTransaction);

        updateTransactionStatus(processedTransaction);

        log.info("Pago procesado exitosamente para transacción: {}", processedTransaction.getId());
        return processedTransaction;
    }

    /**
     * Valida que la transacción tenga todos los datos requeridos.
     */
    private void validateTransaction(Transaction transaction) {
        if (transaction.getAmount() == null || transaction.getAmount().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto de la transacción debe ser mayor a cero");
        }
        if (transaction.getMerchantId() == null || transaction.getMerchantId().isBlank()) {
            throw new IllegalArgumentException("El ID del comerciante es requerido");
        }
        if (transaction.getCustomerId() == null || transaction.getCustomerId().isBlank()) {
            throw new IllegalArgumentException("El ID del cliente es requerido");
        }
        log.debug("Transacción validada correctamente");
    }

    /**
     * Registra la transacción en el originador de créditos.
     */
    private Transaction registerTransaction(Transaction transaction) {
        try {
            log.debug("Registrando transacción en originador de créditos: {}", transaction.getId());
            Transaction registered = creditOriginatorPort.registerTransaction(transaction);
            log.info("Transacción registrada exitosamente en originador: {}", registered.getId());
            return registered;
        } catch (Exception e) {
            log.error("Error al registrar transacción en originador: {}", e.getMessage());
            transaction.setFailureReason("Error en registro de transacción: " + e.getMessage());
            throw new RuntimeException("Fallo en registro de transacción", e);
        }
    }

    /**
     * Procesa el pago a través de la gateway de pagos.
     */
    private Transaction processWithGateway(Transaction transaction) {
        try {
            log.debug("Procesando pago con gateway para transacción: {}", transaction.getId());
            Transaction processed = paymentGatewayPort.processPayment(transaction);
            log.info("Pago procesado por gateway para transacción: {}", processed.getId());
            return processed;
        } catch (PaymentGatewayException e) {
            log.error("Error de la gateway de pagos: {}", e.getMessage());
            transaction.setFailureReason("Error en gateway de pagos: " + e.getMessage());
            throw new RuntimeException("Fallo en procesamiento de pago", e);
        }
    }

    /**
     * Actualiza el estado de la transacción en el originador de créditos.
     */
    private void updateTransactionStatus(Transaction transaction) {
        try {
            log.debug("Actualizando estado de transacción: {}", transaction.getId());
            TransactionStatus newStatus = determineTransactionStatus(transaction);
            creditOriginatorPort.updateTransactionStatus(
                transaction.getId().toString(),
                newStatus,
                Optional.ofNullable(transaction.getFailureReason())
            );
            log.info("Estado de transacción actualizado a: {}", newStatus);
        } catch (Exception e) {
            log.warn("Error al actualizar estado de transacción: {}", e.getMessage());
        }
    }

    /**
     * Determina el estado final de la transacción basado en el resultado del procesamiento.
     */
    private TransactionStatus determineTransactionStatus(Transaction transaction) {
        if (transaction.getFailureReason() != null && !transaction.getFailureReason().isBlank()) {
            return TransactionStatus.FAILED;
        }
        return TransactionStatus.COMPLETED;
    }

    /**
     * Método de fallback cuando el circuit breaker está abierto o hay una excepción.
     * Proporciona una respuesta degradada graceful.
     */
    private Transaction processPaymentFallback(Transaction transaction, Throwable throwable) {
        log.error("Circuit breaker activado para transacción: {}. Error: {}", 
            transaction.getId(), throwable.getMessage());

        transaction.setFailureReason("Sistema temporalmente no disponible. Por favor intente más tarde.");
        transaction.setStatus(TransactionStatus.PENDING);

        return transaction;
    }

    /**
     * Reintenta una transacción que falló anteriormente.
     * Verifica que la transacción aún pueda ser reintentada según la configuración.
     *
     * @param transactionId ID de la transacción a reintentar
     * @return la transacción reintentada
     */
    @Retry(name = "paymentRetry")
    public Transaction retryTransaction(UUID transactionId) {
        log.info("Iniciando reintento para transacción: {}", transactionId);

        Optional<Transaction> transactionOpt = creditOriginatorPort.getTransactionById(transactionId.toString());
        if (transactionOpt.isEmpty()) {
            throw new IllegalArgumentException("Transacción no encontrada: " + transactionId);
        }

        Transaction transaction = transactionOpt.get();
        if (!transaction.canRetry(MAX_RETRIES)) {
            throw new IllegalStateException("Transacción excedió el límite de reintentos");
        }

        transaction.incrementRetryCount();
        return execute(transaction);
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/adapter/CreditOriginatorAdapter.java ===
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

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/adapter/AntiFraudAdapter.java ===
package com.pragma.payments.infrastructure.adapter;

import com.pragma.payments.domain.model.Transaction;
import com.pragma.payments.domain.port.AntiFraudPort;
import com.pragma.payments.infrastructure.exception.AntiFraudException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class AntiFraudAdapter implements AntiFraudPort {

    @Override
    @CircuitBreaker(name = "antiFraudCircuitBreaker", fallbackMethod = "fallbackValidateTransaction")
    @Retry(name = "antiFraudRetry")
    public boolean validateTransaction(Transaction transaction) throws AntiFraudException {
        log.info("Iniciando validación antifraude para transacción: {}", transaction.getId());

        try {
            boolean isValid = performAntiFraudValidation(transaction);

            if (isValid) {
                log.info("Transacción {} validada exitosamente por motor antifraude", transaction.getId());
            } else {
                log.warn("Transacción {} rechazada por motor antifraude", transaction.getId());
            }

            return isValid;
        } catch (Exception e) {
            log.error("Error en validación antifraude para transacción {}: {}", transaction.getId(), e.getMessage());
            throw new AntiFraudException("Error en validación antifraude: " + e.getMessage(), e);
        }
    }

    private boolean performAntiFraudValidation(Transaction transaction) {
        log.debug("Ejecutando validación interna de antifraude para amount: {}, merchant: {}",
                transaction.getAmount(), transaction.getMerchantId());

        if (transaction.getAmount().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            log.warn("Monto inválido detectado: {}", transaction.getAmount());
            return false;
        }

        if (transaction.getMerchantId() == null || transaction.getMerchantId().isBlank()) {
            log.warn("Merchant ID inválido");
            return false;
        }

        if (transaction.getCustomerId() == null || transaction.getCustomerId().isBlank()) {
            log.warn("Customer ID inválido");
            return false;
        }

        if (transaction.getAmount().compareTo(new java.math.BigDecimal("10000")) > 0) {
            log.info("Monto alto detectado, requiriendo validación adicional");
            return validateHighValueTransaction(transaction);
        }

        return true;
    }

    private boolean validateHighValueTransaction(Transaction transaction) {
        log.debug("Validando transacción de alto valor: {}", transaction.getAmount());

        String customerId = transaction.getCustomerId();
        if (customerId.startsWith("TEST")) {
            log.warn("Cliente de prueba detectado en transacción de alto valor");
            return false;
        }

        return true;
    }

    private boolean fallbackValidateTransaction(Transaction transaction, Exception e) {
        log.error("FALLBACK: Circuit breaker abierto o reintentos agotados para AntiFraud. Transacción: {}. Error: {}",
                transaction.getId(), e.getMessage());

        if (e instanceof AntiFraudException) {
            throw (AntiFraudException) e;
        }

        throw new AntiFraudException("Servicio de antifraude no disponible temporalmente", e);
    }

    @Override
    public String getServiceName() {
        return "AntiFraudService";
    }

    @Override
    public boolean isAvailable() {
        log.debug("Verificando disponibilidad del servicio AntiFraud");
        return true;
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/adapter/RiskBureauAdapter.java ===
package com.pragma.payments.infrastructure.adapter;

import com.pragma.payments.domain.model.Transaction;
import com.pragma.payments.domain.port.RiskBureauPort;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class RiskBureauAdapter implements RiskBureauPort {

    @Override
    @CircuitBreaker(name = "riskBureauCircuitBreaker", fallbackMethod = "fallbackEvaluateRisk")
    @Retry(name = "riskBureauRetry")
    public RiskScore evaluateRisk(Transaction transaction) throws RiskBureauException {
        log.info("Iniciando evaluación de riesgo para transacción: {}", transaction.getId());

        try {
            RiskScore score = calculateRiskScore(transaction);

            log.info("Evaluación de riesgo completada para transacción {}. Score: {}, Nivel: {}",
                    transaction.getId(), score.score(), score.riskLevel());

            return score;
        } catch (Exception e) {
            log.error("Error en evaluación de riesgo para transacción {}: {}", transaction.getId(), e.getMessage());
            throw new RiskBureauException("Error en evaluación de riesgo: " + e.getMessage(), e);
        }
    }

    private RiskScore calculateRiskScore(Transaction transaction) {
        log.debug("Calculando score de riesgo para merchant: {}, amount: {}",
                transaction.getMerchantId(), transaction.getAmount());

        int baseScore = 50;

        BigDecimal amount = transaction.getAmount();
        if (amount.compareTo(new BigDecimal("1000")) > 0) {
            baseScore += 20;
            log.debug("Monto mayor a 1000, incrementando riesgo");
        }

        if (amount.compareTo(new BigDecimal("5000")) > 0) {
            baseScore += 15;
            log.debug("Monto mayor a 5000, incrementando riesgo");
        }

        String paymentMethod = transaction.getPaymentMethod();
        if ("CREDIT_CARD".equals(paymentMethod)) {
            baseScore += 10;
            log.debug("Método de pago con tarjeta de crédito, ajuste de riesgo");
        } else if ("DEBIT_CARD".equals(paymentMethod)) {
            baseScore += 5;
        }

        String customerId = transaction.getCustomerId();
        if (customerId != null && customerId.length() < 8) {
            baseScore += 10;
            log.debug("Customer ID corto detectado, incrementando riesgo");
        }

        RiskLevel level = determineRiskLevel(baseScore);

        return new RiskScore(
                transaction.getId().toString(),
                baseScore,
                level,
                LocalDateTime.now(),
                generateRiskFactors(transaction)
        );
    }

    private RiskLevel determineRiskLevel(int score) {
        if (score >= 80) {
            return RiskLevel.HIGH;
        } else if (score >= 50) {
            return RiskLevel.MEDIUM;
        } else {
            return RiskLevel.LOW;
        }
    }

    private String[] generateRiskFactors(Transaction transaction) {
        return new String[]{
                "MONTO: " + transaction.getAmount(),
                "METODO: " + transaction.getPaymentMethod(),
                "MERCHANT: " + transaction.getMerchantId()
        };
    }

    private RiskScore fallbackEvaluateRisk(Transaction transaction, Exception e) {
        log.error("FALLBACK: Circuit breaker abierto o reintentos agotados para RiskBureau. Transacción: {}. Error: {}",
                transaction.getId(), e.getMessage());

        return new RiskScore(
                transaction.getId().toString(),
                50,
                RiskLevel.MEDIUM,
                LocalDateTime.now(),
                new String[]{"FALLBACK_ACTIVE", e.getMessage()}
        );
    }

    @Override
    public String getServiceName() {
        return "RiskBureauService";
    }

    @Override
    public boolean isAvailable() {
        log.debug("Verificando disponibilidad del servicio RiskBureau");
        return true;
    }

    public record RiskScore(
            String transactionId,
            int score,
            RiskLevel riskLevel,
            LocalDateTime evaluatedAt,
            String[] riskFactors
    ) {}

    public enum RiskLevel {
        LOW, MEDIUM, HIGH
    }

    public static class RiskBureauException extends RuntimeException {
        public RiskBureauException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/adapter/BankingCoreAdapter.java ===
package com.pragma.payments.infrastructure.adapter;

import com.pragma.payments.domain.model.Transaction;
import com.pragma.payments.domain.port.BankingCorePort;
import com.pragma.payments.infrastructure.exception.BankingCoreException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class BankingCoreAdapter implements BankingCorePort {

    @Override
    @CircuitBreaker(name = "bankingCoreCircuitBreaker", fallbackMethod = "fallbackProcessPayment")
    @Retry(name = "bankingCoreRetry")
    public PaymentResult processPayment(Transaction transaction) throws BankingCoreException {
        log.info("Iniciando procesamiento de pago en core bancario para transacción: {}", transaction.getId());

        try {
            validatePaymentRequest(transaction);

            PaymentResult result = executePayment(transaction);

            log.info("Pago procesado exitosamente. TransactionId: {}, AuthorizationCode: {}",
                    transaction.getId(), result.authorizationCode());

            return result;
        } catch (BankingCoreException e) {
            log.error("Error en procesamiento de pago para transacción {}: {}", transaction.getId(), e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado en core bancario para transacción {}: {}", transaction.getId(), e.getMessage());
            throw new BankingCoreException("Error inesperado en procesamiento: " + e.getMessage(), e);
        }
    }

    private void validatePaymentRequest(Transaction transaction) throws BankingCoreException {
        log.debug("Validando solicitud de pago para transacción: {}", transaction.getId());

        if (transaction.getAmount() == null || transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            log.error("Monto inválido para transacción: {}", transaction.getId());
            throw new BankingCoreException("Monto inválido");
        }

        if (transaction.getAmount().compareTo(new BigDecimal("50000")) > 0) {
            log.error("Monto excede límite máximo permitido: {}", transaction.getAmount());
            throw new BankingCoreException("Monto excede límite máximo");
        }

        if (transaction.getMerchantId() == null || transaction.getMerchantId().isBlank()) {
            log.error("Merchant ID no proporcionado para transacción: {}", transaction.getId());
            throw new BankingCoreException("Merchant ID requerido");
        }

        if (transaction.getCustomerId() == null || transaction.getCustomerId().isBlank()) {
            log.error("Customer ID no proporcionado para transacción: {}", transaction.getId());
            throw new BankingCoreException("Customer ID requerido");
        }

        log.debug("Validación completada exitosamente para transacción: {}", transaction.getId());
    }

    private PaymentResult executePayment(Transaction transaction) {
        log.debug("Ejecutando pago en sistema bancario para amount: {}, currency: {}",
                transaction.getAmount(), transaction.getCurrency());

        String authorizationCode = generateAuthorizationCode();

        String referenceNumber = generateReferenceNumber(transaction);

        return new PaymentResult(
                transaction.getId().toString(),
                authorizationCode,
                referenceNumber,
                PaymentStatus.APPROVED,
                LocalDateTime.now(),
                "Pago procesado exitosamente"
        );
    }

    private String generateAuthorizationCode() {
        return "AUTH-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private String generateReferenceNumber(Transaction transaction) {
        return "REF-" + transaction.getMerchantId().substring(0, Math.min(4, transaction.getMerchantId().length()))
                + "-" + System.currentTimeMillis();
    }

    private PaymentResult fallbackProcessPayment(Transaction transaction, Exception e) {
        log.error("FALLBACK: Circuit breaker abierto o reintentos agotados para BankingCore. Transacción: {}. Error: {}",
                transaction.getId(), e.getMessage());

        throw new BankingCoreException("Core bancario no disponible temporalmente. Por favor intente más tarde.", e);
    }

    @Override
    public PaymentResult reversePayment(String transactionId, String reason) throws BankingCoreException {
        log.info("Iniciando reversión de pago para transacción: {}, reason: {}", transactionId, reason);

        try {
            String reversalCode = "REV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

            log.info("Reversión completada para transacción: {}, código: {}", transactionId, reversalCode);

            return new PaymentResult(
                    transactionId,
                    reversalCode,
                    "REVERSAL-" + transactionId,
                    PaymentStatus.REVERSED,
                    LocalDateTime.now(),
                    "Reversión exitosa: " + reason
            );
        } catch (Exception e) {
            log.error("Error en reversión de pago para transacción {}: {}", transactionId, e.getMessage());
            throw new BankingCoreException("Error en reversión: " + e.getMessage(), e);
        }
    }

    @Override
    public AccountBalance getAccountBalance(String accountId) throws BankingCoreException {
        log.info("Consultando saldo para cuenta: {}", accountId);

        try {
            BigDecimal availableBalance = new BigDecimal("15000.00");
            BigDecimal holdBalance = new BigDecimal("500.00");

            return new AccountBalance(
                    accountId,
                    availableBalance,
                    holdBalance,
                    availableBalance.subtract(holdBalance),
                    LocalDateTime.now()
            );
        } catch (Exception e) {
            log.error("Error consultando saldo para cuenta {}: {}", accountId, e.getMessage());
            throw new BankingCoreException("Error consultando saldo: " + e.getMessage(), e);
        }
    }

    @Override
    public String getServiceName() {
        return "BankingCoreService";
    }

    @Override
    public boolean isAvailable() {
        log.debug("Verificando disponibilidad del servicio BankingCore");
        return true;
    }

    public record PaymentResult(
            String transactionId,
            String authorizationCode,
            String referenceNumber,
            PaymentStatus status,
            LocalDateTime processedAt,
            String message
    ) {}

    public enum PaymentStatus {
        APPROVED, DECLINED, REVERSED, PENDING
    }

    public record AccountBalance(
            String accountId,
            BigDecimal availableBalance,
            BigDecimal holdBalance,
            BigDecimal totalBalance,
            LocalDateTime asOf
    ) {}
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/adapter/PaymentGatewayAdapter.java ===
package com.pragma.payments.infrastructure.adapter;

import com.pragma.payments.domain.model.Transaction;
import com.pragma.payments.domain.port.PaymentGatewayPort;
import com.pragma.payments.infrastructure.exception.PaymentGatewayException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class PaymentGatewayAdapter implements PaymentGatewayPort {

    private static final Logger log = LoggerFactory.getLogger(PaymentGatewayAdapter.class);
    private static final String GATEWAY_NAME = "PAYMENT_GATEWAY";
    private static final Map<String, Transaction> transactionStore = new HashMap<>();

    @Override
    @Retry(name = "paymentGatewayRetry", fallbackMethod = "processPaymentFallback")
    @CircuitBreaker(name = "paymentGatewayCircuitBreaker", fallbackMethod = "processPaymentFallback")
    public Transaction processPayment(Transaction transaction) {
        log.info("Procesando pago para transacción: {} - Monto: {} {}", 
                transaction.getId(), transaction.getAmount(), transaction.getCurrency());

        validateTransaction(transaction);

        String transactionKey = generateTransactionKey(transaction.getMerchantId(), transaction.getId());
        
        try {
            PaymentResult result = executePaymentGatewayCall(transaction);
            
            if (result.isSuccessful()) {
                transaction.markAsProcessed();
                transactionStore.put(transactionKey, transaction);
                log.info("Pago procesado exitosamente para transacción: {}", transaction.getId());
            } else {
                transaction.setFailureReason(result.getErrorMessage());
                log.warn("Pago rechazado por gateway para transacción: {} - Razón: {}", 
                        transaction.getId(), result.getErrorMessage());
            }
            
            return transaction;
            
        } catch (GatewayCommunicationException e) {
            log.error("Error de comunicación con gateway de pagos para transacción: {} - {}", 
                    transaction.getId(), e.getMessage());
            throw new PaymentGatewayException("Error de comunicación con gateway: " + e.getMessage(), e);
        } catch (GatewayTimeoutException e) {
            log.error("Timeout del gateway de pagos para transacción: {} - {}", 
                    transaction.getId(), e.getMessage());
            throw new PaymentGatewayException("Timeout del gateway: " + e.getMessage(), e);
        }
    }

    @Override
    @Retry(name = "paymentGatewayRetry")
    public Optional<Transaction> getPaymentStatus(String merchantId, String transactionId) {
        log.debug("Consultando estado del pago - Merchant: {} - Transaction: {}", merchantId, transactionId);
        
        String key = generateTransactionKey(merchantId, UUID.fromString(transactionId));
        return Optional.ofNullable(transactionStore.get(key));
    }

    @Override
    @Retry(name = "paymentGatewayRefundRetry")
    public Transaction refundPayment(String merchantId, String transactionId, BigDecimal amount) {
        log.info("Iniciando reembolso para transacción: {} - Monto: {}", transactionId, amount);
        
        String key = generateTransactionKey(merchantId, UUID.fromString(transactionId));
        Transaction originalTransaction = transactionStore.get(key);
        
        if (originalTransaction == null) {
            log.error("Transacción no encontrada para reembolso: {}", transactionId);
            throw new PaymentGatewayException("Transacción no encontrada: " + transactionId);
        }
        
        if (amount.compareTo(originalTransaction.getAmount()) > 0) {
            log.error("Monto de reembolso excede el original - Requerido: {} - Original: {}", 
                    amount, originalTransaction.getAmount());
            throw new PaymentGatewayException("Monto de reembolso excede el original");
        }
        
        RefundResult result = executeRefundCall(merchantId, transactionId, amount);
        
        if (result.isSuccessful()) {
            log.info("Reembolso procesado exitosamente para transacción: {}", transactionId);
        } else {
            log.warn("Reembolso rechazado para transacción: {} - Razón: {}", 
                    transactionId, result.getErrorMessage());
            throw new PaymentGatewayException("Reembolso rechazado: " + result.getErrorMessage());
        }
        
        return originalTransaction;
    }

    private void validateTransaction(Transaction transaction) {
        if (transaction.getAmount() == null || transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new PaymentGatewayException("Monto de transacción inválido");
        }
        if (transaction.getMerchantId() == null || transaction.getMerchantId().isBlank()) {
            throw new PaymentGatewayException("ID de comerciante inválido");
        }
        if (transaction.getCustomerId() == null || transaction.getCustomerId().isBlank()) {
            throw new PaymentGatewayException("ID de cliente inválido");
        }
        if (transaction.getPaymentMethod() == null || transaction.getPaymentMethod().isBlank()) {
            throw new PaymentGatewayException("Método de pago no especificado");
        }
    }

    private PaymentResult executePaymentGatewayCall(Transaction transaction) {
        log.debug("Ejecutando llamada al gateway de pagos para {}", transaction.getId());
        
        simulateGatewayProcessing(transaction);
        
        boolean simulatedSuccess = !transaction.getCustomerId().contains("DECLINED") 
                && !transaction.getMerchantId().contains("SUSPENDED");
        
        if (transaction.getAmount().compareTo(new BigDecimal("10000")) > 0) {
            return new PaymentResult(false, "Monto excede límite permitido");
        }
        
        return new PaymentResult(simulatedSuccess, 
                simulatedSuccess ? null : "Transacción declinada por el emisor");
    }

    private RefundResult executeRefundCall(String merchantId, String transactionId, BigDecimal amount) {
        log.debug("Ejecutando reembolso para transacción: {}", transactionId);
        
        simulateRefundProcessing(merchantId, transactionId);
        
        return new RefundResult(true, null);
    }

    private void simulateGatewayProcessing(Transaction transaction) {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("Simulación de procesamiento interrumpida para {}", transaction.getId());
        }
    }

    private void simulateRefundProcessing(String merchantId, String transactionId) {
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("Simulación de reembolso interrumpida para {}", transactionId);
        }
    }

    private String generateTransactionKey(String merchantId, UUID transactionId) {
        return merchantId + "_" + transactionId.toString();
    }

    private Transaction processPaymentFallback(Transaction transaction, Throwable t) {
        log.error("Fallback activado para transacción: {} - Causa: {}", 
                transaction.getId(), t.getMessage());
        transaction.setFailureReason("Servicio de pagos no disponible: " + t.getMessage());
        return transaction;
    }

    private record PaymentResult(boolean successful, String errorMessage) {}
    private record RefundResult(boolean successful, String errorMessage) {}

    private static class GatewayCommunicationException extends RuntimeException {
        public GatewayCommunicationException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    private static class GatewayTimeoutException extends RuntimeException {
        public GatewayTimeoutException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/config/Resilience4jConfig.java ===
package com.pragma.payments.infrastructure.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class Resilience4jConfig {

    @Bean
    public RetryRegistry retryRegistry() {
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofMillis(500))
                .build();
        return RetryRegistry.of(config);
    }

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofSeconds(30))
                .slidingWindowSize(10)
                .minimumNumberOfCalls(5)
                .build();
        return CircuitBreakerRegistry.of(config);
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/exception/AntiFraudException.java ===
package com.pragma.payments.infrastructure.exception;

public class AntiFraudException extends RuntimeException {
    private final String transactionId;
    private final String riskLevel;
    private final boolean retryable;

    public AntiFraudException(String message) {
        super(message);
        this.transactionId = null;
        this.riskLevel = null;
        this.retryable = false;
    }

    public AntiFraudException(String message, Throwable cause) {
        super(message, cause);
        this.transactionId = null;
        this.riskLevel = null;
        this.retryable = false;
    }

    public AntiFraudException(String message, String transactionId, String riskLevel, boolean retryable) {
        super(message);
        this.transactionId = transactionId;
        this.riskLevel = riskLevel;
        this.retryable = retryable;
    }

    public AntiFraudException(String message, String transactionId, String riskLevel, boolean retryable, Throwable cause) {
        super(message, cause);
        this.transactionId = transactionId;
        this.riskLevel = riskLevel;
        this.retryable = retryable;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public boolean isRetryable() {
        return retryable;
    }

    public boolean isHighRisk() {
        return "HIGH".equalsIgnoreCase(riskLevel);
    }

    public boolean isMediumRisk() {
        return "MEDIUM".equalsIgnoreCase(riskLevel);
    }

    public boolean requiresManualReview() {
        return "HIGH".equalsIgnoreCase(riskLevel) || "MEDIUM".equalsIgnoreCase(riskLevel);
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/exception/BankingCoreException.java ===
package com.pragma.payments.infrastructure.exception;

public class BankingCoreException extends RuntimeException {
    private final String accountId;
    private final String operationType;
    private final boolean retryable;
    private final int httpStatusCode;

    public BankingCoreException(String message) {
        super(message);
        this.accountId = null;
        this.operationType = null;
        this.retryable = false;
        this.httpStatusCode = 500;
    }

    public BankingCoreException(String message, Throwable cause) {
        super(message, cause);
        this.accountId = null;
        this.operationType = null;
        this.retryable = false;
        this.httpStatusCode = 500;
    }

    public BankingCoreException(String message, String accountId, String operationType, boolean retryable) {
        super(message);
        this.accountId = accountId;
        this.operationType = operationType;
        this.retryable = retryable;
        this.httpStatusCode = retryable ? 503 : 400;
    }

    public BankingCoreException(String message, String accountId, String operationType, boolean retryable, Throwable cause) {
        super(message, cause);
        this.accountId = accountId;
        this.operationType = operationType;
        this.retryable = retryable;
        this.httpStatusCode = retryable ? 503 : 400;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getOperationType() {
        return operationType;
    }

    public boolean isRetryable() {
        return retryable;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public boolean isInsufficientFunds() {
        return getMessage() != null && getMessage().toLowerCase().contains("insufficient funds");
    }

    public boolean isAccountLocked() {
        return getMessage() != null && getMessage().toLowerCase().contains("account locked");
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/exception/PaymentGatewayException.java ===
package com.pragma.payments.infrastructure.exception;

public class PaymentGatewayException extends RuntimeException {
    private final String gatewayCode;
    private final String gatewayMessage;
    private final boolean retryable;
    private final long retryAfterMillis;

    public PaymentGatewayException(String message) {
        super(message);
        this.gatewayCode = null;
        this.gatewayMessage = null;
        this.retryable = false;
        this.retryAfterMillis = 0;
    }

    public PaymentGatewayException(String message, Throwable cause) {
        super(message, cause);
        this.gatewayCode = null;
        this.gatewayMessage = null;
        this.retryable = false;
        this.retryAfterMillis = 0;
    }

    public PaymentGatewayException(String message, String gatewayCode, String gatewayMessage, boolean retryable) {
        super(message);
        this.gatewayCode = gatewayCode;
        this.gatewayMessage = gatewayMessage;
        this.retryable = retryable;
        this.retryAfterMillis = retryable ? 5000 : 0;
    }

    public PaymentGatewayException(String message, String gatewayCode, String gatewayMessage, boolean retryable, long retryAfterMillis) {
        super(message);
        this.gatewayCode = gatewayCode;
        this.gatewayMessage = gatewayMessage;
        this.retryable = retryable;
        this.retryAfterMillis = retryAfterMillis;
    }

    public PaymentGatewayException(String message, String gatewayCode, String gatewayMessage, boolean retryable, long retryAfterMillis, Throwable cause) {
        super(message, cause);
        this.gatewayCode = gatewayCode;
        this.gatewayMessage = gatewayMessage;
        this.retryable = retryable;
        this.retryAfterMillis = retryAfterMillis;
    }

    public String getGatewayCode() {
        return gatewayCode;
    }

    public String getGatewayMessage() {
        return gatewayMessage;
    }

    public boolean isRetryable() {
        return retryable;
    }

    public long getRetryAfterMillis() {
        return retryAfterMillis;
    }

    public boolean isTimeout() {
        return gatewayCode != null && gatewayCode.equals("TIMEOUT");
    }

    public boolean isDeclined() {
        return gatewayCode != null && gatewayCode.startsWith("DECLINE");
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/exception/GlobalExceptionHandler.java ===
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

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/controller/PaymentController.java ===
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

// === ARCHIVO: src/test/java/com/pragma/payments/application/usecase/ProcessPaymentUseCaseTest.java ===
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

// === ARCHIVO: src/test/java/com/pragma/payments/infrastructure/adapter/AntiFraudAdapterTest.java ===
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
```
