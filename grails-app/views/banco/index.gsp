<!doctype html>
<html>
<head>
	<title>Bancos</title>
	<meta name="layout" content="luxor">
</head>
<body>

<content tag="header">
	Catálogo de bancos
</content>
	
<content tag="subHeader">
	<ol class="breadcrumb">
		<li class="active">
			<g:link controller="bancoSat">SAT</g:link>
		</li>
		<li>
			<g:link action="list">
				<strong>Bancos </strong>
			</g:link>
		</li>
		<li>
			<g:link controller="bancoSat">Cuentas</g:link>
		</li>
	</ol>
</content>

<content tag="document">
	<div class="row">
	    <div class="col-lg-8">
	        <div class="ibox float-e-margins">
	            <lx:iboxTitle></lx:iboxTitle>
	            <div class="ibox-content">
	            	<table id="grid" class="grid table table-responsive">
	            		<thead>
	            			<tr>
	            				<th>Nombre</th>
	            				<th>Banco SAT</th>
	            				<th>Nacional</th>
	            			</tr>
	            		</thead>
	            		<tbody>
	            			<g:each in="${bancoInstanceList}" var="row">
	            			<tr id="${row.id}">	
	            				<td>
	            					<g:link action="show" id="${row.id}">${row.nombre}</g:link>
	            				</td>
	            				<td>${row.bancoSat}</td>
	            				<td ><g:checkBox name="nacional" value="${row.nacional}" disabled = "disabled"/></td>
	            			</tr>
	            			</g:each> 
	            		</tbody>
	            	</table>
	            </div>
	        </div>
	    </div>
	</div>
	
</content>
	
</body>
</html>
