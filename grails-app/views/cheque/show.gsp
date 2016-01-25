<%@ page import="com.luxsoft.impapx.tesoreria.Cheque" %>
<%@ page import="luxsoft.cfd.ImporteALetra" %>
<!doctype html>
<html>
<head>
	<title>Cheque ${chequeInstance.id}</title>
	<meta name="layout" content="luxor">
</head>
<body>

<content tag="header">Cheque ${chequeInstance.folio} / ${chequeInstance.cuenta}</content>
<content tag="subHeader">
	<ol class="breadcrumb">
    	<li><g:link action="index">Cheques</g:link></li>
    	<li class="active"><strong>Alta</strong></li>
	</ol>
</content>

<content tag="document">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-lg-8">
				<div class="ibox float-e-margins ">
					<lx:iboxTitle title="Folio: ${chequeInstance.folio} ${chequeInstance.cancelacion?'CANCELADO':''}"/>
				    <div class="ibox-content">
				    	<lx:errorsHeader bean="${chequeInstance}"/>
				    	<g:form name="createForm" action="create" class="form-horizontal" method="POST">	
				    		<f:with bean="chequeInstance">
				    			<f:display property="egreso" widget="numeric" wrapper="bootstrap3" />
								<f:display property="folio" widget="numeric" wrapper="bootstrap3" />
								<f:display property="egreso.importe" widget="numeric" wrapper="bootstrap3" />
								<f:display property="fechaImpresion" widget="datetime" wrapper="bootstrap3" label="Fecha"/>
								<f:display property="cancelacion" widget="numeric" wrapper="bootstrap3" />
								<f:display property="comentarioCancelacion" widget="numeric" wrapper="bootstrap3" />
				    		</f:with>
				    		<div class="form-group">
				    			<div class="col-lg-offset-3 col-lg-10">
				    				<div class="btn-group">
				    					<lx:backButton label="Cheques"/>
				    					<g:if test="${!chequeInstance.cancelacion}">
				    						<button class="btn btn-danger btn-outline" 
				    							data-toggle="modal" data-target="#cancelarDialog">
				    							<i class="fa fa-ban"></i> Cancelar
				    						</button>
				    						
				    					</g:if>
				    				</div>
				    				
				    			</div>
				    		</div>
				    	</g:form>
				    	<g:if test="${!chequeInstance.cancelacion}">
				    		<div class="col-lg-5">
				    			<g:jasperReport jasper="${chequeInstance.cuenta.nombre}-Cheque" format="PDF" name="Cheque">
				    					<g:hiddenField name="ID" value="${chequeInstance.id}"/>
				    					<g:hiddenField name="IMPLETRA" value="${importeALetra}"/>
				    					<g:hiddenField name="IMPORTE" 
				    					value="${new java.text.DecimalFormat('##,###.##').format(chequeInstance.egreso.importe.abs())}"/>
				    			</g:jasperReport>
				    		</div>
				    		<div class="">
				    			<g:jasperReport jasper="PolizaCheque" format="PDF" name=" Póliza">
				    					<g:hiddenField name="ID" value="${chequeInstance.id}"/>
				    					<g:hiddenField name="IMPLETRA" value="${importeALetra}"/>
				    					<g:hiddenField name="IMPORTE" 
				    					value="${new java.text.DecimalFormat('##,###.##').format(chequeInstance.egreso.importe.abs())}"/>
				    			</g:jasperReport>
				    			
				    		</div>
				    	</g:if>
				    	
				    </div>

				</div>
			</div>
			<div class="col-lg-4">
				
			</div>
		</div>
	</div>

	<div class="modal fade" id="cancelarDialog" tabindex="-1">
		<div class="modal-dialog ">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Cancelación</h4>
				</div>
				<g:form action="cancelar" class="form-horizontal" id="${chequeInstance.id}">
					<div class="modal-body">
						<div class="form-group">
							<label for="comentario" class="control-label col-sm-2">Comentario</label>
							<div class="col-sm-10">
								<input name="comentario" class="form-control" value="">
							</div>
						</div>
					</div>
					
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
						<g:submitButton class="btn btn-info" name="aceptar"
								value="Aceptar" />
					</div>
				</g:form>
	
			</div>
			<!-- moda-content -->
		</div>
		<!-- modal-di -->
	</div>
	
</content>
	

</body>
</html>


