<%@ page import="com.luxsoft.impapx.tesoreria.PagoProveedor" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="taskView">
<title><g:message code="comision.list.label" default="Alta de pago"/></title>
<r:require module="autoNumeric"/>
</head>
<body>
	
	<content tag="header">
		<h3>Pago a proveedor</h3>
 	</content>
	<content tag="consultas">
	
		<li><g:link class="list" action="list">
			<i class="icon-list"></i>
			Pagos
			</g:link>
		</li>
	</content>
	
 	<content tag="operaciones">
 		<li><g:link  action="create"><i class="icon-plus "></i> Alta de pago</g:link></li>
 	</content>
 	
 	<content tag="document">
 		<g:render template="/shared/messagePanel" model="[beanInstance:pagoProveedorInstance]"/>

		<fieldset>
			<g:form class="form-horizontal" action="#" >
				<fieldset>
					<f:with bean="pagoProveedorInstance">
						<f:field property="fecha">
							<lx:shortDate date="${pagoProveedorInstance.fecha}"/>
						</f:field>
						<f:field property="cuenta">
							<g:textField name="cuenta" value="${pagoProveedorInstance.cuenta}"/>
						</f:field>
						<f:field property="requisicion">
							<g:textField name="cuenta" value="${pagoProveedorInstance.requisicion}"/>
						</f:field>
						<f:field property="egreso" label="Pago">
							<g:textField name="egreso" value="${pagoProveedorInstance.egreso}" class="input-xxlarge"/>
						</f:field>
						<f:field property="comentario" input-class="input-xxlarge" input-readOnly="true"/>
					</f:with>
				</fieldset>
				<g:form>
			<g:hiddenField name="id" value="${pagoProveedorInstance?.id}" />
			<div class="form-actions">
				<%-- 
				<g:link class="btn" action="edit" id="${pagoProveedorInstance?.id}">
					<i class="icon-pencil"></i>
					<g:message code="default.button.edit.label" default="Edit" />
				</g:link>
				--%>
				<button class="btn btn-danger" type="submit" name="_action_delete">
					<i class="icon-trash icon-white"></i>
					<g:message code="default.button.delete.label" default="Delete" />
				</button>
			</div>
		</g:form>
			</g:form>
		</fieldset>
		
 	</content>	
</body>
</html>
