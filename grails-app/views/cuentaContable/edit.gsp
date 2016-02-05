<%@ page import="com.luxsoft.impapx.contabilidad.CuentaContable" %>
<!doctype html>
<html>
<head>
	<title>Cuenta ${cuentaContableInstance.id}</title>
	<meta name="layout" content="luxor">
	<asset:javascript src="forms/forms.js"/>
</head>
<body>

<content tag="header">Cuenta: ${cuentaContableInstance}</content>
<content tag="subHeader">
	<ol class="breadcrumb">
    	<li><g:link action="index">Catálogo</g:link></li>
    	<g:if test="${cuentaContableInstance.padre}">
    		<li class="active"> <i class="fa fa-step-backward"></i> <g:link action="edit" id="${cuentaContableInstance.padre.id}">${cuentaContableInstance.padre.clave}</g:link></li>
    	</g:if>
    	
    	
	</ol>
</content>
	
<content tag="document">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-lg-7">
				<lx:iboxTitle title="Cuenta contable  "/>
				
				<div class="ibox-content">
					<g:form name="updateForm" action="update" class="form-horizontal" method="PUT">	
						<g:hiddenField name="id" value="${cuentaContableInstance.id}"/>
						<g:hiddenField name="version" value="${cuentaContableInstance.version}"/>
						<f:with bean="cuentaContableInstance">
							<f:display property="clave" wrapper="bootstrap3" widget-class="form-control"/>
							<f:field property="descripcion" wrapper="bootstrap3" widget-class="form-control"/>
							<f:field property="tipo" wrapper="bootstrap3" widget-class="form-control chosen-select"/>
							<f:field property="subTipo" wrapper="bootstrap3" widget-class="form-control"/>
							<f:field property="naturaleza" wrapper="bootstrap3" widget-class="form-control chosen-select"/>
							<f:field property="deResultado" wrapper="bootstrap3"/>
							<f:field property="presentacionContable" wrapper="bootstrap3"/>
							<f:field property="presentacionFinanciera" wrapper="bootstrap3"/>
							<f:field property="presentacionFiscal" wrapper="bootstrap3"/>
							<f:field property="presentacionPresupuestal" wrapper="bootstrap3"/>
							<f:field property="cuentaSat" wrapper="bootstrap3" widget-class="form-control chosen-select"/>
						</f:with>
						<div class="form-group">
							<div class="col-lg-offset-3 col-lg-10">
								<g:if test="${cuentaContableInstance.padre}">
									<lx:backButton label="${cuentaContableInstance.padre}" 
										action="show" id="${cuentaContableInstance.padre.id}"/>
								</g:if>
								<g:else>
									<lx:backButton label="Cuentas" />
								</g:else>
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
			<g:if test="${!cuentaContableInstance.detalle}">
			<div class="col-lg-5">
				<lx:iboxTitle title="Sub cuentas "/>
				<div class="ibox-content">
					<table id="grid" class="grid table table-responsive  table-bordered ">
						<thead>
							<tr>
								<th>Clave</th>
								<th>Descripción</th>
							</tr>
						</thead>
						<tbody>
							<g:each in="${cuentaContableInstance.subCuentas}" var="row">
								<tr>
									<td><g:link action="edit" id="${row.id}">
										${fieldValue(bean: row, field: "clave")}
										</g:link>
									</td>
									<td><g:link action="edit" id="${row.id}">
										${fieldValue(bean: row, field: "descripcion")}
										</g:link>
									</td>
								</tr>
							</g:each>
						</tbody>
					</table>
					<button class="btn btn-primary" data-toggle="modal" data-target="#agregarDialog"> Agregar</button>
				</div>
			</div>
			</g:if>
		</div>
		<div class="modal fade" id="agregarDialog" tabindex="-1">
			<div class="modal-dialog ">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">Agregar sub cuenta</h4>
					</div>
					<g:form action="agregarSubCuenta" class="form-horizontal" >
						
						<g:hiddenField name="padre" value="${cuentaContableInstance.id}"/>
						
						<div class="modal-body">
							<div class="form-group">
								<label for="clave" class="control-label col-sm-3">Clave</label>
								<div class="col-sm-9">
									<input id="subCuentaClave" name="clave" class="form-control" value="">
								</div>
							</div>
							<div class="form-group">
								<label for="descripcion" class="control-label col-sm-3">Descricpción</label>
								<div class="col-sm-9">
									<input name="descripcion" class="form-control" value="">
								</div>
							</div>
							<div class="form-group">
								<label for="detalle" class="control-label col-sm-3">De detalle</label>
								<div class="col-sm-9">
									<input name="detalle" class="form-control" value="true" type="checkbox" checked>
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

		<div class="modal fade" id="deleteDialog" tabindex="-1">
			<div class="modal-dialog ">
				<div class="modal-content">
					<g:form action="delete" class="form-horizontal" method="DELETE">
						<g:hiddenField name="id" value="${cuentaContableInstance.id}"/>

						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="myModalLabel">Eliminar cuenta contable ${cuentaContableInstance.clave}</h4>
						</div>
						<div class="modal-body">
							<p><small>${cuentaContableInstance}</small></p>
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

			$('#subCuentaClave').mask('00000');

			$('form[name=updateForm]').submit(function(e){
				$("#saveBtn")
				.attr('disabled','disabled')
				.html('Procesando...');
				try{
					$(".money,.porcentaje,.tc",this).each(function(index,element){
					  var val=$(element).val();
					  var name=$(this).attr('name');
					  var newVal=$(this).autoNumeric('get');
					  $(this).val(newVal);
					  console.log('Enviando elemento numerico con valor:'+name+" : "+val+ " new val:"+newVal);
					});
				}catch(err){
					console.log(err);
				}
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



