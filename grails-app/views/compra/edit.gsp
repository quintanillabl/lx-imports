
<%@ page import="com.luxsoft.impapx.Compra" %>
<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="luxor">
	<g:set var="entityName" value="${message(code: 'compra.label', default: 'Compra')}" scope="request"/>
	<g:set var="entity" value="${compraInstance}" scope="request" />
	<asset:javascript src="forms/forms.js"/>
	<title>Compra ${entity.id}</title>
</head>

<body>
	
<content tag="header"> Compra Folio:${compraInstance.folio}  (Id:${compraInstance.id})</content>
<content tag="subHeader">
	<ol class="breadcrumb">
	    <li><g:link action="index">${entityName}(s)</g:link></li>
	    <li><g:link action="create">Alta</g:link></li>
	    <li ><g:link action="show" id="${entity.id}">Consulta</g:link></li>
	    <li class="active"><strong><g:link action="edit" id="${entity.id}">Edición</g:link></strong></li>
	</ol>
</content>

<content tag="document">

	<div class="wrapper wrapper-content animated fadeInRight">
	    <div class="row">
	        <div class="col-lg-12">
	            <div class="ibox float-e-margins">
	                %{-- <lx:iboxTitle title="Propiedades"/> --}%
	                <div class="ibox-content">
	                	<ul class="nav nav-tabs" role="tablist">
	                	   	<li class="active">
	                	   		<a data-target="#partidas" data-toggle="tab"> <i class="fa fa-th-list"></i> Partidas</a>
	                	   	</li>
	                	   	<li><a href="#compra" data-toggle="tab"> <i class="fa fa-pencil"></i>  Propiedades</a></li>
	                	</ul>
            	  		<div class="tab-content"> 
            	  			<div class="tab-pane active" id="partidas">
            	  				<div class="row toolbar-panel">
            	  					<div class="col-md-6">
            	  						<div class="btn-group">
            	  							<button class="btn btn-outline btn-primary" 
            	  								data-toggle="modal" data-target="#agregarPartidaDialog">
            	  								<i class="fa fa-plus"></i> Agregar
            	  							</button>
            	  					    	
            	  					    	
            	  					  		<button id="eliminarBtn" class="btn btn-danger">
            	  					  			<i class="fa fa-trash"></i> Eliminar
            	  					  		</button>
            	  					    </div>
            	  					</div>
            	  				</div>
            					<g:render template="partidas"/>
            				</div> 	

				  			<div class="tab-pane" id="compra">
				  				<div class="row">
				  					<div class="col-md-6">
				  						<br>
				  						<g:render template="updateForm"/>	
				  					</div>
				  				</div>
				  				
							</div> 			
            	  		</div>
	                </div>
	            </div>
	        </div>
	    </div>

	    <div class="modal fade" id="agregarPartidaDialog" tabindex="-1">
	    	<div class="modal-dialog ">
	    		<div class="modal-content">
	    			<div class="modal-header">
	    				<button type="button" class="close" data-dismiss="modal"
	    					aria-hidden="true">&times;</button>
	    				<h4 class="modal-title" id="myModalLabel">Agregar partida</h4>
	    			</div>
	    			<g:form action="agregarPartida" class="form-horizontal">
	    				<g:hiddenField name="compra.id" value="${compraInstance.id}"/>
	    				<div class="modal-body ui-front">
	    					<div class="form-group">
	    						<label for="productoField" class="control-label col-sm-2">Producto</label>
	    						<div class="col-sm-10">
	    							<g:hiddenField id="productoId" name="producto.id"/>
	    							<input id="productoField" name="productoField" class="form-control" value="">
	    						</div>
	    					</div>
	    					<div class="form-group">
	    						<label for="cantidad" class="control-label col-sm-2">Cantidad</label>
	    						<div class="col-sm-10">
	    							<input name="cantidad" class="form-control" value="">
	    						</div>
	    					</div>
	    				</div>
	    				
	    				<div class="modal-footer">
	    					<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
	    					<g:submitButton class="btn btn-info" name="aceptar"
	    							value="Salvar" />
	    				</div>
	    			</g:form>
	    
	    		</div>
	    		<!-- moda-content -->
	    	</div>
	    	<!-- modal-di -->
	    </div>

	</div>

	<script type="text/javascript">
		$(function(){

			
			$(".grid tbody tr").hover(function(){
				$(this).toggleClass("info");
			});
			$(".grid tbody tr").click(function(){
				$(this).toggleClass("success selected");
			});

			$('body').on('shown.bs.modal', '.modal', function () {
			    $('[id$=productoField]').focus();
			});

			$("#productoField").autocomplete({
					source:'<g:createLink controller="producto" action="productosJSONList"/>',
					minLength:3,
					select:function(e,ui){
						console.log('Valor seleccionado: '+ui.item.id);
						$("#productoId").val(ui.item.id);
					}
			});
			$("#cantidad").autoNumeric({vMin:'0.000'});
			
			$("#eliminarBtn").click(function(e){
				eliminar();
			});

			function selectedRows(){
				var res=[];
				var data=$("tbody tr.selected").each(function(){
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
				console.log('Eliminando partidas: '+res);
				
				$.ajax({
					url:"${createLink(action:'eliminarPartida')}",
					data:{
						compraId:${compraInstance.id},partidas:JSON.stringify(res)
					},
					success:function(response){
						
						location.reload();
					},
					error:function(request,status,error){
						alert("Error: "+status);
					}
				});
			}
			//$('.chosen-select').chosen();
		});

	</script>

</content>
	
	

	

</body>
</html>



