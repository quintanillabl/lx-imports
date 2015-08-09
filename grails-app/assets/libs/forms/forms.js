// This is a manifest file that'll be compiled into datatables.js.
//= require autoNumeric.js
//= require_self
$(function(){
	// $(".entero").autoNumeric({wEmpty:'zero',lZero: 'deny',vMin:'0'});
	// // $("input[data-moneda]").autoNumeric({wEmpty:'zero',mRound:'B',aSign: '$'});
	// // $("input[data-porcentaje]").autoNumeric({altDec: '%', vMax: '99.99'});
 //  $(".moneda").autoNumeric({wEmpty:'zero',mRound:'B',aSign: '$'});
 //  $(".tc").autoNumeric({vMin:'0.0000'});
 //  $("input[data-porcentaje]").autoNumeric({altDec: '%', vMax: '99.99'});

    /**
    * Handler para quitar el signo de pesos a los campos numericos de la forma
    
    $(".numeric-form").submit(function(event){

      $(".moneda-field",this).each(function(index,element){
        var val=$(element).val();
        var name=$(this).attr('name');
        var newVal=$(this).autoNumeric('get');
        $(this).val(newVal);
        //console.log('Enviando elemento numerico con valor:'+name+" : "+val+ " new val:"+newVal);
      });
      
    });
  **/
  
});




jQuery.fn.forceNumeric = function () {

    return this.each(function () {
        $(this).keydown(function (e) {
            var key = e.which || e.keyCode;

            if (!e.shiftKey && !e.altKey && !e.ctrlKey &&
            // numbers   
                key >= 48 && key <= 57 ||
            // Numeric keypad
                key >= 96 && key <= 105 ||
            // comma, period and minus, . on keypad
               key == 190 || key == 188 || key == 109 || key == 110 ||
            // Backspace and Tab and Enter
               key == 8 || key == 9 || key == 13 ||
            // Home and End
               key == 35 || key == 36 ||
            // left and right arrows
               key == 37 || key == 39 ||
            // Del and Ins
               key == 46 || key == 45)
                return true;

            return false;
        });
    });
}