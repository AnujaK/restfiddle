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

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

public class ConversationDTO extends BaseDTO {

    @JsonManagedReference
    private RfRequestDTO rfRequestDTO;

    @JsonManagedReference
    private RfResponseDTO rfResponseDTO;

    private Long duration;
    
    @JsonBackReference
    private NodeDTO nodeDTO;
    
    private String workspaceId;
    
    private Date lastRunDate;

    public RfRequestDTO getRfRequestDTO() {
	return rfRequestDTO;
    }

    public void setRfRequestDTO(RfRequestDTO rfRequestDTO) {
	this.rfRequestDTO = rfRequestDTO;
    }

    public RfResponseDTO getRfResponseDTO() {
	return rfResponseDTO;
    }

    public void setRfResponseDTO(RfResponseDTO rfResponseDTO) {
	this.rfResponseDTO = rfResponseDTO;
    }

    public Long getDuration() {
	return duration;
    }

    public void setDuration(Long duration) {
	this.duration = duration;
    }
    

    public NodeDTO getNodeDTO() {
        return nodeDTO;
    }

    public void setNodeDTO(NodeDTO nodeDTO) {
        this.nodeDTO = nodeDTO;
    }

    public String getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
    }

    public Date getLastRunDate() {
        return lastRunDate;
    }

    public void setLastRunDate(Date lastRunDate) {
        this.lastRunDate = lastRunDate;
    }
}
