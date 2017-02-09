
<%@ page import="com.luxsoft.impapx.cxp.Aplicacion" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<g:set var="entityName" value="${message(code: 'aplicacion.label', default: 'Aplicacion')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="container">
			
			<div class="row row-header">
				<div class="col-md-8 col-sm-offset-2 toolbar-panel">
					<div class="btn-group">
					    <lx:backButton/>
					    <lx:createButton/>
					    <lx:editButton id="${aplicacionInstance?.id}"/>
					    <lx:printButton/>
					</div>
				</div>
			</div> <!-- End .row 1 -->
			<div class="row">
			    <div class="col-md-8 col-sm-offset-2">
					<div class="panel panel-primary">
						<div class="panel-heading"> ${entityName}: ${aplicacionInstance} </div>
						<g:if test="${flash.message}">
							<small><span class="label label-info ">${flash.message}</span></small>
						</g:if> 
					  	<div class="panel-body">
					  		<g:form class="form-horizontal">
					  			
					  					
					  			<f:display property="importe" bean="aplicacionInstance"/>
					  					
					  			
					  					
					  			<f:display property="impuesto" bean="aplicacionInstance"/>
					  					
					  			
					  					
					  			<f:display property="total" bean="aplicacionInstance"/>
					  					
					  			
					  					
					  			<f:display property="impuestoTasa" bean="aplicacionInstance"/>
					  					
					  			
					  					
					  			<f:display property="comentario" bean="aplicacionInstance"/>
					  					
					  			
					  					
					  			<f:display property="abono" bean="aplicacionInstance"/>
					  					
					  			
					  					
								<f:display property="dateCreated" bean="aplicacionInstance" widget="datetime"/>
					  					
					  			
					  					
					  			<f:display property="factura" bean="aplicacionInstance"/>
					  					
					  			
					  					
					  			<f:display property="fecha" bean="aplicacionInstance"/>
					  					
					  			
					  					
					  			<f:display property="lastUpdated" bean="aplicacionInstance"/>
					  					
					  			
					  		</g:form>
					  </div>
					</div>
			    </div>
			</div><!-- End .row 2 -->

		</div><!-- End container -->

	</body>
</html>


