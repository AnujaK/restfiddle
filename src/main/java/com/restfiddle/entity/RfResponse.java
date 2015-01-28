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
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class RfResponse extends NamedEntity {
    private static final long serialVersionUID = 1L;

    @Lob
    private byte[] body;

    @Transient
    private String bodyString;

    @OneToMany
    private List<RfHeader> rfHeaders;

    @OneToOne(mappedBy = "rfResponse")
    @JsonBackReference
    private Conversation item;

    public byte[] getBody() {
	return body;
    }

    public void setBody(byte[] body) {
	this.body = body;
    }

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

    public String getBodyString() {
	return bodyString;
    }

    public void setBodyString(String bodyString) {
	this.bodyString = bodyString;
    }

}
