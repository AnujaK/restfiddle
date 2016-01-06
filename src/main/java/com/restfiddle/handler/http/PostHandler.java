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

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.restfiddle.dto.RfRequestDTO;
import com.restfiddle.dto.RfResponseDTO;

@Component
@Deprecated
public class PostHandler extends GenericHandler {

    Logger logger = LoggerFactory.getLogger(PostHandler.class);

    public RfResponseDTO process(RfRequestDTO rfRequestDTO) throws IOException {
	RfResponseDTO response = null;
	CloseableHttpClient httpclient = HttpClients.createDefault();
	HttpPost httpPost = new HttpPost(rfRequestDTO.getEvaluatedApiUrl());
	httpPost.addHeader("Content-Type", "application/json");
	httpPost.setEntity(new StringEntity(rfRequestDTO.getApiBody()));
	try {
	    response = processHttpRequest(httpPost, httpclient);
	} finally {
	    httpclient.close();
	}
	return response;
    }
}
