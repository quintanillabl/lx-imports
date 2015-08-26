<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>Partida de embarque ${embarqueDetInstance.id}</title>
	<asset:javascript src="forms/forms.js"/>

</head>
<body>

	<div class="container">
		
		<div class="row">

			<div class="col-md-12">

				<div class="page-header">
				  	<g:if test="${flash.message}">
				  		<small><span class="label label-warning ">${flash.message}</span></small>
				  	</g:if> 
				  	<g:if test="${flash.error}">
				  		<small><span class="label label-danger ">${flash.error}</span></small>
				  	</g:if> 
				  </h3>
				</div>
			</div>
		</div><!-- end .row -->

		<div class="row ">
			<div class="col-md-8 col-md-offset-2">
				<g:render template="editForm"/>
			</div>
		</div> <!-- end .row 2 -->

	</div>
	
	
</body>
</html>