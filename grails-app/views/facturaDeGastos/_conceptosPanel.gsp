<div class="modal fade" id="createGastoDialog" tabindex="-1">
	<div class="modal-dialog ">
		<div class="modal-content">
		<g:form class="form-horizontal" name="createForm" action="agregarPartida" >
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4>Alta de concepto</h4>
			</div>
			<div class="modal-body ">
				<f:with bean="${new com.luxsoft.impapx.ConceptoDeGastoCommand() }">
					<g:hiddenField name="factura" value="${facturaDeGastosInstance.id}"/>
					<f:field property="concepto" wrapper="bootstrap3">
						<g:hiddenField id="conceptoId" name="concepto.id"/>
						<g:field type="text" id="concepto" name="conceptoDesc" required="true" 
							class="form-control "/>
					</f:field>
					<f:field property="tipo" 
						widget-class="form-control" wrapper="bootstrap3"/>
					<f:field property="descripcion" 
						widget-required="true" widget-class="form-control" wrapper="bootstrap3"/>
					<f:field property="importe" 
						widget-required="true"  widget="money" wrapper="bootstrap3"/>
					<f:field property="impuestoTasa" 
						widget-required="true" widget="porcentaje" wrapper="bootstrap3"/>
					<f:field property="retensionTasa" label="Ret (%)"
						widget-required="true" widget="porcentaje" wrapper="bootstrap3"/>
					<f:field property="retensionIsrTasa"  label="Ret ISR(%)"
						widget-required="true" widget="porcentaje" wrapper="bootstrap3"/>
					<f:field property="descuento"  
						widget="money" wrapper="bootstrap3"/>
					<f:field property="rembolso"  
						widget="money" label="Vales" wrapper="bootstrap3"/>
					<f:field property="fechaRembolso"  
						label="Fecha Vales" wrapper="bootstrap3"/>
					<f:field property="otros"  
						widget="money" wrapper="bootstrap3"/>
					<f:field property="comentarioOtros"
						widget-class="form-control" wrapper="bootstrap3"/>
				</f:with>
			</div>
			
			<div class="modal-footer">
				<button id="createBtn" type="submit" class="btn btn-primary">
					<i class="icon-ok icon-white"></i>
					<g:message code="default.button.create.label" default="Create" />
				</button>
			</div>

		</g:form>
		</div>
	</div>
</div>


<%--
<script>
$(function(){
	$("#concepto").autocomplete({
		source:'<g:createLink controller="cuentaContable" action="cuentasDeDetalleJSONList"/>',
		minLength:3,
		select:function(e,ui){
			console.log('Valor seleccionado: '+ui.item.id);
			$("#conceptoId").val(ui.item.id);
		}
	});
	
	$("#eliminarBtn").click(function(e){
		eliminar();
	});
	
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
			url:"${createLink(action:'eliminarConceptos')}",
			data:{
				facturaId:${facturaDeGastosInstance.id},partidas:JSON.stringify(res)
			},
			success:function(response){
				location.reload();
			},
			error:function(request,status,error){
				alert("Error: "+status);
			}
		});
	}
	
	$(".tasa").autoNumeric({aSign: ' %', pSign: 's', vMin:'0.00',vMax: '100.00',wEmpty:'zero'} );
	$(".moneyField").autoNumeric({vMin:'0.00',wEmpty:'zero',mRound:'B'});
	$('#c_Importe').blur(function(){
		console.log('Importe registrado');
		var importe=$("#c_Importe").autoNumericGet();
		$('#c_ietu').autoNumericSet(importe);
	});
	
});
function selectedRows(){
	var res=[];
	var data=$("tbody tr.selected").each(function(){
		var tr=$(this);
		res.push(tr.attr("id"));
	});
	return res;
}
</script>
--%>