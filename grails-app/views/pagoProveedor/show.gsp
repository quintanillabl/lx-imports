<!doctype html>
<html>
<head>
	<title>Pago ${pagoProveedorInstance.id}</title>
	<meta name="layout" content="luxor">
</head>
<body>

<content tag="header">Pago ${pagoProveedorInstance.id} ${pagoProveedorInstance.requisicion.proveedor}</content>
<content tag="subHeader">
	<ol class="breadcrumb">
		<li><g:link action="index">Pagos</g:link></li>
		<li><g:link action="create">Alta</g:link></li>
		<li class="active"><strong>Consulta</strong></li>
		%{-- <g:if test="${!requisicionInstance.pagoProveedor}">
			<li><g:link action="edit" id="${requisicionInstance.id}">Edición</g:link></li>
		</g:if> --}%
	</ol>
</content>
	
<content tag="document">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-lg-10">
				<lx:iboxTitle title="Pago a proveedor "/>
				<div class="ibox-content">
					<lx:errorsHeader bean="${pagoProveedorInstance}"/>
					<form class="form-horizontal">	
						<f:with bean="pagoProveedorInstance">

							<f:display property="requisicion" widget-class="form-control " wrapper="bootstrap3"/>
							<f:display property="fecha"  wrapper="bootstrap3"/>
							<f:display property="cuenta" widget-class="form-control chosen-select" wrapper="bootstrap3"/>
							<f:display property="tipoDeCambio" widget="tc" wrapper="bootstrap3" />
							<f:display property="egreso"  wrapper="bootstrap3"/>
							<f:display property="pago"  wrapper="bootstrap3" label="Pago CXP"/>
							<f:display property="comentario"  wrapper="bootstrap3"/>
							<div class="form-group">
								<div class="col-lg-offset-3 col-lg-10">
									<lx:backButton/>
									<lx:deleteButton bean="${pagoProveedorInstance}"/>
								</div>
							</div>
						</f:with>
					</form>
				</div>
			</div>
		</div>
	</div>
	
</content>
</body>
</html>

