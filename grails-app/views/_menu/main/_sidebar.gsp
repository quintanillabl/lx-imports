<ul class="nav metismenu" id="side-menu">
	<li class="nav-header">
		<div class="dropdown profile-element">
			<span>
                %{-- <asset:image src="demos/IMG_0002.jpg" class="img-circle" absolute="false"/> --}%
                <img src="http://placehold.it/54x54&text=Foto" class="img-circle" absolute="false">
                %{-- <img src="http://www.lorempixum.com/g/400/100/sports" alt="" class="img-circle" absolute="false"> --}%
                %{-- <img src="https://placeimg.com/48/48/people/sepia" class="img-circle" absolute="false"> --}%
                %{-- <img src="http://loremflickr.com/g/48/48/people" class="img-circle" absolute="false"/> --}%
            </span>
        	<a data-toggle="dropdown" class="dropdown-toggle" href="#">
        	    <span class="clear"> 
        	    	<span class="block m-t-xs"> 
        	    		<strong class="font-bold">Rubén Cancino</strong>
        	        </span> 
        	    	<span class="text-muted text-xs block">Sistemas <b class="caret"></b>
        	    	</span> 
        		</span>
        	</a>
        	<ul class="dropdown-menu animated fadeInRight m-t-xs">
                <li><a href="profile.html">Perfil</a></li>
                <li><a href="contacts.html">Contactos</a></li>
                <li><a href="mailbox.html">Mensajes</a></li>
                <li class="divider"></li>
                <li><a href="login.html">Salir </a></li>
			</ul>
		</div>
		<div class="logo-element">
			LX
		</div>
	</li>
	<li class="">
        <a href="index.html">
        	<i class="fa fa-tachometer"></i> <span class="nav-label">Tableros</span> <span class="fa arrow"></span>
        </a>
        <ul class="nav nav-second-level collapse">
            <li class="active">
                <g:link action="homeDashboard" controller="home">
                    <i class="fa fa-area-chart"></i> Inicio
                </g:link>
            </li>
            <li ><a href="index.html">Compras</a></li>
            <li><a href="dashboard_2.html">Ventas</a></li>
            <li><a href="dashboard_3.html">Tesoreria</a></li>
        </ul>
	</li>
	<li class="${nav.activePath().encodeAsHTML().startsWith('user/catalogos/')?'active':''}">

        <a href="#"><i class="fa fa-th-large"></i> <span class="nav-label">Catálogos</span><span class="fa arrow"></span></a>
        <sec:ifAnyGranted roles="COMPRAS,ADMIN">
            <nav:menu  scope="user/catalogos" custom="true" class="nav nav-second-level collapse">
                <li class="${active ? 'active' : ''}">
                    <p:callTag tag="g:link" attrs="${linkArgs}">
                       <i class="${item.data.icon ?:''}"></i>&nbsp;<nav:title item="${item}"/>
                    </p:callTag>
                </li>
            </nav:menu> 
        </sec:ifAnyGranted>
	</li>
    <li>
        <a href="layouts.html"><i class="fa fa-shopping-cart"></i> <span class="nav-label">Compras</span></a>
    </li>
    <g:render template="/_menu/main/importaciones"/>
    <g:render template="/_menu/main/cuentasPorPagar"/>
    <g:render template="/_menu/main/contabilidad"/>
    <g:render template="/_menu/main/tesoreria"/>
</ul>