<%@ page import="com.luxsoft.impapx.Venta" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="luxor">
	<title>Ventas</title>
	<asset:javascript src="forms/forms.js"/>
</head>

<content tag="header"> 
	Venta ${ventaInstance.clase?.toUpperCase()}: ${ventaInstance.id} ( ${ventaInstance.cliente})
</content>
<content tag="subHeader">
	<ol class="breadcrumb">
    	<li><g:link action="index">Ventas</g:link></li>
    	<li class="active"><strong>Edición</strong></li>
	</ol>
</content>

<content tag="document">


	<div class="ibox float-e-margins">
		<lx:iboxTitle title="N.Cargo ${ventaInstance.id}"/>
    <div class="ibox-content">

			<ul class="nav nav-tabs" id="myTab">
				
				<li class="active"><a href="#venta" data-toggle="tab"><i class="fa fa-pencil"></i>  Propiedades</a></li>
				<li class=""><a href="#partidas" data-toggle="tab"><i class="fa fa-th-list"></i>  Partidas</a></li>
			</ul>

  		<div class="tab-content"> <!-- Tab Content -->
  			
			<div class="tab-pane active" id="venta">
				<br>
				<g:render template="editForm" bean="${ventaInstance}"/>
			</div>
			<div class="tab-pane" id="partidas">
				<g:render template="partidasPanel" bean="${ventaInstance}"/>
			</div>			
  		</div>	<!-- End Tab Content -->
    </div>
	</div>

	%{-- <g:render template="/common/deleteDialog" bean="${ventaInstance}"/> --}%

	<script type="text/javascript">
		$(function(){
			//$('.chosen-select').chosen();
			$(".numeric").autoNumeric('init',{vMin:'0'},{vMax:'9999'});
			$(".money").autoNumeric('init',{wEmpty:'zero',mRound:'B',aSign: '$'});
			$(".tc").autoNumeric('init',{vMin:'0.0000'});
			$('#grid').dataTable({
			    responsive: true,
			    aLengthMenu: [[20, 40, 60, 100, -1], [20, 40,60, 100, "Todos"]],
			    "language": {
			        "url": "${assetPath(src: 'datatables/dataTables.spanish.txt')}"
			    },
			    "dom": 'T<"clear">lfrtip',
			    "tableTools": {
			        "sSwfPath": "${assetPath(src: 'plugins/dataTables/swf/copy_csv_xls_pdf.swf')}"
			    },
			    "order": []
			});

			$(".grid tbody tr").click(function(){
        $(this).toggleClass("success selected");
      });

			var selectRows=function(){
				var res=[];
				var data=$(".grid .selected").each(function(){
					var tr=$(this);
					res.push(tr.attr("id"));
				});
				return res;
			};



			$("#eliminarPartidas").click(function(e){
				var res=selectRows();
				if(res.length==0){
					alert('Debe seleccionar al menos un registro');
					return;
				}
				var ok=confirm('Eliminar  ' + res.length+' partida(s)?');
				if(!ok)
					return;
				console.log('Cancelando facturas: '+res);
				$.post(
					"${createLink(action:'eliminarConceptos')}",
					{notaDeCargoId:${ventaInstance.id},partidas:JSON.stringify(res)})
				.done(function(data){
					location.reload();
				})
				.fail(function(jqXHR, textStatus, errorThrown){
					alert("Error: "+textStatus);
				});	
				
			});
		});
	</script>
</content>

	
</html>




