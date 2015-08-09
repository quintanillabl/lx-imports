<g:hiddenField id="requisicionId" name="${property}.id" value="${value?.id}" />
<input 
	id="requisicionField" 
	type="text" 
	class="form-control" 
	value="${value}" 
	placeholder:"Seleccione una requisición "
	required="${required}">
</input>

<script type="text/javascript">
	$(function(){
		$("#requisicionField").autocomplete({
			source:'<g:createLink controller="requisicion" action="requisicionesAsJSON"/>',
			minLength:1,
			select:function(e,ui){
				//console.log('Valor seleccionado: '+ui.item.id);
				$("#requisicionId").val(ui.item.id);
			}
		});
	});
</script>