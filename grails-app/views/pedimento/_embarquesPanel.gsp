<div class="row toolbar-panel">
	<div class="col-md-12 ">
		<div class="btn-group">
			<button  class="btn btn-success" data-target="#agregarContendorDialog" data-toggle="modal">
				<i class="icon-plus icon-white"></i>
		  		Agregar 
		  	</button>
		  	<button id="elimiarBtn" class="btn btn-danger" >
				<i class="fa fa-trash"></i> Eliminar
			</button>
			<button class="btn btn-default" id="refreshBtn">
				<i class="fa fa-refresh"></i> Refrescar
			</button>
		</div>
		
	</div>
</div> 


<div id="embarquesGrid">
	
	<table id="embarquesDetGrid" class="simpleGrid table table-striped table-bordered table-condensed">
	<thead>
		<tr>
			<th>Id</th>
			<th>Clave</th>
			<th>Descripcion</th>
			<th>Compra</th>
			<th>Cantidad</th>
			<th>Kg Net</th>
			<th>Kg Est</th>
			<th>Factura</th>
			<th>Pedimento</th>
			<th></th>
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
			<th></th>
			<th></th>
			<th></th>
			<th></th>
			<th>Total:</th>
			<th id="totalPedimento">
				<lx:moneyFormat number="${pedimentoInstance?.embarques.sum{ it.'gastosPorPedimento'} }"/>
				
			</th>
			
		</tr>
	</tfoot>
</table>
</div>

<div class="modal fade" id="agregarContendorDialog" tabindex="-1">
	<div class="modal-dialog">
		<div class="modal-content">
			<g:formRemote name="agregar" class="form-horizontal" id="${pedimentoInstance.id}"
				url="[controller:'pedimento',action:'agregarEmbarquesPorContenedor']"
				onSuccess=" reload(data)"
				onFailure="showError(XMLHttpRequest,textStatus,errorThrown)">

				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="myModalLabel">Agregar contenedor</h4>
				</div>

				<div class="modal-body ui-front">
					<g:hiddenField name="pedimentoId" value="${pedimentoInstance.id}"/>
					<g:hiddenField name="contenedorId" value=""/>
					<g:field type="text" name="contenedor" placeholder="Seleccione una contenedor" 
						class="form-control" required="true" autofocus="true"/>
				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
					<button type="submit" class="btn btn-primary" >
						<i class="icon-ok icon-white"></i>
						Aceptar
					</button>
				</div>

			</g:formRemote>	
		</div>
			
	</div>
	
</div>



<script>

function reload(data){
	console.log('Reloading...');
	var table=$("#embarquesDetGrid").DataTable();
	table.ajax.reload();
	//$("#embarquesDetGrid").dataTable().fnReloadAjax();
	$('#agregarContendorDialog').val("");
}

$(function(){
	
	
	$(".simpleGrid tbody tr").on('hover',function(){
		$(this).toggleClass("info");
	});
	
	$(".simpleGrid tbody tr").on('click',function(){
		$(this).toggleClass("success selected");
	});
	
	$(document).on("keydown",function(event){
		var keycode = (event.keyCode ? event.keyCode : event.which);
		if(event.ctrlKey ){
			if(keycode==65){
				//console.log('detecting under ctrl');
				$(".simpleGrid tbody tr").addClass("success selected");
			}else if(keycode==67){
				$(".simpleGrid tbody tr").removeClass("success selected");
			}
		}
	});
	
	function selectAllRows(){
		$(".simpleGrid tbody tr").addClass("success selected");
	}
	
	function clearAllRows(){
		$(".simpleGrid tbody tr").removeClass("success selected");
	}
	
	function selectedRows(){
		var res=[];
		var data=$(".simpleGrid .selected").each(function(){
			var tr=$(this);
			res.push(tr.attr("id"));
		});
		return res;
	}
	
	$('#agregarContendorDialog').on('show', function(){
		$("#contenedor").val("");
	});
		
	var oTable=$("#embarquesDetGrid").dataTable({
		
		"sDom": "<'row'<'span12'f>r>t<'row'<'span6'i><'span6'p>>",
        "oLanguage": {
      		"sUrl":"<g:resource dir="js" file="dataTables.spanish.txt" />"
    	},
    	bProcessing: true,
		bServerSide:false,
		"fnServerParams":function(aoData){
			aoData.push({"name":"pedimentoId","value":"${pedimentoInstance.id}"})
		},
		sAjaxSource: '${createLink(controller:'pedimento',action:'embarquesAsJSON')}', 
		"aoColumns":[
			{"sName": "detId", "sTitle": "EmbarqueDet","bVisible":false, "bSortable": false}
			,{"sName": "id", "sTitle": "Embarque", "sWidth": "10%", "bSortable": "false"}
			,{"sName": "bl", "sTitle": "BL", "sWidth": "10%", "bSortable": "false"}
			,{"sName": "contenedor", "sTitle": "Contenedor", "sWidth": "10%", "bSortable": "true"}
			,{"sName": "clave", "sTitle": "Producto", "sWidth": "10%", "bSortable": "true"}
			,{"sName": "descripcion", "sTitle": "Desc",  "bSortable": "true"}
			,{"sName": "cantidad", "sTitle": "Cantidad", "sWidth": "10%", "bSortable": "false"}
			,{"sName": "kilosNetos", "sTitle": "KgNet", "sWidth": "10%", "bSortable": "false"}
			,{"sName": "kilosEstimados", "sTitle": "KgEst", "sWidth": "10%", "bSortable": "false"}
			,{"sName": "gastosPorPedimento", "sTitle": "Pedimento",  "bSortable": "false"}
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
		//oTable.fnReloadAjax();
		var table=$("#embarquesDetGrid").DataTable();
		table.ajax.reload();
	});
	
	$("#elimiarBtn").on('click',function(e){
		eliminar();
	});
	
	
	
	
	function selectedRows(){
		var res=[];
		var data=$("tbody > tr.selected").each(function(){
			var tr=$(this);
			res.push(tr.attr("id"));
		});
		return res;
	}
	
	function eliminar(){
			var res=selectedRows();
			if(res.length==0){
				alert('Debe seleccionar al menos un registro');
				return;
			}
			var ok=confirm('Eliminar  ' + res.length+' partida(s)?');
			if(!ok)
				return;
			console.log('Cancelando facturas: '+res);
			$.ajax({
				url:"${createLink(controller:'pedimento',action:'eliminarAsignacionDeEmbarques')}",
				data:{
					pedimentoId:${pedimentoInstance.id},
					partidas:JSON.stringify(res)
				},
				success:function(response){
					//$("#embarquesGrid").html(response);
					oTable.fnReloadAjax();
				},
				error:function(request,status,error){
					alert("Error: "+status);
				},
				complete:function(){
					$('#agregarContendorDialog').val("");
				}
			});
	}
	
	$("#contenedor").autocomplete(
		{
			source:'${createLink(controller:'pedimento',action:'contenedoresPendientes') }',
			minLength:2,
			select:function(e,ui){
		   		$("#contenedorId").val(ui.item.id);
		}
	});
	
	
});	
		
function showError(XMLHttpRequest,textStatus,errorThrown){
	console.log('Error: '+errorThrown);
	
}		
</script>

