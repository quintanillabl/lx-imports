
<%@ page import="com.luxsoft.impapx.Proveedor" %>
<!DOCTYPE html>
<html>
<head>
	<title>Proveedor ${proveedorInstance.id}</title>
	<meta name="layout" content="luxor">
</head>
<body>

<content tag="header">
	Proveedor ${proveedorInstance.nombre}
</content>

<content tag="subHeader">
	<ol class="breadcrumb">
    	<li><g:link action="index">Proveedores</g:link></li>
    	<li class="active"><strong>Consulta</strong></li>
    	<li><g:link action="edit" id="${proveedorInstance.id}">Edición</g:link></li>
	</ol>
</content>

<content tag="document">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-lg-10">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<div class="btn-group">
						    <lx:backButton/>
						    <lx:createButton/>
						    <lx:editButton id="${proveedorInstance?.id}"/>
						    <lx:printButton/>
						</div>
					</div>
				    <div class="ibox-content">
						<lx:errorsHeader bean="${proveedorInstance}"/>
						<ul class="nav nav-tabs" role="tablist">
						    <li role="presentation" class="active">
						    	<a href="#home" aria-controls="home" role="tab" data-toggle="tab">Propiedades</a>
						    </li>
						    <li role="presentation">
						    	<a href="#productos" aria-controls="productos" data-toggle="tab">Productos</a>
						    </li>
						    <g:if test="${proveedorInstance.agenciaAduanal}">
						    	<li role="agentes">
						    		<a href="#agentes"  data-toggle="tab">Agentes</a>
						    	</li>
						    </g:if>
						    
						</ul>
						<div class="panel-body">
							<div class="tab-content">
							  	<div role="tabpanel" class="tab-pane fade in active" id="home">
							  		<g:render template="showForm"/>
							  	</div>
							 	<div role="tabpanel" class="tab-pane fade" id="productos">
							  		<g:render template="productos"/>
							  	</div>
							  	<div role="tabpanel" class="tab-pane fade" id="agentes">
							  		<g:render template="agentes"/>
							  	</div>
							</div>
						</div>

						
				    </div>
				</div>

				
			</div>
			
		</div>
	</div>
</content>

</body>
</html>


