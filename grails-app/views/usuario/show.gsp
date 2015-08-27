<html>
<head>
	<meta charset="UTF-8">
	<meta name="layout" content="luxor"/>
	<title>Usuario: ${usuarioInstance.id}</title>
</head>
<body>

<content tag="header">
	${usuarioInstance.username} 
</content>

<content tag="document">
	<div class="ibox float-e-margins">
		<lx:iboxTitle title="Usuario: ${usuarioInstance.id}"/>
	    <div class="ibox-content">
			<ul class="nav nav-pills ">
				<li><g:link action="index">
							<span class="glyphicon glyphicon-arrow-left"></span> Catálogo
					    </g:link>
					</li>
				<li><g:link action="edit" id="${usuarioInstance.id}">
							<span class="glyphicon glyphicon-pencil"></span> Editar
					    </g:link>
				</li>
					
				<li><g:link action="create">
						<span class="glyphicon glyphicon-plus"></span> Nuevo
				    </g:link>
				</li>
			</ul>
		    <ul class="nav nav-tabs" role="tablist">
			  <li role="presentation" class="active"><a href="#usuario" role="tab" data-toggle="tab">Propiedades</a></li>
			  <li role="presentation"><a href="#roles" role="tab" data-toggle="tab">Roles</a></li>
			</ul>
			<div class="tab-content">
				
				<div role="tabpanel" class="tab-pane active" id="usuario">
					<div class="form-panel">
						<form class="form-horizontal" >
						<f:with bean="${usuarioInstance}">
							<f:field property="username" widget-class="form-control"/>
							<f:field property="apellidoPaterno" widget-class="form-control"/>
							<f:field property="apellidoMaterno" widget-class="form-control"/>
							<f:field property="nombres" widget-class="form-control"/>
							<f:field property="enabled" widget-class="form-control" label="Activo"/>
							<f:field property="accountExpired" widget-class="form-control" label="La cuenta expira"/>
							<f:field property="accountLocked" widget-class="form-control" label="Cuenta bloqueada"/>
							<f:field property="passwordExpired" widget-class="form-control" label="Password vencido"/>
							<f:field property="numeroDeEmpleado" widget-class="form-control"/>
							<f:field property="puesto" widget-class="form-control"/>
							
						</f:with>
						</form>
					</div>
				</div>

				  <div role="tabpanel" class="tab-pane" id="roles">
				  		<div class="form-panel">
					  		<table class="table table-striped table-bordered table-condensed">
					  			<thead>
					  				<tr>
					  					<th>Id</th>
					  					<th>Nombre</th>
					  					<th>Asignar</th>
					  				</tr>
					  			</thead>
					  			<tbody>
					  				<g:each in="${usuarioInstance.getAuthorities()}" var="row" status="i">
					  					<tr>
					  						<td>${fieldValue(bean:row,field:"id")}</td>
					  						<td>${fieldValue(bean:row,field:"authority")}</td>
					  						<td><g:checkBox name="roles" value="${row.id}" checked="true"/></td>
					  					</tr>
					  				</g:each>
					  			</tbody>
					  		</table>
				  		</div>
				  </div>

			</div>
	    </div>
	</div>
</content>
	

	

	
<content tag="form">
	
	<g:hasErrors bean="${usuarioInstance}">
        <div class="alert alert-danger">
            <g:renderErrors bean="${usuarioInstance}" as="list" />
        </div>
    </g:hasErrors>


    
	
	<fieldset disabled>

	
	
	  
	
	</fieldset>
	

</content>
	
</body>
</html>