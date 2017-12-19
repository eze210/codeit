package codeit

import com.intellij.util.containers.OrderedSet

class Desafio {

    static hasMany = [ejercicios: Ejercicio, resultados: Resultado]

    String título
    String descripcion

    Programador creador
    Range<Date> vigencia

    OrderedSet<Ejercicio> ejercicios

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
            return resultado
        }
        Iterator<Resolucion> iterador = solucion.resoluciones.iterator()
        resultado.puntaje = ejercicios.inject(0) { ac, el -> ac + el.validarResolucion(iterador.next()) }
        resultado.valido = true
        return resultado
    }

    Boolean puedeParticipar(Participante participante) {
        return !resultados.find { it.solucion.participante.involucraA(participante) }
    }

}
