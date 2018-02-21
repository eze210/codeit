package codeit

import org.joda.time.DateTime

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class DesafioController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 1, 100)
        if (params.containsKey("from")) {
            Participante participante = Participante.findById(params.from)
            Integer inicio = (params.containsKey("offset") ? params.offset : 0) * params.max
            Integer count = participante.desafios.size()
            Integer fin = Math.min(inicio + params.max, count - 1)
            List<Desafio> desafios = fin > 0 ? (participante.desafios as List)[inicio..fin] : []
            respond desafioList: desafios, desafioCount: count, participante: participante
        } else {
            respond desafioList: Desafio.list(params), desafioCount: Desafio.count()
        }
    }

    def show(Desafio desafio) {
        respond desafio
    }

    def create() {

    }

    @Transactional
    def save() {
        Programador creador = Programador.findById(params.creador_id)
        String titulo = params.titulo
        String descripcion = params.descripcion
        DateTime desde = null //TODO: Get from params
        DateTime hasta = null //TODO: Get from params

        List<String> enunciados = []//TODO: Get from params: params.list("enunciado")

        if (titulo.isEmpty()) {
            flash.errors = ["El título no puede estar vacío"]
        }
        if (descripcion.isEmpty()) {
            flash.errors = flash.errors + "La descripción no puede estar vacía"
        }
        if (enunciados.contains("")) {
           flash.errors = flash.errors + "No puede tener enunciados vacíos"
        }
        if (flash.errors) {
            redirect action: "create"
            return
        }

        Desafio desafio
        if (desde) {
            desafio = creador.proponerDesafio(titulo, descripcion, desde, hasta)
        } else if (hasta) {
            desafio = creador.proponerDesafio(titulo, descripcion, hasta)
        } else {
            desafio = creador.proponerDesafio(titulo, descripcion)
        }

        List<Ejercicio> ejercicios = enunciados.collect { new Ejercicio(desafio, it) }
        ejercicios.forEach { desafio.agregarEjercicio(it) }

        // Crea desafío y ejercicios
        desafio.save flush: true, failOnError: true

        redirect action: "show", id: solucion.id
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
