package codeit

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class SolucionController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        if (params.containsKey("to")) {
            Desafio desafio = Desafio.findById(params.to)
            Integer inicio = (params.containsKey("offset") ? params.offset : 0) * params.max
            Integer fin = Math.min(inicio+params.max, desafio.soluciones.size()-1)
            List<Solucion> soluciones = (desafio.soluciones as List)[inicio..fin]
            respond soluciones, model:[solucionCount: desafio.soluciones.size()]
        } else {
            respond Solucion.list(params), model:[solucionCount: Solucion.count()]
        }
    }

    def show(Solucion solucion) {
        respond solucion
    }

    def create() {
        Desafio desafio = Desafio.findById(params.to)
        render view:"create", model:[desafio: desafio]
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

//        if (params.descripcion.isEmpty()) {
//            solucion.errors.reject("solucion.descripcion.vacia", "No puede crearse una solución sin descripción")
//        }
//
//        if (solucion.hasErrors()) {
//            transactionStatus.setRollbackOnly()
//            render view:'create', model: [desafio: desafio, errors: solucion.errors]
//            return
//        }

        // Actualizar las soluciones en el desafío y los resultados creados
        desafio.save flush: true, failOnError: true

        redirect action: "show", id: solucion.id
    }

    def edit(Solucion solucion) {
        respond solucion
    }

    @Transactional
    def update(Solucion solucion) {
        if (solucion == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (solucion.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond solucion.errors, view:'edit'
            return
        }

        solucion.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'solucion.label', default: 'Solucion'), solucion.id])
                redirect solucion
            }
            '*'{ respond solucion, [status: OK] }
        }
    }

    @Transactional
    def delete(Solucion solucion) {

        if (solucion == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        solucion.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'solucion.label', default: 'Solucion'), solucion.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'solucion.label', default: 'Solucion'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
