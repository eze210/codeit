package codeit

class Equipo extends Participante {

    static class ProgramadorYaMiembro extends IllegalArgumentException {
        ProgramadorYaMiembro() {
            super("El programador ya está en el grupo")
        }
    }

    static class EquipoYaExistente extends IllegalArgumentException {
        EquipoYaExistente() {
            super("Ya existe otro grupo con estos miembros")
        }
    }

    static hasMany = [programadores: Programador]
    Set<Programador> programadores
    String nombre

    static constraints = {
        nombre nullable: false, blank: false
    }

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
//        if (!Equipo.formanEquipoValido(self, nuevoMiembro)) {
//            throw new EquipoYaExistente()
//        }
        programadores.add(nuevoMiembro)
        nuevoMiembro.equipos.add(this)
    }

    static Boolean formanEquipoValido(Participante parte1, Participante parte2) {
        Set<Programador> programadores = new HashSet<>()
        programadores.addAll(parte1.programadoresInvolucrados())
        programadores.addAll(parte2.programadoresInvolucrados())

//        if (Equipo.findResult({ programadores == it.programadoresInvolucrados() })) {
//            return false
//        }
        // todo: improve with database direct access
        List<Equipo> otrosEquipos = Equipo.findAll()
        for (Equipo equipo: otrosEquipos) {
            if (equipo.programadores.collect { it.id }.intersect(programadores.collect { it.id }).size() == programadores.size()) {
                return false
            }
        }
        return true
    }

}
