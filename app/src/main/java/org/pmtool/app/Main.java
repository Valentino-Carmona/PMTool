package org.pmtool.app;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.pmtool.manager.GerentePortafolio;
import org.pmtool.manager.GerenteProyecto;
import org.pmtool.model.Actividad;
import org.pmtool.model.Proyecto;

public class Main {
    public static void main(String[] args) {

        /** ========================================================================
         *                              Proyecto Ejemplo
         *  ========================================================================
         */ 
        proyectoEjemplo();
        
        /** ========================================================================
         *                              Proyecto CC1001 - Coca Cola Chatbot
         *  ========================================================================
         */ 
        cocaColaChatbotEjemplo();

         /** ========================================================================
         *                              Proyecto ML2002 - ML Agents
         *  ========================================================================
         */
        mlAgentsEjemplo();
    }

    private static String formatearFecha(LocalDate fecha) {
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return fecha != null ? fecha.format(formatoFecha) : "No definida";
    }

    private static void mostrarActividadConJerarquia(Actividad actividad, String prefix, String numeracion) {
        String fechaInicio = formatearFecha(actividad.getFechaInicioPlanificada());
        String fechaFin = formatearFecha(actividad.getFechaFinPlanificada());
        
        System.out.println(prefix + numeracion + ". " + actividad.getNombre() + 
                          " (" + fechaInicio + " - " + fechaFin + ")");
        
        int subContador = 1;
        for (Actividad subactividad : actividad.getSubactividades()) {
            String nuevaNumeracion = numeracion + "." + subContador;
            mostrarActividadConJerarquia(subactividad, prefix + "   ", nuevaNumeracion);
            subContador++;
        }
    }

    private static void mostrarProyectoFormateado(Proyecto proyecto) {
        System.out.println("\nProyecto: " + proyecto.getNombre());
        System.out.println("Fecha de inicio: " + formatearFecha(proyecto.getFechaInicioPlanificada()));
        System.out.println("Fecha de fin estimada: " + formatearFecha(proyecto.getFechaFinPlanificada()));
        System.out.println("Actividades:");
        
        int contador = 1;
        for (Actividad actividad : proyecto.getActividades()) {
            mostrarActividadConJerarquia(actividad, "", String.valueOf(contador));
            contador++;
        }
    }

    private static void proyectoEjemplo() {
        GerentePortafolio gerentePortafolio = new GerentePortafolio();
        GerenteProyecto gerenteProyecto = new GerenteProyecto();

        // === PROYECTO ===
        Proyecto proyecto = gerentePortafolio.crearProyecto("Proyecto Ejemplo", 500, 100000);
        LocalDate fechaInicioProyecto = LocalDate.of(2025, 9, 1);
        
        // === ACTIVIDADES ===
        Actividad planificacion = gerenteProyecto.crearActividad(proyecto, "Planificación del proyecto", 0);
        Actividad definicionReq = gerenteProyecto.crearSubActividad(planificacion, "Definir requisitos", 3);
        Actividad disenoUI = gerenteProyecto.crearSubActividad(planificacion, "Diseñar la UI/UX", 2);

        Actividad desarrollo = gerenteProyecto.crearActividad(proyecto, "Desarrollo", 0);
        Actividad implementacion = gerenteProyecto.crearSubActividad(desarrollo, "Implementar funcionalidad", 5);
        Actividad entorno = gerenteProyecto.crearSubActividad(desarrollo, "Preparar entorno de despliegue", 2);
        
        Actividad cierre = gerenteProyecto.crearActividad(proyecto, "Cierre del proyecto", 0);
        Actividad despliegue = gerenteProyecto.crearSubActividad(cierre, "Desplegar aplicación", 1);
        Actividad capacitacion = gerenteProyecto.crearSubActividad(cierre, "Capacitar a usuarios", 2);

        // === CONFIGURACIÓN DE DEPENDENCIAS ===
        gerenteProyecto.configurarDependencia(disenoUI, definicionReq,  0); // Diseño depende de Definir requisitos
        gerenteProyecto.configurarDependencia(implementacion, disenoUI,  0); // Implementación depende de Diseño
        gerenteProyecto.configurarDependencia(entorno, definicionReq,  2); // Entorno depende de Definir requisitos con 2 días de retraso
        gerenteProyecto.configurarDependencia(despliegue, entorno,  0); // Despliegue depende de Entorno
        gerenteProyecto.configurarDependencia(capacitacion, despliegue,  -1); // Capacitación depende de Despliegue con 1 día de adelanto

        // === PLANIFICACION DEL PROYECTO ===
        gerenteProyecto.planificarProyecto(proyecto, fechaInicioProyecto);

        // Mostrar resultados con formato mejorado
        mostrarProyectoFormateado(proyecto);
    }

