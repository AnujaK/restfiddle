// TODO : Implement it properly
$("#rf-col-1-btn").click(function() {
    if ($(this).html() == "<span class='glyphicon glyphicon-resize-small'></span>") {
	$(this).html("<span class='glyphicon glyphicon-resize-full'></span>");
    } else {
	$(this).html("<span class='glyphicon glyphicon-resize-small'></span>");
    }
    $("#rf-col-1-body").slideToggle();
});

$('#col-1-toggle-btn').toggle(function() {
    $('.rf-col-1').hide();
    $('.rf-col-2').css('left', '0%');
    $('.rf-col-3').css('left', '33%');
    $('.rf-col-3').removeClass('col-xs-6').addClass("col-xs-8");

}, function() {
    $('.rf-col-1').show();
    $('.rf-col-2').css('left', '17%');
    $('.rf-col-3').css('left', '50%');
    $('.rf-col-3').removeClass('col-xs-8').addClass("col-xs-6");
});

// TODO : remove hard-coded workspace id
$("#saveProjectBtn").bind("click", function() {
    new app.commonService().saveProject("1", {
	"name" : $("#projectTextField").val()
    }, onSaveProjectSuccess, onSaveProjectFail);
});
var onSaveProjectSuccess = function(responseData) {
    console.log("project created successfully!");
    $('#projectModal').modal("hide");
    console.log("success");
};
var onSaveProjectFail = function() {
    console.log("failed");
    alert("failed");
};
