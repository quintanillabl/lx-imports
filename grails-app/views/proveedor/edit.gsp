
<%@ page import="com.luxsoft.impapx.Proveedor" %>
<!DOCTYPE html>
<html>
<head>
	<title>Proveedor ${proveedorInstance.id}</title>
	<meta name="layout" content="luxor">
	<asset:javascript src="forms/forms.js"/>
</head>
<body>

<content tag="header">
	Proveedor ${proveedorInstance.nombre} 
</content>

<content tag="subHeader">
	<ol class="breadcrumb">
    	<li><g:link action="index">Proveedores</g:link></li>
    	<li><g:link action="create">Alta</g:link></li>
    	<li><g:link action="show" id="${proveedorInstance.id}">Consulta</g:link></li>
    	<li class="active"><strong>Edición</strong></li>
	</ol>
</content>

<content tag="document">
	<div class="row">
		<div class="col-lg-12">
			<div class="ibox float-e-margins">
				
			    <div class="ibox-content">
					<lx:errorsHeader bean="${proveedorInstance}"/>
					<ul class="nav nav-tabs" role="tablist">
					    <li  class="${tab=='propiedades'?'active':''}">
					    	<a href="#home" aria-controls="home" role="tab" data-toggle="tab">Propiedades</a>
					    </li>
					    <li role="presentation">
					    	<a href="#productos" aria-controls="productos" data-toggle="tab">Productos</a>
					    </li>
					    <g:if test="${proveedorInstance.agenciaAduanal}">
					    	<li class="${tab=='agentes'?'active':''}">
					    		<a href="#agentes" aria-controls="productos" data-toggle="tab">Agentes</a>
					    	</li>
					    </g:if>
					    
					</ul>
					<div class="panel-body">
						<div class="tab-content">
						  	<div role="tabpanel" class="tab-pane fade in ${tab=='propiedades'?'active':''}" id="home">
						  		<g:render template="editForm"/>
						  	</div>
						 	
						 	<div role="tabpanel" class="tab-pane fade" id="productos">
						 		<div class="row">
						 			<div class="col-md-12">
						 				<div class="btn-group">
						 				    <lx:createButton label="Agregar" action="selectorDeProductos" id="${proveedorInstance.id}"/>
						 				    <button id="asignarCosto" class="btn btn-outline btn-default" >
						 				    	<i class="fa fa-tag"></i> Asingar costo
						 				    </button>
						 				</div>
						 				<g:render template="productos"/>
						 			</div>
						 		</div>
						  	</div>

						  	<div class="tab-pane fade in ${tab=='agentes'?'active':''}" id="agentes">
						 		<div class="row">
						 			<div class="col-md-12">
						 				<div class="btn-group">
						 				    <button data-target="#agregarAgenteDialog" data-toggle="modal" class="btn btn-outline btn-default"> Agregar</button>
						 				</div>
						 				<g:render template="agentes"/>
						 			</div>
						 		</div>
						  	</div>

						</div>
					</div>

					
			    </div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="asignarPrecioDialog" tabindex="-1">
		<div class="modal-dialog ">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Asignar precio</h4>
				</div>
				<form name="asignarCostoForm" class="form-horizontal" >
					<div class="modal-body">
						<div class="form-group">
							<label for="costoUnitario" class="control-label col-sm-2">Precio</label>
							<div class="col-sm-10">
								<input id="costoUnitario" name="costoUnitario" class="form-control" value="" type="text">
							</div>
						</div>
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


	<div class="modal fade" id="agregarAgenteDialog" tabindex="-1">
		<div class="modal-dialog ">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Agregar agente</h4>
				</div>
				<g:form action="agregarAgenteAduanal" class="form-horizontal" >
					<g:hiddenField name="id" value="${proveedorInstance.id}"/>
					<div class="modal-body">
						<div class="form-group">
							<label for="agente" class="control-label col-sm-2">Nombre</label>
							<div class="col-sm-10">
								<input  id="agente" name="agente" class="form-control" value="" type="text">
							</div>
						</div>
					</div>
					
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
						<g:submitButton class="btn btn-info" name="aceptar"
								value="Agregar" />
					</div>
				</g:form>
	
			</div>
			<!-- moda-content -->
		</div>
		<!-- modal-di -->
	</div>

	<script type="text/javascript">
		$(function(){
			$("#costoUnitario").autoNumeric({vMin:'0.000'});

			$(".grid tbody tr").hover(function(){
				$(this).toggleClass("info");
			});
			$(".grid tbody tr").click(function(){
				$(this).toggleClass("success selected");
			});
			function selectedRows(){
				var res=[];
				var data=$("tbody tr.selected").each(function(){
					var tr=$(this);
					res.push(tr.attr("id"));
				});
				return res;
			};

			function clearAllRows(){
				$("#grid tbody tr").removeClass("success selected");
			};	

			$("#asignarCosto").click(function(e){
				var res=selectedRows();
				if(res==""){
					alert("Debe seleccionar al menos una factura");
					return
				}
				$('#asignarPrecioDialog').modal('show');
				//asignarCosto();
				//e.stopPropagation();
			});

			$('form[name=asignarCostoForm]').submit(function(e){
				e.preventDefault(); 
				var res=selectedRows();
				var costoUnitario=$("#costoUnitario").autoNumeric('get');
				console.log('Asignando costo unitario: '+costoUnitario+' a partidas: '+res);
				$.post(
					"${createLink(controller:'proveedor',action:'actualizarCostoUnitarioEnProductos')}",
					{costoUnitario:costoUnitario,partidas:JSON.stringify(res)}
				).done(function(data){
					console.log('Rres: '+data.costoUnitario);
					$('.selected td[name=costoUnitario]').text(data.costoUnitario);
					$('tbody tr.selected').removeClass('text-danger');
					$("#asignarPrecioDialog").modal("hide");
					clearAllRows();
				}).fail(function(jqXHR, textStatus, errorThrown){
					console.log(errorThrown);
					alert("Error asignando facturas: "+errorThrown);
				});

				
	    		return true;
			});

			
		});
	</script>
</content>

</body>
</html>




