// This is a manifest file that'll be compiled into datatables.js.
//= require jquery.dataTables.js
//= require jquery.bootstrap.js
//= require_self

$(function(){
	
	var table=$('.luxor-grid').dataTable( {
    	"paging":   false,
    	"ordering": false,
    	"info":     false,
    	"autoWidth": true, //EVALUANDO DESDE 19-jul-2015
    	"dom": '<"toolbar col-md-4">rt<"bottom"lp>'
	} );

	//new $.fn.dataTable.FixedHeader( table );
	
	$("#filtro").on('keyup',function(e){
		var term=$(this).val();
		$('.luxor-grid').DataTable().search(
			$(this).val()
		        
		).draw();
	});

	$('.luxor-grid-2').dataTable( {
    	"language": {
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
		        "sLast":     "último",
		        "sNext":     "Siguiente",
		        "sPrevious": "Anterior"
		    },
		    "oAria": {
		        "sSortAscending":  ": activar para Ordenar Ascendentemente",
		        "sSortDescending": ": activar para Ordendar Descendentemente"
		    }
		},
	    	aLengthMenu: [[20, 40, 60, 100, -1], [20, 40, 60, 100, "Todos"]],
			iDisplayLength: 20,
			"autoWidth": false
	} );
	
	/* Ej de uso con url de idiomas
		$('#grid').dataTable( {
	    	"language": {
				"url": "${assetPath(src: 'datatables/dataTables.spanish.txt')}"
	    	},
	    	aLengthMenu: [[20, 40, 60, 100, -1], [20, 40, 60, 100, "Todos"]],
			iDisplayLength: 20,
			"autoWidth": false
		} );
	*/
	
});