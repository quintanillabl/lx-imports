<%@ page import="com.luxsoft.impapx.tesoreria.Cheque" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="taskView">
<title><g:message code="comision.list.label" default="Impresión  de cheque"/></title>
<r:require module="autoNumeric"/>
</head>
<body>
	
	<content tag="header">
		<h3>Impresión de cheque</h3>
 	</content>
	<content tag="consultas">
	
		<li><g:link class="list" action="list">
			<i class="icon-list"></i>
			Cheques
			</g:link>
		</li>
	</content>
	
 	<content tag="operaciones">
 		<li><g:link  action="create"><i class="icon-plus "></i> Nuevo cheque</g:link></li>
 	</content>
 	
 	<content tag="document">
 		<g:render template="/shared/messagePanel" model="[beanInstance:chequeInstance]"/>

		<fieldset>
					<g:form class="form-horizontal" action="create" >
						<fieldset>
							<f:with bean="chequeInstance">
								<f:field property="egreso" label="Pago"/>
								<f:field property="folio" input-id="folio" />
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
 	$("#folio").autoNumeric({vMin:'1',vMax:'10000',aSep: ''});
 	
 });
 </r:script>
	
</body>
</html>
