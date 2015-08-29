<div class="btn-group">
	<g:if test="${facturaDeGastosInstance.requisitado<=0.0}">
		<div class="btn-group toolbar-panel">
			<a id="addBtn" href="#createConceptoDialog" class="btn btn-default" data-toggle="modal">
				<i class="fa fa-plus"></i> Agregar concepto
			</a>
			
			<button id="deleteBtn" class="btn btn-danger " >
					<i class="fa fa-trash"></i> Eliminar concepto
			</button>
		</div>
	</g:if>
</div>
<table id="grid" class=" simpleGrid table  table-hover table-bordered table-condensed">
	<thead>
		<tr>
			<th class="header">Id</th>
			<th class="header">Cuenta</th>
			<th class="header">Descripcion</th>
			<th class="header">Tipo</th>
			<th class="header">Importe</th>
			<th class="header">Impuesto</th>
			<th class="header">Ret IVA</th>
			<th class="header">Ret(%)</th>
			<th class="header">Ret ISR</th>
			<th class="header">ISR(%)</th>
			<th class="header">Total</th>
		</tr>
	</thead>
	<tbody>
		<g:each in="${facturaDeGastosInstance.conceptos}" var="row">
			<tr id="${row.id}">
				<td><g:link controller="conceptoDeGasto" action="show" id="${row.id}">
			    	${row.id}</g:link>
			    </td>
			    <td>
			    	<a data-toggle="modal" data-target="#conceptoDialog" data-gasto="${row.id}">
			    		${fieldValue(bean: row, field: "concepto.clave")}
			    	</a>
			    	%{-- <g:link controller="conceptoDeGasto" action="show" id="${row.id}">
			    		
			    	</g:link> --}%
			    </td>
				<td>${fieldValue(bean: row, field: "concepto.descripcion")}</td>
				<td>${fieldValue(bean: row, field: "tipo")}</td>
				<td><lx:moneyFormat number="${row.importe }" /></td>
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


<script type="text/javascript">
	$(function(){
		
		$("tbody tr").on('click',function(){
			$(this).toggleClass("success selected");
		});

		var getSelectedRows=function(){
			var res=[];
			var data=$(".simpleGrid .selected").each(function(){
				var tr=$(this);
				res.push(tr.attr("id"));
			});
			return res;
		};

		//Eliminar conceptos

		function eliminar(){
			var res=getSelectedRows();

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
					window.location.reload(true);
				},
				error:function(request,status,error){
					alert("Error: "+status);
				}
			});
		};

		$("#deleteBtn").on('click',function(e){
			eliminar();
		});

		/*
		$('#conceptoDialog').on('show.bs.modal', function (event) {
		  var button = $(event.relatedTarget) // Button that triggered the modal

		  var gasto = button.data('gasto'); // Extract info from data-* attributes
		  // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
		  // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
		  var modal = $(this);
		  modal.find('.modal-title').text('Concepto de gasto:' + gasto);
		  $.ajax({
		  	url:"${createLink(action:'consultarGasto')}",
		  	data:{
		  		id:gasto
		  	},
		  	success:function(response){
		  		console.log('Aceesado.....'+response);
		  		$("#conceptoDetailForm").text(response);
		  		modal.find('.modal-body').html(response);
		  	},
		  	error:function(request,status,error){
		  		alert("Error: "+status);
		  	}
		 });
		*/
	});

</script>