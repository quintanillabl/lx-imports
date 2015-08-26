<div class="btn-group">
	
	<g:if test="${!ventaInstance.cfdi}">
			<g:if test="${ventaInstance.clase=='generica'}">
				<g:link action="agregarConcepto" class="btn btn-primary" id="${ventaInstance.id}">
					<i class="icon-plus icon-white"></i>
		  			 Agregar concepto
				</g:link>
			</g:if>
			
			<g:if test="${ventaInstance.clase!='GENERICA'}">
				<button  class="btn btn-success" data-target="#agregarContendorDialog" data-toggle="modal">
					<i class="icon-plus icon-white"></i>
		  			 Agregar contenedor
		  		</button>
			</g:if>
			
			<button id="elimiarBtn" class="btn btn-danger" >
				<i class="icon-trash icon-white"></i>
				<g:message code="default.button.delete.label" default="Delete" />
			</button>
	</g:if>
	
	
</div>

<div id="gridDiv">
	<g:render template="partidas" bean="${ventaInstance}"/>
</div>

<div class="modal fade" id="agregarContendorDialog" tabindex="-1">
	<div class="modal-dialog ">
		<div class="modal-content">

			<form name="asignarContenedorForm" class="form-horizontal" >
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="myModalLabel">Agregar contenedor</h4>
				</div>
				<div class="modal-body ui-front">
					<g:hiddenField id="ventaId" name="ventaId" value="${ventaInstance.id}"/>
					<g:hiddenField id="contenedorId" name="contenedorId" value=""/>
					<g:field id="contenedor" type="text" name="contenedor" placeholder="Seleccione una contenedor" 
						class="form-control" required="true" autofocus="true"/>
				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
					<g:submitButton class="btn btn-info" name="aceptar"
							value="Asignar" />
				</div>
				
			</form>
			

		</div>
		<!-- moda-content -->
	</div>
	<!-- modal-di -->
</div>
<script type="text/javascript">
	$(function(){
		
		function selectedRows(){
				var res=[];
				var data=$("tr.selected").each(function(){
					var tr=$(this);
					res.push(tr.attr("id"));
				});
				return res;
		};

		$("tbody tr").on('click',function(){
			$(this).toggleClass("success selected");
		});

		$('body').on('shown.bs.modal', '#agregarContendorDialog', function () {
			$("#contenedor").val("");
			$("#contenedorId").val("");
          	$('[id$=contenedor]').focus();
      	});
      	$("#contenedor").autocomplete({
  			source:'${createLink(controller:'venta',action:'contenedoresPendientes') }',
  			minLength:2,
  			select:function(e,ui){
  		   		$("#contenedorId").val(ui.item.id);
  			}
      	});
      	$('form[name=asignarContenedorForm]').submit(function(e){
      	    e.preventDefault(); 
      	    var data=$(this).serialize();
      	    console.log('Asignando contenedor: '+data);
      	    $("#gridDiv").load(
      	    	"${createLink(action:'agregarContenedor')}",
      	    	data,
      	    	function(){
      	    		console.log("OK....");
      	    		$("#contenedor").val("");
					$("#contenedorId").val("");
      	    		$('#agregarContendorDialog').modal('hide');
      	    	}
      	    );
      	    return true;
      	});

      	$("#elimiarBtn").on('click',function(e){
			var res=selectedRows();
			if(res.length==0){
				alert('Debe seleccionar al menos un registro');
				return;
			}
			var ok=confirm('Eliminar  ' + res.length+' partida(s)?');
			if(!ok)
				return;
			console.log('Cancelando facturas: '+res);
			$.post("${createLink(controller:'venta',action:'eliminarPartidas')}",
				{partidas:JSON.stringify(res)}
			).done(function(data){
				location.reload();
			}).fail(function(jqXHR, textStatus, errorThrown){
				alert("Error: "+errorThrown);
			});
			
		});
	});
</script>

<%--
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
--%>
