<%@ page import="com.luxsoft.impapx.Distribucion" %>
<!doctype html>
<html>

	<head>
		<meta name="layout" content="luxor">		
		<title>Reporte de distribucion Id: ${distribucion.id} </title>
	</head>
	
	<body>
	<div class="container">
	
		<div class="row"> <!-- Report Header -->
			
			<div class="span12">
				<h2 align="right">${empresa.nombre}</h2>
				<h3 align="right">Distribución de importaciones</h3>
			</div>
			
			<div class="span6">
				
				<p>
					Embarque: <strong>${distribucion.embarque.nombre}</strong><br>
					BL: <strong>${distribucion.embarque.bl}</strong><br>
					Proveedor: <strong>${distribucion.embarque.proveedor.nombre}</strong>
				</p>
			</div>
			
			<div class="span6">
				<p>
					Referencia Aduanal: <strong>PENDIENTE</strong><br>
				</p>
			</div>
			
		</div><%-- End Report Header --%>
		
			
	</div>
	</body>
	
</html>