<g:set var="consultasControllers" value="${['consultas']}" />

<li class="${consultasControllers.contains(webRequest.controllerName)?'active':''}">
    
    <a href="#"><i class="fa fa-usd"></i> <span class="nav-label">Consultas</span><span class="fa arrow"></span></a>
    
    <ul class="nav nav-second-level collapse">
    	<li class="${webRequest.controllerName=='venta'?'active':''}">
    	    <g:link controller="embarquesBi"><i class="fa fa-money"></i> <span class="nav-label">Embarques</span></g:link>
    	</li>
        <li class="${webRequest.controllerName=='cfdi'?'active':''}">
            <g:link controller="consultas" action="proveedores"><i class="fa fa-file-code-o"></i> <span class="nav-label">Proveedores</span></g:link>
        </li>

    </ul>
    
</li>