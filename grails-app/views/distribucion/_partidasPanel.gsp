<%@ page import="com.luxsoft.impapx.DistribucionDet" %>
<div class="btn-group">

	<g:link action="selectorDePartidas" class="btn btn-primary" id="${distribucionInstance?.id}"
		params="[embarqueId:distribucionInstance.embarque.id]">
		<i class="icon-plus icon-white"></i>
		<g:message code="default.button.add.label" default="Agregar" />
	</g:link>

	<button id="elimiarBtn" class="btn btn-danger" >
		<i class="icon-trash icon-white"></i>
		<g:message code="default.button.delete.label" default="Delete" />
	</button>

  	<g:link action="selectorParaFechaDeEntrega" class="btn " id="${distribucionInstance?.id}" class="btn btn-default btn-outline"
		params="[embarqueId:distribucionInstance.embarque.id]">
		<i class="icon-plus icon-white"></i>
		Entrega
	</g:link>
  	
  	<button  class="btn btn-default btn-outline" data-target="#programacionDeEntrega" data-toggle="modal">
  		Reporte
  	</button>
	
</div>



<div id="facturasGrid">
	<g:render template="partidasGrid" bean="${distribucionInstance}"
		model="[partidas:distribucionInstance.partidas]" />
</div>
<g:jasperReport jasper="ListaDeDistribucion" format="PDF,HTML,XLS" name="Imprimir Lista">
	<g:hiddenField name="ID" value="${distribucionInstance.id}"/>
</g:jasperReport>


<div class="modal fade" id="programacionDeEntrega" tabindex="-1">
	<div class="modal-dialog ">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="myModalLabel">Programación de entrega</h4>
			</div>
			
			<div class="modal-body">
				<g:jasperReport jasper="ProgramacionDeEntregaDeContenedores" format="PDF" name="" buttonPosition="bottom">
					<g:hiddenField name="ID" value="${distribucionInstance.id}"/>
					<input id="ICONTACTO" type="text" name="ICONTACTO" value="" placeholder="Contacto" class="input-xxlarge">
					<input id="ITELEFONO" type="text" name="ITELEFONO" value="" placeholder="Dirección" class="input-xxlarge">
					<input id="IDIRECCION" type="text" name="IDIRECCION" value="" placeholder="Teléfono" class="input-xxlarge">
				</g:jasperReport>
			</div>
			
			<div class="modal-footer">
				
			</div>
			

		</div>
		<!-- moda-content -->
	</div>
	<!-- modal-di -->
</div>



<div class="modal hide fade" id="asignarEntregaDialog" tabindex=-1 role="dialog" 
	aria-labelledby="myModalLabel">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		<h4 class="myModalLabel">Asignacion de entrega</h4>
	</div>
	
	<div class="modal-body">
		<fieldset>
		
			<g:form action="asignarFechaEntrega">
				
				<g:hiddenField name="id" value="${distribucionInstance.id}"/>
				<input id="fechaEntrega" class="datepicker" type="text" name="fechaEntrega" placeholder="Fecha" class="input-large"  required="required"/>
				<input id="contenedorEntrega" 	 type="text" name="contenedor"   placeholder="Contenedor" class="input-xxlarge" required="required"/>
				
				<div class="form-actions">
					<a href="#" class="btn" data-dismiss="modal">Cancelar</a>
					%{-- <g:submitToRemote 
						url="[controller:'distribucion', action:'asignarFechaEntrega']"
						before="hideEntregaForm()"
						onComplete="reloadPage();"
						class="btn btn-primary"
						value="Aceptar"
						 >
					</g:submitToRemote> --}%
				</div>
				
			</g:form>
			
		</fieldset>
	</div>
	
</div>


<div class="modal hide fade" id="asignarEntradaDialog" tabindex=-1 role="dialog" 
	aria-labelledby="myModalLabel">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		<h4 class="myModalLabel">Registro de contenedores entregados</h4>
	</div>
	
	<div class="modal-body">
		<fieldset>
		
			<g:form action="asignarFechaEntrada">
				
				<g:hiddenField name="id" value="${distribucionInstance.id}"/>
				<input id="fechaEntrada" class="datepicker" type="text" name="fechaEntrada" placeholder="Fecha" class="input-large"  required="required"/>
				<input id="contenedorEntrada" 	 type="text" name="contenedor"   placeholder="Contenedor" class="input-xxlarge" required="required"/>
				
				<div class="form-actions">
					<a href="#" class="btn" data-dismiss="modal">Cancelar</a>
					%{-- <g:submitToRemote 
						url="[controller:'distribucion', action:'asignarFechaEntrada']"
						before="hideEntradaForm()"
						onComplete="reloadPage();"
						class="btn btn-primary"
						value="Aceptar"
						 >
					</g:submitToRemote> --}%
				</div>
				
			</g:form>
			
		</fieldset>
	</div>
	
