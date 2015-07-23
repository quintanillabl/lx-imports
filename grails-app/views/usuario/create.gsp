<html>
<head>
	<meta charset="UTF-8">
	<meta name="layout" content="createForm"/>
	<title>Alta de usuario</title>
</head>
<body>

	<content tag="header">
		<h3>${"Usuario nuevo"}</h3>
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
		</ul>
	</content>
	
	<content tag="formTitle">Usuario nuevo</content>
	
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

		<g:form class="form-horizontal" action="save">
		<div class="tab-content">

		  <div role="tabpanel" class="tab-pane active" id="usuario">
		  		<div class="form-panel">
				
					
					<f:with bean="${usuarioInstance}">
						<f:field property="username" input-class="form-control" input-autocomplete="on" input-autofocus="autofocus"/>
						<f:field property="apellidoPaterno" input-class="form-control uppercase-field "/>
						<f:field property="apellidoMaterno" input-class="form-control uppercase-field"/>
						<f:field property="nombres" input-class="form-control uppercase-field" input-autocomplete="on"/>
						<f:field property="email" input-class="form-control" input-autocomplete="on"/>
						<f:field property="password" input-class="form-control" input-type="password" 
							input-autocomplete="off"/>
						<f:field property="confirmPassword" input-class="form-control" label="Confirmar" 
							input-type="password"  input-autocomplete="off"/>
						<f:field property="enabled" input-class="form-control" label="Activo"/>
						%{-- <f:field property="tarjeta" input-class="form-control"/>
						<f:field property="numeroDeEmpleado" input-class="form-control"/> --}%
					</f:with>

					<div class="form-group">
				    	<div class="col-sm-offset-8 col-sm-4">
				    		<g:link action="index" class="btn btn-default">
									<span class="glyphicon glyphicon-arrow-left"></span> Cancelar
						    	</g:link>
				      		<button type="submit" class="btn btn-default">
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
			  				<g:each in="${com.luxsoft.sx4.sec.Role.list(sort:'id',order:'asc')}" var="row" status="i">
			  					<tr>
			  						<td>${fieldValue(bean:row,field:"id")}</td>
			  						<td>${fieldValue(bean:row,field:"authority")}</td>
			  						<td><g:checkBox name="roles" value="${row.id}" checked="false"/></td>
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