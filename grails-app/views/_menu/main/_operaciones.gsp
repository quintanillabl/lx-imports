<g:set var="operacionesControllers" 
    value="${['distribucion','venta','notaDeCargo','cfdi']}" />
<li class="${operacionesControllers.contains(webRequest.controllerName)?'active':''}">
    <a href="#"><i class="fa fa-usd"></i> <span class="nav-label">Ventas</span><span class="fa arrow"></span></a>
    <sec:ifAnyGranted roles="VENTAS,ADMIN">
        <ul class="nav nav-second-level collapse">
        	%{-- <li class="${webRequest.controllerName=='distribucion'?'active':''}">
        	    <g:link controller="distribucion"><i class="fa fa-random"></i> <span class="nav-label">Distribución</span></g:link>
        	</li> --}%
        	<li class="${webRequest.controllerName=='venta'?'active':''}">
        	    <g:link controller="venta"><i class="fa fa-money"></i> <span class="nav-label">Ventas</span></g:link>
        	</li>
            <li class="${webRequest.controllerName=='notaDeCargo'?'active':''}">
                <g:link controller="notaDeCargo"><i class="fa fa-money"></i> <span class="nav-label">Notas de cargo</span></g:link>
            </li>
            <li class="${webRequest.controllerName=='cfdi'?'active':''}">
                <g:link controller="cfdi"><i class="fa fa-file-code-o"></i> <span class="nav-label">CFDIs</span></g:link>
            </li>
            
        </ul>
    </sec:ifAnyGranted>
</li>