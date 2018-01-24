package codeit

class Prueba {

    Ejercicio ejercicio
    String entrada
    String salidaEsperada

    static constraints = {
        ejercicio nullable: false
        entrada nullable:false
        salidaEsperada: nullable: false
    }

    Boolean validarResolucion(Resolucion resolucion) {
        resolucion.ejecutar(entrada) == salidaEsperada
    }

}
