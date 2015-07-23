<div class="btn-group">
	
	
	<g:if test="${ventaInstance.clase=='generica'}">
		<g:link action="agregarConcepto" class="btn btn-primary" id="${ventaInstance.id}">
			<i class="icon-plus icon-white"></i>
  			 Agregar concepto
		</g:link>
	</g:if>
	
	<g:if test="${ventaInstance.clase!='generica'}">
		<button  class="btn btn-success" data-target="#agregarContendorDialog" data-toggle="modal">
			<i class="icon-plus icon-white"></i>
  			 Agregar contenedor
  		</button>
	</g:if>
	
	<button id="elimiarBtn" class="btn btn-danger" >
		<i class="icon-trash icon-white"></i>
		<g:message code="default.button.delete.label" default="Delete" />
	</button>
	
</div>

<div id="gridDiv">
	<g:render template="partidas" bean="${ventaInstance}"/>
</div>

<div class="modal hide fade" id="agregarContendorDialog" tabindex=-1 role="dialog" 
	aria-labelledby="myModalLabel">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		<h4 class="myModalLabel">Agregar contenedor</h4>
	</div>
	
	<div class="modal-body">
		
		<g:formRemote name="agregar" class="form-horizontal" id="${ventaInstance.id}"
			url="[controller:'venta',action:'agregarContenedor']"
			update="gridDiv"
			onFailure="showError(XMLHttpRequest,textStatus,errorThrown);"
			onSuccess="success(data,textStatus)"
			>
			<g:hiddenField name="ventaId" value="${ventaInstance.id}"/>
			<g:hiddenField name="contenedorId" value=""/>
			<g:field type="text" name="contenedor" placeholder="Seleccione una contenedor" 
				class="input-xxlarge" required="true" autofocus="true"/>
			
			<div class="form-actions">
				<a href="#" class="btn" data-dismiss="modal">Cancelar</a>
				<button type="submit" class="btn btn-primary" >
					<i class="icon-ok icon-white"></i>
					Aceptar
				</button>
			</div>
			
		</g:formRemote>
	</div>
	
</div>

<r:script>
$(function(){

	$('#agregarContendorDialog').on('show', function(){
		$("#contenedor").val("");
	});
	
		
	$("#partidasGrid").dataTable({
		aLengthMenu: [[100, 150, 200, 250, -1], [100, 150, 200, 250, "Todos"]],
        iDisplayLength: 50,
        "oLanguage": {
      		"sUrl":"<g:resource dir="js" file="dataTables.spanish.txt" />"
	    },
    	"aoColumnDefs": [
        	{ "sType": "numeric","bSortable": true,"aTargets":[0] }
         ],
         "bPaginate": false  
	});
	
	$("#elimiarBtn").live('click',function(e){
		eliminar();
	});
	
	function selectedRows(){
			var res=[];
			var data=$("tr.selected").each(function(){
				var tr=$(this);
				res.push(tr.attr("id"));
			});
			return res;
	}
	
	function eliminar(){
			var res=selectedRows();
			if(res.length==0){
				alert('Debe seleccionar al menos un registro');
				return;
			}
			var ok=confirm('Eliminar  ' + res.length+' partida(s)?');
			if(!ok)
				return;
			console.log('Cancelando facturas: '+res);
			$.ajax({
				url:"${createLink(controller:'venta',action:'eliminarPartidas')}",
				data:{
					partidas:JSON.stringify(res)
				},
				success:function(response){
					//$("#gridDiv").html(response);
					location.reload();
					
				},
				error:function(request,status,error){
					alert("Error: "+status);
				}
			});
	}
	
	$("#contenedor").autocomplete(
		{
			source:'${createLink(controller:'venta',action:'contenedoresPendientes') }',
			minLength:2,
			select:function(e,ui){
		   		$("#contenedorId").val(ui.item.id);
		}
	});
	
	
});	
		
function showError(XMLHttpRequest,textStatus,errorThrown){
	console.log('Error: '+errorThrown);
	
}

function success(data,textStatus){
	console.log('Hiding dialog....');
	$('#agregarContendorDialog').modal('hide');
}

		
		
</r:script>
