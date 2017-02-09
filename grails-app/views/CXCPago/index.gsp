<!doctype html>
<html>
<head>
	<title>Cobros (CxC)</title>
	<meta name="layout" content="luxor">
</head>
<body>

<content tag="header">
	Cobros de CxC registrados en el periodo: ${session.periodoTesoreria.asPeriodoText()}
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
	        		 <sec:ifAnyGranted roles="TESORERIA">
	        			<lx:createButton/> 
	        		 </sec:ifAnyGranted>
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
	            				<td>Folio</td>
	            				<td>Banco</td>
	            				<td>Cliente</td>
	            				<td>Fecha</td>
	            				<td>F.P</td>
	            				<td>Mon</td>
	            				<td>T.C</td>
	            				<td>Total</td>
	            				<td>Disponible MN</td>
	            				<td>Ingreso</td>		
	            			</tr>
	            		</thead>
	            		<tbody>
	            			<g:each in="${cobros}" var="row">
            				<tr>
            					<td><g:link action="show" id="${row.id}">
            						${fieldValue(bean: row, field: "id")}
            						</g:link>
            					</td>
            					<td>${fieldValue(bean: row, field: "cuenta.banco.nombre")}</td>
            					<td>${fieldValue(bean: row, field: "cliente.nombre")}</td>
            					<td><lx:shortDate date="${row.fecha}"/></td>
            					<td>${fieldValue(bean: row, field: "formaDePago")}</td>
            					<td>${fieldValue(bean: row, field: "moneda")}</td>
            					<td>${fieldValue(bean: row, field: "tc")}</td>
            					<td><lx:moneyFormat number="${row.total }"/></td>
            					<td><lx:moneyFormat number="${row.disponible }"/></td>	
            					<td>${row?.ingreso?.id}</td>
            				
            				</tr>
	            			</g:each>
	            		</tbody>
	            	</table>
	            </div>
	        </div>
	    </div>
	    <g:render template="/common/periodoDeTesoreria"/>
	</div>
	
	
	<script type="text/javascript">
		$(function(){
 			$('#grid').dataTable({
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

            $('#data_4 .input-group.date').bootstrapDP({
                minViewMode: 1,
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

