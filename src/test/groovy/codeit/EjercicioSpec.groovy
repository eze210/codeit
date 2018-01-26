package codeit

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class EjercicioSpec extends Specification implements DomainUnitTest<Ejercicio> {

    Desafio desafio

    def setup() {
        desafio = new Desafio("Título", "Descripción", new Programador("Creador"))
    }

    def cleanup() {
    }

    void "Un ejercicio nuevo no tiene pruebas"() {
        when:"Un ejercicio es nuevo"
        Ejercicio ejercicio = new Ejercicio(desafio, "Un título")

        then:"no tiene pruebas"
        ejercicio.pruebas.size() == 0
    }

    void "Un ejercicio nuevo se resuelve con una resolución nueva"() {
        when:"Un ejercicio es nuevo y una resolución es nueva"
        Ejercicio ejercicio = new Ejercicio(desafio, "Un título")
        Resolucion resolucion = new Resolucion(ejercicio, "{ entrada -> null }")

        then:"La resolución resuelve al desafío"
        ejercicio.validarResolucion(resolucion)
    }

    void "Un ejercicio con una prueba agregada tiene una prueba asociada a si mismo"() {
        when:"Un ejercicio tiene una prueba agregada"
        Ejercicio ejercicio = new Ejercicio(desafio, "Un título")
        Prueba prueba = ejercicio.agregarPrueba("", "")

        then:"tiene una prueba y la prueba esta asociada al ejercicio"
        ejercicio.pruebas.size() == 1
        prueba.ejercicio == ejercicio
    }

    void "Ejercicio simple"() {
        when:"Un ejercicio tiene una prueba simple agregada"
        Ejercicio ejercicio = new Ejercicio(desafio, "Un título")
        ejercicio.agregarPrueba("Entrada", "Entrada")
        Resolucion resolucionCorrecta = new Resolucion(ejercicio, "{entrada -> entrada}")
        Resolucion resolucionQuePasa = new Resolucion(ejercicio, "{entrada -> \"Entrada\"}")
        Resolucion resolucionInorrecta = new Resolucion(ejercicio, "{entrada -> \"Cadena incorrecta\"}")

        then:"Es resuelto con una resolución simple"
        ejercicio.validarResolucion(resolucionCorrecta)
        ejercicio.validarResolucion(resolucionQuePasa)
        !ejercicio.validarResolucion(resolucionInorrecta)
    }

    void "Ejercicio con varias pruebas"() {
        when:"Un ejercicio tiene una prueba simple agregada"
        Ejercicio ejercicio = new Ejercicio(desafio, "Un título")
        ejercicio.agregarPrueba("Entrada", "Entrada")
        ejercicio.agregarPrueba("Salida", "Salida")
        Resolucion resolucionCorrecta = new Resolucion(ejercicio, "{entrada -> entrada}")
        Resolucion resolucionQueYaNoPasa = new Resolucion(ejercicio, "{entrada -> \"Entrada\"}")
        Resolucion resolucionInorrecta = new Resolucion(ejercicio, "{entrada -> \"Cadena incorrecta\"}")

        then:"Es resuelto con una resolución simple"
        ejercicio.validarResolucion(resolucionCorrecta)
        !ejercicio.validarResolucion(resolucionQueYaNoPasa)
        !ejercicio.validarResolucion(resolucionInorrecta)
    }

}
