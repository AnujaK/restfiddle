$(function() {
    var serviceSideTreeData = {};
    var uiSideTreeData = {};

    function nodeConverter(serverNode, uiNode) {
	if (serverNode.children == undefined || serverNode.children.length == 0) {
	    return;
	}
	uiNode.folder = true;
	uiNode.children = [];
	for (var i = serverNode.children.length - 1; i >= 0; i--) {
	    uiNode.children.push({
		title : serverNode.children[i].name
	    });
	    nodeConverter(serverNode.children[i], uiNode.children[i]);
	}
    }
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
	source : [ {
	    title : "<p>Folder 1<p>",
	    key : "2",
	    folder : true,
	    children : [ {
		title : "<p>POST http://localhost:8080/modules</p>",
		key : "3"
	    }, {
		title : "<p>GET http://localhost:8080/modules/1</p>",
		key : "4"
	    } ]
	}, {
	    title : "<p>Folder 2<p>",
	    key : "2",
	    folder : true,
	    children : [ {
		title : "<p>GET http://localhost:8080/modules?name=payment</p>",
		key : "3"
	    }, {
		title : "<p>GET http://localhost:8080/modules?name=order</p>",
		key : "4"
	    } ]
	}, {
	    title : "<p>GET http://localhost:8080/modules?name=cart</p>",
	    key : "1"
	}, ],
    });
});