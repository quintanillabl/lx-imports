<%@ page import="com.luxsoft.impapx.FacturaDeImportacion" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="luxor">
	<title>Programación de pagos</title>
</head>
<body>

<content tag="header">
	Programación de pagos Periodo:${session.periodoParaPagos}
</content>

<content tag="document">
	<div class="ibox float-e-margins">
		<div class="ibox-title">
			<div class="row">
				<div class="col-md-3">
				    <div class="input-group">
			            <span class="input-group-btn">
			                <button data-target="#periodoDialog" data-toggle="modal" class="btn  btn-outline btn-success ">
			            		<i class="fa fa-calendar"></i> 
			        		</button>
			            </span>
				        <input type='text' id="filtro" placeholder="Filtrar" class="form-control" autofocus="on">
				    </div>
				    
				</div>
				<div class="col-md-4">
				    <g:form class="form-horizontal" action="programacionDePagos">
				        <g:hiddenField id ="proveedorId" name="proveedor" />
				        <div class="input-group">
				            <input id="searchField" name="searchDesc" type="text" 
				                class="form-control " placeholder="Seleccionar proveedor">
				            <span class="input-group-btn">
				                <button id="buscarBtn" type="submit" class="btn btn-default" disabled="disabled">
				                    <i class="fa fa-search"></i></span>
				                </button> 
				            </span>
				        </div>
				    </g:form>
				</div>  
				<div class="btn-group">
				    <lx:printButton/>
				</div>
			</div>
			
		</div>
	    <div class="ibox-content">
			<table id="grid"
				class="display table table-hover table-bordered table-condensed">
				<thead>
					<tr>
						<th>Id</th>
						<th>Proveedor</th>
						<th>Dcto</th>
						<th>Fecha</th>
						<th>BL</th>
						<th>Vto</th>
						<th>Moneda</th>
						<th>Total</th>
						<th>Pagos</th>
						<th>Saldo</th>
						%{-- <th class="header">Creada</th>
						<th class="header">Modificada</th> --}%
					</tr>
				</thead>
				<tfoot>
		            <tr>
		                <th colspan="7" style="text-align:right">Totales:</th>
		                %{-- <th></th>
		                <th></th>
		                <th></th>
		                <th></th>
		                <th></th> --}%
		                
		                <th></th>
		                <th></th>
		                <th></th>
		            </tr>
				</tfoot>
				<tbody>
					<g:each in="${facturaDeImportacionInstanceList}" var="row">
						<tr>
							<td>
								<g:link action="show" id="${row.id}">
									<lx:idFormat id="${row.id}"/>
								</g:link></td>
							<td><g:link action="show" id="${row.id}">
									${fieldValue(bean: row, field: "proveedor.nombre")}
								</g:link>
							</td>
							<td>${fieldValue(bean: row, field: "documento")}</td>
							<td><lx:shortDate date="${row.fecha }"/></td>
							<td><lx:shortDate date="${row.fechaBL }"/></td>
							<td><lx:shortDate date="${row.vencimiento }"/></td>
							<td>
								${fieldValue(bean: row, field: "moneda")}
							</td>
							<td><lx:moneyFormat number="${row.total}"/></td>
							<td><lx:moneyFormat number="${row.pagosAplicados}"/></td>
							<td><lx:moneyFormat number="${row.saldoActual}"/></td>
							
							%{-- <td><abbr title="${g.formatDate(date:row.dateCreated)}">
								...</abbr></td>
							<td><abbr title="${g.formatDate(date:row.lastUpdated)}">
								...</abbr></td> --}%
						</tr>
					</g:each>
				</tbody>
			</table>
	    </div>
	</div>
	<g:render template="selectorDePeriodoDePagos"/>
	<script type="text/javascript">
	    $(function(){
	        $('#grid').DataTable({
	            responsive: true,
	            aLengthMenu: [[20, 40, 60, 100, -1], [20, 40,60, 100, "Todos"]],
	            "language": {
	                "url": "${assetPath(src: 'datatables/dataTables.spanish.txt')}"
	            },
	            //"dom": 'T<"clear">lfrtip',
	            "tableTools": {
	                "sSwfPath": "${assetPath(src: 'plugins/dataTables/swf/copy_csv_xls_pdf.swf')}"
	            },
	            "order": [],
	            "footerCallback":function(row,data,start,end,display){
	            	
	            	var api = this.api(), data;
	            	// Remove the formatting to get integer data for summation
    	            var intVal = function ( i ) {
    	                return typeof i === 'string' ?
    	                    i.replace(/[\$,]/g, '')*1 :
    	                    typeof i === 'number' ?
    	                        i : 0;
    	            };
    	            // Total over all pages
                    total = api
                        .column( 9 )
                        .data()
                        .reduce( function (a, b) {
                            return intVal(a) + intVal(b);
                        } );

                    saldo = api
                        .column( 9 )
                        .data()
                        .reduce( function (a, b) {
                            return intVal(a) + intVal(b);
                        } );

                    // Total over this page
                    pageTotal = api
                        .column( 9, { page: 'current'} )
                        .data()
                        .reduce( function (a, b) {
                            return intVal(a) + intVal(b);
                        }, 0 );

                    pageSaldo = api
                        .column( 9, { page: 'current'} )
                        .data()
                        .reduce( function (a, b) {
                            return intVal(a) + intVal(b);
                        }, 0 );

                    // Update footer
                    $( api.column( 7 ).footer() ).html(
                        '$'+pageTotal.round(2)
                    );
                    $( api.column( 9 ).footer() ).html(
                        '$'+pageSaldo.round(2)
                    );

                    console.log("Total: "+total);

	            }
	        });
	        var table=$("#grid").DataTable();
	        


	        $("#filtro").on('keyup',function(e){
	            var term=$(this).val();
	            $('#grid').DataTable().search(
	                $(this).val()
	                    
	            ).draw();
	        });

	        $('.date').bootstrapDP({
	            format: 'dd/mm/yyyy',
	            keyboardNavigation: false,
	            forceParse: false,
	            autoclose: true,
	            todayHighlight: true,

	        });

	        $("#searchField").autocomplete({
	            source:'<g:createLink controller="proveedor" action="proveedoresJSONList"/>',
	            minLength:1,
	            select:function(e,ui){
	                $("#searchField").val(ui.item.id);
	                $("#proveedorId").val(ui.item.id);
	                var button=$("#buscarBtn");
	                button.removeAttr('disabled');
	            }
	        });

	    });
	</script>  
</content>
	
</body>
</html>
