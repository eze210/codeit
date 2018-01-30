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

    void agregarMiembro(Programador nuevoMiembro) throws ProgramadorYaMiembro, EquipoYaExistente {
        if (programadores.contains(nuevoMiembro)) {
            throw new ProgramadorYaMiembro()
        }
        if (!Equipo.formanEquipoValido(this, nuevoMiembro)) {
            throw new EquipoYaExistente()
        }
        programadores.add(nuevoMiembro)
        nuevoMiembro.equipos.add(this)
    }

    static Boolean formanEquipoValido(Participante parte1, Participante parte2) {
        Set<Programador> miembros = new HashSet<>(parte1.programadoresInvolucrados())
        miembros.addAll(parte2.programadoresInvolucrados())

        if (count() == 0) {
            return true
        }

        if (findByProgramadores(miembros)) {
            return false
        }
        return true

        // todo: improve with database direct access
//        List<Equipo> otrosEquipos = Equipo.findAll()
//        for (Equipo equipo: otrosEquipos) {
//            if (equipo.programadores.collect { it.id }.intersect(programadores.collect { it.id }).size() == programadores.size()) {
//                return false
//            }
//        }
//        return true
    }

}
