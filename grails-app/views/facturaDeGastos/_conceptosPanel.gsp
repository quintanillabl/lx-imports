<%@ page import="com.luxsoft.impapx.cxp.ConceptoDeGasto" %>
<r:require modules="luxorTableUtils,luxorForms"/>
<div class="btn-toolbar">
	<div class="btn-group ">
		<button id="agregarBtn" class="btn btn-primary" data-toggle="modal" data-target="#createDialog">
  			<i class="icon-plus icon-white"></i>Agregar
  		</button>
  		<button id="eliminarBtn" class="btn btn-danger">
  			<i class="icon-trash icon-white"></i>Eliminar
  		</button>
	</div>
</div>

<table id="grid"
	class="simpleGrid table table-striped table-hover table-bordered table-condensed">
	<thead>
		<tr>
			<th class="header">Id</th>
			<th class="header">Cuenta</th>
			<th class="header">Descripcion</th>
			<th class="header">Tipo</th>
			<th class="header">Importe</th>
			<th class="header">IETU</th>
			
			<th class="header">Impuesto</th>
			<th class="header">Retensión</th>
			<th class="header">Ret(%)</th>
			<th class="header">ISR</th>
			<th class="header">ISR(%)</th>
			<th class="header">Total</th>
			
		</tr>
	</thead>
	<tbody>
		<g:each in="${conceptos}" var="row">
			<tr id="${row.id}">
				<td><g:link controller="conceptoDeGasto" action="edit" id="${row.id}">
			    	${row.id}</g:link>
			    </td>
			    <td><g:link controller="conceptoDeGasto" action="edit" id="${row.id}">
			    	${fieldValue(bean: row, field: "concepto.clave")}</g:link>
			    </td>
				<td>${fieldValue(bean: row, field: "concepto.descripcion")}</td>
				<td>${fieldValue(bean: row, field: "tipo")}</td>
				<td><lx:moneyFormat number="${row.importe }" /></td>
				<td><lx:moneyFormat number="${row.ietu }" /></td>
				
				<td><lx:moneyFormat number="${row.impuesto }" /></td>
				<td><lx:moneyFormat number="${row.retension }" /></td>
				<td>${fieldValue(bean: row, field: "retensionTasa")}</td>
				<td><lx:moneyFormat number="${row.retensionIsr }" /></td>
				<td>${fieldValue(bean: row, field: "retensionIsrTasa")}</td>
				<td><lx:moneyFormat number="${row.total }" /></td>
				
			</tr>
		</g:each>
	</tbody>
	<tfoot>
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td><label class="pull-right" >Totales: </label></td>
			<td><lx:moneyFormat number="${facturaDeGastosInstance.importe}" /></td>
			<td><lx:moneyFormat number="${facturaDeGastosInstance.conceptos.sum(0.0,{it.ietu})}" /></td>
			<td><lx:moneyFormat number="${facturaDeGastosInstance.impuestos}" /></td>
			<td><lx:moneyFormat number="${facturaDeGastosInstance.retImp}" /></td>
			<td></td>
			<td><lx:moneyFormat number="${facturaDeGastosInstance.retensionIsr}" /></td>
			<td></td>
			<td><lx:moneyFormat number="${facturaDeGastosInstance.total}" /></td>
		</tr>
		
		
	</tfoot>
</table>

<div id="createDialog" class="modal hide fade modal-large" role="dialog" aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		<h4>Alta de concepto</h4>
	</div>
	
	<div class="modal-body">
	
		<fieldset>
			<g:form class="form-horizontal" action="agregarPartida" id="${facturaDeGastosInstance.id }" >
				<fieldset>
					<f:with bean="${new ConceptoDeGasto() }">
						<f:field property="concepto" >
							<g:hiddenField id="conceptoId" name="concepto.id"/>
							<g:field type="text" id="concepto" name="conceptoDesc" required="true" class="input-xxlarge"/>
						</f:field>
						<f:field property="tipo"/>
						<f:field property="descripcion" input-required="true"  input-class="input-xxlarge"/>
						 
						<f:field property="importe" input-required="true" input-id="c_Importe" input-class="moneyField"/>
						<f:field property="ietu" input-required="true" input-id="c_ietu" input-class="moneyField"/>
						<f:field property="impuestoTasa" input-required="true" input-class="tasa"/>
						<f:field property="retensionTasa" input-required="true" input-class="tasa"/>
						<f:field property="retensionIsrTasa" input-required="true" input-class="tasa"/>
						<f:field property="descuento"  input-class="moneyField"/>
						<f:field property="rembolso"  input-class="moneyField" label="Vales"/>
						<f:field property="fechaRembolso"  input-id="fechaRembolso" label="Fecha Vales"/>
						<f:field property="otros"  input-class="moneyField"/>
						<f:field property="comentarioOtros"  />
						
						
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
<r:script>

$(function(){

	$("#fechaRembolso").datepicker({
    	 dateFormat: 'dd/mm/yy',
         showOtherMonths: true,
         selectOtherMonths: true,
         showOn:'focus',
         showAnim:'fold',
         minDate:'01/10/2012',
         maxDate:'31/12/2015',
         navigationAsDateFormat:false,
         showButtonPanel: true,
         changeMonth:true,
         changeYear:true,
         closeText:'Cerrar'
      });
	
	$("#concepto").autocomplete({
			source:'<g:createLink controller="cuentaContable" action="cuentasDeDetalleJSONList"/>',
			minLength:3,
			select:function(e,ui){
				console.log('Valor seleccionado: '+ui.item.id);
				$("#conceptoId").val(ui.item.id);
			}
	});
	
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
			url:"${createLink(action:'eliminarConceptos')}",
			data:{
				facturaId:${facturaDeGastosInstance.id},partidas:JSON.stringify(res)
			},
			success:function(response){
				location.reload();
			},
			error:function(request,status,error){
				alert("Error: "+status);
			}
		});
	}
	
	$(".tasa").autoNumeric({aSign: ' %', pSign: 's', vMin:'0.00',vMax: '100.00',wEmpty:'zero'} );
	$(".moneyField").autoNumeric({vMin:'0.00',wEmpty:'zero',mRound:'B'});
	$('#c_Importe').blur(function(){
		console.log('Importe registrado');
		var importe=$("#c_Importe").autoNumericGet();
		$('#c_ietu').autoNumericSet(importe);
	});
	
	
	
	
});
function selectedRows(){
	var res=[];
	var data=$("tbody tr.selected").each(function(){
		var tr=$(this);
		res.push(tr.attr("id"));
	});
	return res;
}
</r:script>
