# PMTool - Documento de Requisitos

## Alcance de la Solución
PMTool es una herramienta de gestión de proyectos diseñada para facilitar la planificación, seguimiento y finalización de proyectos mediante la organización jerárquica de actividades siguiendo una Estructura de Desglose de Trabajo (EDT/WBS). El sistema permite la gestión de portafolios de proyectos, establecimiento de fechas, control de presupuestos y seguimiento del estado de actividades.

La solución PMTool permite a los usuarios:

- Crear y gestionar proyectos con datos básicos (nombre, horas estimadas, presupuesto)
- Planificar fechas de inicio y fin para proyectos y actividades
- Crear y organizar actividades en estructura jerárquica EDT
- Gestionar subactividades a múltiples niveles
- Controlar estados de proyectos (PLANIFICADO, EN_CURSO, FINALIZADO)
- Controlar estados de actividades (PLANIFICADA, EN_EJECUCION, COMPLETADA)
- Finalizar proyectos verificando la completitud de todas sus actividades

## 1. Requisitos de Negocio

| ID | Descripción | Prioridad |
|----|-------------|-----------|
| RN1 | Gestionar eficientemente portafolios de proyectos con una estructura jerárquica clara | Alta |
| RN2 | Facilitar la planificación y seguimiento de proyectos mediante estructura de desglose de trabajo (EDT) | Alta |
| RN3 | Asegurar que todas las actividades de un proyecto se completen antes de finalizarlo | Alta |
| RN4 | Mantener un control preciso de tiempos planificados y reales de los proyectos | Alta |
| RN5 | Permitir la gestión del presupuesto asignado a los proyectos | Media |

## 2. Requisitos de Usuario

| ID | Descripción | Prioridad |
|----|-------------|-----------|
| RU1 | Como gerente de portafolio, quiero crear proyectos especificando nombre, horas y presupuesto | Alta |
| RU2 | Como gerente de proyecto, quiero planificar fechas de inicio y fin para un proyecto | Alta |
| RU3 | Como gerente de proyecto, quiero crear actividades con una estructura jerárquica EDT | Alta |
| RU4 | Como gerente de proyecto, quiero visualizar la jerarquía completa de actividades de un proyecto | Media |
| RU5 | Como gerente de proyecto, quiero activar o desactivar actividades para reflejar su estado actual | Media |
| RU6 | Como gerente de proyecto, quiero finalizar un proyecto cuando todas sus actividades estén completadas | Alta |

## 3. Requisitos Funcionales

| ID | Descripción | Prioridad |
|----|-------------|-----------|
| RF1 | El sistema debe permitir crear un proyecto con nombre, horas estimadas y presupuesto | Alta |
| RF2 | El sistema debe permitir establecer fechas planificadas de inicio y fin para proyectos | Alta |
| RF3 | El sistema debe permitir crear actividades principales dentro de un proyecto | Alta |
| RF4 | El sistema debe permitir crear subactividades dentro de actividades existentes | Alta |
| RF5 | El sistema debe asignar automáticamente números EDT a las actividades según su jerarquía | Alta |
| RF6 | El sistema debe permitir establecer fechas planificadas para actividades | Alta |
| RF7 | El sistema debe permitir cambiar el estado de las actividades a PLANIFICADA, EN_EJECUCION o COMPLETADA | Media |
| RF8 | El sistema debe actualizar automáticamente el estado del proyecto según el estado de sus actividades | Media |
| RF9 | El sistema debe impedir finalizar un proyecto si tiene actividades no completadas | Alta |
| RF10 | El sistema debe mostrar la jerarquía completa de actividades con su numeración EDT | Media |

## 4. Requisitos No Funcionales

| ID | Descripción | Prioridad |
|----|-------------|-----------|
| RNF1 | El sistema debe estar implementado en lenguaje Java | Alta |
| RNF2 | El sistema debe usar clases internas para representar la relación entre proyectos y actividades | Alta |
| RNF3 | El sistema debe utilizar enumeraciones para representar estados de proyectos y actividades | Media |
| RNF4 | El sistema debe gestionar fechas mediante la API LocalDate de Java | Media |
| RNF5 | El sistema debe soportar una estructura jerárquica de actividades potencialmente ilimitada en profundidad | Media |

