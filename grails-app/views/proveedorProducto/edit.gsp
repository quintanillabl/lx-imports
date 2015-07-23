<%@ page import="com.luxsoft.impapx.ProveedorProducto" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="luxor">
		<g:set var="entityName" value="${message(code: 'proveedorProducto.label', default: 'ProveedorProducto')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="container">
			
			<div class="span8">

				<div class="page-header">
					<h3>Alta de producto para: ${proveedorProductoInstance?.proveedor?.nombre}</h3>
				</div>

				<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
				</g:if>

				<g:hasErrors bean="${proveedorProductoInstance}">
				<bootstrap:alert class="alert-error">
				<ul>
					<g:eachError bean="${proveedorProductoInstance}" var="error">
					<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
					</g:eachError>
				</ul>
				</bootstrap:alert>
				</g:hasErrors>

				<fieldset>
					<g:form class="form-horizontal" action="edit" >
						<fieldset>
							<f:with bean="proveedorProductoInstance">
								<g:hiddenField name="id" value="${proveedorProductoInstance?.id}"/>
								
								
								<f:display property="producto"/>
								<%--
									<g:hiddenField name="proveedor.id" value="${proveedorProductoInstance?.proveedor?.id}"/>
									<g:hiddenField name="producto.id"  value="${proveedorProductoInstance?.producto?.id}"/> 
								<f:field property="producto">
									<g:field type="text" name="producto" id="productoAuto" class="span7" value="${proveedorProductoInstance?.producto?.toString()}"/>
								</f:field>
								--%>
								<f:field property="gramos" value="${proveedorProductoInstance.gramos }"/>
								<f:field property="codigo" input-id="codigo" value="${proveedorProductoInstance?.codigo}"/>
								<f:field property="descripcion" input-id="descripcion" value="${proveedorProductoInstance?.descripcion}"/>
								<f:field property="costoUnitario" input-id="costoUnitario" value="${proveedorProductoInstance?.costoUnitario}"/>
							</f:with>
							<div class="form-actions">
								<button type="submit" class="btn btn-primary">
									<i class="icon-ok icon-white"></i>
									Actualizar
								</button>
							</div>
						</fieldset>
					</g:form>
				</fieldset>
				
			</div>

		</div>
		<r:script>
			$(function(){
				
				$("#productoAuto").autocomplete(
					{
						source:'<g:createLink controller="proveedor" action="productosAsignablesJSONList" params="['proveedorId':proveedorProductoInstance?.proveedor?.id]"/>',
						minLength:3,
						select:function(e,ui){
							console.log('Valor seleccionado: '+ui.item.id);
							$("#productoId").val(ui.item.id);
							$("#codigo").val(ui.item.id);
							$("#descripcion").val(ui.item.descripcion);
							$("#costoUnitario").val(0);
						}
					}
				);
			});
</r:script>
	</body>
</html>
