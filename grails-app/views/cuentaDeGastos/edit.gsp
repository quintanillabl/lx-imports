<%@ page import="com.luxsoft.impapx.CuentaDeGastos" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="luxor">
	<title>Cuenta de gastos ${cuentaDeGastosInstance.id}</title>
	<asset:javascript src="forms/forms.js"/>
</head>
<body>

<content tag="header"> Cuenta: ${cuentaDeGastosInstance.id}   Embarque: ${cuentaDeGastosInstance.embarque.nombre} BL:${cuentaDeGastosInstance.embarque.bl}</content>
<content tag="subHeader">
	<ol class="breadcrumb">
    	<li><g:link action="index">Cuentas</g:link></li>
    	<li class="active"><strong>Edición</strong></li>
	</ol>
</content>

<content tag="document">
	<div class="ibox float-e-margins">
		<lx:iboxTitle title="Cuenta de gastos"/>
	    <div class="ibox-content">

			<ul class="nav nav-tabs" role="tablist">
				<li class="active"><a href="#facturas" data-toggle="tab"><i class="fa fa-th-list"> </i> Facturas</a></li>
			   <li class=""><a href="#cuenta" data-toggle="tab"><i class="fa fa-pencil"></i>  Propiedades</a></li>
			</ul>
	  		<div class="tab-content"> <!-- Tab Content -->
				<div class="tab-pane " id="cuenta">
					<br>
					<g:render template="editForm" bean="${cuentaDeGastosInstance}"/>
				</div>
				
				<div class="tab-pane active" id="facturas">
					<br>
					<g:render template="facturasPanel" bean="${cuentaDeGastosInstance}"/>
				</div>			
	  		</div>	<!-- End Tab Content -->
	    </div>
	</div>
</content>

	
</body>
</html>
