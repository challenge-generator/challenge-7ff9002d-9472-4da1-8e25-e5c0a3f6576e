# Manejo de Errores y Reintentos en Sistema de Pagos

El sistema de pagos de una entidad financiera requiere un manejo robusto de errores y reintentos para asegurar la consistencia y disponibilidad del servicio. El sistema interactúa con varios componentes: el originador de créditos, el motor antifraude, el buró de riesgos, el core bancario y la gateway de pagos. El objetivo es asegurar que las transacciones se procesen correctamente a pesar de posibles fallos temporales en cualquiera de estos componentes.

## Informacion General

| Campo | Valor |
|-------|-------|
| **Tema** | E2E prod technologiesWork 202610131810 |
| **Nivel** | senior-l2 |
| **Tipo** | practical |
| **Tiempo estimado** | 8 horas |

## Fases del Reto

### Fase 0: Configuración del Proyecto

**Objetivo:** Obtener el proyecto base funcional enviando el Código Base a un asistente de IA, que lo analizará, corregirá errores y generará un ZIP listo para usar.

**Tiempo estimado:** 15-30 minutos

**Instrucciones:**

- Asegúrate de tener instalado para ejecutar el proyecto: JDK 17+, Maven 3.9+, IDE con soporte Java.
- Copia todo el contenido del campo **Código Base** de este reto — incluyendo el texto de instrucciones que aparece al inicio.
- Abre un asistente de IA (Claude en claude.ai, ChatGPT o Gemini — se recomienda Claude), pega el contenido copiado en el chat y envíalo.
- El asistente analizará los archivos, corregirá errores y generará un archivo ZIP descargable. Descárgalo y extráelo en la carpeta donde quieras trabajar.
- Ejecuta `mvn compile` en la raíz. Si no hay errores, estás listo.

**Entregable:** El proyecto compila/arranca sin errores.

<details>
<summary>Pistas de conocimiento</summary>

- Copia el Código Base completo incluyendo el texto de instrucciones al inicio — esas instrucciones le indican al asistente exactamente qué hacer con los archivos.
- Si el asistente no genera el ZIP automáticamente al terminar el análisis, escríbele: "genera el ZIP ahora".
- Si el proyecto tiene errores al arrancar, comparte el mensaje de error con el mismo asistente para que lo corrija.

</details>

### Fase 1: Implementación de Manejo de Errores Básico

**Objetivo:** Implementar un mecanismo básico de manejo de errores que permita identificar y registrar fallos en las transacciones.

**Tiempo estimado:** 2 horas

**Instrucciones:**

- Identificar las posibles fuentes de error en las interacciones con los componentes del sistema.
- Implementar un registro de errores que capture la naturaleza y el origen de cada fallo.
- Asegurar que el sistema continue operando a pesar de errores aislados.

**Entregable:** Mecanismo de registro de errores funcional.

<details>
<summary>Pistas de conocimiento</summary>

- Considera los diferentes tipos de errores que pueden ocurrir en cada interacción.
- Piensa en cómo el sistema puede continuar operando a pesar de errores puntuales.

</details>

### Fase 2: Implementación de Reintentos

**Objetivo:** Implementar un mecanismo de reintentos que permita recuperar transacciones fallidas.

**Tiempo estimado:** 3 horas

**Instrucciones:**

- Diseña un mecanismo de reintentos que intente volver a procesar las transacciones que han fallado.
- Establece un límite máximo de reintentos para evitar bucles infinitos.
- Implementa una lógica que determine cuándo y cómo se deben realizar los reintentos.

**Entregable:** Mecanismo de reintentos funcional.

<details>
<summary>Pistas de conocimiento</summary>

- Considera la latencia aceptable para los reintentos.
- Piensa en cómo manejar los casos donde los reintentos no tienen éxito.

</details>

### Fase 3: Optimización y Escalabilidad

**Objetivo:** Optimizar el mecanismo de reintentos para asegurar la escalabilidad y eficiencia del sistema.

**Tiempo estimado:** 3 horas

**Instrucciones:**

- Evalúa el rendimiento del mecanismo de reintentos bajo carga.
- Identifica y elimina cuellos de botella.
- Implementa mejoras para asegurar que el sistema pueda manejar un alto volumen de transacciones sin degradación de rendimiento.

**Entregable:** Mecanismo de reintentos optimizado y escalable.

<details>
<summary>Pistas de conocimiento</summary>

- Considera el uso de colas de mensajes para manejar la carga de reintentos.
- Piensa en cómo distribuir la carga de reintentos entre múltiples instancias del sistema.

</details>

## Dimensiones Evaluadas

- **queEs**: ¿Qué es un mecanismo de reintentos y por qué es importante en un sistema de pagos?
- **paraQueSirve**: ¿Para qué sirve el registro de errores en el contexto de un sistema de pagos?
- **comoSeUsa**: ¿Cómo se usa un mecanismo de reintentos en una transacción de pago?
- **erroresComunes**: ¿Cuáles son los errores comunes que pueden ocurrir en las interacciones con el motor antifraude y cómo se pueden manejar?
- **queDecisionesImplica**: ¿Qué decisiones implica la implementación de un mecanismo de reintentos escalable y eficiente?

## Criterios de Evaluacion

- Implementación de un mecanismo de registro de errores funcional.
- Implementación de un mecanismo de reintentos funcional.
- Optimización y escalabilidad del mecanismo de reintentos.

## Como trabajar con un asistente de IA

Hay dos caminos, elegi uno:

- **AGENTS.md** (recomendado) — instrucciones nativas del repo. Abri esta carpeta con tu agente local (Claude Code, Cursor, Codex, Copilot, Gemini) y las carga solo. Sabe que archivos faltan y con que comando se verifica, y completa el scaffold escribiendo en disco.
- **PROMPT_MEJORA.md** — para copiar y pegar en un chat (claude.ai, ChatGPT). Devuelve un ZIP con el proyecto. Sirve si no tenes un agente en el IDE.

Ninguno de los dos resuelve las fases del reto: eso es tu trabajo.

## Verificacion

El proyecto esta listo para trabajar cuando este comando corre sin errores:

```bash
mvn clean compile
```

---

*Reto generado automaticamente por Challenge Generator - Pragma*
