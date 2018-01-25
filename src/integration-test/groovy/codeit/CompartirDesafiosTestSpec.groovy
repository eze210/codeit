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

    void "creacion del desafio"() {
        when:"Existe un programador que propone un desafio"
        Programador unProgramador = new Programador("Nombre")
        Desafio unDesafio = unProgramador.proponerDesafio(
                "El título",
                "La descripción",
                DateTime.now(),
                DateTime.now())

        then:"el desafio queda registrado en el sistema como creado por el programador"
        unDesafio.creador == unProgramador
    }

    void "agregar ejercicios"() {
        when:"existe un desafio creado por un determinado programador, está vigente, y ese programador intenta subir un nuevo ejercicio"
        Programador elProgramador = new Programador("Nombre")
        DateTime ahora = DateTime.now()
        DateTime maniana = ahora.plusDays(1)
        Desafio elDesafio = elProgramador.proponerDesafio(
                "El título",
                "La descripción",
                ahora,
                maniana)
        assert elDesafio.estaVigente()

        Ejercicio elEjercicio = elProgramador.proponerEjercicioPara(elDesafio)

        then:"el ejercicio queda agregado al desafio"
        elDesafio.ejercicios.contains(elEjercicio)
    }

}
