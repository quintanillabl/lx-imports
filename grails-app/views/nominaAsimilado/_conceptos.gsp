	%{-- 
	String clave
	String descripcion
	String claveSat
	BigDecimal importeGravado=0.00
	BigDecimal importeExcento=0.00 --}%

<%@page expressionCodec="none" %>
<table class="table  table-bordered table-condensed">
	<thead>
		<tr>
			<th>Clave</th>
			<th>SAT</th>
			<th>Descripción</th>
			<th>Gravado</th>
			<th>Excento</th>
		</tr>
	</thead>
	<tbody>
		
		<g:findAll in="${it?.partidas}" expr="it.tipo==param" >
			
			<tr>
				<td>${fieldValue(bean:it,field:"clave")}</td>
				<td>${fieldValue(bean:it,field:"claveSat")}</td>
				<td>${fieldValue(bean:it,field:"descripcion")}</td>
				<td class="text-right"><g:formatNumber number="${it.importeGravado}" format="#,###,###.##" minFractionDigits="2"/></td>
				<td class="text-right"><g:formatNumber number="${it.importeExcento}" format="#,###,###.##" minFractionDigits="2"/></td>
			</tr>

		</g:findAll>
	</tbody>
	<tfoot>
		<tr>
			<th></th>
			<th></th>
			<th>Total</th>
			<th class="text-right"><g:formatNumber 
				number="${param=='PERCEPCION'? it.nominaAsimilado.percepciones:it.nominaAsimilado.deducciones}" format="#,###.##"/></th>
			<th class="text-right"><g:formatNumber 
				number="${param=='PERCEPCION'? it.nominaAsimilado.percepciones:it.nominaAsimilado.deducciones}" format="#,###.##"/></th>
			
		</tr>
	</tfoot>
</table>

<div class="modal fade" id="conceptoInfoDialog" tabindex="-1" >
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">Detalle de concepto</h4>
			</div>
	
			<div class="modal-body">
				<div id="modalTarget">
					
				</div>
				
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
			</div>
	
				
		</div> <!-- moda-content -->
	</div> <!-- modal-dialog -->
</div> <!-- .modal  -->


<script type="text/javascript">
	$(document).ready(function(){

		$("a[data-toggle='modal']").on('click', function(e) {
		    $_clObj = $(this); //clicked object
		    $_mdlObj = $_clObj.attr('data-target'); //modal element id 
		    $($_mdlObj).on('shown.bs.modal',{ _clObj: $_clObj }, function (event) {
		           $_clObj = event.data._clObj; //$_clObj is the clicked element !!!
		           //do your stuff here...
		           var url = $_clObj.data('url'); // Extract info from data-* attributes
		           //console.log(url);
		           var modal = $(this);
					modal.find('#modalTarget').text('Cargando datos...');
					$.ajax({
						type:'GET',
						url:url,
						dataType:'html',
						success:function(data){
							modal.find('#modalTarget').html(data);
							// element.attr('data-content',data);
							// element.attr('data-popover-visible',"true");
							// element.popover('show');
						}
					});

		    });
		});
		
		var get_data_for_popover=function(){
			var element=$(this);
			var url=$(this).attr('data-url');
			console.log('URL: ' + url);
			return "DEMO DATA"
			
		}
	});
	
</script>


