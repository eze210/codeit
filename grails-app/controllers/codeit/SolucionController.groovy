package codeit

import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Transactional(readOnly = true)
@Secured('ROLE_USER')
class SolucionController {

    static allowedMethods = [save: "POST", update: "PUT"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        if (params.containsKey("to")) {
            Desafio desafio = Desafio.findById(params.int("to"))
            Integer inicio = (params.containsKey("offset") ? params.offset : 0) * params.max
            Integer count = desafio.resultados.size()
            Integer fin = Math.min(inicio + params.max, count - 1)
            List<Solucion> soluciones = fin > 0 ? (desafio.resultados*.solucion as List)[inicio..fin] : []
            respond solucionList: soluciones, solucionCount: count, desafio: desafio
        } else {
            // TODO: Filter solucions from current user
            respond solucionList: Solucion.list(params), solucionCount: Solucion.count()
        }
    }

    def show(Solucion solucion) {
        respond solucion
    }

    def create() {
        Desafio desafio = Desafio.findById(params.to)
        render view: "create", model: [desafio: desafio]
    }

    @Transactional
    def save() {
        Desafio desafio = Desafio.findById(params.desafio_id)
        Participante participante = Participante.findById(params.participante_id)
        String descripcion = params.descripcion
        Solucion solucion = participante.proponerSolucionPara(desafio, descripcion)

        List<String> codigos = params.list("codigo")
        List<String> idEjercicios = params.list("ejercicio_id")
        List<Ejercicio> ejercicios = idEjercicios.collect { Ejercicio.findById(it.toInteger()) }
        List<Resolucion> resoluciones = [ejercicios, codigos].transpose().collect {
            Ejercicio ej = it[0]
            String cod = it[1]
            new Resolucion(ej, cod)
        }
        resoluciones.forEach {
            solucion.agregarResolucion(it)
        }

        if (params.descripcion.isEmpty()) {
            flash.errors = ["La descripción no puede estar vacía"]
            redirect action: "create", params: [to: desafio.id]
            return
        }

        // Actualiza las soluciones en el desafío y los resultados y resoluciones creados
        desafio.save flush: true, failOnError: true

        redirect action: "show", id: solucion.id
    }

    def edit(Solucion solucion) {
        if (params.containsKey("descripcion")) {
            render view: "edit", model: [solucion: solucion, params: params]
        } else {
            render view: "edit", model: [solucion: solucion]
        }
    }

    @Transactional
    def update() {
        Solucion solucion = Solucion.findById(params.int("solucion_id"))

        List<String> codigos = params.list("codigo")
        List<Ejercicio> ejercicios = params.list("ejercicio_id").collect { Ejercicio.findById(it.toInteger()) }
        List<String> idResoluciones = params.list("resolucion_id")

        if (params.descripcion.isEmpty()) {
            flash.errors = ["La descripción no puede estar vacía"]

            def newParams = [:]
            newParams.descripcion = params.descripcion
            newParams.codigo = params.codigo

            redirect action: "edit", id: solucion.id, params: newParams
            return
        }

        [ejercicios, codigos].transpose().withIndex().forEach { tupla, index ->
            if (index < solucion.resoluciones.size()) {
                Resolucion resolucion = Resolucion.findById(idResoluciones[index].toInteger())
                assert resolucion.ejercicio.id == tupla[0].id
                resolucion.codigo = tupla[1]
            } else {
                Resolucion resolucion = new Resolucion(tupla[0], tupla[1])
                solucion.agregarResolucion(resolucion)
            }
        }

        // Actualiza las soluciones en el desafío y los resultados y resoluciones creadas
        solucion.desafio.save flush: true, failOnError: true
        flash.message = "Los cambios han sido guardados"
        redirect action: "edit", id: solucion.id
    }

}
