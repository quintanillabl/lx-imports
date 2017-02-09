
<%@ page import="com.luxsoft.impapx.tesoreria.Banco" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="layout" content="luxor">
		<title>Banco ${bancoInstance.nombre}</title>
	</head>

	<body>
		<content tag="header">${bancoInstance.nombre}</content>
		<content tag="subHeader"></content>

		<content tag="document">
			<div class="row">
			    <div class="col-lg-7">
			        <div class="ibox float-e-margins">
			            <lx:iboxTitle/>
			            <div class="ibox-content">
			            	<lx:backButton/>
			            	<lx:createButton/>
			            	<lx:editButton id="${bancoInstance?.id}"/>
			            	<lx:printButton/>
			            	<g:form class="form-horizontal">
			            		<f:display property="nombre" bean="bancoInstance"/>
			            		<f:display property="nacional" bean="bancoInstance"/>
			            		<f:display property="bancoSat" bean="bancoInstance"/>
			            		
			            	</g:form>
			            </div>
			        </div>
			    </div>
			    <div class="col-lg-5">
			        <div class="ibox float-e-margins">
			            <lx:iboxTitle title="Cuentas"/>
			            <div class="ibox-content">
			            	<table id="grid" class="grid table table-responsive">
			            		<thead>
			            			<tr>
			            				<th>No</th>
			            				<th>Tipo</th>
			            				<th>Nombre</th>
			            			</tr>
			            		</thead>
			            		<tbody>
			            			<g:each in="${bancoInstance.cuentas}" var="row">
			            			<tr id="${row.id}">	
			            				<td>${row.numero}</td>
			            				<td>${row.tipo}</td>
			            				<td>${row.nombre}</td>
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


