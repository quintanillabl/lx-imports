<g:select class="form-control chosen-select"  
	name="${property}" 
	value="${value?.id}"
	from="${com.luxsoft.impapx.Requisicion.findAll("from Requisicion r left join fetch r.pagoProveedor pp where r.total>0  and pp is  null")}" 
	optionKey="id" 
	noSelection="${['null':'Seleccione una requisicion']}"
	required='required'
	/>
<%--
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
				source:'<g:createLink controller="pagoProveedor" action="requisicionesDisponiblesJSONList"/>',
				minLength:1,
				select:function(e,ui){
					//console.log('Valor seleccionado: '+ui.item.id);
					$("#requisicionId").val(ui.item.id);
				}
		});
	});
</script>
--%>