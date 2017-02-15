<%@ page import="com.luxsoft.nomina.NominaAsimilado" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="createForm">
	<g:set var="entityName" value="${'nominaAsimilado'}" scope="request"/>
	<g:set var="entity" value="${nominaAsimiladoInstance}" scope="request" />
	<title>Alta de nómina  de asimilado a salario</title>
</head>
<body>
	

<content tag="formFields">

	<f:with bean="nominaAsimiladoInstance" >
		<f:field property="asimilado" widget-class="form-control chosen-select" wrapper="bootstrap3"/>
		<f:field property="formaDePago" widget-class="form-control chosen-select" wrapper="bootstrap3"/>
		<f:field property="fecha" widget-class="form-control chosen-select" wrapper="bootstrap3"/>
		<f:field property="pago" widget-class="form-control chosen-select" wrapper="bootstrap3"/>
		<f:field property="concepto" widget-class="form-control" wrapper="bootstrap3"/>
		<f:field property="percepciones" widget="numeric" wrapper="bootstrap3"/>
	</f:with>

</content>



</body>
</html>



