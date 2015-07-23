
<nav class="navbar navbar-default navbar-fixed-bottom navbar-inverse" role="navigation">
	<div class="container">
		
		<sec:ifLoggedIn>
			
			<a class="navbar-brand" href="#empresaDialog" >
				<strong>
				Empresa: "${session.empresa}"
				</strong>
			</a>
			<p class="navbar-text navbar-left"> 
			
			</p>
			<p class="navbar-text navbar-right">Usuario: 
				<a href="#" class="navbar-link">
					<sec:loggedInUserInfo field="username"/>
				</a>
			</p>
		</sec:ifLoggedIn>
		<sec:ifNotLoggedIn>
			<p class="navbar-text navbar-left"> Ingreso al sistema</p>
		</sec:ifNotLoggedIn>
	</div>
</nav>

