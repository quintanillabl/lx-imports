<div class="row">
<div class="col-sm-4">
	<div class="form-group">
		<label for="property" class="control-label col-sm-4">${fieldLabel}</label>
		<div class="col-sm-6">
			<g:select id="${opName}" class="form-control"
				  name="${opName}" 
				  from="${opKeys}" 
				  keys="${opKeys}"
				  value="${opValue}"
				  valueMessagePrefix="fp.op"
				  onChange="grailsFilterPane.filterOpChange('${opName}', '${ctrlAttrs.id}');" />
		</div>
	</div>
	
</div>
<div class="col-sm-4">
	<div class="form-group">
		<%
				ctrlAttrs.class=' form-control'
			%>
			<filterpane:input ctrlType="${ctrlType}" ctrlAttrs="${ctrlAttrs}" />
	</div>
	
</div>
<div class="col-sm-4">
	<g:if test="${toCtrlAttrs != null}">
	<span style="${toCtrlSpanStyle}" id="between-span-${toCtrlAttrs.id}">
		<span class="col-sm-2">
			<g:message code="fp.tag.filterPane.property.betweenValueSeparatorText" default="and" />
		</span>
		<span class="col-sm-10">
			<filterpane:input ctrlType="${ctrlType}" ctrlAttrs="${toCtrlAttrs}" />
		</span>
		
	</span>
	</g:if>
</div>
</div>
