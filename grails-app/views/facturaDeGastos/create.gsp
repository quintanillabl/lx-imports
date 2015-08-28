<%@ page import="com.luxsoft.impapx.GastosDeImportacion" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="createForm">
	<g:set var="entityName" value="${message(code: 'facturaDeGastos.label', default: 'Factura de Gastos')}" scope="request"/>
	<g:set var="entity" value="${facturaDeGastosInstance}" scope="request" />
	<title>Alta de gasto</title>
	
</head>
<body>

<content tag="header">
	Registro de factura (Factura de gastos)
</content>

<content tag="formFields">
	<f:with bean="entity">
		<f:field property="proveedor" widget-class="form-control" 
			wrapper="bootstrap3" widget-required="true"/>
		<f:field property="documento" widget-class="form-control" wrapper="bootstrap3"/>
		<f:field property="fecha" wrapper="bootstrap3" widget-required="true"/>
		<f:field property="vencimiento" wrapper="bootstrap3"  />
		<f:display property="moneda" wrapper="bootstrap3"/>
		<f:display property="tc" widget-class="form-control tc" wrapper="bootstrap3" widget-type="text"/>
		<f:field property="comentario" widget-class="form-control" wrapper="bootstrap3"/>
	</f:with>

	<%--
	<script type="text/javascript">
		$(function(){
			$("#moneda").change(function(){
				var date=$("#fecha").val();
				var moneda=$(this).val();
				if(moneda==='USD' && date){
					console.log('Buscando tipo de cambio moneda: '+moneda+' Fecha: '+date);
					$.get("${createLink(controller:'tipoDeCambio', action:'ajaxTipoDeCambioDiaAnterior')}",
						{fecha:date}
						).done(function(data){
							if(data.error){
								alert(data.error);
							}
							else{
								var factor=data.factor;
								$("#tc").autoNumeric('set',factor);
							}
						}).fail(function(){
							alert("Error");
						});
				}
			});
			 		 			
			$('#totalesPanel').on('blur','input',function(){
				var importe=$(this).autoNumeric('get');
				actualizarTotal();
			});
			var actualizarTotal=function(){
			 		 				
				var importe=$('#importe').autoNumeric('get');
				var descuentos=$('#descuentos').autoNumeric('get');
				
				var subTotal=importe-descuentos;
				$('#subTotal').autoNumeric('set',subTotal.round(2));

				var iva=$("#tasaDeImpuesto").autoNumeric('get');

				var impuestos=(iva/100.00)*subTotal;
				$("#impuestos").autoNumeric('set',impuestos);

			var retImp=$('#retImp').autoNumeric('get');
				
				var total=subTotal+impuestos-retImp
				$('#total').autoNumeric('set',total);

			};
			
		});
	</script>
	--%>

</content>


 	

</body>
</html>
	

