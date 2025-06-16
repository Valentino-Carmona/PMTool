# Issue: 4
Feature: Planificación de actividades de un proyecto
  # Como Gerente de Proyecto
  # Quiero planificar las actividades de un proyecto
  # Para poder cumplir con sus objetivos

  Scenario: Creación de una actividad con código único
    Given hay un proyecto en estado "PLANIFICADO"
    When el Gerente de Proyecto define una nueva actividad con nombre "Nombre Test" y duración 5 días
    Then se asigna un código único a la actividad
    And la actividad se asocia al proyecto
    And su estado inicial es "PLANIFICADA"

  Scenario: Intento de crear una actividad sin nombre
    Given hay un proyecto en estado "PLANIFICADO"
    When el Gerente de Proyecto intenta definir una nueva actividad sin nombre
    Then se informa un mensaje indicando que el nombre es obligatorio
    And no se registra la actividad al proyecto

  Scenario: Intento de agregar actividades a un proyecto finalizado
    Given hay un proyecto en estado "FINALIZADO"
    When el Gerente de Proyecto intenta definir una nueva actividad
    Then se informa un mensaje indicando que no se pueden agregar actividades a proyectos finalizados

  Scenario: Planificar un proyecto con fecha de inicio válida
    Given un proyecto con actividades y dependencias definidas
    When el gerente de proyecto planifica el proyecto con fecha de inicio "2025-09-01"
    Then las fechas de inicio y fin de todas las actividades se calculan automáticamente
    And la fecha de fin del proyecto es la máxima fecha de fin de las actividades

  Scenario: Intentar planificar un proyecto sin fecha de inicio
    Given un proyecto con actividades
    When el gerente de proyecto intenta planificar el proyecto con fecha de inicio nula
    Then se lanza un error indicando la fecha de inicio no puede ser nula