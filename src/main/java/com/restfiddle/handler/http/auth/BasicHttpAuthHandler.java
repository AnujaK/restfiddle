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

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;

import sun.misc.BASE64Encoder;

public class BasicHttpAuthHandler {

    public void Base64Auth() {

	String authStr = "user1" + ":" + "user1";

	String encoding = (new BASE64Encoder()).encode(authStr.getBytes());

	HttpPost httppost = new HttpPost("http://test.webdav.org/auth-basic/");
	httppost.setHeader("Authorization", "Basic " + encoding);

	System.out.println("executing request " + httppost.getRequestLine());
	HttpClient client = HttpClientBuilder.create().build();
	HttpResponse response = null;
	try {
	    response = client.execute(httppost);
	} catch (ClientProtocolException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	int statusCode = response.getStatusLine().getStatusCode();
	System.out.println(statusCode);
    }

    public void processAuth() {
	try {
	    BasicHttpAuthHandler authHandler = new BasicHttpAuthHandler();
	    authHandler.Base64Auth();
	    CredentialsProvider provider = new BasicCredentialsProvider();
	    UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("user1", "user1");
	    provider.setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT), credentials);
	    HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();

	    HttpResponse response = client.execute(new HttpGet("http://test.webdav.org/auth-basic/"));
	    int statusCode = response.getStatusLine().getStatusCode();
	    System.out.println(statusCode);

	} catch (ClientProtocolException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
