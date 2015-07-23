<%@page expressionCodec="none" %>
<div class="form-group ${invalid?'has-error has-feedback':''}">
	<label class="${colsLabel?:'col-sm-2' } control-label" for="${property}">${label}</label>
	<div class="${cols?:'col-sm-10' }">
		 ${widget}
		<g:if test="${invalid}">
			<span class="help-block">${errors.join('<br>')}</span>
			<span class="glyphicon glyphicon-remove form-control-feedback"></span>
		</g:if>
	</div>
</div>