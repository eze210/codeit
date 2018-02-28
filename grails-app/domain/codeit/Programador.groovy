package codeit

import codeit.seguridad.Usuario
import org.joda.time.DateTime

import javax.validation.constraints.NotNull

/** Clase Programador. */
class Programador extends Participante implements Creador, Puntuable {

    /** Usuario para la seguridad. */
    Usuario usuario

    /** Equipos a los que pertenece un programador. */
    Set<Equipo> equipos

    /** Invitaciones que se han hecho a un programador. */
    Set<Invitacion> invitaciones

    /** Desafíos creados por un programador. */
    Set<Desafio> desafiosCreados

    /** Puntaje del programador. */
    Puntaje puntaje

    /** Declaraciones necesarias para el mapeo relacional. */
    static hasMany = [equipos: Equipo,
                      invitaciones: Invitacion,
                      desafiosCreados: Desafio]

    /** Reglas para el mapeo relacional. */
    static constraints = {
        equipos nullable: false
        invitaciones nullable: false
    }

    /** Constructor de un Programador.
     *
     * @param nombre Nombre del nuevo programador.
     */
    Programador(String nombre) {
        super(nombre)

        this.usuario = Seguridad.crearUsuario(nombre, '1234')

        this.equipos = new HashSet<>()
        this.invitaciones = new HashSet<>()
        this.desafiosCreados = new HashSet<>()
        this.puntaje = new Puntaje([new Faceta(TipoFaceta.Ganador),
                               new Faceta(TipoFaceta.Desafiante),
                               new Faceta(TipoFaceta.Solucionador),
                               new Faceta(TipoFaceta.Creativo),
                               new Faceta(TipoFaceta.Prolijo)])
    }

    /** Asigna un punto al desafío.
     *
     * @param desafio Desafío al que se quiere asignar un punto.
     * @return El nuevo puntaje del desafío.
     */
    Integer otorgarPuntoADesafio(Desafio desafio) throws ComparteMiembrosConCreador, NoParticipaDelDesafio {
        if (comparteMiembrosCon(desafio.creador)) {
            throw new ComparteMiembrosConCreador()
        }

        if (!participaDe(desafio)) {
            throw new NoParticipaDelDesafio()
        }

        desafio.otorgarPuntoEnFaceta(TipoFaceta.Desafio)
    }

    /* ****************************************************************** *
     * Implementación de la Interfaz Participante.
     * ****************************************************************** */

    /** Devuelve al propio programador como una colección de programadores.
     *
     * @return El propio programador en una colección de programadores.
     */
    Set<Programador> programadoresInvolucrados() {
        [this] as Set<Programador>
    }

    /** Otorga una insignia a la solución y a todos los programadores involucrados.
     *
     * @param insignia Insignia a otorgar.
     * @param solucion Solución que se quiere premiar.
     * @return
     */
    def otorgarInsigniaASolucion(Insignia insignia, Solucion solucion) {
        assert solucion.desafio.creador == this
        solucion.otorgarInsignia(insignia)
    }

    /** Acepta la invitación indicada.
     *
     * @param invitacion Invitación que se desea aceptar.
     * @return El equipo correspondiente con su nuevo estado.
     */
    Equipo aceptarInvitacion(Invitacion invitacion) {
        assert invitacion.invitado == this
        Equipo equipo = invitacion.aceptar()
        invitaciones = invitaciones.findAll({it.validar()})
        equipo
    }

    /** Crea un equipo nuevo que contiene al programador.
     *
     * @param Nombre del nuevo equipo.
     * @return El nuevo equipo.
     */
    Equipo crearEquipo(String nombreDelEquipo) {
        Equipo equipo = new Equipo(nombreDelEquipo)
        equipos.add(equipo)
        equipo.agregarMiembro(this)
        equipo
    }

    /** Crea una invitación desde el programador actual al otro programador, en nombre del equipo especificado.
     *
     * @param otroProgramador Programador que debe ser invitado.
     * @param equipo Equipo en nombre del cual se hace la invitación
     *
     * @return La nueva invitación.
     */
    Invitacion invitar(Programador otroProgramador, Equipo equipo) {
        equipo.invitar(otroProgramador)
    }

