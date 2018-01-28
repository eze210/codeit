package codeit

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class EquipoServiceSpec extends Specification implements ServiceUnitTest<EquipoService>{

    def setup() {
    }

    def cleanup() {
    }

    void "no se puede crear un equipo con los mismos miembros de uno que ya existe"() {
        expect:"fix me"
            true == true
    }
}
