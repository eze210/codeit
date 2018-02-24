package codeit

import groovy.transform.EqualsAndHashCode

class Resultado {

    /** Indica si todos los ejercicios de la solución estaban resueltos. */
    Boolean valido

    /** Indica si todos los ejercicios estaban correctamente resueltos. */
    Boolean correcto

    /** Cantidad de ejercicios correctamente resueltos. */
    Integer puntaje

    /** Solución que se probó para generar el resultado. */
    Solucion solucion

    /** Reglas para el mapeo relacional. */
    static constraints = {
        solucion nullable: false
        valido nullable: true
        correcto nullable: true
        puntaje nullable: true
    }


    /** Constructor de un resultado sin procesar.
     *
     * @param solucion Solución que aún no se verificó.
     */
    Resultado(Solucion solucion) {
        this.solucion = solucion
        this.valido = null
        this.correcto = null
        this.puntaje = null
        solucion.resultado = this
    }

    /** Consulta si el resultado está procesado.
     *
     * @return \c true si el resultado fue construido a partir de la verificación de una solución.
     */
    Boolean estaProcesado() {
        return valido != null && correcto != null && puntaje != null
    }

    void procesar() {
        solucion.validar()
    }

    void invalidar() {
        valido = null
        correcto = null
        puntaje = null
    }

    /** Interpretación del resultado como Boolean.
     *
     * @return \c true si el resultado es resultado de una verificación y todos los
     * ejercicios estaban resueltos correctamente, o \c false en otro caso.
     */
    Boolean asBoolean() {
        return correcto ?: false
    }

}
