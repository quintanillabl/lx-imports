<%@ page import="com.luxsoft.impapx.Pedimento" %>
<%@ page contentType="text/html;charset=ISO-8859-1" %>
<!doctype html>
<html>
	<head>
		<g:set var="entityName" value="${message(code: 'pedimento.label', default: 'Pedimento')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
		<asset:javascript src="forms/forms.js"/>
		<asset:stylesheet src="datatables/dataTables.css"/>
		<asset:javascript src="datatables/dataTables.js"/> 
	</head>
	<body>
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<div class="page-header">
						<h1>Pedimento: ${pedimentoInstance.pedimento} <small>${pedimentoInstance.proveedor}</small></h1>
						<g:if test="${flash.message}">
						  	<span class="label label-warning">${flash.message}</span>
						</g:if>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-12">
					<ul class="nav nav-tabs" role="tablist">
						<li class="">
							<g:link action="index" >
							    <i class="fa fa-step-backward"></i> Pedimentos
							</g:link> 
						</li>
						<li class="active"><a href="#pedimento" data-toggle="tab">Pedimento</a></li>
						<li class=""><a href="#embarques" data-toggle="tab">Embarques</a></li>
					</ul>
			  		<div class="tab-content"> <!-- Tab Content -->
						<div class="tab-pane active" id="pedimento">
							<g:render template="editForm" bean="${pedimentoInstance}"/>
						</div>
						<div class="tab-pane" id="embarques">
							<g:render template="embarquesPanel" bean="${pedimentoInstance}"/>
						</div>
			  		</div>	<!-- End Tab Content -->
				</div>
			</div>
			
			
		</div>
		
	</body>
</html>
