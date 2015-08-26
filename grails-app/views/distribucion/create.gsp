<%@ page import="com.luxsoft.impapx.Distribucion" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="createForm">
	<g:set var="entityName" value="${message(code: 'distribucion.label', default: 'Distribución')}" scope="request"/>
	<g:set var="entity" value="${distribucionInstance}" scope="request" />
	<title>Alta de Distribución</title>
</head>
<body>
	

<content tag="formFields">
	<f:with bean="${entity}" >
		<f:field property="fecha" wrapper="bootstrap3"/>
		<f:field property="embarque" wrapper="bootstrap3">
			<g:select class="form-control chosen-select"  
				name="${property}" 
				value="${value?.id}"
				from="${com.luxsoft.impapx.Embarque.findAll("from Embarque e where e not in(select d.embarque from Distribucion d)")}" 
				optionKey="id" 
				optionValue="nombre"
				
				/>
		</f:field>
		<f:field property="comentario" wrapper="bootstrap3" widget-class="form-control"/>
			

		
		%{-- <f:field property="proveedor" label="Agente" wrapper="boostrap3"/>
		<f:field property="embarque" wrapper="boostrap3">
			<g:select class="form-control chosen-select"  
				name="${property}" 
				value="${value?.id}"
				from="${com.luxsoft.impapx.EmbarqueDet.executeQuery("select distinct(det.embarque) from EmbarqueDet det where det not in(select v.embarque from VentaDet v)")}" 
				optionKey="id" 
				optionValue="nombre"
				
				/>
		</f:field>
		<f:field property="referencia" wrapper="boostrap3" widget-class="form-control"/> --}%
		
	</f:with>
	
</content>

</body>
</html>


