<%@ page import="com.luxsoft.impapx.contabilidad.CuentaContable" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="createForm">
	<g:set var="entityName" value="${message(code: 'cuentaContable.label', default: 'Cuenta contable')}" scope="request"/>
	<g:set var="entity" value="${cuentaContableInstance}" scope="request" />
	<title>Alta de Cuenta contable</title>
</head>
<body>
	

<content tag="formFields">
	<f:with bean="${cuentaContableInstance}" >
		
		<f:field property="clave" widget-autofocus="true" wrapper="bootstrap3" widget-class="form-control" />
		<f:field property="descripcion" wrapper="bootstrap3" widget-class="form-control" />
		<f:field property="tipo" wrapper="bootstrap3" widget-class="form-control"  />
		<f:field property="subTipo" wrapper="bootstrap3" widget-class="form-control" />	
		<f:field property="naturaleza" wrapper="bootstrap3" widget-class="form-control" />

		<f:field property="presentacionContable" wrapper="bootstrap3" />
		<f:field property="presentacionContable" wrapper="bootstrap3" />
		<f:field property="presentacionFiscal" wrapper="bootstrap3" />
		<f:field property="presentacionFinanciera" wrapper="bootstrap3" />
		<f:field property="presentacionPresupuestal" wrapper="bootstrap3" />

		<f:field property="cuentaSat" wrapper="bootstrap3" >
			<g:select class="form-control chosen-select"  
				name="${property}" 
				value="${value?.id}"
				from="${com.luxsoft.lx.sat.CuentaSat.findAll()}" 
				optionKey="id" 
				optionValue="nombre"
				noSelection="[null:'Seleccione una cuenta del SAT']"
				/>
		</f:field>
		
	</f:with>
	<script type="text/javascript">
		$(function(){
			$('#clave').mask('00000');
		});
	</script>
	
</content>

</body>

	
</html>

