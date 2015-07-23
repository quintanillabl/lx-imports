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
</div>

<div id="facturasGrid">
	<g:render template="facturas" bean="${pedimentoInstance}"
		model="[facturas:pedimentoInstance.facturas]" />
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
			<g:formRemote name="asignacionDeFacturaForm" class="form-horizontal"
				url="[controller:'pedimento',action:'asignarFactura']"
				update="facturasGrid" onFailure="alert('Error');"
				after="clearForm();">
				<g:hiddenField name="pedimentoId" value="${pedimentoInstance?.id}" />
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
	}
	
	$(function(){
		$("#factura").autocomplete(
		{
			source:'${createLink(controller:'pedimento',action:'facturasDisponiblesPorAsignarJSON') }',
			minLength:3,
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
				url:"${createLink(controller:'pedimento',action:'cancelarAsignacionDeFacturas')}",
				data:{
					pedimentoId:${pedimentoInstance.id},partidas:JSON.stringify(res)
				},
				success:function(response){
					$("#facturasGrid").html(response);
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
		
					
	});		
				
</r:script>