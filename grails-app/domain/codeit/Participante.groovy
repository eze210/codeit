package codeit

interface Participante {

    Set<Programador> programadoresInvolucrados()

    default Boolean involucraA(Participante participante) {
        return programadoresInvolucrados().contains(participante)
    }

}