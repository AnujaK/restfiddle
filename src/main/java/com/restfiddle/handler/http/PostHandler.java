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
package com.restfiddle.handler.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PostHandler {
    Logger logger = LoggerFactory.getLogger(PostHandler.class);

    public void process() throws IOException {

	CloseableHttpClient httpclient = HttpClients.createDefault();
	try {
	    HttpPost httpPost = new HttpPost("http://targethost/login");

	    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	    nvps.add(new BasicNameValuePair("username", "vip"));
	    nvps.add(new BasicNameValuePair("password", "secret"));

	    httpPost.setEntity(new UrlEncodedFormEntity(nvps));

	    CloseableHttpResponse httpResponse = httpclient.execute(httpPost);

	    try {
		logger.info("http response status : " + httpResponse.getStatusLine());
		HttpEntity responseEntity = httpResponse.getEntity();

		EntityUtils.consume(responseEntity);
	    } finally {
		httpResponse.close();
	    }
	} finally {
	    httpclient.close();
	}

    }
}
