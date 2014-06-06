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

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.restfiddle.dto.RfRequestDTO;
import com.restfiddle.exceptions.ApiException;
import com.restfiddle.handler.RequestHandler;
import com.restfiddle.handler.http.GenericHandler;

@RestController
@EnableAutoConfiguration
@ComponentScan
public class ApiController {

    Logger logger = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    RequestHandler requestHandler;

    @RequestMapping(value = "/api/processor", method = RequestMethod.POST, headers = "Accept=application/json")
    String processor(@RequestBody RfRequestDTO rfRequestDTO) {
	try {
	    GenericHandler handler = requestHandler.getHandler(rfRequestDTO.getMethodType());
	    return handler.process(rfRequestDTO);

	} catch (IOException e) {
	    logger.error("IO Exception", e);
	    throw new ApiException(e);
	}
    }

}
