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

    // TODO: Agregar invitaciones
    void "Conformación de equipos - Invitación programador a programador"() {
        given:"Dos programadores que quieren participar juntos en futuros desafíos"
        Programador programadorQueInvita = new Programador("Invita")
        Programador programadorInvitado = new Programador("Invitado")

        and:"juntos forman un equipo válido"
        assert Equipo.formanEquipoValido(programadorQueInvita, programadorInvitado)

        when:"un programador invita al otro a formar un equipo"
        Invitacion invitacion = programadorQueInvita.invitar(programadorInvitado)

        then:"el segundo recibe la invitación"
        programadorInvitado.invitaciones.contains(invitacion)

        and:"que queda en estado pendiente"
        invitacion.estado == Invitacion.Estado.Pendiente
    }

    void "Conformación de equipos - Invitación equipo a programador"() {
        given:"Un equipo creado"

        and:"es válido que invite a un programador"

        when:"el equipo invita a ese programador"

        then:"el programador recibe la invitación"
    }

    void "Conformación de equipos - Aceptar invitaciones"() {
        given:"Un programador recibió una invitación"

        when:"la acepta"

        then:"queda agregado al equipo y se vuelven a validar todas las invitaciones que haya recibido"
    }

    void "Logros conjuntos"() {
        given:"un equipo ya ha conseguido logros en un desafío"

        when:"un programador nuevo se suma al equipo"

        then:"no hereda los logros"

        and:"no consigue puntos extra de desafíos finalizados"

        and:"sí gana la habilidad de conseguirlos para toda acción del equipo"
    }

    void "Desintegración de equipos"() {
        given:"Un equipo"

        and:"sin un determinado programador el equipo sigue siendo válido"

        when:"ese programador decide irse del equipo"

        then:"no puede participar más en nombre de ese equipo"

        and:"no pierde puntos ni logros conseguidos"
    }
}
