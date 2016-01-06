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
package com.restfiddle.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class RfRequest extends NamedEntity {
    private static final long serialVersionUID = 1L;

    private String apiUrl;

    private String methodType;

    private String apiBody;

    private List<RfHeader> rfHeaders = new ArrayList<RfHeader>();

    private List<RfCookie> rfCookies;

    private List<UrlParam> urlParams;

    private List<FormParam> formParams;

    private BasicAuth basicAuth;

    private DigestAuth digestAuth;

    private OAuth2 oAuth2;

    private String conversationId;
    
    private String evaluatedApiUrl;

    @DBRef
    private Assertion assertion;

    public String getMethodType() {
	return methodType;
    }

    public void setMethodType(String methodType) {
	this.methodType = methodType;
    }

    public List<RfHeader> getRfHeaders() {
	return rfHeaders;
    }

    public void setRfHeaders(List<RfHeader> rfHeaders) {
	this.rfHeaders = rfHeaders;
    }

    public List<RfCookie> getRfCookies() {
	return rfCookies;
    }

    public void setRfCookies(List<RfCookie> rfCookies) {
	this.rfCookies = rfCookies;
    }

    public List<UrlParam> getUrlParams() {
	return urlParams;
    }

    public void setUrlParams(List<UrlParam> urlParams) {
	this.urlParams = urlParams;
    }

    public List<FormParam> getFormParams() {
	return formParams;
    }

    public void setFormParams(List<FormParam> formParams) {
	this.formParams = formParams;
    }

    public BasicAuth getBasicAuth() {
	return basicAuth;
    }

    public void setBasicAuth(BasicAuth basicAuth) {
	this.basicAuth = basicAuth;
    }

    public DigestAuth getDigestAuth() {
	return digestAuth;
    }

    public void setDigestAuth(DigestAuth digestAuth) {
	this.digestAuth = digestAuth;
    }

    public OAuth2 getoAuth2() {
	return oAuth2;
    }

    public void setoAuth2(OAuth2 oAuth2) {
	this.oAuth2 = oAuth2;
    }

    public Assertion getAssertion() {
	return assertion;
    }

    public void setAssertion(Assertion assertion) {
	this.assertion = assertion;
    }

    public String getConversationId() {
	return conversationId;
    }

    public void setConversationId(String conversationId) {
	this.conversationId = conversationId;
    }
    
    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getApiBody() {
        return apiBody;
    }

    public void setApiBody(String apiBody) {
        this.apiBody = apiBody;
    }

    public String getEvaluatedApiUrl() {
        return evaluatedApiUrl;
    }

    public void setEvaluatedApiUrl(String evaluatedApiUrl) {
        this.evaluatedApiUrl = evaluatedApiUrl;
    }
}
