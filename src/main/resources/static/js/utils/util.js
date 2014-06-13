// TODO : Implement it properly
$("#rf-col-1-btn").click(function() {
    if ($(this).html() == "<span class='glyphicon glyphicon-resize-small'></span>") {
	$(this).html("<span class='glyphicon glyphicon-resize-full'></span>");
    } else {
	$(this).html("<span class='glyphicon glyphicon-resize-small'></span>");
    }
    $("#rf-col-1-body").slideToggle();
});

// TODO : This will go to project-view.js
var projNewModel = new app.Project();
projNewModel.set({
    "name" : $("#projectTextField").val()
});
// TODO : remove hard-coded workspace id
$("#saveProjectBtn").bind("click", function() {
    alert("hi");
    new app.commonService().saveProject("1", projNewModel.toJSON(), onSaveProjectSuccess, onSaveProjectFail);
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
