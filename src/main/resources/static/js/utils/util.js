// TODO : This file must be written properly

$('.col-1-toggle-btn').toggle(function() {
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
	var projectList = "";
    $.each(responseData, function(idx, project) {
			projectList = projectList + '<li><a href="#" data-toggle="modal" data-target="#comingSoon"><span class="glyphicon glyphicon-list-alt"></span>&nbsp;&nbsp;'+ project.name + '</a></li>';
	console.log("idx = " + idx + " project = " + project.name);
    });
	$(".project-list").html(projectList);
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
var bindClickEvent = function(selector,callback){
	$(selector).unbind("click").bind("click",function(event){
		if(callback){
			callback(event);
		}
	});
};
var handleSwitchWS = function(event){
	var wId = $(event.target).attr("id");
	console.log(wId);
	$("#switchWorkspaceModal").modal("hide");
    new app.commonService().getProjects(wId, null, onGetProjectsSuccess, onGetProjectsFailure);
};
$(".dummySwitchWorkspace").unbind("click").bind("click", function() {
/*	$("#switchWorkspaceModal .modal-body").html('<li><a href="#" data-toggle="modal" data-target="#comingSoon">ww2</a></li><li><a href="#" data-toggle="modal" data-target="#comingSoon">w2r</a></li>');
	$("#switchWorkspaceModal").modal("show");
	alert("test it");*/
	new app.commonService().getWorkspaces(onGetWSSuccess, onGetWSFail);
	function onGetWSSuccess(responsdata){
		var workSpceList = '<div class="list-group">';
		 $.each(responsdata, function(ids,workSpace) {
			 workSpceList =workSpceList +  '<a href="#" id = '+workSpace.id+' class="dummyWSli list-group-item">'+workSpace.name+'</a>';
		 });
		 workSpceList =workSpceList + '</div>';
		 $("#switchWorkspaceModal .modal-body").html(workSpceList);
		 $("#switchWorkspaceModal").modal("show");
		 bindClickEvent(".dummyWSli",handleSwitchWS);
	};
	function onGetWSFail(responsdata){
		console.log(responsdata);
		alert("fail");
	};
});