<html>
<head>
	<meta charset="UTF-8">
	<meta name="layout" content="createForm"/>
	<title>Usuario: ${usuarioInstance.id}</title>
</head>
<body>

	<content tag="header">
		<h3>Cambio de Password${usuarioInstance.nombre}</h3>
	</content>
	
	<content tag="operaciones">
		<ul class="nav nav-pills nav-stacked">
			<li><g:link action="index">
  					<span class="glyphicon glyphicon-arrow-left"></span> Catálogo
  			    </g:link>
  			</li>
  			<li><g:link action="edit" id="${usuarioInstance.id}">
  					<span class="glyphicon glyphicon-arrow-rigth"></span> Regresar
  			    </g:link>
  			</li>
		</ul>
	</content>
	
	<content tag="formTitle">Usuario: ${usuarioInstance.id}</content>
	
	<content tag="form">
		
		<g:hasErrors bean="${passwordCommand}">
            <div class="alert alert-danger">
                <g:renderErrors bean="${passwordCommand}" as="list" />
            </div>
        </g:hasErrors>
		
		<g:form class="form-horizontal" action="cambioDePassword" >
			<div class="form-panel">
				<g:hiddenField name="id" value="${usuarioInstance.id}"/>
				<g:hiddenField name="version" value="${usuarioInstance.version}"/>

				<f:with bean="${passwordCommand}">
					<f:field property="password" widget-class="form-control" widget-type="password" widget-autocomplete="off"/>
					<f:field property="confirmarPassword" 
					widget-class="form-control" label="Confirmar" widget-type="password"  widget-autocomplete="off"/>
				</f:with>

				<div class="form-group">
			    	<div class="col-sm-offset-8 col-sm-4">
			    		
			      		<button type="submit" class="btn btn-primary">
			      			<span class="glyphicon glyphicon-floppy-save"></span> Salvar
			      		</button>
			    	</div>
			  	</div>
				
			</div>
		</g:form>
		  
		
		
		

	</content>
	
</body>
</html>