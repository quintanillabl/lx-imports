<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>Registro Polizas (SAT)</title>
</head>
<body>

	<div class="container">
		
		<div class="row">

			<div class="col-md-12">

				<div class="page-header">
				  <h3>Alta de registro Polizas para el SAT  <small> (${session.empresa})</small>
				  	<g:if test="${flash.message}">
				  		<small><span class="label label-warning ">${flash.message}</span></small>
				  	</g:if> 
				  	<g:if test="${flash.error}">
				  		<small><span class="label label-danger ">${flash.error}</span></small>
				  	</g:if> 
				  </h3>
				</div>
			</div>
		</div><!-- end .row -->

		<div class="row ">
			
			<div class="col-md-8 col-md-offset-2 ">
				
				<g:form class="form-horizontal" action="save" >	

					<div class="panel panel-primary">
						<div class="panel-heading">Datos generales</div>
					  	<div class="panel-body">
						    <g:hasErrors bean="${satPolizasLogInstance}">
						    	<div class="alert alert-danger">
						    		<ul class="errors" >
						    			<g:renderErrors bean="${satPolizasLogInstance}" as="list" />
						    		</ul>
						    	</div>
						    	
						    </g:hasErrors>
						    <g:hiddenField name="rfc" value="${session.empresa.rfc}"/>
						    <g:hiddenField name="nombre" value="${session.empresa.nombre}"/>
							<f:with bean="${satPolizasLogInstance}">
								<f:field property="ejercicio" widget-class="form-control"/>
								<f:field property="mes" widget-class="form-control"/>
								<f:field property="tipo" widget-class="form-control"/>
								<f:field property="numeroDeOrden"  widget-class="form-control" label="# Orden"/>
								<f:field property="numeroDeTramite" widget-class="form-control" label="# Tramite"/>

								<f:field property="comentario" widget-class="form-control"/>
							</f:with>
					  	</div>
					 
						<div class="panel-footer">
						  	<div class="form-group">
						  		<div class="buttons col-md-offset-4 col-md-4">
						  			<g:submitButton name="Salvar" class="btn btn-primary " />
						  			<g:link action="index" class="btn btn-default"> Cancelar</g:link>
						  		</div>
						  	</div>
						</div>

					</div>

				</g:form>
				
			</div>
		</div> <!-- end .row 2 -->

	</div>
	
	
</body>
</html>