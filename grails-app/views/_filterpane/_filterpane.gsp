<!-- This has been customized to be inline isntead of a popup -->
<div class="modal fade filterDialog" id="${fp.containerId}" tabindex="-1">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"
          aria-hidden="true">&times;</button>
        <h4 class="modal-title" id="myModalLabel">Busqueda avanzada</h4>
      </div>
      <g:form action="search" class="form-horizontal" >
        <div class="modal-body">
          <div class="container-fluid">
            
            <%-- Do we still need this hidden prop? --%>
            <input type="hidden" name="filterProperties" value="${fp.filterProperties}"/>
            <input type="hidden" name="listDistinct" value="${fp.listDistinct}"/>
            <input type="hidden" name="uniqueCountColumn" value="${fp.uniqueCountColumn}"/>


            <g:each in="${fp.properties}" var="propMap">
              <g:render  template="/_filterpane/filterpaneProperty" model="${propMap}"/>
            </g:each>

            <g:if test="${fp.showSortPanel == true}">
              <g:render plugin="filterpane" template="/_filterpane/filterpaneSort" model="${fp.sortModel}"/>
            </g:if>
            
            <g:else>
              <input type="hidden" name="sort" value="${params.sort}"/>
              <input type="hidden" name="order" value="${params.order}"/>
            </g:else>

          
          </div>
        </div>
        
        <div class="modal-footer">
          <g:if test="${fp.showButtons == true}">
            <g:render template="/_filterpane/filterpaneButtons" model="${fp.buttonModel}" />
          </g:if>
          %{-- <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
          <g:submitButton class="btn btn-info" name="aceptar"
              value="Buscar" /> --}%
        </div>
      </g:form>

    </div>
    <!-- moda-content -->
  </div>
  <!-- modal-di -->
</div>

