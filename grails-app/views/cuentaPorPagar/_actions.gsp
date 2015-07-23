
<sec:ifAnyGranted roles="COMPRAS,ADMIN">
<li><g:link class="list" controller="facturaDeImportacion" action="list">
		<!-- <i class="icon-list"></i> -->
		<g:message code="facturaDeImportacion.list.label" default="Importaciones"/>
	</g:link>
</li>
</sec:ifAnyGranted>

<sec:ifAnyGranted roles="COMPRAS,ADMIN">
<li><g:link class="list" controller="gastosDeImportacion" action="list">
		<!-- <i class="icon-list"></i> -->
		<g:message code="gastosDeImportacion.list.label" default="Gastos de importación"/>
	</g:link>
</li>
</sec:ifAnyGranted>

<sec:ifAnyGranted roles="COMPRAS,ADMIN,TESORERIA">
<li><g:link class="list" controller="facturaDeGastos" action="list">
		<!-- <i class="icon-list"></i> -->
		<g:message code="facturaDeGastos.list.label" default="Otros gastos"/>
	</g:link>
</li>
</sec:ifAnyGranted>

<sec:ifAnyGranted roles="COMPRAS,ADMIN,TESORERIA">
<li><g:link class="list" controller="requisicion" action="list">
		<!-- <i class="icon-list"></i> -->
		<g:message code="requisicion.list.label" default="Requisiciones"/>
	</g:link>
</li>
</sec:ifAnyGranted>

<sec:ifAnyGranted roles="COMPRAS,ADMIN">
<li><g:link  controller="pago" action="list">
		<!-- <i class="icon-list"></i> -->
		<g:message code="pago.list.label" default="Pagos"/>
	</g:link>
</li>
</sec:ifAnyGranted>

<sec:ifAnyGranted roles="COMPRAS,ADMIN">
<li><g:link class="list" controller="notaDeCredito" action="list">
		<!-- <i class="icon-list"></i> -->
		<g:message code="notaDeCredito.list.label" default="Notas de Crédito"/>
	</g:link>
</li>
</sec:ifAnyGranted>

<sec:ifAnyGranted roles="COMPRAS,ADMIN">
<li><g:link class="list" controller="facturaDeImportacion" action="detalleDeFacturas">
		Detalle de Facturas
	</g:link>
</li>
</sec:ifAnyGranted>
<%-- 
<li><g:link class="list" action="anticipos" controller="requisicion">
		<!-- <i class="icon-list"></i> -->
		<g:message code="anticipos.list.label" default="Anticipos"/>
	</g:link>
</li>
--%>
<sec:ifAnyGranted roles="COMPRAS,ADMIN,TESORERIA">
<li><g:link class="list" controller="cuentaDeGastosGenerica" action="list">
		<!-- <i class="icon-list"></i> -->
		<g:message code="cuentaDeGastosGenerica.list.label" default="Cuenta de Gastos (Genérica)"/>
	</g:link>
</li>
</sec:ifAnyGranted>

