# language: es

Característica: Convertir número a palabras
    Yo como estudiante
    necesito convertir un número a su nombre en palabras
    para verificar su escritura.

    Antecedentes:
        Dado que el usuario se encuentra en el recurso

    Escenario: Convertir entero positivo a nombre formal
        Cuando el usuario indica el entero positivo y genera la consulta
        Entonces visualizará el nombre del número en palabras

    Escenario: Convertir entero negativo a palabras
        Cuando el usuario indica el número negativo y genera la consulta
        Entonces visualizará un error