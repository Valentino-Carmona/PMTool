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

  Scenario: Intento de finalizar actividad sin fecha real de finalización
    Given existe una actividad en estado "EN_EJECUCION"
    When el Gerente de Proyecto intenta modificar el estado a "COMPLETADA" sin proveer la fecha real de finalización
    Then se informa un mensaje indicando que falta la fecha real de finalización
    And la actividad mantiene su estado en "EN_EJECUCION"
