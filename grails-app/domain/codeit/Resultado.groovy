package codeit

import groovy.transform.EqualsAndHashCode

// Así son iguales para los sets si pertenecen a la misma solución
@EqualsAndHashCode(excludes=["valido", "puntaje", "correcto"])
class Resultado {

    Boolean valido
    Boolean correcto
    Integer puntaje

    Solucion solucion

    static belongsTo = [desafio: Desafio]

    static constraints = {
        solucion nullable: false
        desafio nullable: false
        valido nullable: true
        correcto nullable: true
        puntaje nullable: true
    }

    Resultado(Solucion solucion) {
        this.solucion = solucion
        this.valido = null
        this.correcto = null
        this.puntaje = null
        this.desafio = solucion.desafio
        solucion.resultado = this
    }

    Resultado(Solucion solucion, Boolean valido, Boolean correcto, Integer puntaje) {
        this.solucion = solucion
        this.valido = valido
        this.correcto = correcto
        this.puntaje = puntaje
        this.desafio = solucion.desafio
        solucion.resultado = this
    }

    Boolean estaProcesado() {
        return valido != null && correcto != null && puntaje != null
    }

    Boolean asBoolean() {
        return correcto ?: false
    }

}
