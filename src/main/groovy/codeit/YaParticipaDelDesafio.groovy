package codeit

class YaParticipaDelDesafio extends IllegalArgumentException {
    YaParticipaDelDesafio() {
        super("No puede actuar de esta forma si ya participa del desafío con otro rol")
    }
}
