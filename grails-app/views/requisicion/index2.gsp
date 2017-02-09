<%@ page import="com.luxsoft.impapx.Requisicion" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="cxp">
	<title>Requisiciones</title>
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
		        		    <i class="fa fa-plus"></i> Nueva requisición
		        		</g:link> 
		        	</li>
		        	%{-- <li>
		        	    <g:link action="programacionDePagos"  >
		        	        <i class="fa fa-list-ol"></i> Programación de pagos
		        	    </g:link> 
		        	</li> --}%
		        </ul>
		    </div>
		</div>
		<table id="grid"
			class="display table table-striped table-hover table-bordered table-condensed">
			<thead>
				<tr>
					<g:sortableColumn property="id"
						title="${message(code: 'requisicion.id.label', default: 'Folio')}" />
					<th class="header"><g:message
							code="requisicion.proveedor.label" default="Proveedor" /></th>
					<g:sortableColumn property="concepto"
						title="${message(code: 'requisicion.concepto.label', default: 'Concepto')}" />
					<g:sortableColumn property="fecha"
						title="${message(code: 'requisicion.fecha.label', default: 'Fecha')}" />
					<g:sortableColumn property="fechaDelPago"
						title="${message(code: 'requisicion.fechaDelPago.label', default: 'Fecha Del Pago')}" />
					<g:sortableColumn property="moneda"
						title="${message(code: 'requisicion.moneda.label', default: 'Moneda')}" />
					<g:sortableColumn property="tc"
						title="${message(code: 'requisicion.tc.label', default: 'Tc')}" />
					<g:sortableColumn property="total"
						title="${message(code: 'requisicion.total.label', default: 'Total')}" />
					<th>Pago</th>
					
				</tr>
			</thead>
			<tbody>
				<g:each in="${requisicionInstanceList}" var="requisicionInstance">
					<tr>
						<td><g:link action="edit" id="${requisicionInstance.id}">
								${fieldValue(bean: requisicionInstance, field: "id")}
							</g:link>
						</td>
						<td><g:link action="edit" id="${requisicionInstance.id}">
								${fieldValue(bean: requisicionInstance, field: "proveedor")}
							</g:link>
						</td>
						<td>${fieldValue(bean: requisicionInstance, field: "concepto")}</td>
						<td><lx:shortDate date="${requisicionInstance.fecha}"/></td>
						<td><lx:shortDate date="${requisicionInstance.fechaDelPago}"/></td>
						<td>${fieldValue(bean: requisicionInstance, field: "moneda")}</td>
						<td>${fieldValue(bean: requisicionInstance, field: "tc")}</td>
						<td><lx:moneyFormat number="${requisicionInstance.total}"/></td>
						<td>${fieldValue(bean: requisicionInstance, field: "pagoProveedor.id")}</td>
						
					</tr>
				</g:each>
			</tbody>
		</table>
		<div class="pagination">
			<g:paginate total="${requisicionInstanceCount ?: 0}" />
		</div>
		
		<filterpane:filterPane 
			domain="com.luxsoft.impapx.Requisicion" dialog="true"
			dateFormat="${[fecha: 'dd/MM/yyyy', entrega: 'dd/MM/yyyy']}"
			excludeProperties="dateCreated,lastUpdated"
			filterPropertyValues="${[fecha:[years:2013..2019,precision:'day',default:new Date()-30]]}"/>
 	</content>

 	<conten tag="script">
 		
 	</conten>
	
	
</body>
</html>
