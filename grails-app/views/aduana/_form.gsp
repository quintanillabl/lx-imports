<%@ page import="com.luxsoft.impapx.Aduana" %>



<div class="fieldcontain ${hasErrors(bean: aduanaInstance, field: 'nombre', 'error')} required">
	<label for="nombre">
		<g:message code="aduana.nombre.label" default="Nombre" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombre" maxlength="50" required="" value="${aduanaInstance?.nombre}"/>

</div>
<fieldset class="embedded"><legend><g:message code="aduana.direccion.label" default="Direccion" /></legend>
<div class="fieldcontain ${hasErrors(bean: aduanaInstance, field: 'direccion.calle', 'error')} required">
	<label for="direccion.calle">
		<g:message code="aduana.direccion.calle.label" default="Calle" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="calle" maxlength="200" required="" value="${direccionInstance?.calle}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: aduanaInstance, field: 'direccion.codigoPostal', 'error')} required">
	<label for="direccion.codigoPostal">
		<g:message code="aduana.direccion.codigoPostal.label" default="Codigo Postal" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="codigoPostal" required="" value="${direccionInstance?.codigoPostal}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: aduanaInstance, field: 'direccion.colonia', 'error')} ">
	<label for="direccion.colonia">
		<g:message code="aduana.direccion.colonia.label" default="Colonia" />
		
	</label>
	<g:textField name="colonia" value="${direccionInstance?.colonia}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: aduanaInstance, field: 'direccion.estado', 'error')} required">
	<label for="direccion.estado">
		<g:message code="aduana.direccion.estado.label" default="Estado" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="estado" required="" value="${direccionInstance?.estado}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: aduanaInstance, field: 'direccion.municipio', 'error')} ">
	<label for="direccion.municipio">
		<g:message code="aduana.direccion.municipio.label" default="Municipio" />
		
	</label>
	<g:textField name="municipio" value="${direccionInstance?.municipio}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: aduanaInstance, field: 'direccion.numeroExterior', 'error')} ">
	<label for="direccion.numeroExterior">
		<g:message code="aduana.direccion.numeroExterior.label" default="Numero Exterior" />
		
	</label>
	<g:textField name="numeroExterior" maxlength="50" value="${direccionInstance?.numeroExterior}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: aduanaInstance, field: 'direccion.numeroInterior', 'error')} ">
	<label for="direccion.numeroInterior">
		<g:message code="aduana.direccion.numeroInterior.label" default="Numero Interior" />
		
	</label>
	<g:textField name="numeroInterior" maxlength="50" value="${direccionInstance?.numeroInterior}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: aduanaInstance, field: 'direccion.pais', 'error')} required">
	<label for="direccion.pais">
		<g:message code="aduana.direccion.pais.label" default="Pais" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="pais" maxlength="100" required="" value="${direccionInstance?.pais}"/>

</div>
</fieldset>
