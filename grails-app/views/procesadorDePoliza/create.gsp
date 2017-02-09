<%@ page import="com.luxsoft.lx.contabilidad.ProcesadorDePoliza" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="createForm">
	<g:set var="entityName" value="${message(code: 'procesadorDePoliza.label', default: 'Procesador de poliza')}" scope="request"/>
	<g:set var="entity" value="${procesadorDePolizaInstance}" scope="request" />
	<title>Alta de procesador de poliza</title>
</head>
<body>
	

<content tag="formFields">
	<f:with bean="${procesadorDePolizaInstance}" >
		
		<f:field property="subTipo" widget-autofocus="true" widget="mayusculas" wrapper="bootstrap3"  />
		<f:field property="tipo" wrapper="bootstrap3" widget-class="form-control"  />
		<f:field property="descripcion" wrapper="bootstrap3" widget-class="form-control" />
		
		<f:field property="service" wrapper="bootstrap3" widget-class="form-control" lable="Servicio"/>	
		

		<f:field property="label" wrapper="bootstrap3" widget-class="form-control" label="Etiqueta"/>
		
		
	</f:with>
	
	
</content>

</body>

	
</html>

