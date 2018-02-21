package codeit

class ComparteMiembrosConCreador extends IllegalArgumentException {
    ComparteMiembrosConCreador() {
        super("No puede participar de un desafío que ayudó a crear")
    }
}
