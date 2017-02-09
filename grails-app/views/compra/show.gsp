
<%@ page import="com.luxsoft.impapx.Compra" %>
<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="luxor">
	<g:set var="entityName" value="${message(code: 'compra.label', default: 'Compra')}" scope="request"/>
	<g:set var="entity" value="${compraInstance}" scope="request" />
	<title>Compra ${entity.id}</title>
</head>

<body>
	
<content tag="header"> Compra Id:${compraInstance.id}</content>
<content tag="subHeader">
	<ol class="breadcrumb">
	    <li><g:link action="index">${entityName}(s)</g:link></li>
	    <li><g:link action="create">Alta</g:link></li>
	    <li class="active"><strong>Consulta</strong></li>
	    <li><g:link action="edit" id="${entity.id}">Edición</g:link></li>
	</ol>
</content>

<content tag="document">

	<div class="wrapper wrapper-content animated fadeInRight">
	    <div class="row">
	        <div class="col-lg-12">
	            <div class="ibox float-e-margins">
	                %{-- <lx:iboxTitle title="Propiedades"/> --}%
	                <div class="ibox-content">
	                	<ul class="nav nav-tabs" role="tablist">
	                		<li class="active"><a href="#compra" data-toggle="tab"> <i class="fa fa-pencil"></i>  Propiedades</a></li>
	                	   <li class="partidas">
	                	   		<a data-target="#partidas" data-toggle="tab"> <i class="fa fa-th-list"></i> Partidas</a>
	                	   	</li>
	                	</ul>
            	  		<div class="tab-content"> 
            	  			<div class="tab-pane active" id="compra">
            	  				<div class="row">
            	  					<div class="col-md-6">
            	  						<br>
            	  						<g:render template="showForm"/>	
            	  					</div>
            	  					
            	  				</div>
            	  				
            				</div> 		
            	  			<div class="tab-pane " id="partidas">
            	  				<br>
            					<g:render template="partidas"/>
            				</div> 		
            	  		</div>
	                </div>
	            </div>
	        </div>
	    </div>

	    
	</div>

</content>
	
	

	

</body>
</html>


