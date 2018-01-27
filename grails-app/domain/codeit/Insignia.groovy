package codeit

abstract class Insignia {

    String origen

    static constraints = {
        origen nullable: false, blank: false
    }

}

class InsigniaAutomatica extends Insignia {

    Integer umbral
    String nombre

    InsigniaAutomatica(Integer umbral, String nombre) {
        this.origen = "Ha superado ${umbral} eventos."
        this.umbral = umbral
        this.nombre = nombre
    }

    static constraints = {
        umbral nullable: false
        nombre nullable: false, blank:false
    }

}

class InsigniaEspecial extends Insignia {}