<g:applyLayout name="application">
<html>
<head>
    <title><g:layoutTitle/></title>
    <g:layoutHead/>
</head>
<body>

    <div class="row wrapper border-bottom white-bg page-heading">
        <div class="col-lg-10">
            <h2>${pageProperty(name:'page.header')?:'Falta page.header'}</h2>
            
            <g:pageProperty name="page.subHeader"/>
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
            <div class="col-lg-12">
                <div class="ibox float-e-margins">
                    
                    <div class="ibox-title">
                        <button data-target="#periodoDialog" data-toggle="modal" class="btn btn-outline btn-success  dim">
                            <i class="fa fa-calendar"></i> 
                        </button>
                        <lx:refreshButton/>
                        <div class="btn-group">
                            <button type="button" name="operaciones"
                                    class="btn btn-default dropdown-toggle" data-toggle="dropdown"
                                    role="menu">
                                    Operaciones <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu">
                                <li>
                                    <a data-toggle="modal" data-target="#generarDialog"><i class="icon-plus "></i>Generar</a>
                                </li>
                                
                            </ul>
                        </div>
                        <div class="btn-group">
                            <button type="button" name="reportes"
                                    class="btn btn-info dropdown-toggle" data-toggle="dropdown"
                                    role="menu">
                                    Reportes <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu">
                                 <g:pageProperty name="page.reportes"/>
                            </ul>
                        </div>
                        
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                            <a class="close-link">
                                <i class="fa fa-times"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <table id="grid"
                            class="table table-striped table-hover table-bordered table-condensed">
                            <thead>
                                <tr>
                                    <th>Folio</th>
                                    <th>Fecha</th>
                                    <th>Descripción</th>
                                    <th>Debe</th>
                                    <th>Haber</th>
                                    <th>Cuadre</th>
                                    <th>Modificada</th>
                                </tr>
                            </thead>
                            <tbody>
                                <g:each in="${polizaInstanceList}" var="row">
                                    <tr>
                                        <td><g:link controller="poliza" action="edit" id="${row.id}">
                                            ${fieldValue(bean: row, field: "folio")}
                                            </g:link>
                                        </td>
                                        <td><lx:shortDate date="${row.fecha }"/></td>
                                        <td><g:link controller="poliza" action="edit" id="${row.id}">
                                            ${fieldValue(bean: row, field: "descripcion")}</g:link>
                                        </td>
                                        <td><lx:moneyFormat number="${row.debe}"/></td>
                                        <td><lx:moneyFormat number="${row.haber}"/></td>
                                        <td><lx:moneyFormat number="${row.cuadre}"/></td>
                                        <td><g:formatDate date="${row.lastUpdated}"/></td>
                                    </tr>
                                </g:each>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <g:render template="/poliza/generarPoliza"/>

    %{-- <g:render template="/poliza/cambiarPeriodo"/> --}%
    <g:render template="/common/selectorDePeriodoContable" bean="${session.periodoContable}"/>
    
    <script type="text/javascript">
        $(function(){
            $('#grid').dataTable({
                responsive: true,
                aLengthMenu: [[250, -1], [250, "Todos"]],
                "language": {
                    "url": "${assetPath(src: 'datatables/dataTables.spanish.txt')}"
                },
                "dom": 'T<"clear">lfrtip',
                "tableTools": {
                    "sSwfPath": "${assetPath(src: 'plugins/dataTables/swf/copy_csv_xls_pdf.swf')}"
                },
                "order": []
            });

            $("#fecha").bootstrapDP({
                changeMonth: false,
                format: 'dd/mm/yyyy',
                keyboardNavigation: false,
                forceParse: false,
                autoclose: true,
                todayHighlight: true,
                startDate:"${formatDate(date:session.periodoContable.toPeriodo().fechaInicial,format:'dd/MM/yyyy')}",
                endDate:"${formatDate(date:session.periodoContable.toPeriodo().fechaFinal,format:'dd/MM/yyyy')}"
            });

        });
    </script>
</body>
</html>
</g:applyLayout>