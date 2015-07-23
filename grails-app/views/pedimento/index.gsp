<%@ page import="com.luxsoft.impapx.Pedimento"%>
<!doctype html>
<html>
<head>
	<g:set var="entityName" value="${message(code: 'pedimento.label', default: 'Pedimento')}" />
	<title><g:message code="pedimento.list.label" default='Pedimentos de importación' /></title>
	<asset:stylesheet src="jquery-ui.css"/>
	<asset:javascript src="jquery-ui/autocomplete.js"/>
</head>
<body>
	<div class="container">
		<div class="row">

			<div class="col-md-12">
				<div class="alert alert-info">
					<h3>Pedimentos registrados</h3>
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
    	      		    <input id="pedimentoField" name="embarqueDesc" type="text" 
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
	            <filterpane:filterButton text="Filtrar" />
		    </div>
		</div>

		<div class="row">
			<div class="col-md-12">
				<table id="grid" class="table table-striped table-hover table-bordered table-condensed ">
					<thead>
						<tr>
							<th>Folio</th>
							<g:sortableColumn property="pedimento"
								title="${message(code: 'pedimento.pedimento.label', default: 'Pedimento')}" />
							<g:sortableColumn property="proveedor.nombre"
								title="Agente" />
							<g:sortableColumn property="fecha"
								title="${message(code: 'pedimento.fecha.label', default: 'Fecha')}" />
							<g:sortableColumn property="dta"
								title="${message(code: 'pedimento.dta.label', default: 'Dta')}" />
							<g:sortableColumn property="prevalidacion"
								title="${message(code: 'pedimento.prevalidacion.label', default: 'Prevalidación')}" />
							<g:sortableColumn property="tipoDeCambio"
								title="${message(code: 'pedimento.tipoDeCambio.label', default: 'T.C.')}" />
							<g:sortableColumn property="impuesto"
								title="${message(code: 'pedimento.impuesto.label', default: 'Impuesto')}" />
							<td>Incrementables</td>

						</tr>
					</thead>
					<tbody>
						<g:each in="${pedimentoInstanceList}" var="pedimentoInstance">
							<tr>
								<td><g:link action="edit" id="${pedimentoInstance.id}">
										<lx:idFormat id="${pedimentoInstance.id}"/>
									</g:link>
								</td>
								<td><g:link action="edit" id="${pedimentoInstance.id}">
										${fieldValue(bean: pedimentoInstance, field: "pedimento")}
									</g:link>
								</td>
								<td>${fieldValue(bean:pedimentoInstance,field:"proveedor.nombre") }</td>
								<td>
									<lx:shortDate date="${pedimentoInstance.fecha}"/>
								</td>
								<td>
									<lx:moneyFormat number="${pedimentoInstance.dta}"/>
								</td>
								<td>
									<lx:moneyFormat number="${pedimentoInstance.prevalidacion}"/>
								</td>

								<td>
									<g:formatNumber number="${pedimentoInstance.tipoDeCambio}" format="###.####"/>
								</td>

								<td>
									<lx:moneyFormat number="${pedimentoInstance.impuesto}"/>
								</td>
								<td>
									<lx:moneyFormat number="${pedimentoInstance.incrementables}"/>
								</td>

							</tr>
						</g:each>
					</tbody>
				</table>
				<div class="pagination">
					<g:paginate total="${pedimentoInstanceCount ?: 0}" />
				</div>
			</div>
		</div>
		<filterpane:filterPane 
			domain="com.luxsoft.impapx.Pedimento" dialog="true"
			dateFormat="${[fecha: 'dd/MM/yyyy']}"
			excludeProperties="dateCreated,lastUpdated"
			filterPropertyValues="${[fecha:[years:2013..2019,precision:'day',default:new Date()-30]]}"/>
	</div>
	<script type="text/javascript">
		$(function(){
			// $('#grid').dataTable( {
		    	
		 //    	aLengthMenu: [[20, 40, 60, 100, -1], [20, 40, 60, 100, "Todos"]],
			// 	iDisplayLength: 20,
			// 	"autoWidth": false
			// } );
			$("#pedimentoField").autocomplete({
				source:'<g:createLink action="pedimentosAsJSONList"/>',
				minLength:3,
				select:function(e,ui){
					console.log('Valor seleccionado: '+ui.item.id);
					$("#pedimentoField").val(ui.item.id);
					$("#id").val(ui.item.id);
					var button=$("#buscarBtn");
	    			button.removeAttr('disabled');
				}
			});
		})
	</script>
</body>
</html>