    private static void cocaColaChatbotEjemplo() {
        /*
        | EDT   | Nombre de la Actividad         | Padre (EDT) | Predecesora (FS) | Lead/Lag (días) | Duración (días) |
        |-------|--------------------------------|-------------|----------------- |-----------------|-----------------|
        | 1     | Planificación del Chatbot      | -           | -                | -               | -               |
        | 1.1   | Definir Requisitos del Chatbot | -           | -                | -               | -               |
        | 1.1.1 | Reunión con Stakeholders       | 1.1         | -                | -               | 2               |
        | 1.1.2 | Documentar Requisitos          | 1.1         | 1.1.1            | 1               | 2               |
        | 1.2   | Diseño Técnico                 | 1           | -                | -               | -               |
        | 1.2.1 | Diseño Base de Datos           | 1.2         | 1.1.2            | 0               | 3               |
        | 1.2.2 | Diseño API                     | 1.2         | 1.2.1            | -1              | 2               |
        | 2     | Desarrollo del Chatbot         | -           | -                | -               | -               |
        | 2.1   | Implementación Backend         | 2           | -                | -               | -               |
        | 2.1.1 | Configurar Servidores          | 2.1         | 1.2.2            | 1               | 1               |
        | 2.1.2 | Desarrollar Lógica del Chatbot | 2.1         | 1.1.1            | 0               | 4               |
        | 2.2   | Implementación Frontend        | 2           | -                | -               | -               |
        | 2.2.1 | Diseñar Interfaz de Usuario    | 2.2         | 1.2.1            | 0               | 3               |
        | 2.2.2 | Integrar con Backend           | 2.2         | 2.2.1            | -1              | 2               |
        | 3     | Pruebas y Validación           | -           | -                | -               | -               |
        | 3.1   | Pruebas Unitarias              | 3           | -                | -               | -               |
        | 3.1.1 | Pruebas Backend                | 3.1         | 2.2.2            | 0               | 2               |
        | 3.1.2 | Pruebas Frontend               | 3.1         | 3.1.1            | 1               | 1               |
        | 3.2   | Pruebas de Integración         | 3           | 3.1              | 0               | 4               |
        | 4     | Despliegue y Lanzamiento       | -           | -                | -               | -               |
        | 4.1   | Preparar Entorno de Producción | 4           | 3.2              | 0               | 2               |
        | 4.2   | Lanzamiento Controlado         | 4           | 4.1              | -1              | 1               |
        */

        GerentePortafolio gerentePortafolio = new GerentePortafolio();
        GerenteProyecto gerenteProyecto = new GerenteProyecto();

        // === PROYECTO ===
        Proyecto proyecto = gerentePortafolio.crearProyecto("CC1001 - Coca Cola Chatbot", 1000, 250000);
        
        // === ACTIVIDADES ===
        Actividad planificacion = gerenteProyecto.crearActividad(proyecto, "Planificación del Chatbot", 0);
        Actividad definirReq = gerenteProyecto.crearSubActividad(planificacion, "Definir Requisitos del Chatbot", 0);
        Actividad reunionStake = gerenteProyecto.crearSubActividad(definirReq, "Reunión con Stakeholders", 2);
        Actividad docReq = gerenteProyecto.crearSubActividad(definirReq, "Documentar Requisitos", 2);

        Actividad disenoTec = gerenteProyecto.crearSubActividad(planificacion, "Diseño Técnico", 0);
        Actividad disenoBD = gerenteProyecto.crearSubActividad(disenoTec, "Diseño Base de Datos", 3);
        Actividad disenoAPI = gerenteProyecto.crearSubActividad(disenoTec, "Diseño API", 2);

        Actividad desarrollo = gerenteProyecto.crearActividad(proyecto, "Desarrollo del Chatbot", 0);
        Actividad backend = gerenteProyecto.crearSubActividad(desarrollo, "Implementación Backend", 0);
        Actividad configServ = gerenteProyecto.crearSubActividad(backend, "Configurar Servidores", 1);
        Actividad logicaChatbot = gerenteProyecto.crearSubActividad(backend, "Desarrollar Lógica del Chatbot", 4);

        Actividad frontend = gerenteProyecto.crearSubActividad(desarrollo, "Implementación Frontend", 0);
        Actividad disenoUI = gerenteProyecto.crearSubActividad(frontend, "Diseñar Interfaz de Usuario", 3);
        Actividad integrarBackend = gerenteProyecto.crearSubActividad(frontend, "Integrar con Backend", 2);

        Actividad pruebas = gerenteProyecto.crearActividad(proyecto, "Pruebas y Validación", 0);
        Actividad unitarias = gerenteProyecto.crearSubActividad(pruebas, "Pruebas Unitarias", 0);
        Actividad backendTest = gerenteProyecto.crearSubActividad(unitarias, "Pruebas Backend", 2);
        Actividad frontendTest = gerenteProyecto.crearSubActividad(unitarias, "Pruebas Frontend", 1);
        Actividad integracion = gerenteProyecto.crearSubActividad(pruebas, "Pruebas de Integración", 4);

        Actividad despliegue = gerenteProyecto.crearActividad(proyecto, "Despliegue y Lanzamiento", 0);
        Actividad prod = gerenteProyecto.crearSubActividad(despliegue, "Preparar Entorno de Producción", 2);
        Actividad lanzamiento = gerenteProyecto.crearSubActividad(despliegue, "Lanzamiento Controlado", 1);

        // === CONFIGURACIÓN DE DEPENDENCIAS ===
        gerenteProyecto.configurarDependencia(docReq, reunionStake, 1);         // 1.1.2 depende de 1.1.1 con +1 día
        gerenteProyecto.configurarDependencia(disenoBD, docReq, 0);             // 1.2.1 depende de 1.1.2
        gerenteProyecto.configurarDependencia(disenoAPI, disenoBD, -1);                 // 1.2.2 depende de 1.2.1

        gerenteProyecto.configurarDependencia(configServ, disenoAPI, 1);        // 2.1.1 depende de 1.2.2
        gerenteProyecto.configurarDependencia(logicaChatbot, reunionStake, 0);  // 2.1.2 depende de 1.1.1

        gerenteProyecto.configurarDependencia(disenoUI, disenoBD, 0);           // 2.2.1 depende de 1.2.1
        gerenteProyecto.configurarDependencia(integrarBackend, disenoUI, -1);           // 2.2.2 depende de 2.2.1 con -1 día

        gerenteProyecto.configurarDependencia(backendTest, integrarBackend, 0); // 3.1.1 depende de 2.2.2
        gerenteProyecto.configurarDependencia(frontendTest, backendTest, 1);    // 3.1.2 depende de 3.1.1 con +1 día
        gerenteProyecto.configurarDependencia(integracion, unitarias, 4);       // 3.2 depende de 3.1


        gerenteProyecto.configurarDependencia(prod, integracion, 0); // 4.1 depende de 3.2
        gerenteProyecto.configurarDependencia(lanzamiento, prod, -1);        // 4.2 depende de 4.1 con -1 día
        
        // === PLANIFICACION DEL PROYECTO ===
        gerenteProyecto.planificarProyecto(proyecto, LocalDate.of(2025, 9, 1));

        // Mostrar resultados con formato mejorado
        mostrarProyectoFormateado(proyecto);
    }

