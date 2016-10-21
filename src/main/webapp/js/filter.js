$(document).ready(function(){
	$("#filterSelect").change(function(){
		$.post("Keyword",
		{
			keyword:$("#filterSelect").val()
		},
		function(data){
				
		});
	});
});