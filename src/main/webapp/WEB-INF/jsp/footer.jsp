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
	<script type="text/template" id="tpl-user-list-item">
		<div class="row">
			<div class="col-xs-6">
				<h5 class="dummyUserName"><@=user.name@></h5>
			</div>
			<div class="col-xs-6">
				<a href="#" data-user-id=<@=user.id@> class="deleteUser">Delete</a>
			</div>
		</div>
	</script>
	<script type="text/template" id="tpl-tag-list-item">
        <a href="#" data-tag-id=<@=tag.id@>><span class="glyphicon glyphicon-tag"></span>&nbsp;&nbsp;<@=tag.name@></a>
	</script>
	<script type="text/template" id="tpl-star-list-item">
		<a href="#" class="list-group-item" data-star-id=<@=node.id@> data-star-ref-id=<@=node.id@> >&nbsp;&nbsp;<@=node.name@></a>
	</script>
    <script type="text/template" id="tpl-tagged-node-list-item">
		<a href="#" class="list-group-item" data-node-id=<@=node.id@> data-tag-node-id=<@=node.id@> >&nbsp;&nbsp;<@=node.name@></a>
	</script>
	<script type="text/template" id="tpl-project-runner-list-item">
        <div class="div-list-item">
            <p><span class="label label-primary"><@=nodeStatusResponse.methodType@></span>&nbsp;&nbsp;<strong><@=nodeStatusResponse.name@></strong></p>
            <p><@=nodeStatusResponse.description@></p>
            <p><@=nodeStatusResponse.apiUrl@>&nbsp;&nbsp;<@=nodeStatusResponse.duration@>&nbsp;ms</p>
        </div>
	</script>
	<script type="text/template" id="tpl-history-list-item">
		<a href="#" class="list-group-item" data-history-id=<@=conversation.id@> data-history-ref-id=<@=conversation.id@> >
			<span class="label label-primary"><@=conversation.rfRequest.methodType@></span>&nbsp;&nbsp;<@=conversation.rfRequest.apiUrl@></a>
	</script>
    <script type="text/template" id="tpl-entity-field">
    <p>
        <div class="row">
            <div class="col-xs-5">
                <input type="text" class="form-control entityFieldName" placeholder="Enter Field Name">
            </div>
            <div class="col-xs-5">
                <select class="form-control entityFieldType">
                    <option>STRING</option>
                    <option>INTEGER</option>
                    <option>LONG</option>
                    <option>DATE</option>
                </select>
            </div>
            <div class="col-xs-2">
                <button type="button" class="btn btn-default destroy"><span class="glyphicon glyphicon-remove"></span>                   </button>
            </div>
        </div>
    </p>    
    </script> 
    <script type="text/template" id="tpl-form-list-item">
    <p>
        <div class="row">
            <div class="col-xs-5">
                <input type="text" class="form-control formDataName" placeholder="Enter Key">
            </div>
            <div class="col-xs-5">
                <input type="text" class="form-control formDataValue" placeholder="Enter Value">
            </div>
            <div class="col-xs-2">
                <button type="button" class="btn btn-default destroy"><span class="glyphicon glyphicon-remove"></span>                   </button>
            </div>
        </div>
    </p>    
    </script> 
    <script type="text/template" id="tpl-file-list-item">
    <p>
        <div class="row">
            <div class="col-xs-5">
                <input type="text" class="form-control fileDataName" placeholder="Enter Key">
            </div>
            <div class="col-xs-5">
                <input type="file" class="form-control fileDataValue" placeholder="Enter Value">
            </div>
            <div class="col-xs-2">
                <button type="button" class="btn btn-default destroy"><span class="glyphicon glyphicon-remove"></span>                   </button>
            </div>
        </div>
    </p>    
    </script> 
    <script type="text/template" id="tpl-header-list-item">
    <p>
        <div class="row">
            <div class="col-xs-5">
                <input type="text" class="form-control headerName http-header" placeholder="Enter Header Name">
            </div>
            <div class="col-xs-5">
                <input type="text" class="form-control headerValue" placeholder="Enter Header Value">
            </div>
            <div class="col-xs-2">
                <button type="button" class="btn btn-default destroy"><span class="glyphicon glyphicon-remove"></span>                   </button>
            </div>
        </div>
    </p>    
    </script> 
	<!-- JavaScript -->
	<script data-main="js/main" src="js/libs/require/require.js"></script>