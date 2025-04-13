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

1.1 La gestión de portafolios de proyectos deberá realizarse con una estructura jerárquica clara.  
1.2 La planificación y el seguimiento de proyectos se realizarán mediante una estructura de desglose de trabajo (EDT).  
1.3 Todas las actividades de un proyecto deberán completarse antes de que este pueda finalizar.  
1.4 Se garantizará un control preciso de los tiempos planificados y reales de los proyectos.  
1.5 La gestión del presupuesto asignado a los proyectos será una funcionalidad clave.  

## 2. Requisitos de Usuario

2.1 El sistema debe permitir a los gerentes de portafolio crear proyectos especificando nombre, horas estimadas y presupuesto, para facilitar la planificación inicial de los proyectos.  
2.2 El sistema debe permitir a los gerentes de proyecto planificar fechas de inicio y fin para un proyecto, con el fin de garantizar un cronograma claro y definido.  
2.3 El sistema debe proporcionar la capacidad de organizar actividades en una estructura jerárquica EDT, para reflejar la relación entre tareas y subactividades.  
2.4 El sistema debe permitir visualizar la jerarquía completa de actividades de un proyecto, para facilitar el seguimiento y la supervisión del progreso.  
2.5 El sistema debe permitir cambiar el estado de las actividades para reflejar su progreso (e.g., activarlas o desactivarlas), asegurando que el estado actual sea consistente con la realidad.  
2.6 El sistema debe garantizar que un proyecto solo pueda finalizar cuando todas sus actividades estén completadas, para cumplir con los criterios de finalización establecidos.  

## 3. Requisitos Funcionales

3.1 **Gestión de Proyectos**
3.1.1 Será posible crear un proyecto con nombre, horas estimadas y presupuesto.  
3.1.1.1 Cuando se crea un proyecto, el estado inicial de este sera "PLANIFICADO"
3.1.2 Se podrán establecer fechas planificadas de inicio y fin para proyectos, lo que hara que automaticamente pase al estado "EN_CURSO".  
3.1.3 El estado del proyecto se actualizará automáticamente según el estado de sus actividades.  
3.1.4 Un proyecto solo podrá finalizar cuando todas sus actividades estén con el estado "COMPLETADA".  

3.2 **Gestión de Actividades**
3.2.1 Será posible crear actividades principales dentro de un proyecto.  
3.2.1.1 Cuando se crea una actividad, el estado inicial de esta sera "PLANIFICADO"
3.2.2 El sistema permitirá la creación de jerarquías en las actividades.
3.2.2.1 Los números EDT se asignarán automáticamente a las actividades según su jerarquía. 
3.2.2.2 Se mostrará la jerarquía completa de actividades con su numeración EDT. 
3.2.3 Se podrán crear subactividades dentro de actividades existentes.   
3.2.4 Será posible establecer fechas planificadas para actividades.  
3.2.5 Se podrá cambiar el estado de las actividades a PLANIFICADA, EN_EJECUCION o COMPLETADA.  
3.2.5.1 Será posible activar una actividad, lo que cambiará su estado a "EN_EJECUCION".  
3.2.5.2 Será posible desactivar una actividad, lo que cambiará su estado a "COMPLETADA". 
3.2.5.3 Será posible visualizar el historial de cambios en el estado de cada actividad.
3.2.6 Será posible asignar responsables a cada actividad.  
3.2.7 Se podrán registrar comentarios o notas en cada actividad para facilitar el seguimiento.    
3.2.8 Se podrán establecer dependencias entre actividades para reflejar relaciones de precedencia.  
3.2.9 Será posible reprogramar actividades en función de cambios en las fechas planificadas.       

## 4. Requisitos No Funcionales

4.1 El sistema estará implementado en lenguaje Java para garantizar portabilidad y robustez.  

## 5. Requisitos del Sistema

5.1 La gestión de portafolio y la gestión de proyectos individuales estarán integradas.  
5.2 Se ofrecerá una representación visual de la estructura EDT de los proyectos.  
5.3 Será posible gestionar múltiples proyectos simultáneamente.  

## 6. Reglas de Negocio

6.1 La fecha de inicio no podrá ser posterior a la fecha de fin en proyectos y actividades.  
6.2 Para finalizar un proyecto, todas sus actividades deberán estar completadas.  
6.3 No se podrá activar una actividad que ya está "COMPLETADA".  
6.4 No se podrá desactivar una actividad que no está "EN_EJECUCION".  
6.5 Los números EDT reflejarán la estructura jerárquica de las actividades. 


