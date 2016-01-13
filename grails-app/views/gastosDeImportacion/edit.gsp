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
				</div>		
			    </div>
			</div>
		</div>
	</div>
</content>


 	

 	
	
	
</body>
</html>
