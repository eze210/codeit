package codeit

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(excludes=["valido", "puntaje"])
class Resultado {

    Solucion solucion
    Boolean valido
    Integer puntaje

    static constraints = {
        solucion nullable: false
    }

}
