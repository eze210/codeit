package codeit

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class DesafioController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond desafioList: Desafio.list(params), desafioCount: Desafio.count()
    }

    def show(Desafio desafio) {
        respond desafio
    }

    def create() {
        respond new Desafio(params)
    }

    @Transactional
    def save(Desafio desafio) {
        if (desafio == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (desafio.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond desafio.errors, view:'create'
            return
        }

        desafio.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'desafio.label', default: 'Desafio'), desafio.id])
                redirect desafio
            }
            '*' { respond desafio, [status: CREATED] }
        }
    }

    def edit(Desafio desafio) {
        respond desafio
    }

    @Transactional
    def update(Desafio desafio) {
        if (desafio == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (desafio.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond desafio.errors, view:'edit'
            return
        }

        desafio.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'desafio.label', default: 'Desafio'), desafio.id])
                redirect desafio
            }
            '*'{ respond desafio, [status: OK] }
        }
    }

    @Transactional
    def delete(Desafio desafio) {

        if (desafio == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        desafio.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'desafio.label', default: 'Desafio'), desafio.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'desafio.label', default: 'Desafio'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