    private static void mlAgentsEjemplo() {
        /*
            | EDT   | Nombre de la Actividad                 | Padre (EDT) | Predecesora (FS) | Lead/Lag | Duración |
            |-------|----------------------------------------|-------------|----------------- |----------|----------|
            | 1     | Planificación de Agentes Inteligentes  | -           | -                | -        | -        |
            | 1.1   | Definición de Modelos                  | 1           | -                | -        | -        |
            | 1.1.1 | Reunión con Equipo de Ciencia de Datos | 1.1         | -                | -        | 2        |
            | 1.1.2 | Selección de Algoritmos                | 1.1         | 1.1.1            | +1       | 3        |
            | 1.1.3 | Documentación Técnica                  | 1.1         | 1.1.2            | 0        | 2        |
            | 1.2   | Diseño Arquitectónico                  | 1           | -                | -        | -        |
            | 1.2.1 | Diseño del Pipeline de Datos           | 1.2         | 1.1.3            | 0        | 4        |
            | 1.2.2 | Diseño de la API de Inferencia         | 1.2         | 1.2.1            | -1       | 3        |
            | 1.2.3 | Diseño del Módulo de Monitoreo         | 1.2         | 1.2.2            | +2       | 2        |
            | 2     | Desarrollo de Agentes                  | -           | -                | -        | -        |
            | 2.1   | Implementación del Modelo              | 2           | -                | -        | -        |
            | 2.1.1 | Preprocesamiento de Datos              | 2.1         | 1.2.1            | 0        | 5        |
            | 2.1.2 | Entrenamiento del Modelo               | 2.1         | 2.1.1            | 0        | 5        |
            | 2.1.3 | Validación del Modelo                  | 2.1         | 2.1.2            | -1       | 3        |
            | 2.1.4 | Optimización de Hiperparámetros        | 2.1         | 2.1.3            | +1       | 4        |
            | 2.2   | Implementación del Frontend            | 2           | -                | -        | -        |
            | 2.2.1 | Diseño de la Interfaz de Control       | 2.2         | 1.2.2            | 0        | 3        |
            | 2.2.2 | Desarrollo del Dashboard               | 2.2         | 2.2.1            | -1       | 4        |
            | 2.2.3 | Integración con Backend                | 2.2         | 2.2.2            | +1       | 2        |
            | 2.2.4 | Pruebas de Usabilidad                  | 2.2         | 2.2.3            | 0        | 2        |
            | 3     | Pruebas y Validación                   | -           | -                | -        | -        |
            | 3.1   | Pruebas de Rendimiento                 | 3           | -                | -        | -        |
            | 3.1.1 | Pruebas de Latencia                    | 3.1         | 2.2.4            | 0        | 3        |
            | 3.1.2 | Pruebas de Escalabilidad               | 3.1         | 3.1.1            | +1       | 4        |
            | 3.1.3 | Pruebas de Precisión                   | 3.1         | 3.1.2            | -2       | 2        |
            | 3.2   | Pruebas de Seguridad                   | 3           | -                | -        | -        |
            | 3.2.1 | Auditoría de Código                    | 3.2         | 2.1.4            | 0        | 3        |
            | 3.2.2 | Pruebas de Inyección de Datos          | 3.2         | 3.2.1            | 0        | 2        |
            | 4     | Despliegue en Producción               | -           | -                | -        | -        |
            | 4.1   | Preparación de Entorno                 | 4           | -                | -        | -        |
            | 4.1.1 | Configuración de Servidores Cloud      | 4.1         | 3.1.3            | 0        | 3        |
            | 4.1.2 | Despliegue del Modelo                  | 4.1         | 4.1.1            | -1       | 2        |
            | 4.1.3 | Despliegue del Frontend                | 4.1         | 4.1.2            | +1       | 1        |
            | 4.2   | Lanzamiento                            | 4           | -                | -        | -        |
            | 4.2.1 | Lanzamiento en Fase Beta               | 4.2         | 4.1.3            | 0        | 2        |
            | 4.2.2 | Monitoreo Inicial                      | 4.2         | 4.2.1            | -1       | 3        |
            | 4.2.3 | Lanzamiento Global                     | 4.2         | 4.2.2            | +2       | 1        |
        */

        GerentePortafolio gerentePortafolio = new GerentePortafolio();
        GerenteProyecto gerenteProyecto = new GerenteProyecto();

        // === PROYECTO ===
        Proyecto proyecto = gerentePortafolio.crearProyecto("ML2002 - ML Agents", 900, 180000);

        // === ACTIVIDADES ===
        Actividad planificacion = gerenteProyecto.crearActividad(proyecto, "Planificación de Agentes Inteligentes", 0);
        Actividad definicionModelos = gerenteProyecto.crearSubActividad(planificacion, "Definición de Modelos", 0);
        Actividad reunionCiencia = gerenteProyecto.crearSubActividad(definicionModelos, "Reunión con Equipo de Ciencia de Datos", 2);
        Actividad seleccionAlg = gerenteProyecto.crearSubActividad(definicionModelos, "Selección de Algoritmos", 3);
        Actividad docTecnica = gerenteProyecto.crearSubActividad(definicionModelos, "Documentación Técnica", 2);

        Actividad disenoArq = gerenteProyecto.crearSubActividad(planificacion, "Diseño Arquitectónico", 0);
        Actividad disenoPipeline = gerenteProyecto.crearSubActividad(disenoArq, "Diseño del Pipeline de Datos", 4);
        Actividad disenoApiInf = gerenteProyecto.crearSubActividad(disenoArq, "Diseño de la API de Inferencia", 3);
        Actividad disenoModuloMon = gerenteProyecto.crearSubActividad(disenoArq, "Diseño del Módulo de Monitoreo", 2);

        Actividad desarrollo = gerenteProyecto.crearActividad(proyecto, "Desarrollo de Agentes", 0);
        Actividad impModelo = gerenteProyecto.crearSubActividad(desarrollo, "Implementación del Modelo", 0);
        Actividad preprocDatos = gerenteProyecto.crearSubActividad(impModelo, "Preprocesamiento de Datos", 5);
        Actividad entrenamiento = gerenteProyecto.crearSubActividad(impModelo, "Entrenamiento del Modelo", 5);
        Actividad validacion = gerenteProyecto.crearSubActividad(impModelo, "Validación del Modelo", 3);
        Actividad optimizacion = gerenteProyecto.crearSubActividad(impModelo, "Optimización de Hiperparámetros", 4);

        Actividad impFrontend = gerenteProyecto.crearSubActividad(desarrollo, "Implementación del Frontend", 0);
        Actividad disenoInterfaz = gerenteProyecto.crearSubActividad(impFrontend, "Diseño de la Interfaz de Control", 3);
        Actividad desarrolloDashboard = gerenteProyecto.crearSubActividad(impFrontend, "Desarrollo del Dashboard", 4);
        Actividad integracionBackend = gerenteProyecto.crearSubActividad(impFrontend, "Integración con Backend", 2);
        Actividad pruebasUsabilidad = gerenteProyecto.crearSubActividad(impFrontend, "Pruebas de Usabilidad", 2);

        Actividad pruebas = gerenteProyecto.crearActividad(proyecto, "Pruebas y Validación", 0);
        Actividad pruebasRendimiento = gerenteProyecto.crearSubActividad(pruebas, "Pruebas de Rendimiento", 0);
        Actividad pruebasLatencia = gerenteProyecto.crearSubActividad(pruebasRendimiento, "Pruebas de Latencia", 3);
        Actividad pruebasEscalabilidad = gerenteProyecto.crearSubActividad(pruebasRendimiento, "Pruebas de Escalabilidad", 4);
        Actividad pruebasPrecision = gerenteProyecto.crearSubActividad(pruebasRendimiento, "Pruebas de Precisión", 2);
        Actividad pruebasSeguridad = gerenteProyecto.crearSubActividad(pruebas, "Pruebas de Seguridad", 0);
        Actividad auditoriaCodigo = gerenteProyecto.crearSubActividad(pruebasSeguridad, "Auditoría de Código", 3);
        Actividad pruebasInyeccion = gerenteProyecto.crearSubActividad(pruebasSeguridad, "Pruebas de Inyección de Datos", 2);

        Actividad despliegue = gerenteProyecto.crearActividad(proyecto, "Despliegue en Producción", 0);
        Actividad prepEntorno = gerenteProyecto.crearSubActividad(despliegue, "Preparación de Entorno", 0);
        Actividad configServCloud = gerenteProyecto.crearSubActividad(prepEntorno, "Configuración de Servidores Cloud", 3);
        Actividad despliegueModelo = gerenteProyecto.crearSubActividad(prepEntorno, "Despliegue del Modelo", 2);
        Actividad despliegueFrontend = gerenteProyecto.crearSubActividad(prepEntorno, "Despliegue del Frontend", 1);

        Actividad lanzamiento = gerenteProyecto.crearSubActividad(despliegue, "Lanzamiento", 0);
        Actividad lanzamientoBeta = gerenteProyecto.crearSubActividad(lanzamiento, "Lanzamiento en Fase Beta", 2);
        Actividad monitoreoInicial = gerenteProyecto.crearSubActividad(lanzamiento, "Monitoreo Inicial", 3);
        Actividad lanzamientoGlobal = gerenteProyecto.crearSubActividad(lanzamiento, "Lanzamiento Global", 1);

        // === CONFIGURACIÓN DE DEPENDENCIAS ===
        gerenteProyecto.configurarDependencia(seleccionAlg, reunionCiencia, 1);     // 1.1.2 depende de 1.1.1 con +1 día
        gerenteProyecto.configurarDependencia(docTecnica, seleccionAlg, 0);         // 1.1.3 depende de 1.1.2
        gerenteProyecto.configurarDependencia(disenoPipeline, docTecnica, 0);       // 1.2.1 depende de 1.1.3
        gerenteProyecto.configurarDependencia(disenoApiInf, disenoPipeline, -1);            // 1.2.2 depende de 1.2.1 con -1 día
        gerenteProyecto.configurarDependencia(disenoModuloMon, disenoApiInf, 2);    // 1.2.3 depende de 1.2.2 con +2 días

        gerenteProyecto.configurarDependencia(preprocDatos, disenoPipeline, 0);     // 2.1.1 depende de 1.2.1
        gerenteProyecto.configurarDependencia(entrenamiento, preprocDatos, 0);      // 2.1.2 depende de 2.1.1
        gerenteProyecto.configurarDependencia(validacion, entrenamiento, -1);               // 2.1.3 depende de 2.1.2 con -1 día
        gerenteProyecto.configurarDependencia(optimizacion, validacion, 1);         // 2.1.4 depende de 2.1.3 con +1 día

        gerenteProyecto.configurarDependencia(disenoInterfaz, disenoApiInf, 0);     // 2.2.1 depende de 1.2.2
        gerenteProyecto.configurarDependencia(desarrolloDashboard, disenoInterfaz, -1);     // 2.2.2 depende de 2.2.1 con -1 día
        gerenteProyecto.configurarDependencia(integracionBackend, desarrolloDashboard, 1);  // 2.2.3 depende de 2.2.2 con +1 día
        gerenteProyecto.configurarDependencia(pruebasUsabilidad, integracionBackend, 0);    // 2.2.4 depende de 2.2.3

        gerenteProyecto.configurarDependencia(pruebasLatencia, pruebasUsabilidad, 0);       // 3.1.1 depende de 2.2.4
        gerenteProyecto.configurarDependencia(pruebasEscalabilidad, pruebasLatencia, 1);    // 3.1.2 depende de 3.1.1 con +1 día
        gerenteProyecto.configurarDependencia(pruebasPrecision, pruebasEscalabilidad, -2);          // 3.1.3 depende de 3.1.2 con -2 días

        gerenteProyecto.configurarDependencia(auditoriaCodigo, optimizacion, 0);            // 3.2.1 depende de 2.1.4
        gerenteProyecto.configurarDependencia(pruebasInyeccion, auditoriaCodigo, 0);        // 3.2.2 depende de 3.2.1

        gerenteProyecto.configurarDependencia(configServCloud, pruebasPrecision, 0);        // 4.1.1 depende de 3.1.3
        gerenteProyecto.configurarDependencia(despliegueModelo, configServCloud, -1);           // 4.1.2 depende de 4.1.1 con -1 día
        gerenteProyecto.configurarDependencia(despliegueFrontend, despliegueModelo, 1); // 4.1.3 depende de 4.1.2 con +1 día

        gerenteProyecto.configurarDependencia(lanzamientoBeta, despliegueFrontend, 0);  // 4.2.1 depende de 4.1.3
        gerenteProyecto.configurarDependencia(monitoreoInicial, lanzamientoBeta, -1);           // 4.2.2 depende de 4.2.1 con -1 día
        gerenteProyecto.configurarDependencia(lanzamientoGlobal, monitoreoInicial, 2);  // 4.2.3 depende de 4.2.2 con +2 días

        // === PLANIFICACION DEL PROYECTO ===
        gerenteProyecto.planificarProyecto(proyecto, LocalDate.of(2025, 10, 1));
        
        // Mostrar resultados con formato mejorado
        mostrarProyectoFormateado(proyecto); 
    }
        
}
