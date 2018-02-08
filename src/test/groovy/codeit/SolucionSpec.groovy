package codeit

import grails.testing.gorm.DomainUnitTest
import org.joda.time.DateTime
import spock.lang.Specification

class SolucionSpec extends Specification implements DomainUnitTest<Solucion> {

    Programador programador
    DateTime ahora
    Desafio desafio
    Ejercicio ejercicio

    def setup() {
        programador = new Programador("Programador")
        ahora = DateTime.now()
        desafio = programador.proponerDesafio("Título", "Descripción", ahora, ahora.plusDays(1))
        ejercicio = programador.proponerEjercicioPara(desafio, "El enunciado")
        for (Integer i = 0; i < 10; ++i) {
            String cadenaEntradaYSalida = "Cadena " + i
            ejercicio.agregarPrueba(cadenaEntradaYSalida, cadenaEntradaYSalida)
        }
    }

    def cleanup() {
    }

    void cuandoUnDesafioTieneUnEjercicioUnaSolucionNuevaNoLoResuelve() {
        given:"Un desafío que tiene un ejercicio"
        assert desafio.ejercicios.size() == 1

        and: "una solución nueva"
        Participante participante = new Programador("Resolvedor")
        Solucion solucion = new Solucion(participante, "Solución equivocada", desafio)

        when:"se propone dicha solución"
        desafio.proponerSolucion(solucion)

        then:"la solución no resuelve el desafío"
        !solucion.validar(desafio.ejercicios).valido
    }

    void solucionCorrecta() {
        given:"Un desafío que tiene un ejercicio"
        assert desafio.ejercicios.size() == 1

        and:"la resolución correcta a ese ejercicio"
        Resolucion resolucion = new Resolucion(ejercicio, "{ x -> x }")
        assert ejercicio.validarResolucion(resolucion)

        when:"se propone una solución que tiene esa resolución"
        Participante participante = new Programador("Resolvedor")
        Solucion solucion = new Solucion(participante, "Solución correcta", desafio)
        solucion.agregarResolucion(resolucion)

        then:"la solución resuelve el desafío"
        desafio.validarSolucion(solucion)
    }

}
