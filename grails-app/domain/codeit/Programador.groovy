package codeit

class Programador extends Participante {

    String nombre

    static constraints = {
        nombre nullable: false, blank: false, unique: true
    }

    Set<Programador> programadoresInvolucrados() {
        [this] as Set<Programador>
    }

}
