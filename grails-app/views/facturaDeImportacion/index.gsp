<%@ page import="com.luxsoft.impapx.FacturaDeImportacion" %>
<%@ page import="com.luxsoft.impapx.FacturasPorPeriodoCommand" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="operaciones">
	<title>Facturas de importación</title>
</head>
<body>
 <content tag="header">
 	Facturas de importación
 </content>
 <content tag="periodo">
 	Periodo:${session.periodo.mothLabel()}
 </content>
 <content tag="operaciones">
 	<li>
 		<g:link  action="create"  >
 		    <i class="fa fa-plus"></i> Nueva de factura
 		</g:link> 
 	</li>
 	<li>
 	    <g:link action="programacionDePagos"  >
 	        <i class="fa fa-list-ol"></i> Programación de pagos
 	    </g:link> 
 	</li>
 	<li>
 	    <a href="#searchDialog" data-toggle="modal" > 
 	    	<i class="fa fa-search"></i> Buscar
 	    </a>
 	</li>
 </content>
 <content tag="grid">
 	<g:render template="facturasPanel" 
 				model="['cuentaPorPagarInstance':facturaDeImportacionInstance
 						,'facturasList':facturaDeImportacionInstanceList
 						,'cuentaPorPagarInstanceTotal':facturaDeImportacionInstanceTotal]"
 						/>
 </content>

 <content tag="searchPanel">
 	%{-- <div class="modal fade" id="searchDialog" tabindex="-1">
 		<div class="modal-dialog ">
 			<div class="modal-content">
 				<div class="modal-header">
 					<button type="button" class="close" data-dismiss="modal"
 						aria-hidden="true">&times;</button>
 					<h4 class="modal-title" id="myModalLabel">Buscar factura</h4>
 				</div>
 				<g:form action="search" class="form-horizontal" >
 					<div class="modal-body">
 						<f:with bean="${cuentaContableInstance}">
 							<f:field property="clave"/>
 							<f:field property="descripcion" input-class="input-xxlarge"/>
 							
 						</f:with>
 					</div>
 					
 					<div class="modal-footer">
 						<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
 						<g:submitButton class="btn btn-info" name="aceptar"
 								value="Buscar" />
 					</div>
 				</g:form>
 	
 			</div>
 			<!-- moda-content -->
 		</div>
 		<!-- modal-di -->
 	</div> --}%
 </content>

 <content tag="searchService">
 	<g:createLink action="search"/>
 </content>
 	

 	
	
	
</body>
</html>
