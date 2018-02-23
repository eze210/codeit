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


    void agregadoDePruebasSinSolucionesPrevias() {
        given:"Un desafío creado por un determinado programador"
        Programador programador = new Programador("Creador")
        Desafio desafio = programador.proponerDesafio("Un título", "Descripción")

        and:"el desafío tiene un ejercicio"
        Ejercicio ejercicio = programador.proponerEjercicioPara(desafio, "Identidad")
        assert desafio.ejercicios.contains(ejercicio)

        and:"el desafío está vigente"
        assert desafio.estaVigente()

        when:"y ese programador agrega una prueba para un ejercicio de dicho desafío"
        Prueba prueba = ejercicio.agregarPrueba("Cadena", "Cadena")

        then:"la prueba queda agregada al ejercicio"
        ejercicio.pruebas.contains(prueba)
    }


    void agregadoDePruebasConSolucionesPrevias() {
        given: "Un desafío creado por un determinado programador"
        Programador programador = new Programador("Creador")
        Desafio desafio = programador.proponerDesafio("Un título", "Descripción")
        Ejercicio ejercicio = programador.proponerEjercicioPara(desafio, "Identidad")

        and: "el desafío está vigente"
        assert desafio.estaVigente()

        and: "el desafío tiene soluciones válidas previas,"
        Programador resolvedor = new Programador("Resolvedor")
        Solucion solucion = resolvedor.proponerSolucionPara(desafio, "La solución al desafío sin pruebas")
        Resolucion resolucion = new Resolucion(ejercicio, "")
        solucion.agregarResolucion(resolucion)
        assert desafio.validarSolucion(solucion)

        when:   "el programador creador agrega una prueba que no falsea las soluciones previas " +
                "para un ejercicio de dicho desafío"
        ejercicio.agregarPrueba("x = 2", "2")
        assert ejercicio.validarResolucion(resolucion)

        then:"La solución sigue siendo válida"
        desafio.validarSolucion(solucion)

        when:"la prueba sí falsea"
        ejercicio.agregarPrueba("y = 3", "2")
        assert !ejercicio.validarResolucion(resolucion)

        then:"La solución deja de valer"
        !desafio.validarSolucion(solucion)
    }


    void agregadoDeSolucion() {
        given:"Un desafío en el sistema"
        Programador creador = new Programador("Creador")
        Desafio desafio = creador.proponerDesafio("Título", "Descripción", new LinkedHashSet<Insignia>([
                TipoFaceta.Creativo.insigniasAutomaticasPosibles[0]
        ]))
        assert desafio != null

        and:"sus insignias requeridas cierto programador posee"
        Programador solucionador = new Programador("Solucionador")
        solucionador.otorgarInsignia(TipoFaceta.Creativo.insigniasAutomaticasPosibles[0])
        assert solucionador.obtenerInsignias().containsAll(desafio.obtenerInsigniasRequeridas())

        when:"ese programador sube una solución"
        Solucion solucion = solucionador.proponerSolucionPara(desafio, "Mi solución")

        then:"se corren las pruebas"
        desafio.obtenerResultadoActualDeSolucion(solucion) != null
        // TODO: hacer un mock de Prueba para verificar la invocación del método
        true
    }

}
