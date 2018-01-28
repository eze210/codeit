package codeit

import org.joda.time.DateTime
import com.google.common.collect.Range

public class Vigencia implements Serializable {

    static enum Tipo {
        Plazo,
        Vencimiento,
        Infinita
    }

    Tipo tipo
    Range<DateTime> rangoDeFechas

    Vigencia(DateTime fechaHasta) {
        this.tipo = Tipo.Vencimiento
        this.rangoDeFechas = Range.lessThan(fechaHasta)
    }

    Vigencia(DateTime fechaDesde, DateTime fechaHasta) {
        this.tipo = Tipo.Plazo
        this.rangoDeFechas = Range.closed(fechaDesde, fechaHasta)
    }

    Vigencia() {
        this.tipo = Tipo.Infinita
        this.rangoDeFechas = Range.all()
    }

    Boolean contiene(DateTime fecha) {
        this.rangoDeFechas.contains(fecha)
    }

}
