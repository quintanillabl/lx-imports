<g:hiddenField id="embarqueId" name="${property}.id" value="${value?.id}" />
<input 
	id="embarqueField" 
	type="text" 
	name="${property}Nombre"  
	class="form-control embarqueField" 
	value="${value}" 
	placeholder="Embarque">
</input>

<script type="text/javascript">
	$(function(){
		$("#embarqueField").autocomplete({
			source:'<g:createLink controller="embarque" action="search"/>',
			minLength:1,
			select:function(e,ui){
				$("#embarqueId").val(ui.item.id);
			}
		});
	});
</script>