
<g:set var="satControllers" 
    value="${['satCatalogoLog','satBalanzaLog','cuentaSat']}" />

<li class="${satControllers.contains(webRequest.controllerName)?'active':''}">
    <a href="#"><i class="fa fa-calculator"></i>
        <span class="nav-label">Contabilidad SAT</span><span class="fa arrow"></span>
    </a>
    <ul class="nav nav-second-level collapse">
        <sec:ifAnyGranted roles="CONTABILIDAD,ADMIN">
            
            <li class="${webRequest.controllerName=='cuentaSat'?'active':''}">
                <g:link controller="cuentaSat">Cuentas SAT</g:link>
            </li>

            <li class="${webRequest.controllerName=='satCatalogoLog'?'active':''}">
                <g:link controller="satCatalogoLog">Catálogos</g:link>
            </li>

            <li class="${webRequest.controllerName=='satBalanzaLog'?'active':''}">
                <g:link controller="satBalanzaLog">Balnazas</g:link>
            </li>

            <li class="${webRequest.controllerName=='satPolizasLog'?'active':''}">
                <g:link controller="satPolizasLog">Polizas</g:link>
            </li>
            <li class="${webRequest.controllerName=='satAuxiliaresLog'?'active':''}">
                <g:link controller="satAuxiliaresLog">Auxiliares</g:link>
            </li>
            
        </sec:ifAnyGranted>
    </ul>
</li>
