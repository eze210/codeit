package codeit

class ComparteMiembrosConParticipante extends IllegalArgumentException {
    ComparteMiembrosConParticipante() {
        super("No puede participar de un desafío que tiene un equipo con el que comparte miembros")
    }
}
