package codeit

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class SolucionController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Solucion.list(params), model:[solucionCount: Solucion.count()]
    }

    def show(Solucion solucion) {
        respond solucion
    }

    def create() {
        if (request.method == "GET") {
            Desafio desafio = Desafio.findById(params.to)
            render view:"create", model:[desafio: desafio]
        } else {
            Desafio desafio = params.desafio
            Participante participante = params.participante
            String descripcion = params.descripcion
            respond new Solucion(participante, descripcion, desafio)
        }
    }

    @Transactional
    def save(Solucion solucion) {
        if (solucion == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (params.descripcion.isEmpty()) {
            solucion.errors.reject("solucion.descripcion.vacia", "No puede crearse una solución sin descripción")
        }

        Desafio desafio = Desafio.findById(params.desafio_id)
        Participante participante = Participante.findById(params.participante_id)
        String descripcion = params.descripcion
        solucion = new Solucion(participante, descripcion, desafio)

        if (solucion.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond solucion.errors, view:'create'
            return
        }

        solucion.save(flush: true, failOnError: true)

        redirect action: "show/${solucion.id}"
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
