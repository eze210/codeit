package codeit

import grails.test.mixin.*
import spock.lang.*

/** Módulo de pruebas auto-generado. */

@TestFor(DesafioController)
@Mock(Desafio)
class DesafioControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null

        params["titulo"] = 'Título'
        params["descripcion"] = 'Descripción'
        Programador prog = new Programador("Creador")
        prog.save flush: true
        params["creador_id"] = prog.id
    }

    def populateInvalidParams(params) {
        assert params != null

        params["titulo"] = ''
        params["descripcion"] = 'Descripción'
    }

    void "Test the index action returns the correct model"() {
        when:"The index action is executed"
            controller.index()

        then:"The response is correct"
        !model.desafioList && model.desafioCount == 0
    }

//    void "Test the create action returns the correct model"() {
//        when:"The create action is executed"
//            controller.create()
//
//        then:"The create view is rendered"
//        view == 'create'
//    }

//    void "Test the save action correctly persists an instance"() {
//        when:"The save action is executed with an invalid instance"
//            request.method = 'POST'
//            populateInvalidParams(params)
//            controller.save()
//
//        then:"The create view is rendered again"
//            flash.errors != null
//            view == 'create'
//
//        when:"The save action is executed with a valid instance"
//            response.reset()
//            populateValidParams(params)
//
//            // TODO: Agregar las líneas siguientes cuando los métodos correspondientes estén implementados
//            controller.save(desafio)
//
//        then:"A redirect is issued to the show action"
//            response.redirectedUrl == '/desafio/show/1'
//            Desafio.count() == 1
//    }

//    void "Test that the show action returns the correct model"() {
//        when:"The show action is executed with a null domain"
//            controller.show(null)
//
//        then:"A 404 error is returned"
//            response.status == 404
//
//        when:"A domain instance is passed to the show action"
//            populateValidParams(params)
//            def desafio = new Desafio(params)
//            controller.show(desafio)
//
//        then:"A model is populated containing the domain instance"
//            model.desafio == desafio
//    }

//    void "Test that the edit action returns the correct model"() {
//        when:"The edit action is executed with a null domain"
//            controller.edit(null)
//
//        then:"A 404 error is returned"
//            response.status == 404
//
//        when:"A domain instance is passed to the edit action"
//            populateValidParams(params)
//            def desafio = new Desafio(params)
//            controller.edit(desafio)
//
//        then:"A model is populated containing the domain instance"
//            model.desafio == desafio
//    }

//    void "Test the update action performs an update on a valid domain instance"() {
//        when:"Update is called for a domain instance that doesn't exist"
//            request.contentType = FORM_CONTENT_TYPE
//            request.method = 'PUT'
//            controller.update(null)
//
//        then:"A 404 error is returned"
//            response.redirectedUrl == '/desafio/index'
//            flash.message != null
//
//        when:"An invalid domain instance is passed to the update action"
//            response.reset()
//            def desafio = new Desafio()
//            desafio.validate()
//            controller.update(desafio)
//
//        then:"The edit view is rendered again with the invalid instance"
//            view == 'edit'
//            model.desafio == desafio
//
//        when:"A valid domain instance is passed to the update action"
//            response.reset()
//            populateValidParams(params)
//            desafio = new Desafio(params).save(flush: true)
//            controller.update(desafio)
//
//        then:"A redirect is issued to the show action"
//            // TODO: Agregar las líneas siguientes cuando los métodos correspondientes estén implementados
//            //desafio != null
//            //response.redirectedUrl == "/desafio/show/$desafio.id"
//            //flash.message != null
//    }

//    void "Test that the delete action deletes an instance if it exists"() {
//        when:"The delete action is called for a null instance"
//            request.contentType = FORM_CONTENT_TYPE
//            request.method = 'DELETE'
//            controller.delete(null)
//
//        then:"A 404 is returned"
//            response.redirectedUrl == '/desafio/index'
//            flash.message != null
//
//        when:"A domain instance is created"
//            response.reset()
//            populateValidParams(params)
//            def desafio = new Desafio(params).save(flush: true)
//
//        then:"It exists"
//            // TODO: Agregar las líneas siguientes cuando los métodos correspondientes estén implementados
//            //Desafio.count() == 1
//
//        when:"The domain instance is passed to the delete action"
//            controller.delete(desafio)
//
//        then:"The instance is deleted"
//            Desafio.count() == 0
//            response.redirectedUrl == '/desafio/index'
//            flash.message != null
//    }

}
