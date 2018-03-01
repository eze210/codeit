package codeit

/** Interfaz puntuable. */
interface Puntuable {

    /** Otorga una insignia.
     *
     * @param insignia La insignia que debe ser otorgada.
     * @return El conjunto de las insignias del Puntuable luego de otorgar.
     */
    Set<String> otorgarInsignia(String insignia)

    /** Devuelve todas las insignias que tiene el Puntuable.
     *
     * @return El conjunto de las insignias del Puntuable.
     */
    Set<String> obtenerInsignias()

    /** Quita una insignia del Puntuable.
     *
     * @param insignia La insignia que debe ser retirada.
     * @return La insignia que se quitó.
     */
    String retirarInsignia(String insignia)

    /** Agrega un punto al Puntuable en la faceta especificada.
     *
     * @param tipoFaceta Faceta en la que se debe agregar un punto.
     * @return La nueva cantidad de puntos en la faceta correspondiente.
     */
    Integer otorgarPuntoEnFaceta(TipoFaceta tipoFaceta)

    /** Devuelve la cantidad de puntos en la faceta correspondiente.
     *
     * @param tipoFaceta Faceta que se quiere consultar.
     * @return La cantidad de puntos en la faceta correspondiente.
     */
    Integer obtenerPuntajeEnFaceta(TipoFaceta tipoFaceta)
}