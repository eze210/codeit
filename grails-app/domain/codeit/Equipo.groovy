package codeit

class Equipo implements Participante {

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

    private String nombre
    private Set<Programador> programadores

    static constraints = {
        nombre nullable: false, blank: false
    }

    void agregarMiembro(Programador nuevoMiembro) throws ProgramadorYaMiembroException {
        if (programadores.contains(nuevoMiembro)) {
            throw new ProgramadorYaMiembroException()
        }

        // todo: check if it's a list when there is only one result
        List<Equipo> otrosEquipos = Equipos.withCriteria {
            programadores {
                eq("id". nuevoMiembro.id)
            }
        }
        for (Equipo equipo: otrosEquipos) {
            Set<Programador> otrosProgramadores = equipo.programadores.clone()
            if (otrosProgramadores.intersect(programadores).size() == programadores.size()) {
                throw new EquipoYaExistente()
            }
        }

        programadores.add(nuevoMiembro)
    }

    Set<Programador> programadoresInvolucrados() {
        return programadores
    }

}
