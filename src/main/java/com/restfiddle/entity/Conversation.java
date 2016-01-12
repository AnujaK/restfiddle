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

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class Conversation extends NamedEntity {
    private static final long serialVersionUID = 1L;

    @DBRef
    private RfRequest rfRequest;

    @DBRef
    private RfResponse rfResponse;

    private Long duration;

    private String nodeId;

    private String workspaceId;

    /**
     * It acts as a reference to track/log run-project/run-folder functionality
     */
    private String runnerLogId;

    private Date lastRunDate;

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

    public Long getDuration() {
	return duration;
    }

    public void setDuration(Long duration) {
	this.duration = duration;
    }

    public String getNodeId() {
	return nodeId;
    }

    public void setNodeId(String nodeId) {
	this.nodeId = nodeId;
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

    public String getRunnerLogId() {
	return runnerLogId;
    }

    public void setRunnerLogId(String runnerLogId) {
	this.runnerLogId = runnerLogId;
    }
}
