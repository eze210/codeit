package codeit

public abstract class Participante {

    abstract Set<Programador> programadoresInvolucrados()

    Boolean involucraA(Participante participante) {
        programadoresInvolucrados().contains(participante)
    }

    Boolean involucraA(Participante[] participantes) {
        programadoresInvolucrados().containsAll(participantes)
    }

    Solucion proponerSolucionPara(Desafio desafio, String descripcionDeLaSolucion) {
        Solucion solucion = new Solucion(this, descripcionDeLaSolucion)
        desafio.proponerSolucion(solucion)
        solucion
    }

}