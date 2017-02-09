
<%@ page import="com.luxsoft.impapx.Embarque" %>
<!DOCTYPE html>
<html>
<head>
	<title>Lista de embarques</title>
	<meta name="layout" content="operaciones">
</head>
<body>

<content tag="header">
	Registro de embarques 
</content>
<content tag="periodo">
	Periodo: ${session.periodoEmbarques.mothLabel()}
</content>


<content tag="grid">
	<table id="grid" class="table table-striped table-bordered table-condensed ">
		<thead>
			<th>Id</th>
			<th>BL</th>
			<th>Nombre</th>
			<th>Proveedor</th>
			<th>Aduana</th>
			<th>Cont</th>
			<th>F Emb</th>
			<th>Ingreso A</th>
			<th>C.Gastos</th>
			<th>Valor</th>
			<th>Facturado</th>
			<th>Pendiente</th>
			<th>Imp</th>
			<th>Alta</th>
			<th>Modificado</th>
		</thead>
		<tbody>
			<g:each in="${embarqueInstanceList}" status="i" var="embarqueInstance">
				<tr class="${embarqueInstance.cuentaDeGastos ?'':'danger' }">
					<td>
						<g:link action="edit" id="${embarqueInstance.id}">
							${fieldValue(bean: embarqueInstance, field: "id")}
						</g:link>
					</td>
					<td>
						<g:link action="edit" id="${embarqueInstance.id}">
							${fieldValue(bean: embarqueInstance, field: "bl")}
						</g:link>
					</td>
					<td>${fieldValue(bean: embarqueInstance, field: "nombre")} </td>
					<td>${fieldValue(bean: embarqueInstance, field: "proveedor.nombre")}</td>
					<td>${fieldValue(bean: embarqueInstance, field: "aduana.nombre")}</td>
					<td><g:formatNumber number="${embarqueInstance.contenedores }" format="###"/>
					<td><g:formatDate date="${embarqueInstance.fechaEmbarque}" format="dd/MM/yyyy"/></td>
					
					<td><g:formatDate date="${embarqueInstance.ingresoAduana}" format="dd/MM/yyyy" /></td>
					<td>${fieldValue(bean: embarqueInstance, field: "cuentaDeGastos")}</td>
					<td><lx:moneyFormat number="${embarqueInstance.valor}"/></td>
					<td><lx:moneyFormat number="${embarqueInstance.facturado}"/></td>
					<td><lx:moneyFormat number="${embarqueInstance.porFacturar()}"/></td>
					<td>
						<g:link action="print" 
							id="${embarqueInstance.id}" target="_blank"><i class="fa fa-print"></i>
						</g:link>
					</td>
					<td><g:formatDate date="${embarqueInstance.dateCreated}" format="dd/MM/yyyy" /></td>
					<td><g:formatDate date="${embarqueInstance.lastUpdated}" format="dd/MM/yyyy" /></td>
				</tr>
			</g:each>
		</tbody>
	</table>
</content>

<content tag="periodoDialog">
	<g:render template="/common/selectorDePeriodo" model="[periodo:session.periodoEmbarques,controller:'embarque']"/>
</content>

<content tag="searchService">
	<g:createLink action="search"/>
</content>


</body>
</html>