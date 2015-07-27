<!doctype html>
<html>
<head>
	<meta name="layout" content="cxp">
	<title>Facturas de gastos</title>
</head>
<body>

 	<content tag="header">
 		<form  class="form-horizontal" >	
 		<div class="panel panel-primary">
 			<div class="panel-heading">
 				Factura de gastos Id: ${facturaDeGastosInstance.id}
 			</div>
			<ul class="nav nav-tabs" id="mainTab">
				<li class="active" ><a href="#facturasPanel" data-toggle="tab">Factura</a></li>
				<li><a href="#conceptosPanel" data-toggle="tab">Conceptos</a></li>
				<li><a href="#cuentaPanel" data-toggle="tab">CxP</a></li>
			</ul>
 		  	
 			<div class="panel-body ">
 				<g:hasErrors bean="${facturaDeGastosInstance}">
 					<div class="alert alert-danger">
 						<ul class="errors" >
 							<g:renderErrors bean="${facturaDeGastosInstance}" as="list" />
 						</ul>
 					</div>
 				</g:hasErrors>
				<%-- Tab Content --%>
				<div class="tab-content">
					<div class="tab-pane fade in active" id="facturasPanel">
						
						<legend>  <span id="conceptoLabel">Propiedades</span></legend> 
						<f:with bean="facturaDeGastosInstance">
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
							<f:display property="tasaDeImpuesto" widget="porcentaje" widget-class="form-control" wrapper="bootstrap3"/>
							<f:display property="retTasa" widget="porcentaje" widget-class="form-control" wrapper="bootstrap3"/>
							<f:display property="retImp" widget="money" wrapper="bootstrap3"/>
							<f:display property="total" widget="money" wrapper="bootstrap3"/>

							%{-- <f:display property="descuento" widget="money" wrapper="bootstrap3"/>
							<f:display property="rembolso" widget="money" wrapper="bootstrap3"/>
							<f:display property="otros" widget="money" wrapper="bootstrap3"/>
							<f:display property="pagosAplicados" widget="money" wrapper="bootstrap3"/>
							<f:display property="saldoActual" widget="money" wrapper="bootstrap3"/> --}%
							
						</div>
						</f:with>
					</div>
					
					<div class="tab-pane fade in" id="conceptosPanel">
						
						<g:render template="conceptosGrid"/>
					</div>

					
					<div class="tab-pane fade in" id="cuentaPanel">
						
						<f:with bean="facturaDeGastosInstance">
						<div class="col-md-6">
							<legend>  <span id="conceptoLabel">Cuenta por pagar</span></legend> 
							<f:display property="total" widget="money" wrapper="bootstrap3"/>
							<f:display property="descuento" widget="money" wrapper="bootstrap3"/>
							<f:display property="rembolso" widget="money" wrapper="bootstrap3"/>
							<f:display property="otros" widget="money" wrapper="bootstrap3"/>
							<f:display property="pagosAplicados" widget="money" wrapper="bootstrap3"/>
							<f:display property="saldoActual" widget="money" wrapper="bootstrap3"/>
						</div>
						<div class="col-md-6">
							<legend>  <span id="conceptoLabel">Requisición</span></legend> 
							<f:display property="fecha" wrapper="bootstrap3" widget-required="true"/>
							<f:display property="vencimiento" wrapper="bootstrap3"  />
							<f:display property="requisitado" widget="money" wrapper="bootstrap3"/>
							<f:display property="pendienteRequisitar" widget="money" wrapper="bootstrap3" label="Pendiente"/>

							
						</div>
						</f:with>
					</div>
				</div>		
 			
 			</div>					
 		 
 			<div class="panel-footer">
 				<div class="btn-group">
 					<g:link class="btn btn-default " action="index"  >
 						<i class="fa fa-step-backward"></i> Facturas
 					</g:link>
 					<g:if test="${facturaDeGastosInstance.requisitado<=0.0}">
 						<lx:editButton id="${facturaDeGastosInstance.id}"/>
 					</g:if>
 					
 					<lx:createButton />
 				</div>
 			</div><!-- end .panel-footer -->

 		</div>
 		</form>
 	</content>

 	
	
	
</body>
</html>
