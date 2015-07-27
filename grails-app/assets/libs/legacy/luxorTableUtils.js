/**
 * Utilerias para table elements
 */

$(function(){

	$(".simpleGrid tbody").on('click','tr',function(){
		$(this).toggleClass("success selected");
	});

	$(".simpleGrid tbody").on('hover','tr',function(){
		$(this).toggleClass("info");
	});
		
	$(document).on("keydown",function(event){
		var keycode = (event.keyCode ? event.keyCode : event.which);
		if(event.ctrlKey ){
			if(keycode==65){
				//console.log('detecting under ctrl');
				$(".simpleGrid tbody tr").addClass("success selected");
			}else if(keycode==67){
				$(".simpleGrid tbody tr").removeClass("success selected");
			}
		}
	});
		
	function selectAllRows(){
		$(".simpleGrid tbody tr").addClass("success selected");
	};
		
	function clearAllRows(){
		$(".simpleGrid tbody tr").removeClass("success selected");
	};

	

	function selectedRows(){
		var res=[];
		var data=$(".simpleGrid .selected").each(function(){
			var tr=$(this);
			res.push(tr.attr("id"));
		});
		return res;
	};
		
});
