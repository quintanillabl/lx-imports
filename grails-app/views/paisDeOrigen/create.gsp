<%@ page import="com.luxsoft.impapx.PaisDeOrigen" %>
<!doctype html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<g:set var="entityName" value="${message(code: 'paisDeOrigen.label', default: 'Pais de origen')}" />
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
					    	<g:hasErrors bean="paisDeOrigenInstance">
					    		<div class="alert alert-danger">
					    			<ul class="errors" >
					    				<g:renderErrors bean="paisDeOrigenInstance" as="list" />
					    			</ul>
					    		</div>
					    	</g:hasErrors>
							
							<f:field bean="paisDeOrigenInstance" property="nombre" wrapper="bootstrap3" widget="mayusculas" />
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
