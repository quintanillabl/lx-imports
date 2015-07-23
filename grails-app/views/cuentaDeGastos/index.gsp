<%@ page import="com.luxsoft.impapx.CuentaDeGastos" %>
<!doctype html>
<html>
<head>
	<g:set var="entityName"
		value="${message(code: 'cuentaDeGastos.label', default: 'CuentaDeGastos')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
	<asset:javascript src="fp.js"/>
	<asset:stylesheet src="fp.css"/>
	<asset:stylesheet src="jquery-ui.css"/>
	<asset:javascript src="jquery-ui/autocomplete.js"/>
	
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<div class="alert alert-info">
					<h3>Cuentas de gastos</h3>
					<g:if test="${flash.message}">
						<span class="label label-warning">${flash.message}</span>
					</g:if>
				</div>
			</div>
		</div><!-- end .row -->
		<div class="row toolbar-panel">
			<div class="col-md-6">
				<g:form class="form-horizontal" action="edit">
					<g:hiddenField name="id" />
		      		<div class="input-group">
		      		    <input id="searchField" name="searchDesc" type="text" 
				    	    class="form-control " placeholder="Buscar entidad"  autofocus="on">
		  		    	<span class="input-group-btn">
				       		<button id="buscarBtn" type="submit" class="btn btn-default" disabled="disabled">
								<i class="fa fa-search"></i></span>
							</button> 
		  		      	</span>
		      		</div>
		  		</g:form>
			</div>
		    <div class="btn-group">
		    	<lx:refreshButton/>
		        <lx:printButton/>
		        <lx:createButton/>
		        <filterpane:filterButton text="Filtrar" />
		    </div>
		</div>

		<div class="row">
			<div class="col-md-12">
				<table id="grid" class="table table-striped table-hover table-bordered table-condensed">
					<thead>
						<tr>
							<th>Folio</th>
							<th>Embarque</th>
							<th>BL</th>
							<th>Agente</th>
							<th>Referencia</th>
							<g:sortableColumn property="fecha" title="${message(code: 'cuentaDeGastos.fecha.label', default: 'Fecha')}" />
							<th><g:message code="cuentaDeGastos.total" default="Total"/></th>
							<th><g:message code="cuentaDeGastos.comentario" default="Comentario"/></th>
							
						</tr>
					</thead>
					<tbody>
						<g:each in="${cuentaDeGastosInstanceList}" var="cuentaDeGastosInstance">
							<tr>
								<td>${fieldValue(bean: cuentaDeGastosInstance, field: "id")}</td>
								<td><g:link action="edit" id="${cuentaDeGastosInstance.id}">
									${fieldValue(bean: cuentaDeGastosInstance, field: "embarque.id")}
									</g:link>
								</td>
								<td><g:link action="edit" id="${cuentaDeGastosInstance.id}">
									${fieldValue(bean: cuentaDeGastosInstance, field: "embarque.bl")}
									</g:link>
								</td>
								<td>${fieldValue(bean: cuentaDeGastosInstance, field: "proveedor.nombre")}</td>
								<td>${fieldValue(bean: cuentaDeGastosInstance, field: "referencia")}</td>
								<td><lx:shortDate date="${cuentaDeGastosInstance.fecha}" /></td>
								<td><lx:moneyFormat number="${cuentaDeGastosInstance.total }"/></td>
								<td><g:link action="edit" id="${cuentaDeGastosInstance.id}">${fieldValue(bean: cuentaDeGastosInstance, field: "comentario")}</g:link></td>
								
							</tr>
						</g:each>
					</tbody>
				</table>
				<div class="pagination">
					<g:paginate total="${cuentaDeGastosInstanceCount ?: 0}" />
				</div>
			</div>

		</div>
		<filterpane:filterPane 
			domain="com.luxsoft.impapx.CuentaDeGastos" dialog="true"
			dateFormat="${[fecha: 'dd/MM/yyyy']}"
			filterPropertyValues="${[fecha:[years:2013..2019,precision:'day',default:new Date()-30]]}"/>
		%{-- <filterpane:filterPane 
			domain="com.luxsoft.impapx.CuentaDeGastos" dialog="true"
			dateFormat="${[fecha: 'dd/MM/yyyy']}"
			filterProperties="fecha,proveedor,referencia"
			associatedProperties="proveedor.nombre"
			filterPropertyValues="${[fecha:[years:2013..2019,precision:'day',default:new Date()-30]]}"/> --}%
	</div>
	<script type="text/javascript">
		$(function(){
			$("#searchField").autocomplete({
				source:'<g:createLink action="cuentasAsJSONList"/>',
				minLength:3,
				select:function(e,ui){
					console.log('Valor seleccionado: '+ui.item.id);
					$("#searchField").val(ui.item.id);
					$("#id").val(ui.item.id);
					var button=$("#buscarBtn");
	    			button.removeAttr('disabled');
				}
			});

		});
	</script>
</body>
</html>
