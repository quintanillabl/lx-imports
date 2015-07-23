<%@ page import="com.luxsoft.impapx.FacturaDeImportacion" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="cxp">
	<title>Facturas de importación</title>
</head>
<body>

 	<content tag="header">
 		<form name="updateForm" action="update" class="form-horizontal" method="PUT">	
 		<div class="panel panel-primary">
 			<div class="panel-heading">
 				Factura de importación Id: ${facturaDeImportacionInstance.id}
 			</div>
			<ul class="nav nav-tabs" id="mainTab">
				<li class="active" ><a href="#facturasPanel" data-toggle="tab">Factura</a></li>
				<li><a href="#embarquesPanel" data-toggle="tab">Embarques</a></li>
				<li><a href="#contenedoresPanel" data-toggle="tab">Contenedores</a></li>
			</ul>
 		  	
 			<div class="panel-body ">
 				<g:hasErrors bean="${facturaDeImportacionInstance}">
 					<div class="alert alert-danger">
 						<ul class="errors" >
 							<g:renderErrors bean="${facturaDeImportacionInstance}" as="list" />
 						</ul>
 					</div>
 				</g:hasErrors>
				<%-- Tab Content --%>
				<div class="tab-content">
					<div class="tab-pane fade in active" id="facturasPanel">
						
						<legend>  <span id="conceptoLabel">Propiedades</span></legend> 
						<f:with bean="facturaDeImportacionInstance">
						<div class="col-md-6">
							<f:display property="proveedor" widget-class="form-control" 
								wrapper="bootstrap3" widget-required="true"/>
							<f:display property="fecha" wrapper="bootstrap3" widget-required="true"/>
							<f:display property="vencimiento" wrapper="bootstrap3"  />
							<f:display property="moneda" wrapper="bootstrap3"/>
							<f:display property="tc" widget-class="form-control" wrapper="bootstrap3"/>
							<f:display property="documento" widget-class="form-control" wrapper="bootstrap3"/>
							<f:display property="comentario" widget-class="form-control" wrapper="bootstrap3"/>
						</div>
						<div class="col-md-6">
							<f:display property="importe" widget="money" wrapper="bootstrap3" />
							<f:display property="subTotal" widget="money" wrapper="bootstrap3"/>
							<f:display property="descuentos" widget="money" wrapper="bootstrap3"/>
							<f:display property="impuestos" widget="money" wrapper="bootstrap3"/>
							<f:display property="total" widget="money" wrapper="bootstrap3"/>
							
						</div>
						</f:with>
					</div>
					<div class="tab-pane fade in" id="embarquesPanel">
						PENDIENTE
					</div>
					<div class="tab-pane fade in" id="contenedoresPanel">
						CONTENEDORES PENDIENTES
					</div>
				</div>		
 			
 			</div>					
 		 
 			<div class="panel-footer">
 				<div class="btn-group">
 					<g:link class="btn btn-default " action="index"  >
 						<i class="fa fa-step-backward"></i> Facturas
 					</g:link>
 					<lx:editButton id="${facturaDeImportacionInstance.id}"/>
 				</div>
 			</div><!-- end .panel-footer -->

 		</div>
 		</form>
 	</content>

 	<conten tag="script">
 		
 	</conten>
	
	
</body>
</html>
