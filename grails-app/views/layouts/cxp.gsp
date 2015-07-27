<g:applyLayout name="application">
    <html>
    <head>
        <title><g:layoutTitle/></title>
        <g:layoutHead/>
    </head>
    </html>
    <body>

        <div class="container-fluid">
           
            <div class="row">
            	
            	<div class="col-md-2">
                    <div class="side-bar">
                        <div class="list-group">
                            <g:if test="${pageProperty(name:'page.tasks')}">
                                <g:pageProperty name="page.tasks"/>
                            </g:if>
                            <g:else> 
                                <sec:ifAnyGranted roles="COMPRAS,ADMIN">
                                    <g:link 
                                        class="list-group-item ${webRequest.controllerName=='facturaDeImportacion'?'active':''}" 
                                        controller="facturaDeImportacion">
                                        Facturas de Importaciones 
                                    </g:link>
                                    <g:link 
                                        class="list-group-item ${webRequest.controllerName=='gastosDeImportacion'?'active':''}"
                                        controller="gastosDeImportacion" >
                                        <g:message code="gastosDeImportacion.list.label" default="Gastos de importación"/>
                                    </g:link>
                                    <g:link  class="list-group-item" controller="pago" action="list">
                                        <!-- <i class="icon-list"></i> -->
                                        <g:message code="pago.list.label" default="Pagos"/>
                                    </g:link>
                                    <g:link class="list-group-item" 
                                        controller="notaDeCredito" action="list">
                                        <!-- <i class="icon-list"></i> -->
                                        <g:message code="notaDeCredito.list.label" default="Notas de Crédito"/>
                                    </g:link>
                                    <g:link class="list-group-item" 
                                        controller="facturaDeImportacion" action="detalleDeFacturas">
                                            Detalle de Facturas
                                    </g:link>
                                </sec:ifAnyGranted>
                                

                                <sec:ifAnyGranted roles="COMPRAS,ADMIN,TESORERIA">
                                    <g:link 
                                        class="list-group-item ${webRequest.controllerName=='facturaDeGastos'?'active':''}" 
                                        controller="facturaDeGastos">
                                        <g:message code="facturaDeGastos.list.label" default="Otros gastos"/>
                                    </g:link>
                                    <g:link class="list-group-item" controller="requisicion" action="index">
                                        <!-- <i class="icon-list"></i> -->
                                        <g:message code="requisicion.list.label" default="Requisiciones"/>
                                    </g:link>
                                    <g:link class="list-group-item" controller="cuentaDeGastosGenerica" action="list">
                                        <!-- <i class="icon-list"></i> -->
                                        <g:message code="cuentaDeGastosGenerica.list.label" default="Cuenta de Gastos (Genérica)"/>
                                    </g:link>
                                </sec:ifAnyGranted>
                                
                            </g:else>  
                             

                            
                        </div>
                    </div>
            		
            	</div><!-- End .col-md-2 side bar -->
            	
            	<div class="col-md-10">
            		<div class="document-panel">
                        <g:if test="${pageProperty(name:'page.header')}">
                            <g:pageProperty name="page.header"/>
                        </g:if>
                        <g:else>
                            <div class="alert alert-info">
                                Periodo ${session.periodo}
                                <g:if test="${flash.message}">
                                    <small><span class="label label-warning ">${flash.message}</span></small>
                                </g:if> 
                                <g:if test="${flash.error}">
                                    <small><span class="label label-danger ">${flash.error}</span></small>
                                </g:if> 
                            </div>
                        </g:else>
            			<g:pageProperty name="page.document"/>
            		</div>
            		
					
            	</div>
            	
            </div><!-- end .row 2  -->
            
            <div class="row">
            	
            </div>
            
        </div>
        
    </body>
</g:applyLayout>