<%@ page import="com.luxsoft.impapx.Requisicion" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="taskView">
<title><g:message code="requisicion.create.label" default="Alta de requisición"/></title>
<r:require module="autoNumeric"/>
</head>
<body>
	
	<content tag="header">
		<h3>Alta de Requisición</h3>
 	</content>
	<content tag="consultas">
	
		<li><g:link class="list" action="list">
			<i class="icon-list"></i>
			Requisiciones
			</g:link>
		</li>
	</content>
	
 	<content tag="operaciones">
 		<li><g:link  action="create">Alta de requisición</g:link></li>
 	</content>
 	
 	<content tag="document">
 		<g:render template="/shared/messagePanel" model="[beanInstance:requisicionInstance]"/>

		<fieldset>
	<g:form class="form-horizontal" action="create"
		id="${requisicionInstance?.id}">
		<g:hiddenField name="version" value="${requisicionInstance?.version}" />
		<fieldset>
			<f:with bean="requisicionInstance">
				<f:field property="proveedor"/>
				<f:field property="concepto" />
				<f:field property="fecha" />
				<f:field property="fechaDelPago" />
				<f:field property="formaDePago" />
				<f:field property="moneda" />
				<f:field property="tc" input-class="tcField" />
				<%-- <f:field property="total" input-class="tcField"/>--%>
				<f:field property="descuentoFinanciero" input-class="porcentField" />
				
				
				<f:field property="comentario" input-class="comentario" />
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
 	$(".tcField").autoNumeric({vMin:'0.000000'});
 	$(".porcentField").autoNumeric({vMax:'100.000000',vMin:'0.0000000'});
 	//$("#total").autoNumericSet(0.0).attr("disabled",'disabled');
 	
 	$('#concepto').bind('change',function(e){
		var selected=$(this).val();
		if(selected=="ANTICIPO"){
			$("#embarqueAuto").removeAttr("disabled").attr("required","true");
		}else{
			$("#embarqueAuto")
			.attr("disabled",'disabled')
			.removeAttr("required");
		}
	});
	/*
	$("#embarqueAuto").autocomplete(
		{			
			source:'<g:createLink controller="requisicion" action="embarquesDisponiblesJSONList"/>',
			minLength:1,
			select:function(e,ui){
				console.log('Valor seleccionado: '+ui.item.id);
				$("#embarqueId").val(ui.item.id);
			}
		});*/
 	
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
