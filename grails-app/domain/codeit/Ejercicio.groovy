package codeit

class Ejercicio {

    static hasMany = [pruebas: Prueba, resoluciones: Resolucion]

    String enunciado
    Set<Prueba> pruebas

    static constraints = {
        enunciado nullable: false, blank: false, unique: true
    }

    Ejercicio(String enunciado) {
        this.enunciado = enunciado
        this.pruebas = new HashSet<>()
    }

    Ejercicio() {
        this("")
    }

    Prueba agregarPrueba(String entrada, String salida) {
        Prueba prueba = new Prueba(ejercicio: this, entrada: entrada, salidaEsperada: salida)
        pruebas.add(prueba)
        prueba
    }

    Boolean validarResolucion(Resolucion resolucion) {
        !pruebas.find { !it.validarResolucion(resolucion) }
    }

}
