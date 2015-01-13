/*
 * Copyright 2015 Ranjan Kumar
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

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Transient;

@Entity
public class FormParam extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    private String key;

    @Lob
    private byte[] value;

    @Transient
    private String valueString;

    public String getKey() {
	return key;
    }

    public void setKey(String key) {
	this.key = key;
    }

    public byte[] getValue() {
	return value;
    }

    public void setValue(byte[] value) {
	this.value = value;
    }

    public String getValueString() {
	if (value == null) {
	    return null;
	} else {
	    return new String(value);
	}
    }

    public void setValueString(String valueString) {
	this.valueString = valueString;
	if (valueString != null) {
	    this.setValue(valueString.getBytes());
	} else {
	    this.setValue(null);
	}
    }

}
