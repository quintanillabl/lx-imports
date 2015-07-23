<%@ page import="com.luxsoft.impapx.FacturaDeImportacion" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="cxp">
	<title>Facturas de importación</title>
</head>
<body>
 	

 	<content tag="header">
 		<g:form name="updateForm" action="update" class="form-horizontal" method="PUT">	
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
						<g:hiddenField name="id" value="${facturaDeImportacionInstance.id}"/>
						<g:hiddenField name="version" value="${facturaDeImportacionInstance.version}"/>
						<f:with bean="facturaDeImportacionInstance">
						<div class="col-md-6">
							<f:field property="proveedor" widget-class="form-control" 
								wrapper="bootstrap3" widget-required="true"/>
							<f:field property="fecha" wrapper="bootstrap3" widget-required="true"/>
							<f:field property="vencimiento" wrapper="bootstrap3"  />
							<f:field property="moneda" wrapper="bootstrap3"/>
							<f:field property="tc" widget-class="form-control" wrapper="bootstrap3"/>
							<f:field property="documento" widget-class="form-control" wrapper="bootstrap3"/>
							<f:field property="comentario" widget-class="form-control" wrapper="bootstrap3"/>
						</div>
						<div class="col-md-6">
							<f:field property="importe" widget-class="form-control" 
								wrapper="bootstrap3" widget-required="true"/>
							<f:field property="subTotal" widget-class="form-control" wrapper="bootstrap3"/>
							<f:field property="descuentos" widget-class="form-control" wrapper="bootstrap3"/>
							<f:field property="impuestos" widget-class="form-control" wrapper="bootstrap3"/>
							<f:field property="total" widget-class="form-control" wrapper="bootstrap3"/>
							
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
 					<button id="saveBtn" class="btn btn-success ">
 							<i class="fa fa-check"></i> Actualizar
 					</button>
 				</div>
 			</div><!-- end .panel-footer -->

 		</div>
 		</g:form>
 		 	<script type="text/javascript">
 		 		$(function(){


 		 			$('#importe').blur(function(){
 						var importe=$(this).val();
 						$('#subTotal').val(importe);
 						$('#total').val(importe);
 		 			});
 		 			$('#descuentos').blur(function(){
 		 				var importe=$('#importe').val();
 		 				var desc=$(this).val();
 		 				importe=importe-desc;
 		 				$('#subTotal').val(importe);
 		 				$('#total').val(importe);
 		 				
 		 			});
 		 		})
 		 	</script>
 	</content>

 	<conten tag="script">
 		
 	</conten>
 	
	
	
</body>
</html>
