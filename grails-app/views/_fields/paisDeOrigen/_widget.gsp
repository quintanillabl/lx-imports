<g:select class="form-control chosen-select"  
 	name="${property}" 
 	value="${value?.id}"
 	from="${com.luxsoft.impapx.PaisDeOrigen.findAll()}" 
 	noSelection="[null:'Seleccione un país de origen']"
 	optionKey="id" 
 	optionValue="nombre"
 />