<form class=class="form-horizontal">

	<f:with bean="${it}">
		<f:display property="cuenta" input-class="span5" input-readOnly="true"/>
		<f:display property="concepto"  input-readOnly="true"/>
		<f:display property="fecha" input-readOnly="true"/>
		<f:display property="tipo" input-readOnly="true"/>
		<f:display property="referenciaBancaria" input-readOnly="true"/>
		<f:display property="tc" input-readOnly="true"/>
		<f:display property="importe" input-readOnly="true"/>
		<f:display property="comentario" input-class="input-xxlarge" input-readOnly="true"/>
	</f:with>
</form>

