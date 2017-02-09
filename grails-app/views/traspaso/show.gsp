<!doctype html>
<html>
<head>
	<title>Traspaso ${traspasoInstance.id}</title>
	<meta name="layout" content="luxor">
</head>
<body>

<content tag="header">Traspaso ${traspasoInstance.id} </content>
<content tag="subHeader">
	<ol class="breadcrumb">
    	<li><g:link action="index">Traspasos</g:link></li>
    	<li><g:link action="create">Alta</g:link></li>
    	<li class="active"><strong>Consulta</strong></li>
    	%{-- <li><g:link action="edit" id="${traspasoInstance.id}">Edición</g:link></li> --}%
	</ol>
</content>

<content tag="document">
	<div class="wrapper wrapper-content animated fadeInRight">
		
		<div class="row">
			<div class="col-lg-7">
				<div class="ibox float-e-margins">
					<lx:iboxTitle title="Traspaso registrado"/>
				    <div class="ibox-content">
				    	<lx:errorsHeader bean="${traspasoInstance}"/>
				    	<form class="form-horizontal" >	
				    		<f:with bean="traspasoInstance">
				    			<f:display property="id" wrapper="bootstrap3" label="Folio"/>
				    			<f:display property="fecha"  wrapper="bootstrap3"/>
				    			<f:display property="cuentaOrigen" widget="tc" wrapper="bootstrap3" />
				    			<f:display property="cuentaDestino" widget="tc" wrapper="bootstrap3" />
				    			<f:display property="comentario" wrapper="bootstrap3"/>
				    		</f:with>
				    		<div class="form-group">
				    			<div class="col-lg-offset-3 col-lg-10">
				    				<lx:backButton label="Traspasos"/>
				    				<lx:createButton/>
				    				%{-- <lx:deleteButton bean="${traspasoInstance}"/> --}%
				    				<a href="" class="btn btn-danger " data-toggle="modal" data-target="#deleteDialog"><i class="fa fa-trash"></i> Eliminar</a> 
				    			</div>
				    		</div>
				    	</form>
				    </div>
				</div>
			</div>
			<div class="col-lg-5">
				<div class="ibox float-e-margins">
					<lx:iboxTitle title="Importes"/>
				    <div class="ibox-content">
						<form class="form-horizontal" >	
				    		<f:with bean="traspasoInstance">
				    			<f:display property="importe" widget="money" wrapper="bootstrap3"/>
								<f:display property="comision" widget="money" wrapper="bootstrap3"/>
								<f:display property="impuesto" widget="money" wrapper="bootstrap3"/>
					    		
				    		</f:with>
						</form>
				    </div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-lg-12">
				<div class="ibox float-e-margins">
					<lx:iboxTitle title="Movimientos"/>

				    <div class="ibox-content">
						<table class="table  table-bordered table-condensed">
							<thead>
								<tr>
									<th>Id</th>
									<th>Cuenta</th>
									<th>Mon</th>
									<th>T.C.</th>
									<th>Fecha</th>
									<th>Concepto</th>
									<th>Tipo</th>
									<th>Importe</th>
									<th>Comentario</th>
								</tr>
							</thead>
								<tbody>
									<g:each in="${traspasoInstance.movimientos}" var="row">
										<tr>
											<td>${row.id}
											<td>${fieldValue(bean: row, field: "cuenta")}</td>
											<td>${fieldValue(bean: row, field: "moneda")}</td>
											<td>${fieldValue(bean: row, field: "tc")}</td>
											<td><lx:shortDate date="${row.fecha }"/></td>
											<td>${fieldValue(bean: row, field: "concepto")}</td>
											<td>${fieldValue(bean: row, field: "tipo")}</td>
											<td><lx:moneyTableRow number="${row.importe }"/></td>
											<td>${fieldValue(bean: row, field: "comentario")}</td>
										</tr>
									</g:each>
								</tbody>
							</table>
				    </div>
				</div>
			</div>
		</div>
	
	</div>

	<div class="modal fade" id="deleteDialog" tabindex="-1">
		<div class="modal-dialog ">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Eliminar</h4>
				</div>
				<g:form action="delete" class="form-horizontal" method="DELETE" >
					<g:hiddenField name="id" value="${traspasoInstance.id}"/>
					<div class="modal-body">
						<p><strong>Eliminar traspaso ${traspasoInstance.id}</strong></p>
					</div>
					
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
						<g:submitButton class="btn btn-danger" name="aceptar"
								value="Eliminar" />
					</div>
				</g:form>
	
			</div><!-- moda-content -->
			
		</div><!-- modal-di -->
		
	</div>
	
</content>
	

</body>
</html>

