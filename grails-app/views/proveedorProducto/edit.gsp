
<%@ page import="com.luxsoft.impapx.ProveedorProducto" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="luxor">
	<title>Producto ${proveedorProductoInstance.producto.clave}</title>
	<asset:javascript src="forms/forms.js"/>
</head>
<body>

<content tag="header">Proveedor: ${proveedorProductoInstance.proveedor} Prod:${proveedorProductoInstance.producto.clave}</content>

<content tag="subHeader">
	<ol class="breadcrumb">
    	<li><g:link controller="proveedor" action="edit" id="${proveedorProductoInstance.proveedor.id}">Productos del proveedor</g:link></li>
    	<li class="active"><strong>Edición</strong></li>
	</ol>
</content>

<content tag="document">
	<div class="row">
		<div class="col-lg-8">
			<div class="ibox float-e-margins">
				<lx:iboxTitle title="Propiedades"/>
				<lx:errorsHeader bean="${proveedorProductoInstance}"/>
			    <div class="ibox-content">
					<g:form  name="updateForm" class="form-horizontal" action="update" method="PUT" >
						<f:with bean="proveedorProductoInstance">
							<g:hiddenField name="id" value="${proveedorProductoInstance?.id}"/>
							<g:hiddenField name="version" value="${proveedorProductoInstance?.version}"/>
							<g:hiddenField name="producto.id" value="${proveedorProductoInstance.producto.id}"/>
							<f:display property="producto"/>
							<f:field property="gramos" widget="numeric"/>
							<f:field property="codigo" widget-class="form-control"/>
							<f:field property="descripcion" widget-class="form-control"/>
							<f:field property="costoUnitario" widget="money"/>
						</f:with>
						<div class="form-group">
							<div class="col-lg-offset-2 col-lg-10">
								<button id="saveBtn" class="btn btn-primary ">
									<i class="fa fa-floppy-o"></i> Salvar
								</button>
								<lx:backButton controller="proveedor" action="edit" id="${proveedorProductoInstance.proveedor.id}"/>
							</div>
						</div>
					</g:form>
			    </div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			$(".numeric").autoNumeric('init',{vMin:'0.0000'});
			$(".money").autoNumeric('init',{mRound:'B',aSign: '$'});
			$('form[name=updateForm]').submit(function(e){
				//e.preventDefault(); 
	    		var button=$("#saveBtn");
	    		button.attr('disabled','disabled')
	    		 .html('Procesando...');
	    		$(".money, .numeric",this).each(function(index,element){
	    		  var val=$(element).val();
	    		  var name=$(this).attr('name');
	    		  var newVal=$(this).autoNumeric('get');
	    		  $(this).val(newVal);
	    		  console.log('Enviando elemento numerico con valor:'+name+" : "+val+ " new val:"+newVal);
	    		});
	    		return true;
			});	

		})
	</script>
</content>

</body>
</html>
