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
	
	<div class="modal fade" id="tagModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title" id="tagModalLabel">New Tag</h4>
				</div>
				<div class="modal-body">
					<input type="text" id="tagTextField" class="form-control" placeholder="Enter Tag Name"> <br>
					<textarea id="tagTextArea" class="form-control" rows="3" placeholder="Enter Description"></textarea>
					<br>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button id="saveTagBtn" type="button" class="btn btn-primary">Save changes</button>
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