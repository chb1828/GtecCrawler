$(document).ready(function() {
	
	$("#searchStop").click(function() {
		var modal = document.getElementById('stopModal');
		var btn = document.getElementById("searchStop");
		var span = document.getElementsByClassName("close")[0];    

		modal.style.display = "block";

		window.onclick = function(event) {
		    if (event.target == modal) {
		        modal.style.display = "none";
		    };
		};
		
		$.ajax({
			url : "/visualization.s",
			type : "POST",
			success : function(data)
			{
				
			},
			error : function(request, status, error) 
			{
				alert("실패");
			}
		});
	});
	
	$("#resetDash").click(function() {
		var modal = document.getElementById('resetModal');
		var btn = document.getElementById("resetDash");
		var span = document.getElementsByClassName("close")[0];    

		modal.style.display = "block";

		window.onclick = function(event) {
		    if (event.target == modal) {
		        modal.style.display = "none";
		    };
		};
		
		$.ajax({
			url : "/visualization.r",
			type : "POST",
			success : function(data)
			{
				
			},
			error : function(request, status, error) 
			{
				alert("실패");
			}
		});
	});
	
});