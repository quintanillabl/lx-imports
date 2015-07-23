<%@ page import="com.luxsoft.impapx.Compra" %>



<div class="fieldcontain ${hasErrors(bean: compraInstance, field: 'proveedor', 'error')} required">
	<label for="proveedor">
		<g:message code="compra.proveedor.label" default="Proveedor" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="proveedor" name="proveedor.id" from="${com.luxsoft.impapx.Proveedor.list()}" optionKey="id" required="" value="${compraInstance?.proveedor?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: compraInstance, field: 'fecha', 'error')} required">
	<label for="fecha">
		<g:message code="compra.fecha.label" default="Fecha" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fecha" precision="day"  value="${compraInstance?.fecha}"  />

</div>

<div class="fieldcontain ${hasErrors(bean: compraInstance, field: 'entrega', 'error')} ">
	<label for="entrega">
		<g:message code="compra.entrega.label" default="Entrega" />
		
	</label>
	<g:datePicker name="entrega" precision="day"  value="${compraInstance?.entrega}" default="none" noSelection="['': '']" />

</div>

<div class="fieldcontain ${hasErrors(bean: compraInstance, field: 'depuracion', 'error')} ">
	<label for="depuracion">
		<g:message code="compra.depuracion.label" default="Depuracion" />
		
	</label>
	<g:datePicker name="depuracion" precision="day"  value="${compraInstance?.depuracion}" default="none" noSelection="['': '']" />

</div>

<div class="fieldcontain ${hasErrors(bean: compraInstance, field: 'comentario', 'error')} required">
	<label for="comentario">
		<g:message code="compra.comentario.label" default="Comentario" />
		<span class="required-indicator">*</span>
	</label>
	<g:textArea name="comentario" cols="40" rows="5" maxlength="255" required="" value="${compraInstance?.comentario}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: compraInstance, field: 'moneda', 'error')} required">
	<label for="moneda">
		<g:message code="compra.moneda.label" default="Moneda" />
		<span class="required-indicator">*</span>
	</label>
	<g:currencySelect name="moneda" value="${compraInstance?.moneda}"  />

</div>

<div class="fieldcontain ${hasErrors(bean: compraInstance, field: 'tc', 'error')} required">
	<label for="tc">
		<g:message code="compra.tc.label" default="Tc" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="tc" value="${fieldValue(bean: compraInstance, field: 'tc')}" required=""/>

</div>

<div class="fieldcontain ${hasErrors(bean: compraInstance, field: 'folio', 'error')} ">
	<label for="folio">
		<g:message code="compra.folio.label" default="Folio" />
		
	</label>
	<g:textField name="folio" value="${compraInstance?.folio}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: compraInstance, field: 'origen', 'error')} ">
	<label for="origen">
		<g:message code="compra.origen.label" default="Origen" />
		
	</label>
	<g:textArea name="origen" cols="40" rows="5" maxlength="255" value="${compraInstance?.origen}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: compraInstance, field: 'importe', 'error')} required">
	<label for="importe">
		<g:message code="compra.importe.label" default="Importe" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="importe" value="${fieldValue(bean: compraInstance, field: 'importe')}" required=""/>

</div>

<div class="fieldcontain ${hasErrors(bean: compraInstance, field: 'descuentos', 'error')} required">
	<label for="descuentos">
		<g:message code="compra.descuentos.label" default="Descuentos" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="descuentos" value="${fieldValue(bean: compraInstance, field: 'descuentos')}" required=""/>

</div>

<div class="fieldcontain ${hasErrors(bean: compraInstance, field: 'subtotal', 'error')} required">
	<label for="subtotal">
		<g:message code="compra.subtotal.label" default="Subtotal" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="subtotal" value="${fieldValue(bean: compraInstance, field: 'subtotal')}" required=""/>

</div>

<div class="fieldcontain ${hasErrors(bean: compraInstance, field: 'impuestos', 'error')} required">
	<label for="impuestos">
		<g:message code="compra.impuestos.label" default="Impuestos" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="impuestos" value="${fieldValue(bean: compraInstance, field: 'impuestos')}" required=""/>

</div>

<div class="fieldcontain ${hasErrors(bean: compraInstance, field: 'total', 'error')} required">
	<label for="total">
		<g:message code="compra.total.label" default="Total" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="total" value="${fieldValue(bean: compraInstance, field: 'total')}" required=""/>

</div>

<div class="fieldcontain ${hasErrors(bean: compraInstance, field: 'partidas', 'error')} ">
	<label for="partidas">
		<g:message code="compra.partidas.label" default="Partidas" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${compraInstance?.partidas?}" var="p">
    <li><g:link controller="compraDet" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="compraDet" action="create" params="['compra.id': compraInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'compraDet.label', default: 'CompraDet')])}</g:link>
</li>
</ul>


</div>

