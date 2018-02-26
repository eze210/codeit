package codeit

import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification


@Integration
@Rollback
class TrabajarEnEquipoTestSpec extends Specification {

    boolean seLlamoAValidar

    def setup() {
        seLlamoAValidar = false
        Invitacion.metaClass.validar = {
            seLlamoAValidar = true
        }
    }

    def cleanup() {
    }

    void conformacionDeEquiposInvitacionProgramadorAProgramador() {
        given:"Dos programadores que quieren participar juntos en futuros desafíos"
        Programador programadorQueInvita = new Programador("Invita")
        Programador programadorInvitado = new Programador("Invitado")
        programadorQueInvita.save(flush: true, failOnError:true)
        programadorInvitado.save(flush: true, failOnError:true)

        and:"juntos forman un equipo válido"
        assert Equipo.formanEquipoValido(programadorQueInvita, programadorInvitado)

        when:"un programador invita al otro a formar un equipo"
        Equipo nuevoEquipo = programadorQueInvita.crearEquipo("Nuevo equipo")
        Invitacion invitacion = programadorQueInvita.invitar(programadorInvitado, nuevoEquipo)

        then:"el segundo recibe la invitación"
        programadorInvitado.invitaciones.contains(invitacion)

        and:"la invitación queda en estado pendiente"
        invitacion.estado == Invitacion.Estado.Pendiente
    }

    void conformacionDeEquiposInvitacionEquipoAProgramador() {
        given:"Un equipo creado"
        Programador creadorDelEquipo = new Programador("Un programador")
        Equipo equipo = creadorDelEquipo.crearEquipo("Equipo")

        and:"un programador"
        Programador nuevoProgramador = new Programador("Nuevo")

        and:"es válido que el equipo invite a ese programador"
        assert Equipo.formanEquipoValido(equipo, nuevoProgramador)

        when:"el equipo invita a ese programador"
        Invitacion invitacion = equipo.invitar(nuevoProgramador)

        then:"el programador recibe la invitación"
        nuevoProgramador.invitaciones.contains(invitacion)
    }

    void conformacionDeEquiposAceptarInvitaciones() {
        Equipo equipo = new Equipo("Equipo")
        equipo.agregarMiembro(new Programador("Un programador"))
        Programador nuevoProgramador = new Programador("Nuevo")
        Invitacion invitacion = equipo.invitar(nuevoProgramador)

        given:"Un programador recibió una invitación"
        assert nuevoProgramador.invitaciones.contains(invitacion)

        when:"la acepta"
        Equipo equipoConformado = nuevoProgramador.aceptarInvitacion(invitacion)

        then:"queda agregado al equipo"
        equipoConformado == equipo
        equipo.programadoresInvolucrados().contains(nuevoProgramador)

        and:"se vuelven a validar todas las invitaciones que haya recibido"
        seLlamoAValidar
    }

    void logrosConjuntos() {
        Programador creadorDelDesafio = new Programador("Desafiante")
        Desafio elDesafio = creadorDelDesafio.proponerDesafio("El desafío", "Descripción")
        Equipo equipo = new Equipo("El equipo")
        Programador resolvedor1 = new Programador("Resolvedor 1")
        Programador resolvedor2 = new Programador("Resolvedor 2")
        equipo.agregarMiembro(resolvedor1)
        Solucion laSolucion = equipo.proponerSolucionPara(elDesafio, "La mejor solución")

        creadorDelDesafio.elegirMejorSolucion(laSolucion)
        laSolucion.otorgarInsignia(TipoFaceta.Creativo.insigniasAutomaticasPosibles[0])

        given:"Un equipo ya ha conseguido logros en un desafío"
        assert resolvedor1.obtenerPuntajeEnFaceta(TipoFaceta.Ganador) == 1
        assert resolvedor1.obtenerInsignias().contains(TipoFaceta.Creativo.insigniasAutomaticasPosibles[0])

        when:"un programador nuevo se suma al equipo"
        equipo.agregarMiembro(resolvedor2)

        then:"no hereda los logros"
        !resolvedor2.obtenerInsignias().contains(TipoFaceta.Creativo.insigniasAutomaticasPosibles[0])

        and:"no consigue puntos extra de desafíos finalizados"
        resolvedor2.obtenerPuntajeEnFaceta(TipoFaceta.Ganador) == 0

        and:"sí gana la habilidad de conseguirlos para toda acción del equipo"
        laSolucion.otorgarInsignia(TipoFaceta.Creativo.insigniasAutomaticasPosibles[1])
        resolvedor2.obtenerInsignias().contains(TipoFaceta.Creativo.insigniasAutomaticasPosibles[1])
    }

    void desintegracionDeEquipos() {
        given:"Un equipo"
        Programador programador1 = new Programador("Un programador")
        Equipo equipo = programador1.crearEquipo("Equipo")
        equipo.agregarMiembro(new Programador("Otro programador"))

        and:"sin un determinado programador el equipo sigue siendo válido"
        assert Equipo.formanEquipoValido(equipo.programadoresInvolucrados() - programador1)

        and:"el equipo tiene alguna insignia"
        equipo.otorgarInsignia(TipoFaceta.Prolijo.insigniasAutomaticasPosibles[0])

        when:"ese programador decide irse del equipo"
        programador1.abandonarEquipo(equipo)

        then:"no puede participar más en nombre de ese equipo"
        !equipo.programadoresInvolucrados().contains(programador1)
        !programador1.equipos.contains(equipo)

        and:"no pierde puntos ni logros conseguidos"
        equipo.obtenerInsignias() == programador1.obtenerInsignias()
    }
}
