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

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.restfiddle.dto.AssertionDTO;
import com.restfiddle.dto.RfHeaderDTO;
import com.restfiddle.dto.RfRequestDTO;
import com.restfiddle.dto.RfResponseDTO;
import com.restfiddle.handler.http.builder.RfHttpClientBuilder;
import com.restfiddle.handler.http.builder.RfRequestBuilder;

@Component
public class GenericHandler {
    Logger logger = LoggerFactory.getLogger(GenericHandler.class);

    @Autowired
    RfRequestBuilder rfRequestBuilder;

    @Autowired
    RfHttpClientBuilder rfHttpClientBuilder;

    /**
     * This method will be used for API processing and the method below this will be deprecated.
     */
    public RfResponseDTO processHttpRequest(RfRequestDTO rfRequestDTO) {
	HttpUriRequest httpUriRequest = rfRequestBuilder.build(rfRequestDTO);

	CloseableHttpClient httpClient = rfHttpClientBuilder.build(rfRequestDTO, httpUriRequest);

	RfResponseDTO responseDTO = null;
	try {
	    long startTime = System.currentTimeMillis();
	    CloseableHttpResponse httpResponse = httpClient.execute(httpUriRequest);
	    long endTime = System.currentTimeMillis();
	    long duration = endTime - startTime;
	    responseDTO = buildRfResponse(httpResponse, duration, rfRequestDTO);
	} catch (ClientProtocolException e) {
	    logger.error(e.getMessage(), e);
	} catch (IOException e) {
	    logger.error(e.getMessage(), e);
	} finally {
	    try {
		if (httpClient != null) {
		    httpClient.close();
		}
	    } catch (IOException e) {
		logger.error(e.getMessage(), e);
	    }
	}
	return responseDTO;
    }

    private RfResponseDTO buildRfResponse(CloseableHttpResponse httpResponse, long duration, RfRequestDTO rfRequestDTO) throws IOException {
	RfResponseDTO responseDTO = buildRfResponse(httpResponse);

	AssertionDTO assertionDTO = rfRequestDTO.getAssertionDTO() != null ? rfRequestDTO.getAssertionDTO() : new AssertionDTO();

	assertionDTO.setResponseTime((int) duration);
	HttpEntity entity = httpResponse.getEntity();
	assertionDTO.setBodyContentType(entity.getContentType() != null ? entity.getContentType().getValue() : null);
	assertionDTO.setResponseSize(responseDTO.getBody().length());
	assertionDTO.setStatusCode(httpResponse.getStatusLine().getStatusCode());
	responseDTO.setAssertionDTO(assertionDTO);
	return responseDTO;
    }

    public RfResponseDTO processHttpRequest(HttpRequestBase baseRequest, CloseableHttpClient httpclient) throws IOException {
	Header[] requestHeaders = baseRequest.getAllHeaders();
	logger.info("request headers length : " + requestHeaders.length);

	for (Header requestHeader : requestHeaders) {
	    logger.info("request header - name : " + requestHeader.getName() + " value : " + requestHeader.getValue());
	}

	CloseableHttpResponse httpResponse = httpclient.execute(baseRequest);

	RfResponseDTO responseDTO = buildRfResponse(httpResponse);
	return responseDTO;
    }

    private RfResponseDTO buildRfResponse(CloseableHttpResponse httpResponse) throws IOException {
	RfResponseDTO responseDTO = new RfResponseDTO();
	String responseBody = "";
	List<RfHeaderDTO> headers = new ArrayList<RfHeaderDTO>();
	try {
	    logger.info("response status : " + httpResponse.getStatusLine());
	    responseDTO.setStatus(httpResponse.getStatusLine().getStatusCode() + " " + httpResponse.getStatusLine().getReasonPhrase());
	    HttpEntity responseEntity = httpResponse.getEntity();
	    Header[] responseHeaders = httpResponse.getAllHeaders();

	    RfHeaderDTO headerDTO = null;
	    for (Header responseHeader : responseHeaders) {
		// logger.info("response header - name : " + responseHeader.getName() + " value : " + responseHeader.getValue());
		headerDTO = new RfHeaderDTO();
		headerDTO.setHeaderName(responseHeader.getName());
		headerDTO.setHeaderValue(responseHeader.getValue());
		headers.add(headerDTO);
	    }
	    Header contentType = responseEntity.getContentType();
	    logger.info("response contentType : " + contentType);

	    // logger.info("content : " + EntityUtils.toString(responseEntity));
	    responseBody = EntityUtils.toString(responseEntity);
	    EntityUtils.consume(responseEntity);
	} finally {
	    httpResponse.close();
	}
	responseDTO.setBody(responseBody);
	responseDTO.setHeaders(headers);
	return responseDTO;
    }

    public RfResponseDTO process(RfRequestDTO rfRequestDTO) throws IOException {
	return null;
    }

}
