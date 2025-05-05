
# Análisis de Interesados (Stakeholders) para PMTool

## Identificación y Descripción Detallada de Interesados

### Interesados

Los interesados (stakeholders) de PMTool:

1. **Gerente de Portafolio**  
   - **Descripción**: Supervisa el portafolio de proyectos, crea nuevos proyectos y asegura alineación estratégica. Gestiona métricas como presupuesto y plazos.  
   - **Origen**: Requisitos 1.1, 2.1, 5.1; Historia de Dominio PMTool-001.  
   - **Rol en PMTool**: Usa el sistema para iniciar proyectos y monitorear el portafolio.  

2. **Gerente de Proyecto**  
   - **Descripción**: Administra proyectos individuales, planifica actividades, asigna recursos y asegura cumplimiento de alcance, tiempo y presupuesto.  
   - **Origen**: Requisitos 2.2, 2.3, 2.5, 2.6, 3.1, 3.2; Historias de Dominio PMTool-002, PMTool-003.  
   - **Rol en PMTool**: Planifica y ejecuta proyectos, gestiona EDT y estados de actividades.   

3. **Recurso**  
   - **Descripción**: Miembros del equipo o contratistas asignados a actividades específicas.  
   - **Origen**: Requisito 3.2.6; Historias de Dominio PMTool-002, PMTool-003.  
   - **Rol en PMTool**: Ejecuta actividades asignadas y reporta su estado.  

4. **Organización**  
   - **Descripción**: Entidad que adopta PMTool, establece objetivos, asigna presupuestos y se beneficia de los proyectos.  
   - **Origen**: Requisitos 1.1, 1.5, 5.1; Historia de Dominio PMTool-001.  
   - **Rol en PMTool**: Define metas y provee recursos, pero no interactúa directamente con el sistema (rol indirecto).  

5. **Soporte**  
   - **Descripción**: Equipo técnico que mantiene PMTool, resuelve problemas y asegura su operatividad. 
   - **Origen**: Requisito 4.1.  
   - **Rol en PMTool**: Asegura estabilidad del sistema y resuelve problemas de los usuarios, con funciones de desarrollo y mantenimiento.

## Diagrama de Stakeholders

![Modelo de Cebolla para PMTool](./img/onion_model.png)

### Capas del Modelo y Actores

#### Capa 0: PMTool
   - **Descripción**: Sistema para gestionar portafolios, proyectos y actividades mediante una estructura de desarrollo de trabajo (EDT/WBS).
   - **Rol**: Habilita funcionalidades como creación de proyectos y gestión de actividades.
#### Capa 1: El Sistema
   - **Descripción**: Usuarios directos que interactúan con PMTool para operar portafolios, proyectos y actividades.
   - **Actores**:
      - **Gerente de Portafolio**: Crea y supervisa proyectos.
      - **Gerente de Proyecto**: Planifica, ejecuta y finaliza proyectos y gestiona EDT.
      - **Recurso**: Ejecuta actividades y reporta estados.
#### Capa 2: El Sistema Contenedor
   - **Descripción**: Entidad que provee contexto y recursos para PMTool, pero no lo usa directamente.
   - **Actores**:
      - **Organización**: Financia PMTool y define metas y presupuestos.
      - **Soporte**: Mantiene la operatividad y realiza actualizaciones del sistema.
#### Capa 3: El Entorno más amplio
   - **Descripción**: No se an encontrado interesados.
   - **Actores**: Ninguno.