package codeit

import org.joda.time.DateTime

import java.time.DateTimeException

class Desafio {

    static hasMany = [ejercicios: Ejercicio, resultados: Resultado]

    String titulo
    String descripcion

    Programador creador
    Range<DateTime> vigencia
    Set<Ejercicio> ejercicios
    Set<Resultado> resultados

    static constraints = {
        titulo nullable: false, blank: false, unique: true
        descripcion nullable: false, blank: false, unique: true
    }

    Desafio(String titulo, String descripcion, Programador creador, DateTime fechaDesde, DateTime fechaHasta) {
        this.titulo = titulo
        this.descripcion = descripcion
        this.creador = creador
        this.vigencia = new ObjectRange(fechaDesde, fechaHasta);
        this.ejercicios = new LinkedHashSet<>()
        this.resultados = new LinkedHashSet<>()
    }

    Boolean proponerSolucion(Solucion solucion) {
        Resultado resultado = Resultado(solucion: solucion)
        resultados.add(resultado)
    }

    Resultado validarSolucion(Solucion solucion) {
        Resultado resultado = resultados.find { it.solucion == solucion } ?: Resultado(solucion: solucion)
        if (solucion.resoluciones.count() != ejercicios.count()) {
            resultado.puntaje = 0
            resultado.valido = false
        } else {
            Iterator<Resolucion> iterador = solucion.resoluciones.iterator()
            resultado.puntaje = ejercicios.inject(0) { ac, el -> ac + el.validarResolucion(iterador++) }
            resultado.valido = true
        }
        resultado
    }

    Boolean puedeParticipar(Participante participante) {
        !resultados.find { it.solucion.participante.involucraA(participante) }
    }

    Boolean estaVigente() {
        assert vigencia != null
        vigencia.containsWithinBounds(DateTime.now())
    }

    Boolean agregarEjercicio(Ejercicio ejercicio) {
        assert ejercicios != null
        ejercicios.add(ejercicio)
    }

}
