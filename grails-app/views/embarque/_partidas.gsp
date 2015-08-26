
<div class="row toolbar-panel">
    <div class="col-md-3">
    	<input type='text' id="filtro" placeholder="Filtrar" class="form-control" autofocus="on">
    </div>
    <div class="btn-group">
    	<g:link action="comprasPendientes" id="${embarqueInstance.id}" class="btn btn-outline btn-primary">
        	<i class="fa fa-plus"></i> Agregar
        </g:link> 
    	<button  id="#eliminarBtn" class="btn btn-danger" onclick="eliminarPartidas()">
			<i class="fa fa-trash"></i> Eliminar
  		</button>
    </div>
    <div class="btn-group">
        <button type="button" name="operaciones"
                class="btn btn-success btn-outline dropdown-toggle" data-toggle="dropdown"
                role="menu">
                Operaciones <span class="caret"></span>
        </button>
        <ul class="dropdown-menu">
        	<g:if test="${embarqueInstance.facturado<=0}">
        		<li><a id="asignarFacturaBtn"><i class="fa fa-chain"></i> Asignar factura</a></li>
        		<li><a  onclick="cancelarFacturas()"><i class="fa fa-chain-broken"></i> Canclear asignación</a></li>
        		<li><a id="asignarContenedorBtn"><i class="fa fa-cube"></i> Asignar Contenedor</a></li>
        		<li>
        			<g:link  action="eliminarFaltantes" id="${embarqueInstance.id}"
        				onclick="return confirm('Eliminar partidas sin kilos netos asignados?');">
        				<i class="fa fa-eraser"></i> Eliminar faltantes
        			</g:link>
        		</li>
        		<li>
        			<g:link action="print"  id="${embarqueInstance.id}">
        			    <i class="fa fa-print"></i> Imprimir
        			</g:link> 
        		</li>
        	</g:if>
        </ul>
    </div>
</div>

<div>
	<g:if test="${embarqueInstance.cuentaDeGastos}">
		<g:link controller='cuentaDeGastos' action="edit" id="${embarqueInstance.cuentaDeGastos}" target="_blank">
			Cuenta de gastos:  ${embarqueInstance.cuentaDeGastos}
		</g:link>
	</g:if>
</div>

<div class="row">
	<div class="col-md-12" style="overflow: auto;">
		<table id="grid" class="table table-striped table-bordered table-condensed " >
			<thead>
				<tr>
					<th>Clave</th>
					<th with="150px">Descripcion</th>
					<th>Compra</th>
					<th>Cantidad</th>
					<th>Kg Net</th>
					<th>Kg Est</th>
					<th>Precio</th>
					<th>Importe</th>
					<th>Factura</th>
					<th>Contenedor</th>
					<th>Tarimas</th>
				</tr>
			</thead>
			<tbody>
				<g:each in="${partidas}" var="row">
				<tr id="${ formatNumber(number:row.id,format:'##########') }" name="embarqueDetId"
					class="${( row?.kilosNetos<=0 || row?.tarimas<=0 )?'text-danger':'' }">
					<td>
						<g:link action="edit" controller="embarqueDet" id="${row?.id}" params="[proveedorId:embarqueInstance.proveedor.id]">
							${fieldValue(bean:row, field:"producto.clave")}
						</g:link>
					</td>
					<td>
						<g:link action="edit" controller="embarqueDet" id="${row?.id}">
							${fieldValue(bean:row, field: "producto.descripcion")}
						</g:link>
					</td>
					<td>${fieldValue(bean: row, field: "compraDet.compra.folio")}</td>
					<td>${fieldValue(bean: row, field: "cantidad")}</td>
					<td>${fieldValue(bean: row, field: "kilosNetos")}</td>
					<td>${fieldValue(bean: row, field: "kilosEstimados")}</td>
					<td><lx:moneyFormat number="${row.precio }"/></td>
					<td><lx:moneyFormat number="${row.importe }"/></td>
					<td name="factura">
						<g:link controller="facturaDeImportacion" action="edit" id="${row?.factura?.id} " target="_blank">
							${fieldValue(bean: row, field: "factura.documento")}
						</g:link>
					</td>
					<td name="contenedor">${fieldValue(bean: row, field: "contenedor")}</td>
					<td name="tarimas">${fieldValue(bean: row, field: "tarimas")}</td>
				</tr>
				</g:each>
			</tbody>
			<tfoot>
				<tr>
					<th></th>
					<th></th>
					<th></th>
					<th id="totalCantidad"><g:formatNumber number="${embarqueInstance?.getTotal('cantidad')}" format="###,###,###.##"/></th>
					<th id="totalKilos"><g:formatNumber number="${embarqueInstance?.getTotal('kilosNetos')}" format="###,###,###.##"/></th>
					<th id="totalKilsoEstimados"><g:formatNumber number="${embarqueInstance?.getTotal('kilosEstimados')}" format="###,###,###.##"/></th>
					<th></th>
					<th id="totalImporte"><lx:moneyFormat number="${embarqueInstance?.getTotal('importe')}" /></th>
					<th></th>
					<th></th>
					<th></th>
					
				</tr>
			</tfoot>
		</table>
	</div>
