package codeit

class Invitacion {

    enum Estado {
        Pendiente,
        Rechazada,
        Aceptada
    }


    Estado estado


    static constraints = {
        equipo nullable: false, blank: false
        invitado nullable: false, blank: false
    }


    static belongsTo = [equipo: Equipo, invitado: Programador]


    Invitacion(Equipo equipo, Programador invitado) {
        this.equipo = equipo
        this.invitado = invitado
        this.estado = Estado.Pendiente
        invitado.invitaciones.add(this)
    }


    Equipo aceptar() {
        this.estado = Estado.Aceptada
        equipo.agregarMiembro(invitado)
    }

}
