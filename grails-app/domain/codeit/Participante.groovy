package codeit

abstract class Participante {

    String nombre

    static hasMany = [soluciones: Solucion]

    static constraints = {
        nombre nullable: false, blank: false, unique: true
    }

    abstract Set<Programador> programadoresInvolucrados()

    Set<Insignia> obtenerInsignias() {
        programadoresInvolucrados().collect({it.insignias}).flatten()
    }

    Participante(String nombre) {
        this.nombre = nombre
    }

    Boolean comparteMiembrosCon(Participante participante) {
        Set<Programador> otros = participante.programadoresInvolucrados()
        programadoresInvolucrados().intersect(otros)
    }

    Solucion proponerSolucionPara(Desafio desafio, String descripcionDeLaSolucion) {
        new Solucion(this, descripcionDeLaSolucion, desafio)
    }

    Integer asignarPuntoA(Desafio desafio) {
        if (comparteMiembrosCon(desafio.creador)) {
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