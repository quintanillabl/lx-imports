<%@ page import="com.luxsoft.impapx.TipoDeCambio" %>



<div class="fieldcontain ${hasErrors(bean: tipoDeCambioInstance, field: 'fecha', 'error')} required">
	<label for="fecha">
		<g:message code="tipoDeCambio.fecha.label" default="Fecha" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fecha" precision="day"  value="${tipoDeCambioInstance?.fecha}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: tipoDeCambioInstance, field: 'monedaOrigen', 'error')} required">
	<label for="monedaOrigen">
		<g:message code="tipoDeCambio.monedaOrigen.label" default="Moneda Origen" />
		<span class="required-indicator">*</span>
	</label>
	<g:currencySelect name="monedaOrigen" value="${tipoDeCambioInstance?.monedaOrigen}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: tipoDeCambioInstance, field: 'monedaFuente', 'error')} required">
	<label for="monedaFuente">
		<g:message code="tipoDeCambio.monedaFuente.label" default="Moneda Fuente" />
		<span class="required-indicator">*</span>
	</label>
	<g:currencySelect name="monedaFuente" value="${tipoDeCambioInstance?.monedaFuente}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: tipoDeCambioInstance, field: 'factor', 'error')} required">
	<label for="factor">
		<g:message code="tipoDeCambio.factor.label" default="Factor" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="factor" value="${fieldValue(bean: tipoDeCambioInstance, field: 'factor')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: tipoDeCambioInstance, field: 'fuente', 'error')} ">
	<label for="fuente">
		<g:message code="tipoDeCambio.fuente.label" default="Fuente" />
		
	</label>
	<g:textField name="fuente" maxlength="200" value="${tipoDeCambioInstance?.fuente}"/>
</div>

