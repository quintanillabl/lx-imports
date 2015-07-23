<%@ page import="com.luxsoft.lx.sat.CuentaSat" %>



<div class="fieldcontain ${hasErrors(bean: cuentaSatInstance, field: 'codigo', 'error')} required">
	<label for="codigo">
		<g:message code="cuentaSat.codigo.label" default="Codigo" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="codigo" maxlength="20" required="" value="${cuentaSatInstance?.codigo}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: cuentaSatInstance, field: 'tipo', 'error')} required">
	<label for="tipo">
		<g:message code="cuentaSat.tipo.label" default="Tipo" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="tipo" maxlength="100" required="" value="${cuentaSatInstance?.tipo}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: cuentaSatInstance, field: 'nivel', 'error')} required">
	<label for="nivel">
		<g:message code="cuentaSat.nivel.label" default="Nivel" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="nivel" type="number" value="${cuentaSatInstance.nivel}" required=""/>

</div>

<div class="fieldcontain ${hasErrors(bean: cuentaSatInstance, field: 'nombre', 'error')} required">
	<label for="nombre">
		<g:message code="cuentaSat.nombre.label" default="Nombre" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombre" required="" value="${cuentaSatInstance?.nombre}"/>

</div>

