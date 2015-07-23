<%@ page import="com.luxsoft.impapx.FacturaDeGastos" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="taskView">
		<g:set var="entityName" value="${message(code: 'facturaDeGastos.label', default: 'Factura de Gastos')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
		<r:require module="luxorForms"/>
	</head>
	<body>
	<content tag="header">
		<div class="alert">
			<h4><strong>
			Factura: ${facturaDeGastosInstance.id} (${facturaDeGastosInstance.documento}) ${facturaDeGastosInstance.proveedor.nombre}
			</strong></h4>
		</div>
 	</content>
 	
 	<content tag="consultas">
 		<g:link class="list" action="list">
				<i class="icon-list"></i>
				Gastos
		</g:link>
 	</content>
 	<content tag="operaciones">
 		<li>
 			<g:link class="" action="create">
				<i class="icon-plus "></i>
				Alta de gasto
			</g:link>
		</li>
 	</content>
 	
 	<content tag="document">
 		
 		<g:render template="/shared/messagePanel" model="[beanInstance:facturaDeGastosInstance]"/>
 		
 		<ul class="nav nav-tabs" id="facturaTab">
			<li class=""><a href="#facturaPanel"  data-toggle="tab">Factura</a></li>
			<li class="active"><a href="#conceptosPanel" 	  data-toggle="tab">Conceptos</a></li>
		</ul>
 		
 		
		
		<div class="tab-content">
			
			<div class="tab-pane " id="facturaPanel">
				<g:render template="form" bean="${facturaDeGastosInstance}" 
					model="[cuentaPorPagarInstance:facturaDeGastosInstance,action:'edit']"/>
			</div>
			
			<div class="tab-pane active" id="conceptosPanel">
				 <g:render template="conceptosPanel" 
					model="[facturaDeGastosInstance:facturaDeGastosInstance,conceptos:facturaDeGastosInstance.conceptos]"/>
					 
			</div>
			
		</div>
		
 	</content>
		
	</body>

</html>

