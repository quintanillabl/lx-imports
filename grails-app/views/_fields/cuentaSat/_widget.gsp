<%@page expressionCodec="none" %>
<asset:stylesheet src="jquery-ui.css"/>
<asset:javascript src="jquery-ui/autocomplete.js"/>
<g:hiddenField id="cuentaSatId" name="${property}.id" value="${value?.id}" />
%{-- <input type="text" id="cuentaCondigoField" class="form-control" placeholder="Codigo"> --}%
<input 
	id="${property}" 
	type="text" 
	name="${property}Nombre"  
	class="form-control cuentaSatField" 
	value="${value}" 
	placeholder="Seleccione una cuenta del SAT">
</input>

<script type="text/javascript">
	$(function(){
		$("#${property}").autocomplete({
				source:'<g:createLink controller="cuentaSat" action="getCuentasJSON"/>',
				minLength:3,
				select:function(e,ui){
					console.log('Valor seleccionado: '+ui.item.id);
					$("#cuentaSatId").val(ui.item.id);
				}
		});
	});
</script>



