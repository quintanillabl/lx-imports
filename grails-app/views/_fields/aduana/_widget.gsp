<%@page expressionCodec="none" %>

<g:select class="form-control"  
	name="${property}" 
	value="${value?.id}"
	from="${com.luxsoft.impapx.Aduana.findAll()}" 
	optionKey="id" 
	optionValue="nombre"
	noSelection="[null:'Seleccione una aduana']"
	/>