package codeit

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

import static groovy.test.GroovyAssert.shouldFail

class EquipoSpec extends Specification implements DomainUnitTest<Equipo> {

    def setup() {
    }

    def cleanup() {
    }

    void unEquipoNuevoNoTieneMiembros() {
        given:"Un equipo nuevo"
        Equipo equipo = new Equipo("Nombre del equipo")

        expect:"El equipo no tiene miembros"
        equipo.programadoresInvolucrados() != null
        equipo.programadoresInvolucrados().empty
    }

    void alAgregarVariosMiembrosEsosMiembrosPertenecenAlEquipo() {
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

    void noSePuedeAgregarDosVecesElMismoProgramadorAlMismoEquipo() {
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

    void noSePuedeArmarUnEquipoConLosMismosMiembrosQueOtroEquipoExistente() {
        given:"Un equipo guardado en la base de datos"
        Equipo equipo1 = new Equipo("Equipo1")
        Programador programador1 = new Programador("Programador1")
        Programador programador2 = new Programador("Programador2")
        equipo1.agregarMiembro(programador1)
        equipo1.agregarMiembro(programador2)
        assert programador1.id == null
        assert programador2.id == null
        assert equipo1.id == null
        programador1.save()
        programador2.save()
        equipo1.save()
        assert programador1.id != null
        assert programador2.id != null
        assert equipo1.id != null

        and:"Un equipo con todos menos 1 de esos miembros"
        Equipo equipo2 = new Equipo("Equipo2")
        equipo2.agregarMiembro(programador1)

        when:"se intenta agregar el miembro faltante al nuevo equipo"
        then:"el miembro faltante no puede ser agregado al nuevo equipo"
        shouldFail(EquipoYaExistente) {
            equipo2.agregarMiembro(programador2)
        }
//        equipo2.involucraA(programador2)
    }


}
