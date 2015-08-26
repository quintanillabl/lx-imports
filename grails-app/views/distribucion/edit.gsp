<%@ page import="com.luxsoft.impapx.Distribucion" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="luxor">
	<title>Distribución</title>
	<asset:javascript src="forms/forms.js"/>
</head>

<content tag="header"> Distribución: ${distribucionInstance.id}   Embarque: ${distribucionInstance.embarque.nombre} BL:${distribucionInstance.embarque.bl}</content>
<content tag="subHeader">
	<ol class="breadcrumb">
    	<li><g:link action="index">Distribuciones</g:link></li>
    	<li class="active"><strong>Edición</strong></li>
	</ol>
</content>

<content tag="document">
	<div class="ibox float-e-margins">
		<lx:iboxTitle title="Cuenta de gastos"/>
	    <div class="ibox-content">

			<ul class="nav nav-tabs" id="myTab">
				<li class="active"><a href="#partidas" data-toggle="tab"><i class="fa fa-th-list"></i>  Partidas</a></li>
				<li class=""><a href="#distribucion" data-toggle="tab"><i class="fa fa-pencil"></i>  Propiedades</a></li>
			</ul>

	  		<div class="tab-content"> <!-- Tab Content -->
	  			<div class="tab-pane active" id="partidas">
	  				<br>
	  				<g:render template="partidasPanel" bean="${distribucionInstance}"/>
	  			</div>			
				<div class="tab-pane " id="distribucion">
					<br>
					<g:render template="editForm" bean="${distribucionInstance}"/>
				</div>
				
	  		</div>	<!-- End Tab Content -->
	    </div>
	</div>
</content>

	
</html>
