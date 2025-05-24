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
3.1.1.1 Cuando se crea un proyecto, el estado inicial de este será "PLANIFICADO".  
3.1.2 Se podrán establecer fechas planificadas de inicio y fin para proyectos, lo que hará que automáticamente pase al estado "EN_CURSO".  
3.1.3 El estado del proyecto se actualizará automáticamente según el estado de sus actividades.  
3.1.4 Un proyecto solo podrá estar como "FINALIZADO" cuando todas sus actividades estén con el estado "COMPLETADA".  
3.1.5 No se podrán crear proyectos con nombres duplicados.  
3.1.6 Se asignará automáticamente un código único a cada proyecto creado.  

3.2 **Gestión de Actividades**
3.2.1 Será posible crear actividades principales con un nombre y horas estimadas dentro de un proyecto.  
3.2.1.1 Cuando se crea una actividad, el estado inicial de esta será "PLANIFICADA".  
3.2.2 El sistema permitirá la creación de jerarquías en las actividades.  
3.2.2.1 Los números EDT se asignarán automáticamente a las actividades según su jerarquía y serán únicos.  
3.2.2.2 Será posible consultar la jerarquía completa de actividades de un proyecto, mostrando los atributos: número EDT, nombre, estado, fechas planificadas, fechas reales y horas estimadas.  
3.2.3 Será posible crear subactividades dentro de actividades existentes.  
3.2.4 Será posible establecer fechas planificadas de inicio y fin para actividades.  
3.2.5 Será posible cambiar el estado de las actividades (PLANIFICADA, EN_EJECUCION, COMPLETADA).  
3.2.5.1 Activar actividad (cambiar a EN_EJECUCION).  
3.2.5.1.1 La fecha real de inicio se registrará automáticamente al activar una actividad.  
3.2.5.2 Desactivar actividad (cambiar a COMPLETADA).  
3.2.5.2.1 La fecha real de finalización se registrará automáticamente al completar una actividad.  
3.2.6 Será posible asignar responsables a cada actividad.  
3.2.7 Será posible establecer dependencias entre actividades con tipos específicos y retrasos/adelantos.  
3.2.7.1 La fecha de inicio de la actividad dependiente se actualizará automáticamente según la dependencia definida.
3.2.7.2 Será posible establecer dependencias entre actividades con tipo Finish to Start.
3.2.7.3 Será posible establecer dependencias entre actividades con tipo Start to Start.
3.2.7.4 Será posible establecer dependencias entre actividades con tipo Start to Finish. 
3.2.7.5 Será posible establecer dependencias entre actividades con tipo Finish to Finish.
3.2.7.6 Será posible establecer, en una misma dependencia, un tiempo de espera entre una actividad y otra.
3.2.7.7 Será posible establecer, en una misma dependencia, que una actividad comience antes de que la anterior termine completamente.
3.2.8 No se podrán agregar actividades a proyectos en estado "FINALIZADO".  
3.2.9 No se podrán crear actividades o subactividades sin un nombre válido.  
3.2.10 Al consultar la jerarquía EDT de un proyecto, se mostrarán los atributos: número EDT, nombre, estado, fechas planificadas, fechas reales, y horas estimadas de cada actividad.

## 4. Requisitos No Funcionales

4.1 El sistema estará implementado en lenguaje Java para garantizar portabilidad y robustez.  

## 5. Requisitos del Sistema

5.1 Integración de gestión de portafolio y proyectos individuales.  
5.2 Representación visual de la estructura EDT.  
5.3 Será posible gestionar múltiples proyectos simultáneamente.  

## 6. Reglas de Negocio

6.1 La fecha de inicio no podrá ser posterior a la fecha de fin en proyectos y actividades.  
6.2 Para finalizar un proyecto, todas sus actividades deberán estar completadas.  
6.3 No se podrá activar una actividad que ya está "COMPLETADA".  
6.4 No se podrá desactivar una actividad que no está "EN_EJECUCION".  
6.5 Los números EDT reflejarán la estructura jerárquica de las actividades. 


