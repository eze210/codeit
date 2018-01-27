package codeit

class Invitacion {

    enum Estado {
        Pendiente,
        Rechazada,
        Aceptada
    }

    Participante participanteQueInvito
    Programador programadorInvitado
    Estado estado

    Invitacion(Participante participanteQueInvito, Programador programadorInvitado) {
        this.participanteQueInvito = participanteQueInvito
        this.programadorInvitado = programadorInvitado
        this.estado = Estado.Pendiente
        programadorInvitado.invitaciones.add(this)
    }

    static constraints = {
        participanteQueInvito nullable: false, blank: false
        programadorInvitado nullable: false, blank: false
    }

}
