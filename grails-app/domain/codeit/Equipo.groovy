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

        programadores.add(nuevoMiembro)
        nuevoMiembro.equipos.add(this)
    }



}
