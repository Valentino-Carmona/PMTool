# Issue: 10
Feature: Gestionar dependencias entre actividades
  # Como Gerente de Proyecto
  # Quiero gestionar dependencias entre actividades
  # Para definir el orden y momento de ejecución de las actividades

  Scenario: Agregar una dependencia Fin-a-Comienzo entre dos actividades
    Given existe un proyecto con dos actividades planificadas A y B
    When el Gerente de Proyecto define una dependencia Fin-a-Comienzo desde A hacia B
    And el Gerente de Proyecto define la fecha de inicio del proyecto en el 10 de abril
    Then la dependencia se registra correctamente
    And la fecha de inicio de B se establece como el 11 de abril

  Scenario: Agregar una dependencia con retraso
    Given existe un proyecto con dos actividades planificadas A y B
    When el Gerente de Proyecto define una dependencia Fin-a-Comienzo con un retraso de 2 días desde A hacia B
    And el Gerente de Proyecto define la fecha de inicio del proyecto en el 10 de abril
    Then la dependencia se registra correctamente con un retraso de 2 días
    And la fecha de inicio de B se establece como el 13 de abril

  Scenario: Agregar una dependencia con adelanto
    Given existe un proyecto con dos actividades planificadas A y B
    When el Gerente de Proyecto define una dependencia Fin-a-Comienzo con un adelanto de 1 día desde A hacia B
    And el Gerente de Proyecto define la fecha de inicio del proyecto en el 10 de abril
    Then la dependencia se registra correctamente con un adelanto de 1 día
    And la fecha de inicio de B se establece como el 10 de abril

  Scenario: Intentar agregar una dependencia a una actividad completada
    Given existe un proyecto con una actividad A completada y otra actividad B planificada
    When el Gerente de Proyecto define una dependencia Fin-a-Comienzo desde A hacia B
    Then se muestra un mensaje de error indicando que no se pueden definir dependencias hacia actividades completadas