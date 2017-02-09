<!doctype html>
<html>
<head>
	<title>Comisión ${comisionInstance.id}</title>
	<meta name="layout" content="luxor">
</head>
<body>

<content tag="header">Traspaso ${comisionInstance.id} </content>
<content tag="subHeader">
	<ol class="breadcrumb">
    	<li><g:link action="index">Traspasos</g:link></li>
    	<li><g:link action="create">Alta</g:link></li>
    	<li class="active"><strong>Consulta</strong></li>
	</ol>
</content>

<content tag="document">
	<div class="wrapper wrapper-content animated fadeInRight">
		
		<div class="row">
			<div class="col-lg-8">
				<div class="ibox float-e-margins">
					<lx:iboxTitle title="Comisión bancaria"/>
				    <div class="ibox-content">
				    	<lx:errorsHeader bean="${comisionInstance}"/>
				    	<form class="form-horizontal" >	
				    		<f:with bean="comisionInstance">
				    			<g:hiddenField name="id" value="${comisionInstance.id}"/>
				    			<g:hiddenField name="version" value="${comisionInstance.version}"/>
				    			<f:display property="fecha" wrapper="bootstrap3"/>
				    			<f:display property="cuenta" wrapper="bootstrap3"/>
				    			<f:display property="referenciaBancaria" widget-class="form-control " wrapper="bootstrap3"/>
				    			<f:display property="comentario" widget-class="form-control "  wrapper="bootstrap3"/>
				    			<f:display property="cxp" widget-class="form-control "  wrapper="bootstrap3" label="Factura"/>
				    			
				    		</f:with>
				    		<div class="form-group">
				    			<div class="col-lg-offset-3 col-lg-10">
				    				<lx:backButton label="Comisiones"/>
				    				<g:link class="btn btn-outline btn-info" action="edit" id="${comisionInstance.id}">
				    					<i class="fa fa-pencil"></i> Editar
				    				</g:link>
				    				<a href="" class="btn btn-danger " data-toggle="modal" data-target="#deleteDialog"><i class="fa fa-trash"></i> Eliminar</a> 

				    			</div>
				    		</div>
				    	</form>
				    </div>
				</div>
			</div>
			<div class="col-lg-4">
				<div class="ibox float-e-margins">
					<lx:iboxTitle title="Importes"/>
				    <div class="ibox-content">
						<form class="form-horizontal" >	
				    		<f:with bean="comisionInstance">
				    			<f:display property="comision" widget="money" wrapper="bootstrap3"/>
				    			<f:display property="impuestoTasa" widget="porcentaje" wrapper="bootstrap3" label="Tasa de impuesto(%)"/>
				    			<f:display property="impuesto" widget="money"  wrapper="bootstrap3"/>
				    			
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
									<g:each in="${comisionInstance.movimientos}" var="row">
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
					<g:hiddenField name="id" value="${comisionInstance.id}"/>
					<div class="modal-body">
						<p><strong>Eliminar comisión ${comisionInstance.id}</strong></p>
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

