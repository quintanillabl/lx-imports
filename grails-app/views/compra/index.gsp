
<%@ page import="com.luxsoft.impapx.Compra" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<g:set var="entityName" value="${message(code: 'compra.label', default: 'Compra')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
	<asset:stylesheet src="datatables/dataTables.css"/>
	<asset:javascript src="datatables/dataTables.js"/> 
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
					<h3>
						<g:message code="compra.list.label" 
							default='Catálogo de Compra' />
					</h3>
					<g:if test="${flash.message}">
						<span class="label label-warning">${flash.message}</span>
					</g:if>
				</div>
			</div>
		</div><!-- end .row -->

		<div class="row toolbar-panel">
			<div class="col-md-3">
				<input type='text' id="filtro" placeholder="Filtrar" class="form-control" autofocus="on">
			</div>
    		<div class="col-md-4">
    			<g:form class="form-horizontal" action="show">
    				<g:hiddenField name="id" />
    	      		<div class="input-group">
    	      		    <input id="compraField" name="compraDesc" type="text" 
    			    	    class="form-control " placeholder="Buscar compra">
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
	            %{-- <lx:searchButton/> --}%
	            <filterpane:filterButton text="Filtrar" />
		    </div>
		</div>

		<div class="row">
			<div class="col-md-12">
				<table id="grid" class="table table-striped table-bordered table-condensed luxor-grid" width="100%">
				<thead>
						<tr>
							<th><g:message code="compra.proveedor.label" default="Proveedor" /></th>
							<g:sortableColumn property="fecha" title="${message(code: 'compra.fecha.label', default: 'Fecha')}" />
						
							<g:sortableColumn property="entrega" title="${message(code: 'compra.entrega.label', default: 'Entrega')}" />
						
							<g:sortableColumn property="depuracion" title="${message(code: 'compra.depuracion.label', default: 'Depuracion')}" />
						
							<g:sortableColumn property="comentario" title="${message(code: 'compra.comentario.label', default: 'Comentario')}" />
						
							<g:sortableColumn property="moneda" title="${message(code: 'compra.moneda.label', default: 'Moneda')}" />
						
						</tr>
					</thead>
					<tbody>
					<g:each in="${compraInstanceList}" status="i" var="compraInstance">
						<tr>
						
							<td><g:link action="show" id="${compraInstance.id}">${fieldValue(bean: compraInstance, field: "proveedor")}</g:link></td>
						
							<td><g:formatDate date="${compraInstance.fecha}" /></td>
							<td><g:formatDate date="${compraInstance.entrega}" /></td>
							<td><g:formatDate date="${compraInstance.depuracion}" /></td>
							<td>${fieldValue(bean: compraInstance, field: "comentario")}</td>
							<td>${fieldValue(bean: compraInstance, field: "moneda")}</td>
						
						</tr>
					</g:each>
					</tbody>
				</table>
				<div class="pagination">
					<g:paginate total="${compraInstanceCount ?: 0}" />
				</div>
			</div>
		</div> <!-- end .row 2 -->
		<filterpane:filterPane 
			domain="com.luxsoft.impapx.Compra" dialog="true"
			dateFormat="${[fecha: 'dd/MM/yyyy', entrega: 'dd/MM/yyyy']}"
			excludeProperties="dateCreated,lastUpdated,descuentos,subtotal,impuestos,importe,origen"
			filterPropertyValues="${[fecha:[years:2013..2019,precision:'day',default:new Date()-30]]}"/>
	</div>
	<script type="text/javascript">
		$(function(){
			$("#compraField").autocomplete({
				source:'<g:createLink action="comprasAsJSONList"/>',
				minLength:3,
				select:function(e,ui){
					console.log('Valor seleccionado: '+ui.item.id);
					$("#compraField").val(ui.item.id);
					$("#id").val(ui.item.id);
					var button=$("#buscarBtn");
	    			button.removeAttr('disabled');
				}
			});

		});
	</script>
</body>
</html>
