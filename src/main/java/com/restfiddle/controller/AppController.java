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
package com.restfiddle.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AppController {
    Logger logger = LoggerFactory.getLogger(AppController.class);

    @Value("${application.message:REST Fiddle}")
    private String message = "REST Fiddle";

    @RequestMapping("/")
    public String home(Map<String, Object> model) {
	model.put("time", new Date());
	model.put("message", this.message);
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	model.put("currentUser", (authentication == null) ? null : (UserDetails) authentication.getPrincipal());
	return "home";
    }

    @RequestMapping(value = "/oauth/response", method = RequestMethod.GET)
    public String method(HttpServletRequest request) {
	return "oauth-response";
    }

}
