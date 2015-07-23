<input type="text" 
	class="form-control" 
	value="${(value instanceof Date)?formatDate(number:value,format:'dd/MM/yyyy hh:mm'):value}" 
	disabled>