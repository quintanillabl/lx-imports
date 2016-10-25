<div class="modal fade" id="createConceptoDialog" tabindex="-1">
	<div class="modal-dialog ">
		<div class="modal-content">
		<g:form class="form-horizontal" name="createGastoForm" action="agregarPartida" >
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4>Alta de concepto</h4>
			</div>
			<div class="modal-body ui-front">
				<f:with bean="${new com.luxsoft.impapx.ConceptoDeGastoCommand() }">
					<g:hiddenField name="factura" value="${facturaDeGastosInstance.id}"/>
					<g:hiddenField name="version" value="${facturaDeGastosInstance.id}"/>
					
					<f:field property="concepto" wrapper="bootstrap3">
						<g:hiddenField id="conceptoId" name="concepto.id"/>
						<g:field type="text" id="conceptoField" name="conceptoDesc" required="true" 
							class="form-control "/>
					</f:field>

					<f:field property="tipo" widget-class="form-control" wrapper="bootstrap3"/>
					
					<f:field property="descripcion" widget-required="true" widget-class="form-control" wrapper="bootstrap3"/>
					
					<f:field property="importe" widget-required="true"  widget="money" wrapper="bootstrap3"/>
					
					<f:field property="impuestoTasa" widget-required="true" widget="porcentaje" wrapper="bootstrap3"/>
					
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
	<script>
	$(function(){
		// $("#concepto").autocomplete({
		// 	source:'<g:createLink controller="cuentaContable" action="cuentasDeDetalleJSONList"/>',
		// 	minLength:1,
		// 	select:function(e,ui){
		// 		console.log('Valor seleccionado: '+ui.item.id);
		// 		$("#conceptoId").val(ui.item.id);
		// 	}
		// });
		$("#conceptoField").autocomplete({
			source:'<g:createLink controller="cuentaContable" action="cuentasDeDetalleJSONList"/>',
			minLength:1,
			select:function(e,ui){
				console.log('Valor seleccionado: '+ui.item.id);
				$("#conceptoId").val(ui.item.id);
			}
		});

		$('body').on('shown.bs.modal', '.modal', function () {
 			$('[id$=concepto]').focus();
 		});
		
		$("#eliminarBtn").click(function(e){
			eliminar();
		});

		$('.chosen-select').chosen();
		$(".numeric").autoNumeric('init',{vMin:'0'},{vMax:'9999'});
		$(".money").autoNumeric('init',{wEmpty:'zero',mRound:'B',aSign: '$'});
		$(".tc").autoNumeric('init',{vMin:'0.0000'});
		$(".porcentaje").autoNumeric('init',{altDec: '%', vMax: '99.99'});

		$(".tasa").autoNumeric({aSign: ' %', pSign: 's', vMin:'0.00',vMax: '100.00',wEmpty:'zero'} );
		$(".moneyField").autoNumeric({vMin:'0.00',wEmpty:'zero',mRound:'B'});



		$('form[name=createGastoForm]').submit(function(e){
		    console.log("Desablidatndo submit button....");

		    var button=$("#saveBtn");
		    button.attr('disabled','disabled')
		    .html('Procesando...');

		    $(".money,.porcentaje,.numeric,.tc",this).each(function(index,element){
		      var val=$(element).val();
		      var name=$(this).attr('name');
		      var newVal=$(this).autoNumeric('get');
		      $(this).val(newVal);
		    });
		    //e.preventDefault(); 
		    return true;
		});

		var actualizar = function(){
			var importe=$("#importe").autoNumeric('get');
			var ivaTasa=$("#impuestoTasa").autoNumeric('get');
			var retIva=$("#retensionTasa").autoNumeric('get');
			var retIsr=$("#retensionIsr").autoNumeric('get');
			var descuento=$("#descuento").autoNumeric('get');
			var descuento=$("#rembolso").autoNumeric('get');
			var descuento=$("#otros").autoNumeric('get');
			var impuesto=importe*(ivaTasa/100);
		};
		$("input[type='text']").on("focusout",function(){
			//actualizar();
		});
		
		
	});
	
	</script>

</div>



