package codeit

import org.joda.time.DateTime

class Desafio {
    // TODO: agregar requisitos para participar

    static hasMany = [ejercicios: Ejercicio, resultados: Resultado]

    String titulo
    String descripcion
    Programador creador
    Vigencia vigencia

    Set<Ejercicio> ejercicios
    Set<Resultado> resultados

    static constraints = {
        titulo nullable: false, blank: false, unique: true
        descripcion nullable: false, blank: false, unique: true
        creador nullable: false
        vigencia nullable: false, blank: false
    }

    Desafio(String titulo, String descripcion, Programador creador, DateTime fechaDesde, DateTime fechaHasta) {
        this.titulo = titulo
        this.descripcion = descripcion
        this.creador = creador
        this.vigencia = new Vigencia(fechaDesde, fechaHasta);
        this.ejercicios = new LinkedHashSet<>()
        this.resultados = new LinkedHashSet<>()
    }

    Resultado proponerSolucion(Solucion solucion) {
        Resultado resultado = validarSolucion(solucion)
        resultados.add(resultado)
        resultado
    }

    Resultado validarSolucion(Solucion solucion) {
        resultados.find { it.solucion == solucion } ?: solucion.validar(ejercicios)
    }

    Boolean puedeParticipar(Participante participante) {
        !resultados.find { it.solucion.participante.involucraA(participante) }
    }

    Boolean estaVigente() {
        assert vigencia != null
        vigencia.contiene(DateTime.now())
    }

    Boolean agregarEjercicio(Ejercicio ejercicio) {
        assert ejercicios != null
        ejercicios.add(ejercicio)
    }

}
