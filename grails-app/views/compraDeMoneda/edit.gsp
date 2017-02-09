<!doctype html>
<html>
<head>
	<title>Compra de moneda</title>
	<meta name="layout" content="luxor">
	<asset:javascript src="forms/forms.js"/>
</head>
<body>

<content tag="header">Compra de moneda Folio: ${compraDeMonedaInstance.id}</content>
<content tag="subHeader">
	<ol class="breadcrumb">
    	<li><g:link action="index">Compra de moneda</g:link></li>
    	<li><g:link action="show" id="${compraDeMonedaInstance.id}">Consulta</g:link></li>
    	<li class="active"><strong>Edición</strong></li>
	</ol>
</content>
	
<content tag="document">
	<div class="wrapper wrapper-content animated fadeInRight">

		<div class="row">
			<div class="col-lg-7">
				<div class="ibox float-e-margins">
					
				
				<lx:iboxTitle title="Compra de moneda: ${compraDeMonedaInstance.id}"/>
				<div class="ibox-content">
					<lx:errorsHeader bean="${compraDeMonedaInstance}"/>
					<g:form name="updateForm" action="update" class="form-horizontal" method="POST">	
						<f:with bean="compraDeMonedaInstance">
							<f:display property="requisicion" widget-class="form-control " wrapper="bootstrap3"/>
							<f:display property="fecha"  wrapper="bootstrap3"/>
							
							<f:display property="tipoDeCambioCompra"  
								widget="tc" wrapper="bootstrap3" label="TC Compra" />
							<f:display property="tipoDeCambio" 
								widget="tc" wrapper="bootstrap3" />
							<f:display property="diferenciaCambiaria"  wrapper="bootstrap3" widget="money"/>
							<legend>Pago a proveedor</legend>
							<f:display property="pagoProveedor.id"  wrapper="bootstrap3" />
							<f:display property="pagoProveedor.comentario"  wrapper="bootstrap3" />
							<f:display property="pagoProveedor.pago"  wrapper="bootstrap3" label="Pago (CxP)"/>
							<div class="form-group">
								<label for="factura" class="control-label col-sm-3">Factura</label>
								<div class="col-sm-9">
									<input name="factura" disabled
										class="form-control" value="${compraDeMonedaInstance.requisicion.partidas[0]?.factura}">
								</div>
							</div>
							<div class="form-group">
								<div class="col-lg-offset-3 col-lg-10">
									<lx:backButton/>
									%{-- <button id="saveBtn" class="btn btn-primary ">
										<i class="fa fa-floppy-o"></i> Actualizar
									</button> --}%
									<lx:deleteButton bean="${compraDeMonedaInstance}"/>
									
								</div>
							</div>

						</f:with>
						
					</g:form>
				</div>
				</div>
			</div>

			<div class="col-lg-5">
				<div class="row">
					<div class="ibox float-e-margins">
						<div class="ibox-title">
						    <h5>Egreso:  <strong>${compraDeMonedaInstance.pagoProveedor.egreso.id}</strong></h5>
						    <div class="ibox-tools">
						        <a class="collapse-link">
						            <i class="fa fa-chevron-up"></i>
						        </a>
						        <a class="close-link">
						            <i class="fa fa-times"></i>
						        </a>
						    </div>
						</div>

						<div class="ibox-content">
							<form action="save" class="form-horizontal">	
								<f:with bean="${compraDeMonedaInstance.pagoProveedor.egreso}">
									<f:display property="cuenta" />
									<f:display property="moneda" />
									<f:display property="importe" widget="money"/>
								</f:with>
							</form>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="ibox float-e-margins">
						<div class="ibox-title">
						    <h5>Ingreso:  <strong>${compraDeMonedaInstance.ingreso.id}</strong></h5>
						    <div class="ibox-tools">
						        <a class="collapse-link">
						            <i class="fa fa-chevron-up"></i>
						        </a>
						        <a class="close-link">
						            <i class="fa fa-times"></i>
						        </a>
						    </div>
						</div>

						<div class="ibox-content">
							<form action="save" class="form-horizontal">	
								<f:with bean="${compraDeMonedaInstance.ingreso}">
									<f:display property="cuenta" />
									<f:display property="moneda" />
									<f:display property="tc" />
									<f:display property="importe" widget="money"/>
								</f:with>
							</form>
						</div>
					</div>
				</div>
				
			</div>
		</div>

	</div>
	<script type="text/javascript">
		$(function(){
			$('.tc').each(function(){
				$(this).attr("required",true)
			});
			$('.input-group.date').bootstrapDP({
	            todayBtn: "linked",
	            keyboardNavigation: false,
	            forceParse: false,
	            calendarWeeks: true,
	            autoclose: true
			});
			$(".tc").autoNumeric('init',{vMin:'0.0000'});
			
			$('.chosen-select').chosen();
			
			$('form[name=updateForm]').submit(function(e){
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

