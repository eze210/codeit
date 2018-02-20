package codeit

class Invitacion {

    enum Estado {
        Pendiente,
        Rechazada,
        Aceptada
    }

    Estado estado

    static belongsTo = [participanteQueInvito: Participante, programadorInvitado: Programador]

    static constraints = {
        participanteQueInvito nullable: false, blank: false
        programadorInvitado nullable: false, blank: false
    }

    Invitacion(Participante participanteQueInvito, Programador programadorInvitado) {
        this.participanteQueInvito = participanteQueInvito
        this.programadorInvitado = programadorInvitado
        this.estado = Estado.Pendiente
        programadorInvitado.invitaciones.add(this)
    }

}
