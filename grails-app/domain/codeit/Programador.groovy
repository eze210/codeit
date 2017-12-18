package codeit

class Programador {

    String nombre

    static constraints = {
        nombre nullable: false, blank: false, unique: true
    }


}
