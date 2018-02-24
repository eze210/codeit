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
        Validador.crearInstancia(Validador.TipoValidador.Sincronico)
        creador = new Programador("Creador")
        desafio = creador.proponerDesafio("Desafío", "Descripción")
        ejercicio = creador.proponerEjercicioPara(desafio, "Ejercicio")
        ejercicio.agregarPrueba("x=\"string\"", "string")
    }


    def cleanup() {
    }


    void puntuacionesSoloDeLosParticipantesCuandoVotaUnParticipante() {
        Integer puntajeInicialDelDesafio = desafio.obtenerPuntajeEnFaceta(TipoFaceta.Desafio)
        Integer puntajeInicialDeLaFacetaDesafiante = creador.obtenerPuntajeEnFaceta(TipoFaceta.Desafiante)

        given:"Un programador ha subido alguna resolución a algún ejercicio de un desafío"
        Programador programador = new Programador("Resolvedor")
        programador.proponerSolucionPara(desafio, "Solución").agregarResolucion(
                new Resolucion(ejercicio, "x")
        )

        when:"intenta asignar un punto al desafío"
        Integer nuevoPuntajeDelDesafio = programador.otorgarPuntoADesafio(desafio)

        then:"al puntaje total del desafío se le sumará una unidad"
        nuevoPuntajeDelDesafio == puntajeInicialDelDesafio + 1

        and:"se asignará un punto en la faceta desafiante del creador"
        creador.obtenerPuntajeEnFaceta(TipoFaceta.Desafiante) == puntajeInicialDeLaFacetaDesafiante + 1
    }


    void puntuacionesSoloDeLosParticipantesVotaUnNoParticipante() {
        Integer puntajeInicialDelDesafio = desafio.obtenerPuntajeEnFaceta(TipoFaceta.Desafio)

        given:"Un programador no ha subido ninguna resolución a ningún ejercicio de un desafío"
        Programador programador = new Programador("Resolvedor")

        when:"intenta asignar un punto al desafío"
        then:"el punto asignado es rechazado"
        shouldFail(NoParticipaDelDesafio) {
            programador.otorgarPuntoADesafio(desafio)
        }

        and:"el puntaje total del desafío se mantiene igual"
        desafio.obtenerPuntajeEnFaceta(TipoFaceta.Desafio) == puntajeInicialDelDesafio
    }


    void puntuacionesSoloDeLosParticipantesVotaElCreador() {
        Integer puntajeInicialDelDesafio = desafio.obtenerPuntajeEnFaceta(TipoFaceta.Desafio)

        when:"El creador de un desafío intenta asignar un punto a su propio desafío"
        then:"el punto asignado es rechazado"
        shouldFail(ComparteMiembrosConCreador) {
            creador.otorgarPuntoADesafio(desafio)
        }

        and:"el puntaje del desafío se mantiene igual"
        desafio.obtenerPuntajeEnFaceta(TipoFaceta.Desafio) == puntajeInicialDelDesafio
    }

}
