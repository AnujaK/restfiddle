var app = app || {};
$(function() {
	app.tree = {}

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
		source : []
	});
	
	var tree = $("#tree").fancytree("getTree");
	

	function nodeConverter(serverNode, uiNode) {
		if (serverNode.nodeType == 'PROJECT' || serverNode.nodeType == 'FOLDER') {
			uiNode.folder = true;
			uiNode.title = '<p>' + serverNode.name + '</p>';
		}
		if (serverNode.children == undefined || serverNode.children.length == 0) {
			return;
		}

		uiNode.children = new Array();
		for ( var i = 0; i < serverNode.children.length; i++) {
			if (serverNode.children[i].nodeType != 'FOLDER') {
				uiNode.children.push({
					title : '<p>' + serverNode.children[i].name + '</p>'
				});
			}
			else{
			    var node = new Object();
			    uiNode.children.push({
				node : node
			});
			}

			nodeConverter(serverNode.children[i], uiNode.children[i]);
		}
	}
	app.tree.showTree = function(projectRefNodeId) {
		$.ajax({
			url : '/api/nodes/' + projectRefNodeId + '/tree',
			type : 'get',
			dataType : 'json',
			contentType : "application/json",
			success : function(serviceSideTreeData) {
				console.log("server side tree data : " + serviceSideTreeData);
				var uiTree = [];
				var uiSideTreeData = {};
				nodeConverter(serviceSideTreeData, uiSideTreeData);
				uiTree.push(uiSideTreeData);
				tree.reload(uiTree);
			}
		});
	};

});