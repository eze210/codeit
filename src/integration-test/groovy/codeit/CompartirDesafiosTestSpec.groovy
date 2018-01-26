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
        when:"Existe un programador que propone un desafío"
        Programador unProgramador = new Programador("Nombre")
        Desafio unDesafio = unProgramador.proponerDesafio(
                "El título",
                "La descripción",
                DateTime.now(),
                DateTime.now())

        then:"el desafío queda registrado en el sistema como creado por el programador"
        unDesafio.creador == unProgramador
    }

    void "Agregar ejercicios - sin soluciones previas"() {
        when:"Existe un desafio creado por un determinado programador, está vigente, y ese programador intenta subir un nuevo ejercicio"
        Programador elProgramador = new Programador("Nombre")
        DateTime ahora = DateTime.now()
        DateTime maniana = ahora.plusDays(1)
        Desafio elDesafio = elProgramador.proponerDesafio(
                "El título",
                "La descripción",
                ahora,
                maniana)
        assert elDesafio != null
        assert elDesafio.estaVigente()

        Ejercicio elEjercicio = elProgramador.proponerEjercicioPara(elDesafio, "Un enunciado")

        then:"el ejercicio queda agregado al desafio"
        elDesafio.ejercicios.contains(elEjercicio)
    }

    void "Agregar ejercicios - con soluciones previas"() {
        when:   "Existe un desafio creado por un determinado programador, " +
                "está vigente, " +
                "tiene una solución válida subida, " +
                "y ese programador intenta subir un nuevo ejercicio"
        Programador programadorCreador = new Programador("Nombre")
        DateTime ahora = DateTime.now()
        DateTime maniana = ahora.plusDays(1)
        Desafio elDesafio = programadorCreador.proponerDesafio(
                "El título",
                "La descripción",
                ahora,
                maniana)

        /* existe el desafio */
        assert elDesafio != null

        /* está vigente */
        assert elDesafio.estaVigente()

        /* tiene una solución subida */
        Programador programadorQueResuelve = new Programador("Otro nombre")
        Solucion solucion = programadorQueResuelve.proponerSolucionPara(elDesafio, "Solución que consiste en... nada")
        assert elDesafio.validarSolucion(solucion)

        /* el creador propone un ejercicio nuevo para el desafío */
        Ejercicio elEjercicio = programadorCreador.proponerEjercicioPara(elDesafio, "Un enunciado")

        then:"la solución que antes era válida ya no lo es"
        !elDesafio.validarSolucion(solucion)
    }

}
