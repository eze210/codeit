package codeit

/** Clase abstracta Participante. */
abstract class Participante implements Puntuable {

    /** Nombre del participante. */
    String nombre

    /** Soluciones propuestas por el participante. */
    Set<Solucion> soluciones

    /** Desafíos en los que participa el participante. */
    Set<Desafio> desafios

    /** Declaraciones necesarias para el mapeo relacional. */
    static hasMany = [soluciones: Solucion,
                      desafios  : Desafio]

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
        this.desafios = new HashSet<>()
    }

    /** Devuelve los programadores involucrados con el participante.
     *
     * @return Los programadores involucrados con el participante.
     */
    abstract Set<Programador> programadoresInvolucrados()

    /** Verifica si el participante involucra a algún programador que también esté involucrado con el
     * participante consultado.
     *
     * @param participante El otro participante.
     * @return \c true si hay programadores compartidos, o \c false en otro caso.
     */
    Boolean comparteMiembrosCon(Participante participante) {
        Set otros = participante.programadoresInvolucrados()*.id
        programadoresInvolucrados()*.id.intersect(otros)
    }

    Boolean contieneA(Participante participante) {
        Set otros = participante.programadoresInvolucrados()*.id
        programadoresInvolucrados()*.id.intersect(otros).size() == otros.size()
    }

    /** Propone una solución en el desafío indicado.
     *
     * @param desafio El desafío que intentará resolver la solución.
     * @param descripcionDeLaSolucion Descripción de la nueva solución.
     * @return La nueva solución.
     */
    Solucion proponerSolucionPara(Desafio desafio, String descripcionDeLaSolucion) {
        desafio.validarParticipacion(this)
        desafios.add(desafio)
        new Solucion(this, descripcionDeLaSolucion, desafio)
    }

    /** Verifica si el participante participa del desafío.
     *
     * @param desafio Desafío del cual se quiere saber si el participante participa.
     * @return \c true si el participante participa del desafío, o \c false en otro caso.
     */
    Boolean participaDe(Desafio desafio) {
        desafio.propusoAlgunaSolucion(this)
    }

    /** Verifica si dos participantes comparten equipos.
     *
     * @param otro El otro participante.
     * @return \c true si comparten equipos, o \c false sino.
     */
    Boolean comparteAlgunEquipoCon(Participante otro) {
        Set<Equipo> equiposInvolucradosConThis = (this.programadoresInvolucrados()*.equipos).flatten()
        Set<Equipo> equiposInvolucradosConOtro = (otro.programadoresInvolucrados()*.equipos).flatten()
        Set<Equipo> interseccion = equiposInvolucradosConThis.intersect(equiposInvolucradosConOtro)
        interseccion.size() > 0
    }
}
