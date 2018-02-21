package codeit

/** Clase abstracta Participante. */
abstract class Participante {

    /** Nombre del participante. */
    String nombre

    /** Soluciones propuestas por el participante. */
    Set<Solucion> soluciones

    /** Desafíos en los que participa el participante. */
    Set<Desafio> desafios

    /** Declaraciones necesarias para el mapeo relacional. */
    static hasMany = [soluciones: Solucion,
                      desafios: Desafio]

    /** Reglas para el mapeo relacional. */
    static constraints = {
        nombre nullable: false, blank: false, unique: true
    }

    /** Constructor de un participante.
     *
     * @param nombre Nombre del participante.
     */
    Participante(String nombre) {
        this.nombre = nombre
        this.soluciones = new HashSet<>()
    }


    /** Devuelve los programadores involucrados con el participante.
     *
     * @return Los programadores involucrados con el participante.
     */
    abstract Set<Programador> programadoresInvolucrados()


    /** Devuelve las insignias conseguidas por los programadores involucrados con el participante.
     *
     * @return Las insignias conseguidas por los programadores involucrados con el participante.
     */
    Set<Insignia> obtenerInsignias() {
        programadoresInvolucrados().collect({it.insignias}).flatten()
    }


    /** Otorga una insignia a todos los programadores involucrados con el participante.
     *
     * @param insignia La insignia que debe ser otorgada a los programadores.
     */
    def asignarInsignia(Insignia insignia) {
        programadoresInvolucrados().forEach({it.insignias.add(insignia)})
    }


    /** Verifica si el participante involucra a algún programador que también esté involucrado con el
     * participante consultado.
     *
     * @param participante El otro participante.
     *
     * @return \c true si hay programadores compartidos, o \c false en otro caso.
     */
    Boolean comparteMiembrosCon(Participante participante) {
        Set<Programador> otros = participante.programadoresInvolucrados()
        programadoresInvolucrados().intersect(otros)
    }

    /** Propone una solución en el desafío indicado.
     *
     * @param desafio El desafío que intentará resolver la solución.
     * @param descripcionDeLaSolucion Descripción de la nueva solución.
     *
     * @return La nueva solución.
     */
    Solucion proponerSolucionPara(Desafio desafio, String descripcionDeLaSolucion) {
        new Solucion(this, descripcionDeLaSolucion, desafio)
    }


    /** Asigna un punto al desafío.
     *
     * @param desafio Desafíó al que se quiere asignar un punto.
     *
     * @return El nuevo puntaje del desafío.
     */
    Integer asignarPuntoA(Desafio desafio) throws InvolucraAlCreador, NoParticipaDelDesafio {
        if (comparteMiembrosCon(desafio.creador)) {
            throw new InvolucraAlCreador()
        }

        if (!participaDe(desafio)) {
            throw new NoParticipaDelDesafio()
        }

        desafio.asignarPunto()
    }


    /** Verifica si el participante participa del desafío.
     *
     * @param desafio Desafío del cual se quiere saber si el participante participa.
     *
     * @return \c true si el participante participa del desafío, o \c false en otro caso.
     */
    Boolean participaDe(Desafio desafio) {
        desafio.esParticipante(this)
    }

}