</div>

<script type="text/javascript">
	$(function(){
		$('#partidasGrid').dataTable({
			aLengthMenu: [[20, 40, 60, 100, -1], [20, 40,60, 100, "Todos"]],
            responsive: true,
            "language": {
				"url": "${assetPath(src: 'datatables/dataTables.spanish.txt')}"
    		},
    		"order": []
        });
		$("tbody tr").on('click',function(){
			$(this).toggleClass("success selected");
		});
        function selectedRows(){
			var res=[];
			var data=$("tr.selected").each(function(){
				var tr=$(this);
				res.push(tr.attr("id"));
			});
			return res;
		};

        $("#elimiarBtn").click(function(e){
        	var res=selectedRows();
        	if(res.length==0){
        		alert('Debe seleccionar al menos un registro');
        		return;
        	}
        	var ok=confirm('Quitar  ' + res.length+' partida(s)?');
        	if(!ok)
        		return;
        	console.log('Removiendo facturas: '+res);

        	$.post(
        		"${createLink(controller:'distribucion',action:'eliminarPartida')}",
        		{
        			partidas:JSON.stringify(res)
        			,distribucionId:${distribucionInstance.id}
        		}).done(function(data){
        			location.reload();
        		}).fail(function(jqXHR, textStatus, errorThrown){
        			console.log(errorThrown);
        			alert("Error: "+errorThrown);
        		});
        	
        });
	});
</script>

<%--
<r:script>
	
	$(function(){
	
		$("#partidasGrid").dataTable({
			"sDom": "<'row'<'span6'l><'span6'f>r>t<'row'<'span6'i><'span6'p>>",
			aLengthMenu: [[100, 150, 200, 250, -1], [100, 150, 200, 250, "Todos"]],
          	iDisplayLength: 100,
          	"oLanguage": {
      			"oPaginate": {
        			"sFirst": "Inicio",
        			"sNext": "Siguiente",
        			"sPrevious": "Página anterior",

      				},
      				"sSearch": "Filtrar:",
    			},
    			"sEmptyTable": "No data available in table",
    			"sLoadingRecords": "Please wait - loading...",
    			"sProcessing": "DataTables is currently busy",
    			"bPaginate": false,
    			"bInfo": false,
    			"sSearch": "Filtrar:"

		});
			
		$("#elimiarBtn").click(function(e){
			eliminar();
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
				url:"${createLink(controller:'distribucion',action:'eliminarPartida')}",
				data:{
					partidas:JSON.stringify(res)
					,distribucionId:${distribucionInstance.id}
				},
				success:function(response){
					//$("#facturasGrid").html(response);
					location.reload();
				},
				error:function(request,status,error){
					alert("Error: "+status);
				}
			});
		}
		
		
		
		$("#partidasGrid tbody tr").live('click',function(){
			console.log('Ultima seleccion:'+$(this).last().attr('id'))
		});
		
		$("#fraccionarBtn").live('click',function(){
			var res=selectedRows();
			if(res.length!=1){
				alert('Debe seleccionar solo un registro');
				return;
			}
			console.log('Fraccionando: '+res);
			$('#fraccionarDialog').modal('toggle')
		});
		
		$('#asignarEntregaDialog').on('show', function(){
			$("#fechaEntrega").val("");
			$("#contenedorEntrega").val("")
		});
		
		$('#asignarEntradaDialog').on('show', function(){
			$("#fechaEntrada").val("");
			$("#contenedorEntrada").val("")
		});
		
		$("#contenedorEntrega").autocomplete(
			{
				source:'${createLink(controller:'distribucion',action:'contenedoresJSON',params:['distribucionId':distribucionInstance.id]) }',
				data:{
					distribucionId:${distribucionInstance.id}
				},
				minLength:3,
				select:function(e,ui){
		   			//$("#facturaGastosId").val(ui.item.id);
		   			console.log('Asignando contenedor..')
			}
		});
		
		$("#contenedorEntrada").autocomplete(
			{
				source:'${createLink(controller:'distribucion',action:'contenedoresJSON',params:['distribucionId':distribucionInstance.id]) }',
				data:{
					distribucionId:${distribucionInstance.id}
				},
				minLength:3,
				select:function(e,ui){}
		});
		
		
					
	});
	
	function hideEntregaForm(){
		$("#asignarEntregaDialog").modal('hide');
		
	}
	function hideEntradaForm(){
		$("#asignarEntradaDialog").modal('hide');
	}
	
	function reloadPage(){
		location.reload();
		
	}		
				
</r:script>
--%>