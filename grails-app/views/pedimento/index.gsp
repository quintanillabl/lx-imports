<%@ page import="com.luxsoft.impapx.Pedimento"%>
<!doctype html>
<html>
<head>
	<g:set var="entityName" value="${message(code: 'pedimento.label', default: 'Pedimento')}" />
	<title><g:message code="pedimento.list.label" default='Pedimentos de importación' /></title>
	<meta name="layout" content="operaciones">
</head>
<body>

<content tag="header">
	Pedimentos registrados
</content>
<content tag="periodo">
	Periodo: ${session.periodo.mothLabel()}
</content>
<content tag="grid">
	<table id="grid" class="table table-striped table-hover table-bordered table-condensed ">
		<thead>
			<tr>
				<th>Id</th>
				<th>Pedimento</th>
				<th>Agencia</th>
				<th>Fecha</th>
				<th>DTA</th>
				<th>Prevalidación</th>
				<th>T.C.</th>
				<th>Impuesto</th>
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
</content>
<content tag="searchService">
	<g:createLink action="search"/>
</content>
		
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
