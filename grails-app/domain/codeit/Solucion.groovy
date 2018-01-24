package codeit

class Solucion {

    static hasMany = [resoluciones: Resolucion]

    Participante participante
    String descripcion
    Set<Resolucion> resoluciones

    static constraints = {
        participante nullable: false
    }

    Solucion(Participante participante, String descripcion) {
        this.participante = participante
        this.descripcion = descripcion
        this.resoluciones = new LinkedHashSet<>()
    }

    Boolean agregarResolucion(Resolucion resolucion) {
        resoluciones.add(resolucion)
    }

}
