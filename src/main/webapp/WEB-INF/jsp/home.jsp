<!DOCTYPE html>

<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>

<title>RESTFiddle</title>

<link rel="shortcut icon" href="/favicon.ico" />

<link href="css/font-awesome.min.css" rel="stylesheet">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/ui.fancytree.css" rel="stylesheet">
<link href="css/prettify/prettify.css" rel="stylesheet">
<link href="css/octicons/octicons.css" rel="stylesheet">
<link href="css/selectize.default.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>

	<div class="navbar navbar-inverse navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
				</button>
				<div class="dropdown" style="float: left; padding: 8px; margin-right: 12px;">
					<button class="btn btn-default" type="button" data-toggle="dropdown">
						<span class='glyphicon glyphicon-align-justify'></span>
					</button>
					<ul class="dropdown-menu" style="width:250px;">
						<li><a href="#" data-toggle="modal" data-target="#workspaceModal">New Workspace</a></li>
						<li><a href="#" id="switchWorkSpace" class="dummySwitchWorkspace">Switch Workspace</a></li>
						<li class="divider"></li>
						<li><a href="#" data-toggle="modal" data-target="#projectModal">New Project</a></li>
						<li class="divider"></li>
						<li><a href="#" data-toggle="modal" data-target="#comingSoon">Activity Log</a></li>
						<li class="divider"></li>
						<li><a href="#" data-toggle="modal" data-target="#updateProfileModal">Update Profile</a></li>
						<li><a href="#" data-toggle="modal" data-target="#changePasswordModal">Change Password</a></li>
						<li class="divider"></li>
						<li><a href="#" data-toggle="modal" data-target="#collaboratorModal">New Collaborator</a></li>
						<li><a href="#" data-toggle="modal" data-target="#manageCollaboratorsModal">Manage Collaborators</a></li>
						<li class="divider"></li>
						<li><a href="#" data-toggle="modal" data-target="#comingSoon">Global Settings</a></li>
					</ul>
				</div>
				<a class="navbar-brand" href="#">RESTFiddle</a>
			</div>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="http://www.restfiddle.com/" target="_blank">About</a></li>
					<li><a href="https://github.com/ranjan-rk/restfiddle" target="_blank">GitHub</a></li>
					<li><a href="http://restfiddle.blogspot.com/" target="_blank">Blog</a></li>
					<li><a href="https://github.com/ranjan-rk" target="_blank">Contact</a></li>
					<li>
						<form action="/logout">
							<button class="btn btn-link" style="margin-top: 7px; margin-right: 20px;" type="submit">Logout</button>
						</form>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<div class="container-fluid">
		<div class="row">
			<div class="col-xs-2 sidebar rf-col-1" style="border-right: 1px solid lightgray; height: 100%; position: fixed;">
				<div id="rf-col-1-body">
					<div id="dd-workspace-wrapper"></div>
					<br>
					<div>
						<h6 style="width: 60px; margin-top: -8px; margin-left: 10px; background: white;">Projects</h6>
					</div>
					<ul class="nav nav-pills nav-stacked rf-left-nav" id="test_project">
					</ul>
					<ul class="nav nav-sidebar project-list">
						<li></li>
					</ul>
					<br>
					<div style="border-bottom: 1px solid rgba(175,175,175,0.6);box-shadow: 0 1px 2px rgba(0,0,0,0.1);"></div>
					<br>
					<div>
						<h6 style="width: 50px; margin-top: -8px; margin-left: 10px; background: white;display:inline;">Labels&nbsp;&nbsp;</h6><button class="btn btn-default btn-xs pull-right" style="margin-right:5px;"><span class="glyphicon glyphicon-plus"></span></button>
					</div>
					<ul class="nav nav-pills nav-stacked rf-left-nav2">
						<li><a href="#" data-toggle="modal" data-target="#comingSoon"> <span class="glyphicon glyphicon-tag" style="color: green;"></span>&nbsp;&nbsp;Important</a>
							<div class="hover_controls">
								<button class="btn btn-default btn-sm" style="width: 50px;">
									<i class="fa fa-caret-down"></i>
								</button>
							</div></li>
						<li><a href="#" data-toggle="modal" data-target="#comingSoon"> <span class="glyphicon glyphicon-tag" style="color: orange;"></span>&nbsp;&nbsp;Wishlist</a></li>
					</ul>
					<br>
					<div style="border-bottom: 1px solid rgba(175,175,175,0.6);box-shadow: 0 1px 2px rgba(0,0,0,0.1);"></div>
					<br>
					<div>
						<h6 style="width: 38px; margin-top: -8px; margin-left: 10px; background: white;">Misc</h6>
					</div>
					<ul class="nav nav-pills nav-stacked rf-left-nav2">
						<li class="li_starred"><a href="#" data-toggle="modal" data-target="#comingSoon"> <span class="glyphicon glyphicon-star"></span>&nbsp;&nbsp;Starred</a>
							<div class="hover_controls">
								<button class="btn btn-default btn-xs">
									<i class="fa fa-caret-down"></i>
								</button>
							</div></li>
						<li><a href="#" data-toggle="modal" data-target="#comingSoon"> <span class="glyphicon glyphicon-time"></span>&nbsp;&nbsp;Activity Log
						</a></li>
						<li><a href="#" data-toggle="modal" data-target="#comingSoon"> <span class="glyphicon glyphicon-wrench"></span>&nbsp;&nbsp; Settings
						</a></li>
					</ul>
					<br>
					<div style="border-bottom: 1px solid rgba(175,175,175,0.6);box-shadow: 0 1px 2px rgba(0,0,0,0.1);"></div>
					<br>
					<div>
						<h6 style="width: 63px; margin-top: -8px; margin-left: 10px; background: white;">Members</h6>
					</div>
					<ul class="nav nav-sidebar">
						<li><span style="border: 1px solid lightgray; padding: 10px; margin: 3px; color: gray;" class="glyphicon glyphicon-user"></span><span
							style="border: 1px solid lightgray; padding: 10px; margin: 3px; color: gray;" class="glyphicon glyphicon-user"></span><span
							style="border: 1px solid lightgray; padding: 10px; margin: 3px; color: gray;" class="glyphicon glyphicon-user"></span><span
							style="border: 1px solid lightgray; padding: 10px; margin: 3px; color: gray;" class="glyphicon glyphicon-user"></span><span
							style="border: 1px solid lightgray; padding: 10px; margin: 3px; color: gray;" class="glyphicon glyphicon-user"></span><span
							style="border: 1px solid lightgray; padding: 10px; margin: 3px; color: gray;" class="glyphicon glyphicon-user"></span></li>
					</ul>
				</div>
			</div>
			<div class="col-xs-4 rf-col-2" style="left: 17%; height: 100%; position: fixed; overflow-y: scroll;">
				<br>
				<button class="btn btn-default btn-sm col-1-toggle-btn">
					<i id="col1-toggle-icon" class="fa fa-angle-double-left"></i>
				</button>
				&nbsp;&nbsp;
				<div class="btn-group">
					<button class="btn btn-default btn-sm" data-toggle="modal" data-target="#folderModal">New Folder</button>
					<button class="btn btn-default btn-sm" data-toggle="modal" id="requestBtn">New Request</button>
				</div>
				&nbsp;&nbsp;
				<div class="btn-group">
					<button type="button" class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown">
						More&nbsp;&nbsp;<span class="caret"></span>
					</button>
					<ul class="dropdown-menu pull-right">
						<li><a style="font-size: 12px;" data-toggle="modal" id="expandAllNodes">Expand All</a></li>
						<li><a style="font-size: 12px;" data-toggle="modal" id="collapseAllNodes">Collapse All</a></li>
						<li class="divider"></li>
						<li><a style="font-size: 12px;" data-toggle="modal" data-target="#comingSoon">Sort</a></li>
						<li><a style="font-size: 12px;" data-toggle="modal" data-target="#comingSoon">Filter</a></li>
						<li class="divider"></li>
						<li><a style="font-size: 12px;" data-toggle="modal" data-target="#deleteRequestModal">Delete Request</a></li>
						<li class="divider"></li>
						<li><a style="font-size: 12px;" data-toggle="modal" data-target="#deleteProjectModal">Delete Project</a></li>
						<li><a style="font-size: 12px;" data-toggle="modal" data-target="#editProjectModal">Edit Project</a></li>
						<li class="divider"></li>
						<li><a style="font-size: 12px;" data-toggle="modal" data-target="#deleteWorkspaceModal">Delete Workspace</a></li>
						<li><a style="font-size: 12px;" data-toggle="modal" data-target="#editWorkspaceModal">Edit Workspace</a></li>
					</ul>
				</div>

				<br> <br>
				<div id="tree"></div>
			</div>
			<div class="col-xs-6 rf-col-3" style="border-left: 1px solid lightgray; left: 50%; height: 100%; position: fixed; overflow-y: scroll;">
				<div class="form-group" id="conversationSection">
					<br>
					<div class="btn-group">
						<button class="btn btn-primary btn-sm" id="run">Run</button>
						<button type="button" class="btn btn-primary btn-sm dropdown-toggle" data-toggle="dropdown">
							<span class="caret"></span> <span class="sr-only">Toggle Dropdown</span>
						</button>
						<ul class="dropdown-menu">
							<li><a href="#" class="btn-sm" data-toggle="modal" data-target="#comingSoon">Save and Run</a></li>
						</ul>
					</div>
					&nbsp;&nbsp;&nbsp;
					<div class="btn-group">
						<button type="button" class="btn btn-default btn-sm" data-toggle="modal" id="saveConversationBtn">Save</button>
						<button type="button" class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown">
							<span class="caret"></span> <span class="sr-only">Toggle Dropdown</span>
						</button>
						<ul class="dropdown-menu">
							<li><a href="#" class="btn-sm" data-toggle="modal" id="saveAsConversationBtn">Save As</a></li>
						</ul>
					</div>
					&nbsp;&nbsp;&nbsp;
					<div class="btn-group">
						<button type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#comingSoon">Clear</button>
						<button type="button" class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown">
							<span class="caret"></span> <span class="sr-only">Toggle Dropdown</span>
						</button>
						<ul class="dropdown-menu">
							<li><a href="#" class="btn-sm" data-toggle="modal" data-target="#comingSoon">Clear Body</a></li>
							<li><a href="#" class="btn-sm" data-toggle="modal" data-target="#comingSoon">Clear Header</a></li>
							<li><a href="#" class="btn-sm" data-toggle="modal" data-target="#comingSoon">Clear Cookie</a></li>
							<li><a href="#" class="btn-sm" data-toggle="modal" data-target="#comingSoon">Clear Auth</a></li>
						</ul>
					</div>
					&nbsp;&nbsp;&nbsp;
					<div class="btn-group">
						<button type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#comingSoon">Copy Response</button>
						<button type="button" class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown">
							<span class="caret"></span> <span class="sr-only">Toggle Dropdown</span>
						</button>
						<ul class="dropdown-menu">
							<li><a href="#" class="btn-sm" data-toggle="modal" data-target="#comingSoon">Show Saved Response</a></li>
						</ul>
					</div>
					&nbsp;&nbsp;&nbsp;
					<button class="btn btn-default btn-sm">
						<span class="glyphicon glyphicon-star"></span>&nbsp;Star
					</button>
					<button class="btn btn-default btn-sm pull-right header-toggle-btn" data-toggle="tooltip" data-placement="left">
						<i id="header-toggle-icon" class="fa fa-angle-double-up"></i>
					</button>
					<br> <br>
					<div>
						<p>
							<span class='glyphicon glyphicon glyphicon-chevron-down'></span>&nbsp;&nbsp;<span id="apiRequestName" style="font-weight:bold;"></span>
						</p>
						<p id="apiRequestDescription"></p>
						<p>
							&nbsp;&nbsp;<span class="label" style="background-color: green;">Important</span>&nbsp;<span class="label" style="background-color: orange;">Wishlist</span>
						</p>
					</div>
					<br>
					<div class="container-fluid">
						<div class="row">
							<div class="col-xs-2" style="padding-left: 0px;">
								<select class="apiRequestType form-control">
									<option>GET</option>
									<option>POST</option>
									<option>PUT</option>
									<option>DELETE</option>
								</select>
							</div>
							<div class="col-xs-10" style="padding: 0px;">
								<input style="display: inline;" type="text" class="form-control" id="apiUrl" placeholder="Enter url"> <br> <br>
							</div>
						</div>
					</div>
					<ul class="nav nav-tabs">
						<li class="active"><a href="#tab-body" data-toggle="tab">Body</a></li>
						<li><a href="#tab-header" data-toggle="tab">Header</a></li>
						<li><a href="#tab-cookie" data-toggle="tab">Cookie</a></li>
						<li><a href="#tab-auth" data-toggle="tab">Auth</a></li>
					</ul>
					<!-- Tab panes -->
					<div class="tab-content">
						<div class="tab-pane active" id="tab-body">
							<br>
							<textarea id="apiBody" class="form-control" style="width: 100%; height: 70px; border: 1px solid lightgray;"></textarea>
						</div>
						<div class="tab-pane" id="tab-header"><br><p>TODO</p></div>
						<div class="tab-pane" id="tab-cookie"><br><p>TODO</p></div>
						<div class="tab-pane" id="tab-auth"><br><p>TODO</p></div>
					</div>
					<hr>
					<div>
						<p>
							<span class='glyphicon glyphicon glyphicon-chevron-right'></span>&nbsp;&nbsp;<b>Response</b>
						</p>
					</div>
					
					<ul class="nav nav-tabs">
						<li class="active"><a href="#res-tab-body" data-toggle="tab">Body</a></li>
						<li><a href="#res-tab-header" data-toggle="tab">Header</a></li>
					</ul>
					<!-- Tab panes -->
					<div class="tab-content">
						<div class="tab-pane active" id="res-tab-body">
							<br>
							<div class="container-fluid">
								<div class="row">
									<div id="response-wrapper"></div>
								</div>
							</div>
							<br><br>					
						</div>
						<div class="tab-pane" id="res-tab-header"><br><p>TODO : Show Response Headers</p></div>
					</div>
					
				</div>
			</div>

		</div>
	</div>

	<!-- Modals -->
	<div class="modal fade" id="folderModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title" id="myModalLabel">New Folder</h4>
				</div>
				<div class="modal-body">
					<input type="text" id="folderId" class="form-control" placeholder="Enter Folder Name"> <br>
					<textarea id="folderTextArea" class="form-control" rows="3" placeholder="Enter Description"></textarea>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary" id="createNewFolderBtn">Save changes</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="requestModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title" id="myModalLabel">New Request</h4>
				</div>
				<input type="hidden" class="form-control" id="source">
				<div class="modal-body">
					<div class="form-group">
						<input class="form-control" id="requestName" placeholder="Enter Request Name"> <br>
						<textarea id="requestTextArea" class="form-control" rows="3" placeholder="Enter Description"></textarea>
					</div>
					<!-- 
					 <div class="form-group">
				    	<label for="requestUrl">API End Point</label>
				    	<input class="form-control" id="requestUrl" placeholder="http://example.com/api/v1/users">
				 	</div> -->
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary" id="createNewRequestBtn">Save changes</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="updateProfileModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Update Profile</h4>
				</div>
				<div class="modal-body">
					<input type="text" id="updateProfileName" class="form-control" placeholder="Enter Name"> <br>
					<input type="text" id="updateProfileEmail" class="form-control" placeholder="Enter Email"> <br>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary" id="updateProfileBtn">Save changes</button>
				</div>
			</div>
		</div>
	</div>	
	<div class="modal fade" id="changePasswordModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Change Password</h4>
				</div>
				<div class="modal-body">
					<input type="text" id="oldPassword" class="form-control" placeholder="Enter Old Password"> <br>
					<input type="password" id="newPassword" class="form-control" placeholder="Enter New Password"> <br>
					<input type="password" id="retypedPassword" class="form-control" placeholder="Retype New Password"> <br>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary" id="changePasswordBtn">Save changes</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="collaboratorModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title" id="myModalLabel">New Collaborator</h4>
				</div>
				<div class="modal-body">
					<input type="text" id="profileName" class="form-control" placeholder="Enter Name"> <br>
					<input type="text" id="profileEmail" class="form-control" placeholder="Enter Email"> <br>
					<input type="password" id="profilePassword" class="form-control" placeholder="Enter Password"> <br>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary" id="createNewCollaboratorBtn">Save changes</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="manageCollaboratorsModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Manage Collaborators</h4>
				</div>
				<div class="modal-body">
					<table class="table table-bordered">
				      <thead>
				        <tr>
				          <th>#</th>
				          <th>Name</th>
				          <th>Email</th>
				          <th>Action</th>
				        </tr>
				      </thead>
				      <tbody>
				        <tr>
				          <td>1</td>
				          <td>RF Admin</td>
				          <td>rf-admin@example.com</td>
				          <td style="color:lightgray;">Delete</td>
				        </tr>
				        <tr>
				          <td>2</td>
				          <td>RF User</td>
				          <td>rf-user@example.com</td>
				          <td>Delete</td>
				        </tr>
				      </tbody>
				    </table>			
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>						
	<div class="modal fade" id="workspaceModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title" id="workspaceModalLabel">New Workspace</h4>
				</div>
				<div class="modal-body">
					<input type="text" id="workspaceTextField" class="form-control" placeholder="Enter Workspace Name"> <br>
					<textarea id="workspaceTextArea" class="form-control" rows="3" placeholder="Enter Description"></textarea>
					<br>
					<!-- 
					<div>
						<label class="radio-inline"> <input type="radio" name="workspaceRadioOptions" id="privateWorkspace" value="private"><span>&nbsp;Private</span>
						</label> <label class="radio-inline"> <input type="radio" name="workspaceRadioOptions" id="restrictedWorkspace" value="restricted"
							checked="checked">&nbsp;Restricted
						</label> <label class="radio-inline"> <input type="radio" name="workspaceRadioOptions" id="publicWorkspace" value="public">&nbsp;Public
						</label>
					</div>
					<br> <input type="text" id="workspace-share-tags" class="demo-default" value="Core Engg Team, QA Team, Ranjan">
					-->
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button id="saveWorkspaceBtn" type="button" class="btn btn-primary">Save changes</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="projectModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title" id="projectModalLabel">New Project</h4>
				</div>
				<div class="modal-body">
					<input type="text" id="projectTextField" class="form-control" placeholder="Enter Project Name"> <br>
					<textarea id="projectTextArea" class="form-control" rows="3" placeholder="Enter Description"></textarea>
					<br>
					<!-- 
					<div>
						<label class="radio-inline"> <input type="radio" name="projectRadioOptions" id="privateProject" value="private"><span>&nbsp;Private</span>
						</label> <label class="radio-inline"> <input type="radio" name="projectRadioOptions" id="restrictedProject" value="restricted" checked="checked">&nbsp;Restricted
						</label><label class="radio-inline"> <input type="radio" name="projectRadioOptions" id="publicProject" value="public">&nbsp;Public
						</label>
					</div>
					<br> <input type="text" id="project-share-tags" class="demo-default" value="Core Engg Team, QA Team, Ranjan">
					 -->
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button id="saveProjectBtn" type="button" class="btn btn-primary">Save changes</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="editProjectModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title" id="editProjectModalLabel">Edit Project</h4>
				</div>
				<div class="modal-body">
					<input type="text" id="editProjectTextField" class="form-control" placeholder="Enter Project Name"> <br>
					<textarea id="editProjectTextArea" class="form-control" rows="3" placeholder="Enter Description"></textarea>
					<br>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button id="editProjectBtn" type="button" class="btn btn-primary">Save changes</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="editWorkspaceModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title" id="editWorkspaceModalLabel">Edit Workspace</h4>
				</div>
				<div class="modal-body">
					<input type="text" id="editWorkspaceTextField" class="form-control" placeholder="Enter Workspace Name"> <br>
					<textarea id="editWorkspaceTextArea" class="form-control" rows="3" placeholder="Enter Description"></textarea>
					<br>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button id="editWorkspaceBtn" type="button" class="btn btn-primary">Save changes</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="deleteWorkspaceModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Delete Workspace</h4>
				</div>
				<div class="modal-body">
					Are You Sure You Want To Delete Current Workspace?
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">No</button>
					<button type="button" class="btn btn-primary" id="deleteWorkspaceBtn">Yes</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="switchWorkspaceModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title" id="projectModalLabel">Switch Workspace</h4>
				</div>
				<div class="modal-body"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="deleteRequestModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Delete Request</h4>
				</div>
				<div class="modal-body">
					Are You Sure You Want To Delete Selected Request?
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">No</button>
					<button type="button" class="btn btn-primary" id="deleteRequestBtn">Yes</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="deleteProjectModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Delete Project</h4>
				</div>
				<div class="modal-body">
					Are You Sure You Want To Delete Selected Project?
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">No</button>
					<button type="button" class="btn btn-primary" id="deleteProjectBtn">Yes</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="comingSoon" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title" id="comingSoonLabel">Coming Soon</h4>
				</div>
				<div class="modal-body">UnsupportedOperationException("Not implemented yet")</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary">Save changes</button>
				</div>
			</div>
		</div>
	</div>
	<script>
	var ctx = "${pageContext.request.contextPath}";
    </script>
	<!-- Templates -->
	<script type="text/template" id="tpl-workspace-list-item">
		<div id="dd-workspace">
          <h5 class="dummyWorkspaceName"><@=workspace.name@></h5>
		</div>
	</script>
	<script type="text/template" id="tpl-project-list-item">
		<a href="#" data-project-id = <@=project.id@>   data-project-ref-id = <@=project.projectRef.id@> >
		<span class="glyphicon glyphicon-list-alt">
		</span>&nbsp;&nbsp;<@=project.name@></a>
	</script>
	<script type="text/template" id="tpl-workspace-all-list-item">
		<a href="#" data-workspace-id = <@=workspace.id@> class="dummyWSli list-group-item"><@=workspace.name@></a>
	</script>
	<!-- JavaScript -->
	<script data-main="js/main" src="js/libs/require/require.js"></script>

</body>
</html>
