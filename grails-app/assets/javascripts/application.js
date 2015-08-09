// This is a manifest file that'll be compiled into application.js.
//
// Any JavaScript file within this directory can be referenced here using a relative path.
//
// You're free to add application-wide JavaScript to this file, but it's generally better 
// to create separate JavaScript files as needed.
//
// Mainly scripts 
//= require jquery-2.1.1.js
//= require bootstrap.min.js

// Peity  
//= require plugins/peity/jquery.peity.min.js
//= require dataTables.js

// Custom and plugin javascript  
//= require inspinia.js
//= require plugins/pace/pace.min.js
//= require plugins/slimscroll/jquery.slimscroll.min.js

// jQuery UI  
// require plugins/jquery-ui/jquery-ui.js
//= require jquery-ui/jquery-ui.js

//= require plugins/datapicker/bootstrap-datepicker.js

//= require plugins/iCheck/icheck.min.js

//= require plugins/metisMenu/jquery.metisMenu.js


// GITTER  
//= require plugins/gritter/jquery.gritter.min.js

// Sparkline  
//= require plugins/sparkline/jquery.sparkline.min.js

// ChartJS 
//= require plugins/chartJs/Chart.min.js

// Toastr  
//= require plugins/toastr/toastr.min.js

//= require sugar.js
//= require plugins/chosen/chosen.jquery.js


//= require_self
$(function(){
	var datepicker = $.fn.datepicker.noConflict(); // return $.fn.datepicker to previously assigned value
	$.fn.bootstrapDP = datepicker;                 // give $().bootstrapDP the bootstrap-datepicker functionality
});
