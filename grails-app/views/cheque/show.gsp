<%@ page import="com.luxsoft.impapx.tesoreria.Cheque" %>
<%@ page import="luxsoft.cfd.ImporteALetra" %>
<!doctype html>
<html>
<head>
	<title>Cheque ${chequeInstance.id}</title>
	<meta name="layout" content="luxor">
</head>
<body>

<content tag="header">Cheque ${chequeInstance.id}</content>
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
				<div class="ibox float-e-margins">
					<lx:iboxTitle title="Registro de cheque"/>
				    <div class="ibox-content">
				    	<lx:errorsHeader bean="${chequeInstance}"/>
				    	<g:form name="createForm" action="create" class="form-horizontal" method="POST">	
				    		<f:with bean="chequeInstance">
				    			<f:display property="egreso" widget="numeric" wrapper="bootstrap3" />
								<f:display property="folio" widget="numeric" wrapper="bootstrap3" />
								<f:display property="fechaImpresion" widget="datetime" wrapper="bootstrap3" />
								<f:display property="cancelacion" widget="numeric" wrapper="bootstrap3" />
								<f:display property="comentarioCancelacion" widget="numeric" wrapper="bootstrap3" />
				    		</f:with>
				    		<div class="form-group">
				    			<div class="col-lg-offset-3 col-lg-10">
				    				<div class="btn-group">
				    					<lx:backButton label="Cheques"/>
				    					<g:link class="btn btn-danger " 
				    						action="cancelar" id="${chequeInstance.id}"
				    						onclick="return confirm('Cancelar el cheque?');">
				    						<i class="fa fa-trash"></i> Cancelar
				    					</g:link>
				    				</div>
				    				
				    			</div>
				    		</div>
				    	</g:form>
				    	<g:if test="${!chequeInstance.cancelacion}">
				    		<div class="col-lg-5">
				    			<g:jasperReport jasper="${chequeInstance.cuenta.nombre}-Cheque" format="PDF" name="Cheque">
				    					<g:hiddenField name="ID" value="${chequeInstance.id}"/>
				    					<g:hiddenField name="IMPLETRA" value="${importeALetra}"/>
				    			</g:jasperReport>
				    		</div>
				    		<div class="">
				    			<g:jasperReport jasper="PolizaCheque" format="PDF" name=" Póliza">
				    					<g:hiddenField name="ID" value="${chequeInstance.id}"/>
				    					<g:hiddenField name="IMPLETRA" value="${importeALetra}"/>
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
	
</content>
	

</body>
</html>


