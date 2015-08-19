<%@ page import="com.luxsoft.impapx.contabilidad.SaldoPorCuentaContable" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="luxor">
	<title>Auxiliar contable (Balanza)</title>
	<asset:javascript src="forms/forms.js"/>
	%{-- <r:require modules="dataTables,luxorForms,luxorTableUtils"/> --}%
</head>
<body>

	<content tag="header">
		Cuenta: ${saldoPadre.cuenta} 
	</content>

	<content tag="subHeader">
		<ol class="breadcrumb">
	    	<li><g:link action="index">Saldos</g:link></li>
	    	<li><g:link action="subcuentas" id="${saldoPadre.id}">Cuenta: ${saldoPadre.cuenta} </g:link></li>
	    	<li class="active"><strong>Auxiliar : ${saldo.mes} - ${saldo.year }</strong></li>
		</ol>
	</content>

	<content tag="document">
		<dl>
			<dt>Saldo inicial:<lx:moneyFormat number="${saldo.saldoInicial }"/></dt>
			<dt>Debe:<lx:moneyFormat number="${saldo.debe }"/></dt>
			<dt>Haber:<lx:moneyFormat number="${saldo.haber }"/></dt>
			<dt>Saldo final:<lx:moneyFormat number="${saldo.saldoFinal }"/></dt>
		</dl>
		
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
     							<g:jasperReport
     								controller="saldoPorCuentaContable"
     		 						action="imprimirAuxiliarContable" 
     		 						jasper="AuxiliarContable" 
     		 						format="PDF" 
     		 						name="Imprimir auxiliar">
     								<g:hiddenField name="id" value="${saldo.id}"/>
     							</g:jasperReport>
	        		    	</li>
	        		    </ul>
	        		</div>
	        		<div class="btn-group">
	        		    <button type="button" name="operaciones"
	        		            class="btn btn-default dropdown-toggle" data-toggle="dropdown"
	        		            role="menu">
	        		            Operaciones <span class="caret"></span>
	        		    </button>
	        		    <ul class="dropdown-menu">
    		    	 		<li>
    		    	 			<a href="#asignarCuentaDialog" data-toggle="modal" class="btn">Reclasificar</a>
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
        				class="table table-striped table-hover table-bordered table-condensed ">
        				<thead>
        					<tr>
        						<td>Poliza</td>
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
        						<tr id="${row.id}">
        							<td>
        							<g:link controller="poliza" action="mostrarPoliza" target="_blank" id="${row.poliza.id}">
        								${row.poliza.tipo }- ${row.poliza.folio  }
        							</g:link>
        							</td>
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
        						<th>Poliza</th>
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
		
		<div class="modal fade" id="asignarCuentaDialog" tabindex="-1">
			<div class="modal-dialog ">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">Reclasificación de cuentas</h4>
					</div>
					<div class="modal-body ui-front">
						<div class="row">
							<form>
								<g:hiddenField id="cuentaId" name="cuenta.id"  />
								<div class="form-group">
									<label for="cuenta" class="control-label col-sm-2">Cuenta</label>
									<div class="col-sm-10">
										<input id="cuenta" type="text" class="form-control" value="" required/>
									</div>
								</div>
							</form>
						</div>
					</div>

					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
						<button id="asignarCuenta" class="btn btn-primary" >Aplicar</button>
					</div>
		
				</div>
				<!-- moda-content -->
			</div>
			<!-- modal-di -->
		</div>

	<script type="text/javascript">
		$(function(){
			$(".money").autoNumeric({vMin:'-999999999.00',wEmpty:'zero',mRound:'B'});
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
	    		"order": [],
	    		"fnFooterCallback": function ( nRow, aaData, iStart, iEnd, aiDisplay ) {
		         	var debe=0;
		         	var haber=0;
		         	var cuadre=0;
		         	for(var i=iStart;i<iEnd;i++){
		         		
		         		var d1=parseFloat(aaData[ aiDisplay[i] ][3]);
		         		var d2=parseFloat(aaData[ aiDisplay[i] ][4]);
		         		debe+=d1;
		         		haber+=d2;
		         		
		         		
		         	}
         	
		         	cuadre=debe-haber
		         	$('#totalDebe').autoNumeric('set',debe);
		         	$('#totalHaber').autoNumeric('set',haber);
		         	$('#totalCuadre').autoNumeric('set',cuadre);
         		}
            });

			$("#cuenta").autocomplete({
					source:'<g:createLink controller="cuentaContable" action="cuentasDeDetalleJSONList"/>',
					minLength:3,
					select:function(e,ui){
						//console.log('Valor seleccionado: '+ui.item.id);
						$("#cuentaId").val(ui.item.id);
					}
			});
			
		});
	</script>

	</content>

	
	
 	
<script>
$(function(){
	
	
	
	$("#grid").dataTable({
		aLengthMenu: [[100, 150, 200, 250, -1], [100, 150, 200, 250, "Todos"]]
        ,iDisplayLength: 50
        ,"oLanguage": {
      		"sUrl":"<g:resource dir="js" file="dataTables.spanish.txt" />"
	    }
    	,"aoColumnDefs": [
        	{ "sType": "numeric","bSortable": true,"sWidth": "10%","aTargets":[2,3]}
        	
         ]
         ,"bPaginate": false
         ,bJQueryUI: true  
         ,sPaginationType: "full_numbers"
         ,"fnFooterCallback": function ( nRow, aaData, iStart, iEnd, aiDisplay ) {
         	var debe=0;
         	var haber=0;
         	var cuadre=0;
         	for(var i=iStart;i<iEnd;i++){
         		
         		var d1=parseFloat(aaData[ aiDisplay[i] ][3]);
         		var d2=parseFloat(aaData[ aiDisplay[i] ][4]);
         		debe+=d1;
         		haber+=d2;
         		
         		
         	}
         	//console.log(debe);
         	//console.log(haber);
         	cuadre=debe-haber
         	$('#totalDebe').autoNumericSet(debe);
         	$('#totalHaber').autoNumericSet(haber);
         	$('#totalCuadre').autoNumericSet(cuadre);
         	//console.log('Total Debe: '+cuadre);
         }
	}).columnFilter();

	
	
});

function reclasificarCuenta(){
		var res=selectedRows();
		if(res.length==0){
			alert('Debe seleccionar al menos un registro');
			return;
		}
		var ok=confirm('Reclasificar  ' + res.length+' partida(s)?');
		if(!ok)
			return;
		console.log('Reclasificando facturas: '+res);
		
		$.ajax({
			url:"${createLink(action:'reclasificarCuenta')}",
			data:{
				saldoId:${saldo.id},partidas:JSON.stringify(res),destinoId:getDestino()
			},
			success:function(response){
				
				location.reload();
			},
			error:function(request,status,error){
				alert("Error: "+status);
			}
		});
}
function selectedRows(){
			var res=[];
			var data=$(".simpleGrid .selected").each(function(){
				var tr=$(this);
				res.push(tr.attr("id"));
			});
			return res;
		}
function getDestino(){
	var res=$("#cuentaId").val();
	return res;
}
</script>

</body>
</html>



