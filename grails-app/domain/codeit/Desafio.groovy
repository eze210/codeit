package codeit


class Desafio {

    static hasMany = [ejercicios: Ejercicio, resultados: Resultado]

    String título
    String descripcion

    Programador creador
    Range<Date> vigencia

    LinkedHashSet<Ejercicio> ejercicios

    Set<Resultado> resultados

    static constraints = {
        título nullable: false, blank: false, unique: true
        descripcion nullable: false, blank: false, unique: true
    }

    Boolean proponerSolucion(Solucion solucion) {
        Resultado resultado = Resultado(solucion: solucion)
        return resultados.add(resultado)
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
        return resultado
    }

    Boolean puedeParticipar(Participante participante) {
        return !resultados.find { it.solucion.participante.involucraA(participante) }
    }

}
