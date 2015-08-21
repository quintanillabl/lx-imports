
<g:set var="sistemasMenu" value="${['usuario']}" />

<li class="${sistemasMenu.contains(webRequest.controllerName)?'active':''}">
    <a href="#"><i class="fa fa-database"></i>
        <span class="nav-label"> Sistemas </span><span class="fa arrow"></span>
    </a>
    <ul class="nav nav-second-level collapse">
        <sec:ifAnyGranted roles="ADMIN">
            <li class="${webRequest.controllerName=='usuario'?'active':''}" >
                <g:link controller="usuario">
                    <i class="fa fa-users"></i> Usuarios
                </g:link>
            </li>
        </sec:ifAnyGranted>
    </ul>
</li>
