<%@ page import="com.luxsoft.impapx.tesoreria.MovimientoDeCuenta" %>
<!doctype html>
<html>
<head>
	<title>Bancos</title>
	<meta name="layout" content="tesoreria">
</head>
<body>

<content tag="header">
	Movimientos de cuenta 
</content>
	
<content tag="subHeader">
	<ol class="breadcrumb">
		<li>
			<g:link  params="[tipo:'TODOS']" >
				<g:if test="${tipo=='TODOS'}">
					<strong>Todos</strong>
				</g:if>
				<g:else>
					Todos
				</g:else>
				
			</g:link>
		</li>
		<li>
			<g:link  params="[tipo:'DEPOSITOS']">
				<g:if test="${tipo=='DEPOSITOS'}">
					<strong>Depositos</strong>
				</g:if>
				<g:else>
					Depositos
				</g:else>
			</g:link>
		</li>
		<li>
			<g:link  params="[tipo:'RETIROS']">
				<g:if test="${tipo=='RETIROS'}">
					<strong>Retiros</strong>
				</g:if>
				<g:else>
					Retiros
				</g:else>
			</g:link>
		</li>
	</ol>
</content>

<content tag="operaciones">
	<li>
		<g:link action="depositar" >
				<i class="fa fa-plus"></i> Depositar
		</g:link>
	</li>
	<li>
		<g:link action="retirar" >
				<i class="fa fa-minus"></i>  Retirar
		</g:link>
	</li>
</content>

<content tag="grid">
	<table id="grid" class="grid table table-responsive table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th>Id</th>
				<th>Banco</th>
				<th >Num</th>
				<th >Mon</th>
				<th >T.C.</th>
				<th>Fecha</th>
				<th>Concepto</th>
				<th >Importe</th>
				<th>Origen</th>
				<th >Comentario</th>
				<th >Ref</th>
			</tr>
		</thead>
		<tbody>
			<g:each in="${movimientoDeCuentaInstanceList}" var="row">
			<tr id="${row.id}">	
				<lx:idTableRow id="${row.id}"/>
				<td>${fieldValue(bean: row, field: "cuenta.nombre")}</td>
				<td>${fieldValue(bean: row, field: "cuenta.numero")}</td>
				<td>${fieldValue(bean: row, field: "moneda")}</td>
				<td>${fieldValue(bean: row, field: "tc")}</td>
				<td><lx:shortDate date="${row.fecha }"/></td>
				<td>${fieldValue(bean: row, field: "concepto")}</td>
				<lx:moneyTableRow number="${row.importe}"/>
				<td>${fieldValue(bean: row, field: "origen")}</td>
				<td>${fieldValue(bean: row, field: "comentario")}</td>
				<td>${fieldValue(bean: row, field: "referenciaBancaria")}</td>
			</tr>
			</g:each> 
		</tbody>
	</table>
</content>

<content tag="searchService"> search</content>


	
	
	
	
</body>
</html>
