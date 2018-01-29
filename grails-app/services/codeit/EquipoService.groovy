package codeit

import grails.gorm.transactions.Transactional

@Transactional
class EquipoService {

    def crearEquipo() {

    }

    Boolean formanEquipoValido(Participante parte1, Participante parte2) {
        Set<Programador> programadores = new HashSet<>(parte1.programadoresInvolucrados())
        programadores.addAll(parte2.programadoresInvolucrados())

       List<Equipo> result = Equipo.findByProgramadores(programadores)
        print("El equipo encontrado es ${result}")
        result.first() ? false : true
//        otrosEquipos.findResult(true, { (programadores == it.programadoresInvolucrados()) ? false : null })
    }

}