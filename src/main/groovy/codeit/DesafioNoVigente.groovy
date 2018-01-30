package codeit

class DesafioNoVigente extends IllegalArgumentException {
    DesafioNoVigente() {
        super("No se puede actuar con un desafío ya vencido.")
    }
}
