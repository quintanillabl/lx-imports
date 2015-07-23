<%@ page import="com.luxsoft.impapx.contabilidad.SaldoPorCuentaContable" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="documentView">


<title>Auxiliar contable (Balanza)</title>

<r:require modules="dataTables,luxorForms,luxorTableUtils"/>

</head>
<body>

	<content tag="header">
		<div class="accordion" id="saldoDeCuentaAccordion">
 			<div class="accordion-group">
 				<div class="accordion-heading">
 					
 					<a class="accordion-toggle alert" data-toggle="collapse" 
 						data-parent="#saldoDeCuentaAccordion" href="#collapseOne">
 						Auxiliar contable : ${saldo.mes} - ${saldo.year }
 					</a>
 					<h4><g:link action="subcuentas" id="${saldoPadre.id}">Cuenta: ${saldoPadre.cuenta} </g:link></h4>
 					<%-- <h4>Cuenta: ${saldoPadre?.cuenta} </h4>--%>
 					<h4>Sub Cuenta: ${saldo.cuenta} </h4>
 					
 				</div>
 				<div id="collapseOne" class="accordion-body collapse ">
 					<div class="accordion-inner ">
					<dl>
						<dt>Saldo inicial:<lx:moneyFormat number="${saldo.saldoInicial }"/></dt>
						<dt>Debe:<lx:moneyFormat number="${saldo.debe }"/></dt>
						<dt>Haber:<lx:moneyFormat number="${saldo.haber }"/></dt>
						<dt>Saldo final:<lx:moneyFormat number="${saldo.saldoFinal }"/></dt>
					</dl>
					<g:jasperReport
						controller="saldoPorCuentaContable"
 						action="imprimirAuxiliarContable" 
 						jasper="AuxiliarContable" 
 						format="PDF,HTML,XLS" 
 						name="Imprimir auxiliar">
						<g:hiddenField name="id" value="${saldo.id}"/>
					</g:jasperReport>
 					</div>
 				</div>
 			</div>
 		</div>
		
	</content>
	
 	<content tag="document">	
 		<g:if test="${flash.message}">
			<bootstrap:alert class="alert-info">
				${flash.message}
			</bootstrap:alert>
		</g:if>
		<form class="form-inline">
  			<fieldset>
    		<label>Debe</label>
    		<input id="totalDebe"  type="text" class="input-medium moneyField" readOnly="true">
    		<label>Haber</label>
    		<input id="totalHaber" type="text" class="input-medium moneyField" readOnly="true">
    		<label>Cuadre</label>
    		<input id="totalCuadre" type="text" class="input-medium moneyField" readOnly="true">
    		<a href="#asignarCuentaDialog" data-toggle="modal" class="btn">Reclasificar</a>
  			</fieldset>
		</form>	
		<table id="grid" 
			class="table table-striped table-hover table-bordered table-condensed simpleGrid">
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
		
		<div class="modal hide fade" id="asignarCuentaDialog" tabindex=-1 role="dialog" 
			aria-labelledby="myModalLabel">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="myModalLabel">Reclasificación de cuenta</h4>
			</div>
			<div class="modal-body">
				<g:hiddenField id="cuentaId" name="cuenta.id"  />
				<input id="cuenta" type="text" name="cuenta" 
					value="" placeholder="Seleccione la cuenta destino" 
					class="input-xxlarge"
					required="true"/>
			</div>
			<div class="modal-footer">
				<a href="#" class="btn" data-dismiss="modal">Cancelar</a>
    			<button id="asignarCuenta" class="btn btn-primary" onclick="reclasificarCuenta()">Aplicar</button>
			</div>
		</div>
		
	</content>
<r:script>
$(function(){
	
	$(".moneyField").autoNumeric({vMin:'-999999999.00',wEmpty:'zero',mRound:'B'});
	
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

	$("#cuenta").autocomplete({
			source:'<g:createLink controller="cuentaContable" action="cuentasDeDetalleJSONList"/>',
			minLength:3,
			
			select:function(e,ui){
				//console.log('Valor seleccionado: '+ui.item.id);
				$("#cuentaId").val(ui.item.id);
			}
	});
	
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
</r:script>			
</body>
</html>



