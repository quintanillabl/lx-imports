<div class="modal fade" id="importarModal" tabindex="-1" role="dialog" aria-labelledby="importarModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="importarModalLabel">Importar empleado</h4>
      </div>
      <g:form  action="importarEmpleado" >

      <div class="modal-body">
        	<g:hiddenField name="id" id="empleadoId"/>
          <div class="form-group">
            <label for="empleadoField" class="control-label">Nombre:</label>
            <input class="form-control" 
              name="nombre" 
              id="empleadoField" 
              placeholder="Digite el nombre del empleado"
              autocomplete="off">
          </div>
         
        
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
        
        <g:submitButton class="btn btn-primary" name="aceptar" value="Importar" />
      </div>
	</g:form>
				

    </div>
  </div>
</div>

<script type="text/javascript">
   $(function(){
      
      var input=$("#empleadoField").autocomplete({
          source:'<g:createLink controller="usuario" action="getEmpleadosAsJSON"/>',
          //source:'/usuario/getEmpleadosAsJSON',
          minLength:3,
          select:function(e,ui){
            console.log('Valor seleccionado: '+ui.item.id);
            $("#empleadoId").val(ui.item.id);
          },
          appendTo: "#importarModal"
      });
      $('body').on('shown.bs.modal', '.modal', function () {
          $('[id$=empleadoField]').focus();
      });
    });
</script>

