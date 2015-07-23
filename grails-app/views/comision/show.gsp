<%@ page import="com.luxsoft.impapx.tesoreria.Comision" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="taskView">
<title><g:message code="comision.list.label" default="Alta de comisiones"/></title>

</head>
<body>
	
	<content tag="header">
		<h3>Comisión bancaria</h3>
 	</content>
	<content tag="consultas">
	
		<li><g:link class="list" action="list">
			<i class="icon-list"></i>
			Comisiones
			</g:link>
		</li>
	</content>
	
 	<content tag="operaciones">
 		<li><g:link  action="create">Alta de comisión</g:link></li>
 	</content>
 	
 	<content tag="document">
 		<g:render template="/shared/messagePanel" model="[beanInstance:comisionInstance]"/>

		<fieldset>
			<g:form class="form-horizontal" action="#" >
				<fieldset>
				<f:with bean="comisionInstance">
					<f:field property="fecha" >
						<lx:shortDate date="${comisionInstance.fecha }"/>
					</f:field>
					<f:field property="cuenta" >
						<g:textField name="cuenta" readOnly="true" value="${comisionInstance.cuenta}"/>
					</f:field>
					<f:field property="comision" >
						<g:textField name="comision" readOnly="true" value="${comisionInstance.comision}"/>
					</f:field>
					<f:field property="tc" input-class="input-xxlarge" input-readOnly="true"/>
					<f:field property="impuestoTasa" label="Tasa de impuesto(%)" input-class="porcentField" input-readOnly="true"/>
					<f:field property="impuesto" input-class="moneyField" input-readOnly="true"/>
					<f:field property="referenciaBancaria" input-class="input-xxlarge" input-readOnly="true"/>
					<f:field property="comentario" input-class="input-xxlarge" input-readOnly="true"/>
					
				</f:with>
				</fieldset>
			</g:form>
		</fieldset>
		<h3> Movimientos </h3>
		<g:render template="/movimientoDeCuenta/movimientosGrid" model="[movimientoDeCuentaInstanceList:comisionInstance.movimientos]"/>
		<g:form>
			<g:hiddenField name="id" value="${comisionInstance?.id}" />
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