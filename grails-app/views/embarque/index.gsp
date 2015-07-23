
<%@ page import="com.luxsoft.impapx.Embarque" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<g:set var="entityName" value="${message(code: 'embarque.label', default: 'Embarque')}" />
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
						<g:message code="embarque.list.label" 
							default='Catálogo de Embarque' />
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
    	      		    <input id="embarqueField" name="embarqueDesc" type="text" 
    			    	    class="form-control " placeholder="Buscar embarque">
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
			<div class="col-md-12" style="overflow: auto;">
				<table id="grid" class="table table-striped table-bordered table-condensed  luxor-grid small-grid">
				<thead>
						<th>Id .</th>
						<th>BL</th>
						<th>Nombre/Prov/Aduana</th>
						%{-- <th>Proveedor</th>
						<th>Aduana</th> --}%
						<th>Cont</th>
						<th>F Emb</th>
						
						
						<th>Ingreso A</th>
						<th>C.Gastos</th>
						<th>Valor</th>
						<th>Facturado</th>
						<th>Pendiente</th>
						<th>Cont</th>
					</thead>
					<tbody>
					<g:each in="${embarqueInstanceList}" status="i" var="embarqueInstance">
						<tr class="${embarqueInstance.cuentaDeGastos ?'':'error' }">
							<td>
								<g:link action="edit" id="${embarqueInstance.id}">
								${fieldValue(bean: embarqueInstance, field: "id")}</g:link>
							</td>
							<td   width="50px" style="with:50px;white-space: nowrap;overflow: hidden;text-overflow:ellipsis;">
								<g:link action="edit" id="${embarqueInstance.id}">
								${fieldValue(bean: embarqueInstance, field: "bl")}</g:link>
							</td>
							<td   width="100px" style="white-space: nowrap;overflow: hidden;text-overflow:ellipsis;">
								${fieldValue(bean: embarqueInstance, field: "nombre")} /${fieldValue(bean: embarqueInstance, field: "proveedor.nombre")}/ ${fieldValue(bean: embarqueInstance, field: "aduana.nombre")}
							</td>
							%{-- <td   width="100px" style="white-space: nowrap;overflow: hidden;text-overflow:ellipsis;">
								${fieldValue(bean: embarqueInstance, field: "proveedor.nombre")}
							</td>
							<td   width="100px" style="white-space: nowrap;overflow: hidden;text-overflow:ellipsis;">
								${fieldValue(bean: embarqueInstance, field: "aduana.nombre")}
							</td> --}%
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
						</tr>
					</g:each>
					</tbody>
				</table>
				<div class="pagination">
					<g:paginate total="${embarqueInstanceCount ?: 0}" />
				</div>
			</div>
		</div> <!-- end .row 2 -->
		<filterpane:filterPane 
			domain="com.luxsoft.impapx.Embarque" dialog="true"
			dateFormat="${[fecha: 'dd/MM/yyyy']}"
			excludeProperties="dateCreated,lastUpdated"
			filterPropertyValues="${[fecha:[years:2013..2019,precision:'day',default:new Date()-30]]}"/>
	</div>
	<script type="text/javascript">
		$(function(){
			$("#embarqueField").autocomplete({
				source:'<g:createLink action="embarquesAsJSONList"/>',
				minLength:3,
				select:function(e,ui){
					console.log('Valor seleccionado: '+ui.item.id);
					$("#embarqueField").val(ui.item.id);
					$("#id").val(ui.item.id);
					var button=$("#buscarBtn");
	    			button.removeAttr('disabled');
				}
			});

		});
	</script>
</body>
</html>
