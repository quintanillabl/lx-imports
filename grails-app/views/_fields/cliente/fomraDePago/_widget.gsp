<%@page expressionCodec="none" %>

<g:select class="form-control"  
	name="${property}" 
	value="${value?.id}"
	from="${['TRANSFERENCIA','CHEQUE','EFECTIVO','DEPOSITO','TARJETA_CREDITO','TARJETA_DEBITO']}" 
	optionKey="id" 
	optionValue="clave"
	/>