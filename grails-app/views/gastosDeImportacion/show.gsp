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
    	<li class="active"><g:link action="create"><strong>Consulta</strong></g:link></li>
    	<li><g:link action="edit" id="${gastosDeImportacionInstance.id}">Edición</g:link></li>
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
						<g:render template="showForm"/>
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


 	<content tag="">
 		<g:form name="showForm"  class="form-horizontal" >	
 		<div class="panel panel-primary">
 			<div class="panel-heading">
 				
 			</div>
			
 		  	
 			<div class="panel-body ">
 				<g:hasErrors bean="${gastosDeImportacionInstance}">
 					<div class="alert alert-danger">
 						<ul class="errors" >
 							<g:renderErrors bean="${gastosDeImportacionInstance}" as="list" />
 						</ul>
 					</div>
 				</g:hasErrors>
				<%-- Tab Content --%>
				<div class="tab-content">
					<div class="tab-pane fade in active" id="facturasPanel">
						
						<legend>  <span id="conceptoLabel">Propiedades</span></legend> 
						<g:hiddenField name="id" value="${gastosDeImportacionInstance.id}"/>
						<g:hiddenField name="version" value="${gastosDeImportacionInstance.version}"/>
						<f:with bean="gastosDeImportacionInstance">
						<div class="col-md-6">
							<f:field property="proveedor" widget-class="form-control" 
								wrapper="bootstrap3" widget-required="true"/>
							<f:field property="fecha" wrapper="bootstrap3" widget-required="true"/>
							<f:field property="vencimiento" wrapper="bootstrap3"  />
							<f:field property="moneda" wrapper="bootstrap3"/>
							<f:field property="tc" widget-class="form-control" wrapper="bootstrap3"/>
							<f:field property="documento" widget-class="form-control" wrapper="bootstrap3"/>
							<f:field property="incrementable"  widget-class="form-control" wrapper="bootstrap3"/>
							<f:field property="decrementable"  widget-class="form-control" wrapper="bootstrap3"/>
							<f:field property="comentario" widget-class="form-control" wrapper="bootstrap3"/>
						</div>
						<div class="col-md-6">
							<f:field property="importe" widget-class="form-control" 
								wrapper="bootstrap3" widget-required="true"/>
							<f:field property="subTotal" widget-class="form-control" wrapper="bootstrap3"/>
							<f:field property="descuentos" widget-class="form-control" wrapper="bootstrap3"/>
							<f:field property="impuestos" widget-class="form-control" wrapper="bootstrap3"/>
							
							<f:field property="tasaDeImpuesto" widget-class="form-control" wrapper="bootstrap3"/>
							<f:field property="retTasa" widget-class="form-control" wrapper="bootstrap3"/>
							<f:field property="retImp" widget-class="form-control" wrapper="bootstrap3"/>
							<f:field property="total" widget-class="form-control" wrapper="bootstrap3"/>
							<f:display property="pagosAplicados"  wrapper="bootstrap3" label="Abonos"/>
							
						</div>
						</f:with>
					</div>
					<div class="tab-pane fade in" id="embarquesPanel">
						PENDIENTE
					</div>
					<div class="tab-pane fade in" id="contenedoresPanel">
						CONTENEDORES PENDIENTES
					</div>
				</div>		
 			
 			</div>					
 		 
 			<div class="panel-footer">
 				<div class="btn-group">
 					<g:link class="btn btn-default " action="index"  >
 						<i class="fa fa-step-backward"></i> Facturas
 					</g:link>
 					
 				</div>
 			</div><!-- end .panel-footer -->

 		</div>
 		</g:form>
 		 	
 	</content>

 	
	
	
</body>
</html>
