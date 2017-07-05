
<%@ page import="com.luxsoft.cfdi.retenciones.RetencionesCommand" %>
<%@ page import="com.luxsoft.cfdi.retenciones.TipoDeRetencion" %>
<!doctype html>
<html>
<head>
	<title>Comprobante de retención y pago</title>
	<asset:javascript src="forms/forms.js"/>
</head>
<body>
	
	<div class="row wrapper border-bottom white-bg page-heading">
       <div class="col-lg-10">
           <h2>Alta de CFDI para retención y pago</h2>
           <ol class="breadcrumb">
           	<li><g:link action="index">Comprobantes</g:link></li>
           	<li class="active"><strong>Alta</strong></li>
           </ol>
       </div>
       <div class="col-lg-2"></div>
	</div>

	<div class="row wrapper wrapper-content animated fadeInRight">
    <div class="ibox float-e-margins">
      <div class="ibox-title"><h5>Alta de comprobante</h5></div>
      <div class="ibox-content">
        <lx:errorsHeader bean="${cfdiRetencionesInstance}"/>
        <g:form name="createForm" action="save" class="form-horizontal" method="POST">
          <g:hiddenField name="empresa.id" value="${session.empresa.id}"/>
          
          <div class="row">
            
            <div class="col-sm-6">
              <f:field property="fecha" widget-class="form-control" wrapper="bootstrap3" />
              <f:field property="receptor" wrapper="bootstrap3" widget-class="form-control">
                <input 
                  id="receptor" 
                  type="text" 
                  name="${property}"  
                  class="form-control" 
                  value="${value}">
                </input>
              </f:field>
              <f:field property="receptorRfc" wrapper="bootstrap3" widget-class="form-control"/>
              <f:field property="receptorCurp" wrapper="bootstrap3" widget-class="form-control"/>
              <!-- <f:field property="nacional" wrapper="bootstrap3" >
                <g:checkBox id="nacional" name="${property}" value="${value}"   class="form-control"/>
              </f:field> -->
              <f:field property="registroTributario" wrapper="bootstrap3" widget-class="form-control"/>
              <f:field property="tipoDeRetencion" wrapper="bootstrap3">
                <g:select class="form-control chosen-select"  
                  name="${property}" 
                  value="${value?.id}"
                  from="${TipoDeRetencion.list()}" 
                  optionKey="id" 
                  noSelection="[null:'Seleccione un tipo']"
                  />
              </f:field>
              <f:field property="retencionDescripcion" wrapper="bootstrap3" widget-class="form-control"/> 
            </div>

            <div class="col-sm-6">
              
              <f:field property="ejercicio" wrapper="bootstrap3">
                <g:select class="form-control chosen-select"  
                  name="${property}"
                  from="${(1995..2020)}"/>
              </f:field>

              <f:field property="mesInicial" wrapper="bootstrap3">
                <g:select class="form-control chosen-select"  
                  name="${property}"
                  from="${(1..12)}"/>
              </f:field>
              <f:field property="mesFinal" wrapper="bootstrap3">
                <g:select class="form-control chosen-select"  
                  name="${property}"
                  from="${(1..12)}"/>
              </f:field>
              
              <f:field property="total" widget="money" wrapper="bootstrap3"/>
              <f:field property="totalGravado" widget="money" wrapper="bootstrap3"/>
              <f:field property="totalExcento" widget="money" wrapper="bootstrap3"/>
            </div>

          </div>
          
          <div class="row">
             <div class="form-group col-sm-6">
               <div class="col-sm-offset-3 col-sm-3">
                 <button id="saveBtn" class="btn btn-primary ">
                   <i class="fa fa-floppy-o"></i> Salvar
                 </button>
               </div>
             </div>
          </div>

        </g:form>
      </div>
    </div>
	</div>

	

<script type="text/javascript">
	$(function(){
		
    $("#fecha").bootstrapDP({
			todayBtn: "linked",
			keyboardNavigation: false,
			forceParse: false,
			calendarWeeks: true,
			autoclose: true,
			format: 'dd/mm/yyyy',
		});

		$('.chosen-select').chosen();
		
    $(".tc").autoNumeric('init',{vMin:'0.0000'});

		$(".money").autoNumeric({wEmpty:'zero',aSep:"",lZero: 'deny'});

		$('form[name=createForm]').submit(function(e){
			var button=$("#saveBtn");
			button.attr('disabled','disabled')
			.html('Procesando...');
			$(".money,.porcentaje,.tc",this).each(function(index,element){
			  var val=$(element).val();
			  var name=$(this).attr('name');
			  var newVal=$(this).autoNumeric('get');
			  $(this).val(newVal);
			  console.log('Enviando elemento numerico con valor:'+name+" : "+val+ " new val:"+newVal);
			});
			//e.preventDefault(); 
			return true;
		});

		$("#receptor").autocomplete({
			source:'<g:createLink  action="buscarReceptores"/>',
			minLength:1,
			select:function(e,ui){
				console.log('Valor seleccionado: '+ui.item.nombre+ " Nacional: "+ui.item.nacional);
				$("#receptorRfc").val(ui.item.rfc);
				//$("#nacional").val(ui.item.nacional);
				//$( "#nacional" ).prop("checked",  ui.item.nacional);
				
			}
		});

	});

</script>
	
</body>
</html>

