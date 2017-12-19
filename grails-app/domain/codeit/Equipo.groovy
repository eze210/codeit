package codeit

class Equipo implements Participante {

    static hasMany = [programadores: Programador]

    String nombre
    Set<Programador> programadores

    static constraints = {
        nombre nullable: false, blank: false
    }

    void agregarMiembro(Programador nuevoMiembro) {
        if (programadores.contains(nuevoMiembro)) {
            throw new Exception("El programador ya está en el grupo")
            //todo: add custom exceptions
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
                throw new Exception("Ya existe otro grupo con estos miembros")
                //todo: add custom exceptions
            }
        }

        programadores.add(nuevoMiembro)
    }

    Set<Programador> programadoresInvolucrados() {
        return programadores
    }

}
