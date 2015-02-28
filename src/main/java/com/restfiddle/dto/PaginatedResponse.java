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

import java.util.List;

public class PaginatedResponse<T> {

    Integer page;

    Integer limit;

    Integer totalPages;

    Long totalElements;

    List<T> data;

    public Integer getTotalPages() {
	return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
	this.totalPages = totalPages;
    }

    public Long getTotalElements() {
	return totalElements;
    }

    public void setTotalElements(Long totalElements) {
	this.totalElements = totalElements;
    }

    public List<T> getData() {
	return data;
    }

    public void setData(List<T> data) {
	this.data = data;
    }

    public Integer getPage() {
	return page;
    }

    public void setPage(Integer page) {
	this.page = page;
    }

    public Integer getLimit() {
	return limit;
    }

    public void setLimit(Integer limit) {
	this.limit = limit;
    }
}
