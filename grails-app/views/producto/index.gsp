
<%@ page import="com.luxsoft.impapx.Producto" %>
<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="catalogos">
	<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>

<content tag="header">
	Catálogo de productos
</content>

<content tag="periodo">
	Periodo:${session.periodo.mothLabel()} 
</content>

<content tag="operaciones">
	<li>
	    <g:link action="importarProductos" controller="importador" 
				onclick="return confirm('Importar productos de SiipapEx');">
			<i class="fa fa-download"></i> Importar 
		</g:link>
	</li>
	<li>
		<a href="" data-target="#importDialog" data-toggle="modal">
			<i class="fa fa-download"></i> Importar por clave
		</a>
	</li>
</content>

<content tag="grid">
	<table id="grid" class="table table-striped table-bordered table-condensed luxor-grid" 
		>
		<thead>
			<tr>
				<th><g:message code="producto.descripcion.label" default="Clave"/></th>
				<th><g:message code="producto.descripcion.label" default="Descripción"/></th>
				<th><g:message code="producto.unidad.label" default="U"/></th>
				<th><g:message code="producto.kilos.label" default="K" /></th>
				<th><g:message code="producto.gramos.label" default="g" /></th>
				<th><g:message code="producto.m2.label" default="M2" /></th>
				<th><g:message code="producto.linea.label" default="Linea" /></th>
				<th><g:message code="producto.marca.label" default="Marca" /></th>
				<th><g:message code="producto.clase.label" default="Clase" /></th>
				<th><g:message code="producto.clase.lastUpdated" default="Modificado" /></th>
			</tr>
		</thead>
		<tbody>
			<g:each in="${productoInstanceList}" var="productoInstance">
			<tr>		
				<td><g:link action="show" id="${productoInstance.id}">
					${fieldValue(bean: productoInstance, field: "clave")}</g:link>
				</td>
				<td style="white-space: nowrap;width: 50%; overflow: hidden; text-overflow:ellipsis;">
					${fieldValue(bean: productoInstance, field: "descripcion")}</td>
				<td>${fieldValue(bean: productoInstance, field: "unidad.clave")}</td>
				<td>${fieldValue(bean: productoInstance, field: "kilos")}</td>
				<td>${fieldValue(bean: productoInstance, field: "gramos")}</td>
				<td>${fieldValue(bean: productoInstance, field: "m2")}</td>
				<td>${fieldValue(bean: productoInstance, field: "linea")}</td>
				<td>${fieldValue(bean: productoInstance, field: "marca")}</td>
				<td>${fieldValue(bean: productoInstance, field: "clase")}</td>
				<td style="white-space: nowrap; width: 10px; overflow: hidden; text-overflow:clip;">
					<g:formatDate date="${productoInstance.lastUpdated}" format="dd/MM/yyyy HH:mm"/>
				</td>
			</tr>
			</g:each>
		</tbody>
	</table>

	<g:render template="search"/>
	<g:render template="import"/>
</content>

<content tag="searchService">
	<g:createLink action="search"/>
</content>

	<div class="container">
		
		<div class="row">
			<div class="col-md-12">
				<div class="alert alert-info">
					<h3>
						<g:message code="producto.list.label" 
							default='Catálogo de Producto' />
					</h3>
					<g:if test="${flash.message}">
						<span class="label label-warning">${flash.message}</span>
					</g:if>
				</div>
			</div>
		</div><!-- end .row -->

		<div class="row toolbar-panel">
		    <div class="btn-group">
	        	
	            
	            
		    </div>
		    <div class="col-md-2">
		    	<input type='text' id="filtro" placeholder="Filtrar" class="form-control" autofocus="on">
		    </div>
		    
    		<div class="col-md-4">
    			<g:form class="form-horizontal" action="show">
    				<g:hiddenField name="id" />
    	      		<div class="input-group">
    	      		    <input id="productoField" name="producto" type="text" 
    			    	    class="form-control " placeholder="Producto">
          		    	<span class="input-group-btn">
				       		<button id="buscarBtn" type="submit" class="btn btn-default" disabled="disabled">
								<i class="fa fa-search"></i></span>
							</button> 
          		      	</span>
    	      		</div><!-- /input-group -->
          		</g:form>
    		</div>	<!-- end .col-md-6-->
		</div>

		<div class="row">
			<div class="col-md-12">
				
				<div class="pagination">
					<g:paginate total="${productoInstanceCount ?: 0}" />
				</div>
			</div>
		</div> <!-- end .row 2 -->
		
	</div>
	<script type="text/javascript">
		$(function(){
			$("#productoField").autocomplete({
				source:'<g:createLink action="productosJSONList"/>',
				minLength:3,
				select:function(e,ui){
					console.log('Valor seleccionado: '+ui.item.id);
					$("#nombreField").val(ui.item.id);
					$("#id").val(ui.item.id);
					var button=$("#buscarBtn");
	    			button.removeAttr('disabled');
				}
			});

		});
	</script>
</body>
</html>
