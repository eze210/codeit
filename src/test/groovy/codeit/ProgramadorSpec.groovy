package codeit

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class ProgramadorSpec extends Specification implements DomainUnitTest<Programador> {

    Programador programador

    def setup() {
        programador = new Programador(nombre: "Nombre")
    }

    def cleanup() {
    }

    void "Como participante el programador se involucra solamente a si mismo"() {
        when:"le pido los programadores involucrados a un programador"
        Set<Programador> involucrados = programador.programadoresInvolucrados()

        then:"se involucra solamente a si mismo"
        involucrados.contains(programador) && involucrados.size() == 1
    }

    void "El programador que propone un desafio es su creador"() {
        when:"Un programador propone un desafio"
        Desafio desafio = programador.proponerDesafio()

        then:"Ese programador es el creador del desafio"
        desafio.creador == programador
    }

}
