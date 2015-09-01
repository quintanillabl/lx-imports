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

							
  						
  							<g:link class="btn btn-default btn-outline" action="print" id="${cfdiInstance.id}">
								<span class="glyphicon glyphicon-print"> Imprimir</span>
							</g:link>
  						
  							<g:link class="btn btn-default btn-outline" 
  								action="mostrarXml" id="${cfdiInstance.id}">XML</g:link>
  							
							<g:link class="btn btn-default btn-outline" action="descargarXml" resource="${cfdiInstance}">
								<span class="glyphicon glyphicon-cloud-download"> Descargar XML</span>
							</g:link>
							
							
							<a href="#enviarCorreoForm" data-toggle="modal" class="btn btn-default btn-outline">
								<span class="glyphicon glyphicon-envelope"></span> Enviar
							</a>

							%{-- <g:if test="${cfdiInstance?.cancelacion}">
								<g:link  action="mostrarAcuse" controller="cancelacionDeCfdi"
									id="${cfdiInstance.cancelacion.id}"
									class="btn btn-default btn-outline" >
									</span> Acuse
								</g:link>
								<g:link  action="descargarAcuseXml" controller="cancelacionDeCfdi"
									id="${cfdiInstance?.cancelacion.id}"
									class="btn btn-default btn-outline" >
									</span> Descargar acuse
								</g:link>
							</g:if>

							<g:else>
								<g:link  action="cancelar" class="btn btn-default btn-outline"  
										onclick="return confirm('Cancelar CFDI?');" id="${cfdiInstance.id }">
									<span class="glyphicon glyphicon-remove-circle"></span> Cancelar
								</g:link>
							</g:else> --}%
							
							
							%{-- <g:jasperReport
									controller="cfdi"
									action="imprimir"
									jasper="CFDI" 
									format="PDF" 
									name="Imprimir CFDI">
									<g:hiddenField name="id" value="${cfdiInstance.id}"/>
							</g:jasperReport> --}%
							
							<g:jasperReport
										controller="cfdi"
										action="imprimirCfdi"
										jasper="CFDI" 
										format="PDF" 
										name="${cfdiInstance.serie}-${cfdiInstance.folio}">
								<g:hiddenField name="id" value="${cfdiInstance.id}"/>
							</g:jasperReport>
  						</div>
						
					</div>
				</div>

			</div>

		</div><!-- end .row -->
		

	<g:render template="mailCfdi"/>
		
	</div>
	
	
</body>
</html>

