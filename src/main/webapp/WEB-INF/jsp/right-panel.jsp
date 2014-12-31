<div class="col-xs-6 rf-col-3">
    <div id="projectRunnerSection">
        <div id="projectRunnerHeader">
            <br>
            <button class="btn btn-primary btn-sm" id="exportRunProjectReport">Export Report</button>
            <br>
            <br>
        </div>
        <div>
            <ul class="nav nav-pills nav-stacked" id="projectRunnerBody"></ul>
        </div>
    </div>
    <div id="webSocketSection">
        <br>
        <div id="webSocketRequest">
            <p>
                <span id="socketName">My First Socket</span>
            </p>
             <p>
                <span id="socketDescription">This is an echo socket.</span>
            </p>
            <br>
            <p>
                <input type="text" class="form-control" id="secketUrl" placeholder="Enter URL">
            </p>
            <p>
                <button class="btn btn-primary btn-sm" id="btnConnectSocket">Connect</button>&nbsp;&nbsp;&nbsp;
                <button class="btn btn-default btn-sm" id="btnDisconnectSocket">Disconnect</button>
            </p>
            <br>
            <p>
                <textarea id="socketMessage" class="form-control" placeholder="Enter Message"></textarea>
            </p>
            <p>
                <button class="btn btn-primary btn-sm" id="btnSendMessage">Send</button>
            </p>
        </div>
        <hr>
        <div id="webSocketResponse">
            <p>
                <span><b>Message Log</b></span>
            </p>
            <div id="webSocketResponseBody">
                
            </div>
        </div>
    </div>
    <div class="form-group" id="conversationSection">
        <div id="conversationHeader">
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
                <button type="button" class="btn btn-default btn-sm" id="clearRequest">Clear</button>
                <button type="button" class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown">
                    <span class="caret"></span> <span class="sr-only">Toggle Dropdown</span>
                </button>
                <ul class="dropdown-menu">
                    <li><a href="#" class="btn-sm" id="clearBody">Clear Body</a></li>
                    <li><a href="#" class="btn-sm" id="clearHeader">Clear Header</a></li>
                    <li><a href="#" class="btn-sm" id="clearCookie">Clear Cookie</a></li>
                    <li><a href="#" class="btn-sm" id="clearAuth">Clear Auth</a></li>
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
            <button id="starNodeBtn" class="btn btn-default btn-sm">
                <span class="glyphicon glyphicon-star"></span>&nbsp;Star
            </button>
            <button class="btn btn-default btn-sm pull-right header-toggle-btn" data-toggle="tooltip" data-placement="left">
                <i id="header-toggle-icon" class="fa fa-angle-double-up"></i>
            </button>
            <br> <br>
        </div>
        
        <div id="conversationBody">
            <div>
                <div>
                    <p>
                        <span id="requestToggle" class='glyphicon glyphicon glyphicon-chevron-down'></span>&nbsp;&nbsp;<span id="apiRequestName"></span>
                    </p>
                </div>
                <div id="requestContainer">
                    <p id="apiRequestDescription"></p>
                    <p>
                        &nbsp;&nbsp;<span class="label label-default">Important</span>&nbsp;<span class="label label-default">Wishlist</span>
                    </p>
                    <br>
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-xs-2 rf-col-xs-2-padding" >
                                <select class="apiRequestType form-control">
                                    <option>GET</option>
                                    <option>POST</option>
                                    <option>PUT</option>
                                    <option>DELETE</option>
                                    <option>HEAD</option>
                                    <option>OPTIONS</option>
                                    <option>TRACE</option>
                                </select>
                            </div>
                            <div class="col-xs-10 rf-col-xs-10-padding">
                                <input type="text" class="form-control typeahead" id="apiUrl" placeholder="Enter URL"> <br> <br>
                            </div>
                        </div>
                    </div>
                    <ul class="nav nav-tabs">
                        <li class="active"><a href="#tab-body" data-toggle="tab">Raw</a></li>
                        <li><a href="#tab-form" data-toggle="tab">Form</a></li>
                        <li><a href="#tab-file" data-toggle="tab">Files</a></li>
                        <li><a href="#tab-header" data-toggle="tab">Header</a></li>
                        <!-- <li><a href="#tab-cookie" data-toggle="tab">Cookie</a></li> -->
                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                                Auth <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li role="presentation"><a class="btn-sm" href="#tab-basic-auth" data-toggle="tab">Basic</a></li>
                                <li role="presentation"><a class="btn-sm" href="#tab-digest-auth" data-toggle="tab">Digest</a></li>
                                <li role="presentation"><a class="btn-sm" href="#tab-oauth1" data-toggle="tab">OAuth 1</a></li>
                                <li role="presentation"><a class="btn-sm" href="#tab-oauth2" data-toggle="tab">OAuth 2</a></li>
                            </ul>
                        </li>
                    </ul>
                    <!-- Tab panes -->
                    <div class="tab-content">
                        <div class="tab-pane active" id="tab-body">
                            <br>
                            <!-- <div class="btn-group">
                                <button class="btn btn-default btn-sm">raw</button>
                                <button class="btn btn-default btn-sm">form</button>
                                <button class="btn btn-default btn-sm">files</button>
                            </div>                            
                            <br>
                            <br> -->
                            <textarea id="apiBody" class="form-control"></textarea>
                        </div>
                        
                        <div class="tab-pane" id="tab-form">
                            <br>
                            <p>
                                <button type="button" class="btn btn-default btn-sm" id="addFormDataBtn">Add Form Data</button>
                            </p>
                            <div id="formDataWrapper">
                            </div>
                        </div>
                        
                        <div class="tab-pane" id="tab-file">
                            <br>
                            <p>
                                <button type="button" class="btn btn-default btn-sm" id="addFileDataBtn">Add File Data</button>
                            </p>
                            <div id="fileDataWrapper">
                            </div>
                        </div>
                        
                        <div class="tab-pane" id="tab-header">
                            <br>
                            <!-- <p><span>Content-Type</span> : <span>application/json</span></p> -->
                            <p>
                                <button type="button" class="btn btn-default btn-sm" id="addHeaderBtn">Add Header</button>
                            </p>
                            <div id="headersWrapper">
                            </div>
                        </div>
                        <!--
                        <div class="tab-pane" id="tab-cookie">
                            <br>
                            <p></p>
                        </div>
                        -->
                        <div class="tab-pane" id="tab-basic-auth">
                            <br>
                            <div class="row">
                                <div class="col-xs-6">
                                    <input type="text" class="form-control" id="bAuthUsername" placeholder="Enter Username">
                                </div>
                                <div class="col-xs-6">
                                    <input type="text" class="form-control" id="bAuthPassword" placeholder="Enter Password">
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane" id="tab-digest-auth">
                            <br>
                            <br> 
                            <br>
                            <p></p>
                        </div>
                        <div class="tab-pane" id="tab-oauth1">
                            <br>
                            <br>
                            <br>
                            <p></p>
                        </div>
                        <div class="tab-pane" id="tab-oauth2">
                            <br>
                            <br>
                            <br>
                            <p></p>
                        </div>
                    </div>
                </div>
            </div>

            <hr>

            <div>
                <div>
                    <p>
                        <span id="responseToggle" class='glyphicon glyphicon glyphicon-chevron-down'></span>&nbsp;&nbsp;<b>Response</b>
                    </p>
                </div>

                <div id="responseContainer">
                    <ul class="nav nav-tabs">
                        <li class="active"><a href="#res-tab-body" data-toggle="tab">Body</a></li>
                        <li><a href="#res-tab-header" data-toggle="tab">Header</a></li>
                    </ul>
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
                        <div class="tab-pane" id="res-tab-header">
                            <div><br>
                                <table id="res-header-wrapper" class="table">
                                </table>
                            </div>
                            <br><br>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>