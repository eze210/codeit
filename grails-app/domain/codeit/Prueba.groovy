package codeit

import groovy.transform.Sortable

/** Clase prueba. */
@Sortable(includes = ['id'])
class Prueba {

    /** Entrada de la prueba. */
    String entrada

    /** Salida esperada de la prueba. */
    String salidaEsperada

    /** Ejercicio que es probado por la prueba. */
    Ejercicio ejercicio

    /** Declaraciones necesarias para el mapeo relacional. */
    static belongsTo = [ejercicio: Ejercicio]

    /** Reglas para el mapeo relacional. */
    static constraints = {
        ejercicio nullable: false
        entrada nullable:false
        salidaEsperada nullable: false
    }

    /** Constructor de una prueba.
     *
     * @param ejercicio Ejercicio que es probado por la prueba.
     * @param entrada Entrada de la prueba.
     * @param salidaEsperada Salida esperada de la prueba.
     */
    Prueba(Ejercicio ejercicio, String entrada, String salidaEsperada) {
        this.ejercicio = ejercicio
        this.entrada = entrada
        this.salidaEsperada = salidaEsperada
    }

    /** Ejecuta el código de una resolución pasándole la entrada de la prueba
     * y verifica que la salida sea la esperada por la prueba.
     *
     * @param resolucion Resolución cuyo código se quiere ejecutar.
     * @return \c true si la salida esperada coincide, o \c false sino.
     */
    Boolean validarResolucion(Resolucion resolucion) {
        resolucion.ejecutar(entrada) == salidaEsperada
    }
}
