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
package com.restfiddle.controller.rest;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.restfiddle.dao.HttpRequestHeaderRepository;
import com.restfiddle.dao.RfRequestRepository;
import com.restfiddle.entity.HttpRequestHeader;

@RestController
@Transactional
public class RfRequestController {

    @Resource
    private RfRequestRepository requestRepository;

    @Resource
    private HttpRequestHeaderRepository requestHeaderRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @RequestMapping(value = "/api/requests/api-urls", method = RequestMethod.GET)
    public @ResponseBody
    List<String> findUniqueApiUrls() {
	@SuppressWarnings("unchecked")
	List<String> uniqueRfRequests = (List<String>) mongoTemplate.getCollection("rfRequest").distinct("apiUrlString");
	// List<RfRequest> uniqueRfRequests = requestRepository.findDistinctRfRequestByApiUrlString();
	return uniqueRfRequests;// uniqueRfRequests;
    }

    @RequestMapping(value = "/api/requests/http-headers", method = RequestMethod.GET)
    public @ResponseBody
    List<String> findHttpRequestHeaders() {
	List<HttpRequestHeader> requestHeaders = requestHeaderRepository.findAll();

	List<String> headers = new ArrayList<String>();
	for (HttpRequestHeader httpRequestHeader : requestHeaders) {
	    headers.add(httpRequestHeader.getName());
	}
	return headers;
    }

}
