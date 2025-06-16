# Issue: 8
Feature: Agregar una subactividad a una actividad existente
  # Como Gerente de Proyecto
  # Quiero agregar una subactividad a una actividad existente
  # Para detallar las tareas necesarias para completar la actividad

  Scenario: Agregar una subactividad a una actividad planificada
    Given existe una actividad en un proyecto
    When el Gerente de Proyecto agrega una subactividad con nombre y dias estimados
    Then la subactividad se registra con un código único
    And la subactividad se asocia a la actividad padre
    And la subactividad tiene estado inicial "PLANIFICADA"

  Scenario: Intentar agregar una subactividad sin nombre
    Given existe una actividad en un proyecto
    When el Gerente de Proyecto intenta agregar una subactividad sin nombre
    Then se muestra un mensaje de error indicando que el nombre de la subactividad es obligatorio
    And no se registra la subactividad

  Scenario: Intentar agregar una subactividad a una actividad completada
    Given existe una actividad completada en un proyecto
    When el Gerente de Proyecto intenta agregar una subactividad
    Then se muestra un mensaje de error indicando que no se pueden agregar subactividades a una actividad completada