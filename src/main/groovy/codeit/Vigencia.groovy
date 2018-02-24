package codeit

import org.hibernate.annotations.Immutable
import org.joda.time.DateTime
import com.google.common.collect.Range

@Immutable
public class Vigencia implements Serializable {

    static enum Tipo {
        Plazo,
        Inicio,
        Vencimiento,
        Infinita
    }

    Tipo tipo
    Range<DateTime> rangoDeFechas

    Vigencia(DateTime fechaDesde, DateTime fechaHasta) {
        if (fechaDesde && fechaHasta) {
            this.tipo = Tipo.Plazo
            this.rangoDeFechas = Range.closed(fechaDesde, fechaHasta)
        } else if (fechaDesde) {
            this.tipo = Tipo.Inicio
            this.rangoDeFechas = Range.atLeast(fechaDesde)
        } else if (fechaHasta) {
            this.tipo = Tipo.Vencimiento
            this.rangoDeFechas = Range.atMost(fechaHasta)
        } else {
            init()
        }

    }

    Vigencia() {
        init()
    }

    private init() {
        this.tipo = Tipo.Infinita
        this.rangoDeFechas = Range.all()
    }

    Boolean contiene(DateTime fecha) {
        this.rangoDeFechas.contains(fecha)
    }

    Boolean estaVigente() {
        this.contiene(DateTime.now())
    }

    Boolean posteriorA(DateTime date) {
        this.rangoDeFechas.hasLowerBound() ? date <= this.rangoDeFechas.lowerEndpoint() : false
    }

}
