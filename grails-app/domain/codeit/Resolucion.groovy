package codeit

class Resolucion {

    // Recibe un String y devuelve un String
    Closure<String> codigo

    static constraints = {
        ejercicio nullable: false
        codigo nullable:false
    }

    String ejecutar(String entrada) {
        return codigo(entrada)
    }

}
