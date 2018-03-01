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

    /** Resultado de la validación de la solución. */
    Resultado resultado

    /** Declaraciones necesarias para el mapeo relacional. */
    static belongsTo = [participante: Participante, desafio: Desafio, resultado: Resultado]
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
        participante.soluciones.add(this)
        desafio.proponerSolucion(this)
    }

    /** Agrega una resolución para un ejercicio del desafío.
     *
     * @param resolucion Resolución que debe ser agregada.
     */
    void agregarResolucion(Resolucion resolucion) {
        /* no puede haber dos resoluciones para el mismo ejercicio */
        resoluciones.removeIf({res -> res.ejercicio.id == resolucion.ejercicio.id})
        resoluciones.add(resolucion)
        resolucion.solucion = this

        desafio.invalidarSolucion(this)
    }

    /** Revalida la solución.
     */
    void validar() {
        /* como no hay dos resoluciones que resuelvan el mismo ejercicio, si los tamaños son iguales
         * entonces todos los ejercicios están resueltos */
        Boolean todosLosEjerciciosEstanResueltos = desafio.ejercicios.size() == resoluciones.size()

        Integer puntos = resoluciones.count { resolucion -> resolucion.ejercicio.validarResolucion(resolucion) }

        resultado.valido = todosLosEjerciciosEstanResueltos
        resultado.correcto = puntos == desafio.ejercicios.size()
        resultado.puntaje = puntos
    }

    /* ****************************************************************** *
     * Implementación de la Interfaz Puntuable.
     * ****************************************************************** */

    @Override
    Set<String> otorgarInsignia(String insignia) {
        participante.otorgarInsignia(insignia)
    }

    @Override
    Set<String> obtenerInsignias() {
        participante.obtenerInsignias()
    }

    @Override
    String retirarInsignia(String insignia) {
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
