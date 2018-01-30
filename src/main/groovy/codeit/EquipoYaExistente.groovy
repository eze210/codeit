package codeit

class EquipoYaExistente extends IllegalArgumentException {
    EquipoYaExistente() {
        super("Ya existe otro grupo con estos miembros")
    }
}