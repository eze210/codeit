package codeit

import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification

import static groovy.test.GroovyAssert.shouldFail

@Integration
@Rollback
class PuntuarDesafiosTestSpec extends Specification {

    Programador creador
    Desafio desafio
    Ejercicio ejercicio

    def setup() {
        creador = new Programador("Creador")
        desafio = creador.proponerDesafio("Desafío", "Descripción")
        ejercicio = creador.proponerEjercicioPara(desafio, "Ejercicio")
        ejercicio.agregarPrueba("string", "string")
    }

    def cleanup() {
    }

    void puntuacionesSoloDeLosParticipantesvotaUnParticipante() {
        Integer puntajeInicialDelDesafio = desafio.puntajeTotal
        //Integer puntajeInicialDeLaFacetaDesafiante = creador.blah

        given:"Un programador ha subido alguna resolución a algún ejercicio de un desafío"
        Programador programador = new Programador("Resolvedor")
        programador.proponerSolucionPara(desafio, "Solución").agregarResolucion(
                new Resolucion(ejercicio, "{x -> x}"))

        when:"intenta asignar un punto al desafío"
        Integer nuevoPuntajeDelDesafio = programador.asignarPuntoA(desafio)

        then:"al puntaje total del desafío se le sumará una unidad"
        nuevoPuntajeDelDesafio == puntajeInicialDelDesafio + 1

        //and:"se asignará un punto en la faceta desafiante del creador"
        //creador.blah == puntajeInicialDeLaFacetaDesafiante + 1
    }

    void puntuacionesSoloDeLosParticipantesVotaUnNoParticipante() {
        Integer puntajeInicialDelDesafio = desafio.puntajeTotal

        given:"Un programador no ha subido ninguna resolución a ningún ejercicio de un desafío"
        Programador programador = new Programador("Resolvedor")

        when:"intenta asignar un punto al desafío"
        then:"el punto asignado es rechazado"
        shouldFail(NoParticipaDelDesafio) {
            programador.asignarPuntoA(desafio)
        }

        and:"el puntaje total del desafío se mantiene igual"
        desafio.puntajeTotal == puntajeInicialDelDesafio
    }

    void puntuacionesSoloDeLosParticipantesVotaElCreador() {
        Integer puntajeInicialDelDesafio = desafio.puntajeTotal

        when:"El creador de un desafío intenta asignar un punto a su propio desafío"
        then:"el punto asignado es rechazado"
        shouldFail(InvolucraAlCreador) {
            creador.asignarPuntoA(desafio)
        }

        and:"el puntaje del desafío se mantiene igual"
        desafio.puntajeTotal == puntajeInicialDelDesafio
    }

}
