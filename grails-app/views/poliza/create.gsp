<%@ page import="com.luxsoft.impapx.contabilidad.Poliza" %>
<%@ page import="com.luxsoft.lx.contabilidad.ProcesadorDePoliza" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="createForm">
	<g:set var="entityName" value="${message(code: 'poliza.label', default: 'Póliza')}" scope="request"/>
	<g:set var="entity" value="${polizaInstance}" scope="request" />
	<title>Alta de Póliza</title>
</head>
<body>
	
<content tag="header">
	Alta de póliza   (${session.periodoContable})
</content>

<content tag="formFields">
	<f:with bean="${polizaInstance}" >
		<g:hiddenField name="ejercicio" value="${session.periodoContable.ejercicio}"/>
		<g:hiddenField name="mes" value="${session.periodoContable.mes}"/>
		<g:hiddenField name="subTipo" value="${polizaInstance.subTipo}"/>
		<f:field property="tipo" widget-autofocus="true" wrapper="bootstrap3" widget-class="form-control" />
		
		
		
		<f:display property="subTipo" wrapper="bootstrap3" widget-class="form-control"/>
		<f:field property="fecha" wrapper="bootstrap3" >
			<input name="fecha" id="fecha" type="text" class="form-control" value="">
		</f:field>
		<f:field property="descripcion" wrapper="bootstrap3" widget-class="form-control" label="Concepto"/>
		<f:field property="manual" wrapper="bootstrap3" />
	</f:with>

<script type="text/javascript">
	$(function(){
		
		$("#fecha").bootstrapDP({
			changeMonth: false,
			format: 'dd/mm/yyyy',
	      	keyboardNavigation: false,
		    forceParse: false,
		    autoclose: true,
		    todayHighlight: true,
	      	startDate:"${formatDate(date:session.periodoContable.toPeriodo().fechaInicial,format:'dd/MM/yyyy')}",
	      	endDate:"${formatDate(date:session.periodoContable.toPeriodo().fechaFinal,format:'dd/MM/yyyy')}"
		});
		
	});
</script>
	
</content>
</body>

	
</html>


