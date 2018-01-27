package codeit

import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification

@Integration
@Rollback
class PuntuarDesafiosTestSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "Puntuaciones sólo de los participantes - Vota un participante"() {
        given:"Un programador ha subido alguna resolución a algún ejercicio de un desafío"

        when:"intenta asignar un punto al desafío"

        then:"al puntaje total del desafío se le sumará una unidad"

        and:"se asignará un punto en la faceta desafiante del creador"
    }

    void "Puntuaciones sólo de los participantes - Vota un no-participante"() {
        given:"Un programador no ha subido ninguna resolución a ningún ejercicio de un desafío"

        when:"intenta asignar un punto al desafío"

        then:"el punto asignado es rechazado"

        and:"el puntaje total del desafío se mantiene igual"
    }

    void "Puntuaciones sólo de los participantes - Vota el creador"() {
        given:"Un programador creó un desafío"

        when:"dicho programador intenta asignar un punto a su propio desafío"

        then:"el punto asignado es rechazado"

        and:"el puntaje del desafío se mantiene igual"
    }

}
