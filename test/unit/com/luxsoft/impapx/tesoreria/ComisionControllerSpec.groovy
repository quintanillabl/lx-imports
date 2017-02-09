package com.luxsoft.impapx.tesoreria



import grails.test.mixin.*
import spock.lang.*

@TestFor(ComisionController)
@Mock(Comision)
class ComisionControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.comisionInstanceList
            model.comisionInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.comisionInstance!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def comision = new Comision()
            comision.validate()
            controller.save(comision)

        then:"The create view is rendered again with the correct model"
            model.comisionInstance!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            comision = new Comision(params)

            controller.save(comision)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/comision/show/1'
            controller.flash.message != null
            Comision.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def comision = new Comision(params)
            controller.show(comision)

        then:"A model is populated containing the domain instance"
            model.comisionInstance == comision
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def comision = new Comision(params)
            controller.edit(comision)

        then:"A model is populated containing the domain instance"
            model.comisionInstance == comision
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/comision/index'
            flash.message != null


        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def comision = new Comision()
            comision.validate()
            controller.update(comision)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.comisionInstance == comision

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            comision = new Comision(params).save(flush: true)
            controller.update(comision)

        then:"A redirect is issues to the show action"
            response.redirectedUrl == "/comision/show/$comision.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/comision/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def comision = new Comision(params).save(flush: true)

        then:"It exists"
            Comision.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(comision)

        then:"The instance is deleted"
            Comision.count() == 0
            response.redirectedUrl == '/comision/index'
            flash.message != null
    }
}
