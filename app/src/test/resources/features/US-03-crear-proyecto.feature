# Issue: 3
Feature: Inclusión de un nuevo proyecto en el portafolio
  # Como Gerente de Portafolio
  # Quiero incluir un nuevo proyecto en el portafolio
  # Para cumplir con los objetivos de la organización

  Scenario: Creación de un proyecto dentro del portafolio
    Given existe un portafolio de proyectos
    When el Gerente de Portafolio solicita agregar un nuevo proyecto al portafolio
    Then se crea un nuevo proyecto con un nombre, el total de horas estimadas, el presupuesto y en estado "PLANIFICADO"

  Scenario: Intentar crear un proyecto con un nombre duplicado
    Given existe un portafolio de proyectos
    And ya existe un proyecto con el nombre "Proyecto Test"
    When el Gerente de Portafolio solicita agregar un nuevo proyecto con el nombre "Proyecto Test"
    Then se muestra un mensaje de error indicando que el nombre del proyecto ya existe
    And no se registra el proyecto en el portafolio