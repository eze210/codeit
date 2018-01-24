package codeit

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class DesafioSpec extends Specification implements DomainUnitTest<Desafio> {

    def setup() {
    }

    def cleanup() {
    }

    void "Creacion del desafio"() {
        when:"Se crea un desafio con ciertos parámetros"
        Programador programador = new Programador("El nombre")
        Desafio desafio = new Desafio("El título", "La descripción", programador)

        then:"el desafío tiene esos parámetros y es válido"
        desafio.creador == programador
        desafio.titulo == "El título"
        desafio.descripcion == "La descripción"

        desafio.ejercicios.size() == 0
        desafio.resultados.size() == 0
        desafio.vigencia.to >= desafio.vigencia.from
    }

    void "Agregar ejercicios"() {
        when:"Se agrega un ejercicio a un desafío nuevo"
        Programador programador = new Programador("El nombre")
        Desafio desafioNuevo = new Desafio("El título", "La descripción", programador)
        Ejercicio ejercicio = new Ejercicio("El enunciado")
        desafioNuevo.agregarEjercicio(ejercicio)

        then:"El desafío tiene ese ejercicio"
        desafioNuevo.ejercicios.size() == 1
        desafioNuevo.ejercicios.contains(ejercicio)
    }

}
