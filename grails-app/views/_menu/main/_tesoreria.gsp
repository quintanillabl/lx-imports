
<g:set var="tesoreriaControllers" 
    value="${['banco','cuentaBancaria','movimientoDeCuenta','saldoDeCuenta','pagoProveedor','compraDeMoneda','CXCPago','TipoDeCambio']}" />

<li class="${tesoreriaControllers.contains(webRequest.controllerName)?'active':''}">
    <a href="#"><i class="fa fa-calendar"></i>
        <span class="nav-label">Tesorería</span><span class="fa arrow"></span>
    </a>
    <ul class="nav nav-second-level collapse">
        <sec:ifAnyGranted roles="COMPRAS,ADMIN">
            <li class="${webRequest.controllerName=='banco'?'active':''}" >
                <g:link controller="banco">Bancos</g:link>
            </li>
            <li class="${webRequest.controllerName=='cuentaBancaria'?'active':''}">
                <g:link controller="cuentaBancaria" >Cuentas</g:link>
            </li>
            <li class="${webRequest.controllerName=='movimientoDeCuenta'?'active':''}">
                <g:link controller="movimientoDeCuenta" >Movimientos</g:link>
            </li>
            <li class="${webRequest.controllerName=='saldoDeCuenta'?'active':''}">
                <g:link controller="saldoDeCuenta" >Saldos</g:link>
            </li>
            <li class="${webRequest.controllerName=='pagoProveedor'?'active':''}">
                <g:link controller="pagoProveedor" >Pagos</g:link>
            </li>
            <li class="${webRequest.controllerName=='compraDeMoneda'?'active':''}">
                <g:link controller="compraDeMoneda" >Compra de moneda</g:link>
            </li>
            <li class="${webRequest.controllerName=='CXCPago'?'active':''}">
                <g:link controller="CXCPago" >Cobros</g:link>
            </li>
            <li class="${webRequest.controllerName=='TipoDeCambio'?'active':''}">
                <g:link controller="TipoDeCambio" >Tipo de cambio</g:link>
            </li>
        </sec:ifAnyGranted>
    </ul>
</li>
