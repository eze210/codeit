package codeit

abstract class Participante {

    String nombre

    static hasMany = [soluciones: Solucion]

    static constraints = {
        nombre nullable: false, blank: false, unique: true
    }

    abstract Set<Programador> programadoresInvolucrados()

    Participante(String nombre) {
        this.nombre = nombre
    }

    Boolean involucraA(Participante participante) {
        programadoresInvolucrados().containsAll(participante.programadoresInvolucrados())
    }

    Solucion proponerSolucionPara(Desafio desafio, String descripcionDeLaSolucion) {
        new Solucion(this, descripcionDeLaSolucion, desafio)
    }

    Integer asignarPuntoA(Desafio desafio) {
        if (involucraA(desafio.creador)) {
            throw new InvolucraAlCreador()
        }

        if (!participaDe(desafio)) {
            throw new NoParticipaDelDesafio()
        }

        desafio.asignarPunto()
    }

    Boolean participaDe(Desafio desafio) {
        desafio.esParticipante(this)
    }

}