</div>

<div class="modal fade" id="asignarFacturaDialog" tabindex="-1">
	<div class="modal-dialog ">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="myModalLabel">Asignacion de facturas</h4>
			</div>
			<div class="modal-body ui-front">
				<g:hiddenField name="facturaId"/>
				<div class="form-group">
			        <input id="factura" type="text" name="factura" class="form-control" 
			        	placeholder="Seleccione una factura" >
			    </div>
			</div>
			<div class="modal-footer">
				<a href="#" class="btn btn-default btn-outline" data-dismiss="modal">Cancelar</a>
		    	<button id="asignarFactura" class="btn btn-primary" onclick="asignarFactura()">Asignar</button>
			</div>
		</div><!-- moda-content -->
	</div><!-- modal-di -->
</div>

<div class="modal fade" id="asignarContendorDialog" tabindex="-1">
	<div class="modal-dialog ">
		<div class="modal-content">
			<form name="asignarContenedorForm">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="myModalLabel">Asignacion de contenedor</h4>
				</div>
				<div class="modal-body ui-front">
					<div class="form-group">
				        <input id="contenedor" type="text" name="contendor" value="" 
				        	placeholder="Digite el  contenedor" class="form-control" >
				    </div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
			    	<g:submitButton name="asignar" class="btn btn-info"  value="Aplicar" />
			    	%{-- <g:submitButton name="aceptar" class="btn btn-primary " value="Asignar"/> --}%
				</div>
			</form>
		</div><!-- moda-content -->
	</div><!-- modal-di -->
</div>

