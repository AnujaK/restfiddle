<div class="col-xs-6 rf-col-3">
    <div class="form-group" id="conversationSection">
        <br>
        <div class="btn-group">
            <button class="btn btn-primary btn-sm" id="run">Run</button>
            <button type="button" class="btn btn-primary btn-sm dropdown-toggle" data-toggle="dropdown">
                <span class="caret"></span> <span class="sr-only">Toggle Dropdown</span>
            </button>
            <ul class="dropdown-menu">
                <li><a href="#" class="btn-sm" data-toggle="modal" data-target="#comingSoon">Save and Run</a></li>
            </ul>
        </div>
        &nbsp;&nbsp;&nbsp;
        <div class="btn-group">
            <button type="button" class="btn btn-default btn-sm" data-toggle="modal" id="saveConversationBtn">Save</button>
            <button type="button" class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown">
                <span class="caret"></span> <span class="sr-only">Toggle Dropdown</span>
            </button>
            <ul class="dropdown-menu">
                <li><a href="#" class="btn-sm" data-toggle="modal" id="saveAsConversationBtn">Save As</a></li>
            </ul>
        </div>
        &nbsp;&nbsp;&nbsp;
        <div class="btn-group">
            <button type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#comingSoon">Clear</button>
            <button type="button" class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown">
                <span class="caret"></span> <span class="sr-only">Toggle Dropdown</span>
            </button>
            <ul class="dropdown-menu">
                <li><a href="#" class="btn-sm" data-toggle="modal" data-target="#comingSoon">Clear Body</a></li>
                <li><a href="#" class="btn-sm" data-toggle="modal" data-target="#comingSoon">Clear Header</a></li>
                <li><a href="#" class="btn-sm" data-toggle="modal" data-target="#comingSoon">Clear Cookie</a></li>
                <li><a href="#" class="btn-sm" data-toggle="modal" data-target="#comingSoon">Clear Auth</a></li>
            </ul>
        </div>
        &nbsp;&nbsp;&nbsp;
        <div class="btn-group">
            <button type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#comingSoon">Copy Response</button>
            <button type="button" class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown">
                <span class="caret"></span> <span class="sr-only">Toggle Dropdown</span>
            </button>
            <ul class="dropdown-menu">
                <li><a href="#" class="btn-sm" data-toggle="modal" data-target="#comingSoon">Show Saved Response</a></li>
            </ul>
        </div>
        &nbsp;&nbsp;&nbsp;
        <button class="btn btn-default btn-sm">
            <span class="glyphicon glyphicon-star"></span>&nbsp;Star
        </button>
        <button class="btn btn-default btn-sm pull-right header-toggle-btn" data-toggle="tooltip" data-placement="left">
            <i id="header-toggle-icon" class="fa fa-angle-double-up"></i>
        </button>
        <br> <br>
        <div>
            <p>
                <span class='glyphicon glyphicon glyphicon-chevron-down'></span>&nbsp;&nbsp;<span id="apiRequestName"></span>
            </p>
            <p id="apiRequestDescription"></p>
            <p>
                &nbsp;&nbsp;<span class="label bg-green">Important</span>&nbsp;<span class="label bg-orange">Wishlist</span>
            </p>
        </div>
        <br>
        <div class="container-fluid">
            <div class="row">
                <div class="col-xs-2 rf-col-xs-2-padding" >
                    <select class="apiRequestType form-control">
                        <option>GET</option>
                        <option>POST</option>
                        <option>PUT</option>
                        <option>DELETE</option>
                    </select>
                </div>
                <div class="col-xs-10 rf-col-xs-10-padding">
                    <input type="text" class="form-control" id="apiUrl" placeholder="Enter url"> <br> <br>
                </div>
            </div>
        </div>
        <ul class="nav nav-tabs">
            <li class="active"><a href="#tab-body" data-toggle="tab">Body</a></li>
            <li><a href="#tab-header" data-toggle="tab">Header</a></li>
            <li><a href="#tab-cookie" data-toggle="tab">Cookie</a></li>
            <li><a href="#tab-auth" data-toggle="tab">Auth</a></li>
        </ul>
        <!-- Tab panes -->
        <div class="tab-content">
            <div class="tab-pane active" id="tab-body">
                <br>
                <textarea id="apiBody" class="form-control"></textarea>
            </div>
            <div class="tab-pane" id="tab-header"><br><p>TODO</p></div>
            <div class="tab-pane" id="tab-cookie"><br><p>TODO</p></div>
            <div class="tab-pane" id="tab-auth"><br><p>TODO</p></div>
        </div>
        <hr>
        <div>
            <p>
                <span class='glyphicon glyphicon glyphicon-chevron-right'></span>&nbsp;&nbsp;<b>Response</b>
            </p>
        </div>

        <ul class="nav nav-tabs">
            <li class="active"><a href="#res-tab-body" data-toggle="tab">Body</a></li>
            <li><a href="#res-tab-header" data-toggle="tab">Header</a></li>
        </ul>
        <!-- Tab panes -->
        <div class="tab-content">
            <div class="tab-pane active" id="res-tab-body">
                <br>
                <div class="container-fluid">
                    <div class="row">
                        <div id="response-wrapper"></div>
                    </div>
                </div>
                <br><br>					
            </div>
            <div class="tab-pane" id="res-tab-header"><br><p>TODO : Show Response Headers</p></div>
        </div>

    </div>
</div>