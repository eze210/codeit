package codeit

/** Clase insignia. */
abstract class Insignia {

    /** Nombre que describe la insignia. */
    String nombre

    /** Explicación del origen de la insignia. */
    String origen

    /** Reglas para el mapeo relacional. */
    static constraints = {
        origen nullable: false, blank: false
        nombre nullable: false, blank: false, unique: true
    }


    /** Constructor de una insignia.
     *
     * @param origen Explicación del origen de la insignia.
     * @param nombre Nombre que describe la insignia.
     */
    Insignia(String origen, String nombre) {
        this.origen = origen
        this.nombre = nombre
    }

}


/** Clase para las insignias que se generan por alcanzar un umbral de puntos acumulados. */
class InsigniaAutomatica extends Insignia {

    /** Umbral de puntos a alcanzar para lograr la insignia. */
    Integer umbral

    /** Reglas para el mapeo relacional. */
    static constraints = {
        umbral nullable: false
    }


    /** Constructor de una insignia automática.
     *
     * @param umbral Umbral de puntos a alcanzar para lograr la insignia.
     * @param nombre Nombre que describe la insignia.
     */
    InsigniaAutomatica(Integer umbral, String nombre) {
        super("Ha superado ${umbral} eventos.", nombre)
        this.umbral = umbral
    }
}
