package codeit

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class EquipoSpec extends Specification implements DomainUnitTest<Equipo> {

    def setup() {
    }

    def cleanup() {
    }

    void "Un equipo nuevo no tiene miembros"() {
        when:"Un equipo es nuevo"
        Equipo equipo = new Equipo("Nombre del equipo")

        then:"El equipo no tiene miembros"
        equipo.programadoresInvolucrados() != null
        equipo.programadoresInvolucrados().empty
    }
}
