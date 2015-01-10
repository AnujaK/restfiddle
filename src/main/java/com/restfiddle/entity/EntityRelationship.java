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

/**
 * Note : There will NOT be two separate records to A -> B and B -> A.
 * 
 */
public class EntityRelationship extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    private String entityA;

    private String entityB;

    private String relationshipType;// ONETOONE, ONETOMANY, MANYTOMANY

    private String relationshipNameAtoB;// Optional Field

    private String relationshipNameBtoA;// Optional Field

    public String getEntityA() {
	return entityA;
    }

    public void setEntityA(String entityA) {
	this.entityA = entityA;
    }

    public String getEntityB() {
	return entityB;
    }

    public void setEntityB(String entityB) {
	this.entityB = entityB;
    }

    public String getRelationshipType() {
	return relationshipType;
    }

    public void setRelationshipType(String relationshipType) {
	this.relationshipType = relationshipType;
    }

    public String getRelationshipNameAtoB() {
	return relationshipNameAtoB;
    }

    public void setRelationshipNameAtoB(String relationshipNameAtoB) {
	this.relationshipNameAtoB = relationshipNameAtoB;
    }

    public String getRelationshipNameBtoA() {
	return relationshipNameBtoA;
    }

    public void setRelationshipNameBtoA(String relationshipNameBtoA) {
	this.relationshipNameBtoA = relationshipNameBtoA;
    }

    public static long getSerialversionuid() {
	return serialVersionUID;
    }

}
