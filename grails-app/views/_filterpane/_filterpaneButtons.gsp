%{-- <div class="buttons">
    <span class="button">
        <input type="button"
               class="btn"
               value="${cancelText}" 
               data-dismiss="modal" />
    </span>
    <span class="button">
        <input type="button"
               class="btn"
               value="${clearText}" 
               onclick="return grailsFilterPane.clearFilterPane('${formName}');" />
    </span>
    <span class="button">
    	<g:actionSubmit class="btn" value="${applyText}" action="${action}" />
    </span>
</div> --}%
<div class="buttons">
  <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
  %{-- <g:submitButton class="btn btn-primary" name="aceptar" value="Aceptar" /> --}%
  <g:actionSubmit class="btn btn-primary" value="${applyText}" action="${action}" />
</div>