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
		<h3>Inversión</h3>
 	</content>
	<content tag="consultas">
	
		<li><g:link class="list" action="list">
			<i class="icon-list"></i>
			Inversions
			</g:link>
		</li>
	</content>
	
 	<content tag="operaciones">
 		<%-- <li><g:link  action="create">Alta de inversión</g:link></li>--%>
 	</content>
 	
 	<content tag="document">
 		<g:render template="/shared/messagePanel" model="[beanInstance:inversionInstance]"/>

		<fieldset>
			<g:form class="form-horizontal" action="#" >
				<fieldset>
				<f:with bean="inversionInstance">
					<f:field property="fecha" input-readOnly="true"/>
					<f:field property="cuentaOrigen" label="Cuenta">
						<g:field type="string" name="cuentaOrigen" value="${inversionInstance.cuentaOrigen}" readOnly="true" class="input-xxlarge"/>
					</f:field>
					<f:field property="importe" input-readOnly="true"/>
					<f:field property="plazo" input-readOnly="true"/>
					<f:field property="tasa" input-readOnly="true"/>
					<f:field property="tasaIsr" label="ISR (%)" input-readOnly="true"/>
					<f:field property="importeIsr" label="ISR" input-readOnly="true"/>
					<f:field property="vencimiento" input-readOnly="true"/>
					<f:field property="comentario" input-class="input-xxlarge" input-readOnly="true"/>
				</f:with>
				
				</fieldset>
			</g:form>
		</fieldset>
		<h3> Movimientos </h3>
		<g:render template="/movimientoDeCuenta/movimientosGrid" model="[movimientoDeCuentaInstanceList:inversionInstance.movimientos]"/>
		<g:form>
			<g:hiddenField name="id" value="${inversionInstance?.id}" />
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

