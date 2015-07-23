<%@ page import="com.luxsoft.impapx.cxp.CuentaDeGastosGenerica" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="luxor">
		<title>Cuenta genérica de gastos</title>
		<r:require modules="luxorTableUtils,dataTables"/>
	</head>
	<body>
		<div class="container-fluid">
		<div class="row-fluid">

			<div class="span2">
				<div class="well">
					<ul class="nav nav-list">
						<li class="nav-header">Cuenta de gastos</li>
						<li>
							<g:link class="list" action="list">
								<i class="icon-list"></i>
								Cuentas
							</g:link>
						</li>
						<li>
							<g:link class="create" action="create">
								<i class="icon-plus"></i>
								Alta
							</g:link>
						</li>
					</ul>
				</div>
			</div>
			
			<div class="span10">

				<div class="page-header">
					<h3>Cueta de gastos genérica: ${ cuentaDeGastosGenericaInstance?.id}</h3>
				</div>

				<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
				</g:if>

				<g:hasErrors bean="${cuentaDeGastosGenericaInstance}">
				<bootstrap:alert class="alert-error">
				<ul>
					<g:eachError bean="${cuentaDeGastosGenericaInstance}" var="error">
					<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
					</g:eachError>
				</ul>
				</bootstrap:alert>
				</g:hasErrors>
				<ul class="nav nav-tabs" id="myTab">
					<li class=""><a href="#cuenta" data-toggle="tab">Cuenta</a></li>
					<li class="active"><a href="#facturas" data-toggle="tab">Facturas</a></li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane " id="cuenta">
						<fieldset>
							<g:form class="form-horizontal" action="update" >
								<fieldset>
									<f:with bean="${cuentaDeGastosGenericaInstance}">
										<f:field property="proveedor">
											<label class="control-label" >${cuentaDeGastosGenericaInstance.proveedor}</label>
										</f:field>
										<f:field property="fecha"/>
										<f:field property="comentario"/>
									</f:with>
									<button type="submit" class="btn btn-primary">
										<i class="icon-ok icon-white"></i>
										Salvar
									</button>
									<button type="submit" class="btn btn-danger" name="_action_delete"
										formnovalidate>
										<i class="icon-trash icon-white"></i>
										Eliminar
									</button>
							
								</fieldset>
							</g:form>
						</fieldset>
					</div>
					
					<div class="tab-pane active" id="facturas">
						<g:render template="facturasPanel" bean="${cuentaDeGastosGenericaInstance}"/>
					</div>
					
				</div>
				

			</div>

		</div>
		</div>
	</body>
</html>


