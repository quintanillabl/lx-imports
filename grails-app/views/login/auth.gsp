<html>
<head>

	<meta name="layout" content="blank" />
	<title><g:message code="springSecurity.login.title"/></title>
	<style type="text/css" media="screen">
		body{
			background-color: #f3f3f4;
		}
	</style>
</head>

<body class="gray-bg" style="background-color: #f3f3f4;">
	
	<div class="loginColumns animated fadeInDown">
		<div class="row ">
			<div class="col-md-6">
				<h2 class="font-bold">Bienvenido a <span class="text-navy">LX-IMPORTS </span></h2>
				<p>
				   Sistema de administración para todo el ciclo de importación y comerciailización de bienes y servicios                
				</p>
				<p><small>Control inteligente para todo el ciclo de la compercialización</small>
				</p>
			</div>
			
			<div class="col-md-6">
				<div class="ibox-content">
					<g:if test="${flash.message}">
						<div class="alert alert-danger">
									${flash.message}
						</div>
					</g:if>
					<form class="m-t" role="form" 
						action='${postUrl}' method='POST' id='loginForm' autocomplete='off'>
                        <div class="form-group">
                        	<input type="text" class="form-control" id="usename" 
                        	name="j_username" placeholder="Usuario" autocomplete="off">
                        </div>
                        <div class="form-group">
                            <input type="password" class="form-control" id="password" name="j_password" placeholder="password">
                        </div>
                        <button type="submit" class="btn btn-primary block full-width m-b">Login</button>
                        
					</form>
                    <p class="m-t">
                        <small>Lx-Imports basado on Luxor3 &copy; 2015</small>
                    </p>
				</div>
			</div>
			
		</div>
		<div class="row">
            <div class="col-md-6">
                Copyright Luxoft Mx
            </div>
            <div class="col-md-6 text-right">
               <small>© 2014-2015</small>
            </div>
		</div>
	</div>
	

</body>
</html>
