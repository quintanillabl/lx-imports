<%@ page import="com.luxsoft.impapx.FacturaDeImportacion" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="cxp">
	<title>Gastos de importación</title>
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
    	      		    <input id="searchField" name="searchDesc" type="text" 
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
		<table id="grid"
			class="display table table-striped table-hover table-bordered table-condensed">
			<thead>
				<tr>
					<g:sortableColumn property="id" title="Id" />
					<g:sortableColumn property="proveedor.nombre" title="Proveedor" />

					<th class="header">Dcto</th>
					<th class="header">Fecha</th>
					<th class="header">Vto</th>
					<th class="header">Moneda</th>
					<th class="header">Total</th>
					<th class="header">Pagos</th>
					<th class="header">Saldo</th>
					<th class="header">Creada</th>
					<th class="header">Modificada</th>
				</tr>
			</thead>
			<tbody>
				<g:each in="${gastosDeImportacionInstanceList}" var="row">
					<tr>
						<td>
							<g:link action="show" id="${row.id}">
								<lx:idFormat id="${row.id}"/>
							</g:link></td>
						<td><g:link action="show" id="${row.id}">
								${fieldValue(bean: row, field: "proveedor.nombre")}
							</g:link>
						</td>
						<td>${fieldValue(bean: row, field: "documento")}</td>
						<td><lx:shortDate date="${row.fecha }"/></td>
						<td><lx:shortDate date="${row.vencimiento }"/></td>
						<td>
							${fieldValue(bean: row, field: "moneda")}
						</td>
						<td><lx:moneyFormat number="${row.total}"/></td>
						<td><lx:moneyFormat number="${row.pagosAplicados}"/></td>
						<td><lx:moneyFormat number="${row.saldoActual}"/></td>
						
						<td><abbr title="${g.formatDate(date:row.dateCreated)}">
							...</abbr></td>
						<td><abbr title="${g.formatDate(date:row.lastUpdated)}">
							...</abbr></td>
					</tr>
				</g:each>
			</tbody>
		</table>
		<div class="pagination">
			<g:paginate total="${gastosDeImportacionInstanceCount ?: 0}" />
		</div>
		<filterpane:filterPane 
			domain="com.luxsoft.impapx.GastosDeImportacion" dialog="true"
			dateFormat="${[fecha: 'dd/MM/yyyy', entrega: 'dd/MM/yyyy']}"
			excludeProperties="dateCreated,lastUpdated"
			filterPropertyValues="${[fecha:[years:2013..2019,precision:'day',default:new Date()-30]]}"/>
 	</content>

 	<conten tag="script">
 		
 	</conten>
	
	
</body>
</html>
