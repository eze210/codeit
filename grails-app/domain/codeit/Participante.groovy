package codeit

public abstract class Participante {

    abstract Set<Programador> programadoresInvolucrados()

    Boolean involucraA(Participante participante) {
        programadoresInvolucrados().contains(participante)
    }

}