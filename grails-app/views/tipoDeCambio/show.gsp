<!doctype html>
<html>
<head>
	<title>Tipo de cambio</title>
	<meta name="layout" content="luxor">
</head>
<body>

<content tag="header">Alta de tipo de cambio </content>
<content tag="subHeader">
	<ol class="breadcrumb">
    	<li><g:link action="index">Tipos de cambio</g:link></li>
    	<li><g:link action="create">Alta</g:link></li>
    	<li class="active"><strong>Consulta</strong></li>
    	<li><g:link action="edit" id="${tipoDeCambioInstance.id}">Edición</g:link></li>
	</ol>
</content>

<content tag="document">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-lg-8">
				<div class="ibox float-e-margins">
					<lx:iboxTitle title="Alta de pago"/>
				    <div class="ibox-content">
				    	<lx:errorsHeader bean="${tipoDeCambioInstance}"/>
				    	<form class="form-horizontal" >	
				    		<f:with bean="tipoDeCambioInstance">
				    			
				    			<f:display property="fecha" wrapper="bootstrap3"/>
				    			<f:display property="monedaOrigen"  wrapper="bootstrap3"/>
				    			<f:display property="monedaFuente"  wrapper="bootstrap3"/>
				    			<f:display property="factor" widget="tc" wrapper="bootstrap3" />
				    			<f:display property="fuente" 
				    				widget-class="form-control" wrapper="bootstrap3"/>

				    		</f:with>
				    		<div class="form-group">
				    			<div class="col-lg-offset-3 col-lg-10">
				    				<lx:backButton label="Tipos de cambio"/>
				    				<lx:createButton/>
				    				<lx:editButton id="${tipoDeCambioInstance.id}"/>
				    				
				    			</div>
				    		</div>
				    	</form>
				    </div>
				</div>
			</div>
		</div>
	</div>
	
</content>
	

</body>
</html>


