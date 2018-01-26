package codeit

class Solucion {

    Participante participante
    String descripcion
    Desafio desafio
    Set<Resolucion> resoluciones

    static hasMany = [resoluciones: Resolucion]

    static constraints = {
        participante nullable: false, blank: false
        descripcion nullable: false, blank: false
        desafio nullable: false, blank: false
    }

    Solucion(Participante participante, String descripcion, Desafio desafio) {
        this.participante = participante
        this.descripcion = descripcion
        this.desafio = desafio
        this.resoluciones = new LinkedHashSet<>()
    }

    Boolean agregarResolucion(Resolucion resolucion) {
        /* no puede haber dos resoluciones para el mismo ejercicio */
        resoluciones.removeIf({res -> res.ejercicio == resolucion.ejercicio})
        resoluciones.add(resolucion)

        /* se vuelve a proponer para que se valide nuevamente */
        desafio.proponerSolucion(this)
    }

    Resultado validar(Set<Ejercicio> todosLosEjercicios) {
        /* como no hay dos resoluciones que resuelvan el mismo ejercicio, que los tamaños sean iguales
         * significa que todos los ejercicios están resueltos */
        Boolean todosLosEjerciciosEstanResueltos = todosLosEjercicios.size() == resoluciones.size()

        Integer puntos = resoluciones.count { resolucion -> resolucion.ejercicio.validarResolucion(resolucion) }

        new Resultado(solucion: this, valido: todosLosEjerciciosEstanResueltos, puntaje: puntos)
    }

}
