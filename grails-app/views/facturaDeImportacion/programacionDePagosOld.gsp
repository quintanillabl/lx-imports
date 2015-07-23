<%@ page import="com.luxsoft.impapx.FacturaDeImportacion" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="taskView">
<g:set var="entityName"
	value="${message(code: 'facturaDeImportacion.label', default: 'Facturas')}" />
<title><g:message code="cuentasPorPagar.list.label" default="Cuentas por pagar (CxP)" /></title>
<r:require module="dataTables"/>
</head>
<body>

	<content tag="header">
		
		<div class="accordion" id="accordion1">
		
			<div class="accordion-group">
		
 				<div class="accordion-heading">
 					<a class="accordion-toggle alert" data-toggle="collapse" data-parent="#saldoDeCuentaAccordion" href="#collapseOne">
 						Programación de pagos  Periodo: ${periodo} ${periodo?.proveedor }
 						
 					</a>
 				</div>
 				
 				<div id="collapseOne" class="accordion-body collapse ">
 					<div class="accordion-inner ">
 						<g:form class="form-inline" action="programacionDePagos">
 							
 							<label>Fecha Inicial</label>
 							<g:field type="string" class="dateField"  name="fechaInicial" value="${periodo.fechaInicial.text()}"/>
 							<label>Fecha Final</label>
 							<g:field type="string" class="dateField"  name="fechaFinal" value="${periodo.fechaFinal.text() }"/>
 							<label>Proveedor</label>
 							<g:hiddenField id="proveedorId" name="proveedor.id" value="${periodo?.proveedor?.id }"/>
 							<g:field type="string" id="proveedorAuto"  name="proveedor" value="${periodo?.proveedor?.nombre}"/>
 							<label>Por pagar</label>
 							<label><lx:moneyFormat number="${facturaDeImportacionInstanceList.sum{it.saldoActual} }"/></label>
 							<button type="submit" class="btn btn-primary">
								<i class="icon-ok icon-white"></i>
								Aplicar
							</button>
 						
 						</g:form>
					
 					</div>
 				</div>
 			
 		</div>
	
		</div>
	</content>
	
 	<content tag="consultas">
 		<g:jasperReport
 				jasper="ProgramacionDePago" 
 				format="PDF,XML" 
 				name="Imprimir">
				<g:hiddenField name="FECHA_INI" value="${periodo.fechaInicial.format('yyyy/MM/dd')}"/>
				<g:hiddenField name="FECHA_FIN" value="${periodo.fechaFinal.format('yyyy/MM/dd')}"/>
				<g:hiddenField name="PROVEEDOR" value="${periodo?.proveedor?.id?:'%' }"/>
							
		</g:jasperReport>
 	</content>
 	
 	<content tag="operaciones">
 		
		<li class="nav-header">Filtros</li>
		<li>
		</li>
 	</content>
 	<content tag="document">
 		
 		<g:if test="${flash.message}">
			<bootstrap:alert class="alert-info">
				${flash.message}
			</bootstrap:alert>
		</g:if>
		<g:render template="facturasPanel" 
			model="['cuentaPorPagarInstance':facturaDeImportacionInstance
					,'facturasList':facturaDeImportacionInstanceList
					,'cuentaPorPagarInstanceTotal':facturaDeImportacionInstanceTotal]"
					/>
 	</content>

	<r:script>
 $(function(){
 	$.datepicker.setDefaults( $.datepicker.regional[ "es" ] );
 	$(".dateField").datepicker({
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
      
      $("#proveedorAuto").autocomplete({
			source:'<g:createLink controller="proveedor" action="proveedoresJSONList"/>',
			minLength:3,
			select:function(e,ui){
				console.log('Valor seleccionado: '+ui.item.id);
				$("#proveedorId").val(ui.item.id);
			},
			
	});
 });
 </r:script>
	
</body>
</html>
