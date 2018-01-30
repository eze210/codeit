package codeit

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

import static groovy.test.GroovyAssert.shouldFail

class EquipoSpec extends Specification implements DomainUnitTest<Equipo> {

    def setup() {
    }

    def cleanup() {
    }

    void "Un equipo nuevo no tiene miembros"() {
        given:"Un equipo nuevo"
        Equipo equipo = new Equipo("Nombre del equipo")

        expect:"El equipo no tiene miembros"
        equipo.programadoresInvolucrados() != null
        equipo.programadoresInvolucrados().empty
    }

    void "Al agregar varios miembros, esos miembros pertenecen al equipo"() {
        given:"Un equipo"
        Equipo equipo = new Equipo("Nombre del equipo")

        when:"Se agregan algunos miembros a un equipo"
        Programador miembro1 = new Programador("Nombre 1")
        Programador miembro2 = new Programador("Nombre 2")
        Programador miembro3 = new Programador("Nombre 3")
        equipo.agregarMiembro(miembro1)
        equipo.agregarMiembro(miembro2)
        equipo.agregarMiembro(miembro3)

        then:"Dichos miembros están involucrados en el equipo"
        equipo.involucraA(miembro1) && equipo.involucraA(miembro2) && equipo.involucraA(miembro3)
    }

    void "No se puede agregar dos veces el mismo programador al mismo equipo"() {
        given:"Un programador y un equipo"
        Programador programador = new Programador("Nombre")
        Equipo equipo = new Equipo("Equipo")

        when:"el programador es agregado al equipo"
        equipo.agregarMiembro(programador)

        then:"el programador no puede ser agregado otra vez"
        shouldFail(ProgramadorYaMiembro) {
            equipo.agregarMiembro(programador)
        }
    }

}
