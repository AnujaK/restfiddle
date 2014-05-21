<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>RESTFiddle</title>

<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<c:url value="/resources/text.txt" var="url" />
	<spring:url value="/resources/text.txt" htmlEscape="true" var="springUrl" />

	<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">RESTFiddle</a>
			</div>
			<div class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="#">Home</a></li>
					<li><a href="#about">About</a></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="container-fluid">
		<div class="row">
			<div class="col-xs-2" style="border-right: 1px solid lightgray; height: 100%; position: fixed; overflow-y: scroll;">
				<br>
				<div>
					<h4 style="color: gray">My Workspace 1</h4>
				</div>
				<hr>
				<div>
					<p>
						<span class="glyphicon glyphicon-road"></span>&nbsp&nbspProject1
					</p>
				</div>
				<hr>
				<div>
					<p>
						<span class="glyphicon glyphicon-road"></span>&nbsp&nbspProject2
					</p>
				</div>
				<hr>
				<div>
					<p>
						<span class="glyphicon glyphicon-road"></span>&nbsp&nbspProject3
					</p>
				</div>
				<hr>
				<div>
					<p>
						<span class="glyphicon glyphicon-time"></span>&nbsp&nbspActivity Log
					</p>
				</div>
				<hr>
				<div>
					<p>
						<span class="glyphicon glyphicon-wrench"></span>&nbsp&nbspSettings
					</p>
				</div>
				<hr>
				<div>
					<p>
						<span class="glyphicon glyphicon-eject"></span>&nbsp&nbspMore
					</p>
				</div>
				<hr>
			</div>
			<div class="col-xs-4" style="left: 17%; height: 100%; position: fixed; overflow-y: scroll;">
				<br>
				<div>
					<p>http://localhost:8080/modules</p>
				</div>
				<hr>
				<div>
					<p>http://localhost:8080/modules/1</p>
				</div>
				<hr>
				<div>
					<p>http://localhost:8080/modules?name=payment</p>
				</div>
				<hr>
				<div>
					<p>http://localhost:8080/modules?name=order</p>
				</div>
				<hr>
				<div>
					<p>http://localhost:8080/modules?name=cart</p>
				</div>
				<hr>
			</div>
			<div class="col-xs-6" style="border-left: 1px solid lightgray; left: 50%; height: 100%; position: fixed; overflow-y: scroll;">
				<br>
				<form role="form" action="/api/rf" method="post">
					<div class="form-group">
						<label for="apiUrl">URL</label> <input type="text" class="form-control" id="apiUrl" placeholder="Enter url">
					</div>
					<button type="submit" class="btn btn-default">Submit</button>
				</form>
			</div>

		</div>
	</div>

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="js/jquery-1.7.2.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="js/bootstrap.min.js"></script>
</body>

</html>
