<html>
<head>
	<meta charset="UTF-8">
	<meta name="layout" content="luxor"/>
	<title>Usuario: ${usuarioInstance.id}</title>
</head>
<body>

	<content tag="header">
		Cambio de Password ${usuarioInstance.nombre} 
	</content>
	<content tag="document">
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