
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
    	<li><g:link action="proveedores">Proveedores</g:link></li>
    	<li class="active"><strong>Consulta rápida</strong></li>
    	<li><g:link action="edit" id="${proveedorInstance.id}">Edición</g:link></li>
	</ol>
</content>

<content tag="document">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-lg-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<div class="btn-group">
						    <lx:backButton/>
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
						    <li>
						    	<a href="#cuentasPorPagar"  data-toggle="tab">Cuentas x Pagar</a>
						    </li>
						    <li>
						    	<a href="#compras" data-toggle="tab">Compras</a>
						    </li>
						    
						</ul>
						<div class="panel-body">
							<div class="tab-content">
							  	<div role="tabpanel" class="tab-pane fade in active" id="home">
							  		<g:render template="/proveedor/showForm"/>
							  	</div>
							 	<div role="tabpanel" class="tab-pane fade" id="productos">
							  		<g:render template="/proveedor/productos"/>
							  	</div>
							  	<div role="tabpanel" class="tab-pane fade" id="agentes">
							  		<g:render template="/proveedor/agentes"/>
							  	</div>
							  	<div class="tab-pane fade" id="cuentasPorPagar">
							  		<div class="row toolbar-panel">
							  		    
							  		    <div class="col-md-3">
							  		        <div class="input-group">
							  		            <input type='text' id="pendientesFiltro" placeholder="Filtrar" class="form-control" autofocus="on">
							  		        </div>
							  		    </div>
							  		    <a href="" class="btn btn-outline btn-default" 
							  		    	data-target="#estadoDeCuentaDialog" data-toggle="modal">
							  		    	<i class="fa fa-print"></i> Estado de cuenta</a>
							  		    %{-- <div class="btn-group">
							  		        <button type="button" name="reportes"
							  		                class="btn btn-primary btn-outline dropdown-toggle" data-toggle="dropdown"
							  		                role="menu">
							  		                Reportes <span class="caret"></span>
							  		        </button>
							  		        <ul class="dropdown-menu">
							  		            <li>
							  		            	<g:link  action="estadoDecuenta"  >
							  		            	    <i class="fa fa-plus"></i> Estado de cuenta
							  		            	</g:link> 
							  		            </li>
							  		        </ul>
							  		    </div> --}%
							  		</div>
							  		<div id="cuentasPorPagar">

							  			<table id="pendientesGrid"
							  				class="display table table-striped table-hover table-bordered table-condensed">
							  				<thead>
							  					<tr>
							  						<td>Id</td>
							  						<th>Dcto</th>
							  						<th>Fecha</th>
							  						<th>BL</th>
							  						<th>Vto</th>
							  						<th>Moneda</th>
							  						<th>T.C.</th>
							  						<th>Total</th>
							  						<th>Requisitado</th>
							  						<th>Pagos</th>
							  						<th>Saldo</th>
							  					</tr>
							  				</thead>
							  				<tbody>
							  					<g:each in="${pendientes}" var="row">
							  						<tr>
							  							<td>
							  								<g:link action="show" id="${row.id}">
							  									<lx:idFormat id="${row.id}"/>
							  								</g:link>
							  							</td>
							  							<td>${fieldValue(bean: row, field: "documento")}</td>
							  							<td><lx:shortDate date="${row.fecha }"/></td>
							  							<td><lx:shortDate date="${row.fechaBL }"/></td>
							  							<td><lx:shortDate date="${row.vencimiento }"/></td>
							  							<td>
							  								${fieldValue(bean: row, field: "moneda")}
							  							</td>
							  							<td>${row.tc}</td>
							  							<td><lx:moneyFormat number="${row.total}"/></td>
							  							<td><lx:moneyFormat number="${row.requisitado}"/></td>
							  							<td><lx:moneyFormat number="${row.pagosAplicados}"/></td>
							  							<td><lx:moneyFormat number="${row.saldoActual}"/></td>
							  							
							  							
							  						</tr>
							  					</g:each>
							  				</tbody>
							  			</table>
							  			
							  		</div>


							  	</div>
							  	<div class="tab-pane fade" id="compras">
							  		
							  	</div>
							</div>
						</div>

						
				    </div>
				</div>

				
			</div>
			
		</div>
	</div>
	<div>
		<g:render template="estadoDeCuentadialog"/>
	</div>
	<script type="text/javascript">
		$(function(){
		    
		    $('#pendientesGrid').dataTable({
		        responsive: true,
		        aLengthMenu: [[20, 40, 60, 100, -1], [20, 40,60, 100, "Todos"]],
		        "language": {
		            "url": "${assetPath(src: 'datatables/dataTables.spanish.txt')}"
		        },
		        "dom": 'T<"clear">lfrtip',
		        "tableTools": {
		            "sSwfPath": "${assetPath(src: 'plugins/dataTables/swf/copy_csv_xls_pdf.swf')}"
		        },
		        "order": []
		    });

		    $("#pendientesFiltro").on('keyup',function(e){
		        var term=$(this).val();
		        $('#pendientesGrid').DataTable().search(
		            $(this).val()
		                
		        ).draw();
		    });

		    $('.date').bootstrapDP({
		        format: 'dd/mm/yyyy',
		        keyboardNavigation: false,
		        forceParse: false,
		        autoclose: true,
		        todayHighlight: true,

		    });
		});
	</script>
</content>

</body>
</html>


