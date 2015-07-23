<%@ page import="com.luxsoft.impapx.Distribucion" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="luxor">
		<g:set var="entityName" value="${message(code: 'pedimento.label', default: 'Distribución')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
		<r:require module="datepicker"/>
		<r:require module="dataTables"/>
		<r:require module="luxorForms"/>
		<r:require module="luxorTableUtils"/>
		
		
	</head>
	<body>
		<div class="container-fluid">
		<div class="row-fluid">

			<div class="span2">
				<div class="well">
					<ul class="nav nav-list">
						<li class="nav-header">${entityName}</li>
						<li>
							<g:link class="list" action="list">
								<i class="icon-list"></i>
								<g:message code="default.list.label" args="[entityName]" />
							</g:link>
						</li>
						<li>
							<g:link class="create" action="create">
								<i class="icon-plus"></i>
								<g:message code="default.create.label" args="[entityName]" />
							</g:link>
						</li>
					</ul>
				</div>
			</div>
			
			<div class="span10">

				<div class="alert">
					<h4><strong>
					Lista de distribución: ${distribucionInstance.id} ( Embarque:${distribucionInstance.embarque})
					</strong></h4>
					
				</div>
				
				<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
				</g:if>

				<g:hasErrors bean="${distribucionInstance}">
				<bootstrap:alert class="alert-error">
				<ul>
					<g:eachError bean="${distribucionInstance}" var="error">
					<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
					</g:eachError>
				</ul>
				</bootstrap:alert>
				</g:hasErrors>
				<ul class="nav nav-tabs" id="myTab">
					<li class=""><a href="#distribucion" data-toggle="tab">Distribución</a></li>
					<li class="active"><a href="#partidas" data-toggle="tab">Lista</a></li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane " id="distribucion">
						<g:render template="editForm" bean="${distribucionInstance}"/>
					</div>
					<div class="tab-pane active" id="partidas">
						<g:render template="partidasPanel" bean="${distribucionInstance}"/>
					</div>
				</div>
			</div>
		</div>
		</div>
	</body>
</html>
