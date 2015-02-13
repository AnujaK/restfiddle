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
		<a href="#" data-project-id = <@=project.id@>   data-project-ref-id = <@=project.projectRef.id@> class ="project-name">
		<span class="glyphicon glyphicon-list-alt">
		</span>&nbsp;&nbsp;<@=project.name@>&nbsp;&nbsp;
              <div class="btn-group hover-down-arrow">
                <button type="button" class="dropdown-toggle" data-toggle="dropdown">
                    <span class= "fa fa-angle-down"></span> <span class="sr-only">Toggle Dropdown</span>
                </button>
                <ul class="dropdown-menu">
                    <li><i class="fa fa-pencil fa-fw"></i> Edit Project</li>
                    <li><i class="fa fa-trash-o fa-fw"></i> Delete Project</li>
                </ul>
            </div>
        </a>
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
        <a href="#" data-tag-id=<@=tag.id@> class = "tag-name"><span class="glyphicon glyphicon-tag"></span>&nbsp;&nbsp;<@=tag.name@>&nbsp;&nbsp;
         <div class="btn-group hover-down-arrow">
                <button type="button" class="dropdown-toggle" data-toggle="dropdown">
                    <span class= "fa fa-angle-down"></span> <span class="sr-only">Toggle Dropdown</span>
                </button>
                <ul class="dropdown-menu">
                    <li><i class="fa fa-pencil fa-fw"></i> Edit Tag</li>
                    <li><i class="fa fa-trash-o fa-fw"></i> Delete Tag</li>
                </ul>
            </div>
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
			<span class="label label-primary"><@=conversation.rfRequest.methodType@></span>&nbsp;&nbsp;<@=conversation.rfRequest.apiUrlString@></a>
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
    <script type="text/template" id="tpl-query-param-list-item">
    <p>
        <div class="row">
            <div class="col-xs-5">
                <input type="text" class="form-control urlDataName" placeholder="Enter Key">
            </div>
            <div class="col-xs-5">
                <input type="text" class="form-control urlDataValue" placeholder="Enter Value">
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
    <script type="text/template" id="tpl-environment">
    <div class="row">
        <div class="col-xs-12">
            <input type="text" id="environmentName" data-environment-id="-1" class="form-control" placeholder="Enter Environment Name"> <br>
            <button type="button" class="btn btn-default btn-sm" id="addEnvFieldBtn">New Property</button>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-12">
            <div id="envFieldsWrapper">
            </div>
        </div>
    </div>
    </script>
    <script type="text/template" id="tpl-env-field">
    <p>
        <div class="row">
            <div class="col-xs-5">
                <input type="text" class="form-control envPropertyName http-header" placeholder="Enter Property Name">
            </div>
            <div class="col-xs-5">
                <input type="text" class="form-control envPropertyValue" placeholder="Enter Property Value">
            </div>
            <div class="col-xs-2">
                <button type="button" class="btn btn-default destroy"><span class="glyphicon glyphicon-remove"></span>                   </button>
            </div>
        </div>
    </p>    
    </script>
    <script type="text/template" id="tpl-manage-environment">
    <div>
        <div class="row">
            <div class="col-xs-6">
                <button type="button" class="btn btn-default" id="addNewEnvironmentBtn">New Environment</button>
            </div>
            <div class="col-xs-6">
                <select class="existingEnvironments form-control">
                    <option>Update Existing</option>
                    <option>Dev Env</option>
                    <option>QA Env</option>
                </select>
            </div>
        </div>
    </div>
    <br>
    <div id="environmentWrapper">
    </div>
    </script>
    <script type="text/template" id="tpl-assert">
    <p>
        <div class="row">
            <div class="col-xs-4">
                <input type="text" class="form-control assertPropertyName http-header" placeholder="Property Name or Expression">
            </div>
            <div class="col-xs-2">
                <input type="text" class="form-control assertCompare" placeholder="Compare">
            </div>
            <div class="col-xs-4">
                <input type="text" class="form-control assertExpectedValue" placeholder="Expected Value">
            </div>
            <div class="col-xs-2">
                <button type="button" class="btn btn-default destroy"><span class="glyphicon glyphicon-remove"></span>                   </button>
            </div>
        </div>
    </p>    
    </script>
    <script type="text/template" id="tpl-manage-asserts">
    <div>
        <div class="row">
            <div class="col-xs-6">
                <button type="button" class="btn btn-default btn-sm" id="addAssertBtn">Add Assert</button>
            </div>
        </div>
    </div>
    <br>
    <div id="assertsWrapper">
    </div>
    </script>
	<!-- JavaScript -->
	<script data-main="js/main" src="js/libs/require/require.js"></script>