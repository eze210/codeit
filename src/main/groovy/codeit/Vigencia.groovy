package codeit

import org.joda.time.DateTime

public class Vigencia implements Serializable {

    static enum Tipo {
        Plazo,
        Vencimiento,
        Infinita
    }

    Tipo tipo
    DateTime vencimiento
    Range<DateTime> rangoDeFechas

    Vigencia(DateTime fechaHasta) {
        this.tipo = Tipo.Vencimiento
        this.vencimiento = fechaHasta
        this.rangoDeFechas = null
    }

    Vigencia(DateTime fechaDesde, DateTime fechaHasta) {
        this.tipo = Tipo.Plazo
        this.vencimiento = fechaHasta
        this.rangoDeFechas = new ObjectRange(fechaDesde, fechaHasta)
    }

    Vigencia() {
        this.tipo = Tipo.Infinita
        this.vencimiento = null
        this.rangoDeFechas = null
    }

    Boolean contiene(DateTime fecha) {
        switch (this.tipo) {
            case Tipo.Infinita:
                return true
            case Tipo.Plazo:
                return rangoDeFechas.containsWithinBounds(fecha)
            case Tipo.Vencimiento:
                return fecha.isBefore(vencimiento)
            default:
                return false
        }
    }

}
