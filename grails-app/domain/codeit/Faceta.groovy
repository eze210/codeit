package codeit

/** Clase faceta. */
class Faceta {

    /** Tipo de la faceta. */
    TipoFaceta tipo

    /** Puntos acumulados en la faceta. */
    Integer puntosAcumulados


    /** Constructor de la faceta.
     *
     * @param tipoFaceta El tipo de la faceta.
     */
    Faceta(TipoFaceta tipoFaceta) {
        this.tipo = tipoFaceta
        this.puntosAcumulados = 0
    }


    /** Asigna una cantidad de puntos a la faceta.
     *
     * @param cantidad Cantidad de puntos a asignar.
     *
     * @return Las insignias cuyo umbral es menor o igual a la cantidad acumulada de puntos.
     */
    Set<InsigniaAutomatica> asignarPuntos(Integer cantidad) {
        puntosAcumulados += cantidad
        tipo.obtenerInsignias(puntosAcumulados)
    }

}
