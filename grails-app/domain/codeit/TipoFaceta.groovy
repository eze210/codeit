package codeit

abstract class TipoFaceta {

    static Set<TipoFaceta> tiposExistentes() {
        return new HashSet<TipoFaceta>([Ganador.getInstance(),
                                        Desafiante.getInstance(),
                                        Solucionador.getInstance(),
                                        Creativo.getInstance(),
                                        Prolijo.getInstance()])
    }

    abstract Set<InsigniaAutomatica> insigniasAutomaticasPosibles()

}

final class Ganador extends TipoFaceta {
    private static Ganador instance
    private Ganador() {}

    static Ganador getInstance() {
        Ganador.instance = Ganador.instance ?: new Ganador()
        return Ganador.instance
    }

    Set<InsigniaAutomatica> insigniasAutomaticasPosibles() {
        def insignias = [new InsigniaAutomatica(5, "Cebollita subcampeón"),
                         new InsigniaAutomatica(10, "Camino al éxito"),
                         new InsigniaAutomatica(15, "Campeón"),
                         new InsigniaAutomatica(20, "Messi")]
        return new HashSet<InsigniaAutomatica>(insignias)
    }

}

final class Desafiante extends TipoFaceta {
    private static Desafiante instance
    static Desafiante getInstance() {
        Desafiante.instance = Ganador.instance ?: new Desafiante()
        return Desafiante.instance
    }
    private Desafiante() {}

    Set<InsigniaAutomatica> insigniasAutomaticasPosibles() {
        def insignias = [new InsigniaAutomatica(15, "Desafía el status quo"),
                         new InsigniaAutomatica(50, "Tú contra el mundo")]
        return new HashSet<InsigniaAutomatica>(insignias)
    }

}

final class Solucionador extends TipoFaceta {
    private static Solucionador instance
    static Solucionador getInstance() {
        Solucionador.instance = Solucionador.instance ?: new Solucionador()
        return Solucionador.instance
    }
    private Solucionador() {}

    Set<InsigniaAutomatica> insigniasAutomaticasPosibles() {
        def insignias = [new InsigniaAutomatica(25, "Tú sí que sabes"),
                         new InsigniaAutomatica(100, "Salva al mundo")]
        return new HashSet<InsigniaAutomatica>(insignias)
    }

}

final class Creativo extends TipoFaceta {
    private static Creativo instance
    static Creativo getInstance() {
        Creativo.instance = Creativo.instance ?: new Creativo()
        return Creativo.instance
    }
    private Creativo() {}

    Set<InsigniaAutomatica> insigniasAutomaticasPosibles() {
        def insignias = [new InsigniaAutomatica(5, "Como Edison, se te prendió la lamparita"),
                         new InsigniaAutomatica(15, "Einstein, científico loco")]
        return new HashSet<InsigniaAutomatica>(insignias)
    }

}

final class Prolijo extends TipoFaceta {
    private static Prolijo instance
    static Prolijo getInstance() {
        Prolijo.instance = Prolijo.instance ?: new Prolijo()
        return Prolijo.instance
    }
    private Prolijo() {}

    Set<InsigniaAutomatica> insigniasAutomaticasPosibles() {
        def insignias = [new InsigniaAutomatica(5, "Limpito limpito"),
                         new InsigniaAutomatica(15, "Ese código sí se deja ver")]
        return new HashSet<InsigniaAutomatica>(insignias)
    }

}
