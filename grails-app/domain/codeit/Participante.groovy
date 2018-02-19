package codeit

abstract class Participante {

    abstract String nombre

    static constraints = {
        nombre nullable: false, blank: false, unique: true
    }

    abstract Set<Programador> programadoresInvolucrados()

    Boolean involucraA(Participante participante) {
        programadoresInvolucrados().containsAll(participante.programadoresInvolucrados())
    }

    Solucion proponerSolucionPara(Desafio desafio, String descripcionDeLaSolucion) {
        new Solucion(this, descripcionDeLaSolucion, desafio)
    }

    Integer asignarPuntoA(Desafio desafio) {
        if (involucraA(desafio.creador)) {
            throw new InvolucraAlCreador()
        }

        if (!participaDe(desafio)) {
            throw new NoParticipaDelDesafio()
        }

        desafio.asignarPunto()
    }

    Boolean participaDe(Desafio desafio) {
        desafio.esParticipante(this)
    }

    Invitacion invitar(Programador programador) {
        new Invitacion(this, programador)
    }

}