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
		<div class="ibox-title">
			<g:if test="${facturaDeGastosInstance.comprobante && facturaDeGastosInstance.comprobante.total!=facturaDeGastosInstance.total}">
				<span class="label label-warning">
					Diferencia entre el total del CFDI y el total del Gasto ${facturaDeGastosInstance.total-facturaDeGastosInstance.comprobante.total}
				</span>
			</g:if>
		</div>
	    <div class="ibox-content">
			<ul class="nav nav-tabs" id="mainTab">
				<li class="active" ><a id="conceptosBtn" href="#conceptosPanel" data-toggle="tab">Conceptos</a></li>
				<li ><a href="#facturasPanel" data-toggle="tab">Propiedades</a></li>
				<li ><a href="#cfdiPanel" data-toggle="tab">CFDI</a></li>
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

					<div class="tab-pane fade in" id="cfdiPanel">
						<br>
						<g:if test="${facturaDeGastosInstance.comprobante}">
							<div class="row">
								<div class="col-md-8">
									<form class="form-horizontal" >
										<f:with bean="${facturaDeGastosInstance.comprobante}">
											<f:display property="serie" wrapper="bootstrap3"/>
											<f:display property="folio" wrapper="bootstrap3"/>
											<f:display property="uuid" wrapper="bootstrap3"/>
											<f:display property="total" widget="money" wrapper="bootstrap3"/>
											<f:display property="cfdiFileName" wrapper="bootstrap3" label="Archivo"/>
											<f:display property="acuseEstado" wrapper="bootstrap3"/>
											<f:display property="acuseCodigoEstatus" wrapper="bootstrap3"/>								
										</f:with>
									</form>
								</div>
									<div class="col-lg-offset-2 col-lg-6">
						                <g:link controller="comprobanteFiscal" action="validar" onclick="return confirm('Validar en el SAT?');"
						                		class="btn btn-default btn-outline" id="${facturaDeGastosInstance.comprobante.id}">
						                	    <i class="fa fa-check-square-o"></i> Validar (SAT)
						                </g:link> 
						                <g:if test="${facturaDeGastosInstance.comprobante.acuse}">
						                	<g:link  controller="comprobanteFiscal" action="mostrarAcuse" 
						                		id="${facturaDeGastosInstance.comprobante.id}"
						                		class="btn btn-default btn-outline" >
						                		<i class="fa fa-file-code-o"></i> Acuse
						                	</g:link>
						                	
						                </g:if>
						                <g:if test="${facturaDeGastosInstance.comprobante}">
						                	<g:link class="btn btn-default btn-outline" 
						                		controller="comprobanteFiscal" action="mostrarCfdi" 
						                		id="${facturaDeGastosInstance.comprobante.id}">CFDI</g:link>
						                	<g:link  action="descargarAcuse" 
						                		id="${facturaDeGastosInstance.comprobante.id}"
						                		class="btn btn-default btn-outline" >
						                		<i class="fa fa-download"></i> Descargar CFDI
						                	</g:link>
						                </g:if>
						                <a href="#uploadFileDialog" data-toggle="modal" class="btn btn-success btn-outline">
											<i class="fa fa-upload"></i></span> Cargar CFDI
										</a>
						            </div>
							</div>
							
						</g:if>
						<g:else>
							<div class="btn-group">
								 <a href="#uploadFileDialog" data-toggle="modal" class="btn btn-success btn-outline">
									<i class="fa fa-upload"></i></span> Cargar CFDI
								</a>
							</div>
						</g:else>
					</div>
				</div>		

	    </div>
	</div>
	<g:render template="/comprobanteFiscal/uploadXmlFile" bean="${facturaDeGastosInstance}"/>
	<script type="text/javascript">
		$(function(){
			$('.date').bootstrapDP({
			    format: 'dd/mm/yyyy',
			    keyboardNavigation: false,
			    forceParse: false,
			    autoclose: true,
			    todayHighlight: true
			});
			$('.chosen-select').chosen();
			$(".numeric").autoNumeric('init',{vMin:'0'},{vMax:'9999'});
			$(".money").autoNumeric('init',{wEmpty:'zero',mRound:'B',aSign: '$'});
			$(".tc").autoNumeric('init',{vMin:'0.0000'});
			$(".porcentaje").autoNumeric('init',{altDec: '%', vMax: '99.99'});
		});
	</script>
</content>

 	
 	
	
</body>
</html>
