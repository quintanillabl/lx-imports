<%@ page import="com.luxsoft.impapx.tesoreria.PagoProveedor" %>
<!doctype html>
<html>
<head>
	<title>Saldo de cuentas contables</title>
	<meta name="layout" content="luxor">
</head>
<body>

<content tag="header">
	Balanza de comprobación  ${session.periodoContable} 
</content>
	
<content tag="subHeader">
	(${session.empresa.nombre})
</content>

<content tag="document">
	<div class="row">
	    <div class="col-lg-12">
	        <div class="ibox float-e-margins">
	        	
	        	<div class="ibox-title">
	        		 <button data-target="#periodoDialog" data-toggle="modal" class="btn btn-outline btn-success  dim">
	        		 	<i class="fa fa-calendar"></i> 
	        		</button>
	        		<lx:refreshButton/>
	        		<div class="btn-group">
	        		    <button type="button" name="operaciones"
	        		            class="btn btn-info dropdown-toggle" data-toggle="dropdown"
	        		            role="menu">
	        		            Reportes <span class="caret"></span>
	        		    </button>
	        		    <ul class="dropdown-menu">
	        		    	<li>
	        		    		<g:link action="balanzaDeComprobacion" >
	        		    		    <i class="fa fa-print"></i> Balanza de comprobación
	        		    		</g:link> 
	        		    	</li>
	        		    	<li>
	        		    		<g:link action="balanceGeneral" >
	        		    		    <i class="fa fa-print"></i> Balance general
	        		    		</g:link> 
	        		    	</li>
	        		    	<li>
	        		    		<g:link action="estadoDeResultados" >
	        		    		    <i class="fa fa-print"></i> Estado de Resultados
	        		    		</g:link> 
	        		    	</li>
	        		    </ul>
	        		</div>
	        		
	        	    <div class="ibox-tools">
	        	        <a class="collapse-link">
	        	            <i class="fa fa-chevron-up"></i>
	        	        </a>
	        	        <a class="close-link">
	        	            <i class="fa fa-times"></i>
	        	        </a>
	        	    </div>
	        	</div>
	            <div class="ibox-content">
	            	<table id="grid" class="grid table table-responsive table-striped table-bordered table-hover">
	            		<thead>
	            			<tr>
	            				<th>Cuenta</th>
	            				<th>Descripcion</th>
	            				<th>Saldo Inicial</th>
	            				<th>Debe</th>
	            				<th>Haber</th>
	            				<th>Saldo Final</th>
	            			</tr>
	            		</thead>
	            		<tbody>
	            			<g:each in="${saldoPorCuentaContableInstanceList}" var="row">
	            				<tr id="${row.id}">
	            					<td >
	            						<g:link  action="show" id="${row.id}">
	            							${fieldValue(bean:row,field:"cuenta.clave")}
	            						</g:link>
	            					</td>
	            					<td>
	            						<g:link  action="show" id="${row.id}">
	            							${fieldValue(bean:row,field:"cuenta.descripcion")}
	            						</g:link>
	            					</td>
	            					<td>${formatNumber(number:row.saldoInicial,type:'currency')}</td>
	            					<td>${formatNumber(number:row.debe,type:'currency')}</td>
	            					<td>${formatNumber(number:row.haber,type:'currency')}</td>
	            					<td>${formatNumber(number:row.saldoFinal,type:'currency')}</td>
	            				</tr>
	            			</g:each>
	            		</tbody>
	            	</table>
	            </div>
	        </div>
	    </div>
	</div>

	<g:render template="/common/selectorDePeriodoContable" bean="${session.periodoContable}"/>
	
	
	
	
	<script type="text/javascript">
		$(function(){
 			$('#grid').dataTable({
                responsive: true,
                aLengthMenu: [[100, 150, 200, 250, -1], [100, 150, 200, 250, "Todos"]],
                "language": {
					"url": "${assetPath(src: 'datatables/dataTables.spanish.txt')}"
	    		},
	    		"dom": 'T<"clear">lfrtip',
	    		"tableTools": {
	    		    "sSwfPath": "${assetPath(src: 'plugins/dataTables/swf/copy_csv_xls_pdf.swf')}"
	    		},
	    		"order": []
            });

            

		});
	</script>
	
</content>
	
</body>
</html>
