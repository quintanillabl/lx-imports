
<br>
<g:form name="updateForm" class="form-horizontal autocalculate" action="update" method="PUT">
	
	<f:with bean="pagoInstance">
		<g:hiddenField name="id" value="${pagoInstance.id}"/>
		<g:hiddenField name="version" value="${pagoInstance.version}"/>
		
		<f:display property="moneda" wrapper="bootstrap3"/>
		
		<f:field property="tc" widget="tc" wrapper="bootstrap3"/>
		<f:field property="fecha" wrapper="bootstrap3"/>
		<f:display property="aplicado"  widget="money" wrapper="bootstrap3"/>
		<f:field property="disponible" widget="money" wrapper="bootstrap3"/>
		<f:field property="comentario" widget-class="form-control" wrapper="bootstrap3"/>
	</f:with>
	<div class="form-group">
	    <div class="col-lg-offset-3 col-lg-9">
	    	<lx:backButton/>
	        <button id="saveBtn" class="btn btn-primary ">
	            <i class="fa fa-floppy-o"></i> Actualizar
	        </button>
	        <a href="" class="btn btn-danger " data-toggle="modal" data-target="#deleteDialog"><i class="fa fa-trash"></i> Eliminar</a> 
	    </div>
	</div>
	
</g:form>

<div class="modal fade" id="deleteDialog" tabindex="-1">
	<div class="modal-dialog ">
		<div class="modal-content">
			<g:form action="delete" class="form-horizontal" method="DELETE">
				<g:hiddenField name="id" value="${pagoInstance.id}"/>

				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Eliminar el registro ${pagoInstance.id}</h4>
				</div>
				<div class="modal-body">
					<p><small>${pagoInstance}</small></p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
					<g:submitButton class="btn btn-danger" name="aceptar" value="Eliminar" />
				</div>
			</g:form>
		</div><!-- moda-content -->
		
	</div><!-- modal-di -->
	
</div>
<script type="text/javascript">
	$(function(){
		$('.date').bootstrapDP({
		    format: 'dd/mm/yyyy',
		    keyboardNavigation: false,
		    forceParse: false,
		    autoclose: true,
		    todayHighlight: true
		});
		$('.chosen-select').chosen();
		$(".numeric").autoNumeric('init',{vMin:'0'},{vMax:'9999'});
		$(".money").autoNumeric('init',{wEmpty:'zero',mRound:'B',aSign: '$'});
		$(".tc").autoNumeric('init',{vMin:'0.0000'});
		$(".porcentaje").autoNumeric('init',{altDec: '%', vMax: '99.99'});

		$('form[name=updateForm]').submit(function(e){
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

		$('.autoCalculate').blur(function(){
			var importe=$("#importe").autoNumericGet();
			var tasa=$("#impuestoTasa").autoNumericGet();
			var impuestos=importe*(tasa/100);
			var total=(+importe)+(+impuestos);
			console.log('Importe : '+importe+ 'Impuestos:'+impuestos+' Total:'+total);
			$('#impuestos').autoNumeric('set',impuestos);
			$('#total').autoNumeric('set',total);
		});
	});
</script>

