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
        when:"Un equipo es nuevo"
        Equipo equipo = new Equipo("Nombre del equipo")

        then:"El equipo no tiene miembros"
        equipo.programadoresInvolucrados() != null
        equipo.programadoresInvolucrados().empty
    }

    void "Al agregar varios miembros, esos miembros pertenecen al equipo"() {
        when:"Se agregan algunos miembros a un equipo"
        Programador miembro1 = new Programador("Nombre 1")
        Programador miembro2 = new Programador("Nombre 2")
        Programador miembro3 = new Programador("Nombre 3")
        Equipo equipo = new Equipo("Nombre del equipo")
        equipo.agregarMiembro(miembro1)
        equipo.agregarMiembro(miembro2)
        equipo.agregarMiembro(miembro3)

        then:"Dichos miembros están involucrados en el equipo"
        equipo.involucraA(miembro1, miembro2, miembro3)
    }

    void "No se puede agregar dos veces el mismo programador al mismo equipo"() {
        when:"Un programador está agregado a un equipo"
        Programador programador = new Programador("Nombre")
        Equipo equipo = new Equipo("Equipo")
        equipo.agregarMiembro(programador)

        then:"El programador no puede ser agregado otra vez"
        shouldFail(Equipo.ProgramadorYaMiembroException) {
            equipo.agregarMiembro(programador)
        }
    }

}
