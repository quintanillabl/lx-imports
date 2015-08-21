<%@ page import="com.luxsoft.impapx.Requisicion" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="operaciones">
	<title>Requisiciones</title>
</head>
<body>
<content tag="header">
	Requisiciones
</content>
<content tag="subHeader">
	<ol class="breadcrumb">
	    <li >
	        <g:link action="index" params="[tipo:'PENDIENTES']">

	        	<g:if test="${tipo=='PENDIENTES'}">
	        		<strong>Pendientes</strong>
	        	</g:if>
	        	<g:else>
	        		Pendientes
	        	</g:else>
	        </g:link>
	    </li>
	    <li>
	        <g:link action="index" params="[tipo:'PAGADAS']">
	    		<g:if test="${tipo=='PAGADAS'}">
	    			<strong>Pagadas</strong>
	    		</g:if>
	    		<g:else>
	    			Pagadas
	    		</g:else>
	    	</g:link>
	    </li>
	    <li >
	    	<g:link action="index" params="[tipo:'TODAS']">
	    		<g:if test="${tipo=='TODAS'}">
	    			<strong>Todas</strong>
	    		</g:if>
	    		<g:else>
	    			Todas
	    		</g:else>
	    	</g:link>
	    </li>
	</ol>
</content>

<content tag="periodo">
	Periodo:${session.periodo.mothLabel()} 
</content>
<content tag="grid">
	<table id="grid" class="table table-striped table-bordered table-hover ">
		<thead>
			<tr>
				<th>Folio</th>
				<th>Proveedor</th>
				<th>Concepto</th>
				<th>Fecha</th>
				<th>Pago</th>
				<th>M</th>
				<th>TC</th>
				<th>Total</th>
				<th>Pago</th>
				<th>Modificado</th>
				
			</tr>
		</thead>
		<tbody>
			<g:each in="${requisicionInstanceList}" var="row">
				<tr>
					<lx:idTableRow id="${row.id}"/>
					<td>${fieldValue(bean: row, field: "proveedor.nombre")}</td>
					<td>${fieldValue(bean: row, field: "concepto")}</td>
					<td><lx:shortDate date="${row.fecha }"/></td>
					<td><lx:shortDate date="${row.fechaDelPago }"/></td>
					<td>${fieldValue(bean: row, field: "moneda")}</td>
					<td>${formatNumber(number:row.tc,format:'##.#####')}</td>
					<td><lx:moneyFormat number="${row.total}"/></td>
					<lx:idTableRow controller="pagoProveedor" id="${row?.pagoProveedor?.id}"/>
					<td><abbr title="${g.formatDate(date:row.lastUpdated)}">...</abbr></td>
				</tr>
			</g:each>
		</tbody>
		
		%{-- <tfoot>
			<tr>
				<th>Folio</th>
				<th>Proveedor</th>
				<th>Concepto</th>
				<th>Fecha</th>
				<th>Pago</th>
				<th>M</th>
				<th>TC</th>
				<th>Total</th>
				<th>Pago</th>
				<th>Modificado</th>
				<th></th>
			</tr>
		</tfoot> --}%
	</table>
</content>
<content tag="searchService">
	<g:createLink action="search"/>
</content>
 	<div class="row wrapper border-bottom white-bg page-heading">
        <div class="col-lg-10">
            <h2>Requisiciones</h2>
            
        </div>
        <div class="col-lg-2">

        </div>
 	</div>
 	<div class="wrapper wrapper-content animated fadeInRight">
 		<div class="row">
 			<div class="col-lg-12">
 				<div class="ibox float-e-margins">
 					<div class="ibox-title">
                        
                        <div class="btn-group">
							<g:link action="create" class="btn  btn-success"  >
							    <i class="fa fa-plus"></i> Nueva
							</g:link> 
							%{-- <filterpane:filterButton text="Filtrar" /> --}%
						</div>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                            <a id="reload">
                            	<i class="fa fa-refresh"></i>
                            </a>
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                                <i class="fa fa-wrench"></i>
                            </a>
                            <ul class="dropdown-menu dropdown-user">
                                <li><a href="#"> Filtrar </a>
                                </li>
                                <li>
                                	<g:link action="create" >
                                	    <i class="fa fa-plus"></i> Nueva
                                	</g:link> 
                                </li>
                                
                                
                            </ul>
                            <a class="close-link">
                                <i class="fa fa-times"></i>
                            </a>
                        </div>
 					</div>
 					<div class="ibox-content">
					    %{-- <div class="btn-group">
				        	<lx:refreshButton/>
				            <lx:printButton/>
				            <filterpane:filterButton text="Filtrar" />
					    </div> --}%
					    <div class="table-responsive">
						

 						</div>
 					</div>
 				</div>

 				
 			</div>
 		</div>
 	</div>
 	<script type="text/javascript">
 		// $(function(){
 		// 	$('#grid').dataTable({
   //              responsive: true,
   //              "dom": 'T<"clear">lfrtip',
   //              "tableTools": {
   //                  "sSwfPath": "${assetPath(src: 'plugins/dataTables/swf/copy_csv_xls_pdf.swf')}"
   //              },
   //              "language": {
			// 		"url": "${assetPath(src: 'datatables/dataTables.spanish.txt')}"
	  //   		},
	  //   		"ajax":{
	  //   			"url":"${createLink(controller:'requisicionRest', action:'index')}",
	  //   			"type":"GET",
	  //   			"dataSrc": ""
	  //   		},
	  //   		"columns": [
		 //            { "data": "id" },
		 //            { "data": "proveedor" },
		 //            { "data": "concepto" },
		 //            { "data": "fecha" },
		 //            { "data": "fechaDelPago" },
		 //            { "data": "moneda" },
		 //            { "data": "tc" },
		 //            { "data": "total" },
		 //            { "data": "pago" },
		 //            { "data": "lastUpdated" },
		 //            { "targets": -1,
		 //            	"data": null,
		 //            	"orderable":false,
		 //            	"render":function ( data, type, full, meta ) {
		 //            			var ref="${createLink(action:'show')}";
		 //            			ref=ref+'/'+data.id
   //    							return '<a href="'+ref+'"><i class="fa fa-info"></i></a>';
   //  					}
		 //            }
	  //   		],
	  //   		"columnDefs":[{
	  //   			"targets": 0,
	  //   			"orderable":false
	  //   		}],
	  //   		"order": []
	    		
	  //   		//"processing": true,
   //      		//"serverSide": true
   //          });
            
   //          var table=$("#grid").DataTable();

   //          $("#reload").on('click',function(){
   //          	console.log('Cargando el grid...');
   //          	table.ajax.reload();
   //          });

 		// });
 	</script>
</body>
</html>
