package codeit

/** Clase Solución. */
class Solucion implements Puntuable {

    /** Participante que propuso la solución. */
    Participante participante

    /** Desafío en el que está propuesta la solución. */
    Desafio desafio

    /** Resoluciones de los ejercicios del desafío. */
    Set<Resolucion> resoluciones

    /** Descripción de la solución. */
    String descripcion

    /** Resultado actual de la solución en el desafío. */
    Resultado resultado

    /** Declaraciones necesarias para el mapeo relacional. */
    static belongsTo = [participante: Participante, desafio: Desafio]
    static hasMany = [resoluciones: Resolucion]

    /** Reglas para el mapeo relacional. */
    static constraints = {
        participante nullable: false, blank: false
        descripcion nullable: false, blank: false
        desafio nullable: false, blank: false
        resoluciones nullable: false
        resultado nullable: false
    }

    /** Constructor de una solución.
     *
     * @param participante Participante que propone la solución.
     * @param descripcion Descripción de la solución.
     * @param desafio Desafío en el que se propone la solución.
     */
    Solucion(Participante participante, String descripcion, Desafio desafio) {
        this.participante = participante
        this.descripcion = descripcion
        this.desafio = desafio
        this.resoluciones = new LinkedHashSet<>()
        desafio.proponerSolucion(this)
    }

    /** Agrega una resolución para un ejercicio del desafío.
     *
     * @param resolucion Resolución que debe ser agregada.
     */
    void agregarResolucion(Resolucion resolucion) {
        /* no puede haber dos resoluciones para el mismo ejercicio */
        resoluciones.removeIf({res -> res.ejercicio == resolucion.ejercicio})
        resoluciones.add(resolucion)
        resolucion.solucion = this

        /* se vuelve a proponer para que se valide nuevamente */
        desafio.proponerSolucion(this)
    }

    /** Valida una solución con los ejercicios que debe resolver.
     *
     * @param todosLosEjercicios Ejercicios del desafío que se intenta resolver.
     * @return El resultado de la validación.
     */
    Resultado validar(Set<Ejercicio> todosLosEjercicios) {
        /* como no hay dos resoluciones que resuelvan el mismo ejercicio, si los tamaños son iguales
         * entonces todos los ejercicios están resueltos */
        Boolean todosLosEjerciciosEstanResueltos = todosLosEjercicios.size() == resoluciones.size()

        Integer puntos = resoluciones.count { resolucion -> resolucion.ejercicio.validarResolucion(resolucion) }

        new Resultado(this,
                todosLosEjerciciosEstanResueltos,
                puntos == todosLosEjercicios.size(),
                puntos)
    }

    /* ****************************************************************** *
     * Implementación de la Interfaz Puntuable.
     * ****************************************************************** */

    @Override
    Set<Insignia> otorgarInsignia(Insignia insignia) {
        participante.otorgarInsignia(insignia)
    }

    @Override
    Set<Insignia> obtenerInsignias() {
        participante.obtenerInsignias()
    }

    @Override
    Insignia retirarInsignia(Insignia insignia) {
        participante.retirarInsignia(insignia)
    }


    @Override
    Integer obtenerPuntajeEnFaceta(TipoFaceta tipoFaceta) {
        participante.obtenerPuntajeEnFaceta(tipoFaceta)
    }

    @Override
    Integer otorgarPuntoEnFaceta(TipoFaceta tipoFaceta) {
        participante.otorgarPuntoEnFaceta(tipoFaceta)
    }
}
