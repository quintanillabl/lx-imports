<%@page expressionCodec="none" %>
<asset:stylesheet src="jquery-ui.css"/>
<asset:javascript src="jquery-ui/autocomplete.js"/>
%{-- <g:hiddenField id="clienteId" name="${property}.id" value="${value?.id}" /> --}%
<input 
	id="${property}" 
	type="text" 
	name="${property}"  
	class="form-control " 
	value="${formatDate(date:value,format:'dd/MM/yyyy')}"/>


<script type="text/javascript">
$(function(){
	$("#${property}").datepicker({
		changeMonth: true,
      	changeYear: true,
      	showAnim: "fadeIn",
      	showOptions: { direction: "up" }
	});
});
</script>



