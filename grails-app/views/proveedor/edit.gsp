<%@ page import="com.luxsoft.impapx.Proveedor" %>
<!doctype html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<g:set var="entityName" value="${message(code: 'proveedor.label', default: 'Proveedor')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="container">
			<div class="row row-header">
				<div class="col-md-8 col-sm-offset-2 toolbar-panel">
					<div class="btn-group">
					    <lx:backButton/>
					    <lx:printButton/>
					    <lx:deleteButton bean="${proveedorInstance}"/>
					</div>
				</div>
			</div> <!-- End .row 1 -->

			<div class="row">
			    <div class="col-md-8 col-sm-offset-2">
			    	<g:form method="post" >
					<div class="panel panel-primary">
						<div class="panel-heading"> 
							<g:message code="default.edit.label" args="[entityName]" />: ${proveedorInstance} 
						</div>
						<g:if test="${flash.message}">
							<small><span class="label label-info ">${flash.message}</span></small>
						</g:if> 
					  	<div class="panel-body">
				  			<g:hiddenField name="id" value="${proveedorInstance?.id}" />
				  			<g:hiddenField name="version" value="${proveedorInstance?.version}" />
				  			<f:all bean="proveedorInstance"/>
					  	</div>
					  	<div class="panel-footer">
					  		<div class="btn-group">
					  			<g:actionSubmit class="btn btn-default" 
					  				action="update" 
					  				value="${message(code: 'default.button.update.label', default: 'Update')}" />
					  		</div>
					  	</div><!-- end .panel-footer -->

					</div>
					</g:form>
			    </div>
			</div><!-- End .row 2 -->

		</div>

		
		
	</body>
</html>
