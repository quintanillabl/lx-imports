<ul class="nav navbar-top-links navbar-right">
    <li>
        <g:link controller="home">
            <span class="m-r-sm text-muted welcome-message">Lx-IMPORTS
        </span>
        </g:link>
        
    </li>
    <li class="dropdown">
        <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
            <i class="fa fa-envelope"></i>  <span class="label label-warning">16</span>
        </a>
        
        <ul class="dropdown-menu dropdown-messages">
            <li>
                <div class="dropdown-messages-box">
                    <a href="profile.html" class="pull-left">
                        <asset:image alt="image" class="img-circle" src="demos/a7.jpg"/>
                    </a>
                    <div class="media-body">
                        <small class="pull-right">46h </small>
                        <strong>Usuario 1</strong> Mensaje de prueba  <br>
                        <small class="text-muted">Hace 5 dias  a las  7:58 pm - (10/07/2015)</small>
                    </div>
                </div>
            </li>
            <li class="divider"></li>
            <li>
                <div class="dropdown-messages-box">
                    <a href="profile.html" class="pull-left">
                        <asset:image alt="image" class="img-circle" src="demos/a4.jpg"/>
                    </a>
                    <div class="media-body ">
                        <small class="pull-right text-navy">5h </small>
                        <strong>Usuario 2</strong> pruebas <strong>Usuario 2</strong>. <br>
                        <small class="text-muted">Hace 5 dias  a las  7:58 pm - (10/07/2015)</small>
                    </div>
                </div>
            </li>
            <li class="divider"></li>
            <li>
                <div class="dropdown-messages-box">
                    <a href="profile.html" class="pull-left">
                        <asset:image alt="image" class="img-circle" src="demos/profile.jpg"/>
                    </a>
                    <div class="media-body ">
                        <small class="pull-right">23h</small>
                        <strong>Usuario 3</strong> pruebas <strong>Ususario 3</strong>. <br>
                        <small class="text-muted">Hace 5 dias  a las  7:58 pm - (10/07/2015)</small>
                    </div>
                </div>
            </li>
            <li class="divider"></li>
            <li>
                <div class="text-center link-block">
                    <a href="mailbox.html">
                        <i class="fa fa-envelope"></i> <strong>Ver todos los mensajes</strong>
                    </a>
                </div>
            </li>
        </ul>
    </li>
    <li class="dropdown">
        <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
            <i class="fa fa-bell"></i>  <span class="label label-primary">8</span>
        </a>
        <ul class="dropdown-menu dropdown-alerts">
            <li>
                <a href="mailbox.html">
                    <div>
                        <i class="fa fa-envelope fa-fw"></i> Tienes 16 mensajes
                        <span class="pull-right text-muted small">Hace 4 minutos</span>
                    </div>
                </a>
            </li>
            <li class="divider"></li>
            <li>
                <a href="profile.html">
                    <div>
                        <i class="fa fa-twitter fa-fw"></i> Alerta de prueba 1
                        <span class="pull-right text-muted small">Hace 5 minutos</span>
                    </div>
                </a>
            </li>
            <li class="divider"></li>
            <li>
                <a href="grid_options.html">
                    <div>
                        <i class="fa fa-upload fa-fw"></i> Server re iniciado
                        <span class="pull-right text-muted small">Hace 4 minutos</span>
                    </div>
                </a>
            </li>
            <li class="divider"></li>
            <li>
                <div class="text-center link-block">
                    <a href="notifications.html">
                        <strong>Ver todas las alertas</strong>
                        <i class="fa fa-angle-right"></i>
                    </a>
                </div>
            </li>
        </ul>
    </li>

    
    <li>
        <g:form controller="logout" class="" >
            <button class="btn btn-w-m btn-link" type="submit" > <i class="fa fa-sign-out"></i></i> Cerrar sesión</button>
        </g:form>
        
    </li>
    <li>
        <a class="right-sidebar-toggle">
            <i class="fa fa-tasks"></i>
        </a>
    </li>
</ul>