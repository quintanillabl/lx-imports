<!doctype html>
<html>
<head>
	<title>${perfilInstance.usuario}</title>
	<meta name="layout" content="luxor">
</head>
<body>

<content tag="header">${perfilInstance.usuario.username} </content>
<content tag="subHeader">
	<ol class="breadcrumb">
		<li><g:link action="edit"><strong>Edición</strong></g:link></li>
    	<li><g:link action="permisos">Permisos</g:link></li>
    	<li><g:link action="alertas">Alertas</g:link></li>
	</ol>
</content>

<content tag="document">
	<div class="col-lg-4">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5>Resumen</h5>
			</div>
			<div>
				<div class="ibox-content no-padding border-left-right">
					%{-- <img class="img-responsive" src="img/profile_big.jpg" alt="image"> --}%
					<g:if test="${perfilInstance.foto}">
						%{-- <img src="${createLink(action:'renderFoto',id:'perfilInstance.id')}" class="img-responsive"> --}%
						<img src="${createLink(controller: 'perfil', action:'renderFoto', id: perfilInstance.id)}" class="img-responsive"/>
					</g:if>
					<g:else>
						<img src="http://placehold.it/590X393&text=Foto" class="img-responsive" absolute="false">
						%{-- <asset:image src="demos/profile_big.jpg" class="img-responsive" absolute="false"/> --}%
					</g:else>
					
				</div>
				<div class="ibox-content profile-content">
					<h4>
						<strong>${perfilInstance.usuario.nombre}</strong>
					</h4>
					<h5> Info </h5>
					<p> Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitat. </p>
					<div class="row m-t-lg">
                        <div class="col-md-4">
                            <span class="bar">Datos provisionales</span>
                            <h5><strong>169</strong> Enviados</h5>
                        </div>
                        <div class="col-md-4">
                            <span class="line">Datos provisionales 2</span>
                            <h5><strong>28</strong> Recibidos</h5>
                        </div>
                        <div class="col-md-4">
                            <span class="bar">Test data</span>
                            <h5><strong>240</strong> Tareas</h5>
                        </div>
                        </div>
                        <div class="user-button">
                            <div class="row">
                                <div class="col-md-6">
                                    <a data-toggle="modal" data-target="#uploadFileDialog" class="btn btn-success btn-sm btn-block">
                                    	<i class="fa fa-picture-o"></i> Subir foto	
                                    </a>
                                    %{-- <div class="btn-group">
                                    	<label class="btn btn-sm btn-primary btn-sm btn-block" for="inputImage" title="Subir  archivo de 		imagen">	
                                    	<input id="inputImage" class="hide" type="file" name="file" accept="image/*">
                                    		<i class="fa fa-picture-o"></i> Subir foto
                                    	</label>
                                    </div> --}%
                                </div>
                                <div class="col-md-6">
                                    <button type="button" class="btn btn-primary btn-sm btn-block">
                                    	<i class="fa fa-envelope"></i> Enviar mensaje
                                    </button>
                                </div>
                                
                            </div>
                        
                    </div>
				</div>
			</div>
		    
		</div>
	</div>

	<div class="col-lg-4">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5>Propiedades</h5>
			</div>
		    <div class="ibox-content">
				<div class="ibox-content no-padding border-left-right">
					PENDIENTE
				</div>
		    </div>
		</div>
	</div>

	<div class="col-lg-4">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5>Preferencias</h5>
			</div>
		    <div class="ibox-content">
				<div class="ibox-content no-padding border-left-right">
					PENDIENTE
				</div>
		    </div>
		</div>
	</div>


	<div class="modal fade" id="uploadFileDialog" tabindex="-1">
		<div class="modal-dialog ">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Selección de archivo para importar</h4>
				</div>
				
				<g:uploadForm class="form" action="uploadFoto" >
					<g:hiddenField name="usuario.id" value="${perfilInstance.usuario.id}"/>
					<div class="modal-body">
						
						<div class="form-group">
						    <label for="inputFile">Subir foto</label>
						    <input type="file" id="inputFile" name="foto" autocomplete="off">
						    <p class="help-block">Seleccion el archivo para subir</p>
						 </div> 
					</div>

					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
						<g:submitButton class="btn btn-primary" name="aceptar"value="Aceptar" />
					</div>
				</g:uploadForm>


			</div>
			<!-- moda-content -->
		</div>
		<!-- modal-di -->
	</div>


</content>

</body>
</html>


