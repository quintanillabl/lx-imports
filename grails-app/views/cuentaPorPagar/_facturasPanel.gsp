<div id="facturasPanel">
	<g:hasErrors bean="${cuentaPorPagarInstance}">
		<bootstrap:alert class="alert-error">
			<ul>
				<g:eachError bean="${cuentaPorPagarInstance}" var="error">
					<li
						<g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
							error="${error}" />
					</li>
				</g:eachError>
			</ul>
		</bootstrap:alert>
	</g:hasErrors>

	<table id="grid"
		class="display table table-striped table-hover table-bordered table-condensed">
		<thead>
			<tr>
				<g:sortableColumn property="id" title="Id" />
				<g:sortableColumn property="proveedor.nombre" title="Proveedor" />

				<th class="header">Dcto</th>
				<th class="header">Fecha</th>
				<th class="header">Vto</th>
				<th class="header">Moneda</th>
				<th class="header">Total</th>
				<th class="header">Pagos</th>
				<th class="header">Saldo</th>
				<th class="header">Creada</th>
				<th class="header">Modificada</th>
			</tr>
		</thead>
		<tbody>
			<g:each in="${facturasList}" var="row">
				<tr>
					<td><g:link action="edit" id="${row.id}">
							${row.id}
						</g:link></td>
					<td><g:link action="edit" id="${row.id}">
							${fieldValue(bean: row, field: "proveedor.nombre")}
						</g:link>
					</td>
					<td>${fieldValue(bean: row, field: "documento")}</td>
					<td><lx:shortDate date="${row.fecha }"/></td>
					<td><lx:shortDate date="${row.vencimiento }"/></td>
					<td>
						${fieldValue(bean: row, field: "moneda")}
					</td>
					<td><lx:moneyFormat number="${row.total}"/></td>
					<td><lx:moneyFormat number="${row.pagosAplicados}"/></td>
					<td><lx:moneyFormat number="${row.saldoActual}"/></td>
					
					<td><abbr title="${g.formatDate(date:row.dateCreated)}">
						...</abbr></td>
					<td><abbr title="${g.formatDate(date:row.lastUpdated)}">
						...</abbr></td>
				</tr>
			</g:each>
		</tbody>
	</table>
	
</div>

<r:script>
		$(function(){
			$("#grid").dataTable({
			//"sDom": "<'row'<'span3'l><'span9'f>r>t<'row'<'span6'i><'span6'p>>",
			//"sDom": "<'row'<'span4'f>r>t<'row'<'span6'i><'span6'p>>",
			aLengthMenu: [[50, 150, 200, 250, -1], [50, 150, 200, 250, "Todos"]],
          	iDisplayLength: 50,
          	 	"oLanguage": {
      			"sUrl":"<g:resource dir="js" file="dataTables.spanish.txt" />"
	    	},
    		"aoColumnDefs": [
                        { "sType": "numeric","bSortable": true,"aTargets":[0] }
                    ],
           "bPaginate": true  
			});
		});
		$("tbody tr").hover(function(){
			$(this).toggleClass("info");
		});
		$("tbody tr").click(function(){
			$(this).toggleClass("success selected");
		});
	</r:script>