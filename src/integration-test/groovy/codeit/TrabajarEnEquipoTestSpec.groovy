package codeit

import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification


@Integration
@Rollback
class TrabajarEnEquipoTestSpec extends Specification {

    def setup() {
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
        Equipo equipo = new Equipo("Equipo")
        equipo.agregarMiembro(new Programador("Un programador"))

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
        nuevoProgramador.aceptarInvitacion(invitacion)

        then:"queda agregado al equipo y se vuelven a validar todas las invitaciones que haya recibido"
    }

    void logrosConjuntos() {
        given:"un equipo ya ha conseguido logros en un desafío"

        when:"un programador nuevo se suma al equipo"

        then:"no hereda los logros"

        and:"no consigue puntos extra de desafíos finalizados"

        and:"sí gana la habilidad de conseguirlos para toda acción del equipo"
    }

    void desintegracionDeEquipos() {
        given:"Un equipo"

        and:"sin un determinado programador el equipo sigue siendo válido"

        when:"ese programador decide irse del equipo"

        then:"no puede participar más en nombre de ese equipo"

        and:"no pierde puntos ni logros conseguidos"
    }
}
