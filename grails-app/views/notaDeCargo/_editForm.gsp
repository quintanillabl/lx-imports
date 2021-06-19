<%@ page import="com.luxsoft.impapx.Venta"%>
<%@ page import="com.luxsoft.cfdi.Cfdi"%>
<%@ page import="com.luxsoft.cfdix.v32.V32CfdiUtils" %>
<g:set var="mnEnabled" value="${ventaInstance.moneda.currencyCode=='MXN'}"/>
<div class="row">
	<div class="col-md-8">
		<g:form name="updateForm" class="form-horizontal" action="update" method="PUT">
			<g:hiddenField name="id" value="${ventaInstance.id}"/>
			<g:hiddenField name="version" value="${ventaInstance.version}"/>
			<f:with bean="ventaInstance" >
				<f:display property="cliente" wrapper="bootstrap3"/>
				<f:display property="clase" wrapper="bootstrap3" widget-class="form-control"/>
				<f:display property="moneda" wrapper="bootstrap3"/>
				<f:display property="tc" widget="tc" widget-disabled="true" wrapper="bootstrap3"/>
				<f:display property="tasaCetes" wrapper="bootstrap3"/>

				<g:if test="${ventaInstance.cfdi}">
					<f:display property="fecha" wrapper="bootstrap3"/>
					<f:display property="formaDePago" wrapper="bootstrap3"/>
					<f:display property="importe" wrapper="bootstrap3" widget="money"/>
					<f:display property="impuestos" wrapper="bootstrap3" widget="money"/>
					<f:display property="total" wrapper="bootstrap3" widget="money"/>
					
					<f:display property="comentario" wrapper="bootstrap3"/>
				</g:if>
				<g:else>
					<f:field property="fecha" wrapper="bootstrap3"/>
						<f:field property="formaDePago" wrapper="bootstrap3">
							<g:select class="form-control "  
								name="${property}" 
								value="${value}"
								from="${['CHEQUE','TRANSFERENCIA','EFECTIVO','TARJETA','DEPOSITO']}"/>
							
					</f:field>
					<f:display property="importe" wrapper="bootstrap3" widget="money"/>
					<f:display property="impuestos" wrapper="bootstrap3" widget="money"/>
					<f:display property="total" wrapper="bootstrap3" widget="money"/>
					<f:field property="usoCfdi" wrapper="bootstrap3" widget-class="form-control">
						%{-- <g:select class="form-control chosen-select"  
						 	name="usoCfdi" 
						 	from="${['G01','G02']}" 
						 /> --}%
						 <g:select class="form-control chosen-select"  
							name="${property}" 
							value="${value}"
							from="${V32CfdiUtils.getUsosDeCfdi()}" 
							optionKey="clave" 
							optionValue="descripcion"
							required='required'
						/>
					</f:field>
					<f:field property="comentario" wrapper="bootstrap3" widget-class="form-control"/>
				</g:else>
				
			</f:with>
			<div class="col-md-offset-3 col-md-6">
				<div class="form-group">
					<button type="submit" class="btn btn-primary">
						<i class="icon-ok icon-white"></i>
						<g:message code="default.button.update.label" default="Actualizar" />
					</button>
					<g:if test="${ventaInstance.getCfdi()}" >
						<g:link controller="cfdi" action="show" class="btn  btn-success" id="${cfdi?.id}">
							CFDI :${cfdi.id}   UUID: ${cfdi.uuid?:'Por Timbrar' }
						</g:link>
					</g:if>
					<g:else>
						<g:link  controller="cfdi" action="facturar" class="btn btn-info" 
							onclick="return confirm('Facturar venta: ${ventaInstance.id}');"
							id="${ventaInstance.id}">
	  		 				Facturar
	  					</g:link>
	  					
	  					<a href="" class="btn btn-danger " data-toggle="modal" data-target="#deleteDialog"><i class="fa fa-trash"></i> Eliminar</a> 
	  					
					</g:else>
				</div>
			</div>
			
		</g:form>
	</div>
	<g:render template="/common/deleteDialog" bean="${ventaInstance}"/>
</div>



<%--
<r:script>
$(function(){
	//var mon=$("#moneda").val();
	//$(#moneda).attr('disabled',mon==""MXN);
	
	$('#moneda').bind('change',function(e){
		var selected=$(this).val();
		if(selected=="MXN"){
			$("#tc").attr("disabled",true).val(1.0);
		}else
			$("#tc").attr("disabled",false);
		
	});
	$("#cuentaDePago").mask("9999");
	$("#fecha").mask("99/99/9999");
	
});
</r:script>
--%>