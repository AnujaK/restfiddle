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

public class RfResponseDTO extends BaseDTO {
    private String body;

    private List<RfHeaderDTO> headers;
    
    @JsonBackReference
    private ConversationDTO itemDTO;

    private AssertionDTO assertionDTO;
    
    public String getBody() {
	return body;
    }

    public void setBody(String body) {
	this.body = body;
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

    public AssertionDTO getAssertionDTO() {
	return assertionDTO;
    }

    public void setAssertionDTO(AssertionDTO assertionDTO) {
	this.assertionDTO = assertionDTO;
    }

}
