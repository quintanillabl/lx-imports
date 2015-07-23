<%@ page import="com.luxsoft.impapx.FacturaDeGastos" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="taskView">
<g:set var="entityName"
	value="${message(code: 'facturaDeGastos.label', default: 'Gastos')}" />
<title><g:message code="cuentasPorPagar.list.label" default="Gastos (CxP)" /></title>
<r:require module="dataTables"/>
</head>
<body>
	
	<content tag="header">
 		<h3><g:message code="cuentasPorPagar.header.label" default="Facturas de Gastos (CxP)" /></h3>
 	</content>
 	
 	<content tag="consultas">
 		<li>
 		<g:link controller="cuentaPorPagar" action="list">
 			<i class="icon-list "></i>
				C X P
		</g:link>
<%--		<g:link  action="list">--%>
<%--				Gastos (Todos)--%>
<%--		</g:link>--%>
<%--		<g:link  action="list">--%>
<%--				Pendientes de pago--%>
<%--		</g:link>--%>
<%--		<g:link  action="list">--%>
<%--				Pagados--%>
<%--		</g:link>--%>
		</li>
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
 		<g:if test="${flash.message}">
			<bootstrap:alert class="alert-info">
				${flash.message}
			</bootstrap:alert>
		</g:if>
		<g:render template="/cuentaPorPagar/facturasPanel" 
			model="['cuentaPorPagarInstance':facturaDeImportacionInstance
					,'facturasList':facturaDeGastosInstanceList
					,'cuentaPorPagarInstanceTotal':facturaDeGastosInstanceTotal]"
					/>
 	</content>	
	
</body>
</html>


