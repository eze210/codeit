package codeit

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class EjercicioSpec extends Specification implements DomainUnitTest<Ejercicio> {

    Programador programador
    Desafio desafio

    def setup() {
        Validador.crearInstancia(Validador.TipoValidador.Sincronico)
        programador = new Programador("Creador")
        desafio = programador.proponerDesafio("Título", "Descripción")
    }

    def cleanup() {
    }

    void unEjercicioNuevoNoTienePruebas() {
        given:"Un ejercicio nuevo"
        Ejercicio ejercicio = programador.proponerEjercicioPara(desafio, "Un título")

        expect:"no tiene pruebas"
        ejercicio.pruebas.size() == 0
    }

    void unEjercicioNuevoSeResuelveConUnaResolucionNueva() {
        given:"Un ejercicio nuevo"
        Ejercicio ejercicio = programador.proponerEjercicioPara(desafio, "Un título")

        and:"una resolución nueva"
        Resolucion resolucion = new Resolucion(ejercicio, "{ entrada -> null }")

        expect:"La resolución resuelve al desafío"
        ejercicio.validarResolucion(resolucion)
    }

    void unEjercicioConUnaPruebaAgregadaTieneUnaPruebaAsociadaASiMismo() {
        given:"Un ejercicio"
        Ejercicio ejercicio = programador.proponerEjercicioPara(desafio, "Un título")

        when:"se le agrega una prueba"
        Prueba prueba = ejercicio.agregarPrueba("", "")

        then:"tiene una prueba"
        ejercicio.pruebas.size() == 1

        and:"la prueba esta asociada al ejercicio"
        prueba.ejercicio == ejercicio
    }

    void ejercicioSimple() {
        given:"Un ejercicio"
        Ejercicio ejercicio = programador.proponerEjercicioPara(desafio, "Un título")

        when:"se agrega una prueba simple"
        ejercicio.agregarPrueba("x=\"Entrada\"", "Entrada")

        then:"Es resuelto con una resolución simple"
        Resolucion resolucionCorrecta = new Resolucion(ejercicio, "x")
        Resolucion resolucionQuePasa = new Resolucion(ejercicio, "\"Entrada\"")
        Resolucion resolucionInorrecta = new Resolucion(ejercicio, "\"Cadena incorrecta\"")

        ejercicio.validarResolucion(resolucionCorrecta)
        ejercicio.validarResolucion(resolucionQuePasa)
        !ejercicio.validarResolucion(resolucionInorrecta)
    }

    void ejercicioConVariasPruebas() {
        given:"Un ejercicio"
        Ejercicio ejercicio = programador.proponerEjercicioPara(desafio, "Un título")

        when:"se agregan dos pruebas simples"
        ejercicio.agregarPrueba("x=\"Entrada\"", "Entrada")
        ejercicio.agregarPrueba("x=\"Salida\"", "Salida")

        then:"Es resuelto con una resolución simple"
        Resolucion resolucionCorrecta = new Resolucion(ejercicio, "x")
        Resolucion resolucionQueYaNoPasa = new Resolucion(ejercicio, "\"Entrada\"")
        Resolucion resolucionInorrecta = new Resolucion(ejercicio, "\"Cadena incorrecta\"")
        ejercicio.validarResolucion(resolucionCorrecta)
        !ejercicio.validarResolucion(resolucionQueYaNoPasa)
        !ejercicio.validarResolucion(resolucionInorrecta)
    }

}
