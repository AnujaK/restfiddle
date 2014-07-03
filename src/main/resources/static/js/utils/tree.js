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
					//var conversationView = new app.ConversationView({model : conversation});
					app.conversation.render(conversation);
					app.conversationEvents.triggerChange(response.get("conversation") ? response.get("conversation").id : null);
				}});
				
			}else if(data.node.isFolder()){
				var conversation = new app.ConversationModel({});
				//var conversationView = new app.ConversationView({model : conversation});
				app.conversation.render(conversation);
				app.conversationEvents.triggerChange(null);
			}
		},
		source : []
	});
	
	var tree = $("#tree").fancytree("getTree");
	

	function nodeConverter(serverNode, uiNode) {
		if (serverNode.nodeType == 'PROJECT' || serverNode.nodeType == 'FOLDER') {
			uiNode.folder = true;
			uiNode.id = serverNode.id
			uiNode.title = '<p>' + serverNode.name + '</p>';
		}
		if (serverNode.children == undefined || serverNode.children.length == 0) {
			return;
		}

		uiNode.children = new Array();
		for ( var i = 0; i < serverNode.children.length; i++) {
			if (serverNode.children[i].nodeType != 'FOLDER') {
				uiNode.children.push({
					title : '<p>' + serverNode.children[i].name + '</p>',
					id : serverNode.children[i].id
				});
			} else if(serverNode.children[i].nodeType == 'FOLDER'){
				uiNode.children.push({});
				nodeConverter(serverNode.children[i], uiNode.children[i]);
			}

			
		}
	}
	app.tree.appendChild = function(parent, child){
		var childNode = parent.addChildren(child);
		childNode.setActive(true);
		$(childNode.li).trigger('click');
		/*console.log(childNode)
		app.conversationEvents.triggerChange(childNode.data.id);*/
	};
	app.tree.convertModelToNode = function(nodeModel){
		return {
			title : nodeModel.get('name'),
			id : nodeModel.get('id'),
			folder : nodeModel.get('nodeType') == 'FOLDER' ? true : false
		};
	};
	app.tree.resetTree = function(){
		tree.reload([]);
	};
	app.tree.getActiveFolder = function(){
		 var node = $("#tree").fancytree("getActiveNode");
		 var folder = getParentFolder(node);
		 if(folder){
			 return folder;
		 }else{
			 //return root folder 
			 return $("#tree").fancytree("getRootNode").getFirstChild();
		 }
		 
	};
	
	var getParentFolder = function(node){
		if(node){
			if(node.isFolder()){
				return node;
			}else if(node.getParent().isFolder()){
				return node.getParent();
			}else{
				return getParentFolder(node.getParent());
			}
		}else{
			return null;
		}
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