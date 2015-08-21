<!doctype html>
<html>
<head>
	<meta name="layout" content="operaciones">
	<title>Notas de Crédito</title>
</head>
<body>
 <content tag="header">
 	Notas de Crédito
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
 	        <g:link action="index" params="[tipo:'APLICADOS']">
 	    		<g:if test="${tipo=='APLICADOS'}">
 	    			<strong>Aplicadas</strong>
 	    		</g:if>
 	    		<g:else>
 	    			Aplicadas
 	    		</g:else>
 	    	</g:link>
 	    </li>
 	    <li >
 	    	<g:link action="index" params="[tipo:'TODOS']">
 	    		<g:if test="${tipo=='TODOS'}">
 	    			<strong>Todas</strong>
 	    		</g:if>
 	    		<g:else>
 	    			Todos
 	    		</g:else>
 	    	</g:link>
 	    </li>
 	</ol>
 </content>
 <content tag="periodo">
 	Periodo:${session.periodo.mothLabel()} 
 </content>
 <content tag="operaciones">
 </content>

 <content tag="grid">
 	<table id="grid" class="table table-striped table-hover table-bordered table-condensed">
 		<thead>
 			<tr>
 				<td>Id</td>
 				<td>Proveedor</td>
 				<td>Fecha</td>
 				<td>Mon</td>
 				<td>Documento</td>
 				<td>Concepto</td>
 				<td>Total</td>
 				<td>Aplicado</td>
 				<td>Disponible</td>		
 			</tr>
 		</thead>
 		<tbody>
 			<g:each in="${notaDeCreditoInstanceList}" var="row">
 				<tr>
 					<td><g:link action="edit" id="${row.id}">
 						${fieldValue(bean: row, field: "id")}
 						</g:link>
 					</td>
 					<td>${fieldValue(bean: row, field: "proveedor.nombre")}</td>
 					<td><lx:shortDate date="${row.fecha }"/></td>
 					<td>${fieldValue(bean: row, field: "moneda")}</td>
 					<td>${fieldValue(bean: row, field: "documento")}</td>
 					<td>${fieldValue(bean: row, field: "concepto")}</td>
 					<td><lx:moneyFormat number="${row.total }"/></td>
 					<td><lx:moneyFormat number="${row.aplicado }"/></td>
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
