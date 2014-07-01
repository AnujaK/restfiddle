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
		click : function(event, data){
			if(!data.node.isFolder() && data.node.data.id){
				var node = new app.NodeModel({id : data.node.data.id});
				node.fetch({success:function(response){
					console.log(response.get("conversation"));
					var conversation = new app.ConversationModel(response.get("conversation"));
					var conversationView = new app.ConversationView({model : conversation});
					conversationView.render();
				}});
				
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

		uiNode.children = [];
		for ( var i = serverNode.children.length - 1; i >= 0; i--) {
			if (serverNode.children[i].nodeType != 'FOLDER') {
				uiNode.children.push({
					title : '<p>' + serverNode.children[i].name + '</p>',
					id : serverNode.children[i].id
				});
			}

			nodeConverter(serverNode.children[i], uiNode.children[i]);
		}
	}
	app.tree.resetTree = function(){
		tree.reload([]);
	};
	app.tree.showTree = function(projectRefNodeId) {
		$.ajax({
			url : '/api/nodes/' + projectRefNodeId + '/tree',
			type : 'get',
			dataType : 'json',
			contentType : "application/json",
			success : function(serviceSideTreeData) {
				console.log("server side tree data : ");
				console.log(serviceSideTreeData);
				var uiTree = [];
				var uiSideTreeData = {};
				nodeConverter(serviceSideTreeData, uiSideTreeData);
				console.log("client side tree data : ");
				console.log(uiSideTreeData);
				uiTree.push(uiSideTreeData);
				tree.reload(uiTree);
			}
		});
	};

});