<%@ page import="com.luxsoft.impapx.cxc.CXCNota" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="taskView">
		<title>Alta de nota</title>
		<r:require module="autoNumeric"/>
	</head>
	<body>
	
	<content tag="header">
		<h3>Alta de Nota de Crédito</h3>
 	</content>
	<content tag="consultas">
	
		<li><g:link class="list" action="list">
			<i class="icon-list"></i>
			Notas registradas
			</g:link>
		</li>
	</content>
	
 	<content tag="operaciones">
 		<li><g:link  action="create"><i class="icon-plus "></i> Alta de nota</g:link></li>
 	</content>
 	
 	<content tag="document">
 		<g:render template="/shared/messagePanel" model="[beanInstance:CXCNotaInstance]"/>

		<fieldset>
			<g:form class="form-horizontal" action="create" >
				<fieldset>
				<f:with bean="CXCNotaInstance">
					<f:field property="cliente"/>
					<f:field property="fecha"/>
					<f:field property="tipo"/>
					<f:field property="moneda"/>
					<f:field property="tc" />
					<f:field property="descuento"/>
					<f:field property="importe" input-class="moneyField"/>
					
					<f:field property="comentario" input-class="input-xxlarge"/>
				</f:with>
				<div class="form-actions">
					<button type="submit" class="btn btn-primary">
						<i class="icon-ok icon-white"></i>
						<g:message code="default.button.create.label" default="Create" />
					</button>
				</div>
				</fieldset>
			</g:form>
		</fieldset>
		
 	</content>
 	
 <r:script>
 $(function(){
 	$("#importe").autoNumeric({vMin:'0.00'}).attr("disabled",'disabled');
 	$("#tc").autoNumeric({vMin:'0.0000'});
 	$("#descuento").autoNumeric({aSign: ' %', pSign: 's', vMin:'0.00',vMax: '100.00',wEmpty:'zero'} );
 	$("#tipo").change(function(){
 		var tipo=$(this).val();
 		
 		if(tipo=='DESCUENTO'){
 			$("#descuento").removeAttr('disabled');
 		}else{
 			$("#descuento").autoNumericSet('0.0').attr("disabled",'disabled');
 		}
 		
 		if(tipo=='BONIFICACION'){
 			$("#importe").removeAttr('disabled');
 		}else{
 			$("#importe").autoNumericSet('0.0').attr('disabled','disabled');
 			
 			
 		}
 	});
 	
 	$("#moneda").val('MXN').change(function(){
 		var mon=$(this).val();
 		if(mon=='MXN'){
 			$("#tc").autoNumericSet('1.0').attr("readonly",true);
 		}else{
 			$("#tc").attr("readonly",false);
 		}
 	});
 	
 	$("#tc").autoNumericSet('1.0').attr("disabled",'disabled');
 	
 });
 </r:script>
	
	
	</body>
</html>

