<%@ page import="com.luxsoft.impapx.Requisicion" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="operaciones">
	<title>Notas de crédito</title>
</head>
<body>
<content tag="header">
	Notas de crédito
</content>
<content tag="subHeader">
	<ol class="breadcrumb">
	    <li >
	        <g:link action="index" params="[tipo:'PENDIENTES']">

	        	<g:if test="${tipo=='PENDIENTES'}">
	        		<strong>Pendientes</strong>
	        	</g:if>
	        	<g:else>
	        		Pendientes
	        	</g:else>
	        </g:link>
	    </li>
	    <li>
	        <g:link action="index" params="[tipo:'PAGADAS']">
	    		<g:if test="${tipo=='PAGADAS'}">
	    			<strong>Pagadas</strong>
	    		</g:if>
	    		<g:else>
	    			Pagadas
	    		</g:else>
	    	</g:link>
	    </li>
	    <li >
	    	<g:link action="index" params="[tipo:'TODAS']">
	    		<g:if test="${tipo=='TODAS'}">
	    			<strong>Todas</strong>
	    		</g:if>
	    		<g:else>
	    			Todas
	    		</g:else>
	    	</g:link>
	    </li>
	</ol>
</content>

<content tag="periodo">
	Periodo:${session.periodo.mothLabel()} 
</content>

<content tag="grid">
	<table id="notasGrid" class="table table-striped table-hover table-bordered table-condensed">
		<thead>
			<tr>
				<td>Id</td>
				<td>Cliente</td>
				<td>Fecha</td>
				<td>Tipo</td>
				<td>Mon</td>
				<td>T.C</td>
				<td>Total</td>
				<td>Disponible</td>		
			</tr>
		</thead>
		<tbody>
			<g:each in="${CXCNotaInstanceList}" var="row">
				<tr>
					<td><g:link action="show" id="${row.id}">
						${fieldValue(bean: row, field: "id")}
						</g:link>
					</td>
					<td><g:link action="edit" id="${row.id}">
						${fieldValue(bean: row, field: "cliente.nombre")}
						</g:link>
					</td>
					<td><lx:shortDate date="${row.fecha }"/></td>
					<td>${fieldValue(bean: row, field: "tipo")}</td>
					<td>${fieldValue(bean: row, field: "moneda")}</td>
					<td>${fieldValue(bean: row, field: "tc")}</td>
					<td><lx:moneyFormat number="${row.total }"/></td>
					<td><lx:moneyFormat number="${row.disponible }"/></td>	
				
				</tr>
			</g:each>
		</tbody>
	</table>
</content>
<content tag="searchService">
	<g:createLink action="search"/>
</content>
 	
 	
</body>
</html>
