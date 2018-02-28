package codeit

import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
@Secured('ROLE_USER')
class EquipoController {

    static allowedMethods = [save: "POST", update: "PUT"]

    def show(Equipo equipo) {
        respond equipo
    }

    @Transactional
    def save(Equipo equipo) {
        if (equipo == null) {
            transactionStatus.setRollbackOnly()
            return
        }

        if (equipo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond equipo.errors, view:'create'
            return
        }

        equipo.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'equipo.label', default: 'Equipo'), equipo.id])
                redirect equipo
            }
            '*' { respond equipo, [status: CREATED] }
        }
    }

    def edit(Equipo equipo) {
        respond equipo
    }

    @Transactional
    def update(Equipo equipo) {
        if (equipo == null) {
            transactionStatus.setRollbackOnly()
            return
        }

        if (equipo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond equipo.errors, view:'edit'
            return
        }

        equipo.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'equipo.label', default: 'Equipo'), equipo.id])
                redirect equipo
            }
            '*'{ respond equipo, [status: OK] }
        }
    }

}
