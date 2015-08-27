<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>LX Imports</title>
</head>
<body>
	
	<div class="row  border-bottom white-bg dashboard-header">

        <div class="col-sm-3">
            <h2>Bienvenio  ${applicationContext.springSecurityService.getCurrentUser()?.nombres}</h2>
            <small>Tienes 42 mensajes y 6 notificaciones.</small>
            <ul class="list-group clear-list m-t">
                <li class="list-group-item fist-item">
                    <span class="pull-right">
                        09:00 pm
                    </span>
                    <span class="label label-success">1</span> Llamar cuanto antes
                </li>
                <li class="list-group-item">
                    <span class="pull-right">
                        10:16 am
                    </span>
                    <span class="label label-info">2</span> Solicitar factura
                </li>
                <li class="list-group-item">
                    <span class="pull-right">
                        08:22 pm
                    </span>
                    <span class="label label-primary">3</span> Registrar ventas
                </li>
                <li class="list-group-item">
                    <span class="pull-right">
                        11:06 pm
                    </span>
                    <span class="label label-default">4</span> Llamar a sistemas
                </li>
                <li class="list-group-item">
                    <span class="pull-right">
                        12:00 am
                    </span>
                    <span class="label label-primary">5</span> Mandar reportre de ventas
                </li>
            </ul>
        </div>
	                    <div class="col-sm-6">
	                        <div class="flot-chart dashboard-chart">
	                            <div class="flot-chart-content" id="flot-dashboard-chart"></div>
	                        </div>
	                        <div class="row text-left">
	                            <div class="col-xs-4">
	                                <div class=" m-l-md">
	                                <span class="h4 font-bold m-t block">$ 406,100</span>
	                                <small class="text-muted m-b block">Ventas mensuales</small>
	                                </div>
	                            </div>
	                            <div class="col-xs-4">
	                                <span class="h4 font-bold m-t block">$ 150,401</span>
	                                <small class="text-muted m-b block">Utilidad anual acumulada</small>
	                            </div>
	                            <div class="col-xs-4">
	                                <span class="h4 font-bold m-t block">$ 16,822</span>
	                                <small class="text-muted m-b block">Maren a mitad de año</small>
	                            </div>

	                        </div>
	                    </div>
	                    <div class="col-sm-3">
	                        <div class="statistic-box">
	                        <h4>
	                            Progreso de embarques
	                        </h4>
	                        <p>
	                            Existen 5 embarques sin asignar.
	                        </p>
	                            <div class="row text-center">
	                                <div class="col-lg-6">
	                                    <canvas id="polarChart" width="80" height="80"></canvas>
	                                    <h5 >Torras</h5>
	                                </div>
	                                <div class="col-lg-6">
	                                    <canvas id="doughnutChart" width="78" height="78"></canvas>
	                                    <h5 >Burgo</h5>
	                                </div>
	                            </div>
	                            <div class="m-t">
	                                <small>Lorem Ipsum is simply dummy text of the printing and typesetting industry.</small>
	                            </div>

	                        </div>
	                    </div>

	</div>

	<div class="wrapper wrapper-content">
        <div class="row">
	        <div class="col-lg-4">
	            <div class="ibox float-e-margins">
	                <div class="ibox-title">
	                    <h5>Nuevis datis para el reporte</h5> <span class="label label-primary">IN+</span>
	                    <div class="ibox-tools">
	                        <a class="collapse-link">
	                            <i class="fa fa-chevron-up"></i>
	                        </a>
	                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
	                            <i class="fa fa-wrench"></i>
	                        </a>
	                        <ul class="dropdown-menu dropdown-user">
	                            <li><a href="#">Config opción 1</a>
	                            </li>
	                            <li><a href="#">Config opción 2</a>
	                            </li>
	                        </ul>
	                        <a class="close-link">
	                            <i class="fa fa-times"></i>
	                        </a>
	                    </div>
	                </div>
	                <div class="ibox-content">
	                    <div>

	                        <div class="pull-right text-right">

	                            <span class="bar_dashboard"></span>
	                            <br/>
	                            <small class="font-bold">$ 00.00</small>
	                        </div>
	                        <h4>Nuevos datos para alcance!
	                            <br/>
	                            <small class="m-r"><a href="graph_flot.html"> Checar precios! </a> </small>
	                        </h4>
	                        </div>
	                    </div>
	                </div>
	           
	        </div>
	            <div class="col-lg-4">
	                <div class="ibox float-e-margins">
	                    <div class="ibox-title">
	                        <h5>Mensajes</h5>
	                        <div class="ibox-tools">
	                            <span class="label label-warning-light">10 Messages</span>
	                           </div>
	                    </div>
	                    <div class="ibox-content">

	                        <div>
	                            <div class="feed-activity-list">

	                                
	                                
	                            </div>

	                            <button class="btn btn-primary btn-block m-t"><i class="fa fa-arrow-down"></i> Mostrar todos</button>

	                        </div>

	                    </div>
	                </div>

	            </div>
	        <div class="col-lg-4">
	            <div class="ibox float-e-margins">
	                <div class="ibox-title">
	                    <h5>Tareas</h5>
	                    <div class="ibox-tools">
	                        <a class="collapse-link">
	                            <i class="fa fa-chevron-up"></i>
	                        </a>
	                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
	                            <i class="fa fa-wrench"></i>
	                        </a>
	                        <ul class="dropdown-menu dropdown-user">
	                            <li><a href="#">Config option 1</a>
	                            </li>
	                            <li><a href="#">Config option 2</a>
	                            </li>
	                        </ul>
	                        <a class="close-link">
	                            <i class="fa fa-times"></i>
	                        </a>
	                    </div>
	                </div>
	                <div class="ibox-content ibox-heading">
	                    <h3>Nuevo versión del sistema !</h3>
	                    <small><i class="fa fa-map-marker"></i> Está usando la nueva version del sistema de imporaciones.</small>
	                </div>
	                <div class="ibox-content inspinia-timeline">

	                    <div class="timeline-item">
	                        <div class="row">
	                            <div class="col-xs-3 date">
	                                <i class="fa fa-briefcase"></i>
	                                6:00 am
	                                <br/>
	                                <small class="text-navy">2 hour ago</small>
	                            </div>
	                            <div class="col-xs-7 content no-top-border">
	                                <p class="m-b-xs"><strong>Inicio de operación</strong></p>

	                                <p>El sistema inicia operaciones el día de hoy.</p>

	                                <p><span data-diameter="40" class="updating-chart"></span></p>
	                            </div>
	                        </div>
	                    </div>
	                    
	                    
	                    
	                    
	                    

	                </div>
	            </div>
	        </div>
        </div>
	</div>


	<script>
	        $(document).ready(function() {
	            setTimeout(function() {
	                toastr.options = {
	                    closeButton: true,
	                    progressBar: true,
	                    showMethod: 'slideDown',
	                    timeOut: 4000
	                };
	                toastr.success('Responsive Admin Theme', 'Welcome to INSPINIA');

	            }, 1300);


	            var data1 = [
	                [0,4],[1,8],[2,5],[3,10],[4,4],[5,16],[6,5],[7,11],[8,6],[9,11],[10,30],[11,10],[12,13],[13,4],[14,3],[15,3],[16,6]
	            ];
	            var data2 = [
	                [0,1],[1,0],[2,2],[3,0],[4,1],[5,3],[6,1],[7,5],[8,2],[9,3],[10,2],[11,1],[12,0],[13,2],[14,8],[15,0],[16,0]
	            ];
	            $("#flot-dashboard-chart").length && $.plot($("#flot-dashboard-chart"), [
	                data1, data2
	            ],
	                    {
	                        series: {
	                            lines: {
	                                show: false,
	                                fill: true
	                            },
	                            splines: {
	                                show: true,
	                                tension: 0.4,
	                                lineWidth: 1,
	                                fill: 0.4
	                            },
	                            points: {
	                                radius: 0,
	                                show: true
	                            },
	                            shadowSize: 2
	                        },
	                        grid: {
	                            hoverable: true,
	                            clickable: true,
	                            tickColor: "#d5d5d5",
	                            borderWidth: 1,
	                            color: '#d5d5d5'
	                        },
	                        colors: ["#1ab394", "#464f88"],
	                        xaxis:{
	                        },
	                        yaxis: {
	                            ticks: 4
	                        },
	                        tooltip: false
	                    }
	            );

	            var doughnutData = [
	                {
	                    value: 300,
	                    color: "#a3e1d4",
	                    highlight: "#1ab394",
	                    label: "App"
	                },
	                {
	                    value: 50,
	                    color: "#dedede",
	                    highlight: "#1ab394",
	                    label: "Software"
	                },
	                {
	                    value: 100,
	                    color: "#b5b8cf",
	                    highlight: "#1ab394",
	                    label: "Laptop"
	                }
	            ];

	            var doughnutOptions = {
	                segmentShowStroke: true,
	                segmentStrokeColor: "#fff",
	                segmentStrokeWidth: 2,
	                percentageInnerCutout: 45, // This is 0 for Pie charts
	                animationSteps: 100,
	                animationEasing: "easeOutBounce",
	                animateRotate: true,
	                animateScale: false
	            };

	            var ctx = document.getElementById("doughnutChart").getContext("2d");
	            var DoughnutChart = new Chart(ctx).Doughnut(doughnutData, doughnutOptions);

	            var polarData = [
	                {
	                    value: 300,
	                    color: "#a3e1d4",
	                    highlight: "#1ab394",
	                    label: "App"
	                },
	                {
	                    value: 140,
	                    color: "#dedede",
	                    highlight: "#1ab394",
	                    label: "Software"
	                },
	                {
	                    value: 200,
	                    color: "#b5b8cf",
	                    highlight: "#1ab394",
	                    label: "Laptop"
	                }
	            ];

	            var polarOptions = {
	                scaleShowLabelBackdrop: true,
	                scaleBackdropColor: "rgba(255,255,255,0.75)",
	                scaleBeginAtZero: true,
	                scaleBackdropPaddingY: 1,
	                scaleBackdropPaddingX: 1,
	                scaleShowLine: true,
	                segmentShowStroke: true,
	                segmentStrokeColor: "#fff",
	                segmentStrokeWidth: 2,
	                animationSteps: 100,
	                animationEasing: "easeOutBounce",
	                animateRotate: true,
	                animateScale: false
	            };
	            var ctx = document.getElementById("polarChart").getContext("2d");
	            var Polarchart = new Chart(ctx).PolarArea(polarData, polarOptions);

	        });
	    </script>
	
</body>
</html>