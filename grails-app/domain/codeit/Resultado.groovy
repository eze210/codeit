package codeit

import groovy.transform.EqualsAndHashCode

// Así son iguales para los sets si pertenecen a la misma solución
@EqualsAndHashCode(excludes=["valido", "puntaje"])
class Resultado {

    Solucion solucion
    Boolean valido
    Boolean correcto
    Integer puntaje

    static constraints = {
        solucion nullable: false
        valido nullable: false
        correcto nullable: false
        puntaje nullable: false
    }

    boolean asBoolean() {
        return correcto
    }

}