| **Código** | **Descripción**                                                                 | **Categoría**           | **Asociaciones con otros requisitos** | **Prioridad** | **Historias de Dominio** | **Historia de Usuario** |
|------------|---------------------------------------------------------------------------------|-------------------------|---------------------------------------|---------------|--------------------------|--------------------------|
| 1.1        | Gestión de portafolios con estructura jerárquica                                | Negocio                 | 2.1, 3.1.1, 5.1                     | Must          | PMTool-001               | US-03-crear-proyecto.feature |
| 1.2        | Planificación y seguimiento mediante EDT                                        | Negocio                 | 2.3, 3.2.2, 3.2.2.2, 5.2            | Must          | PMTool-002               | US-09-consultar-jerarquia-edt.feature |
| 1.3        | Todas las actividades completadas para finalizar proyecto                       | Negocio                 | 2.6, 3.1.4, 6.2                     | Must          | PMTool-003               | US-06-cambiar-estado-proyecto.feature |
| 1.4        | Control de tiempos planificados y reales                                        | Negocio                 | 2.2, 3.1.2, 3.2.4, 6.1              | Should        | PMTool-002               | US-04-planificar-actividades.feature, US-06-cambiar-estado-proyecto.feature |
| 1.5        | Gestión del presupuesto asignado a proyectos                                    | Negocio                 | 2.1, 3.1.1                           | Should        | PMTool-001               | US-03-crear-proyecto.feature |
| 2.1        | Crear proyectos con nombre, horas estimadas y presupuesto                       | Usuario                 | 1.5, 3.1.1, 3.1.5, 3.1.6            | Must          | PMTool-001               | US-03-crear-proyecto.feature |
| 2.2        | Planificar fechas de inicio y fin para proyectos                                | Usuario                 | 1.4, 3.1.2, 6.1                     | Must          | PMTool-002               | US-06-cambiar-estado-proyecto.feature |
| 2.3        | Crear actividades organizadas en EDT                                            | Usuario                 | 1.2, 3.2.1, 3.2.2, 3.2.3, 3.2.9     | Must          | PMTool-002               | US-04-planificar-actividades.feature, US-08-agregar-subactividad.feature |
| 2.4        | Visualizar jerarquía completa de actividades                                    | Usuario                 | 1.2, 3.2.2.2, 3.2.10, 5.2           | Should        | PMTool-002               | US-09-consultar-jerarquia-edt.feature |
| 2.5        | Activar o desactivar actividades para reflejar estado                           | Usuario                 | 3.2.5, 3.2.5.1, 3.2.5.2, 6.3, 6.4   | Must          | PMTool-003               | US-07-activar-actividad.feature, US-05-finalizar-actividad.feature |
| 2.6        | Finalizar proyecto solo si todas las actividades completadas                    | Usuario                 | 1.3, 3.1.4, 6.2                     | Must          | PMTool-003               | US-06-cambiar-estado-proyecto.feature |
| 3.1.1      | Crear proyecto con nombre, horas estimadas y presupuesto                        | Funcional               | 1.5, 2.1, 3.1.1.1, 3.1.5, 3.1.6     | Must          | PMTool-001               | US-03-crear-proyecto.feature |
| 3.1.1.1    | Estado inicial del proyecto: PLANIFICADO                                        | Funcional               | 3.1.1                                | Should        | PMTool-001               | US-03-crear-proyecto.feature |
| 3.1.2      | Establecer fechas planificadas y pasar a EN_CURSO                               | Funcional               | 1.4, 2.2, 6.1                       | Must          | PMTool-002               | US-06-cambiar-estado-proyecto.feature |
| 3.1.3      | Estado del proyecto según estado de actividades                                 | Funcional               | 2.5, 3.2.5                           | Must          | PMTool-003               | US-06-cambiar-estado-proyecto.feature |
| 3.1.4      | Finalizar proyecto solo si todas las actividades completadas                    | Funcional               | 1.3, 2.6, 6.2                       | Must          | PMTool-003               | US-06-cambiar-estado-proyecto.feature |
| 3.1.5      | No se podrán crear proyectos con nombres duplicados                             | Funcional               | 2.1, 3.1.1                           | Must          | PMTool-001               | US-03-crear-proyecto.feature |
| 3.1.6      | Asignar automáticamente un código único a cada proyecto creado                 | Funcional               | 2.1, 3.1.1                           | Must          | PMTool-001               | US-03-crear-proyecto.feature |
| 3.2.1      | Crear actividades principales dentro de un proyecto                            | Funcional               | 2.3, 3.2.1.1, 3.2.9                 | Must          | PMTool-002               | US-04-planificar-actividades.feature |
| 3.2.1.1    | Estado inicial de la actividad: PLANIFICADA                                     | Funcional               | 3.2.1                                | Should        | PMTool-002               | US-04-planificar-actividades.feature |
| 3.2.2      | Creación de jerarquías en actividades                                           | Funcional               | 1.2, 2.3, 3.2.2.1, 3.2.2.2, 3.2.3   | Must          | PMTool-002               | US-08-agregar-subactividad.feature, US-09-consultar-jerarquia-edt.feature |
| 3.2.2.1    | Asignación automática de números EDT según jerarquía                            | Funcional               | 3.2.2, 6.5                           | Should        | PMTool-002               | US-08-agregar-subactividad.feature |
| 3.2.2.2    | Mostrar jerarquía completa con numeración EDT                                   | Funcional               | 2.4, 3.2.2, 3.2.10, 5.2             | Should        | PMTool-002               | US-09-consultar-jerarquia-edt.feature |
| 3.2.3      | Crear subactividades dentro de actividades existentes                           | Funcional               | 2.3, 3.2.2, 3.2.9                   | Must          | PMTool-002               | US-08-agregar-subactividad.feature |
| 3.2.4      | Establecer fechas planificadas para actividades                                 | Funcional               | 1.4, 2.3, 6.1                       | Must          | PMTool-002               | US-04-planificar-actividades.feature |
| 3.2.5      | Cambiar estado de actividades (PLANIFICADA, EN_EJECUCION, COMPLETADA)           | Funcional               | 2.5, 3.1.3, 3.2.5.1, 3.2.5.2, 6.3, 6.4 | Must          | PMTool-003               | US-07-activar-actividad.feature, US-05-finalizar-actividad.feature |
| 3.2.5.1    | Activar actividad (cambiar a EN_EJECUCION)                                      | Funcional               | 2.5, 3.2.5, 3.2.5.1.1, 6.3          | Must          | PMTool-003               | US-07-activar-actividad.feature |
| 3.2.5.1.1  | La fecha real de inicio se registrará automáticamente al activar una actividad  | Funcional               | 2.5, 3.2.5.1, 1.4                   | Must          | PMTool-003               | US-07-activar-actividad.feature |
| 3.2.5.2    | Desactivar actividad (cambiar a COMPLETADA)                                     | Funcional               | 2.5, 3.2.5, 3.2.5.2.1, 6.4          | Must          | PMTool-003               | US-05-finalizar-actividad.feature |
| 3.2.5.2.1  | La fecha real de finalización se registrará automáticamente al completar una actividad | Funcional               | 2.5, 3.2.5.2, 1.4                   | Must          | PMTool-003               | US-05-finalizar-actividad.feature |
| 3.2.6      | Será posible asignar responsables a cada actividad                              | Funcional               | 2.3, 3.2.1                           | Must          | PMTool-002               |  |
| 3.2.7      | Establecer dependencias entre actividades con tipos específicos y retrasos/adelantos | Funcional               | 2.3, 3.2.4, 3.2.7.1 to 3.2.7.7, 6.1 | Must          | PMTool-002               | US-10-gestionar-dependencias.feature |
| 3.2.7.1    | La fecha de inicio de la actividad dependiente se actualizará automáticamente según la dependencia definida | Funcional               | 3.2.7, 6.1                           | Must          |               | US-10-gestionar-dependencias.feature |
| 3.2.7.2    | Establecer dependencias entre actividades con tipo Finish to Start              | Funcional               | 3.2.7                                | Must          | PMTool-002               | US-10-gestionar-dependencias.feature |
| 3.2.7.3    | Establecer dependencias entre actividades con tipo Start to Start               | Funcional               | 3.2.7                                | Should        |                | US-10-gestionar-dependencias.feature |
| 3.2.7.4    | Establecer dependencias entre actividades con tipo Start to Finish              | Funcional               | 3.2.7                                | Should        |                | US-10-gestionar-dependencias.feature |
| 3.2.7.5    | Establecer dependencias entre actividades con tipo Finish to Finish             | Funcional               | 3.2.7                                | Should        |               | US-10-gestionar-dependencias.feature |
| 3.2.7.6    | Establecer, en una misma dependencia, un tiempo de espera entre una actividad y otra | Funcional               | 3.2.7                                | Must          | PMTool-002               | US-10-gestionar-dependencias.feature |
| 3.2.7.7    | Establecer, en una misma dependencia, que una actividad comience antes de que la anterior termine completamente | Funcional               | 3.2.7                                | Should        |                | US-10-gestionar-dependencias.feature |
| 3.2.8      | No se podrán agregar actividades a proyectos finalizados                       | Funcional               | 3.1.4, 2.3                           | Must          | PMTool-003               | US-04-planificar-actividades.feature |
| 3.2.9      | No se podrán crear actividades o subactividades sin un nombre válido           | Funcional               | 2.3, 3.2.1, 3.2.3                   | Must          | PMTool-002               | US-04-planificar-actividades.feature, US-08-agregar-subactividad.feature |
| 3.2.10     | Mostrar atributos al consultar la jerarquía EDT: número EDT, nombre, estado, fechas planificadas, fechas reales, horas estimadas | Funcional               | 2.4, 3.2.2.2, 5.2                   | Should        | PMTool-002               | US-09-consultar-jerarquia-edt.feature |
| 4.1        | El sistema estará implementado en lenguaje Java para garantizar portabilidad y robustez | No Funcional            | None                                  | Must          | None                     | None                     |
| 5.1        | Integración de gestión de portafolio y proyectos individuales                   | Sistema                 | 1.1, 2.1, 3.1.1                     | Must          | PMTool-001               | US-03-crear-proyecto.feature |
| 5.2        | Representación visual de la estructura EDT                                      | Sistema                 | 1.2, 2.4, 3.2.2.2, 3.2.10           | Should        | PMTool-002               | US-09-consultar-jerarquia-edt.feature |
| 5.3        | Será posible gestionar múltiples proyectos simultáneamente                      | Sistema                 | 1.1, 2.1, 3.1.1                     | Must          | PMTool-001               | US-03-crear-proyecto.feature |
