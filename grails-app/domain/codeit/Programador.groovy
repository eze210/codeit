package codeit

import org.joda.time.DateTime

import javax.validation.constraints.NotNull

class Programador extends Participante {

    String nombre

    static constraints = {
        nombre nullable: false, blank: false, unique: true
    }

    Programador(String nombre) {
        this.nombre = nombre
    }

    Set<Programador> programadoresInvolucrados() {
        [this] as Set<Programador>
    }

    Desafio proponerDesafio(String titulo, String descripcion, DateTime fechaDesde, DateTime fechaHasta) {
        new Desafio(titulo, descripcion, this, fechaDesde, fechaHasta)
    }

    Ejercicio proponerEjercicioPara(@NotNull Desafio desafio) {
        Ejercicio nuevoEjercicio = new Ejercicio("El enunciado")
        desafio.agregarEjercicio(nuevoEjercicio)
        nuevoEjercicio
    }

}
