<%@ page import="com.luxsoft.impapx.tesoreria.Traspaso" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="taskView">
<g:set var="entityName"
	value="${message(code: 'traspaso.label', default: 'Traspaso')}" />
<title><g:message code="traspaso.list.label" default="Traspaso entre cuentas"/></title>
</head>
<body>
	
	<content tag="header">
		<h3>Alta de traspaso</h3>
 	</content>
	<content tag="consultas">
	
		<li><g:link class="list" action="list">
			<i class="icon-list"></i>
			Traspasos
			</g:link>
		</li>
	</content>
	
 	<content tag="operaciones">
 		<li><g:link  action="create">Alta de traspaso</g:link></li>
 	</content>
 	
 	<content tag="document">
 		<g:render template="/shared/messagePanel" model="[beanInstance:traspasoInstance]"/>

		<fieldset>
			<g:form class="form-horizontal" action="create" >
				<fieldset>
				<f:with bean="traspasoInstance">
					<f:field property="cuentaOrigen"/>
					<f:field property="cuentaDestino"/>
					<f:field property="fecha"/>
					<f:field property="importe"/>
					<f:field property="comentario" input-class="input-xxlarge"/>
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

