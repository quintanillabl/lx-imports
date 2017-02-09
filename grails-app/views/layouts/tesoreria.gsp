<g:applyLayout name="application">
    <html>
    <head>
        <title><g:layoutTitle/></title>
        <g:layoutHead/>
    </head>
    </html>
    <body>

        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-lg-10">
                <h2>${pageProperty(name:'page.header')?:'Falta page.header'} 
                    <small>(${session.periodoTesoreria.asPeriodoText()})</small>
                </h2>
                <g:pageProperty name="page.subHeader"/>
                
                <g:if test="${flash.message}">
                    <small><span class="label label-warning ">${flash.message}</span></small>
                </g:if> 
                <g:if test="${flash.error}">
                    <small><span class="label label-danger ">${flash.error}</span></small>
                </g:if> 
            </div>
        </div>

        <div class=" row wrapper wrapper-content animated fadeInRight">
            
            <div class="col-lg-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>${pageProperty(name:'page.gridTitle')?:'Registros'} </h5>
                        <div class="ibox-tools">
                            <a data-target="#periodoDialog" data-toggle="modal" >
                                <i class="fa fa-calendar"></i> 
                            </a>
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                            <a class="close-link">
                                <i class="fa fa-times"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        
                        <div class="row toolbar-panel">
                            
                            <div class="col-md-3">

                                <div class="input-group">
                                        <span class="input-group-btn">
                                            <button data-target="#periodoDialog" data-toggle="modal" class="btn  btn-outline btn-success ">
                                        <i class="fa fa-calendar"></i> 
                                    </button>
                                        </span>
                                    <input type='text' id="filtro" placeholder="Filtrar" class="form-control" autofocus="on">
                                </div>
                                
                            </div>
                            <div class="col-md-4">
                                <g:form class="form-horizontal" action="show">
                                    <g:hiddenField id ="searchId" name="id" />
                                    <div class="input-group">
                                        <input id="searchField" name="searchDesc" type="text" 
                                            class="form-control " placeholder="Buscar registro">
                                        <span class="input-group-btn">
                                            <button id="buscarBtn" type="submit" class="btn btn-default" disabled="disabled">
                                                <i class="fa fa-search"></i></span>
                                            </button> 
                                        </span>
                                    </div>
                                </g:form>
                            </div>  

                            <div class="btn-group">
                                <lx:refreshButton/>
                                <lx:createButton/>
                            </div>
                            <div class="btn-group">
                                <button type="button" name="operaciones"
                                        class="btn btn-success btn-outline dropdown-toggle" data-toggle="dropdown"
                                        role="menu">
                                        Operaciones <span class="caret"></span>
                                </button>
                                <ul class="dropdown-menu">
                                    <g:pageProperty name="page.operaciones"/>
                                </ul>
                            </div>
                            <div class="btn-group">
                                <button type="button" name="reportes"
                                        class="btn btn-primary btn-outline dropdown-toggle" data-toggle="dropdown"
                                        role="menu">
                                        Reportes <span class="caret"></span>
                                </button>
                                <ul class="dropdown-menu">
                                    <g:pageProperty name="page.reportes"/>
                                </ul>
                            </div>
                        </div>
                       
                       <g:pageProperty name="page.grid"/>

                    </div>
                </div>
            </div>
        </div>

            <div class="modal fade" id="periodoDialog" tabindex="-1">
                <div class="modal-dialog ">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"
                                aria-hidden="true">&times;</button>
                            <h4 class="modal-title" id="myModalLabel">Cambiar periodo</h4>
                        </div>
                        <g:form action="cambiarPeriodo" class="form-horizontal" >
                            <div class="modal-body">
                                <div class="form-group" id="data_4">
                                    <label class="font-noraml">Month select</label>
                                    <div class="input-group date">
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <input type="text" class="form-control" name="fecha"
                                            value="${formatDate(date:session.periodoTesoreria,format:'dd/MM/yyyy')}">
                                    </div>
                                </div>
                            </div>
                            
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
                                <g:submitButton class="btn btn-info" name="aceptar"
                                        value="Aceptar" />
                            </div>
                        </g:form>
            
                    </div>
                    <!-- moda-content -->
                </div>
                <!-- modal-di -->
            </div>

        <script type="text/javascript">
            $(function(){
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
                $("#filtro").on('keyup',function(e){
                    var term=$(this).val();
                    $('#grid').DataTable().search(
                        $(this).val()
                            
                    ).draw();
                });

                $('.date').bootstrapDP({
                    minViewMode: 1,
                    format: 'dd/mm/yyyy',
                    keyboardNavigation: false,
                    forceParse: false,
                    autoclose: true,
                    todayHighlight: true,

                });
                <g:if test="${pageProperty(name:'page.searchService')}">
                    $("#searchField").autocomplete({
                        source:'<g:createLink action="search"/>',
                        minLength:1,
                        select:function(e,ui){
                            $("#searchField").val(ui.item.id);
                            $("#searchId").val(ui.item.id);
                            var button=$("#buscarBtn");
                            button.removeAttr('disabled');
                        }
                    });
                </g:if>   

            });
        </script>  
                
    </body>
</g:applyLayout>