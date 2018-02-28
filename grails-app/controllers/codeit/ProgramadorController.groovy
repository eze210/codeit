package codeit

import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
@Secured('ROLE_USER')
class ProgramadorController {

    static allowedMethods = [save: "POST", update: "PUT"]

    def show(Programador programador) {
        respond programador
    }

    @Transactional
    def save(Programador programador) {
        if (programador == null) {
            transactionStatus.setRollbackOnly()
            return
        }

        if (programador.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond programador.errors, view:'create'
            return
        }

        programador.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'programador.label', default: 'Programador'), programador.id])
                redirect programador
            }
            '*' { respond programador, [status: CREATED] }
        }
    }

    def edit(Programador programador) {
        respond programador
    }

    @Transactional
    def update(Programador programador) {
        if (programador == null) {
            transactionStatus.setRollbackOnly()
            return
        }

        if (programador.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond programador.errors, view:'edit'
            return
        }

        programador.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'programador.label', default: 'Programador'), programador.id])
                redirect programador
            }
            '*'{ respond programador, [status: OK] }
        }
    }

}
