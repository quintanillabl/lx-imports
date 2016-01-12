<g:select class="form-control chosen-select"  
 	name="${property}" 
 	value="${value?.id}"
 	from="${com.luxsoft.impapx.PaisDeOrigen.findAll()}" 
 	optionKey="id" 
 	optionValue="nombre"
 />