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

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Conversation extends NamedEntity {
    private static final long serialVersionUID = 1L;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonManagedReference
    private RfRequest rfRequest;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonManagedReference
    private RfResponse rfResponse;

    public RfRequest getRfRequest() {
	return rfRequest;
    }

    public void setRfRequest(RfRequest rfRequest) {
	this.rfRequest = rfRequest;
    }

    public RfResponse getRfResponse() {
	return rfResponse;
    }

    public void setRfResponse(RfResponse rfResponse) {
	this.rfResponse = rfResponse;
    }
}
