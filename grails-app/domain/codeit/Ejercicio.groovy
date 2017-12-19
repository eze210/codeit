package codeit

class Ejercicio {

    static hasMany = [pruebas: Prueba, resoluciones: Resolucion]

    String enunciado
    Set<Prueba> pruebas

    static constraints = {
        enunciado nullable: false, blank: false, unique: true
    }

    Boolean agregarPrueba(Prueba prueba) {
        return pruebas.add(prueba)
    }

    Boolean validarResolucion(Resolucion resolucion) {
        return !pruebas.find { !it.validarResolucion(resolucion) }
    }

}
