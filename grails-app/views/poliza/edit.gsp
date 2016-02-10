<%@ page import="com.luxsoft.impapx.contabilidad.Poliza" %>
<%@ page import="com.luxsoft.impapx.contabilidad.PolizaDet" %>
<%@ page import="com.luxsoft.impapx.contabilidad.CuentaContable" %>
<!doctype html>
<html>
<head>
	<title>Mantenimiento de póliza</title>
	<meta name="layout" content="luxor">
	<asset:javascript src="forms/forms.js"/>
</head>
<body>

<content tag="header">
	Poliza ${polizaInstance.subTipo } : ${polizaInstance.folio}     Concepto:${polizaInstance.descripcion} (${polizaInstance.fecha.text()})
	 						
</content>
	
<content tag="subHeader">
	Ejercicio : ${polizaInstance.ejercicio}
	Mes : ${polizaInstance.mes}
	Debe:  <lx:moneyFormat number="${polizaInstance.debe}"/> 
	Haber :  <lx:moneyFormat number="${polizaInstance.haber}"/>
	Cuadre :  <lx:moneyFormat number="${polizaInstance.cuadre}"/>
</content>

<content tag="document">

	<div class="row">
		
	    <div class="col-lg-12">
	        <div class="ibox float-e-margins">
	        	
	        	<div class="ibox-title">
	        		
	        		<div class="btn-group">

	        			<g:link  action="${action}" class="btn btn-default btn-outline" 
	        				params="[subTipo:polizaInstance.subTipo]" >
	        			    <i class="fa fa-step-backward"></i> Polizas
	        			</g:link> 
	        		    
						<g:link class="btn btn-default btn-outline" action="print" id="${polizaInstance.id}">
							<span class="fa fa-print"> Imprimir</span>
						</g:link>

	     				
	    				<g:if test="${polizaInstance.manual}">
	    					<a href="#createDialog" data-toggle="modal"
	    						class="btn btn-primary btn-outline">
	    						<i class="fa fa-plus "></i> Agregar partida
	    					</a>
	    				</g:if>
	    				<g:else>
	    					
	    					<a href="#recalcularDialog" data-toggle="modal"
	    						class="btn btn-primary btn-outline">
	    						<i class="fa fa-cogs "></i> Recalcular
	    					</a>
	    				</g:else>

	    				<a href="#modificarDialog" data-toggle="modal"
	    					class="btn btn-default btn-outline">
	    					<i class="fa fa-pencil-square-o"></i> Modificar
	    				</a>
	        		    <g:if test="${!polizaInstance.cierre}">

	        		    	<a class="btn btn-danger " data-toggle="modal" data-target="#deleteDialog">
	        		    		<i class="fa fa-trash"></i> Eliminar
	        		    	</a> 
	        		    </g:if>
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
	            		class="table  table-hover table-bordered table-condensed " >
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
	            				<th>E</th>
	            				
	            				
	            			</tr>
	            		</thead>
	            		<tbody>
	            			<g:each in="${polizaInstance.partidas}" var="row">
	            				<tr id="${row.id}">
	            					<td>
	            						<g:if test="${polizaInstance.manual}">
	            							<g:link controller="poliza" action="editPartida" id="${row.id}">
	            								${fieldValue(bean: row, field: "cuenta.clave")}
	            							</g:link>
	            						</g:if>
	            						<g:else>
	            							${fieldValue(bean: row, field: "cuenta.clave")}
	            						</g:else>
	            						
	            					</td>
	            					<td>${fieldValue(bean: row, field: "cuenta.descripcion")}</td>
	            					<td><g:formatNumber number="${row.debe}" format="########.##"/></td>
	            					<td><g:formatNumber number="${row.haber}" format="########.##"/></td>
	            					<td>${fieldValue(bean: row, field: "descripcion")}</td>
	            					<td>${fieldValue(bean: row, field: "referencia")}</td>
	            					<td>${fieldValue(bean: row, field: "asiento")}</td>
	            					<td>${fieldValue(bean: row, field: "entidad")}</td>
	            					<td><g:formatNumber number="${row.origen}" format="########"/></td>
	            					<td>
	            						<g:if test="${polizaInstance.manual}">
	            							<g:link action="eliminarPartida" id="${row.id}"
	            								onclick="return confirm('Eliminar partida?');">
	            								<i class="fa fa-trash"></i>
	            							</g:link>
	            							
	            						</g:if>
	            					</td>
	            				</tr>
	            			</g:each>
	            		</tbody>
	            		%{-- <tfoot>
	            			<tr>
	            				<th></th>
	            				<th></th>
	            				<th>Debe</th>
	            				<th>Haber</th>
	            				<th></th>
	            				<th></th>
	            				<th></th>
	            				<th></th>
	            				<th></th>
	            				<th></th>
	            			</tr>
	            		</tfoot> --}%
	            	</table>
	            </div>
	        </div>
	    </div>
	</div>
	
	<div class="modal fade" id="createDialog" tabindex="-1">
		<div class="modal-dialog ">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Detalle de póliza</h4>
				</div>
				<g:form action="agregarPartida" class="form-horizontal" id="${polizaInstance.id}">
					<div id="createPanel" class="modal-body">
						<f:with bean="${new PolizaDet() }">
							<f:field property="cuenta"  >
								<g:hiddenField id="cuentaId" name="cuenta.id" />
								<input type="text" id="cuentaField" class="form-control" >
							</f:field>
							
							<f:field property="debe"  widget="money"/>
							<f:field property="haber"  widget="money"/>
							<f:field property="asiento" widget-class="form-control"/>
							<f:field property="descripcion" widget-class="form-control"/>
							<f:field property="referencia" widget-class="form-control"/>
						</f:with>
						
					</div>
					
					<div class="modal-footer">
						
						<g:submitButton class="btn btn-info" name="aceptar"
								value="Aceptar" />
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
					</div>
				</g:form>
	
			</div>
			<!-- moda-content -->
		</div>
		<!-- modal-di -->
	</div>
	

	<div class="modal fade" id="modificarDialog" >
		<div class="modal-dialog ">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Modificar poliza ${polizaInstance.folio}</h4>
				</div>
				<g:form action="update" class="form-horizontal" id="${polizaInstance.id}" method="PUT">
					<g:hiddenField name="version" value="${polizaInstance.version}"/>

					<div id="createPanel" class="modal-body">
						<f:with bean="${polizaInstance}">
							<f:field property="descripcion" widget-class="form-control" label="Concepto"/>
							<f:field property="manual" widget-class="form-control"/>
						</f:with>
						
					</div>
					
					<div class="modal-footer">
						
						<g:submitButton class="btn btn-info" name="aceptar"
								value="Aceptar" />
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
					</div>
				</g:form>
	
			</div>
			<!-- moda-content -->
		</div>
		<!-- modal-di -->
	</div>

	<div class="modal fade" id="recalcularDialog" >
		<div class="modal-dialog ">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Modificar poliza ${polizaInstance.folio}</h4>
				</div>
				<g:form action="update" class="form-horizontal" id="${polizaInstance.id}" method="PUT">
					<div id="createPanel" class="modal-body">
							<p>Recalcular poliza</p>
					</div>
					
					<div class="modal-footer">
						
						<g:submitButton class="btn btn-info" name="aceptar"
								value="Aceptar" />
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
					</div>
				</g:form>
	
			</div>
			<!-- moda-content -->
		</div>
		<!-- modal-di -->
	</div>

	<g:render template="/common/deleteDialog" bean="${polizaInstance}"/>
	

	<script type="text/javascript">
		$(function(){
			$(".money").autoNumeric({vMin:'-999999999.00',wEmpty:'zero',mRound:'B'});
			$("#cuentaField").autocomplete({
				appendTo: "#createPanel",
				source:'<g:createLink controller="cuentaContable" action="getCuentasDeDetalleJSON"/>',
				minLength:1,
				select:function(e,ui){
					$("#cuentaId").val(ui.item.id);
				}
			});
 			$('#grid').dataTable({
                responsive: true,
                aLengthMenu: [[200, 250, -1], [ 200, 250, "Todos"]],
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
		         		
		         		var d1=parseFloat(aaData[ aiDisplay[i] ][2]);
		         		var d2=parseFloat(aaData[ aiDisplay[i] ][3]);
		         		debe+=d1;
		         		haber+=d2;
		         		
		         		
		         	}
         	
		         	cuadre=debe-haber
		         	$('#totalDebe').autoNumeric('set',debe);
		         	$('#totalHaber').autoNumeric('set',haber);
		         	$('#totalCuadre').autoNumeric('set',cuadre);
         		}
            });
			
			var selectRows=function(){
				var res=[];
				var data=$(".simpleGrid .selected").each(function(){
					var tr=$(this);
					res.push(tr.attr("id"));
				});
				return res;
			};

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
						polizaId:${polizaInstance.id},partidas:JSON.stringify(res)
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
		
	</script>
	
</content>
	
</body>
</html>




	
	
 