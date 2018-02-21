package codeit

import groovy.transform.Sortable

@Sortable(includes = ['ejercicio'])
class Resolucion {

    String codigo

    static belongsTo = [ejercicio: Ejercicio, solucion: Solucion]

    static constraints = {
        ejercicio nullable: false
        solucion nullable: false
        codigo nullable: false
    }

    Resolucion(Ejercicio ejercicio, String codigo) {
        this.ejercicio = ejercicio
        this.codigo = codigo
    }

    String ejecutar(String entrada) {
        String programa = entrada + "\n" + codigo
        new GroovyShell().evaluate(programa)
    }

    Boolean resuelveElEjercicio() {
        ejercicio.validarResolucion(this)
    }

}
