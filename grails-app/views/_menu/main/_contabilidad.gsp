
<g:set var="contabilidadControllers" 
    value="${['cuentaContable','saldoPorCuentaContable','polizaDeCompras','polizaDeEgresos','polizaDeDiario','polizaDeDiarioFlete','polizaDeDiarioIvaIsr','polizaDeDiarioAplicacionAnticipo','polizaDeIngresos','polizaDeImpuestos','poliza','polizaDeProvisionAnual','polizaDeCierreAnual','diot','procesadorDePoliza','balanza']}" />

<g:set var="polizaControllers" 
    value="${['poliza','procesadorDePoliza','polizaDeCompras','polizaDeEgresos','polizaDeDiario','polizaDeDiarioFlete','polizaDeDiarioIvaIsr','polizaDeDiarioAplicacionAnticipo','polizaDeIngresos','polizaDeImpuestos','poliza','polizaDeProvisionAnual','polizaDeCierreAnual','balanza']}" />


<li class="${contabilidadControllers.contains(webRequest.controllerName)?'active':''}">
    <a href="#"><i class="fa fa-calculator"></i>
        <span class="nav-label">Contabilidad</span><span class="fa arrow"></span>
    </a>
    <ul class="nav nav-second-level collapse">
        <sec:ifAnyGranted roles="CONTABILIDAD,ADMIN">
            
            <li class="${webRequest.controllerName=='cuentaContable'?'active':''}" >
                <g:link controller="cuentaContable">Cuentas</g:link>
            </li>

            <li class="${webRequest.controllerName=='procesadorDePoliza'?'active':''}" >
                <g:link controller="procesadorDePoliza">Procesadores</g:link>
            </li>

            <li class="${webRequest.controllerName=='saldoPorCuentaContable'?'active':''}" >
                <g:link controller="saldoPorCuentaContable">Saldos</g:link>
            </li>

            <li class="${webRequest.controllerName=='balanza'?'active':''}" >
                <g:link controller="balanza">Balanza</g:link>
            </li>

            <li class="${webRequest.controllerName=='diot'?'active':''}" >
                <g:link controller="diot">DIOT</g:link>
            </li>
            <li class="${polizaControllers.contains(webRequest.controllerName)?'active':''}">
                <a href="#">Pólizas <span class="fa arrow"></span></a>
                <ul class="nav nav-third-level">

                    <li class="${subTipo=='TODAS'?'active':''}">
                        <g:link controller="poliza">Todas</g:link>
                    </li>
                    <li class="${subTipo=='COBRANZA'?'active':''}">
                        <g:link controller="poliza" params="[subTipo:'COBRANZA']">Cobranza</g:link>
                    </li>
                    <li class="${subTipo=='ING_VARIOS'?'active':''}">
                        <g:link controller="poliza" params="[subTipo:'ING_VARIOS']">Ingreso (varios)</g:link>
                    </li>
                    <li class="${subTipo=='PAGO'?'active':''}">
                        <g:link controller="poliza" params="[subTipo:'PAGO']">Egresos</g:link>
                    </li>

                    <g:each in="${com.luxsoft.lx.contabilidad.ProcesadorDePoliza.where{tipo=='DIARIO'}.list().sort{it.orden}}" status="i" var="row">
                        <li class="${subTipo==row.subTipo?'active':''}">
                            <g:link controller="poliza" action="index" params="[subTipo:row.subTipo]">${row.label}</g:link>
                        </li>
                    </g:each>
                    
                    <li class="${webRequest.controllerName=='polizaDeCierreAnual'?'active':''}">
                        <g:link controller="polizaDeCierreAnual">Cierre anual</g:link>
                    </li>

                </ul>
            </li>

            
           

            
        </sec:ifAnyGranted>
    </ul>
</li>
