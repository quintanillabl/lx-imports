<%@ page import="com.luxsoft.impapx.contabilidad.Poliza" %>



<div class="fieldcontain ${hasErrors(bean: polizaInstance, field: 'descripcion', 'error')} required">
	<label for="descripcion">
		<g:message code="poliza.descripcion.label" default="Descripcion" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="descripcion" maxlength="250" required="" value="${polizaInstance?.descripcion}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: polizaInstance, field: 'debe', 'error')} required">
	<label for="debe">
		<g:message code="poliza.debe.label" default="Debe" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="debe" value="${fieldValue(bean: polizaInstance, field: 'debe')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: polizaInstance, field: 'haber', 'error')} required">
	<label for="haber">
		<g:message code="poliza.haber.label" default="Haber" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="haber" value="${fieldValue(bean: polizaInstance, field: 'haber')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: polizaInstance, field: 'tipo', 'error')} ">
	<label for="tipo">
		<g:message code="poliza.tipo.label" default="Tipo" />
		
	</label>
	<g:select name="tipo" from="${polizaInstance.constraints.tipo.inList}" value="${polizaInstance?.tipo}" valueMessagePrefix="poliza.tipo" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: polizaInstance, field: 'fecha', 'error')} required">
	<label for="fecha">
		<g:message code="poliza.fecha.label" default="Fecha" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fecha" precision="day"  value="${polizaInstance?.fecha}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: polizaInstance, field: 'folio', 'error')} required">
	<label for="folio">
		<g:message code="poliza.folio.label" default="Folio" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="folio" type="number" value="${polizaInstance.folio}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: polizaInstance, field: 'partidas', 'error')} ">
	<label for="partidas">
		<g:message code="poliza.partidas.label" default="Partidas" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${polizaInstance?.partidas?}" var="p">
    <li><g:link controller="polizaDet" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="polizaDet" action="create" params="['poliza.id': polizaInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'polizaDet.label', default: 'PolizaDet')])}</g:link>
</li>
</ul>

</div>

