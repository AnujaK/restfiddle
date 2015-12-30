<div class="col-xs-4 rf-col-2">
    <br>
    &nbsp;
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
     &nbsp;
     <div class="btn-group" id="sortOptionsDropdown">
        <button type="button" class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown">
            Sort&nbsp;&nbsp;<span class="caret"></span>
        </button>
        <ul class="dropdown-menu pull-right">
            <li><a class="rf-font-12" data-toggle="modal" id="sortByName">Sort by Name ASC</a></li>
            <li><a class="rf-font-12" data-toggle="modal" id="sortByNameDesc">Sort by Name DESC</a></li>
            <li><a class="rf-font-12" data-toggle="modal" id="sortByLastRun">Sort by Last Run ASC</a></li>
            <li><a class="rf-font-12" data-toggle="modal" id="sortByLastRunDesc">Sort by Last Run DESC</a></li>
            <li><a class="rf-font-12" data-toggle="modal" id="sortByLastModified">Sort by Last Modified ASC</a></li>
            <li><a class="rf-font-12" data-toggle="modal" id="sortByLastModifiedDesc">Sort by Last Modified DESC</a></li>
        </ul>
    </div>
    &nbsp;
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
            <li class="divider"></li>
            <li><a class="rf-font-12" data-toggle="tooltip" data-placement="bottom" data-container="body" title="Flat View shows requests only." id="showFlatView">Flat View</a></li>
            <li><a class="rf-font-12" data-toggle="tooltip" data-placement="bottom" data-container="body" title="Tree View shows tree structure of project." id="showTreeView">Tree View</a></li>
        </ul>
    </div>
    
    <div class="btn-group" id="searchBox">
	    <div class="input-group stylish-input-group">
	         <input type="text" class="form-control"  placeholder="Search"  id="search">
	         <span class="input-group-addon">
	             <button id="searchbtn" type="submit">
	                 <span class="glyphicon glyphicon-search"></span>
	             </button>  
	         </span>
         </div>
    </div>


    <ul class="nav nav-pills nav-stacked rf-left-nav" id="starred-items">
    </ul>

    <ul class="nav nav-pills nav-stacked rf-left-nav" id="tagged-items">
    </ul> 

    <ul class="nav nav-pills nav-stacked rf-left-nav" id="history-items">
    </ul>
    
    <ul class="nav nav-pills nav-stacked rf-left-nav" id="requests-items">
    </ul>

    <div id="tree" class="fancytree-colorize-hover"></div>
    <br><br>
</div>