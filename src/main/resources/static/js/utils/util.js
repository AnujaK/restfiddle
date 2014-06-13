// TODO : This file must be written properly

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

var onGetProjectsSuccess = function(responseData) {
    $.each(responseData, function(idx, project) {
	$(".project-list").append(
		'<li><a href="#" data-toggle="modal" data-target="#comingSoon"><span class="glyphicon glyphicon-list-alt"></span>&nbsp;&nbsp;'
			+ project.name + '</a></li>');
	console.log("idx = " + idx + " project = " + project.name);
    });
    console.log("projects retrieved successfully!");
};

var onGetProjectsFailure = function() {
    console.log("failed");
    alert("failed");
};

// TODO : remove hard-coded workspace id
new app.commonService().getProjects("1", null, onGetProjectsSuccess, onGetProjectsFailure);

$("#saveProjectBtn").bind("click", function() {
    new app.commonService().saveProject("1", {
	"name" : $("#projectTextField").val()
    }, onSaveProjectSuccess, onSaveProjectFailure);
});
var onSaveProjectSuccess = function(responseData) {
    $(".project-list").append(
	    '<li><a href="#" data-toggle="modal" data-target="#comingSoon"><span class="glyphicon glyphicon-list-alt"></span>&nbsp;&nbsp;'
		    + responseData.name + '</a></li>');
    console.log("project created successfully!");
    $('#projectModal').modal("hide");
};
var onSaveProjectFailure = function() {
    console.log("failed");
    alert("failed");
};
