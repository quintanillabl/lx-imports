
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
				<tr class="${embarqueInstance.cuentaDeGastos ?'':'error' }">
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

<content tag="searchService">
	<g:createLink action="search"/>
</content>

%{-- <content tag="document">
	<script type="text/javascript">
		$(function(){
 			$('#grid').dataTable({
                responsive: true,
                "language": {
					"url": "${assetPath(src: 'datatables/dataTables.spanish.txt')}"
	    		},
	    		"dom": 'T<"clear">lfrtip',
	    		"tableTools": {
	    		    "sSwfPath": "${assetPath(src: 'plugins/dataTables/swf/copy_csv_xls_pdf.swf')}"
	    		},
	    		"order": []
            });
	    	$("#filtro").on('keyup',function(e){
	    		var term=$(this).val();
	    		$('#grid').DataTable().search(
					$(this).val()
	    		        
	    		).draw();
	    	});
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
</content> --}%
</body>
</html>