package codeit


abstract class Insignia {

    String nombre
    String origen

    static constraints = {
        origen nullable: false, blank: false
        nombre nullable: false, blank: false, unique: true
    }

    Insignia(String origen, String nombre) {
        this.origen = origen
        this.nombre = nombre
    }

}


class InsigniaAutomatica extends Insignia {

    Integer umbral

    InsigniaAutomatica(Integer umbral, String nombre) {
        super("Ha superado ${umbral} eventos.", nombre)
        this.umbral = umbral
    }

    static constraints = {
        umbral nullable: false
    }

}


class InsigniaEspecial extends Insignia {

    InsigniaEspecial(String origen, String nombre) {
        super(origen, nombre)
    }

}