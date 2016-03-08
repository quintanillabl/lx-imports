
<g:set var="contabilidadControllers" 
    value="${['cuentaContable','saldoPorCuentaContable','polizaDeCompras','polizaDeEgresos','polizaDeDiario','polizaDeDiarioFlete','polizaDeDiarioIvaIsr','polizaDeDiarioAplicacionAnticipo','polizaDeIngresos','polizaDeImpuestos','poliza','polizaDeProvisionAnual','polizaDeCierreAnual','diot','cuentaSat','procesadorDePoliza','balanza']}" />

<g:set var="polizaControllers" 
    value="${['poliza','procesadorDePoliza','polizaDeCompras','polizaDeEgresos','polizaDeDiario','polizaDeDiarioFlete','polizaDeDiarioIvaIsr','polizaDeDiarioAplicacionAnticipo','polizaDeIngresos','polizaDeImpuestos','poliza','polizaDeProvisionAnual','polizaDeCierreAnual','balanza']}" />

<g:set var="satControllers" 
    value="${['cuentaSat']}" />

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
                    <li class="${subTipo=='PAGO'?'active':''}">
                        <g:link controller="poliza" params="[subTipo:'PAGO']">Egresos</g:link>
                    </li>

                    <g:each in="${com.luxsoft.lx.contabilidad.ProcesadorDePoliza.where{tipo=='DIARIO'}}" status="i" var="row">
                        <li class="${subTipo==row.subTipo?'active':''}">
                            <g:link controller="poliza" action="index" params="[subTipo:row.subTipo]">${row.label}</g:link>
                        </li>
                    </g:each>
                    
                    <li class="${webRequest.controllerName=='polizaDeCierreAnual'?'active':''}">
                        <g:link controller="polizaDeCierreAnual">Cierre anual</g:link>
                    </li>

                </ul>
            </li>

            %{-- <li >  
                <a href="#">Pólizas (OLD)<span class="fa arrow"></span></a>
                <ul class="nav nav-third-level">
                    
                    <li class="${webRequest.controllerName=='polizaDeCompras'?'active':''}">
                        <g:link controller="polizaDeCompras" >Compras</g:link>
                    </li>

                    <li class="${webRequest.controllerName=='polizaDeEgresos'?'active':''}">
                        <g:link controller="polizaDeEgresos">Egreso</g:link>
                    </li>

                    <li class="${webRequest.controllerName=='polizaDeDiario'?'active':''}">
                        <g:link controller="polizaDeDiario">Diario</g:link>
                    </li>

                    <li class="${webRequest.controllerName=='polizaDeDiarioFlete'?'active':''}">
                        <g:link controller="polizaDeDiarioFlete">Diario - Flete</g:link></li>
                    
                    <li class="${webRequest.controllerName=='polizaDeDiarioIvaIsr'?'active':''}">

                        <g:link controller="polizaDeDiarioIvaIsr">Diario - IVA/ISR</g:link></li>
                    
                    <li class="${webRequest.controllerName=='polizaDeDiarioAplicacionAnticipo'?'active':''}">
                        <g:link controller="polizaDeDiarioAplicacionAnticipo">Diario - Aplic</g:link></li>
                    
                    <li class="${webRequest.controllerName=='polizaDeIngresos'?'active':''}">
                        <g:link controller="polizaDeIngresos">Ingresos</g:link></li>
                    
                    <li class="${webRequest.controllerName=='polizaDeImpuestos'?'active':''}">
                        <g:link controller="polizaDeImpuestos">Impuestos</g:link></li>
                    
                    <li class="${webRequest.controllerName=='poliza'?'active':''}">
                        <g:link controller="poliza" params="[subTipo:'GENERICAS']">Genérica</g:link></li>
                    
                    <li class="${webRequest.controllerName=='polizaDeProvisionAnual'?'active':''}">
                        <g:link controller="polizaDeProvisionAnual">Prov Anual (Compras)</g:link></li>
                    
                    <li class="${webRequest.controllerName=='polizaDeCierreAnual'?'active':''}">
                        <g:link controller="polizaDeCierreAnual">Cierre anual</g:link></li>

                </ul>
            </li> --}%

            <li class="${satControllers.contains(webRequest.controllerName)?'active':''}">
                <a href="#">Contabilidad SAT <span class="fa arrow"></span></a>
                <ul class="nav nav-third-level">
                    <li class="${webRequest.controllerName=='cuentaSat'?'active':''}">
                        <g:link controller="cuentaSat">Cuentas SAT</g:link>
                    </li>

                    <li class="${webRequest.controllerName=='econta'?'active':''}">
                        <g:link controller="econta">Catálogo XML</g:link>
                    </li>

                    <li class="${webRequest.controllerName=='polizaDeEgresos'?'active':''}">
                        <g:link controller="polizaDeEgresos">Balnazas</g:link>
                    </li>
                </ul>
            </li>
        </sec:ifAnyGranted>
    </ul>
</li>
