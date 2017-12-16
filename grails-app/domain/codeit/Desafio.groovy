package codeit

class Desafio {

    String título
    String descripcion

    Programador creador
    Range<Date> vigencia


    static constraints = {
        título nullable: false, blank: false
        descripcion nullable: false, blank: false
    }
}
