<%@ page import="com.luxsoft.impapx.FacturaDeImportacion" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="luxor">
	<title>Facturas de importación</title>
</head>
<body>

<content tag="header">
	Factura de importación Id: ${facturaDeImportacionInstance.id}
</content>
<content tag="subHeader">
	<ol class="breadcrumb">
		<li><g:link action="index">Facturas</g:link></li>
		<li><g:link action="create">Alta</g:link></li>
		<li class="active"><strong>Consulta</strong></li>
		<g:if test="${facturaDeImportacionInstance.requisitado<=0.0}">
			<li><g:link action="edit" id="${facturaDeImportacionInstance.id}">Edición</g:link></li>
		</g:if>
		<g:if test="${facturaDeImportacionInstance.requisitado>0.0}">
			<li><g:link  controller="requisicion" action="buscarRequisicion" id="${facturaDeImportacionInstance.id}" >Requisición</g:link></li>
		</g:if>
	</ol>
</content>

<content tag="document">
	<div class="ibox float-e-margins">
		<lx:iboxTitle title="Propiedades"/>
	    <div class="ibox-content">
			<ul class="nav nav-tabs" id="mainTab">
				<li class="active" ><a href="#facturasPanel" data-toggle="tab">Factura</a></li>
				<li><a href="#embarquesPanel" data-toggle="tab">Embarques</a></li>
				<li><a href="#contenedoresPanel" data-toggle="tab">Contenedores</a></li>
				<li><a href="#pagosAplicadosPanel" data-toggle="tab">Abonos</a></li>
			</ul>
			<div class="tab-content"> 
				<div class="tab-pane active" id="facturasPanel">
					<div class="row">
						<br>
						<form class="form-horizontal">
							<f:with bean="facturaDeImportacionInstance">
							<div class="col-md-6">
								<f:display property="proveedor" widget-class="form-control" 
									wrapper="bootstrap3" widget-required="true"/>
								<f:display property="fecha" wrapper="bootstrap3" widget-required="true"/>
								<f:display property="vencimiento" wrapper="bootstrap3"  />
								<f:display property="moneda" wrapper="bootstrap3"/>
								<f:display property="tc" widget-class="form-control" wrapper="bootstrap3"/>
								<f:display property="documento" widget-class="form-control" wrapper="bootstrap3"/>
								<f:display property="comentario" widget-class="form-control" wrapper="bootstrap3"/>
							</div>
							<div class="col-md-6">
								<f:display property="importe" widget="money" wrapper="bootstrap3" />
								<f:display property="subTotal" widget="money" wrapper="bootstrap3"/>
								<f:display property="descuentos" widget="money" wrapper="bootstrap3"/>
								<f:display property="impuestos" widget="money" wrapper="bootstrap3"/>
								<f:display property="total" widget="money" wrapper="bootstrap3"/>
								<f:display property="requisitado" widget="money" wrapper="bootstrap3"/>
								<f:display property="pagosAplicados" widget="money" wrapper="bootstrap3"/>
								<f:display property="saldo" widget="money" wrapper="bootstrap3"/>
								
							</div>
							</f:with>
						</form>
					</div>
					
				</div>
				<div class="tab-pane" id="embarquesPanel">
					
				</div>
				<div class="tab-pane" id="pagosAplicadosPanel">
					<table id="grid"
						class="simpleGrid table table-striped table-hover table-bordered table-condensed">
						<thead>
							<tr>
								<th>Abono</th>			
								<th>Fecha</th>
								<th>Pagado</th>
								<th>Docto</th>
								<th>Concepto</th>
								<th>Comentario</th>
								
							</tr>
						</thead>
						<tbody>
							<g:each in="${facturaDeImportacionInstance.aplicaciones}" var="row">
								<tr id="${fieldValue(bean:row, field:"abono.id")}">
									<td>${fieldValue(bean: row, field: "abono.id")}</td>				
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
								<td><lx:moneyFormat number="${facturaDeImportacionInstance.pagosAplicados}" /></td>
								<td></td>
							</tr>
						</tfoot>
					</table>
				</div>
	  		</div>

			%{-- <div class="tab-content">
				<div class="tab-pane fade in active" id="facturasPanel">
					<f:with bean="facturaDeImportacionInstance">
					<div class="col-md-6">
						<f:display property="proveedor" widget-class="form-control" 
							wrapper="bootstrap3" widget-required="true"/>
						<f:display property="fecha" wrapper="bootstrap3" widget-required="true"/>
						<f:display property="vencimiento" wrapper="bootstrap3"  />
						<f:display property="moneda" wrapper="bootstrap3"/>
						<f:display property="tc" widget-class="form-control" wrapper="bootstrap3"/>
						<f:display property="documento" widget-class="form-control" wrapper="bootstrap3"/>
						<f:display property="comentario" widget-class="form-control" wrapper="bootstrap3"/>
					</div>
					<div class="col-md-6">
						<f:display property="importe" widget="money" wrapper="bootstrap3" />
						<f:display property="subTotal" widget="money" wrapper="bootstrap3"/>
						<f:display property="descuentos" widget="money" wrapper="bootstrap3"/>
						<f:display property="impuestos" widget="money" wrapper="bootstrap3"/>
						<f:display property="total" widget="money" wrapper="bootstrap3"/>
						
					</div>
					</f:with>
				</div>
				<div class="tab-pane fade in" id="embarquesPanel">
					PENDIENTE
				</div>
				<div class="tab-pane fade in" id="contenedoresPanel">
					CONTENEDORES PENDIENTES
				</div>
			</div>	 --}%
	    </div>
	</div>
</content>

 	<content tag="">
 		<form name="updateForm" action="update" class="form-horizontal" method="PUT">	
 		<div class="panel panel-primary">
 			<div class="panel-heading">
 				Factura de importación Id: ${facturaDeImportacionInstance.id}
 			</div>
			<ul class="nav nav-tabs" id="mainTab">
				<li class="active" ><a href="#facturasPanel" data-toggle="tab">Factura</a></li>
				<li><a href="#embarquesPanel" data-toggle="tab">Embarques</a></li>
				<li><a href="#contenedoresPanel" data-toggle="tab">Contenedores</a></li>
			</ul>
 		  	
 			<div class="panel-body ">
 				<g:hasErrors bean="${facturaDeImportacionInstance}">
 					<div class="alert alert-danger">
 						<ul class="errors" >
 							<g:renderErrors bean="${facturaDeImportacionInstance}" as="list" />
 						</ul>
 					</div>
 				</g:hasErrors>
				<%-- Tab Content --%>
					
 			
 			</div>					
 		 
 			<div class="panel-footer">
 				<div class="btn-group">
 					<g:link class="btn btn-default " action="index"  >
 						<i class="fa fa-step-backward"></i> Facturas
 					</g:link>
 					<lx:editButton id="${facturaDeImportacionInstance.id}"/>
 				</div>
 			</div><!-- end .panel-footer -->

 		</div>
 		</form>
 	</content>

 	<conten tag="documento">
 		
 	</conten>
	
	
</body>
</html>
