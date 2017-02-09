<%@ page import="com.luxsoft.impapx.contabilidad.Poliza" %>
<!doctype html>
<html>
<head>
	<title>Poliza ${folio}</title>
	<meta name="layout" content="luxor">
	<title>Póliza ${poliza.tipo}</title>
	<g:set var="periodo" value="${session.periodoContable}"/>
	<asset:javascript src="forms/forms.js"/>
</head>
<body>

<content tag="header">
	Poliza ${poliza.tipo}: ${poliza.folio}  ${poliza.fecha.text()} ${poliza.descripcion} 
</content>
	
<content tag="subHeader">
	
	Debe:  <lx:moneyFormat number="${poliza.debe}"/> 
	Haber :  <lx:moneyFormat number="${poliza.haber}"/>
	Cuadre :  <lx:moneyFormat number="${poliza.cuadre}"/>

</content>

<content tag="document">
	<div class="row">
	    <div class="col-lg-12">
	        <div class="ibox float-e-margins">
	        	
	        	<div class="ibox-title">
	        		 <button data-target="#periodoDialog" data-toggle="modal" class="btn btn-outline btn-success  dim">
	        		 	<i class="fa fa-calendar"></i> 
	        		</button>
	        		<lx:backButton label="Polizas"/>
	        		<div class="btn-group">
	        		    <button type="button" name="operaciones"
	        		            class="btn btn-info dropdown-toggle" data-toggle="dropdown"
	        		            role="menu">
	        		            Reportes <span class="caret"></span>
	        		    </button>
	        		    <ul class="dropdown-menu">
	        		    	<li>
	     		 				<g:jasperReport
	     		 						jasper="PolizaContable" 
	     		 						format="PDF,HTML,XLS" 
	     		 						name="Imprimir">
	     								<g:hiddenField name="ID" value="${poliza.id}"/>
	     						</g:jasperReport>
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
        			<form class="form-inline">
        				<div class="form-group">
        				    <label for="totalDebe">Debe</label>
        				    <input id="totalDebe"  type="text" class="money" readOnly="true">
        				</div>
        	  			<div class="form-group">
        				    <label for="totalHaber">Haber</label>
        				    <input id="totalHaber"  type="text" class="money" readOnly="true">
        				</div>
        	    		<div class="form-group">
        				    <label for="totalCuadre">Cuadre</label>
        				    <input id="totalCuadre"  type="text" class="money" readOnly="true">
        				</div>
        			</form>	
	            	<table id="grid"
	            		class="table table-striped table-hover table-bordered table-condensed">
	            		<thead>
	            			<tr>
	            				<th>Cuenta</th>
	            				<th>Concepto</th>
	            				<th>Debe</th>
	            				<th>Haber</th>
	            				<th>Descripcion</th>
	            				<th>Referencia</th>
	            				<th>Asiento</th>
	            				<th>Entidad</th>
	            				<th>Origen</th>
	            				
	            				
	            			</tr>
	            		</thead>
	            		<tbody>
	            			<g:each in="${partidas}" var="row">
	            				<tr>
	            					<td>${fieldValue(bean: row, field: "cuenta.clave")}</td>
	            					<td>${fieldValue(bean: row, field: "cuenta.descripcion")}</td>
	            					<td><g:formatNumber number="${row.debe}" format="########.##"/></td>
	            					<td><g:formatNumber number="${row.haber}" format="########.##"/></td>
	            					<td>${fieldValue(bean: row, field: "descripcion")}</td>
	            					<td>${fieldValue(bean: row, field: "referencia")}</td>
	            					<td>${fieldValue(bean: row, field: "asiento")}</td>
	            					<td>${fieldValue(bean: row, field: "entidad")}</td>
	            					<td><g:formatNumber number="${row.origen}" format="########"/></td>
	            				</tr>
	            			</g:each>
	            		</tbody>
	            		<tfoot>
	            			<tr>
	            				<th>Cuenta</th>
	            				<th>Concepto</th>
	            				<th>Debe</th>
	            				<th>Haber</th>
	            				<th>Descripcion</th>
	            				<th>Referencia</th>
	            				<th>Asiento</th>
	            				<th>Entidad</th>
	            				<th>Origen</th>
	            			</tr>
	            		</tfoot>
	            	</table>
	            </div>
	        </div>
	    </div>
	</div>
	
	
	<script type="text/javascript">
		$(function(){
			$(".money").autoNumeric({vMin:'-999999999.00',wEmpty:'zero',mRound:'B'});
 			$('#grid').dataTable({
                responsive: true,
                aLengthMenu: [200, 250, -1], [200, 250, "Todos"]],
                "language": {
					"url": "${assetPath(src: 'datatables/dataTables.spanish.txt')}"
	    		},
	    		"dom": 'T<"clear">lfrtip',
	    		"tableTools": {
	    		    "sSwfPath": "${assetPath(src: 'plugins/dataTables/swf/copy_csv_xls_pdf.swf')}"
	    		},
	    		"order": [],
	    		"fnFooterCallback": function ( nRow, aaData, iStart, iEnd, aiDisplay ) {
		         	var debe=0;
		         	var haber=0;
		         	var cuadre=0;
		         	for(var i=iStart;i<iEnd;i++){
		         		
		         		var d1=parseFloat(aaData[ aiDisplay[i] ][2]);
		         		var d2=parseFloat(aaData[ aiDisplay[i] ][3]);
		         		debe+=d1;
		         		haber+=d2;
		         	}
		         	console.log('Debe: '+debe);
		         	console.log('Haber: '+haber);
		         	cuadre=debe-haber
		         	$('#totalDebe').autoNumeric('set',debe);
		         	$('#totalHaber').autoNumeric('set',haber);
		         	$('#totalCuadre').autoNumeric('set',cuadre);
         		}
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


