<%@ page import="com.luxsoft.impapx.Pedimento" %>
<%@ page contentType="text/html;charset=ISO-8859-1" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="luxor">
	<title>Pedimento ${pedimentoInstance.id}</title>
	<asset:javascript src="forms/forms.js"/>
</head>
<body>

<content tag="header">
	Pedimento: ${pedimentoInstance.pedimento} (${pedimentoInstance.proveedor})
</content>

<content tag="subHeader">
	<ol class="breadcrumb">
    	<li><g:link action="index">Pedimentos</g:link></li>
    	<li><g:link action="create">Alta</g:link></li>
    	<li><g:link action="show" id="${pedimentoInstance.id}">Consulta</g:link></li>
    	<li class="active"><strong>Edición</strong></li>
	</ol>
</content>

<content tag="document">
	<div class="row">
		<div class="col-lg-12">
			<div class="ibox float-e-margins">
				<lx:iboxTitle title="Titulo"/>
			    <div class="ibox-content">
					<ul class="nav nav-tabs" role="tablist">
						<li class="active"><a href="#pedimento" data-toggle="tab">Pedimento</a></li>
						<li class=""><a href="#embarques" data-toggle="tab">Embarques</a></li>
					</ul>
					<div class="tab-content"> 
						<div class="tab-pane active" id="pedimento">
							<g:render template="editForm" bean="${pedimentoInstance}"/>
						</div>
						<div class="tab-pane" id="embarques">
							<g:render template="embarquesPanel" bean="${pedimentoInstance}"/>
						</div>
			  		</div>	
			    </div>
			</div>
		</div>
	</div>
</content>
		
</body>
</html>
