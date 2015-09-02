<%@ page import="com.luxsoft.impapx.cxp.ConceptoDeGasto" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="luxor">
	<title>Concepto ${conceptoDeGastoInstance.id}</title>
	<asset:javascript src="forms/forms.js"/>
</head>

<content tag="header">
	Concepto de gasto ${conceptoDeGastoInstance.id}
</content>
<content tag="subHeader">
	<ol class="breadcrumb">
		<li><g:link action="index">Factura de gasto: ${conceptoDeGastoInstance.factura}</g:link></li>
	</ol>
</content>

<content tag="document">
	<div class="row">
	    <div class="col-lg-8">
	        <div class="ibox float-e-margins">
	            <div class="ibox-title">
	                <h5>Factura ${conceptoDeGastoInstance.factura}</h5>
	            </div>
	            <div class="ibox-content">
	                <lx:errorsHeader bean="${conceptoDeGastoInstance}"/>
	                <g:form name="updateForm" action="update" class="form-horizontal" method="PUT">  
	                    <g:hiddenField name="id" value="${conceptoDeGastoInstance.id}"/>
	                    <g:hiddenField name="version" value="${conceptoDeGastoInstance.version}"/>
	                    <f:with bean="conceptoDeGastoInstance">
	                    	<g:hiddenField name="factura.id" value="${conceptoDeGastoInstance?.factura?.id }"/>
	                    	
	                    	<f:field property="concepto" >
	                    		<g:hiddenField id="conceptoId" name="concepto.id" 
	                    			value="${conceptoDeGastoInstance?.concepto?.id}"/>
	                    		<g:field type="text" id="concepto" name="conceptoDesc" required="true" class="form-control" 
	                    			value="${conceptoDeGastoInstance?.concepto}"/>
	                    	</f:field>
	                    	<f:field property="tipo" value="${conceptoDeGastoInstance?.tipo}" widget-class="chosen-select"/>
	                    	<f:field property="descripcion" widget-required="true"  widget-class="form-control"/>
	                    
	                    	<f:field property="importe" widget="money" input-id="c_Importe" input-class="moneyField"/>
	                    	<f:field property="impuestoTasa" widget="porcentaje"/>
	                    	<f:field property="impuesto" widget="money"/>
	                    	<f:field property="retensionTasa" widget="porcentaje"/>
	                    	<f:field property="retension" widget="money"/>
	                    	<f:field property="retensionIsrTasa" widget="porcentaje"/>
	                    	<f:field property="retensionIsr" widget="money"/>
	                    	<f:field property="descuento"  widget="money"/>
	                    	<f:field property="rembolso"  widget="money" label="Vales"/>
	                    	<f:field property="fechaRembolso"  label="Fecha Vales"/>
	                    	<f:field property="otros"  widget-class="form-control"/>
	                    	<f:field property="comentarioOtros" widget-class="form-control" />
	                    </f:with>
	                    <div class="form-group">
	                        <div class="col-lg-offset-3 col-lg-9">
	                            <button id="saveBtn" class="btn btn-primary ">
	                                <i class="fa fa-floppy-o"></i> Actualizar
	                            </button>
	                            <lx:backButton/>
	                        </div>
	                    </div>
	                </g:form>
	            </div>
	        </div>
	    </div>
	</div>
	<script type="text/javascript">
		$(function(){
			$("#concepto").autocomplete({
				source:'<g:createLink controller="cuentaContable" action="cuentasDeDetalleJSONList"/>',
				minLength:3,
				select:function(e,ui){
					console.log('Valor seleccionado: '+ui.item.id);
					$("#conceptoId").val(ui.item.id);
				}
			});
			
			$('#c_Importe').blur(function(){
				console.log('Importe registrado');
				var importe=$("#c_Importe").autoNumericGet();
				$('#c_ietu').autoNumericSet(importe);
			});
			$('.chosen-select').chosen();
			$(".numeric").autoNumeric('init',{vMin:'0'},{vMax:'9999'});
			$(".money").autoNumeric('init',{wEmpty:'zero',mRound:'B',aSign: '$'});
			$(".tc").autoNumeric('init',{vMin:'0.0000'});
			$(".porcentaje").autoNumeric('init',{altDec: '%', vMax: '99.99'});

			$('form[name=updateForm]').submit(function(e){
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
		});
	</script>
</content>

	
</body>
</html>
