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
        Set<Insignia> todasLasInsignias = (programadoresInvolucrados()*.insignias).flatten()
        todasLasInsignias
    }


    /** Otorga una insignia a todos los programadores involucrados con el participante.
     *
     * @param insignia La insignia que debe ser otorgada a los programadores.
     *
     * @return El conjunto de las insignias de todos los programadores involucrados.
     */
    Set<Insignia> asignarInsignia(Insignia insignia) {
        programadoresInvolucrados().collect({it.insignias.add(insignia)})
        obtenerInsignias()
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
        desafio.validarParticipacion(this)
        new Solucion(this, descripcionDeLaSolucion, desafio)
    }


    // TODO: Es método de Programador?
    /** Asigna un punto al desafío.
     *
     * @param desafio Desafío al que se quiere asignar un punto.
     *
     * @return El nuevo puntaje del desafío.
     */
    Integer asignarPuntoA(Desafio desafio) throws ComparteMiembrosConCreador, NoParticipaDelDesafio {
        if (comparteMiembrosCon(desafio.creador)) {
            throw new ComparteMiembrosConCreador()
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


    /** Otorga un punto en alguna faceta a los programadores involucrados.
     *
     * @param tipoFaceta Faceta en la cual se quiere asignar un punto.
     *
     * @return La nueva cantidad de puntos que suman todos los programadores involucrados en la faceta.
     */
    Integer asignarPuntoEnFaceta(TipoFaceta tipoFaceta) {
        Integer suma = programadoresInvolucrados()*.asignarPuntoEnFaceta(tipoFaceta).sum()
        suma
    }

    Boolean comparteAlgunEquipoCon(Participante otro) {
        Set<Equipo> equiposInvolucradosConThis = (this.programadoresInvolucrados()*.equipos).flatten()
        Set<Equipo> equiposInvolucradosConOtro = (otro.programadoresInvolucrados()*.equipos).flatten()
        Set<Equipo> interseccion = equiposInvolucradosConThis.intersect(equiposInvolucradosConOtro)
        interseccion.size() > 0
    }

}