## Matriz de Trazabilidad
| **Código** | **Descripción**                                                                 | **Categoría**           | **Asociaciones con otros requisitos** | **Prioridad** |
|------------|---------------------------------------------------------------------------------|-------------------------|---------------------------------------|---------------|
| 1.1        | Gestión de portafolios con estructura jerárquica                                | Negocio                 | 5.1                                   | Alta          |
| 1.2        | Planificación y seguimiento mediante EDT                                        | Negocio                 | 2.3, 3.2.2, 5.2                      | Alta          |
| 1.3        | Todas las actividades completadas para finalizar proyecto                       | Negocio                 | 2.6, 3.1.4, 6.2                      | Alta          |
| 1.4        | Control de tiempos planificados y reales                                        | Negocio                 | 2.2, 3.1.2, 3.2.4                    | Media         |
| 1.5        | Gestión del presupuesto asignado a proyectos                                    | Negocio                 | 2.1, 3.1.1                           | Media         |
| 2.1        | Crear proyectos con nombre, horas estimadas y presupuesto                       | Usuario                 | 1.5, 3.1.1                           | Alta          |
| 2.2        | Planificar fechas de inicio y fin para proyectos                                | Usuario                 | 1.4, 3.1.2                           | Alta          |
| 2.3        | Crear actividades organizadas en EDT                                            | Usuario                 | 1.2, 3.2.1, 3.2.2                    | Alta          |
| 2.4        | Visualizar jerarquía completa de actividades                                    | Usuario                 | 3.2.2.2, 5.2                         | Media         |
| 2.5        | Activar o desactivar actividades para reflejar estado                           | Usuario                 | 3.2.5, 3.2.5.1, 3.2.5.2              | Alta          |
| 2.6        | Finalizar proyecto solo si todas las actividades completadas                    | Usuario                 | 1.3, 3.1.4, 6.2                      | Alta          |
| 3.1.1      | Crear proyecto con nombre, horas estimadas y presupuesto                        | Funcional               | 1.5, 2.1, 3.1.1.1                    | Alta          |
| 3.1.1.1    | Estado inicial del proyecto: PLANIFICADO                                        | Funcional               | 3.1.1                                 | Media         |
| 3.1.2      | Establecer fechas planificadas y pasar a EN_CURSO                               | Funcional               | 1.4, 2.2, 6.1                        | Alta          |
| 3.1.3      | Estado del proyecto según estado de actividades                                 | Funcional               | 3.2.5                                 | Alta          |
| 3.1.4      | Finalizar proyecto solo si todas las actividades completadas                    | Funcional               | 1.3, 2.6, 6.2                        | Alta          |
| 3.2.1      | Crear actividades principales dentro de un proyecto                            | Funcional               | 2.3, 3.2.1.1, 3.2.6                   | Alta          |
| 3.2.1.1    | Estado inicial de la actividad: PLANIFICADO                                     | Funcional               | 3.2.1                                 | Media         |
| 3.2.2      | Creación de jerarquías en actividades                                           | Funcional               | 1.2, 2.3, 3.2.2.1, 3.2.2.2, 3.2.3    | Alta          |
| 3.2.2.1    | Asignación automática de números EDT según jerarquía                            | Funcional               | 3.2.2, 6.5                           | Media         |
| 3.2.2.2    | Mostrar jerarquía completa con numeración EDT                                   | Funcional               | 2.4, 3.2.2, 5.2                      | Media         |
| 3.2.3      | Crear subactividades dentro de actividades existentes                           | Funcional               | 3.2.2                                 | Alta          |
| 3.2.4      | Establecer fechas planificadas para actividades                                 | Funcional               | 1.4, 6.1                             | Alta          |
| 3.2.5      | Cambiar estado de actividades (PLANIFICADA, EN_EJECUCION, COMPLETADA)           | Funcional               | 2.5, 3.1.3, 3.2.5.1, 3.2.5.2         | Alta          |
| 3.2.5.1    | Activar actividad (cambiar a EN_EJECUCION)                                      | Funcional               | 2.5, 3.2.5, 6.3                      | Alta          |
| 3.2.5.2    | Desactivar actividad (cambiar a COMPLETADA)                                     | Funcional               | 2.5, 3.2.5, 6.4                      | Media         |
| 3.2.5.3    | Visualizar historial de cambios de estado de actividades                        | Funcional               |                                       | Media         |
| 3.2.6      | Asignar responsables a cada actividad                                           | Funcional               | 3.2.1                                 | Media         |
| 3.2.7      | Registrar comentarios o notas en actividades                                   | Funcional               |                                       | Media         |
| 3.2.8      | Establecer dependencias entre actividades                                       | Funcional               |                                       | Media         |
| 3.2.9      | Reprogramar actividades según cambios en fechas                                 | Funcional               |                                       | Media         |
| 4.1        | Sistema implementado en Java para portabilidad y robustez                       | No Funcional            |                                       | Media         |
| 5.1        | Integración de gestión de portafolio y proyectos individuales                   | Sistema                 | 1.1                                   | Alta          |
| 5.2        | Representación visual de la estructura EDT                                      | Sistema                 | 1.2, 2.4, 3.2.2.2                    | Media         |
| 5.3        | Gestionar múltiples proyectos simultáneamente                                   | Sistema                 |                                       | Alta          |
| 6.1        | Fecha de inicio no posterior a fecha de fin en proyectos y actividades         | Reglas de Negocio       | 3.1.2, 3.2.4                         | Alta          |
| 6.2        | Finalizar proyecto solo si todas las actividades completadas                    | Reglas de Negocio       | 1.3, 2.6, 3.1.4                      | Alta          |
| 6.3        | No activar actividad que ya está COMPLETADA                                     | Reglas de Negocio       | 3.2.5.1                              | Media         |
| 6.4        | No desactivar actividad que no está EN_EJECUCION                                | Reglas de Negocio       | 3.2.5.2                              | Media         |
| 6.5        | Números EDT reflejan estructura jerárquica de actividades                       | Reglas de Negocio       | 3.2.2.1                              | Media         |