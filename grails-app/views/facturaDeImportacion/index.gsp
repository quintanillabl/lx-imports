<%@ page import="com.luxsoft.impapx.FacturaDeImportacion" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="cxp">
	<g:set var="entityName"
		value="${message(code: 'facturaDeImportacion.label', default: 'Facturas')}" />
	<title>Facturas de importación</title>
	<asset:javascript src="fp.js"/>
	<asset:stylesheet src="fp.css"/>
	<asset:stylesheet src="jquery-ui.css"/>
	<asset:javascript src="jquery-ui/autocomplete.js"/>
</head>
<body>
 	
 	<content tag="document">
 		
		<div class="row toolbar-panel">
    		<div class="col-md-6">
    			<g:form class="form-horizontal" action="show">
    				<g:hiddenField name="id" />
    	      		<div class="input-group">
    	      		    <input id="compraField" name="compraDesc" type="text" 
    			    	    class="form-control " placeholder="Buscar">
          		    	<span class="input-group-btn">
				       		<button id="buscarBtn" type="submit" class="btn btn-default" disabled="disabled">
								<i class="fa fa-search"></i></span>
							</button> 
          		      	</span>
    	      		</div>
          		</g:form>
    		</div>

		    <div class="btn-group">
	        	<lx:refreshButton/>
	            <lx:printButton/>
	            <filterpane:filterButton text="Filtrar" />
		    </div>
		    <div class="btn-group">
		        <button type="button" name="operaciones"
		                class="btn btn-default dropdown-toggle" data-toggle="dropdown"
		                role="menu">
		                Operaciones <span class="caret"></span>
		        </button>
		        <ul class="dropdown-menu">
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
		        </ul>
		    </div>
		</div>
		<g:render template="facturasPanel" 
			model="['cuentaPorPagarInstance':facturaDeImportacionInstance
					,'facturasList':facturaDeImportacionInstanceList
					,'cuentaPorPagarInstanceTotal':facturaDeImportacionInstanceTotal]"
					/>
		<filterpane:filterPane 
			domain="com.luxsoft.impapx.FacturaDeImportacion" dialog="true"
			dateFormat="${[fecha: 'dd/MM/yyyy', entrega: 'dd/MM/yyyy']}"
			excludeProperties="dateCreated,lastUpdated"
			filterPropertyValues="${[fecha:[years:2013..2019,precision:'day',default:new Date()-30]]}"/>
 	</content>

 	<conten tag="script">
 		
 	</conten>
	
	
</body>
</html>
