package codeit

class Puntaje implements Puntuable {

    /** Insignias conseguidas por un puntaje. */
    Set<String> insignias

    /** Insignias otorgadas por un puntaje. */
    Set<String> insigniasRetiradas

    /** Facetas en las que puede ser puntuado un puntaje. */
    Set<Faceta> facetas

    /** Declaraciones necesarias para el mapeo relacional. */
    static belongsTo = [Faceta, Insignia]
    static hasMany = [facetas           : Faceta,
                      insignias         : String,
                      insigniasRetiradas: String]

    Puntaje(Collection<? extends Faceta> facetas) {
        this.facetas = new LinkedHashSet<>(facetas)
        this.insignias = new LinkedHashSet<>()
        this.insigniasRetiradas = new LinkedHashSet<>()
    }

    /* ****************************************************************** *
     * Implementación de la Interfaz Puntuable.
     * ****************************************************************** */

    @Override
    Set<String> otorgarInsignia(String insignia) {
        insignias.add(insignia)
        insignias
    }

    @Override
    Set<String> obtenerInsignias() {
        insignias - insigniasRetiradas
    }

    @Override
    String retirarInsignia(String insignia) {
        if (!insignias.contains(insignias))
            throw new NoTieneInsignia()

        insigniasRetiradas.add(insignia)
        insignia
    }

    @Override
    Integer otorgarPuntoEnFaceta(TipoFaceta tipoFaceta) {
        Faceta faceta = this.facetas.find({it.tipo == tipoFaceta})
        if (!faceta)
            throw new NoTieneFaceta()

        insignias.addAll(faceta.asignarPuntos(1)*.nombre)
        faceta.puntosAcumulados
    }

    @Override
    Integer obtenerPuntajeEnFaceta(TipoFaceta tipoFaceta) {
        Faceta faceta = this.facetas.find({it.tipo == tipoFaceta})
        if (!faceta)
            throw new NoTieneFaceta()

        faceta.puntosAcumulados
    }
}
