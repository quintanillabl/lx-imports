<div class="row">
	<div class="col-md-6">
		<br>
		<g:form name="updateForm" class="form-horizontal" action="update" method="PUT">
			<g:hiddenField name="id" value="${notaDeCreditoInstance.id}"/>
			<g:hiddenField name="version" value="${notaDeCreditoInstance.version}"/>

			<f:with bean="notaDeCreditoInstance">
				<f:display property="proveedor" wrapper="bootstrap3"/>
				<f:field property="concepto" widget-class="form-control" wrapper="bootstrap3"/>
				<f:field property="moneda" wrapper="bootstrap3"/>
				<f:field property="tc" widget="tc" wrapper="bootstrap3"/>
				<f:field property="documento" widget-class="form-control" wrapper="bootstrap3"/>
				<f:field property="fecha" wrapper="bootstrap3"/>
				<f:field property="importe" widget="money" wrapper="bootstrap3"/>
				<f:field property="impuestoTasa" widget="porcentaje" widget-class="autoCalculate" wrapper="bootstrap3"/>
				<f:field property="impuestos" widget="money" widget-class="autoCalculate" wrapper="bootstrap3"/>
				<f:field property="comentario" widget-class="form-control" wrapper="bootstrap3"/>
			</f:with>
			<div class="form-group">
			    <div class="col-lg-offset-3 col-lg-9">
			        <button id="saveBtn" class="btn btn-primary ">
			            <i class="fa fa-floppy-o"></i> Actualizar
			        </button>
			        <a href="" class="btn btn-danger " data-toggle="modal" data-target="#deleteDialog"><i class="fa fa-trash"></i> Eliminar</a> 
			    </div>
			</div>
		</g:form>
	</div>

	<div class="col-md-6">
		<br>
		<form class="form-horizontal">
			<f:with bean="notaDeCreditoInstance">
				<f:display property="total" widget="money" widget-class="autoCalculate " wrapper="bootstrap3"/>
				<f:display property="aplicado" widget="money" widget-class="autoCalculate " wrapper="bootstrap3"/>
				<f:display property="disponible" widget="money" widget-class="autoCalculate " wrapper="bootstrap3"/>
			</f:with>
		</form>
	</div>
	<g:render template="/common/deleteDialog" bean="${notaDeCreditoInstance}"/>
</div>
<%--

<fieldset>
	<g:form class="form-horizontal" action="edit" id="${notaDeCreditoInstance?.id}">
		<fieldset>
			<f:with bean="notaDeCreditoInstance">
				<g:hiddenField name="version" value="${notaDeCreditoInstance.version}"/>
				
				
				<f:field property="concepto" input-disabled="disabled" value="${ notaDeCreditoInstance.concepto}"/>
				<f:field property="moneda" input-readOnly="true">
					<g:field type="text" value="${notaDeCreditoInstance.moneda}" name="moneda" readOnly="true"/>
				</f:field>
				<f:field property="tc" value="${notaDeCreditoInstance.tc }"/>
				<f:field property="documento" value="${notaDeCreditoInstance.documento}"/>
				<f:field property="fecha"/>
				<g:if test="notaDeCreditoInstance.aplicado">
					<f:field property="importe" input-class="autoCalculate moneyField" />
					<f:field property="impuestoTasa" input-class="autoCalculate"/>
					<f:field property="impuestos" input-class="autoCalculate moneyField"/>
					<f:field property="total" input-class="autoCalculate moneyField"/>
				</g:if>
				
				<f:field property="aplicado"  value="${notaDeCreditoInstance.aplicado?:0.0}" input-readOnly="true"/>
				<f:field property="disponible" value="${notaDeCreditoInstance.disponible}" input-readOnly="true"/>
				<f:field property="comentario" input-class="input-xxlarge"/>
			</f:with>
					
			<div class="form-actions">
				<button type="submit" class="btn btn-primary">
					<i class="icon-ok icon-white"></i>
					<g:message code="default.button.update.label" default="Update" />
				</button>
				
				
				
				<g:actionSubmit value="Eliminar" action="delete" class="btn btn-danger" onclick="return myConfirm2(this,'Eliminar nota de credito?','Eliminar nota?')">
					<i class="icon-trash icon-white"></i>
					<g:message code="default.button.delete.label" default="Delete" />
				</g:actionSubmit>
				
				
				
			</div>
		</fieldset>
	</g:form>
</fieldset>

$(function(){
	
	$('#moneda').bind('change',function(e){
		var selected=$(this).val();
		if(selected=="MXN"){
			$("#tc").attr("disabled",true);//.val(1.0);
			$("#tc").autoNumericSet(1.0);
		}else
			$("#tc").attr("disabled",false);
		
	});
	
	$(".moneyField").autoNumeric({vMin:'0.00',wEmpty:'zero',mRound:'B'});
 	$("#tc").autoNumeric({vMin:'0.0000'});
	$("#fecha").mask("99/99/9999");
	$("#impuestoTasa").autoNumeric({aSign: ' %', pSign: 's', vMin:'0.00',vMax: '100.00',wEmpty:'zero'} );
	
	
		
	$('.autoCalculate').blur(function(){
		var importe=$("#importe").autoNumericGet();
		var tasa=$("#impuestoTasa").autoNumericGet();
		var impuestos=importe*(tasa/100);
		var total=(+importe)+(+impuestos);
		console.log('Importe : '+importe+ 'Impuestos:'+impuestos+' Total:'+total);
		$('#impuestos').autoNumericSet(impuestos);
		$('#total').autoNumericSet(total);
	});
	
});
--%>

