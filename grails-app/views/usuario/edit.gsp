<html>
<head>
	<meta charset="UTF-8">
	<meta name="layout" content="createForm"/>
	<title>Usuario: ${usuarioInstance.id}</title>
</head>
<body>

	<content tag="header">
		<h3>${usuarioInstance.nombre} (${usuarioInstance.puesto})</h3>
	</content>
	
	<content tag="operaciones">
		<ul class="nav nav-pills nav-stacked">
			<li><g:link action="index">
  					<span class="glyphicon glyphicon-arrow-left"></span> Catálogo
  			    </g:link>
  			</li>
  			<li><g:link action="create">
  					<span class="glyphicon glyphicon-plus"></span> Nuevo
  			    </g:link>
  			</li>
  			<li><g:link action="cambioDePassword" id="${usuarioInstance.id}">
  					<span class="glyphicon glyphicon-pencil"></span> Cambiar password
  			    </g:link>
  			</li>
  			<li><g:link action="cambiarPassword" id="${usuarioInstance.id}"
  				onclick="return confirm('Seguro que desa eliminar el usuario de ${usuarioInstance.nombre}');">
  					<span class="glyphicon glyphicon-trash"></span> Eliminar
  			    </g:link>
  			</li>
		</ul>
	</content>
	
	<content tag="formTitle">Usuario: ${usuarioInstance.id}</content>
	
	<content tag="form">
		
		<g:hasErrors bean="${usuarioInstance}">
            <div class="alert alert-danger">
                <g:renderErrors bean="${usuarioInstance}" as="list" />
            </div>
        </g:hasErrors>


        <ul class="nav nav-tabs" role="tablist">
		  <li role="presentation" class="active"><a href="#usuario" role="tab" data-toggle="tab">Usuario</a></li>
		  <li role="presentation"><a href="#roles" role="tab" data-toggle="tab">Roles</a></li>
		</ul>
		
		
		<g:form class="form-horizontal" action="update" method="PUT">
		<div class="tab-content">
			
			<div role="tabpanel" class="tab-pane active" id="usuario">
				<div class="form-panel">
					<g:hiddenField name="id" value="${usuarioInstance.id}"/>
					<g:hiddenField name="version" value="${usuarioInstance.version}"/>
					<g:hiddenField name="password" value="${usuarioInstance.password}"/>
					<f:with bean="${usuarioInstance}">
						<f:field property="username" widget-class="form-control"/>
						<f:field property="apellidoPaterno" widget-class="form-control"/>
						<f:field property="apellidoMaterno" widget-class="form-control"/>
						<f:field property="nombres" widget-class="form-control"/>
						
						<f:field property="enabled" widget-class="form-control" label="Activo"/>
						<f:field property="accountExpired" widget-class="form-control" label="La cuenta expira"/>
						<f:field property="accountLocked" widget-class="form-control" label="Cuenta bloqueada"/>
						<f:field property="passwordExpired" widget-class="form-control" label="Password vencido"/>
					</f:with>

					<div class="form-group">
				    	<div class="col-sm-offset-8 col-sm-4">
				    		
				      		<button type="submit" class="btn btn-primary">
				      			<span class="glyphicon glyphicon-floppy-save"></span> Salvar
				      		</button>
				    	</div>
				  	</div>
					
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
				  				<g:each in="${com.luxsoft.sec.Rol.list(sort:'id',order:'asc')}" var="row" status="i">
				  					<tr>
				  						<td>${fieldValue(bean:row,field:"id")}</td>
				  						<td>${fieldValue(bean:row,field:"authority")}</td>
				  						<td><g:checkBox name="roles" value="${row.id}" 
				  							checked="${usuarioInstance.getAuthorities().contains(row)}"/></td>
				  					</tr>
				  				</g:each>
				  			</tbody>
				  		</table>
			  		</div>
			  </div>

		</div>
		</g:form>
		  
		
		
		

	</content>
	
</body>
</html>