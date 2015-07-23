<table
		class="table table-striped table-hover table-bordered table-condensed">
		<thead>
			<tr>
				<g:sortableColumn property="id" title="${"Id"}" />
				<g:sortableColumn property="cuenta" title="${"Cuenta"}" />
				<th class="header"><g:message code="movimientoDeCuenta.moneda.label" default="Mon" /></th>
				<th class="header"><g:message code="movimientoDeCuenta.tc.label" default="TC" /></th>
				<g:sortableColumn property="fecha" title="${"Fecha"}" />
				<g:sortableColumn property="concepto" title="${"Concepto"}" />
				<g:sortableColumn property="tipo" title="${"Tipo"}" />
				<th class="header"><g:message code="movimientoDeCuenta.importe.label" default="Importe" /></th>
				<th class="header"><g:message code="movimientoDeCuenta.comentario.label" default="Comentario" /></th>
			</tr>
		</thead>
		<tbody>
			<g:each in="${movimientoDeCuentaInstanceList}"
				var="movimientoDeCuentaInstance">
				<tr>
					<td><g:link action="show"
							id="${movimientoDeCuentaInstance.id}">
							${fieldValue(bean: movimientoDeCuentaInstance, field: "id")}
						</g:link></td>
					<td>
						${fieldValue(bean: movimientoDeCuentaInstance, field: "cuenta")}
					</td>
					<td>
						${fieldValue(bean: movimientoDeCuentaInstance, field: "moneda")}
					</td>

					<td>
						${fieldValue(bean: movimientoDeCuentaInstance, field: "tc")}
					</td>

					<td>
						<lx:shortDate date="${movimientoDeCuentaInstance.fecha }"/>
					</td>

					<td>
						${fieldValue(bean: movimientoDeCuentaInstance, field: "concepto")}
					</td>

					<td>
						${fieldValue(bean: movimientoDeCuentaInstance, field: "tipo")}
					</td>
					<td><lx:moneyFormat number="${movimientoDeCuentaInstance.importe }"/></td>
					<td>
						${fieldValue(bean: movimientoDeCuentaInstance, field: "comentario")}
					</td>

				</tr>
			</g:each>
		</tbody>
	</table>