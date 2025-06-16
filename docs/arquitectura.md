# Arquitectura del Sistema PMTool (Modelo C4)

Este documento describe la arquitectura de PMTool usando el modelo C4. A continuación se presentan los diagramas de contexto y contenedores.

---

## Nivel 1: Diagrama de Contexto (Context Diagram)

![Diagrama de Contexto](./img/Diagrama_De_Contexto.png)

```plantuml
@startuml
!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Context.puml

Person(GPf, "Gerente de Portafolio", "Crea y supervisa proyectos en el portafolio")
Person(GPr, "Gerente de Proyecto", "Planifica actividades, asigna responsables y monitorea avance")

System(PMTool, "PMTool", "Herramienta para gestión de proyectos y actividades jerárquicas")

Rel(GPf, PMTool, "Crea proyectos")
Rel(GPf, PMTool, "Permite monitoreo y control de los proyectos")
Rel(GPr, PMTool, "Planifica el proyecto y sus actividades")
Rel(GPr, PMTool, "Permite monitoreo y control de los proyectos y actividades")
@enduml
```

### Descripción

* **Gerente de Portafolio**: Responsable de crear y supervisar los proyectos en el portafolio.
* **Gerente de Proyecto**: Responsable de planificar las actividades, asignar responsables y monitorear el avance.
* **PMTool**: Sistema central que permite la gestión de proyectos y actividades jerárquicas.

---

## Nivel 2: Diagrama de Contenedores (Container Diagram)

![Diagrama de Contenedores](./img/Diagrama_De_Contenedores.png)

```plantuml
@startuml
!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Container.puml

Person(GPf, "Gerente de Portafolio")
Person(GPr, "Gerente de Proyecto")

System_Boundary(s1, "PMTool") {
    Container(AppService, "Capa de Aplicación", "Java", "Orquesta la lógica de negocio")
    Container(Domain, "Capa de Dominio", "Java", "Contiene las reglas del negocio y entidades")
}

GPf --> AppService : Crea y supervisa proyectos en el portafolio
GPr --> AppService : Planifica actividades, asigna responsables y monitorea avance

AppService --> Domain : Ejecuta lógica de negocio
@enduml
```

### Descripción

* **Capa de Aplicación**: Orquesta la lógica de negocio implementada en clases como GerentePortafolio y GerenteProyecto, que coordinan las operaciones con las entidades del dominio.
* **Capa de Dominio**: Contiene las reglas del negocio y las entidades del sistema como Proyecto, Actividad y las implementaciones de Dependencia.
* * **Aclaracion**: Aunque se diseña la Base de Datos, esta no se implementa, por lo que no se incluye aun en el diagrama de contenedores.

---

## Arquitectura en Capas

En esta etapa son necesarias la **Capa de Aplicación** y la **Capa de Dominio**. La arquitectura general del sistema PMTool se organiza del siguiente modo:

* **Aplicación**: Encargada de orquestar los casos de uso y coordinar las operaciones entre la lógica de dominio y la infraestructura. Implementa servicios que utilizan las entidades del dominio.
* **Dominio**: Contiene el núcleo del sistema: entidades, lógica de negocio y reglas que reflejan el modelo de dominio (`mdd.md`). Esta capa es independiente de detalles técnicos.
