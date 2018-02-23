package codeit

import org.joda.time.DateTime

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Transactional(readOnly = true)
@Secured('ROLE_ADMIN')
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
        render view: "create", model: [params: params]
    }

    @Transactional
    def save() {
        Programador creador = Programador.findById(params.creador_id)
        String titulo = params.titulo
        String descripcion = params.descripcion
        DateTime desde = null //TODO: Get from params
        DateTime hasta = null //TODO: Get from params

        List<String> enunciados = params.list("enunciado")

        if (titulo.isEmpty()) {
            flash.errors = ["El título no puede estar vacío"]
        }
        if (Desafio.findByTitulo(titulo)) {
            flash.errors = (flash.errors ?: []) + ["Ya existe un desafío con ese título"]
        }
        if (descripcion.isEmpty()) {
            flash.errors = (flash.errors ?: []) + ["La descripción no puede estar vacía"]
        }
        enunciados.withIndex().forEach { elem, index ->
            if (!elem) {
                flash.errors = (flash.errors ?: []) + ["El enunciado del ejercicio ${index + 1} no puede ser vacío."]
            }
        }
        if (flash.errors) {
            redirect action: "create", params: [titulo: params.titulo, descripcion: params.descripcion, enunciados: params.enunciado]
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

        redirect action: "show", id: desafio.id
    }

    def edit(Desafio desafio) {
        respond desafio
    }

    @Transactional
    def update() {
        Desafio desafio = Desafio.findById(params.desafio_id)
        String titulo = params.titulo
        String descripcion = params.descripcion
        DateTime desde = null //TODO: Get from params
        DateTime hasta = null //TODO: Get from params

        List<String> enunciados = params.list("enunciado")

        if (titulo.isEmpty()) {
            flash.errors = ["El título no puede estar vacío"]
        }
        if (Desafio.findByTitulo(titulo)) {
            flash.errors = (flash.errors ?: []) + ["Ya existe un desafío con ese título"]
        }
        if (descripcion.isEmpty()) {
            flash.errors = (flash.errors ?: []) + ["La descripción no puede estar vacía"]
        }
        enunciados.withIndex().forEach { elem, index ->
            if (!elem) {
                flash.errors = (flash.errors ?: []) + ["El enunciado del ejercicio ${index + 1} no puede ser vacío."]
            }
        }
        if (flash.errors) {
            redirect action: "edit", params: [desafio: desafio, titulo: params.titulo, descripcion: params.descripcion, enunciados: params.enunciado]
            return
        }

        if (desde) {
            desafio.vigencia = new Vigencia(desde, hasta)
        } else if (hasta) {
            desafio.vigencia = new Vigencia(hasta)
        } else {
            desafio.vigencia = new Vigencia()
        }

        enunciados.withIndex().forEach { enunciado, index ->
            if (index < desafio.ejercicios.size()) {
                desafio.ejercicios[index].enunciado = enunciado
            } else {
                desafio.creador.proponerEjercicioPara(desafio, enunciado)
            }
        }

        // Actualiza desafío, ejercicios y pruebas
        desafio.save flush: true, failOnError: true

        redirect action: "edit", id: desafio.id
    }

}
