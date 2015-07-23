<html>
<head>
	<meta charset="UTF-8">
	<meta name="layout" content="createForm"/>
	<title>Alta de Empresa</title>
</head>
<body>

	<content tag="header">
		<h3>Registro de empresa nueva</h3>
	</content>
	
	<content tag="operaciones">
		<ul class="nav nav-pills nav-stacked">
  			<li><g:link action="index">
  					<span class="glyphicon glyphicon-arrow-left"></span> Catálogo
  			    </g:link>
  			</li>
  			<li><g:link action="create">
  					<span class="glyphicon glyphicon-plus"></span> Nuevo
  			    </g:link>
  			</li>
		</ul>
	</content>
	
	<content tag="formTitle">Datos generales</content>
	
	<content tag="form">
		
		<g:hasErrors bean="${empresaInstance}">
            <div class="alert alert-danger">
                <g:renderErrors bean="${empresaInstance}" as="list" />
            </div>
        </g:hasErrors>
		
		<g:form class="form-horizontal" action="save" >
			
			<f:with bean="${empresaInstance }">
				<f:field property="nombre" widget-class="form-control uppercase-field " widget-autofocus="on"/>
				<f:field property="rfc" widget-class="form-control uppercase-field"/>
				<f:field property="clave" widget-class="form-control uppercase-field"/>
				<f:field property="regimen" widget-class="form-control"/>
				<g:render template="/common/direccion" bean="${empresaInstance}"/> 
				
			</f:with>
			
			<div class="form-group">
		    	<div class="col-sm-offset-9 col-sm-2">
		      		<button type="submit" class="btn btn-default">
		      			<span class="glyphicon glyphicon-floppy-save"></span> Salvar
		      		</button>
		    	</div>
		  	</div>
		</g:form>
		
		<r:script>
			
		</r:script>

	</content>
	
</body>
</html>