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
package com.restfiddle.entity;

public class OAuth2 extends NamedEntity{
    private static final long serialVersionUID = 1L;

    private String authorizationUrl;

    private String accessTokenUrl;

    private String clientId;

    private String clientSecret;

    private String accessTokenLocation;

    private String scopes;

    public String getAuthorizationUrl() {
	return authorizationUrl;
    }

    public void setAuthorizationUrl(String authorizationUrl) {
	this.authorizationUrl = authorizationUrl;
    }

    public String getAccessTokenUrl() {
	return accessTokenUrl;
    }

    public void setAccessTokenUrl(String accessTokenUrl) {
	this.accessTokenUrl = accessTokenUrl;
    }

    public String getClientId() {
	return clientId;
    }

    public void setClientId(String clientId) {
	this.clientId = clientId;
    }

    public String getClientSecret() {
	return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
	this.clientSecret = clientSecret;
    }

    public String getAccessTokenLocation() {
	return accessTokenLocation;
    }

    public void setAccessTokenLocation(String accessTokenLocation) {
	this.accessTokenLocation = accessTokenLocation;
    }

    public String getScopes() {
	return scopes;
    }

    public void setScopes(String scopes) {
	this.scopes = scopes;
    }
}