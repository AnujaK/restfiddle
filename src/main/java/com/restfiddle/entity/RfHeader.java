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

import javax.persistence.Lob;
import javax.persistence.Transient;

public class RfHeader extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    private String headerName;
    @Lob
    private byte[] headerValue;

    @Transient
    private String headerValueString;

    public String getHeaderName() {
	return headerName;
    }

    public void setHeaderName(String headerName) {
	this.headerName = headerName;
    }

    public byte[] getHeaderValue() {
	return headerValue;
    }

    public void setHeaderValue(byte[] headerValue) {
	this.headerValue = headerValue;
    }

    public String getHeaderValueString() {
	if (headerValue == null) {
	    return null;
	} else {
	    return new String(headerValue);
	}
    }

    public void setHeaderValueString(String headerValueString) {
	this.headerValueString = headerValueString;
	if (headerValueString != null) {
	    this.setHeaderValue(headerValueString.getBytes());
	} else {
	    this.setHeaderValue(null);
	}
    }

}
