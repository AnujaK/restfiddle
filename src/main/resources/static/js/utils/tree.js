$(function() {
    var uiSideTreeData = {};

    function nodeConverter(serverNode, uiNode) {
	if (serverNode.nodeType == 'PROJECT' || serverNode.nodeType == 'FOLDER') {
	    uiNode.folder = true;
	}
	if (serverNode.children == undefined || serverNode.children.length == 0) {
	    return;
	}

	uiNode.children = [];
	for (var i = serverNode.children.length - 1; i >= 0; i--) {
	    uiNode.children.push({
		title : '<p>GET ' + serverNode.children[i].name + '</p>' + '<p>http://localhost:8080/api/workspaces</p>'
	    });
	    nodeConverter(serverNode.children[i], uiNode.children[i]);
	}
    }

    $.ajax({
	// TODO : populate project-reference node-id for the selected project
	url : '/api/nodes/1/tree',
	type : 'get',
	dataType : 'json',
	contentType : "application/json",
	success : function(serviceSideTreeData) {
	    console.log("server side tree data : " + serviceSideTreeData);
	    nodeConverter(serviceSideTreeData, uiSideTreeData);

	    $("#tree").fancytree({
		extensions : [ "glyph" ],
		glyph : {
		    map : {
			doc : "glyphicon glyphicon-file",
			docOpen : "glyphicon glyphicon-file",
			checkbox : "glyphicon glyphicon-unchecked",
			checkboxSelected : "glyphicon glyphicon-check",
			checkboxUnknown : "glyphicon glyphicon-share",
			error : "glyphicon glyphicon-warning-sign",
			expanderClosed : "glyphicon glyphicon-plus-sign",
			expanderLazy : "glyphicon glyphicon-plus-sign",
			expanderOpen : "glyphicon glyphicon-minus-sign",
			folder : "glyphicon glyphicon-folder-close",
			folderOpen : "glyphicon glyphicon-folder-open",
			loading : "glyphicon glyphicon-refresh"
		    }
		},
		source : uiSideTreeData
	    });
	}
    });

});