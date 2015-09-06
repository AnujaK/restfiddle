	<!-- Modals -->
	<div class="modal fade" id="assertsModal" tabindex="-1">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Manage Asserts</h4>
				</div>
				<div class="modal-body">
					<div id="manageAssertsWrapper">
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary" id="saveAssertsBtn">Save changes</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="entityModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">New Entity</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group">
				        <button type="button" class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown">
				            Predefined Entity&nbsp;&nbsp;<span class="caret"></span>
				        </button>
						<ul class="dropdown-menu">
	            			<li><a class="rf-font-12" data-toggle="modal" data-target="#createUserModal">User Entity</a></li>
	            			<li><a class="rf-font-12" data-toggle="modal" data-target="#createRoleModal">Role Entity</a></li>
	            		</ul>
	            	</div>
	            	<br> <br>
					<form id = "createNewEntityForm">
					<input type="text" id="newEntityName" class="form-control" placeholder="Enter Entity Name" name = "entityName" required>
                    <p class="text-danger" id="new-entity-error"></p>
					 <br>
					<textarea id="newEntityDescription" class="form-control" rows="3" placeholder="Enter Description"></textarea>
					<br>
					<p>
						<button type="button" class="btn btn-default btn-sm" id="addEntityFieldBtn">Add Field</button>
					</p>
					<div id="entityFieldsWrapper">
					</div>
				    </form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary" id="createNewEntityBtn">Save changes</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="createUserModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Create User Entity</h4>
				</div>

				<div class="modal-body">
					This will create system defined entity User. Are you sure you want to create?
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">No</button>
					<button type="button" class="btn btn-primary" id="createUserEntity">Yes</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="createRoleModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Create Role Entity</h4>
				</div>

				<div class="modal-body">
					This will create system defined entity Role. Are you sure you want to create?
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">No</button>
					<button type="button" class="btn btn-primary" id="createRoleEntity">Yes</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="folderModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title" id="myModalLabel">New Folder</h4>
				</div>

				<div class="modal-body">
					<form id= "newFolderCreationForm">
						<input type="text" id="folderId" class="form-control" placeholder="Enter Folder Name" name = "folderName" required><br>
						<textarea id="folderTextArea" class="form-control" rows="3" placeholder="Enter Description"></textarea>
					</form>
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
				<input type="hidden" class="form-control" id="source"/>
				<div class="modal-body">
					<div class="form-group">
						<form id = "newRequestForm">
						<input class="form-control" id="requestName" placeholder="Enter Request Name" name = "newRequestName" required/><br>
						<textarea id="requestTextArea" class="form-control" rows="3" placeholder="Enter Description"></textarea>
					    </form>
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
	<div class="modal fade" id="socketModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">New Socket</h4>
				</div>
				<input type="hidden" class="form-control" id="socketSource">
				<div class="modal-body">
					<form id = "newSocketForm">
						<div class="form-group">
							<input class="form-control" id="newSocketName" placeholder="Enter Socket Name" name = "newSocketName" required/> <br>
							<textarea class="form-control" id="socketTextArea" rows="3" placeholder="Enter Description"></textarea>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary" id="createNewSocketBtn">Save changes</button>
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
	<!-- <div class="modal fade" id="collaboratorModal" tabindex="-1">
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
	</div> -->
	<div class="modal fade" id="manageCollaboratorsModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Manage Collaborators</h4>
				</div>
                <div class="modal-body">
				<div id= "collaborators"></div>
				<a id = "addCollaborator">Add Collaborators</a>
				<form id = "addCollaboratorForm">
					<div class = "row">
						<div class = "col-lg-8 col-md-8 col-sm-10">
							<br>
							<input type="text" id="collaboratorName" class="form-control" name = "collaboratorName" placeholder="Name" required> <br>
							<input type="email" id="collaboratorEmailId" class="form-control" name = "collaboratorEmailId" placeholder="Email Id" required><br>
							<input type="password" id="collaboratorPassword" class="form-control" name = "collaboratorPassword" placeholder="Password" required>
							<br>
							<button type="button" class="btn btn-default pull-right" id = "saveCollaborator">Save</button>
						</div>
					</div>
				</form>
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
					<form id = "workspaceForm">
						<input type="text" id="workspaceTextField" class="form-control" name ="workspaceName" placeholder="Enter Workspace Name" required>
						<p class="text-danger" id="workspace-error"></p> <br>
						<textarea id="workspaceTextArea" class="form-control" rows="3" placeholder="Enter Description"></textarea>
						<br>
					</form>
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
				<form id = "projectCreationForm">
					<input type="text" id="projectTextField" class="form-control" name = "projectName" placeholder="Enter Project Name" required>
					<p class="text-danger" id="project-error"></p> <br> 
					<textarea id="projectTextArea" class="form-control" rows="3" placeholder="Enter Description"></textarea>
					<br>
				</form>
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

