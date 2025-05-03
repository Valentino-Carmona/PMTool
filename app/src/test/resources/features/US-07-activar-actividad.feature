# Issue: 7
Feature: Activar actividad planificada
  # Como Gerente de Proyecto
  # Quiero activar una actividad planificada
  # Para registrar el inicio de su ejecución

  Scenario: Activar una actividad planificada exitosamente
    Given existe una actividad planificada lista para iniciar
    When el Gerente de Proyecto inicia la ejecución de la actividad
    Then la actividad pasa a estar en ejecución
    And se registra la fecha de inicio real de la actividad

  Scenario: Intentar activar una actividad ya completada
    Given existe una actividad que ya está completada
    When el Gerente de Proyecto inicia la ejecución de la actividad
    Then se muestra un mensaje de error indicando que la actividad está completada

  Scenario: Intentar activar una actividad ya en ejecución
    Given existe una actividad que ya está en ejecución
    When el Gerente de Proyecto inicia la ejecución de la actividad
    Then se muestra un mensaje de error indicando que la actividad ya está en ejecución