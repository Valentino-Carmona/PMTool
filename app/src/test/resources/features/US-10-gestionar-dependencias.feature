# Issue: 10
Feature: Gestionar dependencias entre actividades
  # Como Gerente de Proyecto
  # Quiero gestionar dependencias entre actividades
  # Para definir el orden y momento de ejecución de las actividades

  Scenario: Agregar una dependencia Fin-a-Comienzo entre dos actividades
    Given existe un proyecto con dos actividades planificadas
    When el Gerente de Proyecto define una dependencia Fin-a-Comienzo entre ellas
    Then la dependencia se registra correctamente
    And la actividad dependiente no puede comenzar hasta que la primera termine

  Scenario: Agregar una dependencia con retraso
    Given existe un proyecto con dos actividades planificadas
    When el Gerente de Proyecto define una dependencia Fin-a-Comienzo con un retraso de 2 días
    Then la dependencia se registra con el retraso especificado
    And la actividad dependiente no puede comenzar hasta 2 días después de que la primera termine

  Scenario: Intentar agregar una dependencia a una actividad completada
    Given existe un proyecto con una actividad completada y otra planificada
    When el Gerente de Proyecto intenta definir una dependencia Fin-a-Comienzo
    Then se muestra un mensaje de error indicando que no se pueden agregar dependencias a actividades completadas