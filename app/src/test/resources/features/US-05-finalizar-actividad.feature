# Issue: 5
Feature: Finalización de actividad y su impacto en el proyecto
  # Como Gerente de Proyecto
  # Quiero marcar una actividad como finalizada
  # Para reflejar su progreso en el proyecto

  Scenario: Finalización de actividad
    Given existe una actividad en estado "EN_EJECUCION"
    When el Gerente de Proyecto informa la finalización de la actividad
    Then el estado de la actividad cambia a "COMPLETADA"
    And la fecha real de finalización se actualizo automaticamente

  Scenario: Intento de finalizar actividad sin haber iniciado la actividad
    Given existe una actividad en estado "PLANIFICADA"
    When el Gerente de Proyecto intenta modificar el estado a "COMPLETADA"
    Then se informa un mensaje indicando que la actividad debe estar EN_EJECUCION para poder finalizarla
    And la actividad mantiene su estado en "PLANIFICADA"

  Scenario: Intentar finalizar una actividad ya completada
    Given existe una actividad en estado "COMPLETADA"
    When el Gerente de Proyecto informa la finalización de la actividad
    Then se muestra un mensaje de error indicando que la actividad ya está completada
    And la actividad mantiene su estado en "COMPLETADA"