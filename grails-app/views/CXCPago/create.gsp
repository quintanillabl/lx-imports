<%@ page import="com.luxsoft.impapx.cxc.CXCPago" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="taskView">
		<title>Alta de cobro</title>
		<r:require module="autoNumeric"/>
	</head>
	<body>
	
	<content tag="header">
		<h3>Alta de Cobro</h3>
 	</content>
	<content tag="consultas">
	
		<li><g:link class="list" action="list">
			<i class="icon-list"></i>
			Cobros registrados
			</g:link>
		</li>
	</content>
	
 	<content tag="operaciones">
 		<li><g:link  action="create"><i class="icon-plus "></i> Alta de pago</g:link></li>
 	</content>
 	
 	<content tag="document">
 		<g:render template="/shared/messagePanel" model="[beanInstance:CXCPagoInstance]"/>

		<fieldset>
			<g:form class="form-horizontal" action="create" >
				<fieldset>
				<f:with bean="CXCPagoInstance">
					<f:field property="cliente"/>
					<f:field input-id="fecha" property="fecha"/>
					<f:field property="formaDePago"/>
					<f:field field-id="cuenta" property="cuenta" label="Cuenta destino"/>
					<f:field field-id="tc" property="tc" input-class="tc"/>
					<f:field property="total" input-class="moneyField"/>
					<f:field property="fechaBancaria"/>
					<f:field property="referenciaBancaria" />
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
 	$(".moneyField").autoNumeric({vMin:'0.00',wEmpty:'zero'});
 	$(".tcField").autoNumeric({vMin:'0.0000'});
 	
 	$("#total2").blur(function(){
		
		var total=$(this).autoNumericGet();
		
		
	});
	
	$("#cuenta").change(function(){
		var date=$("#fecha").val();
		$.ajax({
			url:"${createLink(controller:'tipoDeCambio', action:'ajaxTipoDeCambioDiaAnterior')}",
			success:function(response){
				console.log('OK: '+response);
				if(response!=null){
					if(response.factor!=null){
						$("#tc").val(response.factor);
						console.log('Tipo de cambio: '+response.factor);
					}else if(response.error!=null){
						alert(response.error);
					}
				}
			},
			data:{
				fecha:date
			},
			error:function(request,status,error){
				alert("Error: "+status);
			}
		});
		
	});
	
 });
 </r:script>
	
	
	</body>
</html>
