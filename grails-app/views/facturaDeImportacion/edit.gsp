<%@ page import="com.luxsoft.impapx.FacturaDeImportacion" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="luxor">
	<title>Facturas de importación</title>
	<asset:javascript src="forms/forms.js"/>
</head>
<body>

<content tag="header">
	Factura de importación Id: ${facturaDeImportacionInstance.id}
</content>
<content tag="subHeader">
	<ol class="breadcrumb">
		<li><g:link action="index">Facturas</g:link></li>
		<li><g:link action="create">Alta</g:link></li>
		<g:if test="${facturaDeImportacionInstance.requisitado<=0.0}">
			<li><g:link action="show" id="${facturaDeImportacionInstance.id}">Consulta</g:link></li>
		</g:if>
		<li class="active"><strong>Edición</strong></li>
	</ol>
</content>

<content tag="document">
	<div class="ibox float-e-margins">
		<lx:iboxTitle title="Propiedades"/>
	    <div class="ibox-content">
			<ul class="nav nav-tabs" id="mainTab">
				<li class="active" ><a href="#facturasPanel" data-toggle="tab">Factura</a></li>
				<li><a href="#embarquesPanel" data-toggle="tab">Embarques</a></li>
				<li><a href="#contenedoresPanel" data-toggle="tab">Contenedores</a></li>
			</ul>
			<div class="tab-content"> 
				<div class="tab-pane active" id="facturasPanel">
					<div class="row">
						<br>
						<g:form name="editForm" class="form-horizontal" action="update" method="PUT">
							<g:hiddenField name="id" value="${facturaDeImportacionInstance.id}"/>
							<g:hiddenField name="version" value="${facturaDeImportacionInstance.version}"/>
							<f:with bean="facturaDeImportacionInstance">
							<div class="col-md-6">
								<f:display property="proveedor" widget-class="form-control" 
									wrapper="bootstrap3" widget-required="true"/>
								<f:field property="fecha" wrapper="bootstrap3" widget-required="true"/>
								<f:field property="vencimiento" wrapper="bootstrap3"  />
								<f:display property="moneda" wrapper="bootstrap3"/>
								<f:field property="tc" widget-class="form-control" wrapper="bootstrap3"/>
								<f:field property="documento" widget-class="form-control" wrapper="bootstrap3"/>
								<f:field property="comentario" widget-class="form-control" wrapper="bootstrap3"/>
							</div>
							<div class="col-md-6">
								<f:field property="importe" widget="money" wrapper="bootstrap3" />
								<f:field property="subTotal" widget="money" wrapper="bootstrap3"/>
								<f:display property="descuentos" widget="money" wrapper="bootstrap3"/>
								<f:display property="impuestos" widget="money" wrapper="bootstrap3"/>
								<f:field property="total" widget="money" wrapper="bootstrap3"/>
								<f:display property="requisitado" widget="money" wrapper="bootstrap3"/>
							</div>
							</f:with>
							<div class="form-group">
							    <div class="col-lg-offset-2 col-lg-9">
							        <lx:backButton/>
							        <button id="saveBtn" class="btn btn-primary ">
							            <i class="fa fa-floppy-o"></i> Actualizar
							        </button>
							        <g:if test="${facturaDeImportacionInstance.requisitado<=0.0}">
							        	<a  class="btn btn-danger " data-toggle="modal" data-target="#deleteDialog"><i class="fa fa-trash"></i> Eliminar</a> 
							        </g:if>
							        
							    </div>
							</div>
						</g:form>
					</div>
					
				</div>
				<div class="tab-pane" id="embarquesPanel">
					
				</div>
	  		</div>

			
	    </div>
	</div>

	<div class="modal fade" id="deleteDialog" tabindex="-1">
		<div class="modal-dialog ">
			<div class="modal-content">
				<g:form action="delete" class="form-horizontal" method="DELETE">
					<g:hiddenField name="id" value="${facturaDeImportacionInstance.id}"/>

					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">Eliminar el registro ${facturaDeImportacionInstance.id}</h4>
					</div>
					<div class="modal-body">
						<p><small>${facturaDeImportacionInstance}</small></p>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
						<g:submitButton class="btn btn-danger" name="aceptar" value="Eliminar" />
					</div>
				</g:form>
			</div><!-- moda-content -->
			
		</div><!-- modal-di -->
		
	</div>


	<script type="text/javascript">
		$(function(){

			$(".numeric").autoNumeric('init',{vMin:'0'},{vMax:'9999'});
			$(".money").autoNumeric('init',{wEmpty:'zero',mRound:'B',aSign: '$'});
			$(".tc").autoNumeric('init',{vMin:'0.0000'});
			$(".porcentaje").autoNumeric('init',{altDec: '%', vMax: '99.99'});
			$('.date').bootstrapDP({
			    format: 'dd/mm/yyyy',
			    keyboardNavigation: false,
			    forceParse: false,
			    autoclose: true,
			    todayHighlight: true
			});
			$("#importe").on('blur',function(){
				var importe=$("#importe").autoNumeric('get');
				$("#subTotal").autoNumeric('set',importe);
				$("#total").autoNumeric('set',importe);
			});

			$("#descuento").on('blur',function(){
				var importe=$("#importe").autoNumeric('get');
				var desc=$(this).autoNumeric('get');
				importe=importe-desc;
				$("#subTotal").autoNumeric('set',importe);
				$("#total").autoNumeric('set',importe);
			});
			$('form[name=editForm]').submit(function(e){
			    console.log("Desablidatndo submit button....");

			    var button=$("#saveBtn");
			    button.attr('disabled','disabled')
			    .html('Procesando...');

			    $(".money,.porcentaje,.numeric,.tc",this).each(function(index,element){
			      var val=$(element).val();
			      var name=$(this).attr('name');
			      var newVal=$(this).autoNumeric('get');
			      $(this).val(newVal);
			    });
			    //e.preventDefault(); 
			    return true;
			});
		});
	</script>
</content>

 	
	
	
</body>
</html>
