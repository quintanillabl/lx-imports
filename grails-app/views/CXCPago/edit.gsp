<%@ page import="com.luxsoft.impapx.cxc.CXCPago" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="taskView">
		<title>Alta de pago</title>
		<r:require module="autoNumeric"/>
	</head>
	<body>
	
	<content tag="header">
		<div class="alert">
			<h3><strong>
				Pago: ${CXCPagoInstance.id}  ${CXCPagoInstance.cliente.nombre} 
				Disponible: <lx:moneyFormat number="${CXCPagoInstance.disponibleMN}"/> 
			</strong></h3>
		</div>
 	</content>
	<content tag="consultas">
	
		<li><g:link class="list" action="list">
			<i class="icon-list"></i>
			Pagos registrados
			</g:link>
		</li>
	</content>
	
 	<content tag="operaciones">
 		<li><g:link  action="create"><i class="icon-plus "></i> Alta de pago</g:link></li>
 	</content>
 	
 	<content tag="document">
 		<g:render template="/shared/messagePanel" model="[beanInstance:CXCPagoInstance]"/>
 		<ul class="nav nav-tabs" id="myTab">
			<li class=""><a href="#abonoPanel" data-toggle="tab">Pago</a></li>
			<li class="active"><a href="#aplicacionesPanel" data-toggle="tab">Aplicaciones</a></li>
		</ul>
		
		<div class="tab-content">
			<div class="tab-pane " id="abonoPanel">
			
			<fieldset>
			<g:form class="form-horizontal" action="#" id="${CXCPagoInstance?.id}">
				<fieldset>
				<f:with bean="CXCPagoInstance">
					<g:hiddenField name="version" value="${CXCPagoInstance?.version}" />
					<g:if test="${!CXCPagoInstance.aplicaciones}">
						<f:field property="cliente"/>
					</g:if>
					<f:field property="fecha">
						<lx:shortDate date="${value}"/>
					</f:field>
					<f:field property="formaDePago" input-readOnly="true">
						<g:textField name="formaDePago" value="${value }" readOnly="true"/>
					</f:field>
					<f:field property="moneda" input-readOnly="true">
						<g:textField name="moneda" value="${value }" readOnly="true"/>
					</f:field>
					<f:field property="tc" input-class="tc" input-readOnly="true"/>
					<f:field property="total" input-class="moneyField" input-readOnly="true"/>
					<f:field property="fechaBancaria" >
						<lx:shortDate date="${value}"/>
					</f:field>
					<f:field property="referenciaBancaria" input-readOnly="true"/>
					<f:field property="comentario" input-class="input-xxlarge" input-readOnly="true"/>
				</f:with>
				<div class="form-actions">
					<%--
					<button type="submit" class="btn btn-primary">
						<i class="icon-ok icon-white"></i>
						<g:message code="default.button.update.label" default="Update" />
					</button>
					<button type="submit" class="btn btn-danger" name="_action_delete" formnovalidate>
						<i class="icon-trash icon-white"></i>
						<g:message code="default.button.delete.label" default="Delete" />
					</button>
					 --%>
				</div>
				</fieldset>
			</g:form>
			</fieldset>
			
			</div>
			<div class="tab-pane active" id="aplicacionesPanel">
				<g:render template="aplicacionesPanel" 
					model="[CXCAbonoInstance:CXCPagoInstance,aplicaciones:CXCPagoInstance.aplicaciones]"/> 
			</div>
		</div>

		
		
 	</content>
 	
 <r:script>
 $(function(){
 	$(".moneyField").autoNumeric({vMin:'0.00',wEmpty:'zero'});
 	$(".tcField").autoNumeric({vMin:'0.0000'});
 	
 });
 </r:script>
	
	
	</body>
</html>