<div class="modal fade" id="importModal" tabindex="-1">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Import</h4>
			</div>
			<div class="modal-body">
				<div class="alert alert-info">
					Import a Postman Collection. Support for other file types will be added soon!
				</div>
				<br>
				<input type="file" name="file" id="importFileId" class="form-control">
				<br>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button id="importFileBtn" type="button" class="btn btn-primary">Import</button>
			</div>
		</div>
	</div>
</div>	

<div class="modal fade" id="manageEnvironmentsModal" tabindex="-1">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Manage Environments</h4>
			</div>
			<div class="modal-body">
			  <form id="environmentManagementForm">
				<div id="manageEnvironmentWrapper">
				</div>
			  </form>	
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button id="saveEnvironmentBtn" type="button" class="btn btn-primary">Save changes</button>
			</div>
		</div>
	</div>
</div>	

<div class="modal fade" id="tagModal" tabindex="-1">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title" id="tagModalLabel">New Tag</h4>
			</div>
			<div class="modal-body">
				<form id = "tagForm">
					<input type="text" id="tagTextField" class="form-control" name = "tagName" placeholder="Enter Tag Name" required> <br>
					<textarea id="tagTextArea" class="form-control" rows="3" placeholder="Enter Description"></textarea>
					<br>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button id="saveTagBtn" type="button" class="btn btn-primary">Save changes</button>
			</div>
		</div>
	</div>
</div>	
<div class="modal fade" id="editNodeModal" tabindex="-1">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title" id="editNodeModalLabel">Edit Node</h4>
			</div>
			<div class="modal-body">
				<form id = "editNodeForm">
					<input type="hidden" id="editNodeId"/>
					<input type="text" id="editNodeTextField" class="form-control" placeholder="Enter Node Name" name = "editNodeName" required> <br>
					<textarea id="editNodeTextArea" class="form-control" rows="3" placeholder="Enter Description"></textarea>
					<br>
			    </form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button id="editNodeBtn" type="button" class="btn btn-primary">Save changes</button>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="editEntityModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title" id="editNodeModalLabel">Edit Entity</h4>
			    </div>
                <div class="modal-body">
				<form id = "editNodeForm">
					<input type="hidden" id="editEntityId"/>
					<input type="text" id="editEntityTextField" class="form-control" placeholder="Enter Node Name" name = "editNodeName" required> <br>
					<textarea id="editEntityTextArea" class="form-control" rows="3" placeholder="Enter Description"></textarea>
                    <br>
					<p>
						<button type="button" class="btn btn-default btn-sm" id="addFieldEditEntityBtn">Add Field</button>
					</p>
					<div id="editEntityFieldsWrapper">
					</div>
					<br>
                    <input type="checkbox" id="regenerateAPI" placeholder="Regenerate API" name = "regenerateAPI" disabled="true"> Regenerate API </input> <br>
			    </form>
			</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary" id="editEntityBtn">Save changes</button>
				</div>
			</div>
		</div>
</div>

<div class="modal fade" id="copyNodeModal" tabindex="-1">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title" id="copyNodeModalLabel">Copy Node</h4>
			</div>
			<div class="modal-body">
				<form>
				    <input type="hidden" id="copyNodeId"/>
				    <input type="hidden" id="copyNodeType"/>
					<input type="text" id="copyNodeTextField" class="form-control" placeholder="Enter Node Name" name = "copyNodeName" required/> <br>
					<textarea id="copyNodeTextArea" class="form-control" rows="3" placeholder="Enter Description"></textarea>
					<br>
				  <div class = "row">
                    <div class = "col-lg-2">
                    	Include
                    </div>
                    <div class = "col-lg-6">
                    	<input type = "checkbox" checked id = "nodeUrl"><label for = "nodeUrl">&nbsp;&nbsp;URL</label><br>
                    	<input type = "checkbox" checked id = "nodeMethodType"><label for = "nodeMethodType">&nbsp;&nbsp;Method Type</label><br>
                    	<input type = "checkbox" checked id = "nodeBody"><label for = "nodeBody">&nbsp;&nbsp;Body</label><br>
                    	<input type = "checkbox" checked id = "nodeHeaders"><label for = "nodeHeaders">&nbsp;&nbsp;Headers</label><br>
                    	<input type = "checkbox" checked id = "nodeAuth"><label for = "nodeAuth">&nbsp;&nbsp;Auth</label><br>
                    	<input type = "checkbox" checked id = "nodeTags"><label for = "nodeTags">&nbsp;&nbsp;Tags</label><br>
                    </div>
				  </div>
                </form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button id="copyNodeBtn" type="button" class="btn btn-primary">Copy</button>
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
				<form id = "projectEditForm">
					<input type="hidden" id="editProjectId"/>
					<input type="text" id="editProjectTextField" class="form-control" placeholder="Enter Project Name" name="projectName" required/> <p class="text-danger" id="project-edit-error"></p><br>
					<textarea id="editProjectTextArea" class="form-control" rows="3" placeholder="Enter Description"></textarea>
					<br>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button id="editProjectBtn" type="button" class="btn btn-primary">Save changes</button>
			</div>
		</div>
	</div>
