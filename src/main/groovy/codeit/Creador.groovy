package codeit

import org.joda.time.DateTime

import javax.validation.constraints.NotNull

/** Interfaz Creador. */
interface Creador {
    Ejercicio proponerEjercicioPara(@NotNull Desafio desafio, String enunciado)
    Desafio proponerDesafio(String titulo, String descripcion, Set<Insignia> insigniasRequeridas, DateTime fechaDesde, DateTime fechaHasta)
    Desafio proponerDesafio(String titulo, String descripcion, Set<Insignia> insigniasRequeridas)
    Desafio proponerDesafio(String titulo, String descripcion, DateTime fechaDesde, DateTime fechaHasta)
    Desafio proponerDesafio(String titulo, String descripcion)
    void elegirMejorSolucion(Solucion solucion)
}