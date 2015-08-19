<%@ page import="com.luxsoft.impapx.contabilidad.Poliza" %>
<%@ page import="com.luxsoft.impapx.contabilidad.PolizaDet" %>
<!doctype html>
<html>
<head>
	<title>Mantenimiento de póliza</title>
	<meta name="layout" content="luxor">
	<g:set var="periodo" value="${session.periodoContable}"/>
	<asset:javascript src="forms/forms.js"/>
</head>
<body>

<content tag="header">
	Poliza ${poliza.tipo } : ${poliza.folio}   Fecha:${poliza.fecha.text()}  Descripción:${poliza.descripcion} 
	 						
</content>
	
<content tag="subHeader">
	Debe:  <lx:moneyFormat number="${poliza.debe}"/> 
	Haber :  <lx:moneyFormat number="${poliza.haber}"/>
	Cuadre :  <lx:moneyFormat number="${poliza.cuadre}"/>
</content>

<content tag="document">

	<div class="row">
		
	    <div class="col-lg-12">
	        <div class="ibox float-e-margins">
	        	
	        	<div class="ibox-title">
	        		 <button data-target="#periodoDialog" data-toggle="modal" class="btn btn-outline btn-success  dim">
	        		 	<i class="fa fa-calendar"></i> 
	        		</button>
	        		<lx:refreshButton/>
	        		<div class="btn-group">
	        		    <button type="button" name="reportes"
	        		            class="btn btn-info dropdown-toggle" data-toggle="dropdown"
	        		            role="menu">
	        		            Reportes <span class="caret"></span>
	        		    </button>
	        		    <ul class="dropdown-menu">
	        		    	<li>
	     		 				<g:jasperReport
	     		 						jasper="PolizaContable" 
	     		 						format="PDF,HTML" 
	     		 						name="Imprimir">
	     								<g:hiddenField name="ID" value="${poliza.id}"/>
	     						</g:jasperReport>
	        		    	</li>
	        		    </ul>
	        		</div>
	        		<div class="btn-group">
	        		    <button type="button" name="operaciones"
	        		            class="btn btn-default dropdown-toggle" data-toggle="dropdown"
	        		            role="menu">
	        		            Operaciones <span class="caret"></span>
	        		    </button>
	        		    <ul class="dropdown-menu">
	    	 		 		<li>
	    	 		 			<a href="#createDialog" data-toggle="modal"><i class="icon-plus "></i>Agregar</a>
	    	 				</li>
	    	 		 		<li>
	    	 		 			<a id="eliminarBtn" href="#createDialog" ><i class="icon-trash "></i>Elimiar</a>
	    	 				</li>
	        		    </ul>
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
				<g:form action="agregarPartida" class="form-horizontal" >
					<div class="modal-body">
						<f:with bean="${new PolizaDet() }">
							<f:field property="cuenta" input-required="true"/>
							<f:field property="debe" input-required="true"/>
							<f:field property="haber" input-required="true"/>
							<f:field property="asiento" input-required="true"/>
							<f:field property="descripcion" input-required="true"/>
							<f:field property="referencia" input-required="true"/>
						</f:with>
						
					</div>
					
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
						<g:submitButton class="btn btn-info" name="aceptar"
								value="Buscar" />
					</div>
				</g:form>
	
			</div>
			<!-- moda-content -->
		</div>
		<!-- modal-di -->
	</div>
	
	<script type="text/javascript">
		$(function(){
			$(".money").autoNumeric({vMin:'-999999999.00',wEmpty:'zero',mRound:'B'});
 			$('#grid').dataTable({
                responsive: true,
                aLengthMenu: [[100, 150, 200, 250, -1], [100, 150, 200, 250, "Todos"]],
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
		
	</script>
	
</content>
	
</body>
</html>




	
	
 