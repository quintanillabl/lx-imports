package com.luxsoft.nomina



import grails.test.mixin.*
import spock.lang.*

@TestFor(NominaAsimiladoController)
@Mock(NominaAsimilado)
class NominaAsimiladoControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.nominaAsimiladoInstanceList
            model.nominaAsimiladoInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.nominaAsimiladoInstance!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def nominaAsimilado = new NominaAsimilado()
            nominaAsimilado.validate()
            controller.save(nominaAsimilado)

        then:"The create view is rendered again with the correct model"
            model.nominaAsimiladoInstance!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            nominaAsimilado = new NominaAsimilado(params)

            controller.save(nominaAsimilado)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/nominaAsimilado/show/1'
            controller.flash.message != null
            NominaAsimilado.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def nominaAsimilado = new NominaAsimilado(params)
            controller.show(nominaAsimilado)

        then:"A model is populated containing the domain instance"
            model.nominaAsimiladoInstance == nominaAsimilado
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def nominaAsimilado = new NominaAsimilado(params)
            controller.edit(nominaAsimilado)

        then:"A model is populated containing the domain instance"
            model.nominaAsimiladoInstance == nominaAsimilado
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/nominaAsimilado/index'
            flash.message != null


        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def nominaAsimilado = new NominaAsimilado()
            nominaAsimilado.validate()
            controller.update(nominaAsimilado)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.nominaAsimiladoInstance == nominaAsimilado

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            nominaAsimilado = new NominaAsimilado(params).save(flush: true)
            controller.update(nominaAsimilado)

        then:"A redirect is issues to the show action"
            response.redirectedUrl == "/nominaAsimilado/show/$nominaAsimilado.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/nominaAsimilado/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def nominaAsimilado = new NominaAsimilado(params).save(flush: true)

        then:"It exists"
            NominaAsimilado.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(nominaAsimilado)

        then:"The instance is deleted"
            NominaAsimilado.count() == 0
            response.redirectedUrl == '/nominaAsimilado/index'
            flash.message != null
    }
}
