
<%@ page import="com.luxsoft.impapx.Proveedor" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<g:set var="entityName" value="${message(code: 'proveedor.label', default: 'Proveedor')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="container">
			
			<div class="row row-header">
				<div class="col-md-8 col-sm-offset-2 toolbar-panel">
					<div class="btn-group">
					    <lx:backButton/>
					    <lx:createButton/>
					    <lx:editButton id="${proveedorInstance?.id}"/>
					    <lx:printButton/>
					</div>
				</div>
			</div> <!-- End .row 1 -->
			<div class="row">
			    <div class="col-md-8 col-sm-offset-2">
			    	<div class="panel panel-primary">
			    		<div class="panel-heading"> ${entityName}: ${proveedorInstance} </div>
			    		<g:if test="${flash.message}">
			    			<small><span class="label label-info ">${flash.message}</span></small>
			    		</g:if> 
			    		<ul class="nav nav-tabs" role="tablist">
			    		    <li role="presentation" class="active">
			    		    	<a href="#home" aria-controls="home" role="tab" data-toggle="tab">Propiedades</a>
			    		    </li>
			    		    <li role="presentation">
			    		    	<a href="#productos" aria-controls="productos" data-toggle="tab">Productos</a>
			    		    </li>
			    		</ul>
			    	  	<div class="panel-body">
			    	  		<div class="tab-content">
			    	  		  	<div role="tabpanel" class="tab-pane fade in active" id="home">
			    	  		  		<g:render template="showForm"/>
			    	  		  	</div>
			    	  		 	<div role="tabpanel" class="tab-pane fade" id="productos">
			    	  		  		<g:render template="productos"/>
			    	  		  	</div>
			    	  		  
			    	  		</div>
			    	  		
			    	  </div>
			    	</div>

					

					


					
			    </div>
			</div><!-- End .row 2 -->

		</div><!-- End container -->

	</body>
</html>


