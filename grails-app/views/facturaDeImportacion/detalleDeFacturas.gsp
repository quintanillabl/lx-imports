<%@ page import="com.luxsoft.impapx.FacturasPorPeriodoCommand" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="taskView">

<title><g:message code="polizasDeDiario.list.label" default="Pólizas de diario"/></title>

<r:require modules="dataTables,luxorForms"/>
</head>
<body>

	<content tag="header">
		<h4>Detalle de Facturas de importación</h4>
		<g:if test="${facturasCommand?.proveedor}">
			<div class="alert">
				<p><h3>Proveedor: ${facturasCommand.proveedor }</h3>
				<p><h4>Periodo: ${facturasCommand.toString() }</h4>
			</div>
		</g:if>
	</content>
	
	
 	
 	<content tag="consultas">
 		
 	</content>
 	
	
 	<content tag="operaciones">
 		<li>
 			<a href="#createDialog" data-toggle="modal"><i class="icon-plus "></i>Ejecutar</a>
		</li>
		<li>
		 	<g:jasperReport
 				controller="facturaDeImportacion"
 				action="reporteDeFacturaDetImportacion" 
 				jasper="FacturasDetXProveedor" 
 				format="PDF,HTML,XLS" 
 				name="Factura por Proveedor">			
			</g:jasperReport>
		</li>
 		
 	</content>
 	
 	<content tag="document">	
 		<g:if test="${flash.message}">
			<bootstrap:alert class="alert-info">
				${flash.message}
			</bootstrap:alert>
		</g:if>
		
		<table id="grid"
			class="table table-striped table-hover table-bordered table-condensed">
			<thead>
				<tr>
					<th>Factura</th>
					<th>Clave</th>
					<th>Producto</th>
					
				</tr>
				<tr>
					
					<th>Factura</th>
					<th>Clave</th>
					<th>Producto</th>
					<th>G</th>
					<th>KxMIL</th>
					<th>K Net</th>
					<th>Prec. Ton</th>
					<th>Valor fac</th>
				</tr>
			</thead>
			<tbody>
				<g:each in="${embarques}" var="row">
					<tr>
						<td>${fieldValue(bean: row, field: "factura.documento")}</td>
						<td>${fieldValue(bean: row, field: "producto.clave")}</td>
						<td>${fieldValue(bean: row, field: "producto.descripcion")}</td>
						<td>${fieldValue(bean: row, field: "producto.gramos")}</td>
						<td>${fieldValue(bean: row, field: "producto.kilos")}</td>
						<td>${fieldValue(bean: row, field: "kilosNetos")}</td>
						<td><lx:moneyFormat number="${row.precio}"/></td>
						<td><lx:moneyFormat number="${row.costoBruto}"/></td>
						
					</tr>
				</g:each>
			</tbody>
			<tfoot>
				<tr>
					<th>Factura</th>
					<th>Clave</th>
					<th>Producto</th>
					<th>G</th>
					<th>KxMIL</th>
					<th>K Net</th>
					<th>Prec. Ton</th>
					<th>Valor fac</th>
				</tr>
			</tfoot>
		</table>
		
		
		<div  id="createDialog" class="modal hide fade modal-large" role="dialog" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h4>Parámetros</h4>
		</div>
	
		<div class="modal-body">
		
			<fieldset>
				<g:form class="form-horizontal" action="detalleDeFacturas" >
						<fieldset>
						
							<f:with bean="${facturasCommand}">
								<f:field property="fechaInicial">
									<g:field type="string" class="dateField"  name="fechaInicial" value="${facturasCommand?.fechaInicial.text() }"/>
								</f:field>
								<f:field property="fechaFinal">
									<g:field type="string" class="dateField"  name="fechaFinal" value="${facturasCommand?.fechaFinal.text() }"/>
								</f:field>
								<f:field property="proveedor">
									<g:hiddenField id="proveedorId" 
										name="proveedor.id" value="${facturasCommand?.proveedor?.id }"/>
 									<g:field type="string" id="proveedorAuto"  name="proveedor" 
 										value="${facturasCommand?.proveedor}" class="input-xxlarge"/>
								</f:field>
							</f:with>
							<div class="form-actions">
								<button type="submit" class="btn btn-primary">
									<i class="icon-ok icon-white"></i>
									Ejecutar
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
	$("#grid").dataTable({
		//aLengthMenu: [[100, 150, 200, 250, -1], [100, 150, 200, 250, "Todos"]],
        //iDisplayLength: 50,
        //"oLanguage": {
      		//"sUrl":"<g:resource dir="js" file="dataTables.spanish.txt" />"
	    //},
         "bPaginate": false  
	}).columnFilter({sPlaceHolder: "head:before",
					aoColumns: [{ type: "text" },
				    	 		{ type: "text" },
                                { type: "text" }
                                ,null
                                ,null
                                ]

		});
	
	
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
			}
	});
	
});
</r:script>			
</body>
</html>



