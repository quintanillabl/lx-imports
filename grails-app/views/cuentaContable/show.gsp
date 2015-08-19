<%@ page import="com.luxsoft.impapx.contabilidad.CuentaContable" %>
<!doctype html>
<html>
<head>
	<title>Cuenta ${cuentaContableInstance.id}</title>
	<meta name="layout" content="luxor">
	<asset:javascript src="forms/forms.js"/>
</head>
<body>

<content tag="header">Cuenta: ${cuentaContableInstance}</content>
<content tag="subHeader">
	<ol class="breadcrumb">
    	<li><g:link action="index">Cuentas</g:link></li>
    	<li class="active"><strong>Consulta</strong></li>
    	<li><g:link action="edit" id="${cuentaContableInstance.id}">Edición</g:link></li>
	</ol>
</content>
	
<content tag="document">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-lg-7">
				<lx:iboxTitle title="Cuenta contable  "/>
				
				<div class="ibox-content">
					<form name="createForm" class="form-horizontal">	
						<f:with bean="cuentaContableInstance">
							<f:display property="clave" wrapper="bootstrap3"/>
							<f:display property="descripcion" wrapper="bootstrap3"/>
							<f:display property="tipo" wrapper="bootstrap3"/>
							<f:display property="subTipo" wrapper="bootstrap3"/>
							<f:display property="naturaleza" wrapper="bootstrap3"/>
							<f:display property="deResultado" wrapper="bootstrap3"/>
							<f:display property="presentacionContable" wrapper="bootstrap3"/>
							<f:display property="presentacionFinanciera" wrapper="bootstrap3"/>
							<f:display property="presentacionFiscal" wrapper="bootstrap3"/>
							<f:display property="presentacionPresupuestal" wrapper="bootstrap3"/>
							<f:display property="cuentaSat" wrapper="bootstrap3"/>
						</f:with>
						<div class="form-group">
							<div class="col-lg-offset-3 col-lg-10">
								<g:if test="${cuentaContableInstance.padre}">
									<lx:backButton label="${cuentaContableInstance.padre}" 
										action="show" id="${cuentaContableInstance.padre.id}"/>
								</g:if>
								<g:else>
									<lx:backButton label="Cuentas" />
								</g:else>
								
								<lx:editButton id="${cuentaContableInstance.id}"/>
							</div>
						</div>
					</form>
				</div>
			</div>
			<g:if test="${!cuentaContableInstance.detalle}">
			<div class="col-lg-5">
				<lx:iboxTitle title="Sub cuentas "/>
				<div class="ibox-content">
					<table id="grid" class="grid table table-responsive  table-bordered ">
						<thead>
							<tr>
								<th>Clave</th>
								<th>Descripción</th>
							</tr>
						</thead>
						<tbody>
							<g:each in="${cuentaContableInstance.subCuentas}" var="row">
								<tr>
									<td><g:link action="show" id="${row.id}">
										${fieldValue(bean: row, field: "clave")}
										</g:link>
									</td>
									<td><g:link action="show" id="${row.id}">
										${fieldValue(bean: row, field: "descripcion")}
										</g:link>
									</td>
								</tr>
							</g:each>
						</tbody>
					</table>
				</div>
			</div>
			</g:if>
		</div>
	</div>
	
</content>
</body>
</html>



