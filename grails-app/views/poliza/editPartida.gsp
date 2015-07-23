<%@ page import="com.luxsoft.impapx.contabilidad.Poliza" %>
<%@ page import="com.luxsoft.impapx.contabilidad.PolizaDet" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="taskView">

<g:set var="periodo" value="${session.periodoContable}"/>
<title>Mantenimiento de póliza</title>

<r:require modules="dataTables,luxorTableUtils"/>
</head>
<body>

	<content tag="header">
		<div class="alert ">
			<g:link action="edit" id="${poliza.id}">
				${poliza}
			</g:link>
		</div>
	</content>
	
 	<content tag="consultas">
 		
 	</content>
 	
 	<content tag="operaciones">
 		
 	</content>
 	
 	<content tag="document">	
 		<g:if test="${flash.message}">
			<bootstrap:alert class="alert-info">
				${flash.message}
			</bootstrap:alert>
		</g:if>
		
	<fieldset>
				<g:form class="form-horizontal" action="editPartida" id="${polizaDet.id }" >
						<fieldset>
							<f:with bean="${polizaDet}">
								<f:field property="cuenta" input-required="true"/>
								<f:field property="debe" input-required="true"/>
								<f:field property="haber" input-required="true"/>
								<f:field property="asiento" input-required="true"/>
								<f:field property="descripcion" input-required="true"/>
								<f:field property="referencia" input-required="true"/>
								
							</f:with>
							<div class="form-actions">
								<button type="submit" class="btn btn-primary">
									<i class="icon-ok icon-white"></i>
									Actualizar
								</button>
							</div>
						</fieldset>
					</g:form>
				</fieldset>	
			
			
		
		
	</content>
<r:script>
$(function(){
	
	
	
});

</r:script>			
</body>
</html>



