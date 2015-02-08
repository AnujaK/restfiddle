/*
 * Copyright 2015 Ranjan Kumar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.restfiddle.sample.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.restfiddle.controller.rest.OAuth2Controller;
import com.restfiddle.dto.OAuth2RequestDTO;

@Component
public class OAuth2DataGenerator {

    @Autowired
    private OAuth2Controller oauth2Controller;

    public void loadGitHubAPI() {
	// Documentation : https://developer.github.com/v3/oauth/
	String name = "GitHub";
	String authorizationUrl = "https://github.com/login/oauth/authorize";
	String accessTokenUrl = "https://github.com/login/oauth/access_token";
	String clientId = "";
	String clientSecret = "";
	String accessTokenLocation = "HEADER_TOKEN";// Authorization: token OAUTH-TOKEN
	List<String> scopes = new ArrayList<String>();
	scopes.add("user");
	scopes.add("repo");

	OAuth2RequestDTO oauth2DTO = buildOAuth2DTO(name, authorizationUrl, accessTokenUrl, clientId, clientSecret, accessTokenLocation, scopes);
	oauth2Controller.create(oauth2DTO);
    }

    public void loadAsanaAPI() {
	// Documentation : https://github.com/Asana/oauth-examples
	// Documentation : http://developer.asana.com/documentation/#AsanaConnect
	String name = "Asana";
	String authorizationUrl = "https://app.asana.com/-/oauth_authorize";
	String accessTokenUrl = "https://app.asana.com/-/oauth_token";
	String clientId = "";
	String clientSecret = "";
	String accessTokenLocation = "HEADER_BEARER";// Authorization: Bearer OAUTH-TOKEN
	// Note: The current OAuth implementation does not support scopes or other flows.
	List<String> scopes = new ArrayList<String>();

	OAuth2RequestDTO oauth2DTO = buildOAuth2DTO(name, authorizationUrl, accessTokenUrl, clientId, clientSecret, accessTokenLocation, scopes);
	oauth2Controller.create(oauth2DTO);
    }

    public void loadTrelloAPI() {
	// Documentation : https://trello.com/docs/gettingstarted/oauth.html
	String name = "Trello";
	String authorizationUrl = "https://trello.com/1/OAuthAuthorizeToken";
	String accessTokenUrl = "https://trello.com/1/OAuthGetAccessToken";
	String clientId = "";
	String clientSecret = "";
	String accessTokenLocation = "HEADER_TOKEN";// Authorization: token OAUTH-TOKEN
	List<String> scopes = new ArrayList<String>();

	OAuth2RequestDTO oauth2DTO = buildOAuth2DTO(name, authorizationUrl, accessTokenUrl, clientId, clientSecret, accessTokenLocation, scopes);
	oauth2Controller.create(oauth2DTO);
    }

    /**
     * Evernote does not have a RESTful API - http://stackoverflow.com/questions/23096982/does-evernote-use-http-to-make-calls-to-its-api
     */
    @Deprecated
    public void loadEvernoteAPI() {
	// Documentation : https://developer.github.com/v3/oauth/
	String name = "Evernote";
	String authorizationUrl = "";
	String accessTokenUrl = "";
	String clientId = "";
	String clientSecret = "";
	String accessTokenLocation = "HEADER_TOKEN";// Authorization: token OAUTH-TOKEN
	List<String> scopes = new ArrayList<String>();

	OAuth2RequestDTO oauth2DTO = buildOAuth2DTO(name, authorizationUrl, accessTokenUrl, clientId, clientSecret, accessTokenLocation, scopes);
	oauth2Controller.create(oauth2DTO);
    }

    private OAuth2RequestDTO buildOAuth2DTO(String name, String authorizationUrl, String accessTokenUrl, String clientId, String clientSecret,
	    String accessTokenLocation, List<String> scopes) {
	OAuth2RequestDTO oauth2DTO = new OAuth2RequestDTO();
	oauth2DTO.setName(name);
	oauth2DTO.setAuthorizationUrl(authorizationUrl);
	oauth2DTO.setAccessTokenUrl(accessTokenUrl);
	oauth2DTO.setClientId(clientId);
	oauth2DTO.setClientSecret(clientSecret);
	oauth2DTO.setAccessTokenLocation(accessTokenLocation);
	oauth2DTO.setScopes(scopes);
	return oauth2DTO;
    }
}
