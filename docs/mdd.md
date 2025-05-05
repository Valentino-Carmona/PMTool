# Modelo de Dominio de PMTool

Este documento presenta el modelo de dominio de PMTool, que describe las principales entidades, atributos y relaciones del sistema. El modelo se ha desarrollado incluyendo la gestión de portafolios, proyectos, actividades y dependencias entre actividades.

## Diagrama del Modelo de Dominio

El siguiente diagrama está en PlantUML y representa las entidades, sus atributos y las relaciones entre ellas.

```txt
@startuml

' Entidades

class Portafolio {
  idPortafolio
  nombre
}

class Proyecto {
  idProyecto
  nombre
  horasEstimadas
  presupuesto
  estado
  fechaInicio
  fechaFin
}

class Actividad {
  idActividad
  numeroEDT
  nombre
  fechaInicio
  fechaFin
  fechaInicioReal
  fechaFinReal
  horasEstimadas
  estado
  responsable
}

class Dependencia {
  idDependencia
  tipo
  lagLead
}

' Relaciones
Portafolio "1" -- "0..*" Proyecto : contiene
Proyecto "1" -- "0..*" Actividad : contiene
Actividad "1" -- "    0..*" Actividad : tiene
Actividad "1" -- "0..*  " Dependencia : tiene
Dependencia "1" -- "1 " Actividad : origen
Dependencia "1" -- "1 " Actividad : destino

note left of Actividad
  Estado: PLANIFICADA, EN_EJECUCION, COMPLETADA.
  Una actividad puede contener otras actividades 
  (subactividades) de forma jerárquica.
end note

note left of Dependencia
  Tipo: FS, SS, FF, SF.
  lagLead: tiempo de retraso o adelanto en días.
end note

@enduml
```

### Notas Explicativas

- **Portafolio**: Representa un conjunto de proyectos gestionados dentro del sistema, cumpliendo con los requisitos de negocio 1.1 y 5.1, y la historia de dominio PMTool-001. No se modelan actores como el "Gerente de Portafolio" ya que no se requiere almacenar información específica sobre ellos (según lineamientos de para el desarrollo de modelos de dominio).
- **Proyecto**: Representa un proyecto individual dentro de un portafolio, con atributos que soportan la gestión de proyectos segun los requisitos 3.1.1, 3.1.2, 3.1.1.1. La relación con `Actividad` refleja la estructura jerárquica EDT (requisito 1.2).
- **Actividad**: Representa las tareas y subactividades dentro de un proyecto, organizadas jerárquicamente a traves  de la relación reflexiva (`Actividad "1" -- "0..*" Actividad : tiene`), haciendo referencia a los requisitos 3.2.2, 3.2.3 y 3.2.2.1. Los atributos cumplen con los requisitos 3.2.4, 3.2.5.1.1, 3.2.5.2.1, tambien con la asignación de responsables, requisito 3.2.6, y estados, requisito 3.2.5.
- **Dependencia**: Modela las relaciones de precedencia entre actividades, requisito 3.2.7, incluyendo los tipos de dependencias (FS, SS, FF, SF) y retrasos/adelantos `lag` y `Lead`. La cardinalidad `"1" -- "1"` en las relaciones `origen` y `destino` significa que una dependencia siempre este vinculada con dos actividades, mientras que `Actividad "1" -- "0..*" Dependencia : tiene` refleja que no todas las actividades tienen dependencias.

### Diccionario de Datos

| **Entidad**     | **Atributo**       | **Tipo Primitivo** | **Descripción**                                                                 |
|-----------------|--------------------|--------------------|---------------------------------------------------------------------------------|
| Portafolio      | idPortafolio       | String             | Identificador único del portafolio.                                             |
| Portafolio      | nombre             | String             | Nombre del portafolio, que agrupa proyectos.                                    |
| Proyecto        | idProyecto         | String             | Identificador único del proyecto.                                               |
| Proyecto        | nombre             | String             | Nombre del proyecto.                                                            |
| Proyecto        | horasEstimadas     | Integer            | Horas estimadas para completar el proyecto.                                     |
| Proyecto        | presupuesto        | Double             | Presupuesto asignado al proyecto, en moneda local.                              |
| Proyecto        | estado             | String             | Estado del proyecto (PLANIFICADO, EN_CURSO, FINALIZADO).                        |
| Proyecto        | fechaInicio        | Date               | Fecha planificada de inicio del proyecto.                                       |
| Proyecto        | fechaFin           | Date               | Fecha planificada de finalización del proyecto.                                 |
| Actividad       | idActividad        | String             | Identificador único de la actividad.                                            |
| Actividad       | numeroEDT          | String             | Número EDT asignado automáticamente según la jerarquía (e.g., 1.1, 1.2.1).      |
| Actividad       | nombre             | String             | Nombre de la actividad.                                                         |
| Actividad       | fechaInicio        | Date               | Fecha planificada de inicio de la actividad.                                    |
| Actividad       | fechaFin           | Date               | Fecha planificada de finalización de la actividad.                              |
| Actividad       | fechaInicioReal    | Date               | Fecha real de inicio de la actividad, registrada al activarla.                  |
| Actividad       | fechaFinReal       | Date               | Fecha real de finalización de la actividad, registrada al completarla.          |
| Actividad       | horasEstimadas     | Integer            | Horas estimadas para completar la actividad.                                    |
| Actividad       | estado             | String             | Estado de la actividad (PLANIFICADA, EN_EJECUCION, COMPLETADA).                 |
| Actividad       | responsable        | String             | Nombre o identificador del recurso asignado como responsable de la actividad.   |
| Dependencia     | idDependencia      | String             | Identificador único de la dependencia.                                          |
| Dependencia     | tipo               | String             | Tipo de dependencia (FS, SS, FF, SF).                                           |
| Dependencia     | lagLead            | Integer            | Tiempo de retraso (lag) o adelanto (lead) en días entre las actividades.        |

![imagen del Modelo de Dominio](./img/mdd.png)