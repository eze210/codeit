package codeit

import grails.testing.mixin.integration.Integration
import grails.test.mixin.*
import grails.transaction.*
import spock.lang.Specification

@Integration
@Rollback
class AutomatizarPruebasTestSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "Agregado de pruebas - sin soluciones previas"() {
        when:   "Existe un desafío creado por un determinado programador," +
                "el desafío está vigente," +
                "y ese programador agrega una prueba para un ejercicio de dicho desafío"
        Programador programador = new Programador("Creador")
        Desafio desafio = programador.proponerDesafio("Un título", "Descripción")
        assert desafio.estaVigente()
        Ejercicio ejercicio = programador.proponerEjercicioPara(desafio, "Identidad")
        assert desafio.ejercicios.contains(ejercicio)
        Prueba prueba = ejercicio.agregarPrueba("Cadena", "Cadena")

        then:"la prueba queda agregada al ejercicio"
        ejercicio.pruebas.contains(prueba)
    }

    void "Agregado de pruebas - con soluciones previas"() {
        when: "Existe un desafío creado por un determinado programador"
        Programador programador = new Programador("Creador")
        Desafio desafio = programador.proponerDesafio("Un título", "Descripción")
        Ejercicio ejercicio = programador.proponerEjercicioPara(desafio, "Identidad")

        and: "el desafío está vigente"
        assert desafio.estaVigente()

        and: "el desafío tiene soluciones válidas previas,"
        Programador resolvedor = new Programador("Resolvedor")
        Solucion solucion = resolvedor.proponerSolucionPara(desafio, "La solución al desafío sin pruebas")
        Resolucion resolucion = new Resolucion(ejercicio, "{x -> \"x\"}")
        solucion.agregarResolucion(resolucion)
        assert desafio.validarSolucion(solucion)


        and:    "el programador creador agrega una prueba que no falsea las soluciones previas " +
                "para un ejercicio de dicho desafío"
        ejercicio.agregarPrueba("x", "x")
        assert ejercicio.validarResolucion(resolucion)

        then:"La solución sigue siendo válida"
        desafio.validarSolucion(solucion)

        when:"la prueba sí falsea"
        ejercicio.agregarPrueba("y", "y")
        assert !ejercicio.validarResolucion(resolucion)

        then:"La solución deja de valer"
        !desafio.validarSolucion(solucion)
    }

    void "Agregado de solución"() {
        when:"Existe un desafío en el sistema"
        Programador creador = new Programador("Creador")
        Desafio desafio = creador.proponerDesafio("Título", "Descripción")
        assert desafio != null

        // TODO: and:"sus insignias requeridas cierto programador posee"
        Programador solucionador = new Programador("Solucionador")

        and:"ese programador sube una solución"
        Solucion solucion = solucionador.proponerSolucionPara(desafio, "Mi solución")

        then:"se corren las pruebas"
        // TODO: hacer un mock de Prueba para verificar la invocación del método
        true
    }

}
