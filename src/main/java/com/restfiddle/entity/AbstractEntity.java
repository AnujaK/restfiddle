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

import java.io.Serializable;

import javax.persistence.Version;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;

public abstract class AbstractEntity implements Serializable, Cloneable, Comparable<Object> {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Version
    private long version = 0;

    public String toString() {
	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public boolean equals(Object o) {
	return EqualsBuilder.reflectionEquals(this, o);
    }

    public int compareTo(Object o) {
	return CompareToBuilder.reflectionCompare(this, o);
    }

    public int hashCode(Object o) {
	return HashCodeBuilder.reflectionHashCode(o);
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public long getVersion() {
	return version;
    }

    public void setVersion(long version) {
	this.version = version;
    }

}
