package codeit

class Resolucion {

    // Recibe un String y devuelve un String
    Closure<String> codigo

    static belongsTo = [ejercicio: Ejercicio, solucion: Solucion]

    static constraints = {
        ejercicio nullable: false
        solucion nullable: false
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
