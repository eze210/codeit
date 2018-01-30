package codeit

class Equipo extends Participante {

    Set<Programador> programadores
    String nombre

    static constraints = {
        nombre(nullable: false, blank: false)
    }

    static belongsTo = Programador
    static hasMany = [programadores: Programador]

    Equipo(String nombre) {
        this.nombre = nombre
        this.programadores = new HashSet<>()
    }

    Set<Programador> programadoresInvolucrados() {
        programadores
    }

    void agregarMiembro(Programador nuevoMiembro) throws ProgramadorYaMiembro {
        if (programadores.contains(nuevoMiembro)) {
            throw new ProgramadorYaMiembro()
        }

        programadores.add(nuevoMiembro)
        nuevoMiembro.equipos.add(this)
    }

    static Boolean formanEquipoValido(Participante parte1, Participante parte2) {
        Set<Programador> miembros = new HashSet<>(parte1.programadoresInvolucrados())
        miembros.addAll(parte2.programadoresInvolucrados())

        if (Equipo.count() == 0) {
            return true;
        }

//        Equipo.find {programadores == miembros}
        if (findByProgramadores(miembros)) {
            return false
        }

        return true
    }

}
