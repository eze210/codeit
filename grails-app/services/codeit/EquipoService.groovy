package codeit

import grails.gorm.transactions.Transactional

@Transactional
class EquipoService {

    def crearEquipo() {

    }

    Boolean formanEquipoValido(Participante parte1, Participante parte2) {
        // todo: check if it's a list when there is only one result
//        List<Equipo> otrosEquipos = Equipos.withCriteria {
//            programadores {
//                eq("id". nuevoMiembro.id)
//            }
//        }
//        for (Equipo equipo: otrosEquipos) {
//            Set<Programador> otrosProgramadores = equipo.programadores.clone()
//            if (otrosProgramadores.intersect(programadores).size() == programadores.size()) {
//                throw new EquipoYaExistente()
//            }
//        }
        Set<Programador> programadores = new HashSet<>()
        programadores.addAll(parte1.programadoresInvolucrados())
        programadores.addAll(parte2.programadoresInvolucrados())

        if (Equipo.findResult({ programadores == it.programadoresInvolucrados() })) {
            return false
        }

        return true
    }

}