%{-- <a href="javascript:void(0);"
   onclick="grailsFilterPane.toggleElement('${filterPaneId}');
   return false;"
   class="${styleClass} btn btn-info"
   style="${style}">${text}</a> --}%
<a href="" class="btn btn-default " data-toggle="modal" data-target="#${filterPaneId}">
	<i class="fa fa-search"></i> ${text}
</a> 