    /** Quita un miembro de un equipo.
     *
     * @param equipo Equipo que el programador desea abandonar.
     *
     * @return El equipo sin el programador.
     */
    Equipo abandonarEquipo(Equipo equipo) {
        equipos.remove(equipo)
        equipo.removerMiembro(this)
    }

    /* ****************************************************************** *
     * Implementación de la Interfaz Puntuable
     * ****************************************************************** */

    @Override
    Set<Insignia> otorgarInsignia(Insignia insignia) {
        puntaje.otorgarInsignia(insignia)
    }

    @Override
    Set<Insignia> obtenerInsignias() {
        puntaje.obtenerInsignias()
    }

    @Override
    Insignia retirarInsignia(Insignia insignia) {
        puntaje.retirarInsignia(insignia)
    }

    @Override
    Integer otorgarPuntoEnFaceta(TipoFaceta tipoFaceta) {
        puntaje.otorgarPuntoEnFaceta(tipoFaceta)
    }

    @Override
    Integer obtenerPuntajeEnFaceta(TipoFaceta tipoFaceta) {
        puntaje.obtenerPuntajeEnFaceta(tipoFaceta)
    }

    /* ****************************************************************** *
     * Implementación de la Interfaz Creador
     * ****************************************************************** */

    /** Propone un nuevo ejercicio con el propio programador como creador.
     *
     * @param desafio Desafío en el que va a ser propuesto el ejercicio.
     * @param enunciado Enunciado del nuevo ejercicio.
     * @return El nuevo ejercicio.
     *
     * Nota: el desafío debe haber sido creado por el programador.
     */
    Ejercicio proponerEjercicioPara(@NotNull Desafio desafio, String enunciado) {
        assert desafio.creador.id == this.id

        Ejercicio nuevoEjercicio = new Ejercicio(desafio, enunciado)
        desafio.agregarEjercicio(nuevoEjercicio)
        nuevoEjercicio
    }

    /** Propone un nuevo desafío.
     *
     * @param titulo Título del nuevo desafío.
     * @param descripcion Descripción del nuevo desafío.
     * @param insigniasRequeridas Insignias que requiere el nuevo desafío.
     * @param fechaDesde Fecha desde la cual estará vigente el nuevo desafío.
     * @param fechaHasta Fecha hasta la cual estará vigente el nuevo desafío.
     * @return El nuevo desafío.
     */
    Desafio proponerDesafio(String titulo, String descripcion, Set<Insignia> insigniasRequeridas, DateTime fechaDesde, DateTime fechaHasta) {
        new Desafio(titulo, descripcion, this, insigniasRequeridas, fechaDesde, fechaHasta)
    }

    /** Propone un nuevo desafío.
     *
     * @param titulo Título del nuevo desafío.
     * @param descripcion Descripción del nuevo desafío.
     * @param insigniasRequeridas Insignias que requiere el nuevo desafío.
     * @return El nuevo desafío.
     */
    Desafio proponerDesafio(String titulo, String descripcion, Set<Insignia> insigniasRequeridas) {
        new Desafio(titulo, descripcion, this, insigniasRequeridas)
    }

    /** Propone un nuevo desafío.
     *
     * @param titulo Título del nuevo desafío.
     * @param descripcion Descripción del nuevo desafío.
     * @param fechaDesde Fecha desde la cual estará vigente el nuevo desafío.
     * @param fechaHasta Fecha hasta la cual estará vigente el nuevo desafío.
     * @return El nuevo desafío.
     */
    Desafio proponerDesafio(String titulo, String descripcion, DateTime fechaDesde, DateTime fechaHasta) {
        new Desafio(titulo, descripcion, this, new LinkedHashSet<Insignia>(), fechaDesde, fechaHasta)
    }

    /** Propone un nuevo desafío.
     *
     * @param titulo Título del nuevo desafío.
     * @param descripcion Descripción del nuevo desafío.
     * @return El nuevo desafío.
     */
    Desafio proponerDesafio(String titulo, String descripcion) {
        new Desafio(titulo, descripcion, this, new LinkedHashSet<Insignia>())
    }

    /** Selecciona una solución de algún desafío propio como la mejor para dicho desafío.
     *
     * @param solucion
     */
    void elegirMejorSolucion(Solucion solucion) {
        assert solucion.desafio.creador == this
        solucion.otorgarPuntoEnFaceta(TipoFaceta.Ganador)
    }
}
