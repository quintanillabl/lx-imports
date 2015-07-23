<%@ page import="com.luxsoft.impapx.cxc.CXCNota" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="taskView">
		<title>CXC Nota de crédito</title>
		<r:require module="autoNumeric"/>
	</head>
	<body>
	
	<content tag="header">
 	</content>
 	
	<content tag="consultas">
		<li><g:link controller="cuentasPorCobrar" action="list">
			<i class="icon-list"></i>
			CxC
			</g:link>
		</li>
		<li><g:link class="list" action="list">
			<i class="icon-list"></i>
			Notas registradas
			</g:link>
		</li>
	</content>
	
 	<content tag="operaciones">
 		<li><g:link  action="create"><i class="icon-plus "></i> Alta de nota</g:link></li>
 	</content>
 	
 	<content tag="document">
 		<div class="alert">
			<h4><strong>
				Nota ${CXCNotaInstance.tipo} : ${CXCNotaInstance.id}  ${CXCNotaInstance.cliente.nombre} 
				Total:<lx:moneyFormat number="${CXCNotaInstance.total}"/> /
				Disponible: <lx:moneyFormat number="${CXCNotaInstance.disponibleMN}"/> 
				CFD: ${CXCNotaInstance?.cfd?.folio }
			</strong></h4>
		</div>
 		<g:render template="/shared/messagePanel" model="[beanInstance:CXCNotaInstance]"/>
 		
 		<ul class="nav nav-tabs" id="editTab">
			<li class=""><a href="#abonoPanel"  data-toggle="tab">Nota</a></li>
			<li class=""><a href="#conceptosPanel" 	  data-toggle="tab">Conceptos</a></li>
			<li class="active"><a href="#aplicacionesPanel" data-toggle="tab">Aplicaciones</a></li>
			
		</ul>
		
		<div class="tab-content">
			
			<div class="tab-pane " id="abonoPanel">
				<g:render template="editForm" bean="${CXPAbonoInstance}"/>
			</div>
			
			<div class="tab-pane " id="conceptosPanel">
				<g:render template="conceptosPanel" 
					model="[CXCAbonoInstance:CXCNotaInstance,conceptos:CXCNotaInstance.partidas]"/> 
			</div>
			
			<div class="tab-pane active" id="aplicacionesPanel">
				<g:render template="aplicacionesPanel" 
					model="[CXCAbonoInstance:CXCNotaInstance,aplicaciones:CXCNotaInstance.aplicaciones]"/> 
			</div>
			
		</div>

		
		
 	</content>
	</body>
</html>


