<div class="accordion-group">
 	<div class="accordion-heading">
 		<a class="accordion-toggle alert" data-toggle="collapse" data-parent="#saldoDeCuentaAccordion" href="#collapseOne">
 			Periodo contable
 		</a>
 	</div>
 	<div id="collapseOne" class="accordion-body collapse ">
 		<div class="accordion-inner ">
 			<g:form  action="actualizarPeriodo">
 				<fieldset>
 					<label>Periodo</label>
 					<g:field type="string" id="periodoContable"  name="currentDate" value="${session.periodoContable.currentDate.text()}"/>
 				</fieldset>
 				<div class="form-actions">
 					<button type="submit" class="btn btn-primary">
						<i class="icon-ok icon-white"></i>
						Actualizar
					</button>
 				</div>
 			</g:form>
 		</div>
 	</div>
 </div>
 
 <r:script>
 $(function(){
 	
 	$("#periodoContable").datepicker({
    	 dateFormat: 'dd/mm/yy',
         showOtherMonths: true,
         selectOtherMonths: true,
         showOn:'focus',
         showAnim:'fold',
         minDate:'01/01/2012',
         maxDate:'31/12/2015',
         navigationAsDateFormat:false,
         showButtonPanel:false,
         changeMonth:true,
         changeYear:true,
         closeText:'Cerrar'
      });
 });
 </r:script>
 