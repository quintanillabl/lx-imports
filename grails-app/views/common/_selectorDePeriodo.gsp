<%@page expressionCodec="none"%>
<div class="modal fade" id="periodoDialog" tabindex="-1">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">Selección de periodo </h4>
			</div>

			<g:form class="form-horizontal" action="${operacion?:'cambiarPeriodo'}" controller="${controller?:'home'}" >
				
				<div class="modal-body">

					<div class="form-group" id="data_1">
                        <label class="font-noraml">Fecha Inicial</label>
                        <div class="input-group date">
                            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                            <input type="text" class="form-control" name="fechaInicial"
                            	value="${formatDate(date:session.periodo.fechaInicial,format:'dd/MM/yyyy')}">
                        </div>
					</div>

					<div class="form-group" id="data_2">
                        <label class="font-noraml">Fecha Final</label>
                        <div class="input-group date">
                            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                            <input type="text" class="form-control" name="fechaFinal"
                            	value="${formatDate(date:session.periodo.fechaFinal,format:'dd/MM/yyyy')}">
                        </div>
					</div>

					
				</div>	
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
					<g:submitButton class="btn btn-primary" name="aceptar" value="Aceptar" />
				</div>
			</g:form>

		</div>
		<!-- moda-content -->
	</div>
	<!-- modal-di -->

</div>

<script type="text/javascript">
	$(document).ready(function(){
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

