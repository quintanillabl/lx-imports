<!doctype html>
<html>
<head>
	<title>Tipo de cambio</title>
	<meta name="layout" content="luxor">
	<asset:javascript src="forms/forms.js"/>
</head>
<body>

<content tag="header">Alta de tipo de cambio </content>
<content tag="subHeader">
	<ol class="breadcrumb">
    	<li><g:link action="index">Tipos de cambio</g:link></li>
    	<li class="active"><strong>Alta</strong></li>
	</ol>
</content>

<content tag="document">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-lg-8">
				<div class="ibox float-e-margins">
					<lx:iboxTitle title="Alta de pago"/>
				    <div class="ibox-content">
				    	<lx:errorsHeader bean="${tipoDeCambioInstance}"/>
				    	<g:form name="createForm" action="save" class="form-horizontal" method="POST">	
				    		<f:with bean="tipoDeCambioInstance">
				    			
				    			<f:field property="fecha" wrapper="bootstrap3"/>
				    			<f:field property="monedaOrigen"  wrapper="bootstrap3"/>
				    			<f:field property="monedaFuente"  wrapper="bootstrap3"/>
				    			%{-- <f:field property="factor" widget="tc" wrapper="bootstrap3" /> --}%
				    			<div class="form-group">
				    				<label for="factor" class="control-label col-sm-3">Factor</label>
				    				<div class="col-sm-9">
				    					<input name="factor" class="form-control tc" value="" required="true">
				    				</div>
				    			</div>
				    			<f:field property="fuente" 
				    				widget-class="form-control" wrapper="bootstrap3"/>
				    			<div class="form-group">
				    				<div class="col-lg-offset-3 col-lg-10">
				    					<button id="saveBtn" class="btn btn-primary ">
				    						<i class="fa fa-floppy-o"></i> Salvar
				    					</button>
				    					<lx:backButton/>
				    				</div>
				    			</div>

				    		</f:with>
				    		
				    	</g:form>
				    </div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){

			$('.input-group.date').bootstrapDP({
	            todayBtn: "linked",
	            keyboardNavigation: false,
	            forceParse: false,
	            calendarWeeks: true,
	            autoclose: true,
	            format: 'dd/mm/yyyy'
			});
			$(".tc").autoNumeric('init',{vMin:'0.0000'});
			$('.chosen-select').chosen();
			$('form[name=createForm]').submit(function(e){
				//e.preventDefault(); 
	    		var button=$("#saveBtn");
	    		button.attr('disabled','disabled')
	    		 .html('Procesando...');
	    		$(".tc",this).each(function(index,element){
	    		   var val=$(element).val();
	    		  var name=$(this).attr('name');
	    		  var newVal=$(this).autoNumeric('get');
	    		  $(this).val(newVal);
	    		  console.log('Enviando elemento numerico con valor:'+name+" : "+val+ " new val:"+newVal);
	    		});
	    		return true;
			});	
			
		});
	</script>	
</content>
	

</body>
</html>


