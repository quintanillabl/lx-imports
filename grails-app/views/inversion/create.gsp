<%@ page import="com.luxsoft.impapx.tesoreria.Inversion" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="taskView">
<g:set var="entityName"
	value="${message(code: 'inversion.label', default: 'Inversion')}" />
<title><g:message code="inversion.list.label" default="Inversiones"/></title>
</head>
<body>
	
	<content tag="header">
		<h3>Alta de Inversion</h3>
 	</content>
	<content tag="consultas">
	
		<li><g:link class="list" action="list">
			<i class="icon-list"></i>
			Inversions
			</g:link>
		</li>
	</content>
	
 	<content tag="operaciones">
 		<li><g:link  action="create">Alta de inversión</g:link></li>
 	</content>
 	
 	<content tag="document">
 		<g:render template="/shared/messagePanel" model="[beanInstance:inversionInstance]"/>

		<fieldset>
			<g:form class="form-horizontal" action="create" >
				<fieldset>
				<f:with bean="inversionInstance">
					
					
					<f:field property="cuentaOrigen" label="Cuenta">
						<g:hiddenField name="cuentaOrigen.id" value="${inversionInstance.cuentaOrigen.id}"/>
						<g:field type="text" 
							name="cuenta.nombre"
							value="${inversionInstance.cuentaOrigen}"
							class="input-xxlarge"
							diabled="true"/>
					</f:field>
					<f:field property="fecha"/>
					<f:field property="importe" input-readOnly="true"/>
					<f:field property="plazo"  input-readOnly="true"/>
					<f:field property="tasa"  input-readOnly="true"/>
					<f:field property="tasaIsr" label="ISR (%)" input-readOnly="true"/>
					
					<%-- <f:field property="vencimiento"/>
					<f:field property="comentario" input-class="input-xxlarge"/>
					--%>
				</f:with>
				<div class="form-actions">
					<button type="submit" class="btn btn-primary">
						<i class="icon-ok icon-white"></i>
						<g:message code="default.button.create.label" default="Create" />
					</button>
				</div>
				</fieldset>
			</g:form>
		</fieldset>
		
 	</content>
	
</body>
</html>

