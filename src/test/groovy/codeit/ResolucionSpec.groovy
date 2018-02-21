package codeit

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class ResolucionSpec extends Specification implements DomainUnitTest<Resolucion> {

    Programador programador
    Desafio desafio

    def setup() {
        programador = new Programador("Creador")
        desafio = programador.proponerDesafio("Título", "Descripción")
    }

    def cleanup() {
    }

    void resolucionDelEjercicioIdentidad() {
        given:"Un ejercicio"
        Ejercicio ejercicio = programador.proponerEjercicioPara(desafio, "Identidad")

        when:"Se le agregan varias pruebas con igual entrada y salida"
        for (Integer i = 0; i < 10; ++i) {
            String salida = "Cadena " + i
            String entrada = "x=\"${salida}\""
            ejercicio.agregarPrueba(entrada, salida)
        }
        Resolucion resolucion = new Resolucion(ejercicio, "x")

        then:"la resolución con el código que devuelve su entrada resuelve el ejercicio"
        resolucion.resuelveElEjercicio()
    }

}
