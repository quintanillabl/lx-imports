<div class="row">
	<div class="col-md-8">
		%{-- <div class="btn-group">
			<lx:deleteButton bean="${distribucionInstance}"/>
			<button id="saveBtn" class="btn btn-success "> Actualizar</button>
		</div> --}%
		<g:form class="form-horizontal" name="updateForm" action="update" id="${distribucionInstance.id}" method="PUT">
			<g:hiddenField name="id" value="${distribucionInstance.id}" />
			<g:hiddenField name="version" value="${distribucionInstance.version}" />
			<f:with bean="${distribucionInstance}">

				<f:field property="fecha" />
				<g:if test="${distribucionInstance.partidas}">
					<f:display property="embarque" />
				</g:if>
				<g:else>
					<f:field property="embarque" />
				</g:else>
				
				<f:field property="comentario">
					<g:textArea name="comentario" value="${distribucionInstance?.comentario }" rows="5" cols="50"/>
				</f:field>

			</f:with>
			<div class="form-group">
			    <div class="col-sm-offset-2 col-sm-10">
			    	<g:actionSubmit class="btn btn-primary" 
			    		action="update" 
			    		value="Actualizar" />
			    	<a class="btn btn-danger " data-toggle="modal" data-target="#deleteDialog"><i class="fa fa-trash"></i> Eliminar</a> 
			    </div>
			    
			</div>
		</g:form>	
	</div>
</div>

<div class="modal fade" id="deleteDialog" tabindex="-1">
	<div class="modal-dialog ">
		<div class="modal-content">
			<g:form action="delete" class="form-horizontal" method="DELETE">
				<g:hiddenField name="id" value="${distribucionInstance.id}"/>

				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Eliminar el registro</h4>
				</div>
				<div class="modal-body">
					<p><strong>Distribución ${distribucionInstance.id}</strong></p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
					<g:submitButton class="btn btn-danger" name="aceptar" value="Eliminar" />
				</div>
			</g:form>
		</div><!-- moda-content -->
		
	</div><!-- modal-di -->
	
</div>
