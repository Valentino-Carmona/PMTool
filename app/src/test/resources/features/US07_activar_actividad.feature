# Issue: 7
Feature: Activar actividad planificada
  # Como Gerente de Proyecto
  # Quiero activar una actividad planificada
  # Para registrar el inicio de su ejecución

  Scenario: Activar una actividad planificada
    Given existe una actividad en estado "PLANIFICADA"
    When el Gerente de Proyecto activa la actividad
    Then el estado de la actividad cambia a "EN_EJECUCION"
    And se registra la fecha real de inicio

  Scenario: Intentar activar una actividad completada
    Given existe una actividad en estado "COMPLETADA"
    When el Gerente de Proyecto intenta activar la actividad
    Then se informa un mensaje indicando que no se puede activar una actividad completada

  Scenario: Intentar activar una actividad ya en ejecución
    Given existe una actividad en estado "EN_EJECUCION"
    When el Gerente de Proyecto intenta activar la actividad
    Then se informa un mensaje indicando que la actividad ya está en ejecución