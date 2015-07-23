<%@ page import="com.luxsoft.impapx.tesoreria.CompraDeMoneda" %>



<div class="fieldcontain ${hasErrors(bean: compraDeMonedaInstance, field: 'formaDePago', 'error')} required">
	<label for="formaDePago">
		<g:message code="compraDeMoneda.formaDePago.label" default="Forma De Pago" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="formaDePago" required="" value="${compraDeMonedaInstance?.formaDePago}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: compraDeMonedaInstance, field: 'tipoDeCambioCompra', 'error')} required">
	<label for="tipoDeCambioCompra">
		<g:message code="compraDeMoneda.tipoDeCambioCompra.label" default="Tipo De Cambio Compra" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="tipoDeCambioCompra" value="${fieldValue(bean: compraDeMonedaInstance, field: 'tipoDeCambioCompra')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: compraDeMonedaInstance, field: 'tipoDeCambio', 'error')} required">
	<label for="tipoDeCambio">
		<g:message code="compraDeMoneda.tipoDeCambio.label" default="Tipo De Cambio" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="tipoDeCambio" value="${fieldValue(bean: compraDeMonedaInstance, field: 'tipoDeCambio')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: compraDeMonedaInstance, field: 'diferenciaCambiaria', 'error')} required">
	<label for="diferenciaCambiaria">
		<g:message code="compraDeMoneda.diferenciaCambiaria.label" default="Diferencia Cambiaria" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="diferenciaCambiaria" value="${fieldValue(bean: compraDeMonedaInstance, field: 'diferenciaCambiaria')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: compraDeMonedaInstance, field: 'cuentaDestino', 'error')} required">
	<label for="cuentaDestino">
		<g:message code="compraDeMoneda.cuentaDestino.label" default="Cuenta Destino" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="cuentaDestino" name="cuentaDestino.id" from="${com.luxsoft.impapx.CuentaBancaria.list()}" optionKey="id" required="" value="${compraDeMonedaInstance?.cuentaDestino?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: compraDeMonedaInstance, field: 'cuentaOrigen', 'error')} required">
	<label for="cuentaOrigen">
		<g:message code="compraDeMoneda.cuentaOrigen.label" default="Cuenta Origen" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="cuentaOrigen" name="cuentaOrigen.id" from="${com.luxsoft.impapx.CuentaBancaria.list()}" optionKey="id" required="" value="${compraDeMonedaInstance?.cuentaOrigen?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: compraDeMonedaInstance, field: 'fecha', 'error')} required">
	<label for="fecha">
		<g:message code="compraDeMoneda.fecha.label" default="Fecha" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fecha" precision="day"  value="${compraDeMonedaInstance?.fecha}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: compraDeMonedaInstance, field: 'moneda', 'error')} required">
	<label for="moneda">
		<g:message code="compraDeMoneda.moneda.label" default="Moneda" />
		<span class="required-indicator">*</span>
	</label>
	<g:currencySelect name="moneda" value="${compraDeMonedaInstance?.moneda}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: compraDeMonedaInstance, field: 'requisicion', 'error')} required">
	<label for="requisicion">
		<g:message code="compraDeMoneda.requisicion.label" default="Requisicion" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="requisicion" name="requisicion.id" from="${com.luxsoft.impapx.Requisicion.list()}" optionKey="id" required="" value="${compraDeMonedaInstance?.requisicion?.id}" class="many-to-one"/>
</div>

