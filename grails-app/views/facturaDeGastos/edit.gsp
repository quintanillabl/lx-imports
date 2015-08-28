<!doctype html>
<html>
<head>
	<meta name="layout" content="luxor">
	<title>Facturas de gastos ${facturaDeGastosInstance.id}</title>
	<asset:javascript src="forms/forms.js"/>
</head>
<body>

<content tag="header">
	Factura de gastos Id: ${facturaDeGastosInstance.id}
</content>

<content tag="subHeader">
	<ol class="breadcrumb">
		<li><g:link action="index">Gastos</g:link></li>
		<li><g:link action="create">Alta</g:link></li>
		<li><g:link action="edit" id="${facturaDeGastosInstance.id}"><strong>Edición</strong></g:link></li>
	</ol>
</content>

<content tag="document">
	<div class="ibox float-e-margins">
		<div class="ibox-title">
		    
		    
		</div>
	    <div class="ibox-content">
			<ul class="nav nav-tabs" id="mainTab">
				<li class="active" ><a href="#facturasPanel" data-toggle="tab">Propiedades</a></li>
				<li><a id="conceptosBtn" href="#conceptosPanel" data-toggle="tab">Conceptos</a></li>
				<li><a href="#cuentaPanel" data-toggle="tab">CxP</a></li>
			</ul>
			
			<div class="tab-content">
					<div class="tab-pane fade in active" id="facturasPanel">
						<g:render template="editForm"/>
					</div>
					
					<div class="tab-pane fade in" id="conceptosPanel">
						<g:if test="${facturaDeGastosInstance.requisitado<=0.0}">
							<div class="btn-group toolbar-panel">
								<a id="addBtn" href="#createGastoDialog" class="btn btn-default" data-toggle="modal">
									<i class="fa fa-plus"></i> Agregar concepto
								</a>
								
								<button id="deleteBtn" class="btn btn-danger " >
										<i class="fa fa-trash"></i> Eliminar concepto
								</button>
							</div>
						</g:if>
						
						%{-- <g:render template="conceptosGrid"/> --}%
					</div>
					
					<div class="tab-pane fade in" id="cuentaPanel">
						<div class="row">
							<div class="col-md-10">
								<form class="form-horizontal">
									<f:with bean="facturaDeGastosInstance">
										<div class="col-md-6">
											<legend>  <span id="conceptoLabel">Cuenta por pagar</span></legend> 
											<f:display property="total" widget="money" wrapper="bootstrap3"/>
											<f:display property="descuento" widget="money" wrapper="bootstrap3"/>
											<f:display property="rembolso" widget="money" wrapper="bootstrap3"/>
											<f:display property="otros" widget="money" wrapper="bootstrap3"/>
											<f:display property="pagosAplicados" widget="money" wrapper="bootstrap3"/>
											<f:display property="saldoActual" widget="money" wrapper="bootstrap3"/>
										</div>
										<div class="col-md-6">
											<legend>  <span id="conceptoLabel">Requisición</span></legend> 
											<f:display property="fecha" wrapper="bootstrap3" widget-required="true"/>
											<f:display property="vencimiento" wrapper="bootstrap3"  />
											<f:display property="requisitado" widget="money" wrapper="bootstrap3"/>
											<f:display property="pendienteRequisitar" widget="money" wrapper="bootstrap3" label="Pendiente"/>
										</div>
									</f:with>
								</form>
							</div>
						</div>
						
					</div>
				</div>		

	    </div>
	</div>
	
