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
package com.restfiddle.controller.rest.sample;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restfiddle.dto.StatusResponse;

@RestController
@EnableAutoConfiguration
@ComponentScan
public class SampleRestApiController {
    Logger logger = LoggerFactory.getLogger(SampleRestApiController.class);

    @RequestMapping("/api/ping")
    String ping() {
	return "Server is up and running!";
    }

    @RequestMapping(value = "/api/test/formparam", method = RequestMethod.POST)
    public StatusResponse formParamTest(@RequestParam(value = "name", required = false) String name,
	    @RequestParam(value = "data", required = false) String data) {
	StatusResponse res = new StatusResponse();

	JSONObject obj = new JSONObject();
	obj.append("name", name);
	obj.append("data", data);
	res.setMessage(obj.toString());

	return res;
    }
}