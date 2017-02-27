<%@ page import="com.luxsoft.nomina.Asimilado" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="showForm">
	<g:set var="entityName" value="${'Asimilado'}" scope="request"/>
	<g:set var="entity" value="${asimiladoInstance}" scope="request" />
	<title>Alta de asimilado a salario</title>
</head>
<body>
	

<content tag="formFields">

	<f:with bean="asimiladoInstance" >
		<f:display property="nombre" widget-class="form-control" wrapper="bootstrap3"/>
		<f:display property="rfc" widget-class="form-control" wrapper="bootstrap3"/>
		<f:display property="curp" widget-class="form-control" wrapper="bootstrap3"/>
		<f:display property="bancoSat" widget-class="form-control chosen-select" wrapper="bootstrap3"/>
		<f:display property="numeroDeCuenta" widget-class="form-control" wrapper="bootstrap3"/>
		<f:display property="clabe" widget-class="form-control" wrapper="bootstrap3"/>
		<f:display property="formaDePago" widget-class="form-control" wrapper="bootstrap3"/>
	</f:with>

</content>

<content tag="deleteButton">
	<lx:deleteButton bean="${asimiladoInstance}"></lx:deleteButton>
</content>


</body>
</html>