</content>

 	<content tag="">
 		
 		<div class="panel panel-primary">
 			<div class="panel-heading">
 				
 			</div>
			
 		  	
 			<div class="panel-body ">
 				<g:hasErrors bean="${facturaDeGastosInstance}">
 					<div class="alert alert-danger">
 						<ul class="errors" >
 							<g:renderErrors bean="${facturaDeGastosInstance}" as="list" />
 						</ul>
 					</div>
 				</g:hasErrors>
				<%-- Tab Content --%>
				<div class="tab-content">
					<div class="tab-pane fade in active" id="facturasPanel">
						<g:render template="editForm"/>
					</div>
					
					<div class="tab-pane fade in" id="conceptosPanel">
						<g:if test="${facturaDeGastosInstance.requisitado<=0.0}">
							<div class="btn-group toolbar-panel">
								<a id="addBtn" href="#createGastoDialog" class="btn btn-default" data-toggle="modal">
									<i class="fa fa-plus"></i> Agregar concepto
								</a>
								
								<button id="deleteBtn" class="btn btn-danger " >
										<i class="fa fa-trash"></i> Eliminar concepto
								</button>
							</div>
						</g:if>
						
						<g:render template="conceptosGrid"/>
					</div>
					
					<div class="tab-pane fade in" id="cuentaPanel">
						<form class="form-horizontal">
						<f:with bean="facturaDeGastosInstance">
						<div class="col-md-6">
							<legend>  <span id="conceptoLabel">Cuenta por pagar</span></legend> 
							<f:display property="total" widget="money" wrapper="bootstrap3"/>
							<f:display property="descuento" widget="money" wrapper="bootstrap3"/>
							<f:display property="rembolso" widget="money" wrapper="bootstrap3"/>
							<f:display property="otros" widget="money" wrapper="bootstrap3"/>
							<f:display property="pagosAplicados" widget="money" wrapper="bootstrap3"/>
							<f:display property="saldoActual" widget="money" wrapper="bootstrap3"/>
						</div>
						<div class="col-md-6">
							<legend>  <span id="conceptoLabel">Requisición</span></legend> 
							<f:display property="fecha" wrapper="bootstrap3" widget-required="true"/>
							<f:display property="vencimiento" wrapper="bootstrap3"  />
							<f:display property="requisitado" widget="money" wrapper="bootstrap3"/>
							<f:display property="pendienteRequisitar" widget="money" wrapper="bootstrap3" label="Pendiente"/>
						</div>
						</f:with>
						</form>
					</div>
				</div>		
 			
 			</div>					
 		 
 			<div class="panel-footer">
 				<div class="btn-group">
 					<g:link class="btn btn-default " action="index"  >
 						<i class="fa fa-step-backward"></i> Facturas
 					</g:link>
 					<g:if test="${facturaDeGastosInstance.requisitado<=0.0}">
 						<lx:printButton id="${facturaDeGastosInstance.id}"/>
 						<button id="saveBtn" class="btn btn-success">
 							<i class="fa fa-floppy-o"></i> Actualizar
 						</button>
 						<lx:deleteButton bean="${facturaDeGastosInstance}"/>

 						%{-- <div class="buttons col-md-offset-1 col-md-3">
 							<g:submitButton name="Actualizar" class="btn btn-primary " />
 							
 						</div> --}%
 					</g:if>
 					
 				</div>
 			</div><!-- end .panel-footer -->

 		</div>
 		
 		<g:render template="conceptosPanel"/>

 		<script type="text/javascript">
 			$(function(){
 				$(".money").autoNumeric('init',{wEmpty:'zero',mRound:'B',aSign: '$',vMin:'0.0'});
 				$(".porcentaje").autoNumeric('init',{aSign: '%', pSign: 's', vMax: '99.99'});

 				$('body').on('shown.bs.modal', '.modal', function () {
 				    $('[id$=concepto]').focus();
 				});
 				
 				// $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
 				// 	var id=$(e.target).attr('id');
 				// 	if(id==='conceptosBtn'){
 				// 		$("#addBtn").show();
 				// 		$("#deleteBtn").show();
 				// 	}else{
 				// 		$("#addBtn").hide();
 				// 		$("#deleteBtn").hide();
 				// 	}
 				//   // e.target // newly activated tab
 				//   // e.relatedTarget // previous active tab
 				// });

 				$("#concepto").autocomplete({
 					source:'<g:createLink controller="cuentaContable" action="cuentasDeDetalleJSONList"/>',
 					minLength:3,
 					autoFocus: true,
 					appendTo: "#createGastoDialog",
 					select:function(e,ui){
 						console.log('Valor seleccionado: '+ui.item.id);
 						$("#conceptoId").val(ui.item.id);
 					}
 				});
 				$("#saveBtn").on('click',function(e){
 					$('form[name=updateForm]').submit();
 				});

 				$('form[name=updateForm]').submit(function(e){
 					var button=$("#saveBtn");
 					button.attr('disabled','disabled')
 					.html('Procesando...');
 					//e.preventDefault(); 
 					return true;
 				});

				$('form[name=createForm]').submit(function(e){
		    		var button=$("#createBtn");
		    		button.attr('disabled','disabled')
		    		.html('Procesando...');
		    		$(".money,.porcentaje",this).each(function(index,element){
		    		  var val=$(element).val();
		    		  var name=$(this).attr('name');
		    		  var newVal=$(this).autoNumeric('get');
		    		  $(this).val(newVal);
		    		  //console.log('Enviando elemento numerico con valor:'+name+" : "+val+ " new val:"+newVal);
		    		});

		    		//e.preventDefault(); 
		    		return true;
				});

				$("#deleteBtn").on('click',function(e){
					eliminar();
				});

				var getSelectedRows=function(){
					var res=[];
					var data=$(".simpleGrid .selected").each(function(){
						var tr=$(this);
						res.push(tr.attr("id"));
					});
					return res;
				};
				
				function eliminar(){
					var res=getSelectedRows();

					if(res.length==0){
						alert('Debe seleccionar al menos un registro');
						return;
					}
					var ok=confirm('Eliminar  ' + res.length+' partida(s)?');
					if(!ok)
						return;
					console.log('Cancelando facturas: '+res);
					
					$.ajax({
						url:"${createLink(action:'eliminarConceptos')}",
						data:{
							facturaId:${facturaDeGastosInstance.id},partidas:JSON.stringify(res)
						},
						success:function(response){
							location.reload();
						},
						error:function(request,status,error){
							alert("Error: "+status);
						}
					});
				}
				
 			});
 		</script>
 	</content>

 	
	
</body>
</html>
