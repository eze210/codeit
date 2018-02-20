package codeit

class Faceta {

    TipoFaceta tipo
    Integer puntosAcumulados


    Faceta(TipoFaceta tipoFaceta) {
        this.tipo = tipoFaceta
        this.puntosAcumulados = 0
    }


    Set<InsigniaAutomatica> asignarPuntos(Integer cantidad) {
        puntosAcumulados += cantidad
        tipo.obtenerInsignias(puntosAcumulados)
    }

}
