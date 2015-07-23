<html>
<head>
	
	<title><g:message code="springSecurity.login.title"/></title>
	
</head>

<body>
	<div class="container">
		<div class="row">
			<div class="jumbotron">
			  <h1> <g:message code="application.label" /></h1>
			   <h2><g:message code="application.description" /></h2>
			</div>
			
		</div>
		

		<g:if test="${flash.message}">
			<div class="row">
				<div class="col-md-6 col-sm-offset-3">
					<div class="alert alert-danger">
						${flash.message}
					</div>
				</div>
			</div> <!-- end .row 2 -->
		</g:if>

		<div class="row">
			<div class="col-md-6 col-sm-offset-3">
				<div class="panel panel-primary">
					<div class="panel-heading">Acceso al sistema</div>
					<div class="panel-body">
				    	<form action='${postUrl}' method='POST' id='loginForm' class='form-horizontal' autocomplete='off'>
				    		
				    		<div class="form-group">
				    			<label for="username" class="col-sm-3 control-label">
				    				<g:message code="springSecurity.login.username.label"/>
				    			</label>
				    			<div class="col-sm-9">
				    				<input type="text" class="form-control" id="usename" name="j_username" placeholder="Usuario">
				    			</div>
				    		</div>
				    		
				    		<div class="form-group">
				    			<label for="password" class="col-sm-3 control-label">
				    				<g:message code="springSecurity.login.password.label"/>
				    			</label>
				    			<div class="col-sm-9">
				    				<input type="password" class="form-control" id="password" name="j_password" placeholder="password">
				    			</div>
				    		</div>

				    		

				    		<div class="form-group">
				    			<div class="col-sm-offset-3 col-sm-9">
				    				<div class="checkbox">
				    					<label >
				    						<input type='checkbox' name='${rememberMeParameter}' id='remember_me'
				    						 <g:if test='${hasCookie}'>checked='checked'</g:if> />
				    						 <strong><g:message code="springSecurity.login.remember.me.label"/></strong>
				    					</label>
				    				</div>
				    			</div>
				    		</div>

				    		<div class="form-group">
				    			<div class="col-sm-offset-3 col-sm-4">
				    				<input 
				    					class="btn btn-success btn-lg btn-block"
				    					type='submit' 
				    					id="submit" 
				    					value='${message(code: "springSecurity.login.button")}'/>
				    			</div>
				    		</div>

				    		
				    	</form>
					</div>
				</div>
					
				
			</div>
		</div>

	</div>


<script type='text/javascript'>
	<!--
	(function() {
		document.forms['loginForm'].elements['j_username'].focus();
	})();
	// -->
</script>
</body>
</html>
