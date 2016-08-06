<!DOCTYPE html>
<html>
	<head>
		<title>Luxor Backend Error</title>
		<meta name="layout" content="main"/>
		
		%{-- <g:if env="development">
			
		</g:if> --}%
		<asset:stylesheet src="errors.css"/>
	</head>
	<body>
		<div class="container">
			<div class="row">
				<div class="col-md-8">
					<g:if env="development">
						<g:renderException exception="${exception}" />
					</g:if>
					<g:else>
					<div class="well">
						<h4>Error interno en Luxor Engine</h4>
					</div>
						<table class="table table-bordered table-condensed">
							<thead>
								<tr>
									<th>Mensaje</th>
									<th>Descripción</th>
									
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>Error</td>
									<td class="danger">${org.apache.commons.lang.exception.ExceptionUtils.getRootCauseMessage(exception)}</td>
								</tr>
								<tr>
									<td>Causa</td>
									<td class="danger">${org.apache.commons.lang.exception.ExceptionUtils.getRootCause(exception).class}</td>
								</tr>
								<tr>
									<td>Time</td>
									<td><g:formatDate date="${new Date()}" format="dd/MM/yyyy mm:ss"/></td>
								</tr>
								<tr>
									<td>Controller</td>
									<td>${controllerName}</td>
								</tr>
								<tr>
									<td>Action</td>
									<td>${actionName}</td>
								</tr>
							</tbody>
						</table>
					</g:else>
					
				</div>
			</div>
		</div>
		
	</body>
</html>

%{-- <!DOCTYPE html>
<html>
	<head>
		<title><g:if env="development">Grails Runtime Exception</g:if><g:else>Error</g:else></title>
		<meta name="layout" content="main">
		<g:if env="development"><asset:stylesheet src="errors.css"/></g:if>
	</head>
	<body>
		<g:if env="development">
			<g:renderException exception="${exception}" />
		</g:if>
		<g:else>
			<ul class="errors">
				<li>An error has occurred</li>
			</ul>
		</g:else>
	</body>
</html> --}%
