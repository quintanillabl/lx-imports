<g:select class="form-control chosen-select"  
	name="${property}" 
	value="${value?.id}"
	from="${com.luxsoft.impapx.CuentaBancaria.list()}" 
	optionKey="id" 
	noSelection="${['null':'Seleccione una cuenta']}"
	required='required'
	/>