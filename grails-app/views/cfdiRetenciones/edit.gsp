<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>Retención ${cfdiRetencionesInstance?.id}</title>
	<asset:stylesheet src="datatables/dataTables.css"/>
	<asset:javascript src="datatables/dataTables.js"/> 
	<asset:javascript src="forms/autoNumeric.js"/>
</head>
<body>

	<div class="container form-panel">


		<div class="row toolbar-panel">
			<div class="col-md-12 ">
				<div class="btn-group">
				    <g:link action="index" class="btn btn-default ">
				        <i class="fa fa-step-backward"></i> Comprobantes
				    </g:link>
				    <g:if test="${cfdiRetencionesInstance.uuid}">
			    	    
			    	    <a href="#enviarCorreoForm" data-toggle="modal" class="btn btn-default">
			    			<span class="glyphicon glyphicon-envelope"></span> Enviar
			    		</a>
			    		<g:link action="print" class="btn btn-default " id="${cfdiRetencionesInstance.id}">
				        <i class="fa fa-print"></i> Imprimir
				    	</g:link> 
				    </g:if>
				    <g:else>
				    	<g:link action="agregarImpuesto" class="btn btn-default " id="${cfdiRetencionesInstance.id}">
				    	    <i class="fa fa-plus"></i> Agregar retención
				    	</g:link>
				    	<g:link action="generarXml" class="btn btn-default " id="${cfdiRetencionesInstance.id}">
				    	    <i class="fa fa-qrcode"></i> Generar XML
				    	</g:link>
				    	%{-- <g:link action="timbrar" class="btn btn-success " id="${cfdiRetencionesInstance.id}">
				    	    <i class="fa fa-qrcode"></i> Timbrar
				    	</g:link> --}%
				    	<buttn id="saveBtn" class="btn btn-primary">
				    		<i class="fa fa-floppy-o"></i> Actualizar
				    	</buttn>
				    	<lx:deleteButton bean="${cfdiRetencionesInstance}" />
				    </g:else>
					<g:if test="${cfdiRetencionesInstance.xml}">
						<g:link class="btn btn-default " action="mostrarXml" id="${cfdiRetencionesInstance.id}">Ver XML</g:link>
						<g:link class="btn btn-default " action="descargarXml" resource="${cfdiRetencionesInstance}">
							<span class="glyphicon glyphicon-cloud-download"> Descargar XML</span>
						</g:link>
						<a href="#uploadFileDialog" data-toggle="modal" class="btn btn-default">
							<i class="fa fa-upload"></i></span> Importar Timbrado
						</a>
					</g:if>
					 
				</div>
				
			</div>
		</div> 

		<div class="row ">
		    <div class="col-md-12 ">
		    	
				<g:form name="updateForm" 
					class="form-horizontal"  action="update" id="${cfdiRetencionesInstance.id}" method="PUT">	

					<div class="panel panel-primary">
						<div class="panel-heading">
							Receptor: ${cfdiRetencionesInstance.receptor} Folio: ${cfdiRetencionesInstance.id}</div>
				  		<div class="panel-body">
				    		<g:hasErrors bean="${cfdiRetencionesInstance}">
				    			<div class="alert alert-danger">
				    				<ul class="errors" >
				    					<g:renderErrors bean="${cfdiRetencionesInstance}" as="list" />
				    				</ul>
				    			</div>
				    		</g:hasErrors>

							<f:with bean="${cfdiRetencionesInstance}">
								<g:hiddenField name="id" value="${cfdiRetencionesInstance.id}"/>
								<g:hiddenField name="version" value="${cfdiRetencionesInstance.version}"/>
								<div class="col-sm-7">
									<f:field property="fecha" wrapper="bootstrap3"/>
									<f:display property="emisor" wrapper="bootstrap3"/>
									<f:display property="receptor" wrapper="bootstrap3"/>
									<f:field property="receptorRfc" wrapper="bootstrap3" widget-class="form-control"/>
									<f:field property="receptorCurp" wrapper="bootstrap3" widget-class="form-control"/>
									<f:field property="receptorNacionalidad" wrapper="bootstrap3" 
									widget-class="form-control" label="Nacionalidad"/>
									<f:field property="receptorRegistroTributario" wrapper="bootstrap3" widget-class="form-control" label="Registro tributario"/>

									
									<f:field property="tipoDeRetencion" wrapper="bootstrap3">
										<g:select class="form-control"  
											name="${property}" 
											value="${value?.id}"
											from="${com.luxsoft.cfdi.retenciones.TipoDeRetencion.list()}" 
											optionKey="id" 
											noSelection="[null:'Seleccione un tipo']"
											/>
									</f:field>
									<f:field property="retencionDescripcion" wrapper="bootstrap3" widget-class="form-control"/>
									


								</div>

								<div class="col-sm-5">
									<f:field property="mesInicial" cols="col-sm-8" colsLabel="col-sm-4" widget-class="form-control"/>
									<f:field property="mesFinal" cols="col-sm-8" colsLabel="col-sm-4" widget-class="form-control"/>
									<f:field property="ejercicio" cols="col-sm-8" colsLabel="col-sm-4" widget-class="form-control"/>
									<f:field property="total" widget="money" 
									cols="col-sm-8" colsLabel="col-sm-4"/>
									<f:field property="totalGravado" widget="money" cols="col-sm-8" colsLabel="col-sm-4"/>
									<f:field property="totalExcento" widget="money" cols="col-sm-8" colsLabel="col-sm-4"/>
									<f:display property="totalRetenido" widget="money" cols="col-sm-8" colsLabel="col-sm-4"/>

									<g:if test="${cfdiRetencionesInstance.uuid}">
										<f:display property="uuid" cols="col-sm-8" colsLabel="col-sm-4"/>
										<f:display property="fechaDeTimbrado" cols="col-sm-8" colsLabel="col-sm-4"/>
									</g:if>

								</div>
							</f:with>
				  		</div>
				  		<table id="grid" class="table table-striped table-bordered table-condensed">
				  			<thead>
				  				<tr>
				  					<th>Folio</th>
				  					<th>Base</th>
				  					<th>Impuesto</th>
				  					<th>Monto</th>
				  					<th>Tipo</th>
				  					<td></td>
				  				</tr>
				  			</thead>
				  			<tbody>
				  				<g:each in="${cfdiRetencionesInstance.impuestosRetenidos}" var="row">
				  					<tr id="${row.id}">
				  						<td>
				  							<g:link  controller="impuestoRetenido" action="edit" id="${row.id}">
				  								${fieldValue(bean:row,field:"id")}
				  							</g:link>
				  						</td>
										<td>${g.formatNumber(number:row.baseRet,type:'currency')}</td>
				  						<td>${fieldValue(bean:row,field:"impuesto")}</td>
				  						<td>${g.formatNumber(number:row.montoRet,type:'currency')}</td>
										<td>${fieldValue(bean:row,field:"tipoPagoRet")}</td>
				  						<td>
				  							<g:if test="${!cfdiRetencionesInstance.uuid}">
				  								<g:link  action="eliminarImpuesto" id="${row.id}" 
				  									onclick="return confirm('Eliminar retención ${row.id}');">
				  									<span class="glyphicon glyphicon-trash"></span>
				  								</g:link>
				  							</g:if>
				  							
				  						</td>
				  					</tr>
				  				</g:each>
				  			</tbody>
				  		</table>
				  		<div class="panel-footer">
				  			<g:if test="${flash.message}">
				  				<span class="label label-warning">${flash.message}</span>
				  			</g:if> 
				  		</div>
					</div>
				</g:form>
				
			</div>

		</div><!-- end .row 2 -->

		
		<g:if test="${flash.error}">
			<div class="row">
				<div class="col-md-12">
					<div class="alert alert-danger">
						${flash.error}
					</div>
				</div>
			</div>
		</g:if>
		
	<div class="modal fade" id="uploadFileDialog" tabindex="-1">
		<div class="modal-dialog ">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Selección de archivo XML Timbrado</h4>
				</div>
				
				<g:uploadForm class="form" action="cargarXml" id="${cfdiRetencionesInstance?.id}">
					<div class="modal-body">
						
						<div class="form-group">
						    <label for="inputFile">Archivo de importación</label>
						    <input type="file" id="inputFile" class="form-control" name="xmlFile" accept="application/txt" autocomplete="off">
						    <p class="help-block">Seleccion el archivo para cargar</p>
						 </div> 
					</div>

					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
						<g:submitButton class="btn btn-primary" name="aceptar"value="Aceptar" />
					</div>
				</g:uploadForm>


			</div>
			<!-- moda-content -->
		</div>
		<!-- modal-di -->
	</div>

	</div>
<script type="text/javascript">
	$(function(){
		
		
		$("#totalesPanel :input").each(function(){
		 	var input = $(this); // This is the jquery object of the input, do what you will
		 	input.removeAttr("type").prop('type','text')
		 	.addClass('form-control')
		 	.addClass('text-right');
		});

		$("#saveBtn").click(function(){
			console.log('Salvar la forma');
			$("form[name=updateForm]").submit();
		});

		$('form[name=updateForm]').submit(function(e){
			console.log("Desablidatndo submit button....");
    		$(this).children('input[type=submit]').attr('disabled', 'disabled');
    		var button=$("#saveBtn");
    		button.attr('disabled','disabled')
    		//.attr('value','Procesando...');
    		.html('Procesando...')
    		//e.preventDefault(); 
    		return true;
		});
		
	});
</script>
	
</body>
</html>