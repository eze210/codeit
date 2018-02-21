package codeit

class Solucion {

    String descripcion

    static belongsTo = [participante: Participante, desafio: Desafio]
    static hasMany = [resoluciones: Resolucion]
    static hasOne = [resultado: Resultado]

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
        desafio.proponerSolucion(this)
    }

    Boolean agregarResolucion(Resolucion resolucion) {
        /* no puede haber dos resoluciones para el mismo ejercicio */
        resoluciones.removeIf({res -> res.ejercicio == resolucion.ejercicio})
        resoluciones.add(resolucion)

        /* se vuelve a proponer para que se valide nuevamente */
        desafio.proponerSolucion(this)
    }

    Resultado validar(Set<Ejercicio> todosLosEjercicios) {
        /* como no hay dos resoluciones que resuelvan el mismo ejercicio, si los tamaños son iguales
         * entonces todos los ejercicios están resueltos */
        Boolean todosLosEjerciciosEstanResueltos = todosLosEjercicios.size() == resoluciones.size()

        Integer puntos = resoluciones.count { resolucion -> resolucion.ejercicio.validarResolucion(resolucion) }

        new Resultado(this,
                todosLosEjerciciosEstanResueltos,
                puntos == todosLosEjercicios.size(),
                puntos)
    }

    def asignarInsignia(Insignia insignia) {
        this.participante.asignarInsignia(insignia)
    }
}
