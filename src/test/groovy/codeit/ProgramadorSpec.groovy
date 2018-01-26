package codeit

import grails.testing.gorm.DomainUnitTest
import org.joda.time.DateTime
import spock.lang.Specification

class ProgramadorSpec extends Specification implements DomainUnitTest<Programador> {

    Programador programador

    def setup() {
        programador = new Programador("Nombre")
    }

    def cleanup() {
    }

    void "Como participante el programador se involucra solamente a si mismo"() {
        when:"le pido los programadores involucrados a un programador"
        Set<Programador> involucrados = programador.programadoresInvolucrados()

        then:"se involucra solamente a si mismo"
        involucrados.contains(programador) && involucrados.size() == 1
    }

    void "El programador que propone un desafío es su creador"() {
        when:"Un programador propone un desafío"
        Desafio desafio = programador.proponerDesafio(
                "Un título",
                "Una descripción",
                DateTime.now(),
                DateTime.now())

        then:"Ese programador es el creador del desafío"
        desafio.creador == programador
    }

}
