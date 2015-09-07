<%@ page import="com.luxsoft.impapx.cxp.NotaDeCredito" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="createForm">
	<g:set var="entityName" value="${message(code: 'notaDeCredito.label', default: 'Nota de crédito')}" scope="request"/>
	<g:set var="entity" value="${notaDeCreditoInstance}" scope="request" />
	<title>Alta de nota de crédito</title>
</head>

<body>
<content tag="header">
	Alta de notas de crédito
</content>

<content tag="formFields">
	<f:with bean="notaDeCreditoInstance">
		<f:field property="proveedor" wrapper="bootstrap3"/>
		<f:field property="concepto" widget-class="form-control" wrapper="bootstrap3"/>
		<f:field property="moneda" wrapper="bootstrap3"/>
		<f:field property="tc" widget="tc" wrapper="bootstrap3"/>
		<f:field property="documento" widget-class="form-control" wrapper="bootstrap3"/>
		<f:field property="fecha" wrapper="bootstrap3"/>
		<f:field property="importe" widget="money" wrapper="bootstrap3"/>
		<f:field property="impuestoTasa" widget="porcentaje" widget-class="autoCalculate" wrapper="bootstrap3"/>
		<f:field property="impuestos" widget="money" widget-class="autoCalculate" wrapper="bootstrap3"/>
		<f:field property="total" widget="money" widget-class="autoCalculate " wrapper="bootstrap3"/>
		<f:field property="comentario" widget-class="form-control" wrapper="bootstrap3"/>
	</f:with>

	<script type="text/javascript">
		$(function(){
			$('form').on('focusout',function(e){
				console.log("Focusout: "+e);
				var importe=$("#importe").autoNumeric('get');
				var tasa=$("#impuestoTasa").autoNumeric('get');
				var impuestos=importe*(tasa/100);
				var total=(+importe)+(+impuestos);
				console.log('Importe : '+importe+ 'Impuestos:'+impuestos+' Total:'+total);
				$('#impuestos').autoNumeric('set',impuestos);
				$('#total').autoNumeric('set',total);
			});

			$("#moneda").change(function(){
				var date=$("#fecha").val();
				var selected=$(this).val();
				if(selected=="MXN"){
					$("#tc").autoNumeric('set',1.000);
				}else{
					if(!date.isBlank()){

						$.post(
							"${createLink(controller:'tipoDeCambio', action:'ajaxTipoDeCambioDiaAnterior')}",
							{fecha:date}
						).done(function(data){
							if(data.factor!=null){
								$("#tc").val(data.factor);
								console.log('Tipo de cambio: '+data.factor);
							}else if(data.error!=null){
								alert(data.error);
							}
						}).fail(function( jqXHR, textStatus, errorThrown){
							aler('Error en servicio: '+errorThrown);
						});
						console.log('Fecha seleccionada: '+date);
						
					}
				};
				
			});
		});
	</script>

</content>		
 	

 	
		
</body>


</html>
