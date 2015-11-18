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
package com.restfiddle.dto;

public class NodeStatusResponseDTO extends BaseDTO {
    private String apiUrl;
    private String methodType;
    private String statusCode;
    private Long duration;
    private Integer successAsserts;
    private Integer failureAsserts;

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

    public String getStatusCode() {
	return statusCode;
    }

    public void setStatusCode(String statusCode) {
	this.statusCode = statusCode;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Integer getSuccessAsserts() {
        return successAsserts;
    }

    public void setSuccessAsserts(Integer successAsserts) {
        this.successAsserts = successAsserts;
    }

    public Integer getFailureAsserts() {
        return failureAsserts;
    }

    public void setFailureAsserts(Integer failureAsserts) {
        this.failureAsserts = failureAsserts;
    }

}
