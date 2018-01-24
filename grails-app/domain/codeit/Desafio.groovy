package codeit

import org.joda.time.DateTime

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

    Desafio(String titulo, String descripcion, Programador programador) {
        this.titulo = titulo
        this.descripcion = descripcion
        this.creador = programador
        this.vigencia = new ObjectRange(new DateTime(), new DateTime());
        this.ejercicios = new LinkedHashSet<>()
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
//        def now = new Formatter.DateTime()
//        vigencia.containsWithinBounds(now)
        true
    }

    Boolean agregarEjercicio(Ejercicio ejercicio) {
        assert ejercicios != null
        ejercicios.add(ejercicio)
    }

}
