<%@ page import="com.luxsoft.impapx.tesoreria.Inversion" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="taskView">
<title><g:message code="inversion.list.label" default="Inversiones"/></title>
</head>
<body>
	
	<content tag="header">
		<h3>Tesorería - Inversiones</h3>
 	</content>
	<content tag="consultas">
 		<g:render template="/movimientoDeCuenta/consultas"/>
 	</content>
 	<content tag="operaciones">
 		<%-- <li><g:link  action="create">Alta de Inversión</g:link></li>--%>
 		<li>
			<a href="#createDialog" data-toggle="modal"><i class="icon-plus"></i>Registrar </a>
		</li>
 	</content>
 	<content tag="document">
 		<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
		</g:if>
 		<table class="table table-striped table-hover table-bordered table-condensed">
			<thead>
				<tr>
					<g:sortableColumn property="folio" title="${message(code: 'inversion.comentario.label', default: 'Folio')}" />
					<g:sortableColumn property="fecha" title="${message(code: 'inversion.fecha.label', default: 'Fecha')}" />
					<g:sortableColumn property="fecha" title="${message(code: 'inversion.fecha.label', default: 'Plazo')}" />
					<g:sortableColumn property="fecha" title="${message(code: 'inversion.fecha.label', default: 'Vto')}" />
					<th class="header"><g:message code="inversion.cuentaOrigen.label" default="Cuenta Origen" /></th>
					<g:sortableColumn property="importe" title="${message(code: 'inversion.comision.label', default: 'Importe')}" />
					<th>Rendimiento</th>
					<g:sortableColumn property="comision" title="${message(code: 'inversion.comision.label', default: 'ISR')}" />
					
				</tr>
			</thead>
			<tbody>
				<g:each in="${inversionInstanceList}" var="inversionInstance">
					<tr>
						<td><g:link action="show" id="${inversionInstance.id}">${fieldValue(bean: inversionInstance, field: "id")}</g:link></td>
						<td><lx:shortDate date="${inversionInstance.fecha}"/></td>
						<td>${fieldValue(bean: inversionInstance, field: "plazo")}</td>
						<td><lx:shortDate date="${inversionInstance.vencimiento}"/></td>
						<td>${fieldValue(bean: inversionInstance, field: "cuentaOrigen")}</td>
						<td><lx:moneyFormat number="${inversionInstance.importe}"/></td>
						<td><lx:moneyFormat number="${inversionInstance.rendimientoReal}"/></td>
						<td><lx:moneyFormat number="${inversionInstance.importeIsr}"/></td>
					</tr>
				</g:each>
			</tbody>
		</table>
		<div class="pagination">
			<bootstrap:paginate total="${inversionInstanceTotal}" />
		</div>
		
		<div class="modal hide fade" id="createDialog" tabindex=-1 role="dialog" aria-labelledby="myCreateLabel">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="myCreateLabel">Registrar inversión</h4>
			</div>
			
			<div class="modal-body">
				<fieldset>
				<g:form action="create" method="GET">
					<fieldset>
					<f:with bean="${new Inversion()}">
						<f:field property="cuentaOrigen" label="Cuenta"/>
					</f:with>
					<div class="form-actions">
						<a href="#" class="btn" data-dismiss="modal">Cancelar</a>
						<button type="submit" class="btn btn-primary">
							<i class="icon-ok icon-white"></i> Aceptar
						</button>
					</div>
					</fieldset>
				</g:form>
				</fieldset>
			</div>
			
		</div>
		
 	</content>
	
</body>
</html>
