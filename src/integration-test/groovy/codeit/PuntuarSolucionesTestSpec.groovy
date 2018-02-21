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

    void otorgarInsigniasRequiereAlcanzarUnPuntaje() {
        given:"Un desafío alcanzó un puntaje P establecido"
        int P = 10
        Programador programadorCreador = new Programador("Nombre")
        Desafio desafio = programadorCreador.proponerDesafio("Desafío", "Descripción")
        for (Integer i = 0; i < P; ++i)
            desafio.asignarPunto()

        and:"tiene insignias habilitadas"
        assert desafio.obtenerInsigniasHabilitadas().size() > 0

        and: "tiene alguna solución propuesta"
        Programador programadorResolvedor = new Programador("Otro Nombre")
        Solucion solucion = programadorResolvedor.proponerSolucionPara(desafio, "Una solución")

        when:"el creador del desafío intenta asignar una insignia E habilitada a una solución"
        Insignia E = desafio.obtenerInsigniasHabilitadas()[0]
        programadorCreador.asignarInsigniaASolucion(E, solucion)

        then:"la insignia será otorgada a cada programador involucrado en el participante que creó la solución"
        programadorResolvedor.obtenerInsignias().contains(E)
    }

    void mejorSolucion() {
        given:"Existen soluciones que resuelven la totalidad de un desafío"

        when:"el creador del desafío elige entre esas soluciones la mejor"

        then:"al participante que la propuso se le otorga un punto en la faceta de ganador"
    }

    void puntajesDeLaComunidad() {
        given:"Un programador tiene determinada cantidad de puntos en una faceta"
        Programador elProgramador = new Programador("El programador")
        InsigniaAutomatica insigniaAutomatica = TipoFaceta.Creativo.insigniasAutomaticasPosibles[0]
        Integer umbral = insigniaAutomatica.umbral
        for (Integer i = 0; i < umbral - 1; ++i)
            elProgramador.asignarPuntoEnFaceta(TipoFaceta.Creativo)

        and:"con esos puntos no alcanza una insignia"
        assert elProgramador.obtenerPuntajeParaFaceta(TipoFaceta.Creativo) == umbral - 1
        assert !elProgramador.obtenerInsignias().contains(insigniaAutomatica)

        when:"se le asigna un punto"
        Integer nuevoPuntaje = elProgramador.asignarPuntoEnFaceta(TipoFaceta.Creativo)

        and:"con ese punto se alcanza el umbral de una insignia"
        assert nuevoPuntaje == umbral

        then:"la insignia es otorgada al programador que recibió el punto"
        elProgramador.obtenerInsignias().contains(insigniaAutomatica)
    }

}
