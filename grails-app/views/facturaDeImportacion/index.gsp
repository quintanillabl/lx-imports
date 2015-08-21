<%@ page import="com.luxsoft.impapx.FacturaDeImportacion" %>
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
 </content>
 <content tag="grid">
 	<g:render template="facturasPanel" 
 				model="['cuentaPorPagarInstance':facturaDeImportacionInstance
 						,'facturasList':facturaDeImportacionInstanceList
 						,'cuentaPorPagarInstanceTotal':facturaDeImportacionInstanceTotal]"
 						/>
 </content>

 <content tag="searchService">
 	<g:createLink action="search"/>
 </content>
 	

 	
	
	
</body>
</html>
