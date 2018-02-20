package codeit

import org.joda.time.DateTime

import javax.validation.constraints.NotNull

class Programador extends Participante {

    Set<Insignia> insignias

    static hasMany = [equipos: Equipo, invitaciones: Invitacion, desafiosCreados: Desafio]

    static constraints = {
        equipos nullable: false
        invitaciones nullable: false
    }

    Programador(String nombre) {
        super(nombre)
        this.soluciones = new HashSet<>()
        this.equipos = new HashSet<>()
        this.invitaciones = new HashSet<>()
        this.desafiosCreados = new HashSet<>()
        this.insignias = new HashSet<>()
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

    Equipo aceptarInvitacion(Invitacion invitacion) {
        invitacion.aceptar()
    }

    Equipo crearEquipo(String nombreDelEquipo) {
        Equipo equipo = new Equipo(nombreDelEquipo)
        equipos.add(equipo)
        equipo
    }

    Invitacion invitar(Programador otroProgramador, Equipo equipo) {
        equipo.invitar(otroProgramador)
    }

}
