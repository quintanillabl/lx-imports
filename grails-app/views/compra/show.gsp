
<%@ page import="com.luxsoft.impapx.Compra" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<g:set var="entityName" value="${message(code: 'compra.label', default: 'Compra')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="container">
			
			<div class="row row-header">
				<div class="col-md-8 col-sm-offset-2 toolbar-panel">
					<div class="btn-group">
					    <lx:backButton/>
					    <lx:createButton/>
					    <lx:editButton id="${compraInstance?.id}"/>
					    <lx:printButton/>
					</div>
				</div>
			</div> <!-- End .row 1 -->
			<div class="row">
			    <div class="col-md-8 col-sm-offset-2">
					<div class="panel panel-primary">
						<div class="panel-heading"> ${entityName}: ${compraInstance} </div>
						<g:if test="${flash.message}">
							<small><span class="label label-info ">${flash.message}</span></small>
						</g:if> 
					  	<div class="panel-body">
					  		<g:form class="form-horizontal">
					  			
					  					
					  			<f:display property="proveedor" bean="compraInstance"/>
					  					
					  			
					  					
					  			<f:display property="fecha" bean="compraInstance"/>
					  					
					  			
					  					
					  			<f:display property="entrega" bean="compraInstance"/>
					  					
					  			
					  					
					  			<f:display property="depuracion" bean="compraInstance"/>
					  					
					  			
					  					
					  			<f:display property="comentario" bean="compraInstance"/>
					  					
					  			
					  					
					  			<f:display property="moneda" bean="compraInstance"/>
					  					
					  			
					  					
					  			<f:display property="tc" bean="compraInstance"/>
					  					
					  			
					  					
					  			<f:display property="folio" bean="compraInstance"/>
					  					
					  			
					  					
					  			<f:display property="origen" bean="compraInstance"/>
					  					
					  			
					  					
					  			<f:display property="importe" bean="compraInstance"/>
					  					
					  			
					  					
					  			<f:display property="descuentos" bean="compraInstance"/>
					  					
					  			
					  					
					  			<f:display property="subtotal" bean="compraInstance"/>
					  					
					  			
					  					
					  			<f:display property="impuestos" bean="compraInstance"/>
					  					
					  			
					  					
					  			<f:display property="total" bean="compraInstance"/>
					  					
					  			
					  					
								<f:display property="dateCreated" bean="compraInstance" widget="datetime"/>
					  					
					  			
					  					
					  			<f:display property="lastUpdated" bean="compraInstance"/>
					  					
					  			
					  					
					  			<f:display property="partidas" bean="compraInstance"/>
					  					
					  			
					  		</g:form>
					  </div>
					</div>
			    </div>
			</div><!-- End .row 2 -->

		</div><!-- End container -->

	</body>
</html>


