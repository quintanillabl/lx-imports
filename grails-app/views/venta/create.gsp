<%@ page import="com.luxsoft.impapx.Venta" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="createForm">
	<g:set var="entityName" value="${message(code: 'venta.label', default: 'Venta')}" scope="request"/>
	<g:set var="entity" value="${ventaInstance}" scope="request" />
	<title>Alta de Venta</title>
</head>
<body>
	

<content tag="formFields">

	<f:with bean="ventaInstance" >
		
		<f:field property="cliente" wrapper="bootstrap3"/>
		<f:field property="clase" wrapper="bootstrap3" widget-class="form-control">
			<g:select class="form-control chosen-select"  
				name="${property}" 
				value="${value}"
				from="${['IMPORTACION','GENERICA']}"/>
		</f:field>
		<f:field property="fecha" wrapper="bootstrap3"/>
		<f:field property="moneda" wrapper="bootstrap3"/>
		<f:field property="tc" widget="tc" widget-disabled="true" wrapper="bootstrap3"/>
		<f:field property="formaDePago" wrapper="bootstrap3">
			<g:select class="form-control chosen-select"  
				name="${property}" 
				value="${value}"
				from="${['CHEQUE','TRANSFERENCIA','EFECTIVO','TARJETA','DEPOSITO']}"/>
			
		</f:field>
		<f:field property="comentario" wrapper="bootstrap3" widget-class="form-control"/>
	</f:with>

</content>

</body>
</html>



