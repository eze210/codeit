package codeit

class Ejercicio {

    static hasMany = [pruebas: Prueba, resoluciones: Resolucion]

    Desafio desafio
    String enunciado
    Set<Prueba> pruebas

    static constraints = {
        desafio nullable: false, blank: false
        enunciado nullable: false, blank: false, unique: true
    }

    Ejercicio(Desafio desafio, String enunciado) {
        this.desafio = desafio
        this.enunciado = enunciado
        this.pruebas = new HashSet<>()
        desafio.agregarEjercicio(this)
    }

    Prueba agregarPrueba(String entrada, String salida) {
        Prueba prueba = new Prueba(this, entrada, salida)
        pruebas.add(prueba)
        desafio.revalidarSoluciones()
        prueba
    }

    Boolean validarResolucion(Resolucion resolucion) {
        !pruebas.find { !it.validarResolucion(resolucion) }
    }

}
