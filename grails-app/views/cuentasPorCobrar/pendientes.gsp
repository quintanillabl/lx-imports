 <!doctype html>
 <html>
 	<head>
 		<meta name="layout" content="taskView"/>
 		<title>Cuentas por cobrar (CxC)</title>
 	</head>
 	<body>
 		<content tag="header">
			<h3>Facturas pendientes </h3>
 		</content>
		<content tag="consultas">
 			<g:render template="consultas"/>
 		</content>
 		<content tag="operaciones">
 			<%--<li><g:link  action="depositar">Depositar</g:link></li>
 			<li><g:link  action="retirar">Retirar</g:link></li> --%>
 		</content>
 		
 		<content tag="document">
 			<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
			</g:if>
			<table id="ventasGrid" class="simpleGrid table table-striped table-hover table-bordered table-condensed">
					<thead>
						<tr>
							<g:sortableColumn property="id" title="${message(code: 'venta.fecha.label', default: 'Folio')}" />
							<g:sortableColumn property="cliente" title="${message(code: 'venta.fecha.label', default: 'Cliente')}" />
							<g:sortableColumn property="fecha" title="${message(code: 'venta.fecha.label', default: 'Fecha')}" />
							<g:sortableColumn property="importe" title="${message(code: 'venta.importe.label', default: 'Importe')}" />
							<g:sortableColumn property="impuestos" title="${message(code: 'venta.impuestos.label', default: 'Impuestos')}" />
							<g:sortableColumn property="descuentos" title="${message(code: 'venta.descuentos.label', default: 'Descuentos')}" />
							<g:sortableColumn property="subtotal" title="${message(code: 'venta.subtotal.label', default: 'Subtotal')}" />
							<g:sortableColumn property="total" title="${message(code: 'venta.subtotal.label', default: 'Total')}" />
							<td>Factura</td>
							<g:sortableColumn property="dateCreated" title="Creada" />
							<g:sortableColumn property="lastUpdated" title="Modificada" />
						</tr>
					</thead>
					<tbody>
						<g:each in="${ventaInstanceList}" var="ventaInstance">
							<tr class="${ventaInstance.cfd?'warning':''}">
								<td>
									<g:link action="show" id="${ventaInstance.id}">${fieldValue(bean: ventaInstance, field: "id")}
									</g:link>
								</td>
								<td>${fieldValue(bean: ventaInstance, field: "cliente")}</td>
								<td><lx:shortDate date="${ventaInstance.fecha}" /></td>
								<td>${fieldValue(bean: ventaInstance, field: "importe")}</td>
								<td>${fieldValue(bean: ventaInstance, field: "impuestos")}</td>
								<td>${fieldValue(bean: ventaInstance, field: "descuentos")}</td>
								<td>${fieldValue(bean: ventaInstance, field: "subtotal")}</td>
								<td>${fieldValue(bean: ventaInstance, field: "total")}</td>
								<td>
									<g:link action="showFactura" id="${ventaInstance?.cfd?.id}">
										${fieldValue(bean: ventaInstance, field: "cfd.folio")}
									</g:link>
								</td>
								<td><g:formatDate date="${ventaInstance.dateCreated}"/></td> 
								<td><g:formatDate date="${ventaInstance.lastUpdated}"/></td>
							</tr>
						</g:each>
					</tbody>
				</table>
				<div class="pagination">
					<bootstrap:paginate total="${ventaInstanceTotal}" />
				</div>
		</content>
 		
 	</body>
 	
 </html>
 