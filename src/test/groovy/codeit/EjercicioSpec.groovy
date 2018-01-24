package codeit

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class EjercicioSpec extends Specification implements DomainUnitTest<Ejercicio> {

    def setup() {
    }

    def cleanup() {
    }

    void "Un ejercicio nuevo no tiene pruebas"() {
        when:"Un ejercicio es nuevo"
        Ejercicio ejercicio = new Ejercicio()

        then:"no tiene pruebas"
        ejercicio.pruebas.size() == 0
    }

    void "Un ejercicio nuevo se resuelve con una resolucion nueva"() {
        when:"Un ejercicio es nuevo y una resolucion es nueva"
        Ejercicio ejercicio = new Ejercicio()
        Resolucion resolucion = new Resolucion()

        then:"La resolucion resuelve al desafio"
        ejercicio.validarResolucion(resolucion)
    }

    void "Un ejercicio con una prueba agregada tiene una prueba asociada a si mismo"() {
        when:"Un ejercicio tiene una prueba agregada"
        Ejercicio ejercicio = new Ejercicio()
        Prueba prueba = ejercicio.agregarPrueba("", "")

        then:"tiene una prueba y la prueba esta asociada al ejercicio"
        ejercicio.pruebas.size() == 1
        prueba.ejercicio == ejercicio
    }

    void "Ejercicio simple"() {
        when:"Un ejercicio tiene una prueba simple agregada"
        Ejercicio ejercicio = new Ejercicio()
        ejercicio.agregarPrueba("Entrada", "Entrada")
        Resolucion resolucionCorrecta = new Resolucion(ejercicio, "{entrada -> entrada}")
        Resolucion resolucionQuePasa = new Resolucion(ejercicio, "{entrada -> \"Entrada\"}")
        Resolucion resolucionInorrecta = new Resolucion(ejercicio, "{entrada -> \"Cadena incorrecta\"}")

        then:"Es resuelto con una resolucion simple"
        ejercicio.validarResolucion(resolucionCorrecta)
        ejercicio.validarResolucion(resolucionQuePasa)
        !ejercicio.validarResolucion(resolucionInorrecta)
    }

    void "Ejercicio con varias pruebas"() {
        when:"Un ejercicio tiene una prueba simple agregada"
        Ejercicio ejercicio = new Ejercicio()
        ejercicio.agregarPrueba("Entrada", "Entrada")
        ejercicio.agregarPrueba("Salida", "Salida")
        Resolucion resolucionCorrecta = new Resolucion(ejercicio, "{entrada -> entrada}")
        Resolucion resolucionQueYaNoPasa = new Resolucion(ejercicio, "{entrada -> \"Entrada\"}")
        Resolucion resolucionInorrecta = new Resolucion(ejercicio, "{entrada -> \"Cadena incorrecta\"}")

        then:"Es resuelto con una resolucion simple"
        ejercicio.validarResolucion(resolucionCorrecta)
        !ejercicio.validarResolucion(resolucionQueYaNoPasa)
        !ejercicio.validarResolucion(resolucionInorrecta)
    }

}
