<%@ page import="com.luxsoft.impapx.Embarque" %>
<!doctype html>
<html>
<head>
	<title>Alta de embarque</title>
	<meta name="layout" content="luxor">
	<asset:javascript src="forms/forms.js"/>
</head>
<body>

<content tag="header">Alta de embarque de importación</content>

<content tag="subHeader">
	<ol class="breadcrumb">
    	<li><g:link action="index">Embarques</g:link></li>
    	<li class="active"><strong>Alta</strong></li>
	</ol>
</content>
	
<content tag="document">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-lg-8">
				<lx:iboxTitle title="Alta de pago a proveedo "/>
				<div class="ibox-content">
					<lx:errorsHeader bean="${embarqueInstance}"/>
					<g:form name="createForm" action="save" class="form-horizontal" method="POST">
						<f:with bean="embarqueInstance">
							<f:field property="bl" widget="mayusculas" ></f:field>
							<f:field property="nombre" widget="mayusculas"></f:field>
							<f:field property="fechaEmbarque" label="F.Embarque"/>
							<f:field property="proveedor" ></f:field>
							<f:field property="aduana"></f:field>
							<f:field property="ingresoAduana" widget-class="form-control" label="ETA"/>
							<f:field property="contenedores" widget="numeric"/>
							<f:field property="comentario" >
								<g:textArea name="comentario" class="comentario form-control" />
							</f:field>
							<div class="form-group">
								<div class="col-lg-offset-2 col-lg-9">
									<button id="saveBtn" class="btn btn-primary ">
										<i class="fa fa-floppy-o"></i> Salvar
									</button>
									<lx:backButton/>
								</div>
							</div>
						</f:with>

						%{-- <f:with bean="pagoProveedorInstance">
							<f:field property="requisicion" widget-class="form-control " wrapper="bootstrap3"/>
							<f:field property="fecha"  wrapper="bootstrap3"/>
							<f:field property="cuenta" widget-class="form-control chosen-select" wrapper="bootstrap3"/>
							<f:field property="tipoDeCambio" widget="tc" wrapper="bootstrap3" widget-required="required"/>
							<f:field property="comentario" widget-class="form-control" wrapper="bootstrap3"/>
							
						</f:with> --}%
						
					</g:form>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			
			$('.input-group.date').bootstrapDP({
				format: 'dd/mm/yyyy',
	            todayBtn: "linked",
	            keyboardNavigation: false,
	            forceParse: false,
	            calendarWeeks: true,
	            autoclose: true
			});
			$(".numeric").autoNumeric('init',{vMin:'0'},{vMax:'9999'});
			
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


