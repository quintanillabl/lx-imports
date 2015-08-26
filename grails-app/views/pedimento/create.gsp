<%@ page import="com.luxsoft.impapx.Pedimento" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="createForm">
	<g:set var="entityName" value="${message(code: 'pedimento.label', default: 'Pedimento')}" scope="request"/>
	<g:set var="entity" value="${pedimentoInstance}" scope="request" />
	<title>Alta de Pedimento</title>
</head>
<body>
	

<content tag="formFields">
	<f:with bean="${pedimentoInstance}" >
		<g:if test="${pedimentoInstance.id}">
			<f:display property="id" wrapper="bootstrap3" />
		</g:if>
		<f:field property="pedimento" widget-autofocus="true" wrapper="bootstrap3" widget-class="form-control" />
		<f:field property="proveedor" wrapper="bootstrap3"  label="Agente aduanal"/>
		<f:field property="fecha" wrapper="bootstrap3" />
		<f:field property="dta" wrapper="bootstrap3" widget="money"/>
		<f:field property="prevalidacion" wrapper="bootstrap3" widget="money"/>
		<f:field property="arancel" wrapper="bootstrap3" widget="money"/>
		<f:field property="tipoDeCambio" wrapper="bootstrap3"  widget="tc"/>
		<f:field property="impuestoTasa" wrapper="bootstrap3" widget="porcentaje"/>
		<f:field property="comentario" wrapper="bootstrap3" widget-class="form-control"/>
	</f:with>

<script type="text/javascript">
	$(function(){
		$("#pedimento").mask("99-99-9999-9999999");
		
	});
</script>
	
</content>
</body>

	
</html>
