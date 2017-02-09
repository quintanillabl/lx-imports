<%@ page import="com.luxsoft.impapx.Compra" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="createForm">
	<g:set var="entityName" value="${message(code: 'compra.label', default: 'Compra')}" scope="request"/>
	<g:set var="entity" value="${compraInstance}" scope="request" />
	<title>Alta de ${entityName}</title>
</head>
<body>

<content tag="formFields">
	<f:with bean="compraInstance">
		<f:field property="proveedor" 
			wrapper="bootstrap3" widget-tabindex="2"/>
		<f:field property="fecha" wrapper="bootstrap3"/>
		<f:field property="entrega" wrapper="bootstrap3"/>
		<f:field property="moneda" wrapper="bootstrap3"/>
		<f:field property="tc" widget="tc" wrapper="bootstrap3" widget-type="text"/>
		<f:field property="comentario" 
			widget-class="form-control" wrapper="bootstrap3"/>
		%{-- <f:field property="fecha" wrapper="bootstrap3"/>
		<f:field property="formaDePago" 
			widget-class="form-control chosen-select" wrapper="bootstrap3"/>
		<f:field property="cuenta" 
			widget-class="form-control chosen-select" label="Cuenta destino" wrapper="bootstrap3"/>
		
		<f:field property="total" widget="money" wrapper="bootstrap3" />
		<f:field property="fechaBancaria" wrapper="bootstrap3"/>
		<f:field property="referenciaBancaria" 
			widget-class="form-control" wrapper="bootstrap3"/>
		 --}%
		

	</f:with>
</content>


</body>
</html>
