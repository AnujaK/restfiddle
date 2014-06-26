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

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class RfRequest extends NamedEntity {
    private static final long serialVersionUID = 1L;

    private String apiUrl;
    private String methodType;
    private String apiBody;

    @OneToMany
    private List<RfHeader> rfHeaders;

    @OneToMany
    private List<RfCookie> rfCookies;

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

    public String getApiBody() {
	return apiBody;
    }

    public void setApiBody(String apiBody) {
	this.apiBody = apiBody;
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

}
