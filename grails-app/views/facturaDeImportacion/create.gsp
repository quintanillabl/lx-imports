<%@ page import="com.luxsoft.impapx.FacturaDeImportacion" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="createForm">
	<g:set var="entityName" value="${message(code: 'facturaDeImportacion.label', default: 'FacturaDeImportacion')}" scope="request"/>
	<g:set var="entity" value="${facturaDeImportacionInstance}" scope="request" />
	<title>Alta de factura de importación</title>
</head>
<body>
	

<content tag="formFields">
	<f:with bean="${entity}" >
		<f:field property="proveedor" wrapper="bootstrap3" widget-required="true"/>
		<f:field property="documento" widget-class="form-control" wrapper="bootstrap3"/>
		<f:field property="fecha" wrapper="bootstrap3"/>
		<f:field property="vencimiento" wrapper="bootstrap3"/>
		<f:field property="moneda" wrapper="bootstrap3"/>
		<f:field property="tc" widget="tc" wrapper="bootstrap3"/>
		<f:field property="importe" widget="money"  widget-required="true" wrapper="bootstrap3"/>
		<f:field property="descuentos" widget="money" wrapper="bootstrap3"/>
		<f:field property="subTotal" widget="money" wrapper="bootstrap3"/>
		<f:field property="impuestos" widget="money" wrapper="bootstrap3" />
		<f:field property="total"  widget="money" wrapper="bootstrap3"/>
		<f:field property="comentario"  widget-class="form-control" wrapper="bootstrap3"/>
	</f:with>

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
				
				
				// $.ajax({
				// 	url:"${createLink(controller:'tipoDeCambio', action:'ajaxTipoDeCambioDiaAnterior')}",
				// 	success:function(response){
				// 		console.log('OK: '+response);
				// 		if(response!=null){
				// 			if(response.factor!=null){
				// 				$("#tc").val(response.factor);
				// 				console.log('Tipo de cambio: '+response.factor);
				// 			}else if(response.error!=null){
				// 				alert(response.error);
				// 			}
				// 		}
				// 	},
				// 	data:{
				// 		fecha:date
				// 	},
				// 	error:function(request,status,error){
				// 		alert("Error: "+status);
				// 	}
				// });
				
			});
		});
	</script>
</content>

<content tag="customScript">
	
</content>

</body>
</html>
