<%@ page import="com.luxsoft.impapx.tesoreria.MovimientoDeCuenta" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="luxor">
	<g:set var="entityName" value="${message(code: 'movimientoDeCuenta.label', default: 'MovimientoDeCuenta')}" />
	<title><g:message code="default.create.label" args="[entityName]" /></title>
</head>
<body>
	
<content tag="header">
	${movimientoDeCuentaInstance}
</content>
<content tag="subHeader">
	<ol class="breadcrumb">
    	<li><g:link  action="index">Movimientos</g:link></li>
    	<li><g:link  action="depositar">Depositar</g:link></li>
 		<li><g:link  action="retirar">Retirar</g:link></li>
    	<li class="active"><strong>Consulta</strong></li>
	</ol>
</content>

<content tag="document">
	<div class="row">
		<div class="col-md-8">
			<div class="ibox float-e-margins">
				<lx:iboxTitle title="Titulo"/>
			    <div class="ibox-content">
					<g:form class="form-horizontal" action="show" id="${movimientoDeCuentaInstance.id }">
						<f:with bean="movimientoDeCuentaInstance">
							<f:display property="cuenta" />
							<f:display property="concepto"/>
							<f:display property="fecha" />
							<f:display property="tipo" />
							<f:display property="referenciaBancaria" />
							<f:display property="tc" />
							<f:display property="importe" />
							<f:display property="comentario" />
						</f:with>
					</g:form>
					<div class="row">
						<div class="form-group">
							<div class="col-sm-5 col-sm-offset-2">
								<lx:deleteButton bean="${movimientoDeCuentaInstance}"/>
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
