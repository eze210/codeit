package codeit

class NoTieneFaceta extends IllegalArgumentException {
    NoTieneFaceta() {
        super("No se puede puntuar en ese tipo de faceta")
    }
}
