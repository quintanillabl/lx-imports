<%@ page import="com.luxsoft.impapx.Embarque" %>
<!doctype html>
<html>
	<head>
		<g:set var="entityName" value="${message(code: 'embarque.label', default: 'Embarque')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
		<asset:stylesheet src="datatables/dataTables.css"/>
		<asset:javascript src="datatables/dataTables.js"/> 
		<asset:javascript src="forms/forms.js"/>
		<style type="text/css" media="screen">
			table td{
				font-size: 12px; 
				white-space: nowrap;
				overflow: hidden;
				text-overflow:ellipsis;
			}
		</style>
	</head>
	<body>
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<div class="page-header">
						<h1>Embarque: ${embarqueInstance.id} <small>${embarqueInstance}</small></h1>
						<g:if test="${flash.message}">
						  	<span class="label label-warning">${flash.message}</span>
						</g:if>
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="col-md-12">
					<ul class="nav nav-tabs" role="tablist">
						<li class="">
							<g:link action="index" >
							    <i class="fa fa-step-backward"></i> Embarques
							</g:link> 
						</li>
					   <li class=""><a href="#embarque" data-toggle="tab">Propiedades</a></li>
					   <li class="active">
					   		<a data-target="#plist" data-toggle="tab">P.List</a>
					   	</li>
					   <li class=""><a href="#costos" data-toggle="tab">Costos</a></li>
					   <li class=""><a href="#con" data-toggle="tab">Contenedores</a></li>
					</ul>
			  		<div class="tab-content"> <!-- Tab Content -->
			  						
						<div class="tab-pane " id="embarque">
							<g:if test="${embarqueInstance.facturado<=0}">
								<g:render template="editForm" bean="${embarqueInstance}"/>
							</g:if>
						</div> 
						
						<div class="tab-pane active" id="plist">
							<g:set var="partidas" value="${embarqueInstance.partidas}"/>
							<g:render template="partidas" 
								model="['embarqueInstance':embarqueInstance,'facturaGastos':facturaGastos]"/>
						</div> 
						
						<div class="tab-pane" id="costos">
							<g:render template="costosPanel"/>
						</div>
						
						<div class="tab-pane" id="con">
							<g:if test="${embarqueInstance.facturado<=0}">
								<g:render template="contenedoresPanel" bean="${embarqueInstance}"/>
							</g:if>
							
						</div>
			  							
			  		</div>	<!-- End Tab Content -->
				</div>
			</div>
		</div>
	
	<script type="text/javascript">
		$(function(){

			
			//$(".entero").autoNumeric({wEmpty:'zero',lZero: 'deny',vMin:'0'});
			//$(".entero").forceNumeric();
			//$(".entero").autoNumeric('init',{vMin: '0', lZero: 'deny',aSep: ''});  //autoNumeric with defaults
			// $("input[data-moneda]").autoNumeric({wEmpty:'zero',mRound:'B',aSign: '$'});
			// $("input[data-porcentaje]").autoNumeric({altDec: '%', vMax: '99.99'});
		});
	</script>
	</body>
</html>
	