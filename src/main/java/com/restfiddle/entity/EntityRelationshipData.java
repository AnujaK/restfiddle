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

public class EntityRelationshipData extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    private Long idOfEntityA;

    private Long idOfEntityB;

    public Long getIdOfEntityA() {
	return idOfEntityA;
    }

    public void setIdOfEntityA(Long idOfEntityA) {
	this.idOfEntityA = idOfEntityA;
    }

    public Long getIdOfEntityB() {
	return idOfEntityB;
    }

    public void setIdOfEntityB(Long idOfEntityB) {
	this.idOfEntityB = idOfEntityB;
    }

    public static long getSerialversionuid() {
	return serialVersionUID;
    }
}
