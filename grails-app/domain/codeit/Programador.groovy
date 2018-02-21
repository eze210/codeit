package codeit

import org.joda.time.DateTime

import javax.validation.constraints.NotNull

class Programador extends Participante {

    Set<Insignia> insignias
    Set<Faceta> facetas


    static hasMany = [equipos: Equipo,
                      invitaciones: Invitacion,
                      desafiosCreados: Desafio,
                      facetas: Faceta]


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
        this.facetas = new LinkedHashSet<>([new Faceta(TipoFaceta.Ganador),
                                            new Faceta(TipoFaceta.Desafiante),
                                            new Faceta(TipoFaceta.Solucionador),
                                            new Faceta(TipoFaceta.Creativo),
                                            new Faceta(TipoFaceta.Prolijo)])
    }


    Set<Programador> programadoresInvolucrados() {
        [this] as Set<Programador>
    }


    Ejercicio proponerEjercicioPara(@NotNull Desafio desafio, String enunciado) {
        Ejercicio nuevoEjercicio = new Ejercicio(desafio, enunciado)
        desafio.agregarEjercicio(nuevoEjercicio)
        nuevoEjercicio
    }


    Integer obtenerPuntajeParaFaceta(TipoFaceta tipoFaceta) {
        facetas.find({it.tipo == tipoFaceta}).getPuntosAcumulados()
    }


    Integer asignarPuntoEnFaceta(TipoFaceta tipoFaceta) {
        Faceta faceta = facetas.find({it.tipo == tipoFaceta})
        insignias.addAll(faceta.asignarPuntos(1))
        faceta.puntosAcumulados
    }


    def asignarInsigniaASolucion(Insignia insignia, Solucion solucion) {
        assert solucion.desafio.creador == this
        solucion.asignarInsignia(insignia)
    }


    Equipo aceptarInvitacion(Invitacion invitacion) {
        assert invitacion.invitado == this
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


    Desafio proponerDesafio(String titulo, String descripcion, Set<Insignia> insigniasRequeridas, DateTime fechaDesde, DateTime fechaHasta) {
        new Desafio(titulo, descripcion, this, insigniasRequeridas, fechaDesde, fechaHasta)
    }


    Desafio proponerDesafio(String titulo, String descripcion, Set<Insignia> insigniasRequeridas, DateTime fechaHasta) {
        new Desafio(titulo, descripcion, this, insigniasRequeridas, fechaHasta)
    }


    Desafio proponerDesafio(String titulo, String descripcion, Set<Insignia> insigniasRequeridas) {
        new Desafio(titulo, descripcion, this, insigniasRequeridas)
    }


    Desafio proponerDesafio(String titulo, String descripcion, DateTime fechaDesde, DateTime fechaHasta) {
        new Desafio(titulo, descripcion, this, new LinkedHashSet<Insignia>(), fechaDesde, fechaHasta)
    }


    Desafio proponerDesafio(String titulo, String descripcion, DateTime fechaHasta) {
        new Desafio(titulo, descripcion, this, new LinkedHashSet<Insignia>(), fechaHasta)
    }


    Desafio proponerDesafio(String titulo, String descripcion) {
        new Desafio(titulo, descripcion, this, new LinkedHashSet<Insignia>())
    }

}
