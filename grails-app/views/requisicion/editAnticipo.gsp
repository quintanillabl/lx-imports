<%@ page import="com.luxsoft.impapx.Requisicion" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="taskView">
		<g:set var="entityName" value="${message(code: 'requisicion.label', default: 'Requisicion')}" />
		<title>Anticipo</title>
		<r:require module="luxorForms"/>
	</head>
	<body>
		
		<content tag="header">
			<div class="alert">
				<h4><strong>Req: ${requisicionInstance?.id} (${requisicionInstance?.proveedor?.nombre})</strong></h4>
			</div>
 		</content>
 		
		<content tag="consultas">
	
			<li><g:link class="list" action="anticipos">
				<i class="icon-list"></i>
				Anticipos
				</g:link>
			</li>
		</content>
	
 		<content tag="operaciones">
 			<li><g:link  action="create">
 				<i class="icon-plus"></i>
 				Alta de anticipo
 				</g:link>
 			</li>
 		</content>
 	
 	<content tag="document">
 		<g:render template="/shared/messagePanel" model="[beanInstance:requisicionInstance]"/>
		<ul class="nav nav-tabs" id="myTab">
						<li class=""><a href="#formPanel" data-toggle="tab">Requisición</a></li>
						<li class="active"><a href="#partidasPanel" data-toggle="tab">Partidas</a></li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane fade in " id="formPanel">
							<g:render template="form" bean="${requisicionInstance }"/>
						</div>
					
						<div class="tab-pane fade in active" id="partidasPanel">
							<g:render template="partidasPanel" bean="${requisicionInstance}" 
								model="['partidasList':requisicionInstance.partidas]"/>	
						</div>
					</div>
		
 	</content>
 	
 
		
	</body>
</html>
