<!doctype html>
<html>
<head>
	<meta name="layout" content="cxp">
	<title>Alta de gasto</title>
	<asset:javascript src="forms/forms.js"/>
</head>
<body>

 	<content tag="header">
 		<g:form name="updateForm" action="save" class="form-horizontal" method="POST">	
 		<div class="panel panel-primary">
 			<div class="panel-heading">
 				Registro de factura de gastos
 			</div>
 			<div class="panel-body ">
 				<g:hasErrors bean="${facturaDeGastosInstance}">
 					<div class="alert alert-danger">
 						<ul class="errors" >
 							<g:renderErrors bean="${facturaDeGastosInstance}" as="list" />
 						</ul>
 					</div>
 				</g:hasErrors>
				<legend>  <span id="conceptoLabel">Propiedades</span></legend> 
				<f:with bean="facturaDeGastosInstance">
				<div class="col-md-6">
					<f:field property="proveedor" widget-class="form-control" 
						wrapper="bootstrap3" widget-required="true"/>
					<f:field property="documento" widget-class="form-control" wrapper="bootstrap3"/>
					<f:field property="fecha" wrapper="bootstrap3" widget-required="true"/>
					<f:field property="vencimiento" wrapper="bootstrap3"  />
					<f:display property="moneda" wrapper="bootstrap3"/>
					<f:display property="tc" widget-class="form-control tc" wrapper="bootstrap3" widget-type="text"/>
					<f:field property="comentario" widget-class="form-control" wrapper="bootstrap3"/>
				</div>

				%{-- <div class="col-md-6" id="totalesPanel">
					<f:field property="importe" widget-class="form-control moneda" 
						wrapper="bootstrap3" widget-required="true" widget-type="text"/>
					<f:field property="descuentos" widget-class="form-control moneda" wrapper="bootstrap3" widget-type="text"/>
					<f:field property="subTotal" widget-class="form-control moneda" wrapper="bootstrap3" widget-type="text"/>
					<f:field property="tasaDeImpuesto" widget-class="form-control porcentaje" wrapper="bootstrap3" widget-type="text"/>
					<f:field property="impuestos" widget-class="form-control moneda" wrapper="bootstrap3" widget-type="text"/>
					<f:field property="retTasa" widget-class="form-control porcentaje" wrapper="bootstrap3" widget-type="text"/>
					<f:field property="retImp" widget-class="form-control moneda" wrapper="bootstrap3" widget-type="text"/>
					<f:field property="total" widget-class="form-control moneda" wrapper="bootstrap3" widget-type="text"/>
					<f:field property="descuento" widget-class="form-control moneda" wrapper="bootstrap3" widget-type="text"/>
					<f:field property="rembolso" widget-class="form-control moneda" wrapper="bootstrap3" widget-type="text"/>
				</div> --}%
				</f:with>		
 			
 			</div>					
 		 
 			<div class="panel-footer">
 				<div class="btn-group">
 					<g:link class="btn btn-default " action="index"  >
 						<i class="fa fa-step-backward"></i> Facturas
 					</g:link>
 					<button id="saveBtn" class="btn btn-success ">
 							<i class="fa fa-floppy-o"></i> Salvar
 					</button>
 				</div>
 			</div><!-- end .panel-footer -->

 		</div>
 		</g:form>
 		<%--
 		 	<script type="text/javascript">
 		 		$(function(){

 		 			$(".moneda").autoNumeric('init',{wEmpty:'zero',mRound:'B',aSign: '$'});
 		 			$(".tc").autoNumeric('init',{vMin:'0.0000'});
 		 			$(".porcentaje").autoNumeric('init',{altDec: '%', vMax: '99.99'});
 		 			
		 			$('#totalesPanel').on('blur','input',function(){
						var importe=$(this).autoNumeric('get');
						actualizarTotal();
						
		 			});
		 			

 		 			var actualizarTotal=function(){
 		 				
 		 				var importe=$('#importe').autoNumeric('get');
 		 				var descuentos=$('#descuentos').autoNumeric('get');
 		 				
 		 				var subTotal=importe-descuentos;
 		 				$('#subTotal').autoNumeric('set',subTotal.round(2));

 		 				var iva=$("#tasaDeImpuesto").autoNumeric('get');

 		 				var impuestos=(iva/100.00)*subTotal;
 		 				$("#impuestos").autoNumeric('set',impuestos);

						var retImp=$('#retImp').autoNumeric('get');
 		 				
 		 				var total=subTotal+impuestos-retImp
 		 				$('#total').autoNumeric('set',total);

 		 			};

 		 			

 					$('form[name=updateForm]').submit(function(e){
 						console.log("Desablidatndo submit button....");

 			    		var button=$("#saveBtn");
 			    		button.attr('disabled','disabled')
 			    		.html('Procesando...');

 			    		$(".moneda,porcentaje",this).each(function(index,element){
 			    		  var val=$(element).val();
 			    		  var name=$(this).attr('name');
 			    		  var newVal=$(this).autoNumeric('get');
 			    		  $(this).val(newVal);
 			    		});
 			    		//e.preventDefault(); 
 			    		return true;
 					});
 		 		})
 		 	</script>
 		 	--%>
 	</content>

 	
	
	
</body>
</html>
