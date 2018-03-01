package codeit

import org.joda.time.DateTime

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Transactional(readOnly = true)
@Secured('ROLE_USER')
class DesafioController {

    static allowedMethods = [save: "POST", update: "PUT"]

    def index(Integer max) {
        params.max = Math.min(max ?: 1, 100)
        if (params.containsKey("from")) {
            Participante participante = Participante.findById(params.int("from"))
            Integer inicio = (params.containsKey("offset") ? params.offset : 0) * params.max
            Integer count = participante.desafios.size()
            Integer fin = Math.min(inicio + params.max, count - 1)
            List<Desafio> desafios = count == 0 ? [] : (participante.desafios as List)[inicio..fin]
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
        DateTime desde = params.desde ? new DateTime(params.desde) : null
        DateTime hasta = params.hasta ? new DateTime(params.hasta) : null

        List<String> enunciados = params.list("enunciado")

//        if (titulo.isEmpty()) {
//            flash.errors = ["El título no puede estar vacío"]
//        }
//        if (Desafio.findByTitulo(titulo)) {
//            flash.errors = (flash.errors ?: []) + ["Ya existe un desafío con ese título"]
//        }
//        if (descripcion.isEmpty()) {
//            flash.errors = (flash.errors ?: []) + ["La descripción no puede estar vacía"]
//        }
//        enunciados.withIndex().forEach { elem, index ->
//            if (!elem) {
//                flash.errors = (flash.errors ?: []) + ["El enunciado del ejercicio ${index + 1} no puede ser vacío."]
//            }
//        }
//        if (flash.errors) {
//            redirect action: "create", params: [titulo: params.titulo, descripcion: params.descripcion, enunciados: params.enunciado]
//            return
//        }

        Desafio desafio = creador.proponerDesafio(titulo, descripcion, desde, hasta)

        List<Ejercicio> ejercicios = enunciados.collect { new Ejercicio(desafio, it) }
        ejercicios.forEach { desafio.agregarEjercicio(it) }

        // Crea desafío y ejercicios
        desafio.save flush: true, failOnError: true

        redirect action: "show", id: desafio.id
    }

    def edit(Desafio desafio) {
        if (params.containsKey("enunciados")) {
            params.desafio = desafio
            render view: "edit", model: params
        } else {
            render view: "edit", model: [desafio: desafio]
        }
    }

    @Transactional
    def update() {
        Desafio desafio = Desafio.findById(params.desafio_id)
        String titulo = params.titulo
        String descripcion = params.descripcion

        List<String> enunciados = params.list("enunciado")

        if (titulo.isEmpty()) {
            flash.errors = ["El título no puede estar vacío"]
        }

        if (titulo != desafio.titulo && Desafio.findByTitulo(titulo)) {
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
            def newParams = [:]
            newParams.titulo = params.titulo
            newParams.descripcion = params.descripcion
            newParams.enunciado = params.enunciado
            enunciados.withIndex().forEach { elem, index ->
                newParams["entrada_" + index.toString()] = params["entrada_${index}"]
                newParams["salida_esperada_" + index.toString()] = params["salida_esperada_${index}"]
            }
            redirect action: "edit", id: desafio.id, params: newParams
            return
        }

        desafio.titulo = params.titulo
        desafio.descripcion = params.descripcion

        List<Ejercicio> ejercicios = desafio.ejercicios.toSorted()
        enunciados.withIndex().forEach { enunciado, index ->

            List<String> entradas = params.list("entrada_${index}")
            List<String> salidas = params.list("salida_esperada_${index}")

            if (index < desafio.ejercicios.size()) {

                Ejercicio ejercicio = ejercicios[index]
                ejercicio.enunciado = enunciado
                List<Prueba> pruebas = ejercicio.pruebas.toSorted()

                entradas.withIndex().forEach { entrada, indexP ->
                    if (indexP < ejercicio.pruebas.size()) {
                        Prueba prueba = pruebas[indexP]
                        prueba.entrada = entrada
                        prueba.salidaEsperada = salidas[indexP]
                    } else {
                        ejercicio.agregarPrueba(entrada, salidas[indexP])
                    }
                }

            } else {
                Ejercicio ejercicio = desafio.creador.proponerEjercicioPara(desafio, enunciado)

                entradas.withIndex().forEach { entrada, indexP ->
                    ejercicio.agregarPrueba(entrada, salidas[indexP])
                }

            }

        }

        // Actualiza desafío, ejercicios y pruebas
        desafio.save flush: true, failOnError: true

        flash.message = "Los cambios han sido guardados."
        redirect action: "edit", id: desafio.id
    }

    def puntuar(Desafio desafio) {
        desafio.otorgarPuntoEnFaceta(TipoFaceta.Desafio)
        desafio.save flush: true, failOnError: true
        redirect action: "show", id: desafio.id
    }

}
