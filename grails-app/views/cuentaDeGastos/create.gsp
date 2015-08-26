<%@ page import="com.luxsoft.impapx.CuentaDeGastos" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="createForm">
	<g:set var="entityName" value="${message(code: 'cuentaDeGastos.label', default: 'CuentaDeGastos')}" scope="request"/>
	<g:set var="entity" value="${cuentaDeGastosInstance}" scope="request" />
	<title>Alta de Cuenta de gastos</title>
</head>
<body>
	

<content tag="formFields">
	<f:with bean="${cuentaDeGastosInstance}" >
		<f:field property="fecha" wrapper="boostrap3"/>
		<f:field property="proveedor" label="Agente" wrapper="boostrap3"/>
		<f:field property="embarque" wrapper="boostrap3">
			<g:select class="form-control chosen-select"  
				name="${property}" 
				value="${value?.id}"
				from="${com.luxsoft.impapx.EmbarqueDet.executeQuery("select distinct(det.embarque) from EmbarqueDet det where det not in(select v.embarque from VentaDet v)")}" 
				optionKey="id" 
				optionValue="nombre"
				
				/>
		</f:field>
		<f:field property="referencia" wrapper="boostrap3" widget-class="form-control"/>
		<f:field property="comentario" wrapper="boostrap3" widget-class="form-control">
			<g:textArea name="comentario" value="${value}" cols="40" rows="4" />
		</f:field>
	</f:with>

<script type="text/javascript">
	$(function(){
		
		
	});
</script>
	
</content>

</body>
</html>
