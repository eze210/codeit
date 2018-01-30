package codeit

class NoParticipaDelDesafio extends IllegalArgumentException {
    NoParticipaDelDesafio() {
        super("No puede actuar si no participa del desafío")
    }
}
