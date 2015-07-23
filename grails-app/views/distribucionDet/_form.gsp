<fieldset>
	<g:form class="form-horizontal" action="fraccionar" id="${distribucionDetInstance.id }">
		<fieldset>
			<f:with bean="${distribucionDetInstance}">
				<f:field property="embarqueDet.producto" >
					<g:field name="embarqueDet.producto" type="text" 
						value="${distribucionDetInstance?.embarqueDet?.producto}" readOnly="true"
						class="input-xxlarge"/>
				</f:field>
				
				<f:field property="contenedor" input-readOnly="true"/>
				
				<f:field property="cantidad" >
					<g:field name="cantidad" type="text" value="${distribucionDetInstance?.cantidad}"/>
					<input type="text" id="sCantidad" readOnly value="${distribucionDetInstance?.cantidad}"/>
				</f:field>
				<f:field property="tarimas" >
					<g:field name="tarimas" type="text" value="${distribucionDetInstance?.tarimas}"/>
					<input type="text" id="sTarimas" readOnly value="${distribucionDetInstance?.tarimas}"/>
				</f:field>
				<f:field property="kilosNetos" >
					<g:field name="kilosNetos" type="text" value="${distribucionDetInstance?.kilosNetos}"/>
					<input type="text" id="sKilosNetos" readOnly value="${distribucionDetInstance?.kilosNetos}"/>
				</f:field>
			</f:with>
			<div class="form-actions">
				<button type="submit" class="btn btn-primary" onclick="return validar();">
					<i class="icon-ok icon-white"></i>
					<g:message code="default.button.update.label" default="Actualizar" />
				</button>
			</div>
		</fieldset>
	</g:form>
</fieldset>
<r:script>

	$(function(){
		
		
	});
	function validar(){
		
		
		var c1=+$("#sCantidad").val();
		var c2=+$("#cantidad").val();
		if(c1-c2<=0){
			alert('Cantidad invalida debe ser menor a :'+c1);
			return false;
		}
		var t1=+$("#sTarimas").val();
		var t2=+$("#tarimas").val();
		if(t1-t2<=0){
			alert('Cantidad de tarimas invalida debe ser menor a :'+t1);
			return false;
		}
		var k1=+$("#sKilosNetos").val();
		var k2=+$("#kilosNetos").val();
		if(k1-k2<=0){
			alert('Kilos netos invalidos deben ser menor a :'+k1);
			return false;
		}
		return true;
	}
</r:script>