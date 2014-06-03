$(function() {
    function send() {
	var item = {
	    apiUrl : $("#apiUrl").val(),
	    methodType : "GET"
	};

	$.ajax({
	    url : '/api/processor',
	    type : 'post',
	    dataType : 'json',
	    contentType : "application/json",
	    success : function(response) {
		$("#response-wrapper").html(JSON.stringify(response));
		console.log("####" + response.name);
	    },
	    data : JSON.stringify(item)
	});
    }

    $("#run").bind("click", send);

});