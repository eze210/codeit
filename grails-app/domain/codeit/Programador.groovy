package codeit

import org.grails.orm.hibernate.cfg.GrailsHibernateUtil
import org.joda.time.DateTime

import javax.validation.constraints.NotNull

/** Clase Programador. */
class Programador extends Participante {

    /** Usuario para la seguridad. */
    User user

    /** Insignias conseguidas por un programador. */
    Set<Insignia> insignias

    /** Equipos a los que pertenece un programador. */
    Set<Equipo> equipos

    /** Invitaciones que se han hecho a un programador. */
    Set<Invitacion> invitaciones

    /** Desafíos creados por un programador. */
    Set<Desafio> desafiosCreados

    /** Facetas en las que puede ser puntuado un programador. */
    Set<Faceta> facetas

    /** Declaraciones necesarias para el mapeo relacional. */
    static hasMany = [equipos: Equipo,
                      invitaciones: Invitacion,
                      desafiosCreados: Desafio,
                      facetas: Faceta]

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

        user = new User(username: nombre, password: '1234')
        try {
            Role role = Role.findByAuthority('ROLE_ADMIN')
            user.save(flush: true)
            UserRole.create(user, role, true)
            UserRole.withSession {
                it.flush()
                it.clear()
            }
        } catch (IllegalStateException e) {
            e.printStackTrace()
        } catch (NullPointerException e) {
            e.printStackTrace()
        }

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


    /** Devuelve el puntaje acumulado en el tipo de faceta pedida.
     *
     * @param tipoFaceta El tipo de la faceta de la cual se quiere obtener la puntuación acumulada.
     *
     * @return El puntaje acumulado en la faceta correspondiente.
     */
    Integer obtenerPuntajeParaFaceta(TipoFaceta tipoFaceta) {
        facetas.find({it.tipo == tipoFaceta}).getPuntosAcumulados()
    }


    /** Suma un punto en el tipo de faceta indicado.
     *
     * @param tipoFaceta El tipo de faceta cuyo puntaje se desea incrementar.
     *
     * @return El nuevo puntaje en la faceta.
     */
    Integer asignarPuntoEnFaceta(TipoFaceta tipoFaceta) {
        Faceta faceta = facetas.find({it.tipo == tipoFaceta})
        insignias.addAll(faceta.asignarPuntos(1))
        faceta.puntosAcumulados
    }


    /** Otorga una insignia a la solución y a todos los programadores involucrados.
     *
     * @param insignia Insignia a otorgar.
     * @param solucion Solución que se quiere premiar.
     *
     * @return
     */
    def asignarInsigniaASolucion(Insignia insignia, Solucion solucion) {
        assert solucion.desafio.creador == this
        solucion.asignarInsignia(insignia)
    }


    /** Acepta la invitación indicada.
     *
     * @param invitacion Invitación que se desea aceptar.
     *
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
     *
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


    /* ****************************************************************** *
     * TODO: Implementación de la Interfaz Creador
     * ****************************************************************** */

    /** Propone un nuevo ejercicio con el propio programador como creador.
     *
     * @param desafio Desafío en el que va a ser propuesto el ejercicio.
     * @param enunciado Enunciado del nuevo ejercicio.
     *
     * @return El nuevo ejercicio.
     *
     * Nota: el desafío debe haber sido creado por el programador.
     */
    Ejercicio proponerEjercicioPara(@NotNull Desafio desafio, String enunciado) {
        assert desafio.creador == this

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
     *
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
     * @param fechaHasta Fecha hasta la cual estará vigente el nuevo desafío.
     *
     * @return El nuevo desafío.
     */
    Desafio proponerDesafio(String titulo, String descripcion, Set<Insignia> insigniasRequeridas, DateTime fechaHasta) {
        new Desafio(titulo, descripcion, this, insigniasRequeridas, fechaHasta)
    }


    /** Propone un nuevo desafío.
     *
     * @param titulo Título del nuevo desafío.
     * @param descripcion Descripción del nuevo desafío.
     * @param insigniasRequeridas Insignias que requiere el nuevo desafío.
     *
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
     *
     * @return El nuevo desafío.
     */
    Desafio proponerDesafio(String titulo, String descripcion, DateTime fechaDesde, DateTime fechaHasta) {
        new Desafio(titulo, descripcion, this, new LinkedHashSet<Insignia>(), fechaDesde, fechaHasta)
    }


    /** Propone un nuevo desafío.
     *
     * @param titulo Título del nuevo desafío.
     * @param descripcion Descripción del nuevo desafío.
     * @param fechaHasta Fecha hasta la cual estará vigente el nuevo desafío.
     *
     * @return El nuevo desafío.
     */
    Desafio proponerDesafio(String titulo, String descripcion, DateTime fechaHasta) {
        new Desafio(titulo, descripcion, this, new LinkedHashSet<Insignia>(), fechaHasta)
    }


    /** Propone un nuevo desafío.
     *
     * @param titulo Título del nuevo desafío.
     * @param descripcion Descripción del nuevo desafío.
     *
     * @return El nuevo desafío.
     */
    Desafio proponerDesafio(String titulo, String descripcion) {
        new Desafio(titulo, descripcion, this, new LinkedHashSet<Insignia>())
    }


    /** Selecciona una solución de algún desafío propio como la mejor para dicho desafío.
     *
     * @param solucion
     */
    def elegirMejorSolucion(Solucion solucion) {
        assert solucion.desafio.creador == this

        solucion.asignarPuntoEnFaceta(TipoFaceta.Ganador)
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
}
