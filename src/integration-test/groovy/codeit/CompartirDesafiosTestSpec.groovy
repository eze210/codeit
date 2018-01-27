package codeit

import grails.testing.mixin.integration.Integration
import grails.transaction.*
import org.joda.time.DateTime
import spock.lang.Specification

@Integration
@Rollback
class CompartirDesafiosTestSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "Creación del desafío"() {
        given:"Un programador"
        Programador unProgramador = new Programador("Nombre")

        when:"propone un desafío"
        Desafio unDesafio = unProgramador.proponerDesafio(
                "El título",
                "La descripción",
                DateTime.now(),
                DateTime.now())

        then:"el desafío queda registrado en el sistema como creado por el programador"
        unDesafio.creador == unProgramador
    }

    void "Agregar ejercicios - sin soluciones previas"() {
        given:"Un desafio creado por un determinado programador"
        Programador elProgramador = new Programador("Nombre")
        DateTime ahora = DateTime.now()
        DateTime maniana = ahora.plusDays(1)
        Desafio elDesafio = elProgramador.proponerDesafio(
                "El título",
                "La descripción",
                ahora,
                maniana)
        assert elDesafio != null

        and:"está vigente"
        assert elDesafio.estaVigente()

        when:"ese programador intenta subir un nuevo ejercicio"
        Ejercicio elEjercicio = elProgramador.proponerEjercicioPara(elDesafio, "Un enunciado")

        then:"el ejercicio queda agregado al desafio"
        elDesafio.ejercicios.contains(elEjercicio)
    }

    void "Agregar ejercicios - con soluciones previas"() {
        given:"Un desafio creado por un determinado programador"
        Programador programadorCreador = new Programador("Nombre")
        DateTime ahora = DateTime.now()
        DateTime maniana = ahora.plusDays(1)
        Desafio elDesafio = programadorCreador.proponerDesafio(
                "El título",
                "La descripción",
                ahora,
                maniana)

        assert elDesafio != null

        and:"está vigente"
        assert elDesafio.estaVigente()

        and:"tiene una solución válida subida"
        Programador programadorQueResuelve = new Programador("Otro nombre")
        Solucion solucion = programadorQueResuelve.proponerSolucionPara(elDesafio, "Solución que consiste en... nada")
        assert elDesafio.validarSolucion(solucion)

        when:"ese programador intenta subir un nuevo ejercicio"
        Ejercicio elEjercicio = programadorCreador.proponerEjercicioPara(elDesafio, "Un enunciado")

        then:"la solución que antes era válida ya no lo es"
        !elDesafio.validarSolucion(solucion)

        when:"pero cuando se agrega una resolución válida para el ejercicio a la solución"
        Resolucion resolucion = new Resolucion(elEjercicio, "{x -> x}")
        assert elEjercicio.validarResolucion(resolucion)
        solucion.agregarResolucion(resolucion)

        then:"la solución vuelve a ser válida"
        elDesafio.validarSolucion(solucion)
    }

}
