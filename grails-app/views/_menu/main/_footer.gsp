
<div class="footer fixed">
	<sec:ifLoggedIn>
	    <div class="pull-right">
	    	T.C. <strong>
	        <g:if test="${!session.tipoDeCambio}">
	        	 NO REGISTRADO
	        </g:if> 
	        <g:else>
	        	${session.tipoDeCambio}
	        </g:else>
	        </strong>
	    </div>
	    <div>
	        <strong>Empresa: "${session.empresa}"</strong>
	        %{-- <p>The current active path is "${nav.activePath().encodeAsHTML()}"</p> --}%
	    </div>
    </sec:ifLoggedIn>
</div>
