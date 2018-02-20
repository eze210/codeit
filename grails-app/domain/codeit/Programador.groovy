package codeit

import org.joda.time.DateTime

import javax.validation.constraints.NotNull

class Programador extends Participante {

    String nombre
    Set<Equipo> equipos
    Set<Invitacion> invitaciones
    Set<Insignia> insignias

    static hasMany = [equipos: Equipo, invitaciones: Invitacion]

    static constraints = {
        nombre nullable: false, blank: false, unique: true
    }

    Programador(String nombre) {
        this.nombre = nombre
        this.equipos = new HashSet<>()
        this.invitaciones = new HashSet<>()
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

    Boolean tieneInsignia(Insignia insignia) {
        insignias.contains(insignia)
    }

}
