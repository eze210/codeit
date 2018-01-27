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

    void agregarMiembro(Programador nuevoMiembro) throws ProgramadorYaMiembro {
        if (programadores.contains(nuevoMiembro)) {
            throw new ProgramadorYaMiembro()
        }

        // todo: check if it's a list when there is only one result
//        List<Equipo> otrosEquipos = Equipos.withCriteria {
//            programadores {
//                eq("id". nuevoMiembro.id)
//            }
//        }
//        for (Equipo equipo: otrosEquipos) {
//            Set<Programador> otrosProgramadores = equipo.programadores.clone()
//            if (otrosProgramadores.intersect(programadores).size() == programadores.size()) {
//                throw new EquipoYaExistente()
//            }
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

        return true
    }

}
