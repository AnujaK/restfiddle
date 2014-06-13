package com.restfiddle.handler.http.auth;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpRequestBase;
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

    public CloseableHttpClient prepareBasicAuthWithBase64Encode(HttpRequestBase httpGet, String userName, String password) {
	String authStr = "user1" + ":" + "user1";
	String encoding = (new BASE64Encoder()).encode(authStr.getBytes());
	httpGet.setHeader("Authorization", "Basic " + encoding);
	CloseableHttpClient client = HttpClientBuilder.create().build();

	return client;
    }
}
