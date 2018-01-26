package codeit

import org.grails.testing.GrailsUnitTest
import org.joda.time.DateTime
import spock.lang.Specification

class VigenciaSpec extends Specification implements GrailsUnitTest {

    def setup() {
    }

    def cleanup() {
    }

    void "Una vigencia creada sin parámetros es infinita"() {
        when:"Se crea una vigencia sin parámetros"
        Vigencia vigencia = new Vigencia()

        then:"es de tipo infinita"
        vigencia.tipo == Vigencia.Tipo.Infinita
    }

    void "Una vigencia infinita contiene la fecha actual"() {
        when:"Una vigencia es infinita"
        Vigencia vigencia = new Vigencia()
        assert vigencia.tipo == Vigencia.Tipo.Infinita

        then:"Contiene la fecha actual"
        vigencia.contiene(DateTime.now())
    }

    void "Una fecha creada con un solo parámetro es un vencimiento"() {
        when:"Una vigencia es creada con un solo parámetro"
        Vigencia vigencia = new Vigencia(DateTime.now())

        then:"es de tipo vencimiento"
        vigencia.tipo == Vigencia.Tipo.Vencimiento
    }

    void "Un vencimiento creado con la fecha actual contiene una fecha del mes pasado pero no una del dia siguiente"() {
        when:"Una vigencia es creada como un vencimiento en la fecha actual"
        DateTime ahora = DateTime.now()
        DateTime mesPasado = ahora.minusMonths(1)
        DateTime diaSiguiente = ahora.plusDays(1)
        Vigencia vigencia = new Vigencia(ahora)
        assert vigencia.tipo == Vigencia.Tipo.Vencimiento

        then:"contiene una fecha del mes pasado pero no una del día siguiente"
        vigencia.contiene(mesPasado)
        !vigencia.contiene(diaSiguiente)
    }

    void "Una vigencia creada con dos parámetros es un plazo"() {
        when:"Una vigencia es creada con dos parámetros"
        Vigencia vigencia = new Vigencia(DateTime.now(), DateTime.now())

        then:"Es un plazo"
        vigencia.tipo == Vigencia.Tipo.Plazo
    }

    void "Un plazo es valido dentro del plazo y no antes ni después"() {
        when:"Una vigencia es un plazo entre el dia anterior y el siguiente a la fecha actual"
        DateTime ahora = DateTime.now()
        DateTime diaAnterior = ahora.minusDays(1)
        DateTime diaAnteriorAlAnterior = ahora.minusDays(2)
        DateTime diaSiguiente = ahora.plusDays(1)
        DateTime diaSiguienteAlSiguiente = ahora.plusDays(2)
        Vigencia vigencia = new Vigencia(diaAnterior, diaSiguiente)
        assert vigencia.tipo == Vigencia.Tipo.Plazo

        then:"contiene la fecha actual, no contiene anteayer, ni la fecha dentro de dos días"
        vigencia.contiene(ahora)
        !vigencia.contiene(diaAnteriorAlAnterior)
        !vigencia.contiene(diaSiguienteAlSiguiente)
    }
}