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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

public class Environment extends NamedEntity {
    private static final long serialVersionUID = 1L;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "environment", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<EnvironmentProperty> properties = new ArrayList<EnvironmentProperty>();

    public List<EnvironmentProperty> getProperties() {
	return properties;
    }

    public void setProperties(List<EnvironmentProperty> properties) {
	this.properties = properties;
    }

}
