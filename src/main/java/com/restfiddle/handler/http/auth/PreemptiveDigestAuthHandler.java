package com.restfiddle.handler.http.auth;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class PreemptiveDigestAuthHandler {

    public PreemptiveDigestAuthHandler() {

    }

    public CloseableHttpClient PreemptiveDigestAuth(String userName, String password) {
	CredentialsProvider credsProvider = new BasicCredentialsProvider();
	credsProvider.setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT, AuthScope.ANY_REALM), new UsernamePasswordCredentials(
		userName, password));
	CloseableHttpClient httpclient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
	return httpclient;

    }
}
