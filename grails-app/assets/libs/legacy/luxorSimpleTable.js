/**
 * Implementacion preferida de dataTables
 */

$(function(){
	$(".grid").dataTable({
		"sDom": "<'row'<'span6'l><'span6'f>r>t<'row'<'span6'i><'span6'p>>",
		aLengthMenu: [[100, 150, 200, 250, -1], [100, 150, 200, 250, "Todos"]],
      	iDisplayLength: 100,
      	"oLanguage": {
      	    "sEmptyTable":     "No hay datos disponibles",
      	    "sInfo":           "Mostrando desde _START_ hasta _END_ de _TOTAL_ registros",
      	    "sInfoEmpty":      "Mostrando desde 0 hasta 0 de 0 registros",
      	    "sInfoFiltered":   "(filtrado de _MAX_ registros en total)",
      	    "sInfoPostFix":    "",
      	    "sInfoThousands":  ",",
      	    "sLengthMenu":     "Mostrar _MENU_ registros",
      	    "sLoadingRecords": "Cargando...",
      	    "sProcessing":     "Procesando...",
      	    "sSearch":         "Buscar:",
      	    "sZeroRecords":    "No se encontraron resultados",
      	    "oPaginate": {
      	        "sFirst":    "Primero",
      	        "sLast":     "òltimo",
      	        "sNext":     "Siguiente",
      	        "sPrevious": "Anterior"
      	    },
      	    "oAria": {
      	        "sSortAscending":  ": activar para Ordenar Ascendentemente",
      	        "sSortDescending": ": activar para Ordendar Descendentemente"
      	    }
      	},
			"bPaginate": false,
			"bInfo": false,
			"sSearch": "Filtrar:"
		});
	});

	$(".grid tbody tr").hover(function(){
		$(this).toggleClass("info");
	});
	$(".grid tbody tr").click(function(){
		$(this).toggleClass("success selected");
	});
	
	$(document).on("keydown",function(event){
		var keycode = (event.keyCode ? event.keyCode : event.which);
		if(event.ctrlKey ){
			if(keycode==65){
				//console.log('detecting under ctrl');
				$(".grid tbody tr").addClass("success selected");
			}else if(keycode==67){
				$(".grid tbody tr").removeClass("success selected");
			}
		}
	});
		
	function selectAllRows(){
		$(".grid tbody tr").addClass("success selected");
	}
	function clearAllRows(){
		$(".grid tbody tr").removeClass("success selected");
	}
	
	function selectedRows(){
		var res=[];
		var data=$(".grid .selected").each(function(){
			var tr=$(this);
			res.push(tr.attr("id"));
		});
		return res;
	}
		
});