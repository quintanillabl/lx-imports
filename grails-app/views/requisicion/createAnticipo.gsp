<%@ page import="com.luxsoft.impapx.Requisicion" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="taskView">
<title><g:message code="anticipo.create.label" default="Alta de anticipo"/></title>
<r:require module="autoNumeric"/>
</head>
<body>
	
	<content tag="header">
		<h3>Alta de Anticipo</h3>
 	</content>
	<content tag="consultas">
	
		<li><g:link class="list" action="anticipos">
			<i class="icon-list"></i>
			Anticipos
			</g:link>
		</li>
	</content>
	
 	<content tag="operaciones">
 		<li><g:link  action="create">Alta de anticipo</g:link></li>
 	</content>
 	
 	<content tag="document">
 		<g:render template="/shared/messagePanel" model="[beanInstance:requisicionInstance]"/>

		<fieldset>
	<g:form class="form-horizontal" action="createAnticipo"
		id="${requisicionInstance?.id}">
		<g:hiddenField name="version" value="${requisicionInstance?.version}" />
		<fieldset>
			<f:with bean="requisicionInstance">
				<f:field property="proveedor"/>
				<f:field property="concepto" >
					<g:textField name="concepto" readOnly="true" value="${requisicionInstance?.concepto }"/>
				</f:field>
				<f:field property="fecha" />
				<f:field property="fechaDelPago" />
				<f:field property="formaDePago" />
				<f:field property="moneda" />
				<f:field property="tc" input-class="tcField" />
				<f:field property="total" class="numericField"/>
				<f:field property="comentario" input-class="comentario" />
			</f:with>
			<div class="form-actions">
				<button type="submit" class="btn btn-primary">
					<i class="icon-ok icon-white"></i>
					<g:message code="default.button.create.label" default="Salvar" />
				</button>
			</div>
		</fieldset>
	</g:form>
</fieldset>
		
 	</content>
 	
 <r:script>
 $(function(){
 	$(".tcField").autoNumeric({vMin:'0.0000'});
 	$(".porcentField").autoNumeric({vMax:'100.00',vMin:'0.00'});
 	$(".numericField").autoNumeric();
 });
 </r:script>
	
</body>
</html>
