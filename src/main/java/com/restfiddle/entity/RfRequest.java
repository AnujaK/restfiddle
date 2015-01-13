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

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class RfRequest extends NamedEntity {
    private static final long serialVersionUID = 1L;

    private String apiUrl;
    private String methodType;

    @Lob
    private byte[] apiBody;

    @Transient
    private String apiBodyString;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RfHeader> rfHeaders;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RfCookie> rfCookies;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UrlParam> urlParams;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FormParam> formParams;

    @OneToOne(mappedBy = "rfRequest")
    @JsonBackReference
    private Conversation item;

    public Conversation getItem() {
	return item;
    }

    public void setItem(Conversation item) {
	this.item = item;
    }

    public String getApiUrl() {
	return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
	this.apiUrl = apiUrl;
    }

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

    public byte[] getApiBody() {
	return apiBody;
    }

    public void setApiBody(byte[] apiBody) {
	this.apiBody = apiBody;
    }

    public String getApiBodyString() {
	if (apiBody == null) {
	    return "";
	}
	return new String(apiBody);
    }

    public void setApiBodyString(String apiBodyString) {
	this.apiBodyString = apiBodyString;
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

}