</div>
<div class="modal fade" id="editTagModal" tabindex="-1">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title" id="editTagModalLabel">Edit Tag</h4>
			</div>
			<div class="modal-body">
				<form id ="tagEditForm">
					<input type="hidden" id="editTagId"/>
					<input type="text" id="editTagTextField" class="form-control" placeholder="Enter Tag Name" name ="tagName" required/> 
					<p class="text-danger" id="tag-name-edit-error"></p><br>
					<textarea id="editTagTextArea" class="form-control" rows="3" placeholder="Enter Description"></textarea>
					<br>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button id="editTagBtn" type="button" class="btn btn-primary">Save changes</button>
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
				<form id = "workspaceEditForm">
					<input type="hidden" id="editWorkspaceId"/>
					<input type="text" id="editWorkspaceTextField" class="form-control" placeholder="Enter Workspace Name" name ="workspaceName" required/>
					<p class="text-danger" id="workspace-edit-error"></p>
					<br>
					<textarea id="editWorkspaceTextArea" class="form-control" rows="3" placeholder="Enter Description"></textarea>
					<br>
				</form>
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
				<input type="hidden" id="deleteWorkspaceId"/>
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
<div class="modal fade" id="deleteNodeModal" tabindex="-1">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title" id="myModalLabel">Delete Node</h4>
			</div>
			<div class="modal-body">
				<input type="hidden" id="deleteNodeId"/>
				Are You Sure You Want To Delete Selected Node?
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
				<input type="hidden" id="deleteProjectId"/>
				Are You Sure You Want To Delete Selected Project?
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">No</button>
				<button type="button" class="btn btn-primary" id="deleteProjectBtn">Yes</button>
			</div>
		</div>
	</div>
</div>
<div class="modal fade" id="deleteTagModal" tabindex="-1">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title" id="myModalLabel">Delete Tag</h4>
			</div>
			<div class="modal-body">
				<input type="hidden" id="deleteTagId"/>
				Are You Sure You Want To Delete Selected Tag?
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">No</button>
				<button type="button" class="btn btn-primary" id="deleteTagBtn">Yes</button>
			</div>
		</div>
	</div>
</div>
<div class="modal fade" id="deleteEnvModal" tabindex="-1">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title" id="myModalLabel">Delete Environment</h4>
			</div>
			<div class="modal-body">
				<input type="hidden" id="deleteEnvId"/>
				 Selected Environment?
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">No</button>
				<button type="button" class="btn btn-primary" id="deleteEnvBtn">Yes</button>
			</div>
		</div>
	</div>
</div>
<div class="modal fade" id="globalSettingsModal" tabindex="-1">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Global Settings</h4>
			</div>
			<div class="modal-body">
			<input type="text" class="form-control" placeholder="Request Timeout" name ="requestTimeout"><br>
			<input type="text" class="form-control" placeholder="Expression Language" name ="expressionLanguage">
			<br>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button id="" type="button" class="btn btn-primary">Save changes</button>
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
<div class="modal fade" id="collaboratorModal" tabindex="-1">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title" id="tagModalLabel">Manage Collaborators</h4>
			</div>
			<div class="modal-body">
				<div id= "collaborators"></div>
				<a id = "addCollaborator">Add Collaborators</a>
				<form id = "addCollaboratorForm">
					<div class = "row">
						<div class = "col-lg-8 col-md-8 col-sm-10">
							<br>
							<input type="text" id="collaboratorName" class="form-control" name = "collaboratorName" placeholder="Name" required> <br>
							<input type="email" id="collaboratorEmailId" class="form-control" name = "collaboratorEmailId" placeholder="Email Id" required><br>
							<input type="password" id="collaboratorPassword" class="form-control" name = "collaboratorPassword" placeholder="Password" required>
							<br>
							<button type="button" class="btn btn-default pull-right" id = "saveCollaborator">Save</button>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>