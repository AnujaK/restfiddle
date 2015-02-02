package com.restfiddle.handler.http.auth;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.DigestScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Component;

import com.restfiddle.dto.DigestAuthDTO;
import com.restfiddle.dto.RfRequestDTO;

@Component
public class DigestAuthHandler {

    public void setCredentialsProvider(RfRequestDTO requestDTO, HttpClientBuilder clientBuilder) {
	DigestAuthDTO digestAuthDTO = requestDTO.getDigestAuthDTO();
	if (digestAuthDTO == null) {
	    return;
	}
	String userName = digestAuthDTO.getUsername();
	String password = digestAuthDTO.getPassword();
	if (userName == null || userName.isEmpty() || password == null || password.isEmpty()) {
	    return;
	}
	CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
	credentialsProvider.setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT, AuthScope.ANY_REALM),
		new UsernamePasswordCredentials(userName, password));

	clientBuilder.setDefaultCredentialsProvider(credentialsProvider);
    }

    public HttpClientContext preemptive() {
	AuthCache authCache = new BasicAuthCache();

	DigestScheme digestAuth = new DigestScheme();

	digestAuth.overrideParamter("realm", "");
	digestAuth.overrideParamter("nonce", "");

	// TODO : Add target
	// authCache.put(target, digestAuth);
	HttpClientContext localContext = HttpClientContext.create();
	localContext.setAuthCache(authCache);

	return localContext;
    }
}
