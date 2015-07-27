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
            <input type="hidden" id = "socketNodeId">
            <p>
                <span id="socketName">Socket Name</span><input id="socketNameTextBox">
            </p>
            <p>
                <span id="socketDescription">Socket Description</span>
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
            &nbsp;&nbsp;
            <div class="btn-group">
                <button type="button" class="btn btn-default btn-sm" data-toggle="modal" id="saveConversationBtn">Save</button>
                <button type="button" class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown">
                    <span class="caret"></span> <span class="sr-only">Toggle Dropdown</span>
                </button>
                <ul class="dropdown-menu">
                    <li><a href="#" class="btn-sm" data-toggle="modal" id="saveAsConversationBtn">Save As</a></li>
                </ul>
            </div>
            &nbsp;&nbsp;
            <div class="btn-group">
                <button type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#assertsModal" id="manageAsserts">Asserts <span id="assertCount" class="badge">0</span></button>
                <button type="button" class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown">
                    <span class="caret"></span> <span class="sr-only">Toggle Dropdown</span>
                </button>
                <ul class="dropdown-menu">
                    <li><a href="#" class="btn-sm" id = "showLastResponse">View Previous Results</a></li>
                </ul>
            </div>
            &nbsp;&nbsp;
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
            &nbsp;&nbsp;
            <div class="btn-group">
                <button type="button" class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown">
                    More&nbsp;&nbsp;<span class="caret"></span>
                </button>
                <ul class="dropdown-menu">
                    <li class="copyResponseList"><a href="#" class="btn-sm" id = "copyResponse" data-toggle="tooltip" data-placement="top" data-container="body" title="" data-original-title="Copy the response content.">Copy Response</a></li>
                    <li><a href="#" class="btn-sm" id="showLastResponse">Show Last Response</a></li>
                </ul>
            </div>
            &nbsp;&nbsp;&nbsp;
            <button id="starNodeBtn" class="btn btn-default btn-sm">
                <span class="glyphicon glyphicon-star"></span>&nbsp;Star
            </button>
            <button class="btn btn-default btn-sm pull-right right-pannel-toggle-btn" data-toggle="tooltip" data-placement="left">
                <i id="full-screen-icon" class="fa fa-arrows-alt"></i>
            </button>
         <!--    <button class="btn btn-default btn-sm pull-right header-toggle-btn" data-toggle="tooltip" data-placement="left">
                <i id="header-toggle-icon" class="fa fa-angle-double-up"></i>
            </button> -->
            <br> <br>
        </div>
        
        <div id="conversationBody">
            <div>
                <div>
                    <p class="apiRequestContainer">
                        <input type = "hidden" id = "apiReqNodeId">
                        <input type = "hidden" id = "rfRequestId">
                        <span id="requestToggle" class='glyphicon glyphicon glyphicon-chevron-down'></span>&nbsp;&nbsp;<input id="apiRequestNameTextBox"></input><span id="apiRequestName"></span>
                    </p>
                </div>
                <div id="requestContainer">
                    <p id="apiRequestDescription"></p>
                    <div class="btn-group label-btn-group">
                        <input type = "hidden" id= "currentStaredNode">
                        <button type="button" class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown">
                            <span class="glyphicon glyphicon-tags"></span> &nbsp;Tags&nbsp;<span class="caret"></span> <span class="sr-only">Toggle Dropdown</span>
                        </button>
                        <ul class="dropdown-menu label-dropdown-menu">
                            
                        </ul>
                    </div>
                    <div id = "tagLabels"></div>

                    <br>
                    <br>
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-xs-2 rf-col-xs-2-padding" >
                                <select class="apiRequestType form-control">
                                    <option selected>GET</option>
                                    <option>POST</option>
                                    <option>PUT</option>
                                    <option>DELETE</option>
                                    <option>HEAD</option>
                                    <option>OPTIONS</option>
                                    <option>TRACE</option>
                                </select>
                            </div>
                            <div class="col-xs-10 rf-col-xs-10-padding">
                                <input type="text" class="form-control typeahead" id="apiUrl" placeholder="Enter URL">
                                <input type="hidden" id="evaluatedApiUrl" > <br> <br>
                            </div>
                        </div>
                    </div>
                    <ul class="nav nav-tabs reponse-pannel-ul">
                        <li class="active"><a href="#tab-body" data-toggle="tab">Raw</a></li>
                        <li><a href="#tab-form" data-toggle="tab">Form</a></li>
                        <li><a href="#tab-file" data-toggle="tab">Files</a></li>
                        <li><a href="#tab-query" data-toggle="tab">Query</a></li>
                        <li><a href="#tab-header" data-toggle="tab">Header</a></li>
                        <!-- <li><a href="#tab-cookie" data-toggle="tab">Cookie</a></li> -->
                        <li><a href="#tab-auth" data-toggle="tab">Auth</a></li>
                </ul>
                <!-- Tab panes -->
                <div class="tab-content reponse-pannel">
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
                        <div class="tab-pane" id="tab-query">
                            <br>
                            <p>
                                <button type="button" class="btn btn-default btn-sm" id="addQueryParamBtn">Add Query Param</button>
                            </p>
                            <div id="queryParamsWrapper">
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
                    <div class="tab-pane" id="tab-auth">
                            <br>
                            <p>
                            <input type="hidden" name="authOptionSelected" id="authOptionSelected" />
							<div class="btn-group" data-toggle="buttons" id="authRadioOptions">
								  <label class="btn btn-primary btn-sm">
								      <input type="radio" name="authOptions" id="option1" value="basic" /> Basic
								  </label>
								  <label class="btn btn-primary btn-sm">
								      <input type="radio" name="authOptions" id="option2" value="digest" /> Digest
								  </label>
								  <label class="btn btn-primary btn-sm">
								      <input type="radio" name="authOptions" id="option3" value="oauth2" /> OAuth 2
								  </label>
							</div>
							 	<!--<button class="btn btn-primary" id="noAuth">No Auth</button>   -->                               
                            </p>
                            <div id="authWrapper">
                            </div>
                    
                    <div class="tab-pane authFields" id="tab-basic-auth">
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
                    <div class="tab-pane authFields" id="tab-digest-auth">
                        <br>
                        <div class="row">
                            <div class="col-xs-6">
                                <input type="text" class="form-control" id="digestUsername" placeholder="Enter Username">
                            </div>
                            <div class="col-xs-6">
                                <input type="text" class="form-control" id="digestPassword" placeholder="Enter Password">
                            </div>
                        </div>
                    </div>
                   <!-- <div class="tab-pane" id="tab-oauth1">
                        <br>
                        <br>
                        <br>
                        <p></p>
                    </div>-->
                    <div class="tab-pane authFields" id="tab-oauth2">
                        <span id="fetchedAccessToken"></span>
                        <br>
                        <div id="oauth2Wrapper">
				        </div>
                        <br>
                        <div><input type="text" class="form-control" id="oauthName" placeholder="Enter Name">                                 </div>
                        <br>
                        <div><input type="text" class="form-control" id="authorizationUrl" placeholder="Enter Authorization Endpoint">                                 </div>
                        <br>
                        <div><input type="text" class="form-control" id="accessTokenUrl" placeholder="Enter Access Token Endpoint">                                   </div>
                        <br>
                        <div><input type="text" class="form-control" id="clientId" placeholder="Enter Client ID">                                               </div>
                        <br>
                        <div><input type="text" class="form-control" placeholder="Enter Client Secret">                                           </div>
                        <br>
                        <div><input type="text" class="form-control" id="authScopes" placeholder="Enter Scope">                                           </div>
                        <br>
                        <div class="row">
                            <div class="col-xs-6">
                                <select class="form-control" id="accessTokenLocation">
                                    <option>Select Access Token Location</option>
                                    <option>Auth Header With Bearer Prefix</option>
                                    <option>Auth Header With OAuth Prefix</option>
                                    <option>URL Parameter with access_token</option>
                                </select>
                            </div>
                            <div class="col-xs-3">
                                <button id="accessTokenBtn" class="btn btn-default">Get Access Token</button>
                            </div>
                            <div class="col-xs-3" id="saveAuth">
                                <button id="saveOAuthBtn" class="btn btn-default">Save</button>
                            </div>
                        </div>
                        <br>

                        <br>
                        <p></p>
                    </div>
                    </div>
                </div>
            </div>
        </div>

        <hr>

        <div>
            <div>
                <p>
                    <span id="responseToggle" class='glyphicon glyphicon glyphicon-chevron-down'></span>&nbsp;&nbsp;<b>Response</b>
                <div class="pull-right">&nbsp;&nbsp;<span>Status : </span><span id="status-code"></span>&nbsp;&nbsp;<span>Time : <span id="req-time"></span> ms</span>&nbsp;&nbsp;<span>Size : <span id="content-size"></span> Bytes</span></div>
                </p>
            </div>

            <div id="responseContainer">
                <ul class="nav nav-tabs">
                    <li class="active"><a href="#res-tab-body" data-toggle="tab">Body</a></li>
                    <li><a href="#res-tab-header" data-toggle="tab">Header</a></li>
                    <li><a href="#res-tab-assert" data-toggle="tab">Asserts <span id="assertResultCount" class="badge">0/0</span></a></li>
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
                     <div class="tab-pane" id="res-tab-assert">
                        <div><br>
                        	<p><div class="success-icon circle"></div><span>0</span> Success&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <div class="failure-icon circle"></div><span>0</span> Failures</p>
                            <table id="res-assert-wrapper" class="table">
                              <thead>
                              	<tr><th>Status</th><th>Expression</th><th>Comparator</th><th>Expected</th><th>Actual</th><tr>
                              </thead>
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