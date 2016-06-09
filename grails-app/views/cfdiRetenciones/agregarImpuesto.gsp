
<!doctype html>
<html>
<head>
	<title>Alta de impuesto retenido</title>
	<asset:javascript src="forms/forms.js"/>
</head>
<body>
	
	<div class="row wrapper border-bottom white-bg page-heading">
       <div class="col-lg-10">
           <h3>Alta de impuesto retenido <small> ${cfdiRetencionesInstance.id} (${cfdiRetencionesInstance.receptor})</
           <ol class="breadcrumb">
           		<li><g:link action="edit" id="${cfdiRetencionesInstance.id}">Comprobantes</g:link></li>
               	<li class="active"><strong>Alta</strong></li>
           </ol>
       </div>
       <div class="col-lg-2"></div>
	</div>

	<div class="row wrapper wrapper-content animated fadeInRight">
		<div class="col-md-offset-3 col-md-6">
			    <div class="ibox float-e-margins">
			      <div class="ibox-title"><h5>Alta de comprobante</h5></div>
			      <div class="ibox-content">
			        <lx:errorsHeader bean="${impuestoRetenidoInstance}"/>
			        <g:form name="createForm" action="salvarImpuesto"  class="form-horizontal" method="POST">
			          <g:hiddenField name="retencion.id" value="${cfdiRetencionesInstance.id}"/>
			          <div class="row">
			          	<f:with bean="${impuestoRetenidoInstance}">
			          		<f:field property="baseRet" widget="money" wrapper="bootstrap3"/>
			          		<f:field property="impuesto" wrapper="bootstrap3" widget-class="form-control"/>
			          		<f:field property="montoRet" widget="money" wrapper="bootstrap3"/>
			          		<f:field property="tipoPagoRet" wrapper="bootstrap3" widget-class="form-control"/>
			          	</f:with>
						<div class="form-group ">
						   	<div class="buttons col-md-offset-3 col-md-4">
						   		<g:submitButton name="Salvar" class="btn btn-primary " />
						   		<g:link action="edit" id="${cfdiRetencionesInstance.id}" 
						   			class="btn btn-default"> Cancelar
						   		</g:link>
						   	</div>
						</div>
			          </div>
			        </g:form>
			      </div>
			    </div>
		</div>
	    
	</div>
	

<script type="text/javascript">
	$(function(){
		$(".money").autoNumeric({wEmpty:'zero',aSep:"",lZero: 'deny'});
	});

</script>
	
</body>
</html>



