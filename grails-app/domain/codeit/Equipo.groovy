package codeit

class Equipo extends Participante {

    class ProgramadorYaMiembroException extends IllegalArgumentException {
        ProgramadorYaMiembroException() {
            super("El programador ya está en el grupo")
        }
    }

    class EquipoYaExistente extends IllegalArgumentException {
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

    void agregarMiembro(Programador nuevoMiembro) throws ProgramadorYaMiembroException {
        if (programadores.contains(nuevoMiembro)) {
            throw new ProgramadorYaMiembroException()
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
    }

}
