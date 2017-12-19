package codeit

class Programador implements Participante {

    String nombre

    static constraints = {
        nombre nullable: false, blank: false, unique: true
    }

    Set<Programador> programadoresInvolucrados() {
        return [this] as Set<Programador>
    }

}
