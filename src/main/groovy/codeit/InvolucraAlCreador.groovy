package codeit

class InvolucraAlCreador extends IllegalArgumentException {
    InvolucraAlCreador() {
        super("No puede participar de un desafío que ayudó a crear")
    }
}
