<div class="col-xs-4 rf-col-2">
    <br>
    <button class="btn btn-default btn-sm col-1-toggle-btn">
        <i id="col1-toggle-icon" class="fa fa-angle-double-left"></i>
    </button>
    &nbsp;
    <div class="btn-group" id="newRequestDropdown">
        <button class="btn btn-default btn-sm" data-toggle="modal" id="requestBtn">New Request</button>
        <button type="button" class="btn btn-sm btn-default dropdown-toggle" data-toggle="dropdown">
            <span class="caret"></span> <span class="sr-only">Toggle Dropdown</span>
        </button>
        <ul class="dropdown-menu pull-right">
            <li><a href="#" class="btn-sm" data-toggle="modal" data-target="#folderModal">New Folder</a></li>
            <li class="divider"></li>
            <li><a class="rf-font-12" data-toggle="modal" data-target="#socketModal">New Socket</a></li>
            <li class="divider"></li>
            <li><a href="#" class="btn-sm" data-toggle="modal" data-target="#entityModal">New Entity</a></li>
        </ul>
    </div>
    &nbsp;
    <div class="btn-group">
        <button class="btn btn-default btn-sm run-project"  data-toggle="tooltip" data-placement="bottom" data-container="body" title="Run project lets you run requests together.">Run Project</button>
    </div>
    &nbsp;&nbsp;
    <div class="btn-group" id="moreOptionsDropdown">
        <button type="button" class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown">
            More&nbsp;&nbsp;<span class="caret"></span>
        </button>
        <ul class="dropdown-menu pull-right">
            <li><a class="rf-font-12" data-toggle="modal" id="expandAllNodes">Expand All</a></li>
            <li><a class="rf-font-12" data-toggle="modal" id="collapseAllNodes">Collapse All</a></li>
            <li class="divider"></li>
            <li><a class="rf-font-12 socket-connector">Socket</a></li>
            <li class="divider"></li>
            <li><a class="rf-font-12" id="editNodeMenuItem">Edit Node</a></li>
            <li><a class="rf-font-12" data-toggle="modal" data-target="#deleteNodeModal">Delete Node</a></li>
            <li class="divider"></li>
            <li><a class="rf-font-12" data-toggle="modal" data-target="#importModal">Import</a></li>
        </ul>
    </div>

    <br> <br>


    <ul class="nav nav-pills nav-stacked rf-left-nav" id="starred-items">
    </ul>

    <ul class="nav nav-pills nav-stacked rf-left-nav" id="tagged-items">
    </ul> 

    <ul class="nav nav-pills nav-stacked rf-left-nav" id="history-items">
    </ul>

    <div id="tree" class="fancytree-colorize-hover"></div>
    <br><br>
</div>