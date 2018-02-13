package codeit

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = true)
class ParticipanteController {

    def index() {
        params.max = Math.min(params.max ? params.int('max') : 1, 100)
        params.offset = params.offset ? params.int('offset') : 0
        List<Programador> programadores = Programador.list(params)
        List<Equipo> equipos = Equipo.list(params)
        respond programadoresList: programadores, programadoresCount: Programador.count(), equiposList: equipos, equiposCount: Equipo.count()
    }

    def listadoProgramadores() {
        params.max = Math.min(params.max ? params.int('max') : 1, 100)
        params.offset = params.offset ? params.int('offset') : 0
        List<Programador> programadores = Programador.list(params)
        render template:"listadoProgramadores", model: [programadoresList: programadores, programadoresCount: Programador.count()]
    }

    def listadoEquipos() {
        params.max = Math.min(params.max ? params.int('max') : 1, 100)
        params.offset = params.offset ? params.int('offset') : 0
        List<Equipo> equipos = Equipo.list(params)
        render template:"listadoEquipos", model: [equiposList: equipos, equiposCount: Equipo.count()]
    }

}
