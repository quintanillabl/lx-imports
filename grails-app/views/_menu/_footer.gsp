
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
			
			<p class="navbar-text navbar-right"> T.C.:
				<a href="#" class="navbar-link">
					<g:if test="${!session.tipoDeCambio}">
						 NO REGISTRADO PARA ${new Date().text()}
					</g:if> 
				</a>
				
			</p>
		</sec:ifLoggedIn>
		<sec:ifNotLoggedIn>
			<p class="navbar-text navbar-left"> Ingreso al sistema</p>
		</sec:ifNotLoggedIn>
	</div>
</nav>

