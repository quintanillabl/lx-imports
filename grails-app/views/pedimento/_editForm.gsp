<%@ page import="com.luxsoft.impapx.Pedimento"%> 
<div class="row toolbar-panel">
	<div class="col-md-12 ">
		<div class="btn-group">
		   <lx:printButton/>
		    <lx:deleteButton bean="${pedimentoInstance}"/>
		    <buttn id="saveBtn" class="btn btn-success">
		    	<i class="fa fa-floppy-o"></i> Actualizar
		    </buttn>
		</div>
		
	</div>
</div> 
	
<div class="row">
	<g:form class="form-horizontal" name="updateForm" action="edit" id="${pedimentoInstance.id }">
		<div class="col-md-6">
			<f:with bean="${pedimentoInstance}" >
				<f:display property="id" input-disabled="true" wrapper="bootstrap3"/>
				<f:field property="fecha" wrapper="bootstrap3"/>
				<f:field property="proveedor" input-required="true" label="Agente" wrapper="bootstrap3"/>
				<f:field property="tipoDeCambio" widget-class="form-control tc" widget-type="text" wrapper="bootstrap3"/>
				<f:field property="impuestoTasa" widget-class="form-control iva" widget-type="text" wrapper="bootstrap3"/>
				<f:field property="dta" widget-class="form-control" widget-type="text" wrapper="bootstrap3"/>
				<f:field property="arancel" widget-class="form-control" widget-type="text" wrapper="bootstrap3"/>
				<f:field property="comentario" widget-class="form-control" wrapper="bootstrap3"/>
			</f:with>
		</div>
		<div class="col-md-6">
			<f:with bean="${pedimentoInstance}" >
				
				<f:display property="impuestoMateriaPrima" widget="money" wrapper="bootstrap3"/>
				<f:display property="incrementables" widget="money" wrapper="bootstrap3"/>
				<f:display property="ivaAcreditable" widget="money" wrapper="bootstrap3"/>
				<f:field property="prevalidacion" widget-class="form-control" widget-type="text" wrapper="bootstrap3"/>
				<f:display property="impuestoPrevalidacion" widget="money" wrapper="bootstrap3"/>
				<f:display property="impuesto" widget="money" wrapper="bootstrap3"/>
			</f:with>
		</div>
	</g:form>
</div>


<script type="text/javascript">
	$(function(){
		$(".numeric").autoNumeric({vMin:'0.00',wEmpty:'zero'}); 	
		$(".tc").autoNumeric({vMin:'0.00000',wEmpty:'zero'}); 	
		$(".iva").autoNumeric({altDec: '%', vMax: '20.00'});
		$("#saveBtn").click(function(){
			console.log('Salvar la forma');
			$("form[name=updateForm]").submit();
		});
		$('form[name=updateForm]').submit(function(e){
    		$(this).children('input[type=submit]')
    		.attr('disabled', 'disabled');
    		var button=$("#saveBtn");
    		button.attr('disabled','disabled')
    		.html('Procesando...')
    		//e.preventDefault(); 
    		return true;
		});
		//$("input[data-moneda]").autoNumeric({wEmpty:'zero',mRound:'B',aSign: '$'});
	});
</script>
