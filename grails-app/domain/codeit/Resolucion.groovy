package codeit

class Resolucion {

    Ejercicio ejercicio
    // Recibe un String y devuelve un String
    Closure<String> codigo

    static constraints = {
        ejercicio nullable: false
        codigo nullable: false
    }

    String ejecutar(String entrada) {
        codigo(entrada)
    }

}
