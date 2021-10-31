$(document).ready(function(){ 
 	$(".minusButton").on("click", function(evt){
		evt.preventDefault();
		produitId = $(this).attr("pid");
		qtyInput = $("#qty" + id);
		
		newQty = parseInt(qtyInput.val()) - 1;
		if(newQty > 0) qtyInput.val(newQty);
		 
	});
	
	$(".plusButton").on("click", function(evt){
		evt.preventDefault();
		produitId = $(this).attr("pid");
		qtyInput = $("#qty" + id);
		
		newQty = parseInt(qtyInput.val()) + 1;
		if(newQty > 0) qtyInput.val(newQty);
		 
	});
 });