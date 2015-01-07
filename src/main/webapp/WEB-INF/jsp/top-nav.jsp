<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
            </button>
            <div class="dropdown rf-dropdown">
                <button class="btn btn-default" type="button" data-toggle="dropdown">
                    <span class='glyphicon glyphicon-align-justify'></span>
                </button>
                <ul class="dropdown-menu rf-menu-width">
                    <li><a href="#" data-toggle="modal" data-target="#workspaceModal">New Workspace</a></li>
                    <li><a href="#" id="switchWorkSpace" class="dummySwitchWorkspace">Switch Workspace</a></li>
                    <li class="divider"></li>
                    <li><a href="#" data-toggle="modal" data-target="#projectModal">New Project</a></li>
                    <li class="divider"></li>
                    <li><a href="#" data-toggle="modal" data-target="#updateProfileModal">Update Profile</a></li>
                    <li><a href="#" data-toggle="modal" data-target="#changePasswordModal">Change Password</a></li>
                    <li class="divider"></li>
                    <li><a href="#" data-toggle="modal" data-target="#collaboratorModal">New Collaborator</a></li>
                    <li><a href="#" data-toggle="modal" id="managerUsersMenu" data-target="#manageCollaboratorsModal">Manage Collaborators</a></li>
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
                        <button class="btn btn-link rf-btn-margin" type="submit">Logout</button>
                    </form>
                </li>
            </ul>
        </div>
    </div>
</div>