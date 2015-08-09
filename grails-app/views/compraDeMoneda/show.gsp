<!doctype html>
<html>
<head>
	<title>Compra de moneda ${compraDeMonedaInstance.id}</title>
	<meta name="layout" content="luxor">
	<asset:javascript src="forms/forms.js"/>
</head>
<body>

<content tag="header">Compra de moneda: ${compraDeMonedaInstance}</content>
<content tag="subHeader">
	<ol class="breadcrumb">
    	<li><g:link action="index">Compras</g:link></li>
    	<li class="active"><strong>Consulta</strong></li>
    	<li><g:link action="edit" id="${compraDeMonedaInstance.id}">Edición</g:link></li>
	</ol>
</content>
	
<content tag="document">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-lg-8">
				<lx:iboxTitle title="Compra de moneda "/>
				
				<div class="ibox-content">
					<form name="createForm" class="form-horizontal">	
						<f:with bean="compraDeMonedaInstance">
							<f:display property="requisicion" widget-class="form-control " wrapper="bootstrap3"/>
							<f:display property="fecha"  wrapper="bootstrap3"/>
							<f:display property="moneda" wrapper="bootstrap3"/>
							<f:display property="tipoDeCambioCompra"  
								widget="tc" wrapper="bootstrap3" label="TC Compra" />
							<f:display property="tipoDeCambio" 
								widget="tc" wrapper="bootstrap3" />

							<f:display property="cuentaOrigen" widget-class="form-control chosen-select" wrapper="bootstrap3"/>
							<f:display property="cuentaDestino" widget-class="form-control chosen-select" wrapper="bootstrap3"/>
						</f:with>
						<div class="form-group">
							<div class="col-lg-offset-3 col-lg-10">
								<lx:backButton/>
								<lx:editButton id="${compraDeMonedaInstance.id}"/>
							</div>
						</div>
					</form>
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
			
			
		});
	</script>	
</content>
</body>
</html>

