
<%@ page import="com.luxsoft.impapx.tesoreria.Cheque" %>
<%@ page import="luxsoft.cfd.ImporteALetra" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="luxor">
		<g:set var="entityName" value="${message(code: 'cheque.label', default: 'Cheque')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body >
		<div class="container">
			<div class="row">
			<div class="span9">

				<div class="page-header alert-info">
					<h3>Cancelación de cheque ${cancelacionCommand.id }</h3>
				</div>				
						
				<fieldset>
					<g:form class="form-horizontal" action="cancelar" id="${cancelacionCommand.id}">
						<fieldset>
							<f:with bean="cancelacionCommand">
								<f:field property="comentario" input-required="true"/>
							</f:with>
							<div class="form-actions">
								<button type="submit" class="btn btn-danger">
									<g:message code="default.button.cancel.label" default="Cancelar" />
								</button>
							</div>
						</fieldset>
					</g:form>
				</fieldset>
			</div>
			</div>
		</div>
	
	</body>
</html>
