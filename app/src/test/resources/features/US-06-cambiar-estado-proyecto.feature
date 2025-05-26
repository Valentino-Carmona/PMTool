# Issue: 6
Feature: Cambio de estado del proyecto
  # Como Gerente de Proyecto
  # Quiero cambiar el estado del proyecto según su progreso
  # Para reflejar su evolución

  Scenario: Cambio de estado de "Planificado" a "En curso"
    Given existe un proyecto en estado "PLANIFICADO"
    When el Gerente de Proyecto suministra una fecha real de inicio
    Then el estado del proyecto cambia a "EN_CURSO"

  Scenario: Intento marcar como finalizado sin haber planificado el proyecto
    Given existe un proyecto en estado "PLANIFICADO"
    When el Gerente de Proyecto intenta modificar el estado del proyecto a "FINALIZADO"
    Then se informa un mensaje indicando que el proyecto debe estar en curso para poder finalizarlo
    And el proyecto mantiene el estado en "PLANIFICADO"

  Scenario: El proyecto se finaliza cuando todas las actividades están completadas
    Given existe un proyecto en estado "EN_CURSO"
    And todas las actividades del proyecto están en estado "COMPLETADA"
    When el Gerente de Proyecto solicita finalizar el proyecto
    Then el estado del proyecto cambia a "FINALIZADO"
    And se registra la fecha real de finalización del proyecto

  Scenario: Intentar finalizar un proyecto con actividades incompletas
    Given existe un proyecto en estado "EN_CURSO"
    And al menos una actividad del proyecto no está en estado "COMPLETADA"
    When el Gerente de Proyecto solicita finalizar el proyecto
    Then se muestra un mensaje de error indicando que todas las actividades deben estar completadas
    And el proyecto mantiene el estado en "EN_CURSO"