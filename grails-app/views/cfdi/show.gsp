<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>Cfdi ${cfdiInstance.folio}</title>
</head>
<body>
	<div class="container">
	
		<div class="row form-panel">
			<div class="col-md-12">
				<div class="panel panel-primary">
					<!-- Default panel contents -->
  					<div class="panel-heading">
  						%{-- Cfdi: ${cfdiInstance.serie}- ${cfdiInstance?.folio}  ${cfdiInstance?.cancelacion?' **CANCELADO**':''} --}%
  						Cfdi: ${cfdiInstance.serie}- ${cfdiInstance?.folio}   
  					</div>
  					<div class="panel-body">
						<div class="row">
							<div class="col-md-12">
								<g:render template="form"/>
							</div>
							
						</div>
  					</div>
  					<div class="panel-footer">
  						<div class="btn-group">
							
							<g:link class="btn btn-default btn-outline" uri="${origen}">
								<i class="fa fa-step-backward"></i> Origen</span>
							</g:link>

  							<g:link class="btn btn-default btn-outline" action="index" >
								<i class="fa fa-list"></i> Comprobantes</span>
							</g:link>

							
  							<g:if test="${cfdiInstance.versionCfdi == '3.3'}">
  								<g:link class="btn btn-default btn-outline" action="print" id="${cfdiInstance.id}">
								<span class="glyphicon glyphicon-print"> Imprimir</span>
								</g:link>
  							</g:if>
  							
  						
  							<g:link class="btn btn-default btn-outline" 
  								action="mostrarXml" id="${cfdiInstance.id}">XML</g:link>
  							
							<g:link class="btn btn-default btn-outline" action="descargarXml" resource="${cfdiInstance}">
								<span class="glyphicon glyphicon-cloud-download"> Descargar XML</span>
							</g:link>
							
							
							<a href="#enviarCorreoForm" data-toggle="modal" class="btn btn-default btn-outline">
								<span class="glyphicon glyphicon-envelope"></span> Enviar
							</a>

							<g:if test="${cfdiInstance?.cancelacion}">
								<g:link  action="mostrarAcuse" controller="cancelacionDeCfdi"
									id="${cfdiInstance.cancelacion.id}"
									class="btn btn-default btn-outline" >
									</span> Acuse
								</g:link>
								
							</g:if>

							<g:else>
								<button class="btn btn-danger btn-outline" data-toggle="modal" data-target="#cancelarCfdiDialog">
									<i class="fa fa-ban"></i> Cancelar
								</button>
							</g:else>
							
							<g:if test="${cfdiInstance.versionCfdi == '3.3' && cfdiInstance.uuid == null}">
								<g:link  action="timbrar" id="${cfdiInstance.id}" class="btn btn-default btn-outline" >
									<i class="fa fa-wifi"></i> Timbrar
								</g:link>
							</g:if>
							
							<g:if test="${cfdiInstance.versionCfdi != '3.3'}">
								<g:jasperReport
										controller="cfdi"
										action="imprimirCfdi"
										jasper="CFDI" 
										format="PDF" 
										name="${cfdiInstance.serie}-${cfdiInstance.folio}">
								<g:hiddenField name="id" value="${cfdiInstance.id}"/>

								</g:jasperReport> 
							</g:if>
  						</div>
						
					</div>
				</div>

			</div>

		</div><!-- end .row -->
		

	<g:render template="mailCfdi"/>
		
	</div>
	
	<div class="modal fade" id="cancelarCfdiDialog" tabindex="-1">
		<div class="modal-dialog ">
			<div class="modal-content">
				<g:form action="cancelar" class="form-horizontal" >
					<g:hiddenField name="id" value="${cfdiInstance.id}"/>

					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">Cancelar el CFDI </h4>
					</div>
					<div class="modal-body">
						<p><small>${cfdiInstance}</small></p>
						<input type="text" name="comentario" placeholder="Comentario de canelacion" class="form-control">
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
						<g:submitButton class="btn btn-danger" name="aceptar" value="Cancelar" />
					</div>
				</g:form>
			</div><!-- moda-content -->
		</div><!-- modal-di -->
		
	</div>
</body>
</html>

