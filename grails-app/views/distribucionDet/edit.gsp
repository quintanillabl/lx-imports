<%@ page import="com.luxsoft.impapx.DistribucionDet" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="luxor">
		
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
						<li class="nav-header">Listas</li>
						<li>
							<g:link class="list" action="list">
								<i class="icon-list"></i>
								Todas
							</g:link>
						</li>
						<li>
							<g:link class="create" action="create">
								<i class="icon-plus"></i>
								Nueva Lista
							</g:link>
						</li>
					</ul>
				</div>
			</div>
			
			<div class="span10">

				<div class="alert">
					<h4><strong>
					Lista de distribución:<g:link controller="distribucion" action="edit" id="${distribucionDetInstance.distribucion.id}"> 
						${distribucionDetInstance.distribucion.id} 
						( Embarque:${distribucionDetInstance.distribucion.embarque})</g:link>
					</strong></h4>
				</div>
				
				<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
				</g:if>

				<g:hasErrors bean="${distribucionDetInstance}">
				<bootstrap:alert class="alert-error">
				<ul>
					<g:eachError bean="${distribucionDetInstance}" var="error">
					<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
					</g:eachError>
				</ul>
				</bootstrap:alert>
				</g:hasErrors>
				<g:render template="editForm" bean="${distribucionDetInstance}"/>
			</div>
		</div>
		</div>
	</body>
</html>
