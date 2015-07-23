<!DOCTYPE html>
<html>
	<head>
		
		<title>Usuarios</title>
		%{-- <asset:stylesheet src="datatables/datatables.css"/>
		<asset:stylesheet src="jquery-ui.css"/>
		<asset:javascript src="datatables/jquery.datatables.js"/>
		<asset:javascript src="jquery-ui/autocomplete.js"/> --}%
		<asset:stylesheet src="jquery-ui.css"/>
		<asset:javascript src="jquery-ui/autocomplete.js"/>
		
	</head>
	
	<body>
		<div class="container">
			
			<div class="row">
				<div class="col-md-12">
					<div class="alert alert-info">
						<h2>Consola para la administración de usuarios</h2>
						<g:if test="${flash.message}">
							<span class="label label-warning text-center">${flash.message}</span>
						</g:if>
					</div>

				</div>
				

			</div><!-- end .row 1 -->
			
			<div class="row">
				<div class="col-md-12">
					<div class="toolbar-panel">
						<div class="btn-group">
							<input type='text' id="nombreField" placeholder="Buscar" class="form-control">
						</div>
						
						
						<div class="btn-group">
							<g:link action="index" class="btn btn-default">
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
									<a href="#importarModal" data-toggle="modal">
										<span class="glyphicon glyphicon-upload"></span> Importar empleado
									</a>
									<g:link action="create">
										<span class="glyphicon glyphicon-floppy-saved"></span> Nuevo
									</g:link>
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
								<li><g:link controller="reporte" action="usuariosDelSistema"> Usuarios</g:link></li>
							    
							</ul>
						</div>
						
					</div>
					
				</div>
			</div><!-- end .row 2 button panel -->

			<div class="row">
				<div class="col-md-12">
					<g:render template="gridPanel"/>
				</div>
			</div>
			<g:render template="importarEmpleadoDialog"/>
			
			
		</div>
	
	<script type="text/javascript">
		

	</script>
		
	</body>
	
</html>
