<%@ page import="com.luxsoft.impapx.contabilidad.Poliza" %>
<%@ page import="com.luxsoft.impapx.contabilidad.PolizaDet" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="polizasView">

<g:set var="periodo" value="${session.periodoContable}"/>
<title>Mantenimiento de póliza</title>

<r:require modules="dataTables,luxorTableUtils,luxorForms"/>
</head>
<body>

	<content tag="header">
		<div class="accordion" id="saldoDeCuentaAccordion">
 			<div class="accordion-group">
 				<div class="accordion-heading">
 					<a class="accordion-toggle alert" data-toggle="collapse" 
 						data-parent="#saldoDeCuentaAccordion" href="#collapseOne">
 						Poliza ${poliza.tipo } : ${poliza.folio}   Fecha:${poliza.fecha.text()}  Descripción:${poliza.descripcion} 
 						Debe:  <lx:moneyFormat number="${poliza.debe}"/> 
 						Haber :  <lx:moneyFormat number="${poliza.haber}"/>
 						Cuadre :  <lx:moneyFormat number="${poliza.cuadre}"/>
 					</a>
 				</div>
 				<div id="collapseOne" class="accordion-body collapse ">
 					<div class="accordion-inner ">
					
					
					
 				</div>
 				
 				<g:jasperReport
 						jasper="PolizaContable" 
 						format="PDF,HTML" 
 						name="Imprimir">
						<g:hiddenField name="ID" value="${poliza.id}"/>
				</g:jasperReport>
				
				
 				
 				</div>
 			</div>
 		</div>
		
	</content>
	
 	<content tag="consultas">
 		<li>
 			<g:link class="" action="list">
				Polizas 
			</g:link>
		</li>
 	</content>
 	
 	<content tag="operaciones">
 		<li>
 			<a href="#createDialog" data-toggle="modal"><i class="icon-plus "></i>Agregar</a>
		</li>
 		<li>
 			<a id="eliminarBtn" href="#createDialog" ><i class="icon-trash "></i>Elimiar</a>
 			
		</li>
			<form>
  			<fieldset>
    		<label>Debe</label>
    		<input id="totalDebe"  type="text" class="input-medium moneyField" readOnly="true">
    		<label>Haber</label>
    		<input id="totalHaber" type="text" class="input-medium moneyField" readOnly="true">
    		<label>Cuadre</label>
    		<input id="totalCuadre" type="text" class="input-medium moneyField" readOnly="true">
  			</fieldset>
		</form>	
 	</content>
 	
 	<content tag="document">	
 		<g:if test="${flash.message}">
			<bootstrap:alert class="alert-info">
				${flash.message}
			</bootstrap:alert>
		</g:if>
		
		<table id="grid"
			class="table table-striped table-hover table-bordered table-condensed simpleGrid" >
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
					<tr id="${row.id}">
						<td>
							<g:link action="editPartida" id="${row.id}">
								${fieldValue(bean: row, field: "cuenta.clave")}
							</g:link>
						</td>
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
		
		<div  id="createDialog" class="modal hide fade" role="dialog" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h4>Detalle de póliza</h4>
		</div>
	
		<div class="modal-body">
		
			<fieldset>
				<g:form class="form-horizontal" action="agregarPartida" id="${poliza.id }" >
						<fieldset>
							<f:with bean="${new PolizaDet() }">
								<f:field property="cuenta" input-required="true"/>
								<f:field property="debe" input-required="true"/>
								<f:field property="haber" input-required="true"/>
								<f:field property="asiento" input-required="true"/>
								<f:field property="descripcion" input-required="true"/>
								<f:field property="referencia" input-required="true"/>
								
							</f:with>
							<div class="form-actions">
								<button type="submit" class="btn btn-primary">
									<i class="icon-ok icon-white"></i>
									<g:message code="default.button.create.label" default="Create" />
								</button>
							</div>
						</fieldset>
					</g:form>
				</fieldset>
		
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
         		
         		var d1=parseFloat(aaData[ aiDisplay[i] ][2]);
         		var d2=parseFloat(aaData[ aiDisplay[i] ][3]);
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
	
	
	
	$("#eliminarBtn").click(function(e){
		eliminar();
	});
	
	function eliminar(){
		var res=selectedRows();
		if(res.length==0){
			alert('Debe seleccionar al menos un registro');
			return;
		}
		var ok=confirm('Eliminar  ' + res.length+' partida(s)?');
		if(!ok)
			return;
		console.log('Cancelando facturas: '+res);
		
		$.ajax({
			url:"${createLink(action:'eliminarPartidas')}",
			data:{
				polizaId:${poliza.id},partidas:JSON.stringify(res)
			},
			success:function(response){
				
				location.reload();
			},
			error:function(request,status,error){
				alert("Error: "+status);
			}
		});
	}
	
});

function selectedRows(){
			var res=[];
			var data=$(".simpleGrid .selected").each(function(){
				var tr=$(this);
				res.push(tr.attr("id"));
			});
			return res;
		}
</r:script>			
</body>
</html>



