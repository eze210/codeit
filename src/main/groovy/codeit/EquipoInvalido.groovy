package codeit

class EquipoInvalido extends IllegalArgumentException {
    EquipoInvalido() {
        this("El equipo que se quiere conformar es inválido")
    }

    EquipoInvalido(String mensaje) {
        super(mensaje)
    }
}