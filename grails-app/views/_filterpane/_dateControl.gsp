<span id="${ctrlAttrs.id}-container" style="${ctrlAttrs.style}">
	<%
		ctrlAttrs.class=' form-control'
		ctrlAttrs.precision='day'

	%>
    <filterpane:datePicker ctrlAttrs="${ctrlAttrs}" />
    %{-- <f:widget bean="${ctrlAttrs.domain}" property="${ctrlAttrs.propertyName}"/> --}%

    <g:if test="${ctrlAttrs.name?.endsWith('To')}">
        <input type="hidden"
               name="filter.${ctrlAttrs.domain}.${ctrlAttrs.propertyName}_isDayPrecision"
               value="${ctrlAttrs.isDayPrecision}"/>
    </g:if>
</span>