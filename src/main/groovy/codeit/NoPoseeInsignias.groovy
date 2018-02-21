package codeit

class NoPoseeInsignias extends IllegalArgumentException {
    NoPoseeInsignias() {
        super("No posee las insignias necesarias para el desafío")
    }
}
