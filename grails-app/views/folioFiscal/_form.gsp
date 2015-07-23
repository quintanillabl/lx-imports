<%@ page import="com.luxsoft.cfd.FolioFiscal" %>



<div class="fieldcontain ${hasErrors(bean: folioFiscalInstance, field: 'anoAprobacion', 'error')} required">
	<label for="anoAprobacion">
		<g:message code="folioFiscal.anoAprobacion.label" default="Ano Aprobacion" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="anoAprobacion" type="number" value="${folioFiscalInstance.anoAprobacion}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: folioFiscalInstance, field: 'asignacion', 'error')} required">
	<label for="asignacion">
		<g:message code="folioFiscal.asignacion.label" default="Asignacion" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="asignacion" precision="day"  value="${folioFiscalInstance?.asignacion}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: folioFiscalInstance, field: 'folio', 'error')} required">
	<label for="folio">
		<g:message code="folioFiscal.folio.label" default="Folio" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="folio" type="number" value="${folioFiscalInstance.folio}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: folioFiscalInstance, field: 'folioFinal', 'error')} required">
	<label for="folioFinal">
		<g:message code="folioFiscal.folioFinal.label" default="Folio Final" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="folioFinal" type="number" value="${folioFiscalInstance.folioFinal}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: folioFiscalInstance, field: 'folioInicial', 'error')} required">
	<label for="folioInicial">
		<g:message code="folioFiscal.folioInicial.label" default="Folio Inicial" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="folioInicial" type="number" value="${folioFiscalInstance.folioInicial}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: folioFiscalInstance, field: 'noAprobacion', 'error')} required">
	<label for="noAprobacion">
		<g:message code="folioFiscal.noAprobacion.label" default="No Aprobacion" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="noAprobacion" type="number" value="${folioFiscalInstance.noAprobacion}" required=""/>
</div>

