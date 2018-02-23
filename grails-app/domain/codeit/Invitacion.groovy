package codeit

/** Clase invitación. */
class Invitacion {

    /** Enumerado con los posibles estados de una invitación. */
    enum Estado {
        Pendiente,
        Rechazada,
        Aceptada
    }

    /** Estado de la invitación. */
    Estado estado

    /** Equipo al que es invitado un programador. */
    Equipo equipo

    /** Programador invitado. */
    Programador invitado

    /** Declaraciones necesarias para el mapeo relacional. */
    static belongsTo = [equipo: Equipo, invitado: Programador]

    /** Reglas para el mapeo relacional. */
    static constraints = {
        equipo nullable: false, blank: false
        invitado nullable: false, blank: false
    }

    /** Constructor de una invitación.
     *
     * @param equipo Equipo que invita.
     * @param invitado Programador invitado.
     */
    Invitacion(Equipo equipo, Programador invitado) {
        this.equipo = equipo
        this.invitado = invitado
        this.estado = Estado.Pendiente
        invitado.invitaciones.add(this)
    }

    /** Acepta la invitación.
     *
     * @return El equipo con los nuevos miembros.
     */
    Equipo aceptar() {
        this.estado = Estado.Aceptada
        equipo.agregarMiembro(invitado)
    }

    /** Valida una invitación.
     *
     * @return \c true si la invitación es válida, o \c false en otro caso.
     */
    Boolean validar() {
        Equipo.formanEquipoValido(equipo, invitado)
    }
}
