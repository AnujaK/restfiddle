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
		<li role="presentation"><a role="menuitem" href="#">Add New Workspace</a></li>
		</ul>
		</div>
	</script>
	<div class="container-fluid">
		<div class="row">
			<div id="dd-workspace-wrapper"></div>
		</div>
	</div>


	<!-- JavaScript -->
	<script src="js/libs/jquery-1.7.2.js"></script>
	<script src="js/libs/jquery-ui.min.js"></script>

	<script src="js/libs/underscore-min.js"></script>
	<script src="js/commons/base.js"></script>
	<script src="js/libs/bootstrap.min.js"></script>

	<script src="js/libs/backbone-min.js"></script>
	<script src="js/libs/jquery.fancytree-custom.min.js"></script>

	<script src="js/models/workspace.js"></script>
	<script src="js/collections/workspaces.js"></script>
	<script src="js/views/workspace-view.js"></script>
	<script src="js/views/app-view.js"></script>
	<script src="js/routers/workspace-router.js"></script>
	<script src="js/app.js"></script>

	<!-- Templates -->

</body>


</html>
