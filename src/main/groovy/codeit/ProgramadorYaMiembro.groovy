package codeit

class ProgramadorYaMiembro extends IllegalArgumentException {
    ProgramadorYaMiembro() {
        super("El programador ya está en el grupo")
    }
}