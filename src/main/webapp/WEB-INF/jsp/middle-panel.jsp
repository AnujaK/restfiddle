<div class="col-xs-4 rf-col-2">
    <br>
    <button class="btn btn-default btn-sm col-1-toggle-btn">
        <i id="col1-toggle-icon" class="fa fa-angle-double-left"></i>
    </button>
    &nbsp;
    <div class="btn-group">
        <button class="btn btn-default btn-sm" data-toggle="modal" id="requestBtn">New Request</button>
        <button type="button" class="btn btn-sm btn-default dropdown-toggle" data-toggle="dropdown">
            <span class="caret"></span> <span class="sr-only">Toggle Dropdown</span>
        </button>
        <ul class="dropdown-menu pull-right">
            <li><a href="#" class="btn-sm" data-toggle="modal" data-target="#folderModal">New Folder</a></li>
            <li class="divider"></li>
            <li><a class="rf-font-12 socket-connector">New Socket</a></li>
            <li class="divider"></li>
            <li><a href="#" class="btn-sm" data-toggle="modal" data-target="#entityModal">New Entity</a></li>
        </ul>
    </div>
    &nbsp;
    <div class="btn-group">
        <button class="btn btn-default btn-sm run-project"  data-toggle="tooltip" data-placement="bottom" data-container="body" title="Run project does not work with in-memory database. Make sure you configure MySQL before using this feature!">Run Project</button>
    </div>
    &nbsp;&nbsp;
    <div class="btn-group">
        <button type="button" class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown">
            More&nbsp;&nbsp;<span class="caret"></span>
        </button>
        <ul class="dropdown-menu pull-right">
            <li><a class="rf-font-12" data-toggle="modal" id="expandAllNodes">Expand All</a></li>
            <li><a class="rf-font-12" data-toggle="modal" id="collapseAllNodes">Collapse All</a></li>
            <li class="divider"></li>
            <!--
            <li><a class="rf-font-12" data-toggle="modal" data-target="#comingSoon">Sort</a></li>
            <li><a class="rf-font-12" data-toggle="modal" data-target="#comingSoon">Filter</a></li>
            <li class="divider"></li>
            -->
            <li><a class="rf-font-12 socket-connector">Socket</a></li>
            <li class="divider"></li>
            <li><a class="rf-font-12" id="editNodeMenuItem">Edit Node</a></li>
            <li><a class="rf-font-12" data-toggle="modal" data-target="#deleteNodeModal">Delete Node</a></li>
            <li class="divider"></li>
            <li><a class="rf-font-12 run-project">Run Project</a></li>
            <li><a class="rf-font-12" data-toggle="modal" data-target="#editProjectModal">Edit Project</a></li>
            <li><a class="rf-font-12" data-toggle="modal" data-target="#deleteProjectModal">Delete Project</a></li>
            <li class="divider"></li>
            <li><a class="rf-font-12" data-toggle="modal" data-target="#editWorkspaceModal">Edit Workspace</a></li>
            <li><a class="rf-font-12" data-toggle="modal" data-target="#deleteWorkspaceModal">Delete Workspace</a></li>
        </ul>
    </div>

    <br> <br>
    
    <ul class="nav nav-pills nav-stacked rf-left-nav" id="starred-items">
    </ul>
    
    <ul class="nav nav-pills nav-stacked rf-left-nav" id="tagged-items">
    </ul> 
    
    <ul class="nav nav-pills nav-stacked rf-left-nav" id="history-items">
    </ul>
    
    <div id="tree"></div>
    <br><br>
</div>