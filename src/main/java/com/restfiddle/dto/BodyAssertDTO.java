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
package com.restfiddle.dto;

public class BodyAssertDTO {

    private String propertyName;

    private String comparator;

    private String expectedValue;
    
    private String actualValue;
    
    private boolean success;

    public String getPropertyName() {
	return propertyName;
    }

    public void setPropertyName(String propertyName) {
	this.propertyName = propertyName;
    }

    public String getComparator() {
	return comparator;
    }

    public void setComparator(String comparator) {
	this.comparator = comparator;
    }

    public String getExpectedValue() {
	return expectedValue;
    }

    public void setExpectedValue(String expectedValue) {
	this.expectedValue = expectedValue;
    }

    public boolean isSuccess() {
	return success;
    }

    public void setSuccess(boolean success) {
	this.success = success;
    }

    public String getActualValue() {
	return actualValue;
    }

    public void setActualValue(String actualValue) {
	this.actualValue = actualValue;
    }

}
