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
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.restfiddle.dto.FormDataDTO;
import com.restfiddle.dto.RfHeaderDTO;
import com.restfiddle.dto.RfRequestDTO;
import com.restfiddle.dto.UrlParamDTO;
import com.restfiddle.handler.http.auth.BasicAuthHandler;

@Component
public class RfRequestBuilder {
    Logger logger = LoggerFactory.getLogger(RfRequestBuilder.class);

    // private static String[] entityContainerTypes = { "POST", "PUT", "PATCH", "DELETE" };

    @Autowired
    private BasicAuthHandler basicAuthHandler;

    public HttpUriRequest build(RfRequestDTO requestDTO) {
	String methodType = requestDTO.getMethodType();

	RequestBuilder requestBuilder = RequestBuilder.create(methodType);

	setUriWithParams(requestDTO, requestBuilder);

	setHeaders(requestDTO, requestBuilder);

	// Set Basic Authentication
	basicAuthHandler.setBasicAuthWithBase64Encode(requestDTO, requestBuilder);

	setRequestEntity(requestDTO, requestBuilder);

	HttpUriRequest httpUriRequest = requestBuilder.build();
	return httpUriRequest;
    }

    private void setUriWithParams(RfRequestDTO requestDTO, RequestBuilder requestBuilder) {
	String apiUrl = requestDTO.getEvaluatedApiUrl();
	if(apiUrl == null || apiUrl.equals("")){
	    apiUrl = requestDTO.getApiUrl();
	}
	List<UrlParamDTO> urlParams = requestDTO.getUrlParams();
	if (urlParams != null && !urlParams.isEmpty()) {
	    apiUrl = buildUrlWithParams(apiUrl, urlParams);
	}
	requestBuilder.setUri(apiUrl);
	// TODO : Shall we set this API URL in UI Layer itself?
	//requestDTO.setApiUrl(apiUrl);
    }

    private String buildUrlWithParams(String apiUrl, List<UrlParamDTO> urlParams) {
	StringBuilder sb = new StringBuilder();
	sb.append(apiUrl);
	boolean paramsAlreadyExist = false;
	if (apiUrl.indexOf("?") > 0) {
	    paramsAlreadyExist = true;
	}

	for (int i = 0; i < urlParams.size(); i++) {
	    UrlParamDTO urlParam = urlParams.get(i);
	    if (i == 0 && !paramsAlreadyExist) {
		sb.append("?");
	    } else {
		sb.append("&");
	    }
	    try {
		sb.append(URLEncoder.encode(urlParam.getKey(), "UTF-8") + "=" + URLEncoder.encode(urlParam.getValue(), "UTF-8"));
	    } catch (UnsupportedEncodingException e) {
		logger.error(e.toString());
	    }
	}
	return sb.toString();
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
	if (requestDTO.getApiBody() != null && !requestDTO.getApiBody().isEmpty()) {
	    try {
		requestBuilder.setEntity(new StringEntity(requestDTO.getApiBody()));

	    } catch (UnsupportedEncodingException e) {
		logger.error(e.getMessage(), e);
	    }
	} else if (formParams != null && !formParams.isEmpty()) {
	    // NOTE : http://stackoverflow.com/questions/12745710/apache-httpclient-4-2-1-post-request-to-fill-form-after-successful-login
	    requestBuilder.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
	    List<NameValuePair> nvps2 = new ArrayList<NameValuePair>();
	    for (FormDataDTO formDataDTO : formParams) {
		nvps2.add(new BasicNameValuePair(formDataDTO.getKey(), formDataDTO.getValue()));
	    }
	    try {
		requestBuilder.setEntity(new UrlEncodedFormEntity(nvps2));
	    } catch (UnsupportedEncodingException e) {
	    }
	}
    }
}
