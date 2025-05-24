# Issue: 9
Feature: Consultar la jerarquía EDT de un proyecto
  # Como Gerente de Proyecto
  # Quiero consultar la jerarquía EDT de un proyecto
  # Para revisar la estructura de actividades y subactividades

  Scenario: Consultar la jerarquía EDT de un proyecto con actividades y subactividades
    Given existe un proyecto con al menos una actividad y una subactividad
    When el Gerente de Proyecto solicita la jerarquía EDT del proyecto
    Then se muestra la estructura jerárquica con las actividades y sus subactividades
    And cada actividad incluye los atributos: número EDT, nombre, estado, fechas planificadas, fechas reales, y horas estimadas

  Scenario: Consultar la jerarquía EDT de un proyecto sin actividades
    Given existe un proyecto sin actividades
    When el Gerente de Proyecto solicita la jerarquía EDT del proyecto
    Then se muestra un mensaje indicando que el proyecto no tiene actividades

  Scenario: Consultar la jerarquía EDT de un proyecto con actividades pero sin subactividades
    Given existe un proyecto con actividades pero sin subactividades
    When el Gerente de Proyecto solicita la jerarquía EDT del proyecto
    Then se muestra la estructura jerárquica con solo las actividades principales