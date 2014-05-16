/*
 * Copyright 2014 Ranjan Kumar
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
package com.restfiddle.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

@Component
public class RequestHandler extends AbstractRequestHandler {

    public void createHttpGetConnection() throws IOException {
	CloseableHttpClient httpclient = HttpClients.createDefault();
	try {
	    HttpGet httpGet = new HttpGet("http://targethost/");
	    System.out.println(httpGet.getRequestLine());
	    Header[] requestHeaders = httpGet.getAllHeaders();
	    System.out.println(requestHeaders.length);
	    for (Header requestHeader : requestHeaders) {
		System.out.println("request header - name : " + requestHeader.getName() + " value : " + requestHeader.getValue());
	    }

	    CloseableHttpResponse httpResponse = httpclient.execute(httpGet);
	    try {
		System.out.println("response status : " + httpResponse.getStatusLine());

		HttpEntity responseEntity = httpResponse.getEntity();
		Header[] responseHeaders = httpResponse.getAllHeaders();
		for (Header responseHeader : responseHeaders) {
		    System.out.println("response header - name : " + responseHeader.getName() + " value : " + responseHeader.getValue());
		}

		Header contentType = responseEntity.getContentType();
		System.out.println("response contentType : " + contentType);
		// System.out.println("content : " + EntityUtils.toString(responseEntity));
		EntityUtils.consume(responseEntity);
	    } finally {
		httpResponse.close();
	    }
	} finally {
	    httpclient.close();
	}

    }

    public void createHttpPostConnection() throws IOException {
	CloseableHttpClient httpclient = HttpClients.createDefault();
	try {
	    HttpPost httpPost = new HttpPost("http://targethost/login");
	    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	    nvps.add(new BasicNameValuePair("username", "vip"));
	    nvps.add(new BasicNameValuePair("password", "secret"));
	    httpPost.setEntity(new UrlEncodedFormEntity(nvps));
	    CloseableHttpResponse response2 = httpclient.execute(httpPost);

	    try {
		System.out.println(response2.getStatusLine());
		HttpEntity entity2 = response2.getEntity();
		// do something useful with the response body
		// and ensure it is fully consumed
		EntityUtils.consume(entity2);
	    } finally {
		response2.close();
	    }
	} finally {
	    httpclient.close();
	}

    }
}
