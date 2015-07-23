<%@ page import="com.luxsoft.impapx.RequisicionDet" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="taskView">
<title><g:message code="requisicionDet.create.label" default="Detalle de requisición"/></title>
<r:require module="autoNumeric"/>
</head>
<body>
	
	<content tag="header">
		<div class="alert">
				<g:link controller="requisicion" id="${requisicionInstance?.id}" action="edit">
					<h4><strong>Req: ${requisicionInstance?.id} (${requisicionInstance?.proveedor?.nombre})</strong></h4>
				</g:link>
				
		</div>
	<content tag="consultas">
	</content>
	
 	<content tag="operaciones">
 		
 	</content>
 	
 	<content tag="document">
 		<g:render template="/shared/messagePanel" model="[beanInstance:requisicionDetInstance]"/>

		<fieldset>
			<g:form class="form-horizontal" action="create" controller="requisicionDet"
				id="${requisicionDetInstance?.id}">
				<fieldset>
					<f:with bean="requisicionDetInstance">
						<g:hiddenField name="requisicionId" value="${requisicionInstance?.id}"/>
						<f:field property="documento"/>
						<f:field property="fechaDocumento" />
						<f:field property="embarque" />
						<%-- <f:field property="importe" input-class="moneyField" />
						<f:field property="impuestos" input-class="moneyField"  />--%>
						<f:field property="total" input-class="moneyField" />
					</f:with>
					
			<div class="form-actions">
				<button type="submit" class="btn btn-primary">
					<i class="icon-ok icon-white"></i>
					<g:message code="default.button.create.label" default="Salvar" />
				</button>
			</div>
		</fieldset>
	</g:form>
</fieldset>
		
 	</content>
 	
 <r:script>
 $(function(){
 	$(".moneyField").autoNumeric({vMin:'0.00',wEmpty:'zero',mRound:'B'});
 	
 	//$("#total").autoNumericSet(0.0).attr("disabled",'disabled');
 	/*
 	$('#concepto').bind('change',function(e){
		var selected=$(this).val();
		if(selected=="ANTICIPO"){
			$("#total").removeAttr("disabled");
		}else{
			$("#total").attr("disabled",'disabled');
			$("#total").autoNumericSet(0.0);
		}
			
		
	});
	*/ 
 	
 });
 <%--
 	//$("#tc").autoNumericSet(1.0).attr("disabled",'disabled');
 	//$("#total").autoNumericSet(0.0).attr("disabled",'disabled');
 	$(".tcField").autoNumeric({vMin:'0.0000'});
 	$(".porcentField").autoNumeric({vMax:'100.00',vMin:'0.00'}).autoNumericSet(0.0);
 	$(".moneyField").autoNumeric({vMin:'0.00',wEmpty:'zero',mRound:'B'});
 	
 	$('#moneda').bind('change',function(e){
		var selected=$(this).val();
		if(selected=="MXN"){
			$("#tc").attr("disabled",true);//.val(1.0);
			$("#tc").autoNumericSet(1.0);
		}else
			$("#tc").removeAttr("disabled");
		
	});
	
	
 --%>
 </r:script>
	
</body>
</html>

