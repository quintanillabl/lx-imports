<%@ page import="com.luxsoft.impapx.cxc.CXCNota" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="luxor">
	<g:set var="entityName" value="${message(code: 'CXCNota.label', default: 'CXCNota')}" scope="request"/>
	<g:set var="entity" value="${CXCNotaInstance}" scope="request" />
	<title>CXC Nota ${CXCNotaInstance.id}</title>
</head>
<body>
	
	<content tag="header">
		Nota ${CXCNotaInstance.id} Disponible: ${formatNumber(number:entity.disponible,type:'currency')}
 	</content>
 	<content tag="subHeader">
 		<ol class="breadcrumb">
            <li><g:link action="index">${entityName}(s)</g:link></li>
            <li><g:link action="show" id="${entity.id}">Consulta</g:link></li>
            <g:if test="${CXCNotaInstance.cfdi}">
            	<li><g:link controller="cfdi" action="show" id="${entity.cfdi}"><strong>CFDI</strong></g:link></li>
            </g:if>
            <li><g:link action="edit" id="${entity.id}"><strong>Edición</strong></g:link></li>
            
        </ol>
 	</content>

 	<content tag="document">
 		<div class="ibox float-e-margins">
 			<div class="ibox-title">
				<g:if test="${CXCNotaInstance.cfdi}">
					<g:link controller="cfdi" action="show" id="${entity.id}"><strong>CFDI</strong></g:link>
				</g:if>
 			</div>
 		    <div class="ibox-content">
		 		<ul class="nav nav-tabs" id="editTab">
					<li class="active"><a href="#conceptosPanel" 	  data-toggle="tab">Conceptos</a></li>
					<li><a href="#aplicacionesPanel" data-toggle="tab">Aplicaciones</a></li>
					<li><a href="#abonoPanel"  data-toggle="tab">Propiedades</a></li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane active" id="conceptosPanel">
						<g:render template="conceptosPanel" 
							model="[CXCAbonoInstance:CXCNotaInstance,conceptos:CXCNotaInstance.partidas]"/> 
					</div>
					<div class="tab-pane " id="aplicacionesPanel">
						<table id="grid"
							class="simpleGrid table table-striped table-hover table-bordered table-condensed">
							<thead>
								<tr>
									<th class="header">Id</th>
									<th class="header">Fecha</th>
									<th class="header">Documento</th>
									<th class="header">Fecha(Docto)</th>	
									<th class="header">Importe (Docto)</th>
									<th class="header">Saldo (Docto)</th>
									<th class="header">Pagado</th>
									
								</tr>
							</thead>
							<tbody>
								<g:each in="${aplicaciones}" var="row">
									<tr id="${row.id}">
									    
										<td><g:link controller="CXCAplicacion" action="edit" id="${row.id}">
											${fieldValue(bean: row, field: "id")}</g:link>
										</td>
										<td><lx:shortDate date="${row.fecha}" /></td>
										<td>${fieldValue(bean: row, field: "factura.facturaFolio")}</td>
										<td>${fieldValue(bean: row, field: "factura.fechaFactura")}</td>
										<td><lx:moneyFormat number="${row.factura.total }" /></td>	
										<td><lx:moneyFormat number="${row.factura.saldoActual }" /></td>
										<td><lx:moneyFormat number="${row.total }" /></td>
										
									</tr>
								</g:each>
							</tbody>
							<tfoot>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td><label class="pull-right" >Total: </label></td>
									<td><lx:moneyFormat number="${CXCNotaInstance.aplicado }" /></td>
								</tr>
							</tfoot>
						</table>
					</div>
					<div class="tab-pane " id="abonoPanel">
						<fieldset disabled>
							<g:render template="editForm" bean="${CXCNotaInstance}"/>
						</fieldset>
					</div>
					
				</div>
 		    </div>
 		</div>
 	</content>
 	
 	
</body>
</html>


