<li>
    <a href="#"><i class="fa fa-plane"></i> <span class="nav-label">Importaciones</span><span class="fa arrow"></span></a>
    <sec:ifAnyGranted roles="COMPRAS,ADMIN">
        <ul class="nav nav-second-level collapse">
            <li><g:link controller="embarque"> <i class="fa fa-ship"></i> Embarques</g:link></li>
            <li><g:link controller="pedimento"><i class="fa fa-list-ol"></i> Pedimentos</g:link></li>
            <li><g:link controller="cuentaDeGastos"> <i class="fa fa-stack-overflow"></i> Cuentas de Gasto</g:link></li>
        </ul>
    </sec:ifAnyGranted>
</li>