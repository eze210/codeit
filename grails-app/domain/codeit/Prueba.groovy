package codeit

class Prueba {

    Ejercicio ejercicio
    String entrada
    String salidaEsperada

    static constraints = {
        ejercicio nullable: false
        entrada nullable:false
        salidaEsperada nullable: false
    }

    Prueba(Ejercicio ejercicio, String entrada, String salidaEsperada) {
        this.ejercicio = ejercicio
        this.entrada = entrada
        this.salidaEsperada = salidaEsperada
    }

    Boolean validarResolucion(Resolucion resolucion) {
        resolucion.ejecutar(entrada) == salidaEsperada
    }

}
