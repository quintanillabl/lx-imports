<li class="dropdown">
	<a class="dropdown-toggle" data-toggle="dropdown" href="#">
		<i class="fa fa-bars"></i>
		Catálogos <b class="caret"></b>
	</a>
	<sec:ifAnyGranted roles="COMPRAS,ADMIN">
		<nav:menu id="nav"  scope="user/catalogos" custom="true" class="dropdown-menu">
			<li class="${active ? 'active' : ''}">
				<p:callTag tag="g:link" attrs="${linkArgs}">
				   <i class="${item.data.icon ?:''}"></i>&nbsp;<nav:title item="${item}"/>
				</p:callTag>
			</li>
		</nav:menu> 
	</sec:ifAnyGranted>
	
</li>
