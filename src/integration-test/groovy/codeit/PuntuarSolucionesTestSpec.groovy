package codeit

import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification

@Integration
@Rollback
class PuntuarSolucionesTestSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "Otorgar insignias requiere alcanzar un puntaje"() {
        when:"Un desafío alcanzó un puntaje P establecido"

        and:"el creador del desafío intenta asignar una insignia E habilitada a una solución"

        then:"la insignia será otorgada a cada programador involucrado en el participante que creó la solución"
    }

    void "Mejor solución"() {
        when:"Existen soluciones que resuelven la totalidad de un desafío"

        and:"el creador del desafío elige entre esas soluciones la mejor"

        then:"al participante que la propuso se le otorga un punto en la faceta de ganador"
    }

    void "Puntajes de la comunidad"() {
        when:"Un programador tiene determinada cantidad de puntos en una faceta"

        and:"otro programador le asigna un punto"

        and:"con ese punto se alcanza el umbral de una insignia"

        then:"la insignia es otorgada al programador que recibió el punto"
    }

}
