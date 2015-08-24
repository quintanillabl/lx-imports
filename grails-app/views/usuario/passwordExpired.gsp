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

				
				<div class="col-md-offset-3 col-md-6">
					<div class="">
						<h2 class="font-bold"><span class="text-navy">LX-IMPORTS </span></h2>
						<p>Su contraseña a caducado favor de actualizarla</p>
					</div>
					<div class="ibox-content">
						<lx:errorsHeader bean="${command}"/>
						<g:if test="${flash.message}">
							<div class="alert alert-danger">
										${flash.message}
							</div>
						</g:if>
							<form class="form-horizontal" role="form" action="updatePassword" autocomplete='off'>
		                        <div class="form-panel">

		                        	<f:with bean="${command}">
		                        		<g:hiddenField name="username" value="${command.username}"/>
		                        		<f:display property="username" wrapper="bootstrap3" label="Usuario" />
		                        		<f:field property="currentPassword" wrapper="bootstrap3" label="Pasword actual"
		                        			widget-class="form-control" widget-type="password" widget-autocomplete="off"/>
		                        		<f:field property="password" wrapper="bootstrap3"
		                        			widget-class="form-control" widget-type="password" widget-autocomplete="off"/>
		                        		<f:field property="confirmarPassword" label="Confirmar"  wrapper="bootstrap3"
		                        			widget-class="form-control" widget-type="password"  widget-autocomplete="off"/>
		                        	</f:with>
		                        	%{-- <div class="form-group">
		                            	<div class="col-sm-offset-10 col-sm-2">
		                              		<button type="submit" class="btn btn-primary">
		                              			<span class="glyphicon glyphicon-floppy-save"></span> Salvar
		                              		</button>
		                            	</div>
		                          	</div> --}%
		                          	<button type="submit" class="btn btn-primary block full-width m-b">Login</button>
		                        	<p class="m-t">
		                        	    <small>Lx-Imports basado on Luxor3 &copy; 2015</small>
		                        	</p>
		                        </div>
							</form>
	                   
					</div>
				</div>
				
			</div>
			<div class="row">
	            <div class="col-md-offset-3 col-md-6">
	                Copyright Luxoft Mx
	            </div>
	            <div class="col-md-offset-3 col-md-6 text-right">
	               <small>© 2014-2015</small>
	            </div>
			</div>
		</div>
	
	%{-- <div class="loginColumns animated fadeInDown">

		<div class="row">
			<div class="col-md-8 col-md-offset-2">
				<h2 class="font-bold"><span class="text-navy">LX-IMPORTS </span></h2>
				<p>Su contraseña a caducado favor de actualizarla</p>
			</div>
		</div>

		<div class="row ">
			<div class="col-md-8 col-md-offset-2 ">
				<div class="ibox-content">
					<g:if test="${flash.message}">
						<div class="alert alert-danger">
							${flash.message}
						</div>
					</g:if>
					
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
	 --}%

</body>
</html>
