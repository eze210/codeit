package codeit

import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification

@Integration
@Rollback
class ResolverDesafiosTestSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    // TODO: escribir el código, agregar insignias y reglas para participar
    void "Requisitos para participar"() {
        given:"un desafío requiere un nivel de insignias para participar"

        and:"un programador posee ese nivel de insignias"

        and:"ese programador no es quien creó el desafío"

        and:"no comparte ningún equipo con el creador"

        and:"no pertenece a un equipo que ya subió una solución"

        when:"el programador quiere subir una solución al desafío"

        then:"es aceptada y validada"
    }

    // TODO: probar los casos negativos

    // TODO: agregar insignias combinadas
    void "Combinación de insignias"() {
        given:"un desafío requiere dos niveles de insignia distintos A y B"

        and:"un equipo X tiene miembros de forma que ellos en conjunto alcanzan el nivel de insignia A y B"

        when:"cualquier miembro sube una solución al desafío"

        then:"la solución es recibida y probada"
    }

    // TODO: probar los casos negativos

}
