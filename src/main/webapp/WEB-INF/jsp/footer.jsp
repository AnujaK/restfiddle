	<script>
	var ctx = "${pageContext.request.contextPath}";
    </script>
	<!-- Templates -->
	<script type="text/template" id="tpl-workspace-list-item">
		<div id="dd-workspace">
          <div class="dummyWorkspaceName"><@=workspace.name@>&nbsp;&nbsp;
              <div class="btn-group hover-down-arrow">
                <button type="button" class="dropdown-toggle" data-toggle="dropdown">
                    <span class= "fa fa-angle-down"></span> <span class="sr-only">Toggle Dropdown</span>
                </button>
                <ul class="dropdown-menu">
                    <li class="edit-workspace"><i class="fa fa-pencil fa-fw"></i> Edit Workspace</li>
                    <li class="delete-workspace"><i class="fa fa-trash-o fa-fw"></i> Delete Workspace</li>
                    <li class="divider"></li>
                    <li class="export-workspace"><i class="fa fa-download fa-fw"></i> Export Workspace</li>
                    <li class="export-workspace"><i class="fa fa-download fa-fw"></i> Export (Swagger)</li>
                </ul>
            </div></div>
		</div>
	</script>
	<script type="text/template" id="tpl-project-list-item">
		<a href="#/workspace/<@=project.workspaceId@>/project/<@=project.id@>" id = <@=project.id@>   data-project-ref-id = <@=project.projectRef.id@> class ="project-name">
		<span class="glyphicon glyphicon-list-alt">
		</span>&nbsp;&nbsp;<@=project.name@>&nbsp;&nbsp;
              <div class="btn-group hover-down-arrow">
                <button type="button" class="dropdown-toggle" data-toggle="dropdown">
                    <span class= "fa fa-angle-down"></span> <span class="sr-only">Toggle Dropdown</span>
                </button>
                <ul class="dropdown-menu">
                    <li class="edit-project"><i class="fa fa-pencil fa-fw"></i> Edit Project</li>
                    <li class="delete-project"><i class="fa fa-trash-o fa-fw"></i> Delete Project</li>
                    <li class="divider"></li>
                    <li class="export-project"><i class="fa fa-download fa-fw"></i> Export Project</li>
                    <li class="export-project"><i class="fa fa-download fa-fw"></i> Export (Swagger)</li>
                </ul>
            </div>
        </a>
	</script>
	<script type="text/template" id="tpl-workspace-all-list-item">
		<a href="#/workspace/<@=workspace.id@>" data-workspace-id = <@=workspace.id@> class="dummyWSli list-group-item"><@=workspace.name@></a>
	</script>
	<script type="text/template" id="tpl-user-list-item">
		<div class="row">
			<div class="col-xs-6">
				<h5 class="dummyUserName"><@=user.name@></h5>
			</div>
			<div class="col-xs-6">
			<@ var name = $(".username").text(); if ( $.trim(name) != user.name ) { @>
				<a href="#" data-user-id=<@=user.id@> class="deleteUser">Delete</a>
			<@ } else{ @>
				<span>Logged in</span>
			<@ }  @>
			</div>
		</div>
	</script>
	<script type="text/template" id="tpl-tag-list-item">
        <a href="#/workspace/<@=tag.workspaceId@>/tag/<@=tag.id@>" id=<@=tag.id@> class = "tag-name"><span class="glyphicon glyphicon-tag"></span>&nbsp;&nbsp;<@=tag.name@>&nbsp;&nbsp;
         <div class="btn-group hover-down-arrow">
                <button type="button" class="dropdown-toggle" data-toggle="dropdown">
                    <span class= "fa fa-angle-down"></span> <span class="sr-only">Toggle Dropdown</span>
                </button>
                <ul class="dropdown-menu">
                    <li class="edit-tag"><i class="fa fa-pencil fa-fw"></i> Edit Tag</li>
                    <li class="delete-tag"><i class="fa fa-trash-o fa-fw"></i> Delete Tag</li>
                </ul>
            </div>
	</script>
    <script type="text/template" id="tpl-tags-list-item">
        <input type = "checkbox" id = <@=tag.name@>>&nbsp;<@=tag.name@>&nbsp;&nbsp;
    </script>
    
     <script type="text/template" id="assert-result-list-item">
         <tr><td><span class="<@=result.iconClass@> circle"></span><@=result.status@></td><td><@=result.propertyName@></td><td><@=result.comparator@></td><td><@=result.expectedValue@></td><td><@=result.actualValue@></td><tr>                   
    </script>
    
	<script type="text/template" id="tpl-star-list-item">
		<a href="#" class="list-group-item" data-star-id=<@=node.id@> data-star-ref-id=<@=node.id@> data-toggle="tooltip" data-placement="bottom" title=<@=node.apiURL@>>
			<span class="<@=node.className@>"><@=node.methodType@></span>&nbsp;&nbsp;<@=node.name@>
             <div><span>&nbsp;&nbsp;<@=node.time@></span><span>&nbsp;<@=node.runBy@><span></div>
		</a>
	</script>
    <script type="text/template" id="tpl-tagged-node-list-item">
		<a href="#" class="list-group-item" data-node-id=<@=node.id@> data-tag-node-id=<@=node.id@> data-toggle="tooltip" data-placement="bottom" title=<@=node.apiURL@>>
			<span class="<@=node.className@>"><@=node.methodType@></span>&nbsp;&nbsp;<@=node.name@>
             <div><span>&nbsp;&nbsp;<@=node.time@></span><span>&nbsp;<@=node.runBy@><span></div>
		</a>
	</script>
	<script type="text/template" id="tpl-project-runner-list-item">
        <div class="div-list-item">
            <p><span class="label label-primary"><@=nodeStatusResponse.methodType@></span>&nbsp;&nbsp;<strong><@=nodeStatusResponse.name@></strong></p>
            <p><@=nodeStatusResponse.description@></p>
            <p><@=nodeStatusResponse.apiUrl@>&nbsp;&nbsp;<@=nodeStatusResponse.duration@>&nbsp;ms</p>
        </div>
	</script>
	<script type="text/template" id="tpl-history-list-item">
		<a href="#" class="list-group-item" data-history-id=<@=conversation.id@> data-history-ref-id=<@=conversation.id@> data-toggle="tooltip" data-placement="bottom" title=<@=conversation.rfRequestDTO.apiUrl@> >
			<div class="<@=conversation.className@>"><@=conversation.rfRequestDTO.methodType@></div>
			<@ if (conversation.name == null || conversation.name == "" ) { @>
   			 	<div class="activity"><@=conversation.rfRequestDTO.apiUrl@></div>
			<@ }  else { @>
				<div class = "activity"><@=conversation.name@></div>
			<@ } @>
            
             <span><@=conversation.time@></span><span>&nbsp;<@=conversation.runBy@></span>
        </a>
	</script>
    <script type="text/template" id="tpl-entity-field">
    <p>
        <div class="row">
            <div class="col-xs-5">
                <input type="text" class="form-control entityFieldName" placeholder="Enter Field Name">
            </div>
            <div class="col-xs-5">
                <select class="form-control entityFieldType">
                    <option>String</option>
                    <option>Number</option>
                    <option>Boolean</option>
                    <option>Date</option>
                    <option>Object</option>
                    <option>Array</option>
					<option>Relation</option>
                    <option>Geographic point</option>
                </select>
            </div>
            <div class="col-xs-2">
                <button type="button" class="btn btn-default destroy"><span class="glyphicon glyphicon-remove"></span>                   </button>
            </div>
        </div>
    </p>    
    </script>
    <script type="text/template" id="tpl-query-param-item">
    <p>
        <div class="row">
            <div class="col-xs-5">
                <input type="text" class="form-control urlDataName" placeholder="Enter Key" value = <@=query.paramKey@>>
            </div>
            <div class="col-xs-5">
                <input type="text" class="form-control urlDataValue" placeholder="Enter Value" value = <@=query.paramValue@>>
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
            <input type="text" id="environmentName" data-environment-id="-1" class="form-control" placeholder="Enter Environment Name" name="envName" required> <br>
            <button type="button" class="btn btn-default btn-sm" id="addEnvFieldBtn">New Property</button>  <button type="button" class="btn btn-default btn-sm pull-right" id="deleteEnvironment">Delete Environment</button>
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
        <div class="row assertRow">
            <div class="col-xs-4">
                <input type="text" class="form-control assertPropertyName http-header" placeholder="Property Name or Expression">
            </div>
            <div class="col-xs-2">
                <select class="assertCompare form-control" placeholder="Select">
                  <option>=</option>
                  <option>!=</option>
                  <option>Contains</option>
                  <option>! Contains</option>
                  <option><</option>
                  <option><=</option>
                  <option>></option>
                  <option>>=</option>
                  <option>Contains Key</option>
                  <option>! Contains Key</option>
                  <option>Contains Value</option>
                  <option>! Contains Value</option>
                </select>
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
    </script>
    <script type="text/template" id="tpl-tree-node">
    &nbsp;<div class="btn-group menu-arrow"><button type="button" class="dropdown-toggle" data-toggle="dropdown"><span class="fa fa-angle-down" data-toggle="dropdown"></span></button><ul class="dropdown-menu"><li class="edit-node"><i class="fa fa-pencil fa-fw"></i> Edit Node</li><li class="delete-node"><i class="fa fa-trash-o fa-fw"></i> Delete Node</li><li class="copy-node"><i class="fa fa-copy fa-fw"></i> Copy Node</li><li class="run-node"><i class="fa fa-play fa-fw"></i> Run Node</li></ul></div>
    </script>
<script type="text/template" id="tpl-tree-folder">
    &nbsp;<div class="btn-group menu-arrow"><button type="button" class="dropdown-toggle" data-toggle="dropdown"><span class="fa fa-angle-down" data-toggle="dropdown"></span></button><ul class="dropdown-menu"><li class="edit-node"><i class="fa fa-pencil fa-fw"></i> Edit Node</li><li class="delete-node"><i class="fa fa-trash-o fa-fw"></i> Delete Node</li><li class="copy-node"><i class="fa fa-copy fa-fw"></i> Copy Node</li><li class="run-folder"><i class="fa fa-play fa-fw"></i> Run Folder</li></ul></div>
    </script>
    <script type="text/template" id = "tpl-environment-list-item">
       <span><@=conversation.rfRequest.methodType@></span>
    </script>
<script type="text/template" id="tpl-oauth2">
                        <div>
                        <div class="row">
                            <div class="col-xs-6">
                            <select class="form-control existingOAuth">
                                <option value="-1" selected disabled>Select Existing</option>
                            </select>
                            </div>
                        </div>
                        </div>
                        </script>
	<!-- JavaScript -->
	<script data-main="js/main" src="js/libs/require/require.js"></script>
