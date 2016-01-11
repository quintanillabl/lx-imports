<g:set var="importacionesControllers" 
    value="${['compra','embarque','pedimento','cuentaDeGastos','distribucion']}" />
<li class="${importacionesControllers.contains(webRequest.controllerName)?'active':''}">
    <a href="#"><i class="fa fa-plane"></i> <span class="nav-label">Importaciones</span><span class="fa arrow"></span></a>
    <sec:ifAnyGranted roles="COMPRAS,ADMIN">
        <ul class="nav nav-second-level collapse">
        	<li class="${webRequest.controllerName=='compra'?'active':''}">
        	    <g:link controller="compra"><i class="fa fa-shopping-cart"></i> <span class="nav-label">Compras</span></g:link>
        	</li>
            <li class="${webRequest.controllerName=='embarque'?'active':''}" >
            	<g:link controller="embarque"> <i class="fa fa-ship"></i> Embarques</g:link>
            </li>

            <li class="${webRequest.controllerName=='pedimento'?'active':''}" >
            	<g:link controller="pedimento"><i class="fa fa-list-ol"></i> Pedimentos</g:link>
            </li>
            <li class="${webRequest.controllerName=='cuentaDeGastos'?'active':''}" >
            	<g:link controller="cuentaDeGastos"> <i class="fa fa-stack-overflow"></i> Cuentas de Gasto</g:link>
            </li>
            <li class="${webRequest.controllerName=='distribucion'?'active':''}">
                <g:link controller="distribucion"><i class="fa fa-random"></i> <span class="nav-label">Distribución</span></g:link>
            </li>
        </ul>
    </sec:ifAnyGranted>
</li>