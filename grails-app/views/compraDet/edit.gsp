<%@ page import="com.luxsoft.impapx.Compra" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="luxor">
	<g:set var="entityName" value="${message(code: 'compraDet.label', default: 'Partida de compra')}" scope="request"/>
	<g:set var="entity" value="${compraDetInstance}" scope="request" />
	<title>CompraDet  ${entity.id}</title>
	<asset:javascript src="forms/forms.js"/>
</head>
<body>

<content tag="header">
	Edición de partida ${entity.id} 
</content>
<content tag="subHeader">
	<lx:backButton label="Regresar a  Compra: ${entity.compra.folio} (Id:${entity.compra.id}) ${entity.compra.proveedor}"/>
</content>

<content tag="document">
	<div class="row">
	    <div class="col-lg-8">
	        <div class="ibox float-e-margins">
	            <div class="ibox-title">
	                <h5>Propiedades</h5>
	            </div>
	            <div class="ibox-content">
	                <lx:errorsHeader bean="${entity}"/>
	                <g:form action="updateForm" class="form-horizontal" action="update" method="PUT">  
	                    <g:hiddenField name="id" value="${entity?.id}" />
	                    <g:hiddenField name="version" value="${entity?.version}" />
	                    <f:with bean="entity">
	                    	<div class="form-group">
	                    		<label for="productoField" class="control-label col-sm-3">Producto</label>
	                    		<div class="col-sm-9">
	                    			<g:hiddenField id="productoId" name="producto.id" value="${entity.producto.id}"/>
	                    			<input id="productoField" name="productoField" class="form-control" value="${entity.producto}">
	                    		</div>
	                    	</div>
	                    	<f:field property="solicitado" widget="numeric" wrapper="bootstrap3"/>
	                    	
	                    	<f:display property="entregado" wrapper="bootstrap3"/>
	                    </f:with>
	                    <div class="form-group">
	                        <div class="col-lg-offset-3 col-lg-9">
	                            <button id="saveBtn" class="btn btn-primary ">
	                                <i class="fa fa-floppy-o"></i> Actualizar
	                            </button>
	                        </div>
	                    </div>
	                </g:form>
	            </div>
	        </div>
	    </div>
	</div>

	<script type="text/javascript">
		$(function(){
			$("#productoField").autocomplete({
					source:'<g:createLink controller="producto" action="productosJSONList"/>',
					minLength:3,
					select:function(e,ui){
						console.log('Valor seleccionado: '+ui.item.id);
						$("#productoId").val(ui.item.id);
					}
			});
			$("#solicitado").autoNumeric({vMin:'0.000'});
			
			$('form[name=updateForm]').submit(function(e){
	    		var button=$("#saveBtn");
	    		//button.attr('disabled','disabled')
	    		//.html('Procesando...');
	    		$(".numeric",this).each(function(index,element){
	    		  var val=$(element).val();
	    		  var name=$(this).attr('name');
	    		  var newVal=$(this).autoNumeric('get');
	    		  $(this).val(newVal);
	    		  console.log('Enviando elemento numerico con valor:'+name+" : "+val+ " new val:"+newVal);
	    		});

	    		//e.preventDefault(); 
	    		return true;
			});

		})
	</script>
</content>



	




</body>
</html>
