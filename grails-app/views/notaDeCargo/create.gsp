<%@ page import="com.luxsoft.impapx.Venta" %>
<%@ page import="com.luxsoft.cfdix.v32.V32CfdiUtils" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="createForm">
	<g:set var="entityName" value="${'Nota de cargo'}" scope="request"/>
	<g:set var="entity" value="${ventaInstance}" scope="request" />
	<title>Alta de nota de cargo</title>
</head>
<body>
	

<content tag="formFields">

	<f:with bean="ventaInstance" >
		
		<g:hiddenField name="tipo" value="${'NOTA_DE_CARGO' }"/>
		<g:hiddenField name="cuentaDePago" value="${'0000' }"/>
		<g:hiddenField name="clase" value="${'NOTA_DE_CARGO' }"/>
		<f:field property="cliente" wrapper="bootstrap3"/>
		<f:field property="fecha" wrapper="bootstrap3"/>
		<f:field property="moneda" wrapper="bootstrap3"/>
		<f:field property="tasaCetes" wrapper="bootstrap3"/>
		<f:field property="tc" widget="tc" widget-disabled="true" wrapper="bootstrap3"/>
		<f:field property="formaDePago" wrapper="bootstrap3">
			<g:select class="form-control chosen-select"  
				name="${property}" 
				value="${value}"
				from="${['CHEQUE','TRANSFERENCIA','EFECTIVO','TARJETA','DEPOSITO']}"/>
			
		</f:field>
		%{-- <f:display property="importe" wrapper="bootstrap3" widget="money"/>
		<f:display property="impuestos" wrapper="bootstrap3" widget="money"/>
		<f:display property="total" wrapper="bootstrap3" widget="money"/> --}%
		<f:field property="usoCfdi" wrapper="bootstrap3" widget-class="form-control">
			%{-- <g:select class="form-control chosen-select"  
			 	name="usoCfdi" 
			 	from="${['G01','G02']}" 
			 /> --}%
			 <g:select class="form-control chosen-select"  
				name="${property}" 
				value="${'G02'}"
				from="${V32CfdiUtils.getUsosDeCfdi()}" 
				optionKey="clave" 
				optionValue="descripcion"
				required='required'
			/>
		</f:field>
		<f:field property="comentario" wrapper="bootstrap3" widget-class="form-control"/>
		
		
		
	</f:with>

	<script type="text/javascript">
	 $(function(){
	 	$("#tc").attr("disabled",true).val(1.0);
	 	$('#moneda').on('change',function(){
	 		var selected=$(this).val();
	 		if(selected=="MXN"){
	 			$("#tc").attr("disabled",true).val(1.0);
	 		}else
	 			$("#tc").attr("disabled",false);
	 	});
	 	

	 	// $("#cuentaDePago").mask("9999");
	 	// $("#fecha").mask("99/99/9999");
	 	
	 	// $('#importe').autoNumeric();
	 	// $('#impuestos').autoNumeric();
	 	// $('#total').autoNumeric();
	 	/*	
	 	$('#importe').blur(function(){
	 		var importe=$(this).autoNumeric('get');
	 		var impuestos=importe*.16;
	 		var total=importe*1.16;
	 		$('#impuestos').autoNumeric('set',impuestos);
	 		$('#total').autoNumeric('set',total);
	 	});
		*/
	 });
	</script>

</content>

</body>
</html>