<script type="text/javascript">
	
	$(function(){	

		// Grid y seleccion
		$('#grid').dataTable( {
        	"paging":   false,
        	"ordering": false,
        	"info":     false,
        	"language": {
				"url": "${assetPath(src: 'datatables/dataTables.spanish.txt')}"
    		},
    		//"dom": '',
    		"order": []
    	} );
    	$("#filtro").on('keyup',function(e){
    		var term=$(this).val();
    		$('#grid').DataTable().search(
				$(this).val()
    		        
    		).draw();
    	});
		$("tbody tr").on('click',function(){
			$(this).toggleClass("success selected");
		});
		$(document).on("keydown",function(event){
			var keycode = (event.keyCode ? event.keyCode : event.which);
			if(event.ctrlKey ){
				if(keycode==65){
					$("#grid tr").addClass("success selected");
					return false;
				}else if(keycode==67){
					$("#grid tr").removeClass("success selected");
					return false;
				}
			}
		})
		/// End grid y seleccion

		/*** Asignacion de facturas ***/

		$("#factura").autocomplete({
			source:'${createLink(action:'facturasPorAsignarJSON',params:[proveedorId:embarqueInstance.proveedor.id]) }',
			minLength:1,
			select:function(e,ui){
	   			$("#facturaId").val(ui.item.id);
			}
		});
		
		$("#facturaGastos").autocomplete({
			source:'${createLink(action:'facturasDeGastosPorAsignarJSON') }',
			minLength:1,
			select:function(e,ui){
	   			$("#facturaGastosId").val(ui.item.id);
			}
		});

		$("#asignarFacturaBtn").on('click',function(){
			var res=selectedRows();
			if(res.length==0){
				alert('Debe seleccionar al menos un registro');
				return;
			}
			$("#facturaId").val("");
			$("#factura").val("");
			$('#asignarFacturaDialog').modal('show');
		});
		
		$('body').on('shown.bs.modal', '#asignarFacturaDialog', function () {
          $('[id$=factura]').focus();
      	});

		$('#asignarFacturaGastosDialog').on('show', function(){
			$("#facturaGastosId").val("");
			$("#facturaGastos").val("")
		});

		$('#asignarFacturaDialog').on('show', function(){
			$("#facturaId").val("");
			$("#factura").val("");
		});

		//// Asignacion de CONTENEDOR

		$("#asignarContenedorBtn").on('click',function(){
			var res=selectedRows();
			if(res.length==0){
				alert('Debe seleccionar al menos un registro');
				return;
			}
			$("#contenedor").val("").focus();
			$('#asignarContendorDialog').modal('show');
		});

		$('body').on('shown.bs.modal', '#asignarContendorDialog', function () {
          $('[id$=contenedor]').focus();
      	});

      	$('form[name=asignarContenedorForm]').submit(function(e){
      	    e.preventDefault(); 
      	    var res=selectedRows();
      	    var contenedor=$("#contenedor").val();
      	    //console.log('Asignando contenedor: '+contenedor+' a partidas: '+res);
      	    $.post(
      	    	"${createLink(controller:'embarque',action:'asignandoContenedor')}",
      	    	{contenedor:contenedor,partidas:JSON.stringify(res)}
      	    ).done(function(data){
      	    	//console.log('Rres: '+data.contenedor);
      	    	$('.selected td[name=contenedor]').text(data.contenedor);
      	    	$("#contenedor").val("");
      	    	$("#asignarContendorDialog").modal("hide");
      	    }).fail(function(jqXHR, textStatus, errorThrown){
      	    	console.log(errorThrown);
      	    	alert("Error: "+errorThrown);
      	    });
      	    return true;
      	});
      	// End asignacion de contenedores
		
	});
	

	function asignarFactura(){
		var facturaId=$('#facturaId').val();
		if(facturaId==""){
			alert("Debe selccionar una factura para asignar");
			return;
		}
		
		var res=selectedRows();
		if(res.length==0){
			alert("Debe seleccionar al menos una partida de embarque");
			return;
		}
		console.log('Asignando factura: '+facturaId);
		console.log('Asignando factura: '+facturaId+' a partidas: '+res);
		$.ajax({
			url:"${createLink(controller:'embarque',action:'asignarFactura')}",
			dataType:"json",
			data:{
				facturaId:facturaId,partidas:JSON.stringify(res)
			},
			success:function(data,textStatus,jqXHR){
				console.log('Rres: '+data.documento);
				$('.selected td[name=factura]').text(data.documento);
				$("#asignarFacturaDialog").modal("hide")

			},
			error:function(request,status,error){
				console.log(error);
				alert("Error: "+error);
			},
			complete:function(){
				console.log('OK ');
			}
		});
	}

	function cancelarFacturas(){
		
		var res=selectedRows();
		if(res.length==0){
			alert('Debe seleccionar al menos un registro');
			return;
		}
		var ok=confirm('Cancelar asignación de ' + res.length+' factura(s)?');
		if(!ok)
			return;
		console.log('Cancelando facturas');
		$.ajax({
			url:"${createLink(controller:'embarque',action:'cancelarAsignacionDeFacturas')}",
			dataType:"json",
			data:{
				partidas:JSON.stringify(res)
			},
			success:function(data,textStatus,jqXHR){
				$('.selected td[name=factura]').text("");
			},
			error:function(request,status,error){
				alert("Error: "+error);
			},
			complete:function(){
				console.log('OK ');
			}
		});
	}
	
	function selectedRows(){
		var res=[];
		var data=$(".selected").each(function(){
			var tr=$(this);
			res.push(tr.attr("id"));
		});
		return res;
	}


	
	
	
	function asignarFacturaDeGastos(){
		var facturaId=$('#facturaGastosId').val();
		if(facturaId==""){
			alert("Debe selccionar una factura de gastos");
			return;
		}
		
		console.log('Asignando factura de gastos: '+facturaId);
		$.ajax({
			url:"${createLink(controller:'embarque',action:'asignarFacturaDeGastos')}",
			dataType:"json",
			data:{
				embarqueId:${embarqueInstance.id},facturaId:facturaId
			},
			success:function(data,textStatus,jqXHR){
				//console.log('Rres: '+data.gasto);
				$("#asignarGastosDialog").modal("hide")
				location.reload();

			},
			error:function(request,status,error){
				console.log(error);
				alert("Error: "+error);
			},
			complete:function(){
				console.log('OK ');
			}
		});
	}
	
	function eliminarPartidas(){
		var res=selectedRows();
		if(res==""){
			alert("Debe seleccionar alguna partida para eliminar");
			return;
		}
		var ok=confirm("Eliminar "+res.length+" partidas? ");
		if(!ok)
			return;
		console.log('Eliminando partidas : '+res);
		$.ajax({
			url:"${createLink(controller:'embarque',action:'eliminarPartidas')}",
			dataType:"json",
			data:{
				embarqueId:${embarqueInstance?.id},partidas:JSON.stringify(res)
			},
			success:function(data,textStatus,jqXHR){
				console.log('Partidas eleiminadas: '+data.eliminadas);
				location.reload();
			},
			error:function(request,status,error){
				//alert("Error: "+error);
				console.log('Error aparente: '+status);
				//location.reload();
			},
			complete:function(){
				console.log('OK ');
				
			}
		});
	}

</script>

