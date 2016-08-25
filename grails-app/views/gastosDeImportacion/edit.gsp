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
						<li ><a href="#cfdiPanel" data-toggle="tab">CFDI</a></li>
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

					<div class="tab-pane fade in" id="cfdiPanel">
						<br>
						<g:if test="${gastosDeImportacionInstance.comprobante}">
							<div class="row">
								<div class="col-md-8">
									<form class="form-horizontal" >
										<f:with bean="${gastosDeImportacionInstance.comprobante}">
											<f:display property="serie" wrapper="bootstrap3"/>
											<f:display property="folio" wrapper="bootstrap3"/>
											<f:display property="uuid" wrapper="bootstrap3"/>
											<f:display property="total" widget="money" wrapper="bootstrap3"/>
											<f:display property="cfdiFileName" wrapper="bootstrap3" label="Archivo"/>
											<f:display property="acuseEstado" wrapper="bootstrap3"/>
											<f:display property="acuseCodigoEstatus" wrapper="bootstrap3"/>								
										</f:with>
									</form>
								</div>
									<div class="col-lg-offset-2 col-lg-6">
						                <g:link controller="comprobanteFiscal" action="validar" onclick="return confirm('Validar en el SAT?');"
						                		class="btn btn-default btn-outline" id="${gastosDeImportacionInstance.comprobante.id}">
						                	    <i class="fa fa-check-square-o"></i> Validar (SAT)
						                </g:link> 
						                <g:if test="${gastosDeImportacionInstance.comprobante.acuse}">
						                	<g:link  controller="comprobanteFiscal" action="mostrarAcuse" 
						                		id="${gastosDeImportacionInstance.comprobante.id}"
						                		class="btn btn-default btn-outline" >
						                		<i class="fa fa-file-code-o"></i> Acuse
						                	</g:link>
						                	
						                </g:if>
						                <g:if test="${gastosDeImportacionInstance.comprobante}">
						                	<g:link class="btn btn-default btn-outline" 
						                		controller="comprobanteFiscal" action="mostrarCfdi" 
						                		id="${gastosDeImportacionInstance.comprobante.id}">CFDI</g:link>
						                	
						                	<g:link  controller="comprobanteFiscal" action="mostrarCfdi" 
						                		id="${gastosDeImportacionInstance.comprobante.id}"
						                		class="btn btn-default btn-outline" >
						                		  CFDI (XML)
						                	</g:link>

						                	<g:link  controller="comprobanteFiscal" action="descargarCfdi" 
						                		id="${gastosDeImportacionInstance.comprobante.id}"
						                		class="btn btn-default btn-outline" >
						                		<i class="fa fa-download"></i>  CFDI
						                	</g:link>

						                </g:if>
						                %{-- <a href="#uploadFileDialog" data-toggle="modal" class="btn btn-success btn-outline">
											<i class="fa fa-upload"></i></span> Cargar CFDI
										</a> --}%
						            </div>
							</div>
							
						</g:if>
						<g:else>
							<div class="btn-group">
								 <a href="#uploadFileDialog" data-toggle="modal" class="btn btn-success btn-outline">
									<i class="fa fa-upload"></i></span> Cargar CFDI
								</a>
							</div>
						</g:else>
					</div>
				</div>		
			    </div>
			</div>
		</div>
	</div>
	<g:render template="/comprobanteFiscal/uploadXmlFile" bean="${gastosDeImportacionInstance}"/>
</content>


 	

 	
	
	
</body>
</html>
