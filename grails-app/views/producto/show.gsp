
<%@ page import="com.luxsoft.impapx.Producto" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<g:set var="entityName" value="${message(code: 'producto.label', default: 'Producto')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="container">
			
			<div class="row row-header">
				<div class="col-md-8 col-sm-offset-2 toolbar-panel">
					<div class="btn-group">
					    <lx:backButton/>
					    <lx:createButton/>
					    <lx:editButton id="${productoInstance?.id}"/>
					    <lx:printButton/>
					</div>
				</div>
			</div> <!-- End .row 1 -->
			<div class="row">
			    <div class="col-md-8 col-sm-offset-2">
					<div class="panel panel-primary">
						<div class="panel-heading"> ${entityName}: ${productoInstance} </div>
						<g:if test="${flash.message}">
							<small><span class="label label-info ">${flash.message}</span></small>
						</g:if> 
					  	<div class="panel-body">
					  		<g:form class="form-horizontal">
					  			
					  					
					  			<f:display property="clave" bean="productoInstance"/>
					  					
					  			
					  					
					  			<f:display property="descripcion" bean="productoInstance"/>
					  					
					  			
					  					
					  			<f:display property="unidad" bean="productoInstance"/>
					  					
					  			
					  					
					  			<f:display property="linea" bean="productoInstance"/>
					  					
					  			
					  					
					  			<f:display property="marca" bean="productoInstance"/>
					  					
					  			
					  					
					  			<f:display property="clase" bean="productoInstance"/>
					  					
					  			
					  					
					  			<f:display property="acabado" bean="productoInstance"/>
					  					
					  			
					  					
					  			<f:display property="color" bean="productoInstance"/>
					  					
					  			
					  					
					  			<f:display property="ancho" bean="productoInstance"/>
					  					
					  			
					  					
					  			<f:display property="calibre" bean="productoInstance"/>
					  					
					  			
					  					
					  			<f:display property="caras" bean="productoInstance"/>
					  					
					  			
					  					
								<f:display property="dateCreated" bean="productoInstance" widget="datetime"/>
					  					
					  			
					  					
					  			<f:display property="gramos" bean="productoInstance"/>
					  					
					  			
					  					
					  			<f:display property="kilos" bean="productoInstance"/>
					  					
					  			
					  					
					  			<f:display property="largo" bean="productoInstance"/>
					  					
					  			
					  					
					  			<f:display property="lastUpdated" bean="productoInstance"/>
					  					
					  			
					  					
					  			<f:display property="m2" bean="productoInstance"/>
					  					
					  			
					  					
					  			<f:display property="precioContado" bean="productoInstance"/>
					  					
					  			
					  					
					  			<f:display property="precioCredito" bean="productoInstance"/>
					  					
					  			
					  		</g:form>
					  </div>
					</div>
			    </div>
			</div><!-- End .row 2 -->

		</div><!-- End container -->

	</body>
</html>


