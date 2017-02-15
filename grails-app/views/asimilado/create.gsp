<%@ page import="com.luxsoft.nomina.Asimilado" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="createForm">
	<g:set var="entityName" value="${'Asimilado'}" scope="request"/>
	<g:set var="entity" value="${asimiladoInstance}" scope="request" />
	<title>Alta de asimilado a salario</title>
</head>
<body>
	

<content tag="formFields">

	<f:with bean="asimiladoInstance" >
		<f:field property="nombre" widget-class="form-control" wrapper="bootstrap3"/>
		<f:field property="rfc" widget-class="form-control" wrapper="bootstrap3"/>
		<f:field property="curp" widget-class="form-control" wrapper="bootstrap3"/>
		<f:field property="bancoSat" widget-class="form-control chosen-select" wrapper="bootstrap3"/>
		<f:field property="numeroDeCuenta" widget-class="form-control" wrapper="bootstrap3"/>
		<f:field property="clabe" widget-class="form-control" wrapper="bootstrap3"/>
		<f:field property="formaDePago" widget-class="form-control" wrapper="bootstrap3">
			<g:select class="form-control chosen-select "  
				name="${property}" 
				value="${value}"
				from="${['CHEQUE','TRANSFERENCIA']}"/>
		</f:field>
	</f:with>

</content>



</body>
</html>



