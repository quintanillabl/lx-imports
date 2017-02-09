%{-- <%@page expressionCodec="none" %>
<asset:stylesheet src="jquery-ui.css"/>
<asset:javascript src="jquery-ui/autocomplete.js"/>
<g:hiddenField id="clienteId" name="${property}.id" value="${value?.id}" />
<input 
	id="${property}" 
	type="text" 
	name="${property}Nombre"  
	class="form-control clienteField" 
	value="${value}" 
	placeholder="Cliente">
</input>

<script type="text/javascript">
$(function(){
	$(".clienteField").autocomplete({
			source:'<g:createLink controller="cliente" action="getClientesJSON"/>',
			minLength:3,
			select:function(e,ui){
				console.log('Valor seleccionado: '+ui.item.id);
				$("#clienteId").val(ui.item.id);
			}
	});
});
</script>



 --}%
 <%@page expressionCodec="none" %>
 <g:select class="form-control chosen-select"  
 	name="${property}" 
 	value="${value?.id}"
 	from="${com.luxsoft.impapx.Cliente.findAll()}" 
 	optionKey="id" 
 	optionValue="nombre"
 	
 	/>