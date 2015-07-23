<%@ page import="com.luxsoft.impapx.ProveedorProducto" %>



<div class="fieldcontain ${hasErrors(bean: proveedorProductoInstance, field: 'producto', 'error')} required">
	<label for="producto">
		<g:message code="proveedorProducto.producto.label" default="Producto" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="producto" name="producto.id" from="${com.luxsoft.impapx.Producto.list()}" optionKey="id" required="" value="${proveedorProductoInstance?.producto?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: proveedorProductoInstance, field: 'codigo', 'error')} ">
	<label for="codigo">
		<g:message code="proveedorProducto.codigo.label" default="Codigo" />
		
	</label>
	<g:textField name="codigo" maxlength="250" value="${proveedorProductoInstance?.codigo}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: proveedorProductoInstance, field: 'descripcion', 'error')} ">
	<label for="descripcion">
		<g:message code="proveedorProducto.descripcion.label" default="Descripcion" />
		
	</label>
	<g:textField name="descripcion" maxlength="250" value="${proveedorProductoInstance?.descripcion}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: proveedorProductoInstance, field: 'costoUnitario', 'error')} required">
	<label for="costoUnitario">
		<g:message code="proveedorProducto.costoUnitario.label" default="Costo Unitario" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="costoUnitario" value="${fieldValue(bean: proveedorProductoInstance, field: 'costoUnitario')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: proveedorProductoInstance, field: 'proveedor', 'error')} required">
	<label for="proveedor">
		<g:message code="proveedorProducto.proveedor.label" default="Proveedor" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="proveedor" name="proveedor.id" from="${com.luxsoft.impapx.Proveedor.list()}" optionKey="id" required="" value="${proveedorProductoInstance?.proveedor?.id}" class="many-to-one"/>
</div>

