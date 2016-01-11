<div class="row">

<div class="col-md-6">
	<br>
	<form name="updateForm" class="form-horizontal"  >
		<f:with bean="${gastosDeImportacionInstance}">
			<g:hiddenField name="id" value="${gastosDeImportacionInstance?.id}"/>
			<g:hiddenField name="provId" value="${gastosDeImportacionInstance?.proveedor?.id}"/>

			<f:display property="proveedor" />
			<f:display property="documento" />
			<f:display property="fecha"/>
			<f:display property="vencimiento" />
			<f:display property="moneda"/>
			<f:display property="tc"/>								
			<f:display property="importe"/>
			<f:field property="incrementable" />
			<f:display property="tasaDeImpuesto"/>
			<f:display property="impuestos"/>
			<f:display property="retTasa"/>
			<f:display property="retImp"/>
			<f:display property="total"/>
			<f:field property="comentario"widget-class="form-control" />
		</f:with>
		
	</form>
</div>

<div class="col-md-6">
	<g:if test="${gastosDeImportacionInstance.comprobante}">
		
	<legend>CFDI </legend>
	<form class="form-horizontal" >
		<f:with bean="${gastosDeImportacionInstance.comprobante}">
			<f:display property="serie" wrapper="bootstrap3"/>
			<f:display property="folio" wrapper="bootstrap3"/>
			<f:display property="uuid" wrapper="bootstrap3"/>
			<f:display property="cfdiFileName" wrapper="bootstrap3" label="Archivo"/>
			<f:display property="acuseEstado" wrapper="bootstrap3"/>
			<f:display property="acuseCodigoEstatus" wrapper="bootstrap3"/>								
		</f:with>
	</form>
	 <div class="form-group">
			<div class="col-lg-offset-3 col-lg-9">
                <g:link controller="comprobanteFiscal" action="validar" onclick="return confirm('Validar en el SAT?');"
                		class="btn btn-default btn-outline" id="${gastosDeImportacionInstance.comprobante.id}">
                	    <i class="fa fa-check-square-o"></i> Validar (SAT)
                </g:link> 
                <g:if test="${gastosDeImportacionInstance.comprobante.acuse}">
                	<g:link  controller="comprobanteFiscal" action="mostrarAcuse" 
                		id="${gastosDeImportacionInstance.comprobante.id}"
                		class="btn btn-default btn-outline" >
                		<i class="fa fa-file-code-o"></i> Acuse
                	</g:link>
                	
                </g:if>
                <g:if test="${gastosDeImportacionInstance.comprobante}">
                	<g:link class="btn btn-default btn-outline" 
                		action="mostrarCfdi" id="${gastosDeImportacionInstance.comprobante.id}">CFDI</g:link>
                	<g:link  action="descargarAcuse" 
                		id="${gastosDeImportacionInstance.comprobante.id}"
                		class="btn btn-default btn-outline" >
                		<i class="fa fa-download"></i>  CFDI
                	</g:link>
                </g:if>
            </div>
		</div>
	</g:if>
</div>


</div>