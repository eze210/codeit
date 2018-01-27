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
        when:"existen dos programadores que quieren participar juntos en futuros desafíos"

        and:"juntos forman un equipo válido"

        and:"un programador invita al otro a formar un equipo"

        then:"el segundo recibe la invitación, que queda en estado pendiente"
    }

    void "Conformación de equipos - Invitación equipo a programador"() {
        when:"Existe un equipo creado"

        and:"es válido que invite a un programador"

        and:"el equipo invita a ese programador"

        then:"el programador recibe la invitación"
    }

    void "Conformación de equipos - Aceptar invitaciones"() {
        when:"Un programador recibió una invitación"

        and:"la acepta"

        then:"queda agregado al equipo y se vuelven a validar todas las invitaciones que haya recibido"
    }

    void "Logros conjuntos"() {
        when:"un equipo ya ha conseguido logros en un desafío"

        and:"un programador nuevo se suma al equipo"

        then:   "no hereda los logros ni consigue puntos extra de desafíos finalizados" +
                "pero sí gana la habilidad de conseguirlos para toda acción actual o " +
                "futura del equipo"
    }

    void "Desintegración de equipos"() {
        when:"Existe un equipo"

        and:"sin un determinado programador el equipo sigue siendo válido"

        and:"ese programador decide irse del equipo"

        then:"no puede participar más en nombre de ese equipo pero no pierde puntos ni logros conseguidos"
    }
}
