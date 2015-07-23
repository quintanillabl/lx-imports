<g:applyLayout name="application">
    <html>
    <head>
        <title><g:layoutTitle/></title>
        <g:layoutHead/>
    </head>
    </html>
    <body>

        <div class="container">
            <div class="row">
            	
            	<div class="col-md-3">

            		<div class="list-group">
                       <nav:menu id="nav" custom="true" scope="user/catalogos">
                           <p:callTag tag="g:link"
                                      attrs="${linkArgs + [class:active ? 'list-group-item  active' : 'list-group-item ']}">
                             
                              <i class="${item.data.icon ?:'fa fa-bars'}"></i>&nbsp;<nav:title item="${item}"/>
                           </p:callTag>
                       </nav:menu> 
					</div>

            	</div><!-- End .col-md-2 side bar -->

                <div class="col-md-9">
                    
                    
                </div>
            	
            	
            	
            </div>
            
            
        
        <g:pageProperty name="page.javascript"/>    
        </div>
        
    </body>
</g:applyLayout>