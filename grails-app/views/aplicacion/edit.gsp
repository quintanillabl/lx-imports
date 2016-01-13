<%@ page import="com.luxsoft.impapx.cxp.Aplicacion" %>
<!doctype html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<g:set var="entityName" value="${message(code: 'aplicacion.label', default: 'Aplicacion abono CXP')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
		<asset:javascript src="forms/forms.js"/>
	</head>
	<body>
		<div class="container">
			<div class="row row-header">
				<div class="col-md-8 col-sm-offset-2 toolbar-panel">
					<div class="btn-group">
					    <lx:backButton/>
					    <lx:printButton/>
					    <lx:deleteButton bean="${aplicacionInstance}"/>
					</div>
				</div>
			</div> <!-- End .row 1 -->

			<div class="row">
			    <div class="col-md-8 col-sm-offset-2">
			    	<g:form name="updateForm" action="update" class="form-horizontal" method="PUT"  >
					<div class="panel panel-primary">
						<div class="panel-heading"> 
							Aplicación de abono ${aplicacionInstance.abono.id} ${aplicacionInstance.abono.proveedor} Disponible:
							${formatNumber(number:aplicacionInstance.abono.disponible,type:'currency')}
						</div>
						<g:if test="${flash.message}">
							<small><span class="label label-info ">${flash.message}</span></small>
						</g:if> 
					  	<div class="panel-body">
				  			<g:hiddenField name="id" value="${aplicacionInstance?.id}" />
				  			<g:hiddenField name="version" value="${aplicacionInstance?.version}" />
				  			<f:with bean="${aplicacionInstance}">
				  				<f:field property="fecha" wrapper="bootstrap3" />
				  				<f:field property="total" widget="money" wrapper="bootstrap3"/>
				  				<f:field property="comentario" wrapper="bootstrap3"widget-class="form-control"/>
				  				
				  			</f:with>
					  	</div>
					  	<div class="panel-footer">
					  		<div class="btn-group">
					  			<g:submitButton id="saveBtn" name="update" 
					  				class="btn btn-primary " 
					  				value="Actualizar"/>
					  		</div>
					  	</div><!-- end .panel-footer -->

					</div>
					</g:form>
			    </div>
			</div><!-- End .row 2 -->

		</div>
	
	<script type="text/javascript">
		$(function(){
			$('.date').bootstrapDP({
			    format: 'dd/mm/yyyy',
			    keyboardNavigation: false,
			    forceParse: false,
			    autoclose: true,
			    todayHighlight: true
			});
			$(".numeric").autoNumeric('init',{vMin:'0'},{vMax:'9999'});
			$(".money").autoNumeric('init',{wEmpty:'zero',mRound:'B',aSign: '$'});
			$(".tc").autoNumeric('init',{vMin:'0.0000'});

			$('form[name=updateForm]').submit(function(e){
	    		$(this).children('input[type=submit]')
	    		.attr('disabled', 'disabled');
	    		var button=$("#saveBtn");
	    		button.attr('disabled','disabled')
	    		.html('Procesando...');
	    		$(".money,.porcentaje,.numeric,.tc",this).each(function(index,element){
	    		  var val=$(element).val();
	    		  var name=$(this).attr('name');
	    		  var newVal=$(this).autoNumeric('get');
	    		  $(this).val(newVal);
	    		});
	    		//e.preventDefault(); 
	    		return true;
			});
		});
	</script>
		
		
	</body>
</html>
