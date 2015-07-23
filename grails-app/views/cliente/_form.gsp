<%@ page import="com.luxsoft.impapx.Cliente" %>



<div class="fieldcontain ${hasErrors(bean: clienteInstance, field: 'nombre', 'error')} required">
	<label for="nombre">
		<g:message code="cliente.nombre.label" default="Nombre" />
		<span class="required-indicator">*</span>
	</label>
	<g:textArea name="nombre" cols="40" rows="5" maxlength="255" required="" value="${clienteInstance?.nombre}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: clienteInstance, field: 'rfc', 'error')} ">
	<label for="rfc">
		<g:message code="cliente.rfc.label" default="Rfc" />
		
	</label>
	<g:textField name="rfc" value="${clienteInstance?.rfc}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: clienteInstance, field: 'email1', 'error')} ">
	<label for="email1">
		<g:message code="cliente.email1.label" default="Email1" />
		
	</label>
	<g:textField name="email1" value="${clienteInstance?.email1}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: clienteInstance, field: 'formaDePago', 'error')} ">
	<label for="formaDePago">
		<g:message code="cliente.formaDePago.label" default="Forma De Pago" />
		
	</label>
	<g:textField name="formaDePago" value="${clienteInstance?.formaDePago}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: clienteInstance, field: 'cuentaDePago', 'error')} ">
	<label for="cuentaDePago">
		<g:message code="cliente.cuentaDePago.label" default="Cuenta De Pago" />
		
	</label>
	<g:textField name="cuentaDePago" maxlength="4" value="${clienteInstance?.cuentaDePago}"/>

</div>
<fieldset class="embedded"><legend><g:message code="cliente.direccion.label" default="Direccion" /></legend>
<div class="fieldcontain ${hasErrors(bean: clienteInstance, field: 'direccion.calle', 'error')} required">
	<label for="direccion.calle">
		<g:message code="cliente.direccion.calle.label" default="Calle" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="calle" maxlength="200" required="" value="${direccionInstance?.calle}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: clienteInstance, field: 'direccion.codigoPostal', 'error')} required">
	<label for="direccion.codigoPostal">
		<g:message code="cliente.direccion.codigoPostal.label" default="Codigo Postal" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="codigoPostal" required="" value="${direccionInstance?.codigoPostal}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: clienteInstance, field: 'direccion.colonia', 'error')} ">
	<label for="direccion.colonia">
		<g:message code="cliente.direccion.colonia.label" default="Colonia" />
		
	</label>
	<g:textField name="colonia" value="${direccionInstance?.colonia}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: clienteInstance, field: 'direccion.estado', 'error')} required">
	<label for="direccion.estado">
		<g:message code="cliente.direccion.estado.label" default="Estado" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="estado" required="" value="${direccionInstance?.estado}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: clienteInstance, field: 'direccion.municipio', 'error')} ">
	<label for="direccion.municipio">
		<g:message code="cliente.direccion.municipio.label" default="Municipio" />
		
	</label>
	<g:textField name="municipio" value="${direccionInstance?.municipio}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: clienteInstance, field: 'direccion.numeroExterior', 'error')} ">
	<label for="direccion.numeroExterior">
		<g:message code="cliente.direccion.numeroExterior.label" default="Numero Exterior" />
		
	</label>
	<g:textField name="numeroExterior" maxlength="50" value="${direccionInstance?.numeroExterior}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: clienteInstance, field: 'direccion.numeroInterior', 'error')} ">
	<label for="direccion.numeroInterior">
		<g:message code="cliente.direccion.numeroInterior.label" default="Numero Interior" />
		
	</label>
	<g:textField name="numeroInterior" maxlength="50" value="${direccionInstance?.numeroInterior}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: clienteInstance, field: 'direccion.pais', 'error')} required">
	<label for="direccion.pais">
		<g:message code="cliente.direccion.pais.label" default="Pais" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="pais" maxlength="100" required="" value="${direccionInstance?.pais}"/>

</div>
</fieldset>
<div class="fieldcontain ${hasErrors(bean: clienteInstance, field: 'subCuentaOperativa', 'error')} ">
	<label for="subCuentaOperativa">
		<g:message code="cliente.subCuentaOperativa.label" default="Sub Cuenta Operativa" />
		
	</label>
	<g:textField name="subCuentaOperativa" maxlength="4" value="${clienteInstance?.subCuentaOperativa}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: clienteInstance, field: 'fisica', 'error')} ">
	<label for="fisica">
		<g:message code="cliente.fisica.label" default="Fisica" />
		
	</label>
	<g:checkBox name="fisica" value="${clienteInstance?.fisica}" />

</div>

