<!doctype html>
<html>
<head>
	<title>Alta de cheque </title>
	<meta name="layout" content="luxor">
	<asset:javascript src="forms/forms.js"/>
</head>
<body>

<content tag="header">Alta de Cheque</content>
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
					<lx:iboxTitle title="Comisión bancaria"/>
				    <div class="ibox-content">
				    	<lx:errorsHeader bean="${chequeInstance}"/>
				    	<g:form name="createForm" action="create" class="form-horizontal" method="POST">	
				    		<f:with bean="chequeInstance">
				    			<f:field property="egreso" label="Pago" wrapper="bootstrap3" >
									<g:select class="form-control chosen-select"  
										name="egreso.id" 
										value="${value}"
										from="${com.luxsoft.impapx.tesoreria.MovimientoDeCuenta.findAll("from MovimientoDeCuenta p where p.tipo='CHEQUE' and p not in(select x.egreso from Cheque x)")}" 
										optionKey="id" 
										noSelection="[null:'Seleccione el egreso']"
										/>
				    			</f:field>
								<f:field property="folio" widget="numeric" wrapper="bootstrap3" />
				    		</f:with>
				    		<div class="form-group">
				    			<div class="col-lg-offset-3 col-lg-10">
				    				<button id="saveBtn" class="btn btn-primary ">
				    					<i class="fa fa-floppy-o"></i> Salvar
				    				</button>
				    				<lx:backButton/>
				    			</div>
				    		</div>
				    	</g:form>
				    </div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			$("#folio").autoNumeric('init',{vMin:'0',vMax:'9999'})
			$('.chosen-select').chosen({placeholder_text_single:'Seleccione una cuenta',search_contains:'true'});
			$('form[name=createForm]').submit(function(e){
	    		var button=$("#saveBtn");
	    		button.attr('disabled','disabled')
	    		 .html('Procesando...');
	    		return true;
			});
		});
	</script>	
</content>
	

</body>
</html>
