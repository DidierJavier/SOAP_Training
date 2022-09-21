#language: es

Característica: Calcular resultado de dos números
  Como usuario
  necesito realizar un calculo entre dos números
  para obtener una respuesta rápida.

  @Multiply
  Escenario: Calcular multiplicación
    Dado que el usuario esta en el recurso para multiplicar los números "5" y "4"
    Cuando el usuario genera el calculo
    Entonces visualizará el resultado calculado de ambos números

  @Subtract
  Escenario: Calcular resta
    Dado que el usuario esta en el recurso para restar los números "10" y "2"
    Cuando el usuario genera el calculo de la resta
    Entonces visualizará el resultado restado de ambos números
