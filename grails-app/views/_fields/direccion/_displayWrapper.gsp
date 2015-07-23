<%@page expressionCodec="none" %>
<div class="form-group ${invalid?'has-error has-feedback':''}">
	%{-- <div class="${cols?:'col-sm-offset-2 col-sm-10 ' }"> --}%
	<div class="col-sm-offset-1 col-sm-10 }">
		<fieldset disabled>
			${widget}
		</fieldset>
		<g:if test="${invalid}">
			<span class="help-block">${errors.join('<br>')}</span>
			<span class="glyphicon glyphicon-remove form-control-feedback"></span>
		</g:if>
	</div>
</div>