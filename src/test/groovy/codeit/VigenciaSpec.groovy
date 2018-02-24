package codeit

import org.grails.testing.GrailsUnitTest
import org.joda.time.DateTime
import spock.lang.Specification

class VigenciaSpec extends Specification implements GrailsUnitTest {

    def setup() {
    }

    def cleanup() {
    }

    void unaVigenciaCreadaSinParametrosEsInfinita() {
        when:"Se crea una vigencia sin parámetros"
        Vigencia vigencia = new Vigencia()

        then:"es de tipo infinita"
        vigencia.tipo == Vigencia.Tipo.Infinita
    }

    void unaVigenciaInfinitaContieneLaFechaActual() {
        given:"Una vigencia infinita"
        Vigencia vigencia = new Vigencia()
        assert vigencia.tipo == Vigencia.Tipo.Infinita

        expect:"Contiene la fecha actual"
        vigencia.contiene(DateTime.now())
    }

    void unaFechaCreadaConUnSoloParametroEsUnVencimiento() {
        when:"Una vigencia es creada con un solo parámetro"
        Vigencia vigencia = new Vigencia(null, DateTime.now())

        then:"es de tipo vencimiento"
        vigencia.tipo == Vigencia.Tipo.Vencimiento
    }

    void unVencimientoCreadoConLaFechaActualContieneUnaFechaDelMesPasadoPeroNoUnaDelDiaSiguiente() {
        given:"Una vigencia que es creada como un vencimiento en la fecha actual"
        DateTime ahora = DateTime.now()
        DateTime mesPasado = ahora.minusMonths(1)
        DateTime diaSiguiente = ahora.plusDays(1)
        Vigencia vigencia = new Vigencia(null, ahora)
        assert vigencia.tipo == Vigencia.Tipo.Vencimiento

        expect:"contiene una fecha del mes pasado"
        vigencia.contiene(mesPasado)

        and:"pero no una del día siguiente"
        !vigencia.contiene(diaSiguiente)
    }

    void UnaVigenciaCreadaConDosParametrosEsUnPlazo() {
        when:"Una vigencia es creada con dos parámetros"
        Vigencia vigencia = new Vigencia(DateTime.now(), DateTime.now())

        then:"Es un plazo"
        vigencia.tipo == Vigencia.Tipo.Plazo
    }

    void "Un plazo es valido dentro del plazo y no antes ni después"() {
        given:"Una vigencia que es un plazo entre el dia anterior y el siguiente a la fecha actual"
        DateTime ahora = DateTime.now()
        DateTime diaAnterior = ahora.minusDays(1)
        DateTime diaAnteriorAlAnterior = ahora.minusDays(2)
        DateTime diaSiguiente = ahora.plusDays(1)
        DateTime diaSiguienteAlSiguiente = ahora.plusDays(2)
        Vigencia vigencia = new Vigencia(diaAnterior, diaSiguiente)
        assert vigencia.tipo == Vigencia.Tipo.Plazo

        expect:"contiene la fecha actual"
        vigencia.contiene(ahora)

        and:"no contiene anteayer"
        !vigencia.contiene(diaAnteriorAlAnterior)

        and:"tampoco la fecha dentro de dos días"
        !vigencia.contiene(diaSiguienteAlSiguiente)
    }

}
