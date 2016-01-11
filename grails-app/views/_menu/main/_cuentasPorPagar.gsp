
<g:set var="cxpMenu" 
    value="${['facturaDeImportacion','gastosDeImportacion','pago','requisicion','notaDeCredito','facturaDeImportacion','facturaDeGastos','cuentaDeGastosGenerica','cuentaPorPagar']}" />

<li class="${cxpMenu.contains(webRequest.controllerName)?'active':''}">
    <a href="#"><i class="fa fa-calendar"></i>
        <span class="nav-label">Cuentas por pagar</span><span class="fa arrow"></span>
    </a>
    <ul class="nav nav-second-level collapse">
        <sec:ifAnyGranted roles="COMPRAS,ADMIN">

            %{-- <li class="${webRequest.controllerName=='cuentaPorPagar'?'active':''}" >
                <g:link controller="cuentaPorPagar">Facturas (Todas)</g:link>
            </li> --}%
            
            <li class="${webRequest.controllerName=='facturaDeImportacion'?'active':''}" >
                <g:link controller="facturaDeImportacion">Facturas de Importaciones </g:link>
            </li>
            <li class="${webRequest.controllerName=='gastosDeImportacion'?'active':''}">
                <g:link controller="gastosDeImportacion" >Gastos de Importación</g:link>
            </li>
            <li class="${webRequest.controllerName=='pago'?'active':''}" >
                <g:link  controller="pago" >Pagos</g:link>
            </li>
            <li class="${webRequest.controllerName=='notaDeCredito'?'active':''}">
                <g:link controller="notaDeCredito">Notas de Crédito</g:link>
            </li>
            %{-- <li>
                <g:link controller="facturaDeImportacion" action="detalleDeFacturas">
                        Detalle de Facturas
                </g:link>
            </li> --}%
        </sec:ifAnyGranted>
        <sec:ifAnyGranted roles="COMPRAS,ADMIN,TESORERIA">
            <li class="${webRequest.controllerName=='facturaDeGastos'?'active':''}" >
                <g:link controller="facturaDeGastos">
                    <g:message code="facturaDeGastos.list.label" default="Otros gastos"/>
                </g:link>
            </li>
            <li class="${webRequest.controllerName=='requisicion'?'active':''}">
                <g:link controller="requisicion">
                    <g:message code="requisicion.list.label" default="Requisiciones"/>
                </g:link>
            </li>
            <li>
                <g:link controller="cuentaDeGastosGenerica" action="list">
                    <g:message code="cuentaDeGastosGenerica.list.label" default="Cuenta de Gastos (Genérica)"/>
                </g:link>
            </li>
            
            
        </sec:ifAnyGranted>
    </ul>
</li>
