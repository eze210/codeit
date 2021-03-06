package codeit

import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification

import static groovy.test.GroovyAssert.shouldFail

@Integration
@Rollback
class ResolverDesafiosTestSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void requisitosParaParticiparCasoFeliz() {
        given:"Un desafío requiere un nivel de insignias para participar"
        Programador creador = new Programador("Creador")
        Desafio desafio = creador.proponerDesafio("Título", "Descripción", new LinkedHashSet<Insignia>([
                TipoFaceta.Prolijo.insigniasAutomaticasPosibles[0]
        ]))

        and:"un programador posee ese nivel de insignias"
        Programador resolvedor = new Programador("Resolvedor")
        resolvedor.otorgarInsignia(
                TipoFaceta.Prolijo.insigniasAutomaticasPosibles[0].nombre
        )

        and:"ese programador no es quien creó el desafío"
        assert creador != resolvedor

        and:"no comparte ningún equipo con el creador"
        assert !creador.comparteAlgunEquipoCon(resolvedor)

        and:"no pertenece a un equipo que ya subió una solución"
        assert !desafio.resultados*.solucion*.participante*.programadoresInvolucrados()*.contains(resolvedor)

        when:"el programador quiere subir una solución al desafío"
        Solucion solucion = resolvedor.proponerSolucionPara(desafio, "Descripción de la solución")

