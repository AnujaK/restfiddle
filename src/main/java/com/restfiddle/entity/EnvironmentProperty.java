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
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class EnvironmentProperty extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    private String propertyName;

    private String propertyValue;

    @ManyToOne
    @JsonBackReference
    private Environment environment;

    public String getPropertyName() {
	return propertyName;
    }

    public void setPropertyName(String propertyName) {
	this.propertyName = propertyName;
    }

    public String getPropertyValue() {
	return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
	this.propertyValue = propertyValue;
    }

    public Environment getEnvironment() {
	return environment;
    }

    public void setEnvironment(Environment environment) {
	this.environment = environment;
    }

}
