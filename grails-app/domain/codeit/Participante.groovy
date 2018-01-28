package codeit

public abstract class Participante {

    class NoParticipaDelDesafio extends Exception {
    }

    class InvolucraAlCreador extends Exception {
    }

    abstract Set<Programador> programadoresInvolucrados()

    Boolean involucraA(Participante participante) {
        programadoresInvolucrados().containsAll(participante.programadoresInvolucrados())
    }

    Solucion proponerSolucionPara(Desafio desafio, String descripcionDeLaSolucion) {
        Solucion solucion = new Solucion(this, descripcionDeLaSolucion, desafio)
        desafio.proponerSolucion(solucion)
        solucion
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