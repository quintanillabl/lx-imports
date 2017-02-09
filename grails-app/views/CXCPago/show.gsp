<!doctype html>
<html>
<head>
	<title>Cobro ${CXCPagoInstance.id}</title>
	<meta name="layout" content="luxor">
</head>
<body>

<content tag="header">Cobro ${CXCPagoInstance.id} </content>
<content tag="subHeader">
	<ol class="breadcrumb">
    	<li><g:link action="index">Cobros</g:link></li>
    	<li><g:link action="create">Alta</g:link></li>
    	<li class="active"><strong>Consulta</strong></li>
    	<li><g:link action="edit" id="${CXCPagoInstance.id}">Edición</g:link></li>
	</ol>
</content>

<content tag="document">
	<div class="wrapper wrapper-content animated fadeInRight">
		
		<div class="row">
			<div class="col-lg-7">
				<div class="ibox float-e-margins">
					<lx:iboxTitle title="Cobro registrado"/>
				    <div class="ibox-content">
				    	<lx:errorsHeader bean="${CXCPagoInstance}"/>
				    	<form class="form-horizontal" >	
				    		<f:with bean="CXCPagoInstance">
				    			<f:display property="id" wrapper="bootstrap3" label="Folio"/>
				    			<f:display property="cliente" wrapper="bootstrap3"/>
				    			<f:display property="fecha"  wrapper="bootstrap3"/>
				    			<f:display property="formaDePago"  wrapper="bootstrap3"/>
				    			<f:display property="cuenta" widget="tc" wrapper="bootstrap3" />
				    			<f:display property="tc" widget-class="form-control" wrapper="bootstrap3"/>
								<f:display property="fechaBancaria" wrapper="bootstrap3"/>
								<f:display property="referenciaBancaria" wrapper="bootstrap3"/>
								<f:display property="comentario" wrapper="bootstrap3"/>
								<div class="form-group">
									<label for="ingreso" class="control-label col-sm-3">Ingreso</label>
									<div class="col-sm-9">
										<p class="form-control-static">
											<a data-toggle="modal" data-target="#ingresoForm">${CXCPagoInstance?.ingreso?.id}</a>
										</p>
										%{-- <input name="ingreso" class="form-control" value="${CXCPagoInstance.ingreso}"> --}%
										
									</div>
								</div>
				    		</f:with>
				    		<div class="form-group">
				    			<div class="col-lg-offset-3 col-lg-10">
				    				<lx:backButton label="Cobros"/>
				    				<lx:createButton/>
				    				<lx:editButton id="${CXCPagoInstance.id}"/>
				    				
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
				    		<f:with bean="CXCPagoInstance">
				    			<f:display property="total" widget="money" wrapper="bootstrap3"/>
								<f:display property="aplicado" widget="money" wrapper="bootstrap3"/>
								<f:display property="disponible" widget="money" wrapper="bootstrap3"/>
					    		<g:if test="${CXCPagoInstance.moneda!=com.luxsoft.lx.utils.MonedaUtils.PESOS}">
					    			<legend>M.N</legend>
					    			<f:display property="totalMN" widget="money" wrapper="bootstrap3" label="Total"/>
									<f:display property="aplicadoMN" widget="money" wrapper="bootstrap3" label="Aplicado"/>
									<f:display property="disponibleMN" widget="money" wrapper="bootstrap3" label="Disponible"/>
					    		</g:if>
				    		</f:with>

				    		
				    		
						</form>
				    </div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-lg-12">
				<div class="ibox float-e-margins">
					<lx:iboxTitle title="Aplicaciones"/>
				    <div class="ibox-content">
						<table class=" grid table  table-hover table-bordered table-condensed">
							<thead>
								<tr>
									<th class="header">Aplicacion</th>			
									<th class="header">Fecha</th>
									<th class="header">Documento</th>
									<th class="header">Fecha(Docto)</th>			
									<th class="header">Pagado</th>
									<th class="header">Comentario</th>
									
								</tr>
							</thead>
							<tbody>
								<g:each in="${CXCPagoInstance.aplicaciones}" var="row">
									<tr id="${fieldValue(bean:row, field:"id")}">
										<td>
											<g:link controller="CXCAplicacion" action="show" id="${row.id}">
												${row.id}
											</g:link>
										</td>				
										<td><lx:shortDate date="${row.fecha}" /></td>
										<td>${fieldValue(bean: row, field: "factura.id")}</td>
										<td>${fieldValue(bean: row, field: "factura.fecha")}</td>				
										<td><lx:moneyFormat number="${row.total }" /></td>
										<td>${fieldValue(bean: row, field: "comentario")}</td>				
									</tr>
								</g:each>
							</tbody>
						</table>
				    </div>
				</div>
			</div>
		</div>

	<div class="modal fade" id="ingresoForm" tabindex="-1">
		<div class="modal-dialog ">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Ingreso en bancos</h4>
				</div>
				<g:form action="show" class="form-horizontal" >
					<div class="modal-body">
						<div class="form">
							
						</div>
					</div>
					
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
					</div>
				</g:form>
			</div>
			<!-- moda-content -->
		</div>
		<!-- modal-di -->
	</div>	
	</div>

	<script type="text/javascript">
		$(document).ready(function(){
			$('#ingresoForm').on('show.bs.modal', function (event) {
				console.log('Cargando datos .....');
			  	var modal = $(this);
        		modal.find('.form').load("${createLink(action:'mostrarIngreso')}",{id:"${CXCPagoInstance.id}"});
        	});
		});
	</script>
	
</content>
	

</body>
</html>


