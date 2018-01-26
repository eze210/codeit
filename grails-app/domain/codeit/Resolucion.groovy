package codeit

class Resolucion {

    Ejercicio ejercicio
    // Recibe un String y devuelve un String
    Closure<String> codigo

    static constraints = {
        ejercicio nullable: false
        codigo nullable: false
    }

    Resolucion(Ejercicio ejercicio, String codigo) {
        this.ejercicio = ejercicio
        GroovyShell shell = new GroovyShell()
        this.codigo = shell.evaluate(codigo)
    }

    String ejecutar(String entrada) {
        codigo(entrada)
    }

    Boolean resuelveElEjercicio() {
        ejercicio.validarResolucion(this)
    }

}
