<%@ page import="com.luxsoft.impapx.FacturaDeImportacion" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="taskView">
<g:set var="entityName"
	value="${message(code: 'gastosDeImportacion.label', default: 'Facturas')}" />
<title><g:message code="cuentasPorPagar.list.label" default="Cuentas por pagar (CxP)" /></title>
<r:require module="dataTables"/>
</head>
<body>
	<content tag="consultasPanelTitle">
 		Facturas
 	</content>
 	<content tag="consultas">
 		<g:render template="/cuentaPorPagar/actions"/>
 	</content>
 	<content tag="operaciones">
 		<li>
 			<g:link class="" action="create">
				<i class="icon-plus "></i>
				<g:message code="cuentaPorPagar.create.label"  />
			</g:link>
		</li>
 	</content>
 	<content tag="document">
 		<h3><g:message code="facturaDeImportacion.list.label" default="Facturas de gastos de importación"/></h3>
 		<g:if test="${flash.message}">
			<bootstrap:alert class="alert-info">
				${flash.message}
			</bootstrap:alert>
		</g:if>
		<g:render template="/cuentaPorPagar/facturasPanel" 
			model="['cuentaPorPagarInstance':gastosDeImportacionInstance
					,'facturasList':gastosDeImportacionInstanceList
					,'cuentaPorPagarInstanceTotal':gastosDeImportacionInstanceTotal]"
					/>
 	</content>
</body>
</html>
