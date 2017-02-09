<%@ page import="com.luxsoft.lx.contabilidad.ProcesadorDePoliza" %>
<!doctype html>
<html>
<head>
	<title>Procesador ${procesadorDePolizaInstance.id}</title>
	<meta name="layout" content="luxor">
	<asset:javascript src="forms/forms.js"/>
</head>
<body>

<content tag="header">Procesador: ${procesadorDePolizaInstance}</content>
<content tag="subHeader">
	<ol class="breadcrumb">
    	<li><g:link action="index">Catálogo</g:link></li>
    	
	</ol>
</content>
	
<content tag="document">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-lg-7">
				<lx:iboxTitle title="Cuenta contable  "/>
				
				<div class="ibox-content">
					<g:form name="updateForm" action="update" class="form-horizontal" method="PUT">	
						<g:hiddenField name="id" value="${procesadorDePolizaInstance.id}"/>
						<g:hiddenField name="version" value="${procesadorDePolizaInstance.version}"/>
						<f:with bean="procesadorDePolizaInstance">
							<f:field property="subTipo" widget-autofocus="true" widget="mayusculas" wrapper="bootstrap3"  />
							<f:field property="tipo" wrapper="bootstrap3" widget-class="form-control"  />
							<f:field property="descripcion" wrapper="bootstrap3" widget-class="form-control" />
							
							<f:field property="service" wrapper="bootstrap3" widget-class="form-control" lable="Servicio"/>	
							

							<f:field property="label" wrapper="bootstrap3" widget-class="form-control" label="Etiqueta"/>
						</f:with>
						<div class="form-group">
							<div class="col-lg-offset-3 col-lg-10">
								<lx:backButton label="Procesadores" />
								<button id="saveBtn" class="btn btn-primary ">
									<i class="fa fa-floppy-o"></i> Salvar
								</button>
								<a href="" class="btn btn-danger " data-toggle="modal" 
									data-target="#deleteDialog">
									<i class="fa fa-trash"></i> Eliminar
								</a> 

							</div>
						</div>
					</g:form>
				</div>
			</div>

			
		</div>
		

		<div class="modal fade" id="deleteDialog" tabindex="-1">
			<div class="modal-dialog ">
				<div class="modal-content">
					<g:form action="delete" class="form-horizontal" method="DELETE">
						<g:hiddenField name="id" value="${procesadorDePolizaInstance.id}"/>

						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="myModalLabel">Eliminar procesador </h4>
						</div>
						<div class="modal-body">
							<p><small>${procesadorDePolizaInstance}</small></p>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
							<g:submitButton class="btn btn-danger" name="aceptar" value="Eliminar" />
						</div>
					</g:form>
				</div><!-- moda-content -->
				
			</div><!-- modal-di -->
			
		</div>


	</div>
	<script type="text/javascript">
		$(function(){
			$('.chosen-select').chosen();

			

			$('form[name=updateForm]').submit(function(e){
				$("#saveBtn")
				.attr('disabled','disabled')
				.html('Procesando...');
				
	    		//e.preventDefault(); 
	    		return true;
			});

			$('body').on('shown.bs.modal', '.modal', function () {
			    $('[name=clave]').focus();
			});
		});
	</script>
</content>
</body>
</html>



