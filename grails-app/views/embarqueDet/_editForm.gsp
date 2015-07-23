
<div class="panel panel-primary">
	<div class="panel-heading">
		Partida de embarque ${embarqueDetInstance.embarque} </div>
  	
	<div class="panel-body ">
		<g:hasErrors bean="${embarqueDetInstance}">
			<div class="alert alert-danger">
				<ul class="errors" >
					<g:renderErrors bean="${embarqueDetInstance}" as="list" />
				</ul>
			</div>
		</g:hasErrors>

		
		<g:form name="updateForm" action="edit" class="form-horizontal"  id="${embarqueDetInstance.id}">	
			
			<g:hiddenField name="kilosPorMillar" value="${kilosPorMillar}"/>
			<g:hiddenField name="factorUnitario" value="1000"/>
			
			<g:hiddenField name="proveedorId" value="${embarqueDetInstance.embarque.proveedor.id}"/>
			<g:hiddenField id="embarqueId" name="embarqueId" value="${embarqueDetInstance?.embarque?.id}"/>
			
			
			<f:with bean="embarqueDetInstance">
				<f:field property="compraDet" >
					<g:hiddenField id="compraDetId" 
						name="compraDetId" value="${embarqueDetInstance?.compraDet?.id}"/>
					<g:hiddenField name="productoId" value="${embarqueDetInstance?.compraDet?.producto?.id}"/>
					<g:field type="text" id="compraDetAuto" class="form-control"
						name="compraDet" required="true" value="${embarqueDetInstance?.compraDet}" />
				</f:field>
				<f:field property="cantidad" 
					widget-type="string" widget-class="form-control numeric" widget-autofocus="true"/>
				<f:field property="precio" widget-class="form-control" widget-type="text"/>
				<f:field property="kilosNetos" widget-class="form-control " widget-type="text"/>
				<f:field property="kilosEstimados"   widget-class="form-control " widget-type="text"/>
				<f:field property="importe"  widget-class="form-control " widget-type="text" 
					widget-required="true" />
				<f:field property="tarimas" widget-class="form-control " widget-type="text"/>
				<f:field property="contenedor" widget-class="form-control " 
						widget-type="string" widget-required="true"/>
			</f:with>
			
		</g:form>
	
	</div>					
 
	<div class="panel-footer">
		<div class="btn-group">
			<g:link class="btn btn-default btn-sm" 
				controller="embarque" action="edit" id="${embarqueDetInstance.embarque.id}" >
				<span class="glyphicon glyphicon-hand-left"></span> Cancelar
			</g:link>
			<button id="saveBtn" class="btn btn-success btn-sm">
					<span class="glyphicon glyphicon-ok"></span> Actualizar
			</button>
		</div>
	</div><!-- end .panel-footer -->

</div>




<script type="text/javascript">
	$(function(){
		//$(".numeric").autoNumeric({wEmpty:'zero',aSep:"",lZero: 'deny'});
		//$(".autonumeric").autoNumeric({vMin:'0.000'});
		//$("#cantidad").autoNumeric({vMin:'0.000'});

		$("#compraDetAuto").focusout(function(){
			console.log('Localizando precio...');
			$.ajax({
				url:"${createLink(controller:'proveedor',action:'localisarPrecio')}",
				data:{
					proveedorId:$("#proveedorId").val(),productoId:$("#productoId").val()
				},
				success:function(data){
					console.log('Exitosamente obtenemos: '+data.costoUnitario);
					$("#precio").val(data.costoUnitario);
				},
				error:function(request,status,error){
					console.log('Error: '+error);
				},
				complete:function(){
					console.log('Completado...');
				}
			});
		});

		$("#saveBtn").click(function(){
			console.log('Salvar la forma');
			$("form[name=updateForm]").submit();
		});

		$('form[name=updateForm]').submit(function(e){
			console.log("Desablidatndo submit button....");
    		$(this).children('input[type=submit]').attr('disabled', 'disabled');
    		var button=$("#saveBtn");
    		button.attr('disabled','disabled')
    		//.attr('value','Procesando...');
    		.html('Procesando...')
    		//e.preventDefault(); 
    		return true;
		});

		$("#kilosNetos").blur(function(){
			//Actualizamos el 
			var costo=$("#precio").val();
			var kilos=$(this).val();
			kilos=kilos/1000 // Precio por tonelada
			var importe=kilos*costo;
			importe=Math.round(importe*100)/100;
			console.log('Importe : '+importe);
			$("#importe").val(importe);
		});
		$("#kilosNetos").blur(function(){
			//Actualizamos el 
			var costo=$("#precio").val();
			var kilos=$(this).val();
			kilos=kilos/1000 // Precio por tonelada
			var importe=kilos*costo;
			importe=Math.round(importe*100)/100;
			$("#importe").val(importe);
		});
		
		
		
		$("#cantidad").focusout(function(){

			/*
			var cantidad=$(this).autoNumeric('get');

			var kilosPorMillar=$("#kilosPorMillar").val();
			var factor=$("#factorUnitario").val();
			if(isNaN(factor))
				factor=1;
			var kilosEstimados=(cantidad/factor)*kilosPorMillar;
			kilosEstimados=Math.round(kilosEstimados*100)/100;

			//console.log('Kilos estimados:' +cantidad+'/'+factor+' * '+kilosPorMillar);
			$("#kilosEstimados").val(kilosEstimados);	
			*/
		});

	});
	
</script>

<%--
<script>
	$(function(){
		
		
	});
</script>
--%>

