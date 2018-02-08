package codeit

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class ResolucionSpec extends Specification implements DomainUnitTest<Resolucion> {

    Desafio desafio

    def setup() {
        desafio = new Desafio("Título", "Descripción", new Programador("Creador"))
    }

    def cleanup() {
    }

    void resolucionDelEjercicioIdentidad() {
        given:"Un ejercicio"
        Ejercicio ejercicio = new Ejercicio(desafio, "Identidad")

        when:"Se le agregan varias pruebas con igual entrada y salida"
        for (Integer i = 0; i < 10; ++i) {
            String entradaYSalida = "Cadena " + i
            ejercicio.agregarPrueba(entradaYSalida, entradaYSalida)
        }
        Resolucion resolucion = new Resolucion(ejercicio, "{ entrada -> entrada }")

        then:"la resolución con el código que devuelve su entrada resuelve el ejercicio"
        resolucion.resuelveElEjercicio()
    }

}
