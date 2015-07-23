<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>Empresa ${empresaInstance.nombre}</title>
</head>
<body>
	<div class="container">

		<div class="row">
			<div class="col-md-12">
				<div class="alert alert-info">
					<h2> 
						${empresaInstance.nombre} 
					</h2>
					<g:if test="${flash.message}">
						<span class="label label-warning">${flash.message}</span>
					</g:if> 
				</div>
			</div>
		</div><!-- end .row -->
		
		<div class="row">
			<div class="col-md-12">
				<g:hasErrors bean="${empresaInstance}">
					<div class="alert alert-danger">
						<g:renderErrors bean="${empresaInstance}" as="list" />
					</div>
				</g:hasErrors>
				<g:form class="form-horizontal " action="update" name="empresaForm" method="PUT">
					<g:hiddenField name="id" value="${empresaInstance.id}"/>
					<g:hiddenField name="version" value="${empresaInstance.id}"/>
					<f:with bean="${empresaInstance}">
						
						<f:field property="clave" input-class="form-control" cols="col-sm-5"/>
						<f:field property="nombre" input-class="form-control" cols="col-sm-5"/>
						<f:field property="regimen" input-class="form-control" cols="col-sm-5"/>
						<f:field property="numeroDeCertificado" input-class="form-control" cols="col-sm-5"/>
					</f:with>

					<legend>Timbrado</legend>
					<div class="row">
						<div class="col-sm-6">
							<f:with bean="${empresaInstance}">
								<f:field property="timbradoDePrueba" colsLabel="col-sm-4" cols="col-sm-8" 
								input-class="form-control" />
								<f:field property="usuarioPac" colsLabel="col-sm-4" cols="col-sm-8" 
								input-class="form-control" />
								<f:field property="passwordPac" colsLabel="col-sm-4" cols="col-sm-8" 
								input-class="form-control" />
								<f:field property="passwordPfx" colsLabel="col-sm-4" cols="col-sm-8" 
								input-class="form-control" />
								
							</f:with>
							
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<label for="certificadoDigitalFile" class="col-sm-4 control-lable">Certificado</label>
								<div class="col-sm-8">
									<a href="#cambiarCertificado" class="btn btn-default">Cambiar</a>
								</div>
							</div>
							<div class="form-group">
								<label for="certificadoDigitalFile" class="col-sm-4 control-lable">Llave privada</label>
								<div class="col-sm-8">
									<a href="#cambiarLlavePrivada" class="btn btn-default">Cambiar</a>
								</div>
							</div>
							<div class="form-group">
								<label for="certificadoDigitalFile" class="col-sm-4 control-lable">Certificado PFX</label>
								<div class="col-sm-8">
									<a href="#cambiarCertificadoPfx" class="btn btn-default">Cambiar</a>
								</div>
							</div>
						</div>
					</div>
					
					
					<g:render template="/common/direccion" bean="${empresaInstance}"/>
					<div class="form-group">
						<label for="fecha" class="col-sm-2 control-label">Última modificación</label>
						<div class="col-sm-4">
							<p class="form-control-static">
								<strong>
									<g:formatDate date="${empresaInstance.lastUpdated}" format="dd/MM/yyyy HH:mm"/>
								</strong>
							</p>
							<g:hiddenField  name="lastUpdated" 
							  value="${empresaInstance?.lastUpdated?.format('dd/MM/yyyy HH:mm')}"/>
						</div>
					</div>
					<div class="form-group">
						<div class="buttons  col-md-offset-2  col-md-3">
							<g:submitButton name="Generar" class="btn btn-primary  btn-block" value="Actualizar"/>
						</div>
					</div>
					<br>
				</g:form>
			</div>

		</div><!-- end .row2 -->

		<div class="row">
			<div class="col-md-12">
				<legend>Folios del sistema</legend>
				<form action="#" class="form-horizontal" >
					<div class="form-group">
						<label for="fecha" class="col-sm-2 control-label">Folio de facturas</label>
						<div class="col-sm-2">
							<p class="form-control-static">
								<strong>
									%{-- ${com.luxsoft.cfdi.CfdiFolio.findBySerie('FACTURA')?.folio} --}%
								</strong>
							</p>
						</div>
						<div class="col-sm-2">
							<a href="#cambioDeFolioFactura" data-toggle="modal" class="btn btn-default"> Cambiar</a>
						</div>
					</div>

					<div class="form-group">
						<label for="fecha" class="col-sm-2 control-label">Folio de notas</label>
						<div class="col-sm-2">
							<p class="form-control-static">
								<strong>
									%{-- ${com.luxsoft.cfdi.CfdiFolio.findBySerie('NOTAS')?.folio} --}%
								</strong>
							</p>
						</div>
						<div class="col-sm-2">
							<a href="#cambioDeFolioNotas" data-toggle="modal" class="btn btn-default"> Cambiar</a>
						</div>
					</div>

					
				</form>
			</div>
		</div>

	</div>

	<script type="text/javascript">
			$(document).ready(function(){
				$('form[name=empresaForm]').submit(function(e){
		    		$(this).children('input[type=submit]').attr('disabled', 'disabled');
		    		var button=$("#Generar");
		    		button.attr('disabled','disabled').attr('value','Procesando...');
		    		return true;
				});
				

			});
	</script>	
	
</body>
</html>