package codeit

enum TipoFaceta {
    Ganador         (new LinkedHashSet([new InsigniaAutomatica(5, "Cebollita subcampeón"),
                      new InsigniaAutomatica(10, "Camino al éxito"),
                      new InsigniaAutomatica(15, "Campeón"),
                      new InsigniaAutomatica(20, "Messi")]))                        ,
    Desafiante      (new LinkedHashSet([new InsigniaAutomatica(15, "Desafía el status quo"),
                      new InsigniaAutomatica(50, "Tú contra el mundo")]))           ,
    Solucionador    (new LinkedHashSet([new InsigniaAutomatica(25, "Tú sí que sabes"),
                      new InsigniaAutomatica(100, "Salva al mundo")]))              ,
    Creativo        (new LinkedHashSet([new InsigniaAutomatica(5, "Como Edison, se te prendió la lamparita"),
                      new InsigniaAutomatica(15, "Einstein, científico loco")]))    ,
    Prolijo         (new LinkedHashSet([new InsigniaAutomatica(5, "Limpito limpito"),
                      new InsigniaAutomatica(15, "Ese código sí se deja ver")]))

    private final LinkedHashSet<InsigniaAutomatica> insigniasAutomaticasPosibles

    TipoFaceta(LinkedHashSet<InsigniaAutomatica> insigniasAutomaticasPosibles) {
        this.insigniasAutomaticasPosibles = insigniasAutomaticasPosibles
    }

}
