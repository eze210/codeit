package codeit

import groovy.transform.Sortable

@Sortable(includes = ['ejercicio'])
class Resolucion {

    /** Código de la resolución como cadena de caracteres. */
    String codigo

    /** Ejercicio que intenta resolver la resolución. */
    Ejercicio ejercicio

    /** Solución a la cual está agregada la resolución. */
    Solucion solucion

    /** Declaraciones necesarias para el mapeo relacional. */
    static belongsTo = [ejercicio: Ejercicio, solucion: Solucion]

    /** Reglas para el mapeo relacional. */
    static constraints = {
        ejercicio nullable: false
        solucion nullable: false
        codigo nullable: false
    }

    /** Constructor de una resolución.
     *
     * @param ejercicio Ejercicio que intenta resolver la resolución.
     * @param codigo Código fuente de la resolución como cadena de caracteres.
     */
    Resolucion(Ejercicio ejercicio, String codigo) {
        this.ejercicio = ejercicio
        this.codigo = codigo
    }

    /** Ejecuta el código de la resolución con la entrada especificada, y devuelve
     * la salida de la ejecución.
     *
     * @param entrada Entrada a pasarle al código de la resolución.
     * @return La salida del código para la entrada especificada.
     */
    String ejecutar(String entrada) {
        String programa = entrada + "\n" + codigo
        new GroovyShell().evaluate(programa)
    }

    /** Delega en el ejercicio la validación de sí misma.
     *
     * @return \c true si resuelve el ejercicio, o \c false sino.
     */
    Boolean resuelveElEjercicio() {
        ejercicio.validarResolucion(this)
    }
}
