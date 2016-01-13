<%@ page import="com.luxsoft.impapx.cxp.NotaDeCredito" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="luxor">
	<title>Nota de crédito ${notaDeCreditoInstance.id}</title>
	<g:set var="entityName" value="${message(code: 'notaDeCredito.label', default: 'Nota de Crédito')}" scope="request"/>
	<g:set var="entity" value="${notaDeCreditoInstance}" scope="request" />
	<asset:javascript src="forms/forms.js"/>
</head>
<body>

<content tag="header">
	
</content>

<content tag="subHeader">
	<ol class="breadcrumb">
       <li><g:link action="index">Notas de crédito</g:link></li>
       <li><g:link action="create">Alta</g:link></li>
       <li><g:link action="show" id="${entity.id}">Consulta</g:link></li>
       <li><g:link action="edit" id="${entity.id}"><strong>Edición</strong></g:link></li>
   </ol>
</content>
 	
<content tag="document">
	<div class="row">
		<div class="col-md-12">
			<div class="ibox float-e-margins">
				
				<div class="ibox-title">
					Nota ${notaDeCreditoInstance.id}  
					<strong> Total: ${formatNumber(number:notaDeCreditoInstance.total,type:'currency')} / Disponible:
						<span id="disponibleField"> ${formatNumber(number:notaDeCreditoInstance.disponible,type:'currency')}</span>
					
					</strong>
				</div>
			    <div class="ibox-content">
			 		<ul class="nav nav-tabs" id="editTab">
						<li class="active"><a href="#aplicacionesPanel" data-toggle="tab">Aplicaciones</a></li>
						<li><a href="#abonoPanel"  data-toggle="tab">Propiedades</a></li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane active" id="aplicacionesPanel">
							<g:render template="aplicacionesPanel" 
								model="[abonoInstance:notaDeCreditoInstance,aplicaciones:notaDeCreditoInstance.aplicaciones]"/>
						</div>
						<div class="tab-pane " id="abonoPanel">
							<g:render template="editForm" bean="${notaDeCreditoInstance}"/>
						</div>
						
					</div>
			    </div>
			</div>
		</div>
	</div>
</content>
 	
 	
 	
</body>

</html>
