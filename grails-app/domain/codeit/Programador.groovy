package codeit

import org.joda.time.DateTime

import javax.validation.constraints.NotNull

class Programador extends Participante {

    static hasMany = [equipos: Equipo, invitaciones: Invitacion, desafiosCreados: Desafio]

    static constraints = {
        equipos nullable: false
        invitaciones nullable: false
    }

    Programador(String nombre) {
        this.nombre = nombre
        this.soluciones = new HashSet<>()
        this.equipos = new HashSet<>()
        this.invitaciones = new HashSet<>()
        this.desafiosCreados = new HashSet<>()
    }

    Set<Programador> programadoresInvolucrados() {
        [this] as Set<Programador>
    }

    Desafio proponerDesafio(String titulo, String descripcion, DateTime fechaDesde, DateTime fechaHasta) {
        new Desafio(titulo, descripcion, this, fechaDesde, fechaHasta)
    }

    Desafio proponerDesafio(String titulo, String descripcion, DateTime fechaHasta) {
        new Desafio(titulo, descripcion, this, fechaHasta)
    }

    Desafio proponerDesafio(String titulo, String descripcion) {
        new Desafio(titulo, descripcion, this)
    }

    Ejercicio proponerEjercicioPara(@NotNull Desafio desafio, String enunciado) {
        Ejercicio nuevoEjercicio = new Ejercicio(desafio, enunciado)
        desafio.agregarEjercicio(nuevoEjercicio)
        nuevoEjercicio
    }

}
