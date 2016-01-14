<%@ page import="com.luxsoft.impapx.GastosDeImportacion" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="luxor">
	<title>Gastos de importación</title>
</head>
<body>

<content tag="header">
	Gastos de importación Factura: ${gastosDeImportacionInstance.documento}  (Id:${gastosDeImportacionInstance.documento}) 
</content>
<content tag="subHeader">
	<ol class="breadcrumb">
    	<li><g:link action="index">Gastos</g:link></li>
    	<li><g:link action="create">Alta</g:link></li>
    	<li class="active"><g:link action="edit" id="${gastosDeImportacionInstance.id}"><strong>Edición</strong></g:link></li>
	</ol>
</content>
<content tag="document">
	<div class="row">
		<div class="col-lg-12">
			<div class="ibox float-e-margins">
				<lx:iboxTitle title=""/>
			    <div class="ibox-content">
					<ul class="nav nav-tabs" id="mainTab">
						<li class="active" ><a href="#facturasPanel" data-toggle="tab">Factura</a></li>
						<li><a href="#embarquesPanel" data-toggle="tab">Embarques</a></li>
						<li><a href="#contenedoresPanel" data-toggle="tab">Contenedores</a></li>
						<li><a href="#pagosAplicadosPanel" data-toggle="tab">Abonos</a></li>
					</ul>
					<div class="tab-content">
					<div class="tab-pane fade in active" id="facturasPanel">
						<g:render template="editForm"/>
					</div>
					<div class="tab-pane fade in" id="embarquesPanel">
						PENDIENTE
					</div>
					<div class="tab-pane fade in" id="contenedoresPanel">
						CONTENEDORES PENDIENTES
					</div>
					<div class="tab-pane" id="pagosAplicadosPanel">
						<table id="grid"
							class="simpleGrid table table-striped table-hover table-bordered table-condensed">
							<thead>
								<tr>
									<th>Aplicacion</th>			
									<th>Fecha</th>
									<th>Pagado</th>
									<th>Docto</th>
									<th>Concepto</th>
									<th>Comentario</th>
									
								</tr>
							</thead>
							<tbody>
								<g:each in="${gastosDeImportacionInstance.aplicaciones}" var="row">
									<tr id="${fieldValue(bean:row, field:"id")}">
										<td>${fieldValue(bean: row, field: "id")}</td>				
										<td><lx:shortDate date="${row.fecha}" /></td>
										<td><lx:moneyFormat number="${row.total }" /></td>
										<g:if test="${row.abono.instanceOf(com.luxsoft.impapx.cxp.NotaDeCredito)}">
											<td>${fieldValue(bean: row, field: "abono.documento")}</td>				
											<td>${fieldValue(bean: row, field: "abono.concepto")}</td>				
										</g:if>
										<g:else>
											<td></td>
											<td></td>
										</g:else>
										
										<td>${fieldValue(bean: row, field: "comentario")}</td>				
									</tr>
								</g:each>
							</tbody>
							<tfoot>
								<tr>
									
									<td></td>
									
									<td><label class="pull-right" >Total: </label></td>
									<td><lx:moneyFormat number="${gastosDeImportacionInstance.pagosAplicados}" /></td>
									<td></td>
								</tr>
							</tfoot>
						</table>
					</div>
				</div>		
			    </div>
			</div>
		</div>
	</div>
</content>


 	

 	
	
	
</body>
</html>
