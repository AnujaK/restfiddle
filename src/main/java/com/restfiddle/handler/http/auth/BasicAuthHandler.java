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
package com.restfiddle.handler.http.auth;

import org.apache.http.HttpHeaders;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.springframework.stereotype.Component;

import sun.misc.BASE64Encoder;

import com.restfiddle.dto.BasicAuthDTO;
import com.restfiddle.dto.RfRequestDTO;

@Component
public class BasicAuthHandler {

    public void setBasicAuthWithBase64Encode(RfRequestDTO requestDTO, RequestBuilder requestBuilder) {
	BasicAuthDTO basicAuthDTO = requestDTO.getBasicAuthDTO();
	if (basicAuthDTO == null) {
	    return;
	}
	String userName = basicAuthDTO.getUsername();
	String password = basicAuthDTO.getPassword();
	if (userName == null || userName.isEmpty() || password == null || password.isEmpty()) {
	    return;
	}

	String authStr = userName + ":" + password;
	String encodedAuth = (new BASE64Encoder()).encode(authStr.getBytes());
	requestBuilder.addHeader(HttpHeaders.AUTHORIZATION, "Basic " + encodedAuth);
    }

    /**
     * TODO : Not used anywhere right now.
     */
    public CredentialsProvider prepareBasicAuth(String userName, String password) {
	CredentialsProvider provider = new BasicCredentialsProvider();
	UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(userName, userName);
	provider.setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT), credentials);

	return provider;
    }
}
