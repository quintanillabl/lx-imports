<%@ page import="com.luxsoft.impapx.contabilidad.CuentaContable" %>
<!doctype html>
<html>
<head>
	<title>Cuentas contables</title>
	<meta name="layout" content="luxor">
</head>
<body>

<content tag="header">
	 Catálogo de cuentas contables
</content>
	
<content tag="subHeader">
	
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
	        		<lx:printButton/>
	        		<lx:createButton/>
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
	            				<th>Clave</th>
	            				<th>Descripción</th>
	            				<th>Tipo</th>
	            				<th>Sub Tipo</th>
	            				<th>Naturaleza</th>
	            				<th>SAT</th>
	            			</tr>
	            		</thead>
	            		<tbody>
	            			<g:each in="${cuentaContableInstanceList}" var="row">
	            				<tr>
	            					<td><g:link action="show" id="${row.id}">
	            						${fieldValue(bean: row, field: "clave")}
	            						</g:link>
	            					</td>
	            					<td><g:link action="show" id="${row.id}">
	            						${fieldValue(bean: row, field: "descripcion")}
	            						</g:link>
	            					</td>
	            					
	            					<td>${fieldValue(bean: row, field: "tipo")}</td>
	            					<td>${fieldValue(bean: row, field: "subTipo")}</td>
	            					<td>${fieldValue(bean: row, field: "naturaleza")}</td>
	            					<td>${fieldValue(bean: row, field: "cuentaSat.codigo")}</td>
	            				</tr>
	            			</g:each>
	            		</tbody>
	            	</table>
	            	
	            </div>
	        </div>
	    </div>
	</div>
	
	
	<script type="text/javascript">
		$(function(){
 			$('#grid').dataTable({
                responsive: true,
                aLengthMenu: [[50, 100, 150, 200, -1], [50, 100, 150, 200, "Todos"]],
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



