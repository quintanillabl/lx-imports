<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="layout" content="application"/>
	<title>LX Mobix</title>
</head>
<body>
	<div class="container">
		<div class="row vertical-align">
			<div class="col-md-offset-4 col-md-4 ">
				
				<div class="panel panel-primary">
				  
				  <div class="panel-heading text-center">
				      <h3 class="panel-title">Inmobiliarias</h3>
				   </div>

				   <div class="list-group">
				     <g:if test="${!empresas}">
				     	<g:link controller="empresa" action="create" class="list-group-item"> Alta de empresa</g:link>
				     </g:if>
				     <g:else>
				     	<g:each in="${empresas}" var="empresa">
				     		<g:link action="setCurrentEmpresa" class="list-group-item" id="${empresa.id}"> 
				     			${empresa.nombre}
				     		</g:link>
				     	</g:each>
				     	
				     </g:else>
				     
				   </div>
				   <div class="panel-footer">
				   		Seleccione una inmobiliaria 
				   </div>

				</div>

				
				
			</div>
		</div>
	</div>
</body>
</html>