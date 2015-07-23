<li class="dropdown">
	<a class="dropdown-toggle" data-toggle="dropdown" href="#">
		<i class="fa fa-calculator"></i>
		Contabilidad <b class="caret"></b>
	</a>
	<sec:ifAnyGranted roles="COMPRAS,ADMIN">
		<nav:menu id="nav"  scope="user/contabilidad"  custom="true" class="dropdown-menu">
			<li class="${active ? 'active' : ''}">
				<p:callTag tag="g:link" attrs="${linkArgs}">
				   <i class="${item.data.icon ?:''}"></i>&nbsp;<nav:title item="${item}"/>
				</p:callTag>
			</li>
		</nav:menu> 
	</sec:ifAnyGranted>
</li>
%{-- <ul class="dropdown-menu">
	<li><g:link controller="cuentaContable" action="list">Cuentas</g:link></li>
	<li><g:link controller="saldoPorCuentaContable" action="list">Saldos</g:link></li>
	<li><g:link controller="certificado" action="list">Certificados</g:link></li>
	<li><g:link controller="folioFiscal" action="list">Folios fiscales</g:link></li>
	<li><g:link controller="cfdiFolio" action="list">Folios CFDI</g:link></li>
	<li><g:link controller="empresa" action="list">Empresa</g:link></li>
	<li><g:link controller="comprobanteFiscal" action="list">Reporte mensual CFD</g:link></li>
	<li><g:link controller="diferenciaCambiaria" action="list">Diferencias cambiarias</g:link></li>
	<li><g:link controller="poliza" action="index">Pólizas</g:link></li>
	<li><g:link controller="poliza" action="index">Balanza</g:link></li>
	<li><g:link controller="diot" action="list">DIOT</g:link></li>
</ul> --}%