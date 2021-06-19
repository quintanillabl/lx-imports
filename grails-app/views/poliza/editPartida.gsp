<%@ page import="com.luxsoft.impapx.contabilidad.PolizaDet" %>
<!doctype html>
<html>
<head>

	<asset:javascript src="forms/forms.js"/>
	<g:set var="entityName" value="${message(code: 'poliza.label', default: 'Detalle de póliza')}" scope="request"/>
	<g:set var="entity" value="${polizaDetInstance}" scope="request" />
	<title>Partida de póliza ${polizaDetInstance.id}</title>
	<meta name="layout" content="application">
</head>
<body>

<div class="row wrapper border-bottom white-bg page-heading">
    <div class="col-lg-10">
        <h2>Partida de póliza   </h2>

        <ol class="breadcrumb">
            <li><g:link action="index">${entityName}(s)</g:link></li>
            <li><g:link action="show" id="${entity.id}">Consulta</g:link></li>
            <li class="active"><strong>Edición</strong></li>
        </ol>
        
        <g:if test="${flash.message}">
            <small><span class="label label-warning ">${flash.message}</span></small>
        </g:if> 
        <g:if test="${flash.error}">
            <small><span class="label label-danger ">${flash.error}</span></small>
        </g:if> 
    </div>
</div>

<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-lg-8">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>Propiedades</h5>
                </div>
                <div class="ibox-content">

                    <lx:errorsHeader bean="${entity}"/>
                    <g:form name="updateForm" action="updatePartida" class="form-horizontal" method="PUT">  
                        <f:with bean="${polizaDetInstance}" >
                        	<g:hiddenField name="id" value="${polizaDetInstance.id}"/>
                        	<g:hiddenField name="version" value="${polizaDetInstance.version}"/>
                        	
                        	<f:field property="cuenta"  >
                        		<g:hiddenField id="cuentaId" name="cuenta.id" value="${value.id}"/>
                        		<input type="text" id="cuentaField" class="form-control" value="${value}">
                        	</f:field>
                        	
                        	<f:field property="debe"  widget="money"/>
                        	<f:field property="haber"  widget="money"/>
                        	<f:field property="asiento" widget-class="form-control"/>
                        	<f:field property="descripcion" widget-class="form-control"/>
                        	<f:field property="referencia" widget-class="form-control"/>
                        </f:with>
                        <div class="form-group">
                            <div class="col-lg-offset-3 col-lg-9">
                                <button id="saveBtn" class="btn btn-primary ">
                                    <i class="fa fa-floppy-o"></i> Actualizar
                                </button>
                                <lx:backButton/>
                            </div>
                        </div>
                    </g:form>
                </div>
            </div>
        </div>
    </div>
</div>
	

<script type="text/javascript">
	$(function(){
		$(".money").autoNumeric({vMin:'-999999999.00',wEmpty:'zero',mRound:'B'});
		$("#cuentaField").autocomplete({
			appendTo: "#createPanel",
			source:'<g:createLink controller="cuentaContable" action="getCuentasDeDetalleJSON"/>',
			minLength:1,
			select:function(e,ui){
				$("#cuentaId").val(ui.item.id);
			}
		});
		
		
	});
</script>
	

</body>

	
</html>


