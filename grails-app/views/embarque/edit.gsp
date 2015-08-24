<%@ page import="com.luxsoft.impapx.Embarque" %>
<!doctype html>
<html>
<head>
	<title>Embarque ${embarqueInstance.id}</title>
	<meta name="layout" content="luxor">
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

<content tag="header"> Embarque: ${embarqueInstance.id}  BL: ${embarqueInstance.bl} (${embarqueInstance.nombre})</content>
<content tag="subHeader">
	<ol class="breadcrumb">
    	<li><g:link action="index">Embarques</g:link></li>
    	<li class="active"><strong>Edición</strong></li>
	</ol>
</content>

<content tag="document">

	<div class="ibox float-e-margins">
		<lx:iboxTitle title="Lista de empaque"/>
	    <div class="ibox-content">
			<ul class="nav nav-tabs" role="tablist">
			   <li class="active">
			   		<a data-target="#plist" data-toggle="tab"> <i class="fa fa-th-list"></i> Lista de empaque</a>
			   	</li>
			   <li class=""><a href="#costos" data-toggle="tab">  <i class="fa fa-money"></i>  Costos</a></li>
			   <li class=""><a href="#con" data-toggle="tab">  <i class="fa fa-cubes"></i> Contenedores</a></li>
			   <li class=""><a href="#embarque" data-toggle="tab"> <i class="fa fa-pencil"></i>  Propiedades</a></li>
			</ul>
		  		<div class="tab-content"> <!-- Tab Content -->
		  					
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

					<div class="tab-pane " id="embarque">
						<g:if test="${embarqueInstance.facturado<=0}">
							<g:render template="editForm" bean="${embarqueInstance}"/>
						</g:if>
					</div> 
					
					%{-- 
					
					
					
					<div class="tab-pane" id="con">
						<g:if test="${embarqueInstance.facturado<=0}">
							<g:render template="contenedoresPanel" bean="${embarqueInstance}"/>
						</g:if>
						
					</div> --}%
		  							
		  		</div>	<!-- End Tab Content -->
	    </div>
	</div>
	
	
	

</content>

	%{-- <div class="container">
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
	</div> --}%

<script type="text/javascript">
	$(function(){
	});
</script>
</body>
</html>
	