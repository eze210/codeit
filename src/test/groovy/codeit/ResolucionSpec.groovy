package codeit

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class ResolucionSpec extends Specification implements DomainUnitTest<Resolucion> {

    def setup() {
    }

    def cleanup() {
    }

    void "Resolución del ejercicio identidad"() {
        when:"Un ejercicio tiene varias pruebas con igual entrada y salida"
        Ejercicio ejercicio = new Ejercicio("Identidad")
        for (Integer i = 0; i < 10; ++i) {
            String entradaYSalida = "Cadena " + i
            ejercicio.agregarPrueba(entradaYSalida, entradaYSalida)
        }
        Resolucion resolucion = new Resolucion(ejercicio, "{ entrada -> entrada }")

        then:"la resolución con el código que devuelve su entrada resuelve el ejercicio"
        resolucion.resuelveElEjercicio()
    }

}
