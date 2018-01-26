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

    void "Cuando un desafío tiene un ejercicio, una solución nueva no lo resuelve"() {
        when:"Un desafío tiene un ejercicio"
        assert desafio.ejercicios.size() == 1

        then:"Una solución nueva no lo resuelve"
        Participante participante = new Programador("Resolvedor")
        Solucion solucion = new Solucion(participante, "Solución equivocada")
        Resultado resultado = desafio.proponerSolucion(solucion)
        !resultado.valido
    }

    void "Solución correcta"() {
        when:"Un desafío tiene un ejercicio, se tiene la resolución correcta a ese ejercicio"
        assert desafio.ejercicios.size() == 1
        Resolucion resolucion = new Resolucion(ejercicio, "{ x -> x }")
        assert ejercicio.validarResolucion(resolucion)

        then:"Una solución que tiene esa resolución resuelve el desafío"
        Participante participante = new Programador("Resolvedor")
        Solucion solucion = new Solucion(participante, "Solución correcta")
        solucion.agregarResolucion(resolucion)
        desafio.validarSolucion(solucion)
    }

}
