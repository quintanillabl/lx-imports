<%@ page import="com.luxsoft.impapx.Embarque" %>
<!doctype html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<g:set var="entityName" value="${message(code: 'embarque.label', default: 'Embarque')}" />
	<title><g:message code="default.create.label" args="[entityName]" /></title>
</head>
<body>
	<div class="container">
		
		<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<div class="page-header">
				  <h1><g:message code="default.create.label" args="[entityName]" /></h1>
				  	<g:if test="${flash.message}">
				  		<small><span class="label label-warning ">${flash.message}</span></small>
				  	</g:if> 
				  </h1>
				</div>
			</div>
		</div><!-- end .row -->

		<div class="row ">
			
			<div class="col-md-6 col-md-offset-3">
				<g:form class="form-horizontal" action="save" >

					<div class="panel panel-primary">
						<div class="panel-heading">
							<g:message code="default.create.label" args="[entityName]" />
						</div>
					  	<div class="panel-body">
					    	<g:hasErrors bean="embarqueInstance">
					    		<div class="alert alert-danger">
					    			<ul class="errors" >
					    				<g:renderErrors bean="embarqueInstance" as="list" />
					    			</ul>
					    		</div>
					    	</g:hasErrors>
							<f:with bean="embarqueInstance">
								<f:field property="bl" input-class="mayusculas" ></f:field>
								<f:field property="nombre" input-class="mayusculas" ></f:field>
								<f:field property="fechaEmbarque" label="F.Embarque"/>
								 <f:field property="proveedor" ></f:field>
								 <f:field property="aduana"></f:field>
								 <f:field property="ingresoAduana" widget-class="form-control" label="ETA"/>
								<f:field property="contenedores" widget-class="form-control"/>
								<f:field property="comentario" >
									<g:textArea name="comentario" class="comentario form-control" />
								</f:field>
								
							</f:with>
					  	</div>
					 
					  	<div class="panel-footer">
					  		<div class="form-group">
					  			<div class="buttons col-md-offset-2 col-md-4">
					  				<g:submitButton name="create" 
					  					class="btn btn-primary " 
					  					value="${message(code: 'default.button.create.label', default: 'Salvar')}"/>
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
