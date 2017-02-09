<g:applyLayout name="application">
    <html>
    <head>
        <title><g:layoutTitle/></title>
        <asset:javascript src="forms/forms.js"/>
        <g:layoutHead/>
    </head>
    </html>
    <body>

        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-lg-10">
                <h2>${pageProperty(name:'page.header')?:"Edición de  ${entityName}"} </h2>
                
                %{-- <g:pageProperty name="page.subHeader"/> --}%
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
                            <h5>${pageProperty(name:'page.formTitle')?:'Propiedades'} </h5>
                        </div>
                        <div class="ibox-content">

                            <lx:errorsHeader bean="${entity}"/>
                            <g:form name="updateForm" action="update" class="form-horizontal" method="PUT">  
                                <g:hiddenField name="id" value="${entity.id}"/>
                                <g:hiddenField name="version" value="${entity.version}"/>
                                <g:pageProperty name="page.formFields"/>
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
                $('.date').bootstrapDP({
                    format: 'dd/mm/yyyy',
                    keyboardNavigation: false,
                    forceParse: false,
                    autoclose: true,
                    todayHighlight: true
                });
                $('.chosen-select').chosen();

            });
        </script>  
             
    </body>
</g:applyLayout>