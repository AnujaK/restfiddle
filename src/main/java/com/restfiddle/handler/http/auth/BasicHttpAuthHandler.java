/*
 * Copyright 2014 Sandesh Deshmane
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

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import sun.misc.BASE64Encoder;

public class BasicHttpAuthHandler {

    public BasicHttpAuthHandler() {

    }

    public CloseableHttpClient prepareBasicAuth(String userName, String password) {
	CredentialsProvider provider = new BasicCredentialsProvider();
	UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(userName, userName);
	provider.setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT), credentials);
	CloseableHttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();

	return client;

    }
    
    public CloseableHttpClient prepareBasicAuthWithBase64Encode(
			HttpGet httpGet, String userName, String password) {
		String authStr = "user1" + ":" + "user1";
		String encoding = (new BASE64Encoder()).encode(authStr.getBytes());
		httpGet.setHeader("Authorization", "Basic " + encoding);
		CloseableHttpClient client = HttpClientBuilder.create().build();

		return client;
	}
}
