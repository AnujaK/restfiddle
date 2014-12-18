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
package com.restfiddle.handler.http.builder;

import java.io.UnsupportedEncodingException;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.restfiddle.dto.RfRequestDTO;

@Component
public class RfRequestBuilder {
    Logger logger = LoggerFactory.getLogger(RfRequestBuilder.class);

    // private static String[] entityContainerTypes = { "POST", "PUT", "PATCH", "DELETE" };

    public HttpUriRequest build(RfRequestDTO requestDTO) {
	String methodType = requestDTO.getMethodType();
	String apiUrl = requestDTO.getApiUrl();

	RequestBuilder requestBuilder = RequestBuilder.create(methodType);

	requestBuilder.setUri(apiUrl);

	requestBuilder.addHeader("Content-Type", "application/json");

	setRequestEntity(requestDTO, requestBuilder);

	HttpUriRequest httpUriRequest = requestBuilder.build();
	return httpUriRequest;
    }

    private void setRequestEntity(RfRequestDTO requestDTO, RequestBuilder requestBuilder) {
	if (requestDTO.getApiBody() == null) {
	    return;
	}
	try {
	    requestBuilder.setEntity(new StringEntity(requestDTO.getApiBody()));
	    
	    //MultipartEntityBuilder builder = MultipartEntityBuilder.create();
	    
	} catch (UnsupportedEncodingException e) {
	    logger.error(e.getMessage(), e);
	}
    }

}
