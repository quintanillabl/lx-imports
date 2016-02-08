
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
    	<li><g:link action="index">Cuentas</g:link></li>
    	<li class="active"><strong>Consulta</strong></li>
    	<li><g:link action="edit" id="${procesadorDePolizaInstance.id}">Edición</g:link></li>
	</ol>
</content>
	
<content tag="document">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-lg-7">
				<lx:iboxTitle title="Procesador de poliza  "/>
				
				<div class="ibox-content">
					<form name="createForm" class="form-horizontal">	
						<f:with bean="procesadorDePolizaInstance">
							<f:display property="subTipo" widget-autofocus="true" wrapper="bootstrap3" widget-class="form-control" />
							<f:display property="tipo" wrapper="bootstrap3" widget-class="form-control"  />
							<f:display property="descripcion" wrapper="bootstrap3" widget-class="form-control" />
							
							<f:display property="service" wrapper="bootstrap3" widget-class="form-control" lable="Servicio"/>	
							

							<f:display property="label" wrapper="bootstrap3" widget-class="form-control" label="Etiqueta"/>
						</f:with>
						<div class="form-group">
							<div class="col-lg-offset-3 col-lg-10">
								<lx:backButton label="Cuentas" />
								<lx:editButton id="${procesadorDePolizaInstance.id}"/>
						
							</div>
						</div>
					</form>
				</div>
			</div>
			
		</div>
	</div>



	
	
</content>
</body>
</html>



