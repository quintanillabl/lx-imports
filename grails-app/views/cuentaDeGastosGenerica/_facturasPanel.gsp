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
	
	<button class="btn " id="refreshBtn">
		<i class="icon-refresh "></i>
		<g:message code="default.button.load.label" default="Refrescar" />
	</button>
</div>

<div>
	<table id="facturasGrid" class=" simpleGrid table table-striped table-bordered table-condensed" cellpadding="0" cellspacing="0" border="0">
		<thead>
			<tr>
				<th>Id</th>
				<th>Documento</th>
				<th>Fecha</th>
				<th>Proveedor</th>
				<th>Importe</th>
				<th>Impuestos</th>
				<th>Retension</th>
				<th>Ret ISR</th>
				<th>Total</th>
				<th>Descuento</th>
				<th>Vale</th>
				<th>Otros</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
		<tfoot>
			<tr>
				<th></th>
				<th></th>
				<th></th>
				<th></th>
				<th><label id="importeGastos"><lx:moneyFormat number="${cuentaDeGastosGenericaInstance.importe}"/></label></th>
				<th><label id="impuestosGastos"><lx:moneyFormat number="${cuentaDeGastosGenericaInstance.impuestos}"/></label></th>
				<th><label ><lx:moneyFormat number="${cuentaDeGastosGenericaInstance.retension}"/></label></th>
				<th><label ><lx:moneyFormat number="${cuentaDeGastosGenericaInstance.retensionIsr}"/></label></th>
				<th><label id="totalGastos"><lx:moneyFormat number="${cuentaDeGastosGenericaInstance.total}"/></label></th>
				<th><label ><lx:moneyFormat number="${cuentaDeGastosGenericaInstance.descuento}"/></label></th>
				<th><label ><lx:moneyFormat number="${cuentaDeGastosGenericaInstance.rembolso}"/></label></th>
				<th><label ><lx:moneyFormat number="${cuentaDeGastosGenericaInstance.otros}"/></label></th>
			</tr>
		</tfoot>
	</table>
</div>

<%-- Forma para la asignacion de facturas --%>
<div class="modal hide fade" id="asignarFacturaDialog" tabindex=-1
	role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">&times;</button>
		<h4 class="myModalLabel">Asignacion de facturas</h4>
	</div>
	<div class="modal-body">
		<fieldset>
			<g:formRemote name="agregarFactura" class="form-horizontal"
				url="[controller:'cuentaDeGastosGenerica',action:'agregarFactura']"
				onSuccess=" reload(data)"
				onFailure="alert('Error');"
				onComplete="clearForm();">
				<g:hiddenField name="cuentaDeGastosId" value="${cuentaDeGastosGenericaInstance?.id}" />
				<g:hiddenField name="facturaId" />
				<input id="factura" class="input-xxlarge" type="text" name="factura"
					value="" placeholder="Seleccione una factura">

				<div class="form-actions">
					<a href="#" class="btn" data-dismiss="modal">Cancelar</a>
					<button type="submit" class="btn btn-primary">Asignar</button>
				</div>
			</g:formRemote>
		</fieldset>
	</div>

</div>
<%-- Fin de forma para la asignacion de factura --%>

<r:script>

function clearForm(){
	$("#factura").val("");
	$("#facturaId").val("");
	$("#asignarFacturaDialog").modal('hide');
}
	
$(function(){

	$('#asignarFacturaDialog').on('show', function(){
		$("#factura").val("");
		$("#facturaId").val("");
	});

	$("#factura").autocomplete(
		{
			source:'${createLink(controller:'cuentaDeGastosGenerica',action:'facturasDisponiblesPorAsignarJSON') }',
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
				url:"${createLink(controller:'cuentaDeGastosGenerica',action:'eliminarFacturas')}",
				type:'POST',
				data:{
					cuentaDeGastosId:${cuentaDeGastosGenericaInstance?.id},partidas:JSON.stringify(res)
				},
				success:function(response){
					console.log('Response: '+response)
					location.reload();
					
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
		
		"sDom": "<'row'<'span12'f>r>t<'row'<'span6'i><'span6'p>>",
        "oLanguage": {
      		"sUrl":"<g:resource dir="js" file="dataTables.spanish.txt" />"
    	},
    	bProcessing: true,
		bServerSide:false,
		"fnServerParams":function(aoData){
			aoData.push({"name":"cuentaDeGastosId","value":"${cuentaDeGastosGenericaInstance?.id}"})
		},
		sAjaxSource: '${createLink(controller:'cuentaDeGastosGenerica',action:'facturasAsJSON')}', 
		"aoColumns":[
			{"sName": "id", "sTitle": "Folio", "sWidth": "10%", "bSortable": "false"}
			,{"sName": "documento", "sTitle": "Documento", "sWidth": "10%", "bSortable": "false"}
			,{"sName": "fecha", "sTitle": "Fecha", "sWidth": "10%", "bSortable": "true"}
			,{"sName": "proveedor", "sTitle": "Proveedor",  "bSortable": "false"}
			,{"sName": "importeMN", "sTitle": "Importe",  "bSortable": "true"}
			,{"sName": "impuestosMN", "sTitle": "Impuestos",  "bSortable": "true"}
			,{"sName": "retensionMN", "sTitle": "Retension",  "bSortable": "false"}
			,{"sName": "retensionIsrMN", "sTitle": "Ret ISR",  "bSortable": "false"}
			,{"sName": "totalMN", "sTitle": "Total",  "bSortable": "true"}
			,{"sName": "descuentoMN", "sTitle": "Descuento",  "bSortable": "false"}
			,{"sName": "rembolsoMN", "sTitle": "Vales",  "bSortable": "false"}
			,{"sName": "otrosMN", "sTitle": "Otros",  "bSortable": "false"}
			
		],
		"fnCreatedRow":function(nRow,aData,iDataIndex){
			$(nRow).attr("id",aData[0]);
		},
		"bDeferRender": false,
    	"bPaginate": false,
    	"bInfo": false,
    	iDisplayLength: 100
	});
	
	$("#refreshBtn").live('click',function(e){
		oTable.fnReloadAjax();
	});
					
});

function onSuccess(data){
	console.log('Success: '+data)
};
		
function reload(data){
	console.log('Reloading...'+data);
	$("#facturasGrid").dataTable().fnReloadAjax();
	location.reload();
	
	
}			
</r:script>
