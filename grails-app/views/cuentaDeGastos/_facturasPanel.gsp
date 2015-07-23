<div class="btn-group">
	<button id="#asignarFacturaBtn" class="btn btn-primary"
		data-target="#asignarFacturaDialog" data-toggle="modal">
		<i class="icon-plus icon-white"></i>
		<g:message code="default.button.add.label" default="Agregar" />
	</button>

	<button id="elimiarBtn" class="btn btn-danger" >
		<i class="icon-trash icon-white"></i>
		<g:message code="default.button.delete.label" default="Delete" />
	</button>
	
	
	<button class="btn btn-default" id="refreshBtn">
		<i class="fa fa-refresh"></i> Refrescar
	</button>
</div>

<div>
	<table id="facturasGrid" class="simpleGrid table table-striped table-bordered table-condensed" >
		<thead>
			<tr>
				<th>Id</th>
				<th>Documento</th>
				<th>Fecha</th>
				<th>Proveedor</th>
				<th>Importe</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
		<tfoot>
			<tr>
				<th></th>
				<th></th>
				<th></th>
				<th class="rightAlign">Importe:  </th>
				<th><label id="totalGastos"><lx:moneyFormat number="${cuentaDeGastosInstance.importe}"/></label></th>
			</tr>
		</tfoot>
	</table>
</div>

<%-- Forma para la asignacion de facturas --%>
<div class="modal fade" id="asignarFacturaDialog" tabindex="-1" >
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="myModalLabel">Asignacion de facturas</h4>
			</div>
			<div class="modal-body ui-front">
				<g:formRemote name="agregarFactura" class="form-horizontal"
					url="[controller:'cuentaDeGastos',action:'agregarFactura']"
					onSuccess=" reload(data)"
					onFailure="alert('Error');"
					onComplete="clearForm();">
					<g:hiddenField name="cuentaDeGastosId" value="${cuentaDeGastosInstance?.id}" />
					<g:hiddenField name="facturaId" />
					<input id="factura" class="form-control" type="text" name="factura"
						value="" placeholder="Seleccione una factura">

					<div class="modal-footer">
						<a href="#" class="btn btn-default" data-dismiss="modal">Cancelar</a>
						<button type="submit" class="btn btn-primary">Asignar</button>
					</div>
				</g:formRemote>
			</div>
		</div>
	</div>
</div>
<%-- Fin de forma para la asignacion de factura --%>



<script>

	function clearForm(){
		$("#factura").val("");
		$("#facturaId").val("");
		$("#asignarFacturaDialog").modal('hide');
	}
	
$(function(){
	$(".simpleGrid tbody").on('click','tr',function(){
		$(this).toggleClass("success selected");
	});

	

	$('#asignarFacturaDialog').on('show', function(){
		$("#factura").val("");
		$("#facturaId").val("");
	});

	$("#factura").autocomplete(
		{
			source:'${createLink(controller:'cuentaDeGastos',action:'facturasDisponiblesPorAsignarJSON') }',
			minLength:2,
			select:function(e,ui){
		   		$("#facturaId").val(ui.item.id);
			}
	});
			
	$("#elimiarBtn").click(function(e){
		eliminarFacturas();
	});
		
	function eliminarFacturas(){
		var res=selectedRows();
		if(res.length==0){
			alert('Debe seleccionar al menos un registro');
			return;
		}
		var ok=confirm('Cancelar asignación de ' + res.length+' factura(s)?');
		if(!ok)
			return;
		console.log('Cancelando facturas: '+res);
		$.ajax({
			url:"${createLink(controller:'cuentaDeGastos',action:'eliminarFacturas')}",
			type:'POST',
			data:{
				cuentaDeGastosId:${cuentaDeGastosInstance?.id},partidas:JSON.stringify(res)
			},
			success:function(response){
				console.log('Response: '+response)
				reload(null);
				
			},
			error:function(request,status,error){
				alert("Error: "+status);
			}
		});
	}
		
	function selectedRows(){
			var res=[];
			var data=$("tr.selected").each(function(){
				var tr=$(this);
				res.push(tr.attr("id"));
			});
			return res;
	}
		
	var oTable=$("#facturasGrid").dataTable({
    	"language": {
			"url": "${assetPath(src: 'datatables/dataTables.spanish.txt')}"
    	},
    	bProcessing: true,
		bServerSide:false,
		"fnServerParams":function(aoData){
			aoData.push({"name":"cuentaDeGastosId","value":"${cuentaDeGastosInstance.id}"})
		},
		sAjaxSource: '${createLink(controller:'cuentaDeGastos',action:'facturasAsJSON')}', 
		"aoColumns":[
			{"sName": "id", "sTitle": "Folio", "sWidth": "10%", "bSortable": "false"}
			,{"sName": "documento", "sTitle": "Documento", "sWidth": "10%", "bSortable": "false"}
			,{"sName": "fecha", "sTitle": "Fecha", "sWidth": "10%", "bSortable": "true"}
			,{"sName": "proveedor", "sTitle": "Proveedor",  "bSortable": "false"}
			,{"sName": "importeMN", "sTitle": "Importe",  "bSortable": "true"}
			
		],
		"fnCreatedRow":function(nRow,aData,iDataIndex){
			$(nRow).attr("id",aData[0]);
		},
		"bDeferRender": false,
    	"bPaginate": false,
    	"bInfo": false,
    	iDisplayLength: 100
	});
	
	$("#refreshBtn").on('click',function(e){
		reload(null);
	});
					
});

function onSuccess(data){
	console.log('data')
};
		

function reload(data){
	var table=$("#facturasGrid").DataTable();
	table.ajax.reload();
	$('#agregarContendorDialog').val("");
}			
</script>