## 5. Requisitos del Sistema

| ID | Descripción | Prioridad |
|----|-------------|-----------|
| RS1 | El sistema debe integrar la gestión de portafolio y la gestión de proyectos individuales | Alta |
| RS2 | El sistema debe ofrecer una representación visual de la estructura EDT de los proyectos | Media |
| RS3 | El sistema debe permitir la gestión de múltiples proyectos simultáneamente | Alta |

## 6. Reglas de Negocio

| ID | Descripción | Prioridad |
|----|-------------|-----------|
| RG1 | La fecha de inicio no puede ser posterior a la fecha de fin en proyectos y actividades | Alta |
| RG2 | Para finalizar un proyecto, todas sus actividades deben estar completadas | Alta |
| RG3 | No se puede activar una actividad que ya está completada | Alta |
| RG4 | No se puede desactivar una actividad que no está en ejecución | Alta |
| RG5 | Los números EDT deben reflejar la estructura jerárquica de las actividades | Alta |




# Matriz de Trazabilidad:


| ID | Descripción | Tipo | Dependencias | Fuente en el Código | Interesados | Impacto en el Usuario |
|----|-------------|------|--------------|---------------------|-------------|------------------------|
| RN1 | Gestionar portafolios de proyectos | Negocio | RS1, RS3 | GerentePortafolio.java | Gerentes de Portafolio, Directivos | Facilita la gestión global de múltiples proyectos |
| RN2 | Planificación con EDT | Negocio | RF3, RF4, RF5, RF10 | Proyecto.Actividad | Gerentes de Proyecto, Analistas de Negocio | Mejora la organización y seguimiento del trabajo |
| RN3 | Completar actividades antes de finalizar | Negocio | RF9, RG2 | Proyecto.java: finalizar() | Gerentes de Proyecto, Clientes | Garantiza entregables completos |
| RN4 | Control de tiempos | Negocio | RF2, RF6, RNF4 | LocalDate en Proyecto/Actividad | Gerentes de Proyecto, Patrocinadores | Permite planificación temporal precisa |
| RN5 | Gestión de presupuesto | Negocio | RF1 | Proyecto.java: presupuesto | Patrocinadores, Directivos | Facilita control financiero de proyectos |
| RU1 | Crear proyectos | Usuario | RF1 | GerentePortafolio.java: crearProyecto() | Gerentes de Portafolio | Permite iniciar un nuevo proyecto |
| RU2 | Planificar fechas de proyecto | Usuario | RF2 | GerenteProyecto.java: planificarProyecto() | Gerentes de Proyecto | Establece marco temporal del proyecto |
| RU3 | Crear actividades jerárquicas | Usuario | RF3, RF4, RF5 | GerenteProyecto.java: crearActividad() | Gerentes de Proyecto | Permite estructurar el trabajo de forma lógica |
| RU4 | Visualizar jerarquía | Usuario | RF10, RS2 | Main.java: mostrarActividadConJerarquia() | Gerentes de Proyecto, Miembros del Equipo | Facilita comprensión del alcance del proyecto |
| RU5 | Activar/desactivar actividades | Usuario | RF7 | Proyecto.Actividad: activar(), desactivar() | Gerentes de Proyecto, Miembros del Equipo | Permite seguimiento del progreso |
| RU6 | Finalizar proyecto | Usuario | RF9 | Proyecto.java: finalizar() | Gerentes de Proyecto, Gerentes de Portafolio | Formaliza la conclusión exitosa del proyecto |
| RF1 | Crear proyecto | Funcional | RU1, RN5, RNF1 | GerentePortafolio.java: crearProyecto() | Gerentes de Portafolio | Inicia el ciclo de vida del proyecto |
| RF2 | Establecer fechas de proyecto | Funcional | RU2, RN4, RNF4, RG1 | Proyecto.java: planificarFechas() | Gerentes de Proyecto | Define horizontes temporales |
| RF3 | Crear actividades principales | Funcional | RU3, RN2, RNF2 | GerenteProyecto.java: crearActividad() | Gerentes de Proyecto | Organiza el trabajo del proyecto |
| RF4 | Crear subactividades | Funcional | RU3, RN2, RNF2, RNF5 | Proyecto.Actividad: agregarSubactividad() | Gerentes de Proyecto | Permite desglose detallado del trabajo |
| RF5 | Asignar números EDT | Funcional | RU3, RN2, RG5 | Proyecto.Actividad: setNumeroEDT() | Gerentes de Proyecto | Facilita referencia unívoca a actividades |
| RF6 | Establecer fechas de actividades | Funcional | RN4, RNF4, RG1 | Proyecto.Actividad: planificarFechas() | Gerentes de Proyecto, Miembros del Equipo | Permite planificación detallada |
| RF7 | Cambiar estado de actividades | Funcional | RU5, RNF3, RG3, RG4 | Proyecto.Actividad: activar(), desactivar() | Miembros del Equipo | Refleja progreso real del trabajo |
| RF8 | Actualizar estado de proyecto | Funcional | RNF3 | Proyecto.java: estado | Gerentes de Proyecto, Directivos | Proporciona visibilidad del avance general |
| RF9 | Validar finalización de proyecto | Funcional | RU6, RN3, RG2 | Proyecto.java: finalizar() | Gerentes de Proyecto, Clientes | Garantiza completitud del proyecto |
| RF10 | Mostrar jerarquía EDT | Funcional | RU4, RN2, RNF5, RS2 | Main.java: mostrarActividadConJerarquia() | Gerentes de Proyecto, Miembros del Equipo | Mejora supervisión del proyecto |
| RNF1 | Implementación en Java | No Funcional | RF1-RF10 | Todo el código .java | Equipo de Desarrollo, CTO | Transparente para el usuario |
| RNF2 | Uso de clases internas | No Funcional | RF3, RF4 | Proyecto.Actividad | Equipo de Desarrollo | Proporciona cohesión conceptual |
| RNF3 | Uso de enumeraciones para estados | No Funcional | RF7, RF8 | EstadoProyecto, EstadoActividad | Equipo de Desarrollo | Garantiza consistencia de estados |
| RNF4 | Gestión de fechas con LocalDate | No Funcional | RF2, RF6, RN4 | Fechas en Proyecto y Actividad | Equipo de Desarrollo | Mejora precisión en planificación |
| RNF5 | Estructura jerárquica ilimitada | No Funcional | RF4, RF10 | Subactividades en Actividad | Equipo de Desarrollo, Gerentes de Proyecto | Permite descomposición compleja del trabajo |
| RS1 | Integración portafolio-proyectos | Sistema | RN1 | GerentePortafolio, GerenteProyecto | Gerentes de Portafolio, CTO | Facilita gestión unificada |
| RS2 | Representación visual EDT | Sistema | RF10, RU4 | Main.java: mostrarActividadConJerarquia() | Gerentes de Proyecto, Directivos | Mejora comprensión de la estructura |
| RS3 | Gestión múltiples proyectos | Sistema | RN1 | GerentePortafolio.java: proyectos | Gerentes de Portafolio, Directivos | Permite controlar varios proyectos a la vez |
| RG1 | Validación de fechas | Regla | RF2, RF6 | Validaciones en planificarFechas() | Gerentes de Proyecto, Analistas de Negocio | Evita inconsistencias temporales |
| RG2 | Validación para finalizar | Regla | RF9, RN3 | Proyecto.java: finalizar() | Gerentes de Proyecto, Clientes | Garantiza proyectos completos |
| RG3 | Restricción de activación | Regla | RF7 | Actividad.java: activar() | Miembros del Equipo, Gerentes de Proyecto | Mantiene integridad de estados |
| RG4 | Restricción de desactivación | Regla | RF7 | Actividad.java: desactivar() | Miembros del Equipo, Gerentes de Proyecto | Mantiene integridad de estados |
| RG5 | Números EDT coherentes | Regla | RF5 | Actividad.java: agregarSubactividad() | Gerentes de Proyecto, Analistas de Negocio | Facilita identificación unívoca |