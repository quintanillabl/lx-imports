<li class="dropdown">
	<a class="dropdown-toggle" data-toggle="dropdown" href="#">
		<i class="fa fa-cog"></i>
		Operaciones <b class="caret"></b>
	</a>
	<sec:ifAnyGranted roles="COMPRAS,ADMIN">
		<nav:menu id="nav"  scope="user/operaciones" custom="true" class="dropdown-menu" depth="2" forceChildren="true">
			<li class="${active ? 'active' : ''}">
				<p:callTag tag="g:link" attrs="${linkArgs}">
				   <i class="${item.data.icon ?:''}"></i>&nbsp;<nav:title item="${item}"/>
				</p:callTag>
			</li>
			%{-- <g:if test="${item.children}">
				<li class="dropdown">
					<a class="dropdown-toggle" data-toggle="dropdown" href="#">
						<nav:title item="${item}"/> <b class="caret"></b>
					</a>
				</li>
			</g:if>
			<g:else>
				<li class="${active ? 'active' : ''}">
					<p:callTag tag="g:link" attrs="${linkArgs}">
					   <i class="${item.data.icon ?:''}"></i>&nbsp;<nav:title item="${item}"/>
					</p:callTag>
				</li>
			</g:else> --}%
			
		</nav:menu> 
	</sec:ifAnyGranted>
</li>

