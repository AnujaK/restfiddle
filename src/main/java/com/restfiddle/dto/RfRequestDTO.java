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

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

public class RfRequestDTO extends BaseDTO {
    private String apiUrl;

    private String methodType;

    private String apiBody;

    private List<RfHeaderDTO> headers;
    
    private List<UrlParamDTO> urlParams;
    
    private List<FormDataDTO> formParams; 
    
    private BasicAuthDTO basicAuthDTO;

    private DigestAuthDTO digestAuthDTO;
    
    @JsonBackReference
    private ConversationDTO itemDTO;
    
    private AssertionDTO assertionDTO;
    
    private String workspaceId;
    
    private String evaluatedApiUrl;

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

    public ConversationDTO getItemDTO() {
	return itemDTO;
    }

    public void setItemDTO(ConversationDTO itemDTO) {
	this.itemDTO = itemDTO;
    }

    public List<RfHeaderDTO> getHeaders() {
	return headers;
    }

    public void setHeaders(List<RfHeaderDTO> headers) {
	this.headers = headers;
    }

    public List<UrlParamDTO> getUrlParams() {
        return urlParams;
    }

    public void setUrlParams(List<UrlParamDTO> urlParams) {
        this.urlParams = urlParams;
    }

    public List<FormDataDTO> getFormParams() {
        return formParams;
    }

    public void setFormParams(List<FormDataDTO> formParams) {
        this.formParams = formParams;
    }

    public BasicAuthDTO getBasicAuthDTO() {
        return basicAuthDTO;
    }

    public void setBasicAuthDTO(BasicAuthDTO basicAuthDTO) {
        this.basicAuthDTO = basicAuthDTO;
    }

    public DigestAuthDTO getDigestAuthDTO() {
        return digestAuthDTO;
    }

    public void setDigestAuthDTO(DigestAuthDTO digestAuthDTO) {
        this.digestAuthDTO = digestAuthDTO;
    }

    public AssertionDTO getAssertionDTO() {
	return assertionDTO;
    }

    public void setAssertionDTO(AssertionDTO assertionDTO) {
	this.assertionDTO = assertionDTO;
    }

    public String getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
    }

    public String getEvaluatedApiUrl() {
        return evaluatedApiUrl;
    }

    public void setEvaluatedApiUrl(String evaluatedApiUrl) {
        this.evaluatedApiUrl = evaluatedApiUrl;
    }
}