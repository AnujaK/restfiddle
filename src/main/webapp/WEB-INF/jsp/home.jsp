<!DOCTYPE html>

<html lang="en">
<head>
	<jsp:include page="header.jsp"></jsp:include>
</head>
<body>
    <jsp:include page="top-nav.jsp"></jsp:include>

	<div class="container-fluid">
		<div class="row">
            
			<jsp:include page="left-panel.jsp"></jsp:include>
            
            <jsp:include page="middle-panel.jsp"></jsp:include>
            
            <jsp:include page="right-panel.jsp"></jsp:include>

		</div>
	</div>

	<jsp:include page="popup.jsp"></jsp:include>
	
	<jsp:include page="footer.jsp"></jsp:include>

</body>
</html>
