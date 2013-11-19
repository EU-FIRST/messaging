


$(document).ready(function() {

	
//	$('#url-submit-form').submit(function() {
//		$.post($(this).attr('action'), $(this).serialize(), function(res){
//			// Do something with the response `res`
//			console.log(res);
//			console.info("post successful");
//			// Don't forget to hide the loading indicator!
//			return false;
//		});
//	})
//	
	
	
	$("#rightbutton").click(function() {
		$("#choice").val("right"); 
		var a = $("#formType").val();
		$('#url-submit-form').submit();
		
		
	})
	$("#leftbutton").click(function() {
		$("#choice").val("left");
		var a = $("#formType").val();
		$('#url-submit-form').submit();	
		
	})
	
});


