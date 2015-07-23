<%@ page import="com.luxsoft.impapx.cxp.CuentaDeGastosGenerica" %>



<div class="fieldcontain ${hasErrors(bean: cuentaDeGastosGenericaInstance, field: 'comentario', 'error')} ">
	<label for="comentario">
		<g:message code="cuentaDeGastosGenerica.comentario.label" default="Comentario" />
		
	</label>
	<g:textField name="comentario" maxlength="250" value="${cuentaDeGastosGenericaInstance?.comentario}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: cuentaDeGastosGenericaInstance, field: 'referencia', 'error')} ">
	<label for="referencia">
		<g:message code="cuentaDeGastosGenerica.referencia.label" default="Referencia" />
		
	</label>
	<g:textField name="referencia" value="${cuentaDeGastosGenericaInstance?.referencia}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: cuentaDeGastosGenericaInstance, field: 'proveedor', 'error')} ">
	<label for="proveedor">
		<g:message code="cuentaDeGastosGenerica.proveedor.label" default="Proveedor" />
		
	</label>
	<g:select id="proveedor" name="proveedor.id" from="${com.luxsoft.impapx.Proveedor.list()}" optionKey="id" value="${cuentaDeGastosGenericaInstance?.proveedor?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: cuentaDeGastosGenericaInstance, field: 'facturas', 'error')} ">
	<label for="facturas">
		<g:message code="cuentaDeGastosGenerica.facturas.label" default="Facturas" />
		
	</label>
	<g:select name="facturas" from="${com.luxsoft.impapx.FacturaDeGastos.list()}" multiple="multiple" optionKey="id" size="5" value="${cuentaDeGastosGenericaInstance?.facturas*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: cuentaDeGastosGenericaInstance, field: 'fecha', 'error')} required">
	<label for="fecha">
		<g:message code="cuentaDeGastosGenerica.fecha.label" default="Fecha" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fecha" precision="day"  value="${cuentaDeGastosGenericaInstance?.fecha}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: cuentaDeGastosGenericaInstance, field: 'importe', 'error')} required">
	<label for="importe">
		<g:message code="cuentaDeGastosGenerica.importe.label" default="Importe" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="importe" value="${fieldValue(bean: cuentaDeGastosGenericaInstance, field: 'importe')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: cuentaDeGastosGenericaInstance, field: 'impuestos', 'error')} required">
	<label for="impuestos">
		<g:message code="cuentaDeGastosGenerica.impuestos.label" default="Impuestos" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="impuestos" value="${fieldValue(bean: cuentaDeGastosGenericaInstance, field: 'impuestos')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: cuentaDeGastosGenericaInstance, field: 'total', 'error')} required">
	<label for="total">
		<g:message code="cuentaDeGastosGenerica.total.label" default="Total" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="total" value="${fieldValue(bean: cuentaDeGastosGenericaInstance, field: 'total')}" required=""/>
</div>

