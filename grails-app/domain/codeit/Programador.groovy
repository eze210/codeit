package codeit

import javax.validation.constraints.NotNull

class Programador extends Participante {

    String nombre

    static constraints = {
        nombre nullable: false, blank: false, unique: true
    }

    Set<Programador> programadoresInvolucrados() {
        [this] as Set<Programador>
    }

    Desafio proponerDesafio() {
        new Desafio(creador: this)
    }

    Ejercicio proponerEjercicioPara(@NotNull Desafio desafio) {
        Ejercicio nuevoEjercicio = new Ejercicio()
        assert nuevoEjercicio != null
        assert desafio.agregarEjercicio(nuevoEjercicio)
        nuevoEjercicio
    }

}
