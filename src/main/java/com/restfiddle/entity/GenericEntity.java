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

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class GenericEntity extends NamedEntity {
    private static final long serialVersionUID = 1L;

    private List<GenericEntityField> fields = new ArrayList<GenericEntityField>();

    @DBRef
    private List<GenericEntityData> entityDataList = new ArrayList<GenericEntityData>();

    private String baseNodeId;

    public List<GenericEntityField> getFields() {
	return fields;
    }

    public void setFields(List<GenericEntityField> fields) {
	this.fields = fields;
    }

    public List<GenericEntityData> getEntityDataList() {
	return entityDataList;
    }

    public void setEntityDataList(List<GenericEntityData> entityDataList) {
	this.entityDataList = entityDataList;
    }

    public String getBaseNodeId() {
	return baseNodeId;
    }

    public void setBaseNodeId(String baseNodeId) {
	this.baseNodeId = baseNodeId;
    }

}
