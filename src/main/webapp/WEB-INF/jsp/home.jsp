<!DOCTYPE html>

<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>RESTFiddle</title>
<link rel="shortcut icon" href="/favicon.ico" />
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/ui.fancytree.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>

	<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">RESTFiddle</a>
			</div>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="#" data-toggle="modal" data-target="#comingSoon">Dashboard</a></li>
					<li><a href="http://www.restfiddle.com/" target="_blank">About</a></li>
					<li><a href="http://www.restfiddle.com/" target="_blank">Help</a></li>
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
				<div id="rf-col-1-header" style="display: none;">
					<br>
					<div class="rf-col-btn" id="rf-col-1-btn">
						<span class='glyphicon glyphicon-resize-small'></span>
					</div>
				</div>
				<br>
				<div id="rf-col-1-body">
					<div id="dd-workspace-wrapper"></div>
					<hr>
					<ul class="nav nav-sidebar">
						<li></li>
						<li class="active"><a href="#" data-toggle="modal" data-target="#comingSoon"><span class="glyphicon glyphicon-list-alt"></span>&nbsp;&nbsp;Project1</a></li>
						<li><a href="#" data-toggle="modal" data-target="#comingSoon"><span class="glyphicon glyphicon-list-alt"></span>&nbsp;&nbsp;Project2</a></li>
						<li><a href="#" data-toggle="modal" data-target="#comingSoon"><span class="glyphicon glyphicon-list-alt"></span>&nbsp;&nbsp;Project3</a></li>
						<li><a href="#" data-toggle="modal" data-target="#comingSoon"><span class="glyphicon glyphicon-list-alt"></span>&nbsp;&nbsp;Project4</a></li>
					</ul>
					<hr>
					<ul class="nav nav-sidebar">
						<li><a href="#" data-toggle="modal" data-target="#projectModal"><span class="glyphicon glyphicon-plus"></span> New Project</a></li>
						<li><a href="#" data-toggle="modal" data-target="#comingSoon"><span class="glyphicon glyphicon-time"></span>&nbsp;&nbsp;Activity Log</a></li>
						<li><a href="#" data-toggle="modal" data-target="#comingSoon"><span class="glyphicon glyphicon-wrench"></span>&nbsp;&nbsp;Settings</a></li>
						<li><a href="#" data-toggle="modal" data-target="#comingSoon"><span class="glyphicon glyphicon-eject"></span>&nbsp;&nbsp;More</a></li>
					</ul>
					<hr>
				</div>
			</div>
			<div class="col-xs-4 rf-col-2" style="left: 17%; height: 100%; position: fixed; overflow-y: scroll;">
				<br>
				<button class="btn btn-default" data-toggle="modal" data-target="#myModal">New Folder</button>
				&nbsp;&nbsp;
				<button class="btn btn-default" data-toggle="modal" data-target="#comingSoon">New Request</button>
				<br> <br>
				<div id="tree"></div>
			</div>
			<div class="col-xs-6 rf-col-3" style="border-left: 1px solid lightgray; left: 50%; height: 100%; position: fixed; overflow-y: scroll;">
				<div class="form-group">
					<br>
					<div class="btn-group">
						<button class="btn btn-primary" id="run">Run</button>
						<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
							<span class="caret"></span> <span class="sr-only">Toggle Dropdown</span>
						</button>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#" data-toggle="modal" data-target="#comingSoon">Save and Run</a></li>
						</ul>
					</div>

					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<div class="btn-group">
						<button type="button" class="btn btn-default" data-toggle="modal" data-target="#comingSoon">Save</button>
						<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
							<span class="caret"></span> <span class="sr-only">Toggle Dropdown</span>
						</button>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#" data-toggle="modal" data-target="#comingSoon">Save As</a></li>
						</ul>
					</div>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<div class="btn-group">
						<button type="button" class="btn btn-default" data-toggle="modal" data-target="#comingSoon">Clear</button>
						<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
							<span class="caret"></span> <span class="sr-only">Toggle Dropdown</span>
						</button>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#" data-toggle="modal" data-target="#comingSoon">Clear Body</a></li>
							<li><a href="#" data-toggle="modal" data-target="#comingSoon">Clear Header</a></li>
							<li><a href="#" data-toggle="modal" data-target="#comingSoon">Clear Cookie</a></li>
							<li><a href="#" data-toggle="modal" data-target="#comingSoon">Clear Auth</a></li>
						</ul>
					</div>
					<hr>
					<div class="container-fluid">
						<div class="row">
							<div class="col-xs-2">
								<select class="apiRequestType">
									<option>GET</option>
									<option>POST</option>
									<option>PUT</option>
									<option>DELETE</option>
								</select>
							</div>
							<div class="col-xs-10">
								<input style="display: inline;" type="text" class="form-control" id="apiUrl" placeholder="Enter url"> <br> <br>
							</div>
						</div>
					</div>
					<br>
					<ul class="nav nav-tabs">
						<li class="active"><a href="#">Body</a></li>
						<li><a href="#">Header</a></li>
						<li><a href="#">Cookie</a></li>
						<li><a href="#">Auth</a></li>
					</ul>
					<br>
					<textarea id="apiBody" style="width: 100%; height: 70px; border: 1px solid lightgray;"></textarea>
					<hr>
					<span>Response</span>
					<button class="btn btn-default" style="float: right" data-toggle="modal" data-target="#comingSoon">Show Saved Response</button>
					<br> <br>
					<div class="container-fluid">
						<div class="row">
							<div id="response-wrapper"></div>
						</div>
					</div>
				</div>
			</div>

		</div>
	</div>

	<!-- Modals -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Add Folder</h4>
				</div>
				<div class="modal-body">
					<input type="text" id="folderId" class="form-control" placeholder="Enter Folder Name">
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary">Save changes</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="workspaceModal" tabindex="-1" role="dialog" aria-labelledby="workspaceModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="workspaceModalLabel">New Workspace</h4>
				</div>
				<div class="modal-body">
					<input type="text" id="workspaceTextField" class="form-control" placeholder="Enter Workspace Name">
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button id="saveWorkspaceBtn" type="button" class="btn btn-primary">Save changes</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="projectModal" tabindex="-1" role="dialog" aria-labelledby="projectModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="projectModalLabel">New Project</h4>
				</div>
				<div class="modal-body">
					<input type="text" id="projectTextField" class="form-control" placeholder="Enter Project Name">
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button id="saveProjectBtn" type="button" class="btn btn-primary">Save changes</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="comingSoon" tabindex="-1" role="dialog" aria-labelledby="comingSoonLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
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

	<!-- Templates -->
	<script type="text/template" id="tpl-workspace-list-item">
		<div class="dropdown" id="dd-workspace">
			<button class="btn btn-default" data-toggle="dropdown">
				Demo Workspace <span class="caret"></span>
			</button>
			<ul class="dropdown-menu" role="menu">
		<@
			_.each(list,function(workspace){
		@>
		<li role="presentation"><a role="menuitem" href="#">
		<@=workspace.name@>
		</a></li>
		<@
			});
		@>
		<li role="presentation" class="divider"></li>
		<li role="presentation">
			<a href="#" role="menuitem" data-toggle="modal" data-target="#workspaceModal"><span class="glyphicon glyphicon-plus"></span> New Workspace</a>
		</li>
		</ul>
		</div>
	</script>
	<!-- JavaScript -->

	<script src="js/libs/jquery-1.7.2.js"></script>
	<script src="js/libs/jquery-ui.min.js"></script>

	<script src="js/libs/underscore-min.js"></script>
	<script src="js/commons/base.js"></script>
	<script src="js/libs/bootstrap.min.js"></script>
	<script src="js/libs/backbone-min.js"></script>
	<script src="js/libs/jquery.fancytree-custom.min.js"></script>

	<script src="js/utils/caller.js"></script>
	<script src="js/services/common-service.js"></script>

	<script src="js/models/workspace.js"></script>
	<script src="js/collections/workspaces.js"></script>
	<script src="js/views/workspace-view.js"></script>
	<script src="js/views/app-view.js"></script>
	<script src="js/routers/workspace-router.js"></script>

	<script src="js/models/project.js"></script>
	<script src="js/collections/projects.js"></script>

	<script src="js/app.js"></script>

	<script src="js/utils/tree.js"></script>
	<script src="js/utils/util.js"></script>
</body>

</html>
