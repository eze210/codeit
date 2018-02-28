package codeit

class Puntaje implements Puntuable {

    /** Insignias conseguidas por un puntaje. */
    Set<Insignia> insignias

    /** Insignias otorgadas por un puntaje. */
    Set<Insignia> insigniasRetiradas

    /** Facetas en las que puede ser puntuado un puntaje. */
    Set<Faceta> facetas

    /** Declaraciones necesarias para el mapeo relacional. */
    static belongsTo = [Faceta, Insignia]
    static hasMany = [facetas           : Faceta,
                      insignias         : Insignia,
                      insigniasRetiradas: Insignia]

    Puntaje(Collection<? extends Faceta> facetas) {
        this.facetas = new LinkedHashSet<>(facetas)
        this.insignias = new LinkedHashSet<>()
        this.insigniasRetiradas = new LinkedHashSet<>()
    }

    /* ****************************************************************** *
     * Implementación de la Interfaz Puntuable.
     * ****************************************************************** */

    @Override
    Set<Insignia> otorgarInsignia(Insignia insignia) {
        insignias.add(insignia)
        insignias
    }

    @Override
    Set<Insignia> obtenerInsignias() {
        insignias - insigniasRetiradas
    }

    @Override
    Insignia retirarInsignia(Insignia insignia) {
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

        insignias.addAll(faceta.asignarPuntos(1))
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
