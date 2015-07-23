<%@ page import="com.luxsoft.impapx.CuentaDeGastos" %>
<!doctype html>
<html>
	<head>
		
		<g:set var="entityName" value="${message(code: 'cuentaDeGastos.label', default: 'Cuenta De Gastos')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
		<asset:stylesheet src="datatables/dataTables.css"/>
		<asset:javascript src="datatables/dataTables.js"/> 
		<asset:javascript src="forms/forms.js"/>
	</head>
	<body>
		<div class="container">
		<div class="row">
			<div class="row">
				<div class="col-md-12">
					<div class="page-header">
						<h1>Cuenta: ${cuentaDeGastosInstance.id} 
							<small>Embarque: ${cuentaDeGastosInstance.embarque}</small></h1>
						<g:if test="${flash.message}">
						  	<span class="label label-warning">${flash.message}</span>
						</g:if>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-12">
					<ul class="nav nav-tabs" role="tablist">
						<li class="">
							<g:link action="index" >
							    <i class="fa fa-step-backward"></i> Cuentas
							</g:link> 
						</li>
					   <li class=""><a href="#cuenta" data-toggle="tab">Cuenta</a></li>
					   <li class="active"><a href="#facturas" data-toggle="tab">Facturas</a></li>
					</ul>
			  		<div class="tab-content"> <!-- Tab Content -->
						<div class="tab-pane " id="cuenta">
							<g:render template="editForm" bean="${cuentaDeGastosInstance}"/>
						</div>
						
						<div class="tab-pane active" id="facturas">
							<g:render template="facturasPanel" bean="${cuentaDeGastosInstance}"/>
						</div>			
			  		</div>	<!-- End Tab Content -->
				</div>
			</div>
			
			

		</div>
		</div>
	</body>
</html>
