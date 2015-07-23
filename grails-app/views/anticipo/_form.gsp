<%@ page import="com.luxsoft.impapx.cxp.Anticipo" %>



<div class="fieldcontain ${hasErrors(bean: anticipoInstance, field: 'comentario', 'error')} ">
	<label for="comentario">
		<g:message code="anticipo.comentario.label" default="Comentario" />
		
	</label>
	<g:textField name="comentario" value="${anticipoInstance?.comentario}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: anticipoInstance, field: 'complemento', 'error')} ">
	<label for="complemento">
		<g:message code="anticipo.complemento.label" default="Complemento" />
		
	</label>
	<g:select id="complemento" name="complemento.id" from="${com.luxsoft.impapx.Requisicion.list()}" optionKey="id" value="${anticipoInstance?.complemento?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: anticipoInstance, field: 'embarque', 'error')} required">
	<label for="embarque">
		<g:message code="anticipo.embarque.label" default="Embarque" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="embarque" name="embarque.id" from="${com.luxsoft.impapx.Embarque.list()}" optionKey="id" required="" value="${anticipoInstance?.embarque?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: anticipoInstance, field: 'gastosDeImportacion', 'error')} required">
	<label for="gastosDeImportacion">
		<g:message code="anticipo.gastosDeImportacion.label" default="Gastos De Importacion" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="gastosDeImportacion" value="${fieldValue(bean: anticipoInstance, field: 'gastosDeImportacion')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: anticipoInstance, field: 'impuestosAduanales', 'error')} required">
	<label for="impuestosAduanales">
		<g:message code="anticipo.impuestosAduanales.label" default="Impuestos Aduanales" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="impuestosAduanales" value="${fieldValue(bean: anticipoInstance, field: 'impuestosAduanales')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: anticipoInstance, field: 'requisicion', 'error')} required">
	<label for="requisicion">
		<g:message code="anticipo.requisicion.label" default="Requisicion" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="requisicion" name="requisicion.id" from="${com.luxsoft.impapx.Requisicion.list()}" optionKey="id" required="" value="${anticipoInstance?.requisicion?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: anticipoInstance, field: 'sobrante', 'error')} required">
	<label for="sobrante">
		<g:message code="anticipo.sobrante.label" default="Sobrante" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="sobrante" name="sobrante.id" from="${com.luxsoft.impapx.tesoreria.MovimientoDeCuenta.list()}" optionKey="id" required="" value="${anticipoInstance?.sobrante?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: anticipoInstance, field: 'total', 'error')} required">
	<label for="total">
		<g:message code="anticipo.total.label" default="Total" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="total" value="${fieldValue(bean: anticipoInstance, field: 'total')}" required=""/>
</div>

