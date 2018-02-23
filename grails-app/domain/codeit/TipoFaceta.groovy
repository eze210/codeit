package codeit

/** Enumerado que tiene los tipos de faceta. */
enum TipoFaceta {
    /** Otorga insignias cuando los programadores logran la mejor solución a un desafío. */
    Ganador         (new LinkedHashSet([new InsigniaAutomatica(5, "Cebollita subcampeón"),
                                        new InsigniaAutomatica(10, "Camino al éxito"),
                                        new InsigniaAutomatica(15, "Campeón"),
                                        new InsigniaAutomatica(20, "Messi")])),

    /** Otorga insignias cuando los desafíos de los programadores son puntuados. */
    Desafiante      (new LinkedHashSet([new InsigniaAutomatica(15, "Desafía el status quo"),
                                        new InsigniaAutomatica(50, "Tú contra el mundo")])),

    /** Otorga insignias cuando los programadores proponen resoluciones. */
    Solucionador    (new LinkedHashSet([new InsigniaAutomatica(25, "Tú sí que sabes"),
                                        new InsigniaAutomatica(100, "Salva al mundo")])),

    /** Otorga insignias cuando los programadores proponen soluciones que logran puntos como creativos. */
    Creativo        (new LinkedHashSet([new InsigniaAutomatica(5, "Como Edison, se te prendió la lamparita"),
                                        new InsigniaAutomatica(15, "Einstein, científico loco")])),

    /** Otorga insignias cuando los programadores proponen soluciones que logran puntos como prolijos. */
    Prolijo         (new LinkedHashSet([new InsigniaAutomatica(5, "Limpito limpito"),
                                        new InsigniaAutomatica(15, "Ese código sí se deja ver")])),

    /** Genera insignias en los desafíos para que los creadores se las otorguen a los solucionadores. */
    Desafio         (new LinkedHashSet([new InsigniaAutomatica(5, "Premio al esfuerzo"),
                                        new InsigniaAutomatica(10, "Me gusta tu estilo"),
                                        new InsigniaAutomatica(15, "Cuando sea grande quiero ser como vos")]))

    /** Insignias que se otorgan por acumulación de puntaje en una faceta. */
    final Set<InsigniaAutomatica> insigniasAutomaticasPosibles

    /** Constructor del enumerado.
     *
     * @param insigniasAutomaticasPosibles
     */
    TipoFaceta(Set<InsigniaAutomatica> insigniasAutomaticasPosibles) {
        this.insigniasAutomaticasPosibles = insigniasAutomaticasPosibles
    }

    /** Devuelve las insignias que debe tener un puntuable que tenga determinada cantidad
     * de puntos en un tipo de faceta.
     *
     * @param puntaje Cantidad de puntos que tiene el puntuable.
     * @return Las insignias que se le deben asignar.
     */
    Set<InsigniaAutomatica> obtenerInsignias(Integer puntaje) {
        insigniasAutomaticasPosibles.findAll { puntaje >= it.umbral }
    }
}
