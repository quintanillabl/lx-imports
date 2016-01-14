
<g:set var="cxcMenu" value="${['cuentasPorCobrar','CXCPago','CXCNota']}" />

<li class="${cxcMenu.contains(webRequest.controllerName)?'active':''}">
    <a href="#"><i class="fa fa-money"></i>
        <span class="nav-label">Cuentas por cobrar</span><span class="fa arrow"></span>
    </a>
    <ul class="nav nav-second-level collapse">
        <sec:ifAnyGranted roles="COMPRAS,VENTAS">
            
            <li class="${webRequest.controllerName=='CXCPago'?'active':''}" >
                <g:link controller="CXCPago">Pagos</g:link>
            </li>
            
            <li class="${webRequest.controllerName=='CXCNota'?'active':''}" >
                <g:link controller="CXCNota">Nota de Crédito</g:link>
            </li>
            
            
        </sec:ifAnyGranted>
    </ul>
</li>
