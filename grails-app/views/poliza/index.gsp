<%@ page import="com.luxsoft.impapx.contabilidad.Poliza" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="taskView">
<g:set var="entityName"	value="${message(code: 'poliza.label', default: 'Poliza')}" />
<g:set var="periodo" value="${session.periodoContable}"/>
<title><g:message code="cuentasPorPagar.list.label" default="Polizas" /></title>
<r:require module="dataTables"/>
</head>
<body>

	<content tag="header">
		<h4>Polizas contables Periodo: ${periodo}</h4>
	</content>
	
	
	<content tag="consultasPanelTitle">Pólizas</content>
 	<content tag="consultas">
 		<g:render template="polizas"/>
 	</content>
 	
 	
 	<content tag="operaciones">
		<li>
			<g:render template="cambiarPeriodo" bean="${session.periodoContable}"/>
 		</li>
 	</content>
 	<content tag="document">
 		
 		<g:if test="${flash.message}">
			<bootstrap:alert class="alert-info">
				${flash.message}
			</bootstrap:alert>
		</g:if>
 	</content>
</body>
</html>

