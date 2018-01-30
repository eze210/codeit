package codeit

import groovy.transform.EqualsAndHashCode

// Así son iguales para los sets si pertenecen a la misma solución
@EqualsAndHashCode(excludes=["valido", "puntaje", "correcto"])
class Resultado {

    Solucion solucion
    Boolean valido
    Boolean correcto
    Integer puntaje

    static constraints = {
        solucion nullable: false
    }

    Resultado(Solucion solucion) {
        this.solucion = solucion
        this.valido = null
        this.correcto = null
        this.puntaje = null
    }

    Resultado(Solucion solucion, Boolean valido, Boolean correcto, Integer puntaje) {
        this.solucion = solucion
        this.valido = valido
        this.correcto = correcto
        this.puntaje = puntaje
    }

    Boolean estaProcesado() {
        return valido != null && correcto != null && puntaje != null
    }

    Boolean asBoolean() {
        return correcto ?: false
    }

}
