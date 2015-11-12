<input type="text" class="form-control" 
	value="${(value instanceof Date)?formatDate(date:value,format:'dd/MM/yyyy'):value}" disabled>