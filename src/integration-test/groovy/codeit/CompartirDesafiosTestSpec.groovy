package codeit

import grails.testing.mixin.integration.Integration
import grails.transaction.*
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
        Programador unProgramador = new Programador(nombre: "Nombre")
        Desafio unDesafio = unProgramador.proponerDesafio()

        then:"el desafio queda registrado en el sistema como creado por el programador"
        unDesafio.creador == unProgramador
    }

    void "agregar ejercicios"() {
        when:"existe un desafio creado por un determinado programador y ese programador intenta subir un nuevo ejercicio"
        Programador elProgramador = new Programador(nombre: "Nombre")
        assert elProgramador != null
        Desafio elDesafio = elProgramador.proponerDesafio()
        assert elDesafio != null
        assert elDesafio.estaVigente()
        Ejercicio elEjercicio = elProgramador.proponerEjercicioPara(elDesafio)

        then:"el ejercicio queda agregado al desafio"
        elDesafio.ejercicios.contains(elEjercicio)
    }

}
