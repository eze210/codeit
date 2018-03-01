package codeit

import groovy.transform.EqualsAndHashCode
import groovy.transform.Sortable

@Sortable(includes = ['id'])
@EqualsAndHashCode(includes = ['enunciado'])
class Ejercicio {

    /** Enunciado del ejercicio. */
    String enunciado

    /** Desafío al que pertenece el ejercicio. */
    Desafio desafio

    /** Pruebas que validan el ejercicio. */
    Set<Prueba> pruebas

    /** Declaraciones necesarias para el mapeo relacional. */
    static belongsTo = [desafio: Desafio]
    static hasMany = [pruebas: Prueba, resoluciones: Resolucion]

    /** Reglas para el mapeo relacional. */
    static constraints = {
        desafio nullable: false, blank: false
        enunciado nullable: false, blank: false, unique: true
    }

    /** Constructor de un ejercicio.
     *
     * @param desafio Desafío al que pertenece el ejercicio.
     * @param enunciado Enunciado del ejercicio.
     */
    Ejercicio(Desafio desafio, String enunciado) {
        this.desafio = desafio
        this.enunciado = enunciado
        this.pruebas = new HashSet<>()
        desafio.agregarEjercicio(this)
    }

    /** Agrega una prueba al ejercicio.
     *
     * @param entrada Entrada de la prueba.
     * @param salida Salida esperada de la prueba.
     * @return La nueva prueba.
     */
    Prueba agregarPrueba(String entrada, String salida) {
        Prueba prueba = new Prueba(this, entrada, salida)
        pruebas.add(prueba)
        desafio.invalidarSoluciones()
        Validador.obtenerInstancia() << desafio
        prueba
    }

    /** Valida una resolución corriendo las pruebas.
     *
     * @param resolucion La resolución a validar.
     * @return \c true si pasa todas las pruebas, o \c false sino.
     */
    Boolean validarResolucion(Resolucion resolucion) {
        !pruebas.find { !it.validarResolucion(resolucion) }
    }
}
