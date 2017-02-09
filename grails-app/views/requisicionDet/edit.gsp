<%@ page import="com.luxsoft.impapx.RequisicionDet" %>
<!doctype html>
<html>
<head>

	<title>Detalle de requisicion ${requisicionDetInstance.id}</title>
</head>
<body>
	<g:set var="requisicionInstance" value="${requisicionDetInstance.requisicion}"></g:set>

	<div class="row wrapper border-bottom white-bg page-heading">
		<g:link controller="requisicion" id="${requisicionInstance?.id}" action="edit">
			<h2>Concepto: ${requisicionDetInstance?.id} <small>Req: (${requisicionDetInstance.requisicion})</small></h2>
		</g:link>
	</div>

	<div class="row wrapper wrapper-content animated fadeInRight">
	    
        <div class="col-lg-6 col-lg-offset-3" >
        	<lx:ibox>
        		<g:form name="updateForm" action="update" class="form-horizontal" method="PUT">  
					
					<g:hiddenField name="id" value="${requisicionDetInstance.id}"/>
                    <g:hiddenField name="version" value="${requisicionDetInstance.version}"/>
					<g:hiddenField name="requisicion.id" value="${requisicionInstance.id}"/>
        			
        			<lx:iboxTitle2 title="Propiedades"/>
        			<lx:iboxContent>
        				<lx:errorsHeader bean="${requisicionDetInstance}"/>
						
						<f:with bean="${requisicionDetInstance}">
							<f:display property="documento" widget-class="form-control"/>
							<f:display property="fechaDocumento" />
							<f:display property="total" widget="money"/>
							
							<f:field property="factura" label="Factura">
			    				<g:hiddenField id="facturaId" name="factura.id" value="${requisicionDetInstance?.factura?.id}"/>
			    				<input type="text" value="${value}" id="facturaField" class="form-control">
				    		</f:field>
						</f:with>

        			</lx:iboxContent>
        			<lx:iboxFooter>
        				<button id="saveBtn" class="btn btn-primary ">
							<i class="fa fa-floppy-o"></i> Actualizar
                        </button>
						<lx:backButton controller="requisicion" action="show" id="${requisicionInstance.id}"/>
        			</lx:iboxFooter>
        		</g:form>
        	</lx:ibox>
        </div>
	    
	</div>
	
	<script>
		$("#facturaField").autocomplete({
			source:'<g:createLink action="getFacturasDisponibles"/>',
			minLength:1,
			select:function(e,ui){
				console.log('Valor seleccionado: '+ui.item.id);
				$("#facturaId").val(ui.item.id);
			}
		});
	</script>
 
	
</body>
</html>

