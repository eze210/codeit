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
            desafio.otorgarPuntoEnFaceta(TipoFaceta.Desafio)

        and:"tiene insignias habilitadas"
        assert desafio.obtenerInsignias().size() > 0

        and: "tiene alguna solución propuesta"
        Programador programadorResolvedor = new Programador("Otro Nombre")
        Solucion solucion = programadorResolvedor.proponerSolucionPara(desafio, "Una solución")

        when:"el creador del desafío intenta asignar una insignia E habilitada a una solución"
        Insignia E = desafio.obtenerInsignias()[0]
        programadorCreador.otorgarInsigniaASolucion(E, solucion)

        then:"la insignia será otorgada a cada programador involucrado en el participante que creó la solución"
        programadorResolvedor.obtenerInsignias().contains(E)
    }

    void mejorSolucion() {
        Programador creador = new Programador("Creador")
        Desafio desafio = creador.proponerDesafio("Título", "Descripción")
        Ejercicio ejercicio = creador.proponerEjercicioPara(desafio, "El enunciado")
        for (Integer i = 0; i < 10; ++i) {
            String salida = "Cadena " + i
            String entrada = "x=\"${salida}\""
            ejercicio.agregarPrueba(entrada, salida)
        }

        Programador resolvedor = new Programador("Resolvedor")
        Solucion solucion = resolvedor.proponerSolucionPara(desafio, "Mi solución")
        solucion.agregarResolucion(new Resolucion(ejercicio, "x"))

        given:"Existen soluciones que resuelven la totalidad de un desafío"
        assert desafio.validarSolucion(solucion)

        when:"el creador del desafío elige entre esas soluciones la mejor"
        creador.elegirMejorSolucion(solucion)

        then:"al participante que la propuso se le otorga un punto en la faceta de ganador"
        resolvedor.obtenerPuntajeEnFaceta(TipoFaceta.Ganador) == 1
    }

    void puntajesDeLaComunidad() {
        given:"Un programador tiene determinada cantidad de puntos en una faceta"
        Programador elProgramador = new Programador("El programador")
        InsigniaAutomatica insigniaAutomatica = TipoFaceta.Creativo.insigniasAutomaticasPosibles[0]
        Integer umbral = insigniaAutomatica.umbral
        for (Integer i = 0; i < umbral - 1; ++i)
            elProgramador.otorgarPuntoEnFaceta(TipoFaceta.Creativo)

        and:"con esos puntos no alcanza una insignia"
        assert elProgramador.obtenerPuntajeEnFaceta(TipoFaceta.Creativo) == umbral - 1
        assert !elProgramador.obtenerInsignias().contains(insigniaAutomatica)

        when:"se le asigna un punto"
        Integer nuevoPuntaje = elProgramador.otorgarPuntoEnFaceta(TipoFaceta.Creativo)

        and:"con ese punto se alcanza el umbral de una insignia"
        assert nuevoPuntaje == umbral

        then:"la insignia es otorgada al programador que recibió el punto"
        elProgramador.obtenerInsignias().contains(insigniaAutomatica)
    }

}
