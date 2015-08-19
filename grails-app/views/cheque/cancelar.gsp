<%@ page import="com.luxsoft.impapx.tesoreria.Cheque" %>
<%@ page import="luxsoft.cfd.ImporteALetra" %>

<%@ page import="com.luxsoft.impapx.tesoreria.Cheque" %>
<%@ page import="luxsoft.cfd.ImporteALetra" %>
<!doctype html>
<html>
<head>
	<title>Cheque ${cancelacionCommand.id}</title>
	<meta name="layout" content="luxor">
</head>
<body>

<content tag="header">Cancelación de cheque ${cancelacionCommand.id }</content>
<content tag="subHeader">
	<ol class="breadcrumb">
    	<li><g:link action="index">Cheques</g:link></li>
    	<li class="active"><strong>Alta</strong></li>
	</ol>
</content>

<content tag="document">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-lg-8">
				<div class="ibox float-e-margins">
					<lx:iboxTitle title="Cancelación de cheque"/>
				    <div class="ibox-content">
				    	<lx:errorsHeader bean="${cancelacionCommand}"/>
				    	<g:form class="form-horizontal" action="cancelar" id="${cancelacionCommand.id}">
				    		<fieldset>
				    			<div class="form-group">
				    				<label for="comentario" class="control-label col-sm-2">Comentario</label>
				    				<div class="col-sm-10">
				    					<input name="comentario" class="form-control" value="" required>
				    				</div>
				    			</div>
				    			<div class="form-group">
				    				<div class="col-lg-offset-3 col-lg-10">
				    					<button type="submit" class="btn btn-danger">
				    						Cancelar
				    					</button>
				    				</div>
				    			</div>
				    		</fieldset>
				    	</g:form>
				    	
				    </div>

				</div>
			</div>
			
		</div>
	</div>
	
</content>
	

</body>
</html>


