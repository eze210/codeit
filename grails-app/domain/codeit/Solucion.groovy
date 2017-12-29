package codeit

class Solucion {

    static hasMany = [resoluciones: Resolucion]

    Participante participante
    String descripcion
    LinkedHashSet<Resolucion> resoluciones

    static constraints = {
        participante nullable:false
    }

    Boolean agregarResolucion(Resolucion resolucion) {
        return resoluciones.add(resolucion)
    }

}
