<li class="dropdown">
	<a class="dropdown-toggle" data-toggle="dropdown" href="#">
		<i class="fa fa-money"></i>
		Tesoreria <b class="caret"></b>
	</a>
	<sec:ifAnyGranted roles="TESORERIA,ADMIN">
		<nav:menu id="nav"  scope="user/tesoreria" custom="true" class="dropdown-menu">
			<li class="${active ? 'active' : ''}">
				<p:callTag tag="g:link" attrs="${linkArgs}">
				   <i class="${item.data.icon ?:''}"></i>&nbsp;<nav:title item="${item}"/>
				</p:callTag>
			</li>
		</nav:menu> 
	</sec:ifAnyGranted>

	%{-- <ul class="dropdown-menu">
		<li><g:link controller="banco" action="list">Bancos</g:link></li>
		<li><g:link controller="proveedor" action="list">Proveedores</g:link></li>
		<li><g:link controller="cuentaPorPagar" action="index">C x P</g:link></li>
		<li><g:link controller="cuentaBancaria" action="list">Cuentas Bancarias</g:link></li>
		<li><g:link controller="movimientoDeCuenta" action="list">Operaciones</g:link></li>
	</ul> --}%
	
</li>
