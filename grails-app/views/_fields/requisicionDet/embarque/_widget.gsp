<g:select class="form-control chosen-select"  
				name="embarque" 
				value="${value}"
				from="${com.luxsoft.impapx.Embarque.where{facturado<=0}.list()}" 
				optionKey="id" 
				noSelection="[null:'Seleccione un embarque']"
				/>