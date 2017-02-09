<%@ page import="com.luxsoft.impapx.Pedimento" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="createForm">
	<g:set var="entityName" value="${message(code: 'pedimento.label', default: 'Pedimento')}" scope="request"/>
	<g:set var="entity" value="${pedimentoInstance}" scope="request" />
	<title>Alta de Pedimento</title>
</head>
<body>
	

<content tag="formFields">
	<f:with bean="${pedimentoInstance}" >
		<g:if test="${pedimentoInstance.id}">
			<f:display property="id" wrapper="bootstrap3" />
		</g:if>
		<f:field property="pedimento" widget-autofocus="true" wrapper="bootstrap3" widget-class="form-control" />
		<f:field property="proveedor" wrapper="bootstrap3"  label="Agencia">
			<g:select class="form-control chosen-select"  
				name="${property}" 
				value="${value?.id}"
				from="${com.luxsoft.impapx.Proveedor.where{agenciaAduanal==true}}" 
				optionKey="id" 
				optionValue="nombre"
				noSelection="[null:'Seleccione una agencia aduanal']"
				/>
		</f:field>
		<f:field property="agenteAduanal" wrapper="bootstrap3"  >
			<select class="form-control" id="agenteAduanal" name="agenteAduanal"></select>
		</f:field>
		<f:field property="fecha" wrapper="bootstrap3" />
		<f:field property="dta" wrapper="bootstrap3" widget="money"/>
		<f:field property="prevalidacion" wrapper="bootstrap3" widget="money"/>
		<f:field property="arancel" wrapper="bootstrap3" widget="money"/>
		<f:field property="tipoDeCambio" wrapper="bootstrap3"  widget="tc"/>
		<f:field property="impuestoTasa" wrapper="bootstrap3" widget="porcentaje"/>
		<f:field property="comentario" wrapper="bootstrap3" widget-class="form-control"/>
		<f:field property="paisDeOrigen" wrapper="bootstrap3" widget-class="form-control"/>
	</f:with>

<script type="text/javascript">
	$(function(){
		$("#pedimento").mask("99-99-9999-9999999");
		$("#proveedor").on('change',function(){
			var proveedor=$(this).val();
			console.log('Seleccion: '+proveedor);

			var $select = $('#agenteAduanal');

			$.getJSON(
				"${createLink(controller:'proveedor',action:'buscarAgentesAduanales')}",
				{id:proveedor}
			).done(function(data){

				//clear the current content of the select
				$select.html('');
				console.log('Actualizando agentes'+data);
				
				$select.append($('<option></option>').attr("value", '').text('Seleccione un agente'));
  				$.each(data, function(key, val){
  		  			console.log('Agregando: '+key);
  		  			console.log('Val: '+val);
  		  			//$select.append('<option>' + val.nombre+ '</option>');	
  		  			
  		  			$select.append($('<option></option>').attr("value", val.nombre).text(val.nombre));

  				});

			});
		});



		
	});
</script>
	
</content>
</body>

	
</html>
