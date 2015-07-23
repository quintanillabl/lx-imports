<html>
	<head>
		<meta name="layout" content="main">
		<title>Importadores</title>
		
	</head>
	<body>
		<p><h1>Importadores de información desde SIIPAP EX</h1></p>
		<div class="nav" role="navigation">
			<ul>
				<li><g:link action="importarLineas" controller="importador" 
					onclick="return confirm('Importar lineas desde SiipapEx?');">Importar</g:link>
				</li>
				<li>
					<g:link class="confirm" action="test" controller="importador"
					onclick="return myConfirm2(this,'Importar líneas','Tarea de importación');">Test</g:link>
				</li>
			</ul>
		</div>
		
		<div id="dialog" title="Confirmar">
			<p>Importar líneas desde SiipapEx</p>
		</div>
		
		<r:script>
			// increase the default animation speed to exaggerate the effect
			$.fx.speeds._default = 300;
			$(function() {
				$( "#dialog" ).dialog({
					autoOpen: false,
					show: "blind",
					hide: "explode",
					modal:true,
					buttons: {
						"Aceptar": function() {
							$( this ).dialog( "close" );
							
						},
						Cancel: function() {
							$( this ).dialog( "close" );
							
						}
					}
				});
		
				$( ".confirm1" ).click(function(e) {
					e.preventDefault();
					var res=$( "#dialog" ).dialog( "open" );
					return res;
				});				
				
			});
	</r:script>
	
	<r:script>
		var confirmed=false;
		function myConfirm(obj){
			//return confirm('Confirmar ?');
			
			if(!confirmed)
    {
        $( '<div></div>' ).html("<p>Seguro que desea continuar?</p>").dialog({
            resizable: false,
            show: "blind",
			hide: "explode",
            modal: true,
            title:"Confirmar tarea",
            buttons: {
                "Aceptar": function()
                {
                    $( this ).dialog( "close" );
                    confirmed = true; obj.click();
                },
                "Cancelar": function()
                {
                    $( this ).dialog( "close" );
                }
            }
        }).dialog("open");
    }
 
    return confirmed;
		}
	</r:script>
	</body>
</html>