$(function() {
    function send() {
	var item = {
	    name : "my item",
	    description : "my new item"
	};

	$.ajax({
	    url : '/api/items',
	    type : 'post',
	    dataType : 'json',
	    contentType : "application/json",
	    success : function(response) {
		console.log("####" + response.name);
	    },
	    data : JSON.stringify(item)
	});
    }

    $("#run").bind("click", send);

});