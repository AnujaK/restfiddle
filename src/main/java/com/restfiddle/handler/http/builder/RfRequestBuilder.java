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
import java.util.List;

import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.restfiddle.dto.FormDataDTO;
import com.restfiddle.dto.RfHeaderDTO;
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

	setHeaders(requestDTO, requestBuilder);

	setRequestEntity(requestDTO, requestBuilder);

	HttpUriRequest httpUriRequest = requestBuilder.build();
	return httpUriRequest;
    }

    private void setHeaders(RfRequestDTO requestDTO, RequestBuilder requestBuilder) {
	List<RfHeaderDTO> headers = requestDTO.getHeaders();
	if (headers != null && !headers.isEmpty()) {
	    boolean contentTypeFound = false;
	    for (RfHeaderDTO rfHeaderDTO : headers) {
		if (rfHeaderDTO.getHeaderName() != null && rfHeaderDTO.getHeaderValue() != null) {
		    requestBuilder.addHeader(rfHeaderDTO.getHeaderName(), rfHeaderDTO.getHeaderValue());
		}
		if (HttpHeaders.CONTENT_TYPE.equalsIgnoreCase(rfHeaderDTO.getHeaderName())) {
		    contentTypeFound = true;
		}
	    }
	    if (!contentTypeFound) {
		requestBuilder.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
	    }
	} else {
	    requestBuilder.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
	}
    }

    private void setRequestEntity(RfRequestDTO requestDTO, RequestBuilder requestBuilder) {
	List<FormDataDTO> formParams = requestDTO.getFormParams();
	if (requestDTO.getApiBody() != null) {
	    try {
		requestBuilder.setEntity(new StringEntity(requestDTO.getApiBody()));

	    } catch (UnsupportedEncodingException e) {
		logger.error(e.getMessage(), e);
	    }
	} else if (formParams != null && !formParams.isEmpty()) {
	    requestBuilder.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE);// TODO
	    MultipartEntityBuilder multiPartBuilder = MultipartEntityBuilder.create();
	    for (FormDataDTO formDataDTO : formParams) {
		multiPartBuilder.addTextBody(formDataDTO.getKey(), formDataDTO.getValue());
	    }
	    requestBuilder.setEntity(multiPartBuilder.build());
	}
    }
}
