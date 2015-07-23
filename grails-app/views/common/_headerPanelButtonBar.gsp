<div class="row toolbar-panel">
	<div class="col-md-3">
		<g:form class="form-horizontal" action="${task?:show}">
			<g:hiddenField name="id" />
      		<div class="input-group">
      		    <input id="searchField" name="searchDesc" type="text" 
		    	    class="form-control " placeholder="Buscar entidad"  autofocus="on">
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
        <lx:printButton/>
        <lx:createButton/>
        <filterpane:filterButton text="Filtrar" />
    </div>
    
    
</div>