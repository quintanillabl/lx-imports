
<!DOCTYPE html>
<html>
<head>
	<title>CFDI s</title>
	<meta name="layout" content="operaciones">
</head>
<body>

<content tag="header">
	Comprobantes fiscales CFDI retenciones y pagos
</content>

<content tag="periodo">
	Periodo:${session.periodo.mothLabel()} 
</content>

<content tag="operaciones">
	
</content>

<content tag="grid">
	<table class="table table-striped table-bordered table-condensed" id="grid">
		<thead>
			<tr>
				<th>Id</th>
				<th>Emisor RFC</th>
				%{-- <th>Folio</th> --}%
				<th>Fecha</th>
				<th>Receptor</th>
				<th>UUID</th>			
				<th>Total</th>
				<th>Comentario</th>
			</tr>
		</thead>
		<tbody>
			<g:each in="${cfdiRetencionesInstanceList}" var="row">
				<tr >
					<td>
						<g:link action="edit" id="${row.id}">
							${fieldValue(bean:row,field:"id")}
						</g:link>
					</td>
					<td>${fieldValue(bean:row,field:"emisorRfc")}</td>
					%{-- <td>${fieldValue(bean:row,field:"folio")}</td> --}%
					<td><g:formatDate date="${row.fecha}" format="dd/MM/yyyy"/></td>
					<td>
						<abbr title="${row.receptor}">
						${org.apache.commons.lang.StringUtils.substring(row.receptor,0,50)}
						</abbr>
					</td>
					<td>
						<abbr title="${row.uuid}">
						${org.apache.commons.lang.StringUtils.substringAfterLast(row.uuid,'-')}
						</abbr>
					</td>
					<td><g:formatNumber number="${row.total}" type="currency"/></td>
					<td>
						%{-- <g:if test="${row?.cancelacion}">CANCELADO</g:if>
						<g:else>${row.comentario}</g:else> --}%
					</td>
				</tr>
			</g:each>
		</tbody>
	</table>
</content>

<content tag="searchService">
	<g:createLink action="search"/>
</content>
	
</body>
</html>


%{-- <!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>Cfdi's</title>
</head>
<body>
	<div class="container">

		<!-- end .row 1 Header -->
		<div class="row row-header">
			<div class="col-md-12">
				<div class="alert alert-info">
					<h4>Comprobantes fiscales digitales CFDIs</h4>
					<g:if test="${flash.message}">
	                    <div class="">
	                        <span class="label label-warning">${flash.message}</span>
	                    </div>
                	</g:if> 
				</div>
			</div>
		</div><!-- end .row -->
		
		<div class="row toolbar-panel">
		    <div class="col-md-6">
		    	<input type='text' id="filtro" placeholder="Filtrar" class="form-control" autofocus="on">
		      </div>

		    <div class="btn-group">
		        
		        <g:link action="index" class="btn btn-default ">
		            <span class="glyphicon glyphicon-repeat"></span> Refrescar
		        </g:link>
		    </div>
		    <div class="btn-group">
		        <button type="button" name="operaciones"
		                class="btn btn-default dropdown-toggle" data-toggle="dropdown"
		                role="menu">
		                Operaciones <span class="caret"></span>
		        </button>
		        <ul class="dropdown-menu">
		            <li>
		                <g:link action="create" ><i class="fa fa-plus"></i> Nueva</g:link>
		            </li>
		        </ul>
		    </div>
		    <div class="btn-group">
		        <button type="button" name="reportes"
		                class="btn btn-default dropdown-toggle" data-toggle="dropdown"
		                role="menu">
		                Reportes <span class="caret"></span>
		        </button>

		        <ul class="dropdown-menu">
		              
		            
		        </ul>
		    </div>
		    
		</div>

	</div>
	
</body>
</html> --}%