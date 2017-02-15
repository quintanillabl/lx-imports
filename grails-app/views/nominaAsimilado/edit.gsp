<%@ page import="com.luxsoft.nomina.NominaAsimilado" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="application">
	<g:set var="entityName" value="${'nominaAsimilado'}" scope="request"/>
	<g:set var="entity" value="${nominaAsimiladoInstance}" scope="request" />
	<title>Alta de nómina  de asimilado a salario</title>
</head>
<body>
	<div class="row wrapper border-bottom white-bg page-heading">
	    <div class="col-lg-10">
	        <h2>Nómina: ${entity.id}  ${entity.asimilado.nombre} <small>(Asimilado a salario)</small></h2>
	        <ol class="breadcrumb">
	            <li><g:link action="index">nominas</g:link></li>
	            <li><g:link action="create">Alta</g:link></li>
	            <li class="active"><strong>Consulta</strong></li>
	            <li><g:link action="edit" id="${entity.id}">Edición</g:link></li>
	        </ol>
	        
	        <g:if test="${flash.message}">
	            <small><span class="label label-warning ">${flash.message}</span></small>
	        </g:if> 
	        <g:if test="${flash.error}">
	            <small><span class="label label-danger ">${flash.error}</span></small>
	        </g:if> 
	    </div>
	</div>

	<lx:wrapper>
	    <div class="row">
	        <div class="col-lg-8 col-lg-offset-2">
	            <div class="ibox float-e-margins">
	                <div class="ibox-title">
	                    <h5>Propiedades</h5>
	                </div>
	                <g:form class="form-horizontal" action="update" method="PUT">  
	                	
	                	<g:hiddenField name="id" value="${entity.id}"/>
	                	<g:hiddenField name="version" value="${entity.version}"/>

		                <div class="ibox-content">
	                        <f:with bean="${entity}" >
	                        	<f:field property="asimilado" widget-class="form-control chosen-select" wrapper="bootstrap3"/>
	                        	<f:field property="formaDePago" widget-class="form-control chosen-select" wrapper="bootstrap3"/>
	                        	<f:field property="fecha" widget-class="form-control chosen-select" wrapper="bootstrap3"/>
	                        	<f:field property="pago" widget-class="form-control chosen-select" wrapper="bootstrap3"/>
	                        	<f:field property="concepto" widget-class="form-control" wrapper="bootstrap3"/>
	                        	<f:field property="percepciones" widget="money" wrapper="bootstrap3"/>
	                        </f:with>
		                </div>
		                <div class="ibox-footer">
		                    <div class="btn-group">
		                        <lx:backButton action="show" id="${entity.id}"/>
		                        <button type="submit" class="btn btn-success btn-outline">
		                        	<i class="fa fa-save"></i> Actualizar
		                        </button>
		                        
		                    </div>
		                </div>
	                </g:form>

	            </div>
	        </div>

	        <div class="col-lg-6">
	        	<lx:ibox>
	        		<lx:iboxTitle title="Percepciones"/>
	        		<g:render template="conceptos" model="[param:'PERCEPCION']" bean="${nominaAsimiladoInstance}"/>
	        	</lx:ibox>
	        	
	        </div>

	        <div class="col-lg-6">
	        	<lx:ibox>
	        		<lx:iboxTitle title="Deducciones"/>
	        		<g:render template="conceptos" model="[param:'DEDUCCION']" bean="${nominaAsimiladoInstance}"/>
	        	</lx:ibox>
	        </div>
	    </div>    		
	</lx:wrapper>

	
	




</body>
</html>