        then:"es aceptada y validada"
        desafio.obtenerResultadoActualDeSolucion(solucion) != null
        (desafio.resultados*.solucion).contains(solucion)
    }

    void requisitosParaParticiparSinInsignias() {
        given:"Un desafío requiere un nivel de insignias para participar"
        Programador creador = new Programador("Creador")
        Desafio desafio = creador.proponerDesafio("Título", "Descripción", new LinkedHashSet<Insignia>([
                TipoFaceta.Prolijo.insigniasAutomaticasPosibles[0]
        ]))

        and:"un programador no posee ese nivel de insignias"
        Programador resolvedor = new Programador("Resolvedor")

        when:"el programador quiere subir una solución al desafío"

        then:"no es aceptada"
        shouldFail(NoPoseeInsignias) {
            resolvedor.proponerSolucionPara(desafio, "Descripción de la solución")
        }
    }

    void requisitosParaParticiparResolvedorEsCreador() {
        given:"Un desafío requiere un nivel de insignias para participar"
        Programador creador = new Programador("Creador")
        Desafio desafio = creador.proponerDesafio("Título", "Descripción", new LinkedHashSet<Insignia>([
                TipoFaceta.Prolijo.insigniasAutomaticasPosibles[0]
        ]))

        and:"un programador posee ese nivel de insignias"
        Programador resolvedor = creador
        resolvedor.otorgarInsignia(
                TipoFaceta.Prolijo.insigniasAutomaticasPosibles[0].nombre
        )

        and:"ese programador es quien creó el desafío"
        assert creador == resolvedor

        when:"el programador quiere subir una solución al desafío"

        then:"no puede proponerla porque es el creador"
        shouldFail(ComparteMiembrosConCreador) {
            resolvedor.proponerSolucionPara(desafio, "Descripción de la solución")
        }
    }

    void requisitosParaParticiparComparteEquiposConCreador() {
        given:"Un desafío requiere un nivel de insignias para participar"
        Programador creador = new Programador("Creador")
        Desafio desafio = creador.proponerDesafio("Título", "Descripción", new LinkedHashSet<Insignia>([
                TipoFaceta.Prolijo.insigniasAutomaticasPosibles[0]
        ]))

        and:"un programador posee ese nivel de insignias"
        Programador resolvedor = new Programador("Resolvedor")
        resolvedor.otorgarInsignia(
                TipoFaceta.Prolijo.insigniasAutomaticasPosibles[0].nombre
        )

        and:"ese programador no es quien creó el desafío"
        assert creador != resolvedor

        and:"comparte algún equipo con el creador"
        creador.crearEquipo("El equipo").agregarMiembro(resolvedor)
        assert creador.comparteAlgunEquipoCon(resolvedor)

        when:"el programador quiere subir una solución al desafío"

        then:"no puede porque comparte equipos con el creador"
        shouldFail(ComparteEquipoConCreador) {
            resolvedor.proponerSolucionPara(desafio, "Descripción de la solución")
        }
    }

    void requisitosParaParticiparPerteneceAEquipoSolucionador() {
        given:"Un desafío requiere un nivel de insignias para participar"
        Programador creador = new Programador("Creador")
        Desafio desafio = creador.proponerDesafio("Título", "Descripción", new LinkedHashSet<Insignia>([
                TipoFaceta.Prolijo.insigniasAutomaticasPosibles[0]
        ]))

        and:"un programador posee ese nivel de insignias"
        Programador resolvedor = new Programador("Resolvedor")
        resolvedor.otorgarInsignia(
                TipoFaceta.Prolijo.insigniasAutomaticasPosibles[0].nombre
        )

        and:"un equipo al que pertenece ya subió una solución al desafío"
        Programador programadorResolvedorPrevio = new Programador("Previo")
        Equipo equipoResolvedorPrevio = programadorResolvedorPrevio.crearEquipo("El equipo")
        equipoResolvedorPrevio.agregarMiembro(resolvedor)
        assert equipoResolvedorPrevio.proponerSolucionPara(desafio, "Solución previa")

        and:"ese programador no es quien creó el desafío"
        assert creador != resolvedor

        and:"no comparte ningún equipo con el creador"
        assert !creador.comparteAlgunEquipoCon(resolvedor)

        and:"pertenece a un equipo que ya subió una solución"
        assert desafio.resultados*.solucion*.participante*.programadoresInvolucrados()*.contains(resolvedor)

        when:"el programador quiere subir una solución al desafío"

        then:"no puede porque pertenece a ese equipo"
        shouldFail(ComparteMiembrosConParticipante) {
            resolvedor.proponerSolucionPara(desafio, "Descripción de la solución")
        }
    }

    void combinacionDeInsigniasCasoFeliz() {
        given:"un desafío requiere dos niveles de insignia distintos A y B"
        Desafio desafio = (new Programador("Creador")).proponerDesafio("Desafío", "Descripción", new LinkedHashSet<Insignia>([
                TipoFaceta.Prolijo.insigniasAutomaticasPosibles[0],
                TipoFaceta.Creativo.insigniasAutomaticasPosibles[0]
        ]))

        and:"un equipo X tiene miembros de forma que ellos en conjunto alcanzan el nivel de insignia A y B"
        Programador programadorProlijo = new Programador("Prolijo")
        programadorProlijo.otorgarInsignia(
                TipoFaceta.Prolijo.insigniasAutomaticasPosibles[0].nombre
        )
        Programador programadorCreativo = new Programador("Creativo")
        programadorCreativo.otorgarInsignia(
                TipoFaceta.Creativo.insigniasAutomaticasPosibles[0].nombre
        )
        Equipo equipo = programadorCreativo.crearEquipo("El equipo").agregarMiembro(programadorProlijo)

        when:"cualquier miembro sube una solución al desafío"
        Solucion solucion = equipo.proponerSolucionPara(desafio, "Una solución")

        then:"la solución es recibida y probada"
        desafio.obtenerResultadoActualDeSolucion(solucion) != null
        (desafio.resultados*.solucion).contains(solucion)
    }

    void combinacionDeInsigniasUnProgramadorSoloNoAlacnza() {
        given:"un desafío requiere dos niveles de insignia distintos A y B"
        Desafio desafio = (new Programador("Creador")).proponerDesafio("Desafío", "Descripción", new LinkedHashSet<Insignia>([
                TipoFaceta.Prolijo.insigniasAutomaticasPosibles[0],
                TipoFaceta.Creativo.insigniasAutomaticasPosibles[0]
        ]))

        and:"un programador tiene la insignia A"
        Programador programadorProlijo = new Programador("Prolijo")
        programadorProlijo.otorgarInsignia(
                TipoFaceta.Prolijo.insigniasAutomaticasPosibles[0].nombre
        )

        and:"otro programador tiene la insignia B"
        Programador programadorCreativo = new Programador("Creativo")
        programadorCreativo.otorgarInsignia(
                TipoFaceta.Creativo.insigniasAutomaticasPosibles[0].nombre
        )

        when:"cada uno sube una solución al desafío individualmente"

        then:"no pueden porque no les alcanzan las insignias"
        shouldFail(NoPoseeInsignias) {
            programadorCreativo.proponerSolucionPara(desafio, "Una solución")
        }
        shouldFail(NoPoseeInsignias) {
            programadorProlijo.proponerSolucionPara(desafio, "Una solución")
        }
    }

}
