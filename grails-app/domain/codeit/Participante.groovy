package codeit

trait Participante {

    abstract Set<Programador> programadoresInvolucrados()

    Boolean involucraA(Participante participante) {
        return programadoresInvolucrados().contains(participante)
    }

}