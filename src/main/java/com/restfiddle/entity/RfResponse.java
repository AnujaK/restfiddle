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

public class RfResponse extends NamedEntity {
    private static final long serialVersionUID = 1L;

    private String body;

    private List<RfHeader> rfHeaders;

    private Conversation item;
    
    private Assertion assertion;

    public Conversation getItem() {
	return item;
    }

    public void setItem(Conversation item) {
	this.item = item;
    }

    public List<RfHeader> getRfHeaders() {
	return rfHeaders;
    }

    public void setRfHeaders(List<RfHeader> rfHeaders) {
	this.rfHeaders = rfHeaders;
    }

    public Assertion getAssertion() {
	return assertion;
    }

    public void setAssertion(Assertion assertion) {
	this.assertion = assertion;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
