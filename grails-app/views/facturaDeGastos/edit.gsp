<!doctype html>
<html>
<head>
	<meta name="layout" content="luxor">
	<title>Facturas de gastos ${facturaDeGastosInstance.id}</title>
	<asset:javascript src="forms/forms.js"/>
</head>
<body>

<content tag="header">
	Factura de gastos Id: ${facturaDeGastosInstance.id}
</content>

<content tag="subHeader">
	<ol class="breadcrumb">
		<li><g:link action="index">Gastos</g:link></li>
		<li><g:link action="create">Alta</g:link></li>
		<li><g:link action="edit" id="${facturaDeGastosInstance.id}"><strong>Edición</strong></g:link></li>
	</ol>
</content>

<content tag="document">
	<div class="ibox float-e-margins">
		<div class="ibox-title"></div>
	    <div class="ibox-content">
			<ul class="nav nav-tabs" id="mainTab">
				<li class="active" ><a id="conceptosBtn" href="#conceptosPanel" data-toggle="tab">Conceptos</a></li>
				<li ><a href="#facturasPanel" data-toggle="tab">Propiedades</a></li>
				<li><a href="#cuentaPanel" data-toggle="tab">CxP</a></li>
			</ul>
			
			<div class="tab-content">
					<div class="tab-pane fade in " id="facturasPanel">
						<g:render template="editForm"/>
					</div>
					
					<div class="tab-pane fade in active" id="conceptosPanel">
						<g:render template="conceptosGrid"/>
						<g:render template="conceptoCreateDialog"/>
					</div>
					
					<div class="tab-pane fade in" id="cuentaPanel">
						<div class="row">
							<div class="col-md-10">
								<form class="form-horizontal">
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
								</form>
							</div>
						</div>
						
					</div>
				</div>		

	    </div>
	</div>
	
</content>

 	
 	
	
</body>
</html>
