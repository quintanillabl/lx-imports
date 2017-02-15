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
	                <div class="ibox-content">
	                    <form class="form-horizontal" >  
	                        <f:with bean="${entity}" >
	                        	<f:display property="asimilado" widget-class="form-control chosen-select" wrapper="bootstrap3"/>
	                        	<f:display property="formaDePago" widget-class="form-control chosen-select" wrapper="bootstrap3"/>
	                        	<f:display property="fecha" widget-class="form-control chosen-select" wrapper="bootstrap3"/>
	                        	<f:display property="pago" widget-class="form-control chosen-select" wrapper="bootstrap3"/>
	                        	<f:display property="concepto" widget-class="form-control" wrapper="bootstrap3"/>
	                        	<f:display property="percepciones" widget="money" wrapper="bootstrap3"/>
	                        </f:with>
	                        
	                    </form>
	                </div>
	                <div class="ibox-footer">
	                    <div class="btn-group">
	                        <lx:backButton/>
	                        <lx:createButton/>
	                        <lx:editButton id="${entity?.id}" />
	                        <lx:printButton/>
	                        <lx:deleteButton bean="${entity}"/>
	                    </div>
	                    <g:if test="${!entity.cfdi}">
	                    	<div class="btn-group">
	                    		<g:link class="btn btn-primary btn-outline" action="generarCfdi" id="${nominaAsimiladoInstance.id}" >
	                    	    	<i class="fa fa-file-excel-o"></i> Generar CFDI
	                    	    </g:link>
	                    	</div>
	                    </g:if>
	                    <g:if test="${entity.cfdi}">
	                    	<g:link class="btn btn-default btn-outline" 
  								action="mostrarXml" id="${entity.cfdi.id}">XML
  							</g:link>
  							<g:link class="btn btn-default btn-outline" action="descargarXml" id="${entity.cfdi.id}" >
								<span class="glyphicon glyphicon-cloud-download"> Descargar XML</span>
							</g:link>
  							
	                    </g:if>
	                    <g:if test="${entity.cfdi && !entity.cfdi.uuid}">
	                    	<div class="btn-group">
	                    		<g:link class="btn btn-primary btn-outline" action="timbrar" id="${nominaAsimiladoInstance.id}" >
	                    	    	<span class="glyphicon glyphicon-screenshot"></span> Timbrar
	                    	    </g:link>
	                    	</div>
	                    </g:if>
	                </div>
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



