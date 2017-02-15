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
                <h2>${pageProperty(name:'page.header')?:"Falta header"} </h2>
                <ol class="breadcrumb">
                    <li><g:link action="index">${entityName}(s)</g:link></li>
                    <li><g:link action="create">Alta</g:link></li>
                    <li class="active"><strong>Consulta</strong></li>
                    <li><g:link action="edit" id="${entity.id}">Edición</g:link></li>
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
                            <form class="form-horizontal" >  
                                <g:pageProperty name="page.formFields"/>
                                <div class="form-group">
                                    <div class="col-lg-offset-3 col-lg-9">
                                        
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="ibox-footer">
                            <div class="btn-group">
                                <lx:backButton/>
                                <lx:createButton/>
                                <lx:editButton id="${entity?.id}"/>
                                <lx:printButton/>
                                <g:if test="${pageProperty(name:'page.deleteButton')}">
                                    <g:pageProperty name="page.deleteButton"/>
                                </g:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
             
    </body>
</g:applyLayout>