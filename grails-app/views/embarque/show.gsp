
<%@ page import="com.luxsoft.impapx.Embarque" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<g:set var="entityName" value="${message(code: 'embarque.label', default: 'Embarque')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="container">
			
			<div class="row row-header">
				<div class="col-md-8 col-sm-offset-2 toolbar-panel">
					<div class="btn-group">
					    <lx:backButton/>
					    <lx:createButton/>
					    <lx:editButton id="${embarqueInstance?.id}"/>
					    <lx:printButton/>
					</div>
				</div>
			</div> <!-- End .row 1 -->
			<div class="row">
			    <div class="col-md-8 col-sm-offset-2">
					<div class="panel panel-primary">
						<div class="panel-heading"> ${entityName}: ${embarqueInstance} </div>
						<g:if test="${flash.message}">
							<small><span class="label label-info ">${flash.message}</span></small>
						</g:if> 
					  	<div class="panel-body">
					  		<g:form class="form-horizontal">
					  			
					  					
					  			<f:display property="bl" bean="embarqueInstance"/>
					  					
					  			
					  					
					  			<f:display property="nombre" bean="embarqueInstance"/>
					  					
					  			
					  					
					  			<f:display property="fechaEmbarque" bean="embarqueInstance"/>
					  					
					  			
					  					
					  			<f:display property="proveedor" bean="embarqueInstance"/>
					  					
					  			
					  					
					  			<f:display property="ingresoAduana" bean="embarqueInstance"/>
					  					
					  			
					  					
					  			<f:display property="contenedores" bean="embarqueInstance"/>
					  					
					  			
					  					
					  			<f:display property="comentario" bean="embarqueInstance"/>
					  					
					  			
					  					
					  			<f:display property="moneda" bean="embarqueInstance"/>
					  					
					  			
					  					
					  			<f:display property="tc" bean="embarqueInstance"/>
					  					
					  			
					  					
					  			<f:display property="liberado" bean="embarqueInstance"/>
					  					
					  			
					  					
					  			<f:display property="aduana" bean="embarqueInstance"/>
					  					
					  			
					  					
					  			<f:display property="cuentaDeGastos" bean="embarqueInstance"/>
					  					
					  			
					  					
								<f:display property="dateCreated" bean="embarqueInstance" widget="datetime"/>
					  					
					  			
					  					
					  			<f:display property="facturado" bean="embarqueInstance"/>
					  					
					  			
					  					
					  			<f:display property="lastUpdated" bean="embarqueInstance"/>
					  					
					  			
					  					
					  			<f:display property="partidas" bean="embarqueInstance"/>
					  					
					  			
					  					
					  			<f:display property="valor" bean="embarqueInstance"/>
					  					
					  			
					  		</g:form>
					  </div>
					</div>
			    </div>
			</div><!-- End .row 2 -->

		</div><!-- End container -->

	</body>
</html>


