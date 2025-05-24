# Arquitectura del Sistema PMTool (Modelo C4)

Este documento describe la arquitectura de PMTool usando el modelo C4. A continuación se presentan los diagramas de contexto y contenedores, alineados con los artefactos `requisitos.md`, `mdd.md`, `ddd.md` e `interesados.md`.

---

## Nivel 1: Diagrama de Contexto (System Context Diagram)

![Diagrama de Contexto](./img/Diagrama_De_Contexto.png)

```plantuml
@startuml
<!-- !includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Context.puml -->

' Personas
Person(Org, "Organización", "Define metas y asigna presupuesto.")
Person(GPf, "Gerente de Portafolio", "Crea y supervisa proyectos en el portafolio")
Person(GPr, "Gerente de Proyecto", "Planifica actividades, asigna responsables y monitorea avance")
Person(Recurso, "Recurso", "Consulta y ejecuta actividades asignadas, reporta fechas reales")
Person(Soporte, "Soporte Técnico", "Mantiene la operatividad del sistema")

' Sistema central
System(PMTool, "PMTool", "Herramienta para gestión de proyectos y actividades jerárquicas")

' Flujo de interacciones
Org --> GPf : Entrega metas y presupuesto
GPf --> PMTool : Crea proyectos
GPr --> PMTool : Planifica actividades, asigna recursos
Recurso --> PMTool : Reporta estado de actividades
PMTool --> GPf : Permite monitoreo y control del portafolio
PMTool --> GPr : Permite monitoreo y control de los proyectos y actividades
PMTool --> Recurso : Muestra asignaciones y fechas
Soporte --> PMTool : Da soporte técnico
@enduml
```

### Descripción

* El sistema **PMTool** interactúa con tres usuarios clave: Gerente de Portafolio, Gerente de Proyecto y Recurso.
* La **Organización** participa en el contexto pero no usa el sistema directamente.
* **Soporte** mantiene la operatividad pero no influye en la lógica funcional.
* El sistema centraliza la gestión de proyectos y actividades, incluyendo estructura jerárquica (EDT), dependencias y fechas.

---

## Nivel 2: Diagrama de Contenedores (Container Diagram)

![Diagrama de Contenedores](./img/Diagrama_De_Contenedores.png)

```plantuml
@startuml
<!-- !includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Container.puml -->

Person(GPf, "Gerente de Portafolio")
Person(GPr, "Gerente de Proyecto")
Person(Recurso, "Recurso")

System_Boundary(s1, "PMTool") {
    Container(WebUI, "Interfaz Web", "Provee la interfaz al usuario final")
    Container(API, "API REST", "Java + Spring", "Expone servicios HTTP para interacción con la UI")
    Container(AppService, "Capa de Aplicación", "Java", "Orquesta la lógica de negocio")
    Container(Domain, "Capa de Dominio", "Java", "Contiene las reglas del negocio y entidades")
    ContainerDb(DB, "Base de Datos", "Almacena Proyectos, Actividades, Dependencias")
}

GPf --> WebUI : Usa la interfaz
GPr --> WebUI : Usa la interfaz
Recurso --> WebUI : Usa la interfaz

WebUI --> API : Solicitudes HTTP (JSON)
API --> AppService : Invoca servicios
AppService --> Domain : Ejecuta lógica de negocio
AppService --> DB : Accede a datos persistidos
@enduml
```

### Descripción

* **Interfaz Web**: Permite a los usuarios interactuar con PMTool desde navegadores.
* **API REST**: Interfaz de comunicación entre la UI y el backend.
* **Capa de Aplicación**: Orquesta servicios y flujos funcionales.
* **Capa de Dominio**: Implementa la lógica de negocio basada en el modelo de dominio (`mdd.md`).
* **Base de Datos**: Almacena las entidades clave como `Proyecto`, `Actividad`, `Dependencia`.

---

## Arquitectura en Capas

En esta etapa son necesarias la **Capa de Aplicación** y la **Capa de Dominio**. La arquitectura general del sistema PMTool se organiza del siguiente modo:

* **Presentación** (futura): Responsable de las interfaces de usuario (por ejemplo, aplicaciones web).
* **Aplicación**: Encargada de orquestar los casos de uso y coordinar las operaciones entre la lógica de dominio y la infraestructura. Implementa servicios que utilizan las entidades del dominio.
* **Dominio**: Contiene el núcleo del sistema: entidades, lógica de negocio y reglas que reflejan el modelo de dominio (`mdd.md`). Esta capa es independiente de detalles técnicos.
* **Infraestructura** (futura): Se encarga del acceso a datos, persistencia, servicios externos, etc. Incluye la base de datos  como único componente necesario en esta etapa.
