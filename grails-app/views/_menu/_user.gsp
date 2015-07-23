<li class="dropdown">
	<sec:ifLoggedIn>
		<a class="dropdown-toggle" data-toggle="dropdown" href="#">
    		<i class="glyphicon glyphicon-user"></i>
    		<sec:loggedInUserInfo field="username"/><b class="caret"></b>
		</a>
		<ul class="dropdown-menu" role="menu">
			<li>
				<g:form controller="logout" class="navbar-form navbar-left" role="search">
  
  					<button type="submit" class="btn btn-default"> <i class="fa fa-power-off"></i> Cerrar sesión</button>
				</g:form>
				
			</li>
			<sec:ifAllGranted roles="ADMIN">
				
				<li>
					<g:link controller="empresa" action="index"><i class="fa fa-building-o"></i> Empresa</g:link>
				</li>
				<li>
					<g:link controller="usuario" action="index"><i class="fa fa-users"></i> Usuarios</g:link>
				</li>
			</sec:ifAllGranted>
			
		</ul>
	</sec:ifLoggedIn>
</li>
