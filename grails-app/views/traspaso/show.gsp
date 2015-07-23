<%@ page import="com.luxsoft.impapx.tesoreria.Traspaso" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="taskView">
<title><g:message code="traspaso.list.label" default="Traspaso entre cuentas"/></title>
</head>
<body>
	
	<content tag="header">
		<h3>Tesorería traspaso</h3>
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
			<g:form class="form-horizontal" action="#" >
				<fieldset>
				<f:with bean="traspasoInstance">
					<f:field property="cuentaOrigen">
						<g:textField name="cuentaOrigen" readOnly="true" value="${traspasoInstance.cuentaOrigen}"/>
					</f:field>
					<f:field property="cuentaDestino">
						<g:textField name="cuentaDestino" readOnly="true" value="${traspasoInstance.cuentaDestino}"/>
					</f:field>
					<f:field property="fecha">
						<lx:shortDate date="${traspasoInstance.fecha}"/>
					</f:field>
					<f:field property="importe">
						<lx:moneyFormat number="${traspasoInstance.importe}"/>
					</f:field>
					<f:field property="comentario" input-class="input-xxlarge" input-readOnly="true"/>
				</f:with>
				
				</fieldset>
			</g:form>
		</fieldset>
		<h3> Movimientos </h3>
		<g:render template="/movimientoDeCuenta/movimientosGrid" model="[movimientoDeCuentaInstanceList:traspasoInstance.movimientos]"/>
		<g:form>
			<g:hiddenField name="id" value="${traspasoInstance?.id}" />
			<div class="form-actions">
				<button class="btn btn-danger" type="submit" name="_action_delete">
					<i class="icon-trash icon-white"></i>
					<g:message code="default.button.delete.label" default="Delete" />
				</button>
			</div>
		</g:form>
		
		
 	</content>
	
</body>
</html>


