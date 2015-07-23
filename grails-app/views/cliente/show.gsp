
<%@ page import="com.luxsoft.impapx.Cliente" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<g:set var="entityName" value="${message(code: 'cliente.label', default: 'Cliente')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="container">
			
			<div class="row row-header">
				<div class="col-md-8 col-sm-offset-2 toolbar-panel">
					<div class="btn-group">
					    <lx:backButton/>
					    <lx:createButton/>
					    <lx:editButton id="${clienteInstance?.id}"/>
					    <lx:printButton/>
					</div>
				</div>
			</div> <!-- End .row 1 -->
			<div class="row">
			    <div class="col-md-8 col-sm-offset-2">
					<div class="panel panel-primary">
						<div class="panel-heading"> ${entityName}: ${clienteInstance} </div>
						<g:if test="${flash.message}">
							<small><span class="label label-info ">${flash.message}</span></small>
						</g:if> 
					  	<div class="panel-body">
					  		<g:form class="form-horizontal">
					  			<f:display property="nombre" bean="clienteInstance" 
					  				colsLabel="col-sm-3" cols="col-sm-9"/>
					  			<f:display property="rfc" bean="clienteInstance"
					  				colsLabel="col-sm-3" cols="col-sm-9"/>
					  			<f:display property="email1" bean="clienteInstance"
					  				colsLabel="col-sm-3" cols="col-sm-9"/>
					  			<f:display property="formaDePago" bean="clienteInstance" label="F.Pago"
					  				colsLabel="col-sm-3" cols="col-sm-9"/>
					  			<f:display property="cuentaDePago" bean="clienteInstance" label="Cuenta Acredora"
					  				colsLabel="col-sm-3" cols="col-sm-9"/>
					  			<f:display property="subCuentaOperativa" bean="clienteInstance" 
					  				colsLabel="col-sm-3" cols="col-sm-9"/>
					  			<f:display property="fisica" bean="clienteInstance"
					  				colsLabel="col-sm-3" cols="col-sm-9"/>
					  			<f:display property="lastUpdated" bean="clienteInstance" label="Modificado"
					  				colsLabel="col-sm-3" cols="col-sm-9"/>
					  			<f:display property="dateCreated" bean="clienteInstance" widget="datetime" label="Creado"	colsLabel="col-sm-3" cols="col-sm-9"/>		
					  			<f:display property="direccion" bean="clienteInstance" templates="direccion"
					  				colsLabel="col-sm-3" cols="col-sm-9"/>
					  		</g:form>
					  </div>
					</div>
			    </div>
			</div><!-- End .row 2 -->

		</div><!-- End container -->

	</body>
</html>


